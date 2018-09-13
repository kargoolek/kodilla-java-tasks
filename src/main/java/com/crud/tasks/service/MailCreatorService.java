package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

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

    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
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
        return templateEngine.process("mail/created-trello-card-mail", context);
    }



}
