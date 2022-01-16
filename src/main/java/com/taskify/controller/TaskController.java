package com.taskify.controller;

import com.taskify.model.ResponseModel;
import com.taskify.model.task.AssignTaskRqModel;
import com.taskify.model.task.ChangeStatusRqModel;
import com.taskify.model.task.TaskRqModel;
import com.taskify.model.task.TaskRsModel;
import com.taskify.service.TaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Task")
@Validated
public class TaskController {

    private final TaskService taskService;

    @ApiOperation("Add task and assign to initial users if needed")
    @PostMapping("/add-task")
    public ResponseEntity<ResponseModel<TaskRsModel>> addTask(@Valid @RequestBody TaskRqModel taskRqModel) {
        return ResponseEntity.ok(ResponseModel.of(taskService.addTask(taskRqModel), HttpStatus.CREATED));
    }

    @ApiOperation("Get tasks of organization")
    @GetMapping("/get-tasks")
    public ResponseEntity<ResponseModel<List<TaskRsModel>>> getTasksOfOrganization(Authentication auth) {
        String email = ((UserDetails) auth.getPrincipal()).getUsername();
        return ResponseEntity.ok(ResponseModel.of(taskService.getTasksOfOrganization(email), HttpStatus.OK));
    }

    @ApiOperation("Assign tasks to users")
    @PostMapping("/assign-task")
    public ResponseEntity<ResponseModel<TaskRsModel>> assignTask(@Valid @RequestBody AssignTaskRqModel assignTaskRqModel) {
        return ResponseEntity.ok(ResponseModel.of(taskService.assignTask(assignTaskRqModel), HttpStatus.OK));
    }

    @ApiOperation("Change status of tasks")
    @PostMapping("/change-status")
    public ResponseEntity<ResponseModel<TaskRsModel>> changeStatusOfTask(@Valid @RequestBody ChangeStatusRqModel changeStatusRqModel) {
        return ResponseEntity.ok(ResponseModel.of(taskService.changeStatus(changeStatusRqModel), HttpStatus.OK));
    }
}
