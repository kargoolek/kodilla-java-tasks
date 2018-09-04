package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    private static Task testTask;
    private static List<Task> testListTasks;
    private static List<TaskDto> testListTasksDto;
    private static TaskDto testTaskDto;

    @BeforeClass
    public static void init() {
        testTask = new Task(99L, "test_title", "test_content");
        testListTasks = new ArrayList<>();
        testListTasks.add(testTask);
        testListTasksDto = new ArrayList<>();
        testTaskDto = new TaskDto(testTask.getId(), testTask.getTitle(), testTask.getContent());
        testListTasksDto.add(testTaskDto);
    }

    @Test
    public void shouldFetchTasksList() throws Exception {
        //Given
        when(service.getAllTasks()).thenReturn(testListTasks);
        when(taskMapper.mapToTaskDtoList(testListTasks)).thenReturn(testListTasksDto);

        //When & Then
        mockMvc.perform(get("/v1/task/getTasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))// or isOK()
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(99)))
                .andExpect(jsonPath("$[0].title", is(testTask.getTitle())))
                .andExpect(jsonPath("$[0].content", is(testTask.getContent()))
                );

    }

    @Test
    public void shouldFetchTaskById() throws Exception {
        //Given
        when(service.getTask(any())).thenReturn(Optional.of(testTask));
        when(taskMapper.mapToTaskDto(testTask)).thenReturn(testTaskDto);

        //When & Then
        mockMvc.perform(get("/v1/task/getTask")
                .contentType(MediaType.APPLICATION_JSON)
                .param("taskId", "99")
                .characterEncoding("UTF-8"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(99)))
                .andExpect(jsonPath("$.title", is(testTask.getTitle())))
                .andExpect(jsonPath("$.content", is(testTask.getContent()))
                );
    }

    @Test
    public void shouldCreateAndDeleteTask() throws Exception {
        //Given
        when(taskMapper.mapToTaskDto(testTask)).thenReturn(testTaskDto);
        when(taskMapper.mapToTask(testTaskDto)).thenReturn(testTask);
        when(service.saveTask(testTask)).thenReturn(testTask);
        when(service.getTask(99L)).thenReturn(Optional.of(testTask));

        Gson gson = new Gson();
        String jsonContent = gson.toJson(testTaskDto);

        //When & Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200)
                );

        mockMvc.perform(get("/v1/task/getTask")
                .contentType(MediaType.APPLICATION_JSON)
                .param("taskId", "99")
                .characterEncoding("UTF-8"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(99)))
                .andExpect(jsonPath("$.title", is(testTask.getTitle())))
                .andExpect(jsonPath("$.content", is(testTask.getContent()))
                );

        mockMvc.perform(delete("/v1/task/deleteTask")
                .contentType(MediaType.APPLICATION_JSON)
                .param("taskId", "99"))
                .andExpect(status().is(200));

        assertEquals(0, service.getAllTasks().size());
    }

    @Test
    public void shouldUpdateTask() throws Exception {
        //Given
        when(taskMapper.mapToTaskDto(testTask)).thenReturn(testTaskDto);
        when(taskMapper.mapToTask(testTaskDto)).thenReturn(testTask);
        when(service.saveTask(testTask)).thenReturn(testTask);
        when(service.getTask(99L)).thenReturn(Optional.of(testTask));

        Gson gson = new Gson();
        String jsonContent = gson.toJson(testTaskDto);

        //When & Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200)
                );

        mockMvc.perform(get("/v1/task/getTask")
                .contentType(MediaType.APPLICATION_JSON)
                .param("taskId", "99")
                .characterEncoding("UTF-8"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(99)))
                .andExpect(jsonPath("$.title", is(testTask.getTitle())))
                .andExpect(jsonPath("$.content", is(testTask.getContent()))
                );

        Task changedTask = new Task(99L, "test_new_title", "test_content");
        TaskDto changedTaskDto = new TaskDto(changedTask.getId(), changedTask.getTitle(), changedTask.getContent());
        when(service.saveTask(any())).thenReturn(changedTask);
        when(taskMapper.mapToTask(any())).thenReturn(changedTask);
        when(taskMapper.mapToTaskDto(any())).thenReturn(changedTaskDto);
        String jsonNewContent = gson.toJson(changedTaskDto);

        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonNewContent)
                .characterEncoding("UTF-8"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(99)))
                .andExpect(jsonPath("$.title", is(changedTaskDto.getTitle())))
                .andExpect(jsonPath("$.content", is(changedTaskDto.getContent()))
                );

        mockMvc.perform(get("/v1/task/getTask")
                .contentType(MediaType.APPLICATION_JSON)
                .param("taskId", "99")
                .characterEncoding("UTF-8"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(99)))
                .andExpect(jsonPath("$.title", is(changedTaskDto.getTitle())))
                .andExpect(jsonPath("$.content", is(changedTaskDto.getContent()))
                );
    }


}
