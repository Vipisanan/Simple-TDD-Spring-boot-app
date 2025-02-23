package com.vp.simpleTaskTddApp.repository;

import com.vp.simpleTaskTddApp.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
