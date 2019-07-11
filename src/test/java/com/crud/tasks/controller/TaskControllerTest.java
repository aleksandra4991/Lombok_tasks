package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService dbService;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void getTasksTest() throws Exception{

        //Given
        Task task1  = new Task("task1", "things to do");
        Task task2  = new Task("task2", "things to do");
        Task task3  = new Task("task3", "things to do");
        List<Task> tasksList = Arrays.asList(task1, task2, task3);

        TaskDto taskDto1  = new TaskDto(1L, "task1", "things to do");
        TaskDto taskDto2  = new TaskDto(2L, "task2", "things to do");
        TaskDto taskDto3  = new TaskDto(3L, "task3", "things to do");
        List<TaskDto> tasksDtoList = Arrays.asList(taskDto1, taskDto2, taskDto3);

        when(dbService.getAllTasks()).thenReturn(tasksList);
        when(taskMapper.mapToTaskDtoList(Matchers.anyListOf(Task.class))).thenReturn(tasksDtoList);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(tasksDtoList);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)));
    }

    @Test
    public void getTaskTest() throws Exception{

        //Given
        Task task1  = new Task("task1", "things to do");
        TaskDto taskDto1  = new TaskDto(1L, "task1", "things to do");

        when(dbService.getTaskById(1L)).thenReturn(task1);
        when(taskMapper.mapToTaskDto(Matchers.any(Task.class))).thenReturn(taskDto1);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto1);

        //When & Then
        mockMvc.perform(get("/v1/task/getTask").contentType(MediaType.APPLICATION_JSON)
                .param("taskId", "1")
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("task1")))
                .andExpect(jsonPath("$.content", is("things to do")));

    }

    @Test
    public void deleteTaskTest() throws Exception{

        //Given
        Task task1  = new Task("task1", "things to do");
        Task savedTask = new Task(1L, "task1", "things to do");

        when(dbService.saveTask(Matchers.any(Task.class))).thenReturn(savedTask);
        Long id = dbService.saveTask(task1).getId();

        //When & Then

        mockMvc.perform(delete("/v1/task/deleteTask").contentType(MediaType.APPLICATION_JSON)
                .param("taskId", id.toString()))
                .andExpect(status().is(200));
    }

    @Test
    public void postTaskTest() throws Exception{

        //Given
        Task task1  = new Task("task1", "things to do");
        Task savedTask1  = new Task(1L, "task1", "things to do");
        TaskDto taskDto1  = new TaskDto(1L, "task1", "things to do");

        when(dbService.saveTask(Matchers.any(Task.class))).thenReturn(savedTask1);
        when(taskMapper.mapToTaskDto(Matchers.any(Task.class))).thenReturn(taskDto1);
        when(taskMapper.mapToTask(Matchers.any(TaskDto.class))).thenReturn(task1);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto1);

        //When & Then
        mockMvc.perform(put("/v1/task/updateTask").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("task1")))
                .andExpect(jsonPath("$.content", is("things to do")))
        ;
    }

    @Test
    public void createTaskTest() throws Exception{

        //Given
        Task task1  = new Task("task1", "things to do");
        Task savedTask1  = new Task(1L, "task1", "things to do");
        TaskDto taskDto1  = new TaskDto(1L, "task1", "things to do");


        when(dbService.saveTask(Matchers.any(Task.class))).thenReturn(savedTask1);
        when(taskMapper.mapToTask(Matchers.any(TaskDto.class))).thenReturn(task1);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto1);

        //When & Then
        mockMvc.perform(post("/v1/task/createTask").contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200));
    }

}
