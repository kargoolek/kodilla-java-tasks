package com.crud.tasks.trello.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
/*tutaj mozna takze wykorzystac:
@TestPropertySource(properties = {
        "trello.api.endpoint.prod=test_endpoint",
        "trello.app.key=test_key",
        "trello.app.token=test_token",
        "trello.app.username=test_username"
}) */
public class TrelloConfigTestSuite {
    @Autowired
    TrelloConfig trelloConfig;

    @Test
    public void testTrelloConfig() {
        //Given, When, Then
        Assert.assertEquals("karol933", trelloConfig.getTrelloUser());
        Assert.assertEquals("https://api.trello.com/1", trelloConfig.getTrelloApiEndpoint());
        Assert.assertEquals("0e4270295066920f60bc9b295f3eea1a", trelloConfig.getTrelloAppKey());
        Assert.assertEquals("55f0c666e296b5fda2c64b884718ed63cf79202eee24f343a19c6a967f83fc37", trelloConfig.getTrelloToken());
    }
}
