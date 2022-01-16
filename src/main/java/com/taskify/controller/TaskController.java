package com.taskify.controller;

import com.taskify.model.ResponseModel;
import com.taskify.model.task.TaskRqModel;
import com.taskify.model.task.TaskRsModel;
import com.taskify.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/add-task")
    public ResponseEntity<ResponseModel<TaskRsModel>> addTask(@RequestBody TaskRqModel taskRqModel) {
        return ResponseEntity.ok(ResponseModel.of(taskService.addTask(taskRqModel), HttpStatus.CREATED));
    }

    @GetMapping("/get-tasks")
    public ResponseEntity<ResponseModel<List<TaskRsModel>>> getTasksOfOrganization(
            @RequestParam("id") String organizationId) {
        return ResponseEntity.ok(ResponseModel.of(taskService.getTasksOfOrganization(organizationId), HttpStatus.OK));
    }

    @PostMapping("/assign-task")
    public ResponseEntity<ResponseModel<TaskRsModel>> assignTask(
            @RequestParam("task-id") String taskId,
            @RequestParam("user-ids") List<String> userIds) {
        return ResponseEntity.ok(ResponseModel.of(taskService.assignTask(taskId, userIds), HttpStatus.OK));
    }

    @PostMapping("/change-status")
    public ResponseEntity<ResponseModel<TaskRsModel>> changeStatusOfTask(
            @RequestParam("task-id") String taskId,
            @RequestParam("task-status") String status) {
        return ResponseEntity.ok(ResponseModel.of(taskService.changeStatus(taskId, status), HttpStatus.OK));
    }
}
