package com.swiss.system.swisssystem2.services;

import com.swiss.system.swisssystem2.dtos.Player;

import java.util.List;

public interface PlayerService {

    List<Player> listAllPlayers();

    String addPlayers(List<Player> players);

    String updatePlayers(List<Player> players, boolean shouldUpdateScore);

    String deletePlayers(List<Long> players);
}
