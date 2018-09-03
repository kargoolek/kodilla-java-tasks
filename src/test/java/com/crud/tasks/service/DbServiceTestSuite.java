package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTestSuite {

    @InjectMocks
    private DbService service;

    @Mock
    TaskRepository repository;

    @Test
    public void testGetAllTasks() {
        //Given
        List<Task> tasks = Arrays.asList(new Task(1L, "test_title", "test_content"));
        when(repository.findAll()).thenReturn(tasks);
        //When
        List<Task> result = service.getAllTasks();
        //Then
        assertEquals(tasks, result);
    }
    
    @Test
    public void testFindById() {
        //Given
        Task testTask = new Task(1L, "test_title", "test_content");
        when(repository.findOne(1L)).thenReturn(testTask);
        when(repository.findOne(2L)).thenReturn(null);
        
        //When
        Task task = service.getTaskById(1L);

        //Then
        assertEquals(testTask, task);
    }

    @Test
    public void testFindByIdOptional() {
        //Given
        Task testTask = new Task(1L, "test_title", "test_content");
        when(repository.findById(1L)).thenReturn(Optional.of(testTask));
        when(repository.findById(2L)).thenReturn(Optional.empty());

        //When
        Optional<Task> task1 = service.getTask(1L);
        Optional<Task> task2 = service.getTask(2L);

        //Then
        assertTrue(task1.isPresent());
        assertEquals(testTask, task1.get());
        assertFalse(task2.isPresent());
    }

    @Test
    public void testSaveTask() {
        //Given
        Task task = new Task(1L, "test_title", "test_content");
        when(repository.save(task)).thenReturn(task);

        //When
        Task result = service.saveTask(task);

        //Then
        assertEquals(task, result);
    }

    @Test
    public void deleteTask() {
        //Given, When
        service.deleteTask(1L);

        //Then
        verify(repository, times(1)).delete(1L);
    }
}