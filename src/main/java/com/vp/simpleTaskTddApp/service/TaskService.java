package com.vp.simpleTaskTddApp.service;

import com.vp.simpleTaskTddApp.exception.TaskNotFoundException;
import com.vp.simpleTaskTddApp.model.Task;
import com.vp.simpleTaskTddApp.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTask() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    public Task updateTaskStatus(Long id, String status){
        Task task = taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
        task.setStatus(status);
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task taskData) {
        Task existingTask = getTaskById(id);
        existingTask.setName(taskData.getName());
        existingTask.setStatus(taskData.getStatus());
        return taskRepository.save(existingTask);
    }

    public void deleteTask(Long id) {
        Task taskToDelete = getTaskById(id);
        taskRepository.delete(taskToDelete);
    }
}
