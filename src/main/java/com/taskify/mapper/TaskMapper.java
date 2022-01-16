package com.taskify.mapper;

import com.taskify.entity.Task;
import com.taskify.entity.User;
import com.taskify.model.task.TaskRqModel;
import com.taskify.model.task.TaskRsModel;
import com.taskify.utility.CustomFormatter;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.UUID;
import java.util.stream.Collectors;

@Mapper
public abstract class TaskMapper {

    public static TaskMapper TASK_MAPPER_INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(target = "deadline", ignore = true)
    public abstract Task buildTask(TaskRqModel taskRqModel);

    @AfterMapping
    void setExtraFields(@MappingTarget Task.TaskBuilder task, TaskRqModel taskRqModel) {
        task.taskId(UUID.randomUUID().toString());
        task.deadline(CustomFormatter.stringToLocalDateTime(taskRqModel.getDeadline()));
    }

    @Mapping(target = "userIds", ignore = true)
    @Mapping(target = "organizationId", source = "organization.organizationId")
    public abstract TaskRsModel buildTaskResponse(Task task);

    @AfterMapping
    void mapUserIds(@MappingTarget TaskRsModel.TaskRsModelBuilder taskRsModel, Task task) {
        taskRsModel.userIds(task.getUsers().stream()
                .map(User::getUserId)
                .collect(Collectors.toList()));
    }
}
