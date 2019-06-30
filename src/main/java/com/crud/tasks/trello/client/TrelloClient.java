package com.crud.tasks.trello.client;

import com.crud.tasks.com.crud.tasks.trello.config.TrelloConfig;
import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class TrelloClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);

    @Autowired
    private TrelloConfig trelloConfig;

    @Autowired
    private RestTemplate restTemplate;

    private URI getTrellourl(){
        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getTrelloApiEndpoint() + "/members/alexandra99784495/boards")
                .queryParam("key", trelloConfig.getTrelloAppKey())
                .queryParam("token", trelloConfig.getTrelloAppToken())
                .queryParam("fields", "name,id")
                .queryParam("lists", "all").build().encode().toUri();

                 return url;
    }

    public List<TrelloBoardDto> getTrelloBoards(){

    try {
        TrelloBoardDto[]boardsResponse = restTemplate.getForObject(getTrellourl(), TrelloBoardDto[].class);

        return Arrays.asList(Optional.ofNullable(boardsResponse).orElse(new TrelloBoardDto[0]));
        }
    catch (RestClientException e) {
        LOGGER.error(e.getMessage());
        return new ArrayList<>();
        }

    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto){

        URI url2 = UriComponentsBuilder.fromHttpUrl(trelloConfig+"/cards")
                .queryParam("key",trelloConfig)
                .queryParam("token", trelloConfig)
                .queryParam("name",trelloCardDto.getName())
                .queryParam("desc",trelloCardDto.getDescription())
                .queryParam("pos",trelloCardDto.getPos())
                .queryParam("idList",trelloCardDto.getListId()).build().encode().toUri();

        return restTemplate.postForObject(url2,null,CreatedTrelloCard.class);
    }


}
