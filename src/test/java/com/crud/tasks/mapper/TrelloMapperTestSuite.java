package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TrelloMapperTestSuite {
    TrelloMapper trelloMapper;

    @Before
    public void initTests() {
        trelloMapper = new TrelloMapper();
    }

    @Test
    public void testMapToBards() {
        //Given
        List<TrelloListDto> testLstTrelloListDto = new ArrayList<>();
        testLstTrelloListDto.add(new TrelloListDto("1", "test list name", false));
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("1","test board1", testLstTrelloListDto);
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("2","test board2", testLstTrelloListDto);
        TrelloBoardDto trelloBoardDto3 = new TrelloBoardDto("3","test board3", testLstTrelloListDto);
        List<TrelloBoardDto> testLstTrelloBoardsDto = new ArrayList<>();
        testLstTrelloBoardsDto.add(trelloBoardDto1);
        testLstTrelloBoardsDto.add(trelloBoardDto2);
        testLstTrelloBoardsDto.add(trelloBoardDto3);
        TrelloCardDto testTrelloCardDto = new TrelloCardDto("test card name", "test description", "top", "1");

        //When
        List<TrelloBoard> lstTrelloBoards = trelloMapper.mapToBoards(testLstTrelloBoardsDto);
        List<TrelloBoardDto> lstTrelloBoardsDto = trelloMapper.mapToBoardsDto(lstTrelloBoards);
        List<TrelloList> lstTrelloList = trelloMapper.mapToList(testLstTrelloListDto);
        List<TrelloListDto> lstTrelloListDto = trelloMapper.mapToListDto(lstTrelloList);
        TrelloCard trelloCard = trelloMapper.mapToTrelloCard(testTrelloCardDto);
        TrelloCardDto trelloCardDto = trelloMapper.mapToTrelloCardDto(trelloCard);

        //Then
        Assert.assertEquals(lstTrelloBoards.get(2).getId(), "3");
        Assert.assertEquals(lstTrelloBoards.get(0).getName(), "test board1");
        Assert.assertEquals(lstTrelloBoardsDto.get(1).getId(), "2");
        Assert.assertEquals(lstTrelloBoardsDto.get(2).getName(), "test board3");
        Assert.assertEquals(lstTrelloList.get(0).getId(), "1");
        Assert.assertEquals(lstTrelloList.get(0).getName(), "test list name");
        Assert.assertEquals(lstTrelloListDto.get(0).getId(), "1");
        Assert.assertEquals(lstTrelloListDto.get(0).getName(), "test list name");
        Assert.assertEquals(trelloCard.getDescription(), "test description");
        Assert.assertEquals(trelloCard.getName(), "test card name");
        Assert.assertEquals(trelloCardDto.getDescription(), "test description");
        Assert.assertEquals(trelloCardDto.getName(), "test card name");
    }
}
