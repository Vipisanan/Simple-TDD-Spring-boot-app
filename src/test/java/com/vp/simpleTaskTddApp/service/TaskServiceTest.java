package com.vp.simpleTaskTddApp.service;

import com.vp.simpleTaskTddApp.exception.TaskNotFoundException;
import com.vp.simpleTaskTddApp.model.Task;
import com.vp.simpleTaskTddApp.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void testCreateTask() {
//        arrange
        Task task = new Task("Task 1", "Pending");
        when(taskRepository.save(any(Task.class))).thenReturn(task);
//        act
        Task createdTask = taskService.createTask(task);

//        assert
        assertNotNull(createdTask);
        assertEquals("Task 1", createdTask.getName());
        assertEquals("Pending", createdTask.getStatus());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testGetAllTask() {
//        arrange
        Task task1 = new Task("Task 1", "Pending");
        Task task2 = new Task("Task 2", "Done");

        List<Task> tasks = List.of(task1, task2);

        when(taskRepository.findAll()).thenReturn(tasks);
//        act
        List<Task> foundTasks = taskService.getAllTask();
//        assert

        assertFalse(foundTasks.isEmpty());
        assertEquals("Task 1", foundTasks.get(0).getName());
        assertEquals("Task 2", foundTasks.get(1).getName());
        verify(taskRepository, times(1)).findAll();


    }

    @Test
    void testGetTaskById() {
//        arrange
        Task task = new Task(1L, "Task 1", "Pending");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

//        act
        Task foundTask = taskService.getTaskById(1L);
//        assert
        assertNotNull(foundTask);
        assertEquals("Task 1", foundTask.getName());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateTaskStatus() {
//        arrange
        Task task = new Task(1L, "Task 1", "Pending");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task toBeUpdatedTask = new Task(1L, "Task 1", "To do");
        when(taskRepository.save(any(Task.class))).thenReturn(toBeUpdatedTask);


//        act
        Task updatedTask = taskService.updateTaskStatus(1L, "To do");
//        assert
        assertNotNull(updatedTask);
        assertEquals("To do", updatedTask.getStatus());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testUpdateTask() {
//      arrange
        Task task = new Task(1L, "Task 1", "Pending");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task updatedTask = new Task(1L, "Task 2", "To do");
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);
//      act
        Task toBeUpdatedTask = new Task("Task 2", "To do");
        Task taskFromService = taskService.updateTask(1L, toBeUpdatedTask);
//        assert
        assertNotNull(taskFromService);
        assertEquals("Task 2", taskFromService.getName());
        assertEquals("To do", taskFromService.getStatus());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));

    }

    @Test
    void testTaskGetById_TaskNotFound() {
//        arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

//        act & assert
        assertThrows(TaskNotFoundException.class, () -> taskService.getTaskById(1L));
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteTask() {
//        arrange
        Task task = new Task(1L, "Task 1", "To do");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

//        act
        taskService.deleteTask(1L);

//        assert
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).delete(any(Task.class));
    }

    @Test
    void testDeleteTask_TaskNotFound() {
//        arrange
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

//        act and assert
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(1L));
        verify(taskRepository, times(1)).findById(1L);
    }
}
