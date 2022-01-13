package com.taskify.service;

import com.taskify.model.task.TaskRqModel;
import com.taskify.model.task.TaskRsModel;

import java.util.List;

public interface TaskService {
    TaskRsModel addTask(TaskRqModel taskRqModel);
    List<TaskRsModel> getTasks();
    TaskRsModel assignTask(String taskId, List<String> userIds);
    TaskRsModel changeStatus(String status);
}