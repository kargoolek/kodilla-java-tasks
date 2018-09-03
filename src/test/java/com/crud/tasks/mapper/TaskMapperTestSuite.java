package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TaskMapperTestSuite {

    @Test
    public void testMapToTask() {
        //Given
        TaskMapper taskMapper = new TaskMapper();
        TaskDto testTaskDto = new TaskDto(1L, "test title", "test content" );
        List<Task> testLstTasks = new ArrayList<>();
        testLstTasks.add(new Task(2L, "new task 1", "new content 1"));
        testLstTasks.add(new Task(3L, "new task 2", "new content 2"));

        //When
        Task task = taskMapper.mapToTask(testTaskDto);
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        List<TaskDto> lstTasks = taskMapper.mapToTaskDtoList(testLstTasks);

        //Then
        Assert.assertEquals(testTaskDto.getId(), task.getId());
        Assert.assertEquals(testTaskDto.getTitle(), task.getTitle());
        Assert.assertEquals(testTaskDto.getContent(), task.getContent());
        Assert.assertEquals(task.getId(), taskDto.getId());
        Assert.assertEquals(2, lstTasks.size());
    }

}
