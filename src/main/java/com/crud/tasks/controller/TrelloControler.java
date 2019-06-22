package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/trello")
public class TrelloControler {
    @Autowired
    private TrelloClient trelloClient;

    @RequestMapping(method = RequestMethod.GET, value = "getTrelloBoards")
    public void getTrelloBoards() {

               Optional<List<TrelloBoardDto>> trelloBoards = Optional.of(trelloClient.getTrelloBoards());

                trelloBoards.get()
                        .stream()
                        .filter(trelloBoardDto -> trelloBoardDto.getId().isEmpty() == false)
                        .filter(trelloBoardDto-> trelloBoardDto.getName().contains("Kodilla"))
                        .forEach(trelloBoardDto -> System.out.println(trelloBoardDto.getId() + " " + trelloBoardDto.getName()));



        }


        //System.out.println("This board contains lists: ");

                    //trelloBoardDto.getLists().forEach(trelloList ->
                    //System.out.println(trelloList.getName() + " - " + trelloList.getId() + " - " + trelloList.isClosed()));





    @RequestMapping(method = RequestMethod.POST,value = "createTrelloCard")
    public CreatedTrelloCard createTrelloCard(@RequestBody TrelloCardDto trelloCardDto){
        return trelloClient.createNewCard(trelloCardDto);
    }

}



