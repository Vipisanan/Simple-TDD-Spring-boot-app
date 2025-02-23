package com.vp.simpleTaskTddApp.repository;

import com.vp.simpleTaskTddApp.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    public TaskRepository taskRepository;

    @Test
    public void testSaveTask() {
//        arrange
        Task task = new Task();
        task.setName("Task 1");
        task.setStatus("Pending");
//        act
        Task savedTask = taskRepository.save(task);

//        assert
        assertNotNull(savedTask.getId());
        assertEquals("Task 1", savedTask.getName());
        assertEquals("Pending" ,savedTask.getStatus());

    }
}
