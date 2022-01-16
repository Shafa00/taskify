package com.taskify.service.impl;

import com.taskify.entity.Organization;
import com.taskify.entity.Task;
import com.taskify.entity.User;
import com.taskify.exception.DataNotFoundException;
import com.taskify.mapper.TaskMapper;
import com.taskify.model.task.TaskRqModel;
import com.taskify.model.task.TaskRsModel;
import com.taskify.repository.OrganizationRepository;
import com.taskify.repository.TaskRepository;
import com.taskify.repository.UserRepository;
import com.taskify.service.TaskService;
import com.taskify.utility.MailSenderService;
import com.taskify.utility.TaskStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.taskify.utility.Constant.*;
import static java.lang.String.format;

@AllArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepo;
    private final UserRepository userRepo;
    private final OrganizationRepository organizationRepo;
    private final TaskMapper taskMapper;
    private final MailSenderService mailSenderService;

    @Override
    public TaskRsModel addTask(TaskRqModel taskRqModel) {
        Task task = taskMapper.buildTask(taskRqModel);
        taskRepo.save(task);

        List<User> users = taskRqModel.getUserIds().stream()
                .map(this::getUserByUserId)
                .collect(Collectors.toList());

        task.setUsers(users);
        task.setStatus(TaskStatus.TODO);
        task.setOrganization(organizationRepo.findByOrganizationId(taskRqModel.getOrganizationId()).orElseThrow(() -> new DataNotFoundException("organization not found")));
        taskRepo.save(task);

        users.forEach(user -> {
            try {
                sendEmail(user.getEmail(), task);
            } catch (MessagingException e) {
                System.err.println("task assignment email can not be send");
            }
        });

        return TaskMapper.TASK_MAPPER_INSTANCE.buildTaskResponse(task);
    }

    @Override
    public List<TaskRsModel> getTasksOfOrganization(String organizationId) {
        Organization organization = organizationRepo.findByOrganizationId(organizationId)
                .orElseThrow(() -> new DataNotFoundException("Organization not found"));

        return taskRepo.findAllByOrganization(organization).stream()
                .map(TaskMapper.TASK_MAPPER_INSTANCE::buildTaskResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TaskRsModel assignTask(String taskId, List<String> userIds) {
        Task task = taskRepo.findByTaskId(taskId)
                .orElseThrow(() -> new DataNotFoundException(format("Task not found by given id %s", taskId)));

        List<User> users = new ArrayList<>();
        userIds.forEach(userId ->
                {
                    if (task.getUsers().stream().noneMatch(user -> user.getUserId().equals(userId))) {
                        User user = userRepo.findByUserId(userId)
                                .orElseThrow(() -> new DataNotFoundException("User not found"));
                        users.add(user);
                    }
                });
        task.setUsers(users);

        users.forEach(user -> {
            try {
                sendEmail(user.getEmail(), task);
            } catch (MessagingException e) {
                System.err.println("task assignment email can not be send");
            }
        });

        taskRepo.save(task);
        return TaskMapper.TASK_MAPPER_INSTANCE.buildTaskResponse(task);
    }

    @Override
    public TaskRsModel changeStatus(String taskId, String status) {
        Task task = taskRepo.findByTaskId(taskId)
                .orElseThrow(() -> new DataNotFoundException(format("Task not found by given id %s", taskId)));
        task.setStatus(TaskStatus.valueOf(status));
        taskRepo.save(task);
        return TaskMapper.TASK_MAPPER_INSTANCE.buildTaskResponse(task);
    }

    private User getUserByUserId(String userId) {
        return userRepo.findByUserId(userId).orElseThrow(() -> new DataNotFoundException("User not found"));
    }

    private void sendEmail(String email, Task task) throws MessagingException {
        mailSenderService
                .sendEmail(email, format(TASK_ASSIGNMENT_SUBJECT, task.getTitle()),
                        format(TASK_ASSIGNMENT_BODY, task.getDescription()));

    }
}
