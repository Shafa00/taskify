package com.taskify.service.impl;

import com.taskify.entity.Organization;
import com.taskify.entity.Task;
import com.taskify.entity.User;
import com.taskify.exception.DataNotFoundException;
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

import static com.taskify.mapper.TaskMapper.TASK_MAPPER_INSTANCE;
import static com.taskify.utility.Constant.TASK_ASSIGNMENT_BODY;
import static com.taskify.utility.Constant.TASK_ASSIGNMENT_SUBJECT;
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
    public TaskRsModel addTask(TaskRqModel taskRqModel, String email) {
        User user = getUserByEmail(email);

        Task task = TASK_MAPPER_INSTANCE.buildTask(taskRqModel);

        List<User> users = taskRqModel.getUserIds().stream()
                .map(this::getUserById)
                .collect(Collectors.toList());

        task.setUsers(users);
        task.setStatus(TaskStatus.TODO);
        task.setOrganization(getOrganization(user.getOrganization().getOrganizationId()));
        taskRepo.save(task);
        log.info(TASK_CREATED_MSG, TASK_MAPPER_INSTANCE.buildTaskResponse(task));

        sendTaskAssignmentMsg(users, task);
        return TASK_MAPPER_INSTANCE.buildTaskResponse(task);
    }

    @Override
    public List<TaskRsModel> getTasksOfOrganization(String email) {
        User user = getUserByEmail(email);

        return taskRepo.findAllByOrganization(user.getOrganization()).stream()
                .map(TASK_MAPPER_INSTANCE::buildTaskResponse)
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
        log.info(TASK_UPDATED_MSG, TASK_MAPPER_INSTANCE.buildTaskResponse(task));

        return TASK_MAPPER_INSTANCE.buildTaskResponse(task);
    }

    @Override
    public TaskRsModel changeStatus(ChangeStatusRqModel changeStatusRqModel) {
        Task task = taskRepo.findByTaskId(changeStatusRqModel.getTaskId()).orElseThrow(
                () -> new DataNotFoundException(format(TASK_NOT_FOUND_MSG, changeStatusRqModel.getTaskId())));
        task.setStatus(TaskStatus.valueOf(changeStatusRqModel.getStatus()));
        taskRepo.save(task);

        log.info(TASK_UPDATED_MSG, TASK_MAPPER_INSTANCE.buildTaskResponse(task));
        return TASK_MAPPER_INSTANCE.buildTaskResponse(task);
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
                log.info(TASK_ASSIGNMENT_MSG, TASK_MAPPER_INSTANCE.buildTaskResponse(task), user.getUserId());
            } catch (MessagingException e) {
                log.info(TASK_ASSIGNMENT_ERROR_MSG, TASK_MAPPER_INSTANCE.buildTaskResponse(task), user.getUserId());
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

    private Organization getOrganization(String organizationId) {
        return organizationRepo.findByOrganizationId(organizationId)
                .orElseThrow(() -> new DataNotFoundException(ORGANIZATION_NOT_FOUND_MSG));    }
}
