package com.crud.tasks.mapper;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloListDto;
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
        List<TrelloListDto> lstTrelloListDto = new ArrayList<>();
        lstTrelloListDto.add(new TrelloListDto("1", "test list", false));
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("1","test board1", lstTrelloListDto);
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("2","test board2", lstTrelloListDto);
        TrelloBoardDto trelloBoardDto3 = new TrelloBoardDto("3","test board3", lstTrelloListDto);
        List<TrelloBoardDto> lstTrelloBoardsDto = new ArrayList<>();
        lstTrelloBoardsDto.add(trelloBoardDto1);
        lstTrelloBoardsDto.add(trelloBoardDto2);
        lstTrelloBoardsDto.add(trelloBoardDto3);

        //When
        List<TrelloBoard> lstTrelloBoards = trelloMapper.mapToBoards(lstTrelloBoardsDto);

        //Then
        Assert.assertEquals(lstTrelloBoards.get(2).getId(), "3");
    }
}
