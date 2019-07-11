package com.crud.tasks.trello.validator;


import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloValidatorTestSuite {

    @Test
    public void validateTrelloBoardsTest() {

        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1", "trello_list", false));

        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("1", "test", trelloLists));
        trelloBoards.add(new TrelloBoard("2", "normal board", trelloLists));
        trelloBoards.add(new TrelloBoard("3", "sumer normal board", trelloLists));

        TrelloValidator trelloValidator = new TrelloValidator();


        //When
        List<TrelloBoard> validatedTrelloBoards = trelloValidator.validateTrelloBoards(trelloBoards);

        //Then
        assertEquals(2, validatedTrelloBoards.size());
    }

    @Test
    public void validateTrelloCardsTest() {

        //Given
        TrelloCard trelloCard = new TrelloCard("cardTest", "This is test card", "3", "3");
        TrelloValidator trelloValidator = new TrelloValidator();

        //When
        trelloValidator.validateCard(trelloCard);
        boolean isCartTest = trelloValidator.isCartTest();

        //Then
        assertEquals(false, isCartTest);
    }

}
