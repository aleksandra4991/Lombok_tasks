package com.crud.tasks.controller;

import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import org.hibernate.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/task")
public class TaskController {
    @Autowired
    private DbService dbService;
    @Autowired
    private TaskMapper taskMapper;

    @RequestMapping(method = RequestMethod.GET,value = "getTasks")
    public List<TaskDto> getTasks(){
        return new ArrayList<>();
    }

    //@RequestMapping(method = RequestMethod.GET,value = "getTask")
    //public TaskDto getTask (Long taskId){
      //  return new TaskDto(1L,"test title","test_content");
    //}

    //@RequestMapping(method = RequestMethod.DELETE,value = "deleteTask")
    //public void deleteTask(Long taskId){
    //}

    //@RequestMapping(method = RequestMethod.PUT,value = "updateTask")
    //public TaskDto updateTask(TaskDto taskDto){
        //return new TaskDto(1L,"Edited test title","Test content");
    //}

    @RequestMapping(method = RequestMethod.POST,value = "createTask",consumes = APPLICATION_JSON_VALUE)
    public void createTask(TaskDto taskDto){
    }

    @RequestMapping(method = RequestMethod.GET,value = "getTask")
    public TaskDto getTask(@RequestParam Long taskId) throws TaskNotFoundException{
        return taskMapper.maptoTaskDto(dbService.getTask(taskId).orElseThrow(TaskNotFoundException::new));
    }

    @RequestMapping(method = RequestMethod.GET,value = "updateTask")
    public TaskDto updateTask(@RequestBody TaskDto taskDto){
        return taskMapper.maptoTaskDto(dbService.saveTask(taskMapper.mapToTask(taskDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "deleteTask")
    public TaskDto deleteTask(@RequestParam Long taskId) throws  TaskNotFoundException{
        return taskMapper.maptoTaskDto(dbService.getTask(taskId).orElseThrow(TaskNotFoundException::new));
    }
}
