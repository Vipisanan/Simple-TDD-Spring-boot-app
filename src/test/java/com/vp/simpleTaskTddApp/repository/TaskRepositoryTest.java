package com.vp.simpleTaskTddApp.repository;

import com.vp.simpleTaskTddApp.model.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

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
        assertEquals("Pending", savedTask.getStatus());

    }

    @Test
    public void testFindTaskById() {
//        arrange
        Task task = new Task();
        task.setName("Task 1");
        task.setStatus("Pending");
//        act
        Task savedTask = taskRepository.save(task);
        Optional<Task> foundTask = taskRepository.findById(savedTask.getId());

//        assert
        assertTrue(foundTask.isPresent());
        assertEquals("Task 1", foundTask.get().getName());
        assertEquals("Pending", foundTask.get().getStatus());

    }

    @Test
    public void testUpdateTask() {
//        arrange
        Task task = new Task("Task 1", "Pending");
        Task savedTask = taskRepository.save(task);

//        act
        savedTask.setName("Task 2");
        savedTask.setStatus("Done");
        taskRepository.save(savedTask);

        Optional<Task> foundTask = taskRepository.findById(savedTask.getId());

//        assert
        assertTrue(foundTask.isPresent());
        assertEquals("Task 2", foundTask.get().getName());
        assertEquals("Done", foundTask.get().getStatus());

    }

    @Test
    public void testDeleteTask() {
//        arrange
        Task task = new Task("Task 1", "Pending");
        Task savedTask = taskRepository.save(task);

//        act
        taskRepository.delete(savedTask);
        Optional<Task> foundTask = taskRepository.findById(savedTask.getId());

//        assert
        assertFalse(foundTask.isPresent());
    }


}
