package com.swiss.system.swisssystem2.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {
    private long playerId;
    private String name;
    private String currentScore;
    private List<Integer> records;
    private List<String> listOfPlayedPlayers;


    public static String getCurrentScore(List<Integer> records) {
        int win = 0;
        int loss = 0;
        for(Integer record : records){
            if(record == 0){
                loss++;
                continue;
            }
            win++;
        }
        return win + "-" + loss;
    }
}
