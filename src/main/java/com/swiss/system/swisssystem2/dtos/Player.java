package com.swiss.system.swisssystem2.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {
    private long playerId;
    private String name;
    private String currentScore;
    private List<Integer> records;
    private Set<String> listOfPlayedPlayers;


    public static String getCurrentScore(List<Integer> records) {
        int win = 0;
        int loss = 0;
        for(Integer integer : records){
            if(integer == 0){
                loss++;
                continue;
            }
            win++;
        }
        return win + "-" + loss;
    }
}
