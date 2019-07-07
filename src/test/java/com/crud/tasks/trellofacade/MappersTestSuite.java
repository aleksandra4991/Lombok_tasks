package com.crud.tasks.trellofacade;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MappersTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToBoards() {
        //Given
        TrelloListDto2 toDo = new TrelloListDto2("1", "To Do", false);
        TrelloListDto2 done = new TrelloListDto2("2", "Done", true);
        TrelloListDto2 inProgress = new TrelloListDto2("3", "In Progress", false);

        List<TrelloListDto2> lists = new ArrayList<>();
        lists.add(toDo);
        lists.add(done);
        lists.add(inProgress);

        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("1", "Test board 1", lists);
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("2", "Test board 2", lists);
        List<TrelloBoardDto> trelloBoardsDto = new ArrayList<>();
        trelloBoardsDto.add(trelloBoardDto1);
        trelloBoardsDto.add(trelloBoardDto2);

        //When
        List<TrelloBoard> mappedBoards = trelloMapper.mapToBoards(trelloBoardsDto);

        //Then
        Assert.assertSame(mappedBoards.get(0).getClass(), TrelloBoard.class );
    }

    @Test
    public void testMapToBoardsDto() {
        //Given
        TrelloList toDo = new TrelloList("1", "To Do", false);
        TrelloList done = new TrelloList("2", "Done", true);
        TrelloList inProgress = new TrelloList("3", "In Progress", false);

        List<TrelloList> lists = new ArrayList<>();
        lists.add(toDo);
        lists.add(done);
        lists.add(inProgress);

        TrelloBoard trelloBoard1 = new TrelloBoard("1", "Test board 1", lists);
        TrelloBoard trelloBoard2 = new TrelloBoard("2", "Test board 2", lists);
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard1);
        trelloBoards.add(trelloBoard2);

        //When
        List<TrelloBoardDto> mappedBoardsDto = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        Assert.assertSame(mappedBoardsDto.get(0).getClass(), TrelloBoardDto.class );
    }

    @Test
    public void testMapToList() {
        //Given
        TrelloListDto2 toDo = new TrelloListDto2("1", "To Do", false);
        TrelloListDto2 done = new TrelloListDto2("2", "Done", true);
        TrelloListDto2 inProgress = new TrelloListDto2("3", "In Progress", false);

        List<TrelloListDto2> lists = new ArrayList<>();
        lists.add(toDo);
        lists.add(done);
        lists.add(inProgress);

        //When
        List<TrelloList> mappedLists = trelloMapper.mapToList(lists);

        //Then
        Assert.assertSame(mappedLists.get(0).getClass(), TrelloList.class );
    }

    @Test
    public void testMapToListDto() {
        //Given
        TrelloList toDo = new TrelloList("1", "To Do", false);
        TrelloList done = new TrelloList("2", "Done", true);
        TrelloList inProgress = new TrelloList("3", "In Progress", false);

        List<TrelloList> lists = new ArrayList<>();
        lists.add(toDo);
        lists.add(done);
        lists.add(inProgress);

        //When
        List<TrelloListDto2> mappedListsDto = trelloMapper.mapToListDto(lists);

        //Then
        Assert.assertSame(mappedListsDto.get(0).getClass(), TrelloListDto2.class );
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("card1", "card1", "1", "1");

        //When
        TrelloCardDto mappedCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        Assert.assertSame(mappedCardDto.getClass(), TrelloCardDto.class );
    }

    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("card1", "card1", "1", "1");

        //When
        TrelloCard mappedCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        Assert.assertSame(mappedCard.getClass(), TrelloCard.class );
    }
}
