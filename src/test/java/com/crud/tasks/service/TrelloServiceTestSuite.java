package com.crud.tasks.service;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.domain.TrelloListDto2;
import com.crud.tasks.trello.client.TrelloClient;
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
public class TrelloServiceTestSuite {

    @Autowired
    private TrelloService trelloService;

    @Test
    public void fetchTrelloBoardsTest(){
        //Given
        TrelloBoard trelloBoardX = new TrelloBoard("1","name",new ArrayList<>());

        //When
        List<TrelloBoardDto> foundTrelloBoard = trelloService.fetchTrelloBoards();

        //Then
        Assert.assertNotEquals(null,foundTrelloBoard.size());
    }
}
