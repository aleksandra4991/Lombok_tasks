package com.crud.tasks.trello.client;

import com.crud.tasks.domain.*;

import java.net.URI;

import com.crud.tasks.trello.config.TrelloConfig;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {

    @InjectMocks
    private TrelloClient trelloClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private TrelloConfig trelloConfig;


    @Before
    public void init(){
        // Given
        when(trelloConfig.getTrelloApiEndpoint()).thenReturn("http://test.com");
        when(trelloConfig.getTrelloAppKey()).thenReturn("test");
        when(trelloConfig.getTrelloAppToken()).thenReturn("test");
        when(trelloConfig.getTrelloAppUsername()).thenReturn("alexandra99784495");
    }

    @Test
    public void shouldFetchTrelloBoards() throws URISyntaxException {
        // Given
        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());

        URI uri = new URI("http://test.com/members/alexandra99784495/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri,TrelloBoardDto[].class)).thenReturn(trelloBoards);

        //When
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        //Then
        assertEquals(1,fetchedTrelloBoards.size());
        assertEquals("test_id",fetchedTrelloBoards.get(0).getId());
        assertEquals("test_board",fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(),fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    public void shouldCreateCard() throws URISyntaxException {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test task",
                "Test description",
                "top",
                "test_id");

        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20description&pos=top&idList=test_id");

        TrelloTrelloDto trelloDto = new TrelloTrelloDto(1, 1);
        TrelloAttachmentsDto attachmentsDto = new TrelloAttachmentsDto(trelloDto);
        BadgesDto bages = new BadgesDto(1, attachmentsDto);

        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                "Test task",
                "http://test.com",
                bages);

        when(restTemplate.postForObject(uri,null,CreatedTrelloCardDto.class)).thenReturn(createdTrelloCardDto);

        //When
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);

        //Then
        assertEquals("1",newCard.getId());
        assertEquals("Test task",newCard.getName());
        assertEquals("http://test.com",newCard.getShortUrl());
    }

    @Test
    public void shouldReturnEmptyList() throws URISyntaxException {

        // Given

        URI uri = new URI("http://test.com/members/alexandra99784495/boards?key=test&token=test&fields=name,id&lists=all");

        when(restTemplate.getForObject(uri, TrelloBoardDto[].class)).thenReturn(null);

        // When
        List<TrelloBoardDto> returnedTrelloBoard = trelloClient.getTrelloBoards();

        // Then

        assertEquals(0, returnedTrelloBoard.size());

    }
}