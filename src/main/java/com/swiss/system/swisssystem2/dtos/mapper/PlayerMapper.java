package com.swiss.system.swisssystem2.dtos.mapper;

import com.swiss.system.swisssystem2.dtos.Player;
import com.swiss.system.swisssystem2.dtos.PlayerEntity;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Mapper(componentModel = "spring")
@Component
public interface PlayerMapper {
    List<PlayerEntity> playerToPlayerEntity(List<Player> player);
    List<Player> playerEntityToPlayer(List<PlayerEntity> playerEntity);

    default Player playerEntityToPlayer(PlayerEntity playerEntity){
        return Player.builder()
                .playerId(playerEntity.getPlayerId())
                .name(playerEntity.getName())
                .currentScore(Player.getCurrentScore(playerEntity.getRecords()))
                .records(playerEntity.getRecords())
                .listOfPlayedPlayers(playerEntity.getListOfPlayedPlayers())
                .build();
    }

    default PlayerEntity playerToPlayerEntity(Player player){
        return PlayerEntity.builder()
                .playerId(player.getPlayerId())
                .name(player.getName())
                .records(player.getRecords() == null ? new ArrayList<>() : player.getRecords())
                .listOfPlayedPlayers(player.getListOfPlayedPlayers() == null ? new HashSet<>() : player.getListOfPlayedPlayers())
                .build();
    }
}
