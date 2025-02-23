package com.vp.simpleTaskTddApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vp.simpleTaskTddApp.exception.TaskNotFoundException;
import com.vp.simpleTaskTddApp.model.Task;
import com.vp.simpleTaskTddApp.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @MockitoBean
    private TaskService taskService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateNewTask() throws Exception {
//        arrange
        Task task = new Task("Task 1", "To do");

//        act &  assert
        when(taskService.createTask(any(Task.class))).thenReturn(task);
        mockMvc.perform(post("/task").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(task))).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Task 1"));
    }

    @Test
    void testCreateNewTask_InvalidInput() throws Exception {
        Task task = new Task();
        task.setStatus("To do");
        //        act &  assert
        mockMvc.perform(post("/task").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(task))).andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllTask() throws Exception {
//        arrange
        List<Task> taskList = List.of(new Task(1L, "Task 1", "To do"), new Task(2L, "Task 2", "Progress"));
        when(taskService.getAllTask()).thenReturn(taskList);

//        act & assert
        mockMvc.perform(get("/task").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2))).andExpect(jsonPath("$[0].name").value("Task 1")).andExpect(jsonPath("$[1].status").value("Progress"));
        verify(taskService).getAllTask();
    }

    @Test
    void testGetTaskById() throws Exception {
//        arrange
        Task task = new Task(1L, "Task 1", "To do");
        when(taskService.getTaskById(1L)).thenReturn(task);

//        act & assert
        mockMvc.perform(get("/task/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Task 1")).andExpect(jsonPath("$.status").value("To do"));
        verify(taskService).getTaskById(1L);
    }

    @Test
    void testUpdateTask() throws Exception {
//        arrange
        Task toBeUpdatedTask = new Task("Task 1", "To do");
        when(taskService.updateTask(any(Long.class), any(Task.class))).thenReturn(toBeUpdatedTask);

//        act & assert

        mockMvc.perform(put("/task/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(toBeUpdatedTask))).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Task 1")).andExpect(jsonPath("$.status").value("To do"));
    }

    @Test
    void testDeleteTask() throws Exception {
//        arrange
        doNothing().when(taskService).deleteTask(1L);
//        act & assert
        mockMvc.perform(delete("/task/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(taskService).deleteTask(1L);
    }

    @Test
    void testGetTaskById_TaskNotFound() throws Exception {
//        arrange
        when(taskService.getTaskById(1L)).thenThrow(new TaskNotFoundException("Task not found"));

//        act & assert
        mockMvc.perform(get("/task/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
        verify(taskService).getTaskById(1L);
    }
}
