package com.taskify.service.impl;

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
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.taskify.utility.Constant.*;
import static com.taskify.utility.MessageConstant.*;
import static java.lang.String.format;

@Log4j2
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
                .map(this::getUserById)
                .collect(Collectors.toList());

        task.setUsers(users);
        task.setStatus(TaskStatus.TODO);
        task.setOrganization(organizationRepo.findByOrganizationId(taskRqModel.getOrganizationId()).orElseThrow(() -> new DataNotFoundException("organization not found")));
        taskRepo.save(task);
        log.info(TASK_CREATED_MSG, task);

        sendTaskAssignmentMsg(users, task);
        return TaskMapper.TASK_MAPPER_INSTANCE.buildTaskResponse(task);
    }

    @Override
    public List<TaskRsModel> getTasksOfOrganization(String email) {
        User user = getUserByEmail(email);

        return taskRepo.findAllByOrganization(user.getOrganization()).stream()
                .map(TaskMapper.TASK_MAPPER_INSTANCE::buildTaskResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TaskRsModel assignTask(AssignTaskRqModel assignTaskRqModel) {
        Task task = getTask(assignTaskRqModel.getTaskId());

        List<User> users = new ArrayList<>();
        assignTaskRqModel.getUserIds().forEach(userId ->
        {
            if (task.getUsers().stream().noneMatch(user -> user.getUserId().equals(userId))) {
                User user = getUserById(userId);
                users.add(user);
            }
        });
        task.setUsers(users);
        sendTaskAssignmentMsg(users, task);

        taskRepo.save(task);
        log.info(TASK_UPDATED_MSG, task);

        return TaskMapper.TASK_MAPPER_INSTANCE.buildTaskResponse(task);
    }

    @Override
    public TaskRsModel changeStatus(ChangeStatusRqModel changeStatusRqModel) {
        Task task = taskRepo.findByTaskId(changeStatusRqModel.getTaskId()).orElseThrow(
                () -> new DataNotFoundException(format(TASK_NOT_FOUND_MSG, changeStatusRqModel.getTaskId())));
        task.setStatus(TaskStatus.valueOf(changeStatusRqModel.getStatus()));
        taskRepo.save(task);

        log.info(TASK_UPDATED_MSG, task);
        return TaskMapper.TASK_MAPPER_INSTANCE.buildTaskResponse(task);
    }

    private User getUserById(String userId) {
        return userRepo.findByUserId(userId).orElseThrow(
                () -> new DataNotFoundException(format(USER_NOT_FOUND_MSG, userId)));
    }

    private void sendEmail(String email, Task task) throws MessagingException {
        mailSenderService
                .sendEmail(email, format(TASK_ASSIGNMENT_SUBJECT, task.getTitle()),
                        format(TASK_ASSIGNMENT_BODY, task.getDescription()));
    }

    private void sendTaskAssignmentMsg(List<User> users, Task task) {
        users.forEach(user -> {
            try {
                sendEmail(user.getEmail(), task);
                log.info(TASK_ASSIGNMENT_MSG, task, user);
            } catch (MessagingException e) {
                log.info(TASK_ASSIGNMENT_ERROR_MSG, task, user);
            }
        });
    }

    private User getUserByEmail(String email) {
        return userRepo.findByEmail(email).orElseThrow(
                () -> new DataNotFoundException(format(USER_NOT_FOUND_BY_EMAIL_MSG, email)));
    }

    private Task getTask(String taskId) {
        return taskRepo.findByTaskId(taskId).orElseThrow(
                () -> new DataNotFoundException(format(TASK_NOT_FOUND_MSG, taskId)));
    }
}
