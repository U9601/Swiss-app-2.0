package com.swiss.system.swisssystem2.services;

import com.swiss.system.swisssystem2.dtos.Player;
import com.swiss.system.swisssystem2.dtos.PlayerEntity;
import com.swiss.system.swisssystem2.dtos.mapper.PlayerMapper;
import com.swiss.system.swisssystem2.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository gameRepository;
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
        gameRepository.saveAll(playerMapper.playerToPlayerEntity(players));
        return "Players Updated";
    }

    @Override
    public String deletePlayers(List<Player> players) {
        try {
            gameRepository.deleteAll(playerMapper.playerToPlayerEntity(players));
        }catch (Exception e){
            log.error("Failed to delete players", e);
            throw new JpaSystemException(new RuntimeException("Failed to delete players"));
        }
        return "Players Deleted";
    }
}
