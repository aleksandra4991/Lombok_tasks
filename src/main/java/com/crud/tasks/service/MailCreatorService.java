package com.crud.tasks.service;

import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    @Autowired
    private TaskRepository taskRepository;

    public String buildTrelloCardEmail(String message){
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");


        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("task_url", "http://localhost:8888/crud");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("company_name", adminConfig.getCompanyName());
        context.setVariable("company_email", adminConfig.getCompanyEmail());
        context.setVariable("company_phone", adminConfig.getCompanyPhone());
        context.setVariable("preview_message", "Your card have been sent to Trello");
        context.setVariable("goodbye_message", "See you soon!");
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildNumberOfTasksEmail(String message){
        List<String> tasksNames = new ArrayList<>();
        taskRepository.findAll().stream()
                .forEach(task -> tasksNames.add(task.getTitle()));

        String button = "Check tasks";
        boolean showList = true;
        if(taskRepository.count()==0L){
            button = "Add tasks";
            showList = false;
        }


        Context context2 = new Context();
        context2.setVariable("message", message);
        context2.setVariable("task_url", "http://localhost:8888/crud");
        context2.setVariable("button", button );
        context2.setVariable("admin_name", adminConfig.getAdminName());
        context2.setVariable("company_name", adminConfig.getCompanyName());
        context2.setVariable("company_email", adminConfig.getCompanyEmail());
        context2.setVariable("company_phone", adminConfig.getCompanyPhone());
        context2.setVariable("preview_message_todo", "What to do today?");
        context2.setVariable("goodbye_message_tomorrow", "See you tomorrow!");
        context2.setVariable("show_list", showList);
        context2.setVariable("admin_config", adminConfig);
        context2.setVariable("tasks_titles_list", tasksNames);
        return templateEngine.process("mail/number-of-tasks-mail", context2);
    }
}
