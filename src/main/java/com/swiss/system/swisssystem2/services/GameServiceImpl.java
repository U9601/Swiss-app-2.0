package com.swiss.system.swisssystem2.services;

import com.swiss.system.swisssystem2.dtos.Player;
import com.swiss.system.swisssystem2.dtos.PlayerEntity;
import com.swiss.system.swisssystem2.dtos.mapper.PlayerMapper;
import com.swiss.system.swisssystem2.repositories.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final PlayerMapper playerMapper;

    @Override
    public List<Player> listAllPlayers() {
        return playerMapper.playerEntityToPlayer(gameRepository.findAll());
    }

    @Override
    public String addPlayers(List<Player> players) {
        try {
            gameRepository.saveAll(playerMapper.playerToPlayerEntity(players));
        }catch (Exception e){
            log.error("Failed to save players", e);
            throw new JpaSystemException(new RuntimeException("Failed to save players"));
        }
        return "Players Added";
    }

    @Override
    public String updatePlayers(List<Player> players) {
        try {
            players.forEach(player -> {
                for(int i = 0; i < player.getListOfPlayedPlayers().size(); i++){
                    Optional<PlayerEntity> currentPlayer = gameRepository.findById(Long.parseLong(player.getListOfPlayedPlayers().get(i)));
                    if(currentPlayer.isEmpty()) continue;
                    player.getListOfPlayedPlayers().set(i, currentPlayer.get().getPlayerId() + ", " + currentPlayer.get().getName());
                }
            });
            gameRepository.saveAll(playerMapper.playerToPlayerEntity(players));
        }catch (Exception e){
            log.error("Failed to update players", e);
            throw new JpaSystemException(new RuntimeException("Failed to update players"));
        }
        return "Players Updated";
    }

    @Override
    public String deletePlayers(List<Player> players) {
        return null;
    }
}
