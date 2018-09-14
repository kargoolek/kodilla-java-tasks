package com.crud.tasks.scheduler;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailScheduler {
    private static final String SUBJECT = "Tasks: Once a day email";
    public static final String DAILY_TASKS_MESSAGE = "Currently in database you got: ";

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private AdminConfig adminConfig;

    @Scheduled(cron = "0 0 20 * * *", zone = "GMT+2")
    @Scheduled(initialDelay = 10000)
    //@Scheduled(fixedDelay = 10000)
    public void sendInformationEmail() {
        long size = taskRepository.count();
        String tasks = (size == 1) ? " task." : " tasks.";
        simpleEmailService.send(new Mail(
            adminConfig.getAdminMail(), "", SUBJECT, DAILY_TASKS_MESSAGE + size + tasks
        ));
    }

}
