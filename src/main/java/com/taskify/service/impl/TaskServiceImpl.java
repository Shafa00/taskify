package com.taskify.service.impl;

import com.taskify.entity.Task;
import com.taskify.entity.User;
import com.taskify.exception.DataNotFoundException;
import com.taskify.mapper.TaskMapper;
import com.taskify.model.task.TaskRqModel;
import com.taskify.model.task.TaskRsModel;
import com.taskify.repository.TaskRepository;
import com.taskify.repository.UserRepository;
import com.taskify.service.TaskService;
import com.taskify.utility.TaskStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepo;
    private final UserRepository userRepo;
    private final TaskMapper taskMapper;

    @Override
    public TaskRsModel addTask(TaskRqModel taskRqModel) {
        Task task = taskMapper.buildTask(taskRqModel);

        List<User> users = taskRqModel.getUserIds().stream()
                .map(this::getUserByUserId)
                .collect(Collectors.toList());

        task.setUsers(users);
        task.setStatus(TaskStatus.TODO);
        taskRepo.save(task);
        return TaskMapper.TASK_MAPPER_INSTANCE.buildTaskResponse(task);
    }

    @Override
    public List<TaskRsModel> getTasks() {
        return null;
    }

    @Override
    public TaskRsModel assignTask(String taskId, List<String> userIds) {
        return null;
    }

    @Override
    public TaskRsModel changeStatus(String status) {
        return null;
    }

    private User getUserByUserId(String userId) {
        return userRepo.findByUserId(userId).orElseThrow(() -> new DataNotFoundException("User not found"));
    }
}
