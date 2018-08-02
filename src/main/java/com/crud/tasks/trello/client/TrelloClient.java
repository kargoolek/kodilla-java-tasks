package com.crud.tasks.trello.client;

import com.crud.tasks.domain.TrelloBoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Component
public class TrelloClient {

    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;

    @Value("${trello.app.key}")
    private String trelloAppKey;

    @Value("${trello.app.token}")
    private String trelloToken;

    @Value("${trello.app.username}")
    private String trelloUser;

    @Autowired
    private RestTemplate restTemplate;

    public List<TrelloBoardDto> getTrelloBoards() {
        TrelloBoardDto[] t = restTemplate.getForObject(buildUrlAddress(), TrelloBoardDto[].class);

        //Simple test of use of Optional class:
        Random r = new Random();
        if(r.nextInt()%2 != 0) {
            t = null;
        }
        //end of simple test

        TrelloBoardDto[] boardsResponse = Optional.ofNullable(t)
                .orElse(new TrelloBoardDto[]{new TrelloBoardDto("An error occured while processing request...", "[ERROR]")});

        return Arrays.asList(boardsResponse);
    }

    private URI buildUrlAddress() {
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/" + trelloUser + "/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields","name,id")
                .build().encode().toUri();
    }

}
