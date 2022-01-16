package com.taskify.service.impl;

import com.taskify.entity.Organization;
import com.taskify.entity.Task;
import com.taskify.entity.User;
import com.taskify.exception.DataNotFoundException;
import com.taskify.mapper.TaskMapper;
import com.taskify.model.task.AssignTaskRqModel;
import com.taskify.model.task.ChangeStatusRqModel;
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
    private final MailSenderService mailSenderService;

    @Override
    public TaskRsModel addTask(TaskRqModel taskRqModel) {
        Task task = TaskMapper.TASK_MAPPER_INSTANCE.buildTask(taskRqModel);

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
    public List<TaskRsModel> getTasksOfOrganization(String email) {
        User user = getUser(email);

        return taskRepo.findAllByOrganization(user.getOrganization()).stream()
                .map(TaskMapper.TASK_MAPPER_INSTANCE::buildTaskResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TaskRsModel assignTask(AssignTaskRqModel assignTaskRqModel) {
        Task task = taskRepo.findByTaskId(assignTaskRqModel.getTaskId())
                .orElseThrow(() -> new DataNotFoundException(format("Task not found by given id %s", assignTaskRqModel.getTaskId())));

        List<User> users = new ArrayList<>();
        assignTaskRqModel.getUserIds().forEach(userId ->
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
    public TaskRsModel changeStatus(ChangeStatusRqModel changeStatusRqModel) {
        Task task = taskRepo.findByTaskId(changeStatusRqModel.getTaskId())
                .orElseThrow(() -> new DataNotFoundException(format("Task not found by given id %s", changeStatusRqModel.getTaskId())));
        task.setStatus(TaskStatus.valueOf(changeStatusRqModel.getStatus()));
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

    private User getUser(String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new DataNotFoundException("Role is not found"));
    }
}
