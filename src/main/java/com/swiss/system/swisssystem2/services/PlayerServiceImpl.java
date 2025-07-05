package com.swiss.system.swisssystem2.services;

import com.swiss.system.swisssystem2.dtos.Player;
import com.swiss.system.swisssystem2.dtos.PlayerEntity;
import com.swiss.system.swisssystem2.dtos.mapper.PlayerMapper;
import com.swiss.system.swisssystem2.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    @Override
    public List<Player> listAllPlayers() {
        return playerMapper.playerEntityToPlayer(playerRepository.findAll());
    }

    @Override
    public String addPlayers(List<Player> players) {
        try {
            playerRepository.saveAll(playerMapper.playerToPlayerEntity(players));
        }catch (Exception e){
            log.error("Failed to save players", e);
            throw new JpaSystemException(new RuntimeException("Failed to save players"));
        }
        return "Players Added";
    }

    @Override
    public String updatePlayers(List<Player> players, boolean shouldUpdateScore) {
        List<PlayerEntity> playerEntities = new ArrayList<>();
        if (shouldUpdateScore) {
            for (Player player : players) {
                Optional<PlayerEntity> optionalPlayerEntity = playerRepository.findById(player.getPlayerId());
                if (optionalPlayerEntity.isEmpty()) continue;
                optionalPlayerEntity.get().getRecords().add(player.getRecords().get(0));
            }
        }
        playerRepository.saveAll(playerEntities);
        return "Players Updated";
    }

    @Override
    public String deletePlayers(List<Long> players) {
        try {
            playerRepository.deleteAllById(players);
        }catch (Exception e){
            log.error("Failed to delete players", e);
            throw new JpaSystemException(new RuntimeException("Failed to delete players"));
        }
        return "Players Deleted";
    }
}
