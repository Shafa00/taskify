package com.taskify.service;

import com.taskify.model.task.AssignTaskRqModel;
import com.taskify.model.task.ChangeStatusRqModel;
import com.taskify.model.task.TaskRqModel;
import com.taskify.model.task.TaskRsModel;

import java.util.List;

public interface TaskService {
    TaskRsModel addTask(TaskRqModel taskRqModel, String email);
    List<TaskRsModel> getTasksOfOrganization(String email);
    TaskRsModel assignTask(AssignTaskRqModel assignTaskRqModel);
    TaskRsModel changeStatus(ChangeStatusRqModel changeStatusRqModel);
}
