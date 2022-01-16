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
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.taskify.utility.MessageConstant.RESPONSE_MSG;
import static com.taskify.utility.UrlConstant.*;

@AllArgsConstructor
@RestController
@Api(produces = MediaType.APPLICATION_JSON_VALUE, tags = "Task")
@Validated
@Log4j2
public class TaskController {

    private final TaskService taskService;

    @ApiOperation("Add task and assign to initial users if needed")
    @PostMapping(ADD_TASK_URL)
    public ResponseEntity<ResponseModel<TaskRsModel>> addTask(@Valid @RequestBody TaskRqModel taskRqModel, Authentication auth) {
        String email = ((UserDetails) auth.getPrincipal()).getUsername();

        ResponseEntity<ResponseModel<TaskRsModel>> response = ResponseEntity.ok(ResponseModel
                .of(taskService.addTask(taskRqModel, email), HttpStatus.CREATED));

        log.info(RESPONSE_MSG, ADD_TASK_URL, response);
        return response;
    }

    @ApiOperation("Get tasks of organization")
    @GetMapping(GET_TASKS_URL)
    public ResponseEntity<ResponseModel<List<TaskRsModel>>> getTasksOfOrganization(Authentication auth) {
        String email = ((UserDetails) auth.getPrincipal()).getUsername();
        ResponseEntity<ResponseModel<List<TaskRsModel>>> response = ResponseEntity.ok(ResponseModel
                .of(taskService.getTasksOfOrganization(email), HttpStatus.OK));

        log.info(RESPONSE_MSG, GET_TASKS_URL, response);
        return response;
    }

    @ApiOperation("Assign tasks to users")
    @PostMapping(ASSIGN_TASK_URL)
    public ResponseEntity<ResponseModel<TaskRsModel>> assignTask(@Valid @RequestBody AssignTaskRqModel assignTaskRqModel) {
        ResponseEntity<ResponseModel<TaskRsModel>> response = ResponseEntity.ok(ResponseModel
                .of(taskService.assignTask(assignTaskRqModel), HttpStatus.OK));

        log.info(RESPONSE_MSG, ASSIGN_TASK_URL, response);
        return response;
    }

    @ApiOperation("Change status of tasks")
    @PostMapping(CHANGE_STATUS_URL)
    public ResponseEntity<ResponseModel<TaskRsModel>> changeStatusOfTask(@Valid @RequestBody ChangeStatusRqModel changeStatusRqModel) {
        ResponseEntity<ResponseModel<TaskRsModel>> response = ResponseEntity.ok(ResponseModel
                .of(taskService.changeStatus(changeStatusRqModel), HttpStatus.OK));

        log.info(RESPONSE_MSG, CHANGE_STATUS_URL, response);
        return response;
    }
}
