package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class DbServiceTest {

    @Autowired
    private DbService dbService;

    @Test
    public void saveTaskTest() {

        //Given
        Task task  = new Task("task1","cooking");

        //When
        Task savedTask = dbService.saveTask(task);

        //Then
        assertNotEquals(null, savedTask.getId());
    }

    @Test
    public void getAllTasksTest() {

        //Given
        Task task1  = new Task("task1","swimming");
        Task task2  = new Task("task2","going out with friends");
        Task task3  = new Task("task3","dancing");

        int tasksCounterBeforeSave = dbService.getAllTasks().size();
        dbService.saveTask(task1);
        dbService.saveTask(task2);
        dbService.saveTask(task3);

        //When
        int tasksCounterAfterSave = dbService.getAllTasks().size();

        //Then
        assertEquals(tasksCounterBeforeSave + 3, tasksCounterAfterSave);
    }

    @Test
    public void deleteTaskTest() {

        //Given
        Task task1  = new Task("task1","swimming");
        Task task2  = new Task("task2","going out with friends");
        Task task3  = new Task("task3","dancing");
        
        dbService.saveTask(task1);
        dbService.saveTask(task2);
        dbService.saveTask(task3);

        int tasksCounterBeforeDeletion = dbService.getAllTasks().size();

        //When
        dbService.deleteTask(task1.getId());
        int tasksCounterAfterDeletion = dbService.getAllTasks().size();


        //Then
        assertEquals(tasksCounterBeforeDeletion - 1, tasksCounterAfterDeletion);
    }

    @Test
    public void getTaskByIdTest() {

        //Given
        Task task1  = new Task("task1", "things to do");
        Task savedTask = dbService.saveTask(task1);
        Long id = savedTask.getId();

        //When
        Task foundTask = dbService.getTaskById(id);

        //Then
        assertEquals(id, foundTask.getId());
    }
}
