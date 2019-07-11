package com.crud.tasks.trello.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertSame;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TaskMapperTestSuite {

    @Autowired
    private TaskMapper taskMapper;

    @Test
    public void testMapToTask() {

        //Given
        TaskDto taskDto = new TaskDto(2L, "task", "Task to do");

        //When
        Task mappedTask = taskMapper.mapToTask(taskDto);

        //Then
        assertSame(mappedTask.getClass(), Task.class );
    }

    @Test
    public void testMapToTaskDto() {

        //Given
        Task task = new Task(2L, "task", "Task to do");

        //When
        TaskDto mappedTask = taskMapper.mapToTaskDto(task);

        //Then
        assertSame(mappedTask.getClass(), TaskDto.class );
    }

    @Test
    public void testMapToTaskDtoList() {

        //Given
        Task task2 = new Task(2L, "task2", "Task to do");
        Task task3 = new Task(3L, "task3", "Task to do");
        Task task4 = new Task(4L, "task4", "Task to do");

        List<Task> taskList = new ArrayList<>();
        taskList.add(task2);
        taskList.add(task3);
        taskList.add(task4);

        //When
        List<TaskDto> mappedTasks = taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertSame(mappedTasks.get(0).getClass(), TaskDto.class );
    }


}
