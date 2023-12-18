package com.swiss.system.swisssystem2.services;

import com.swiss.system.swisssystem2.dtos.Matchup;
import com.swiss.system.swisssystem2.dtos.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {

    private final PlayerService playerService;
    private final Random rand = new Random();

    @Override
    public List<Matchup> startGame() {
        List<Player> players = playerService.listAllPlayers();
        if (players.size() < 4) return Collections.emptyList();

        removePlayersWith3Wins(players);

        Map<String, List<Player>> splitUpScores = splitUpScores(players);
        List<Matchup> matchups = new ArrayList<>();
        for(List<Player> playerList : splitUpScores.values()) {
            for(int i = 0; i < players.size(); i++){
                if(playerList.isEmpty()) break;

                if(playerList.size() == 1){
                    matchups.add(Matchup.builder().player1(playerList.get(0)).hasByeRound(true).build());
                    break;
                }

                int randomInt1 = 0;
                int randomInt2 = 0;

                while(randomInt1 == randomInt2){
                    randomInt1 = rand.nextInt(playerList.size());
                    randomInt2 = rand.nextInt(playerList.size());
                }

                Player player1 = playerList.get(randomInt1);
                Player player2 = playerList.get(randomInt2);

                //TODO fix this thing
                if(player1.getListOfPlayedPlayers().stream().anyMatch(element -> player2.getPlayerId() == Long.parseLong(element.substring(0, 1)))){
                    log.info("They have already played");
                    i--;
                    continue;
                }

                matchups.add(new Matchup(player1, player2, false));
                playerList.removeIf(player -> player.getPlayerId() == player1.getPlayerId() || player.getPlayerId() == player2.getPlayerId());
            }

        }
        return matchups;
    }

    private void removePlayersWith3Wins(List<Player> players){
        players.removeIf(player -> player.getCurrentScore().charAt(0) == '3');
    }

    private Map<String, List<Player>> splitUpScores(List<Player> players){
        Map<String, List<Player>> splitUpScores = new HashMap<>();
        players.forEach(player -> {
            if(splitUpScores.containsKey(player.getCurrentScore())){
                splitUpScores.get(player.getCurrentScore()).add(player);
            }else{
                List<Player> playerList = new ArrayList<>();
                playerList.add(player);
                splitUpScores.put(player.getCurrentScore(), playerList);
            }
        });
        return splitUpScores;
    }
}
