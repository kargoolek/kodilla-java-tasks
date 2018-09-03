package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTestSuite {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private AdminConfig adminConfig;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService emailService;

    @Test
    public void testFetchTrelloBoards() {
        // Given
        TrelloListDto trelloListDto = new TrelloListDto("1", "test_list", false);
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(trelloListDto);
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("1", "test_board", trelloLists));

        // When
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoards);
        List<TrelloBoardDto> result = trelloService.fetchTrelloBoards();

        // Then
        assertEquals(trelloBoards, result);
        assertEquals(trelloListDto.getId(), result.get(0).getLists().get(0).getId());
        assertEquals(trelloListDto.isClosed(), result.get(0).getLists().get(0).isClosed());
        assertEquals(trelloListDto.getName(), result.get(0).getLists().get(0).getName());
    }

    @Test
    public void testCreateCardAndMailSend() {
        // Given
        TrelloCardDto card = new TrelloCardDto("test_name", "test_description", "test_pos", "test_list_id");
        when(trelloClient.createNewCard(card)).thenReturn(new CreatedTrelloCardDto("1", "test_name", "short_url", new Badges()));
        when(adminConfig.getAdminMail()).thenReturn("admin@test.com");

        // When
        CreatedTrelloCardDto result = trelloService.createTrelloCard(card);

        // Then
        assertEquals("1", result.getId());
        assertEquals("test_name", result.getName());
        assertEquals("short_url", result.getShortUrl());
        verify(emailService, times(1)).send(any());
    }
}