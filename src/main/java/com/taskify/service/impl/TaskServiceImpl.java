package com.taskify.service.impl;

import com.taskify.model.task.TaskRqModel;
import com.taskify.model.task.TaskRsModel;
import com.taskify.service.TaskService;

import java.util.List;

public class TaskServiceImpl implements TaskService {
    @Override
    public TaskRsModel addTask(TaskRqModel taskRqModel) {
        return null;
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
}
