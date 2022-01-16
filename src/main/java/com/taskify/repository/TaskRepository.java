package com.taskify.repository;

import com.taskify.entity.Organization;
import com.taskify.entity.Task;
import com.taskify.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByOrganization(Organization organization);

    Optional<Task> findByTaskId(String taskId);
}
