package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.scheduler.EmailScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Value("${info.company.name}")
    String companyName;

    @Value("${info.company.goal}")
    String companyGoal;

    @Value("${info.company.email}")
    String companyEmail;

    @Value("${info.company.phone}")
    String companyPhone;

    @Value("${trello.app.username}")
    String trelloUserName;

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    TemplateEngine templateEngine;

    @Autowired
    TrelloService trelloService;

    public String buildTrelloCardEmail(String message) {

        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        List<String> boardsNames = new ArrayList<>();
        List<TrelloBoardDto> boards = trelloService.fetchTrelloBoards();
        for (TrelloBoardDto b : boards) {
            boardsNames.add(b.getName());
        }

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://kargoolek.github.io/");
        context.setVariable("button", "Visit website");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("company_name", companyName);
        context.setVariable("company_goal", companyGoal);
        context.setVariable("company_email", companyEmail);
        context.setVariable("company_phone", companyPhone);
        context.setVariable("goodbye_message", "See you again! :)");
        context.setVariable("preview_message", "Message from Trello Mail Service.");
        context.setVariable("show_button", false);
        context.setVariable("isFriend", false);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);

        //Trello once per day mail
        context.setVariable("once_per_day_trello_boards", boardsNames);
        context.setVariable("once_per_day_trello_link","https://trello.com/"+trelloUserName+"/boards");
        context.setVariable("once_per_day_button_show", boardsNames.size() > 0);
        context.setVariable("once_per_day_button_text", "SHOW ALL BOARDS");

        if(message.contains(EmailScheduler.DAILY_TASKS_MESSAGE)) return templateEngine.process("mail/daily-tasks-count-mail", context);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

}
