package com.swiss.system.swisssystem2.controllers;

import com.swiss.system.swisssystem2.dtos.Player;
import com.swiss.system.swisssystem2.services.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping("/list-all-players")
    public ResponseEntity<List<Player>> listAllPlayers() {
        return ResponseEntity.ok(playerService.listAllPlayers());
    }

    @PostMapping("/add-players")
    public ResponseEntity<String> addPlayers(@RequestBody List<Player> players) {
        return ResponseEntity.ok(playerService.addPlayers(players));
    }

    @PutMapping("/update-players")
    public ResponseEntity<String> updatePlayers(@RequestBody List<Player> players) {
        return ResponseEntity.ok(playerService.updatePlayers(players, true));
    }

    @DeleteMapping("/delete-players")
    public ResponseEntity<String> deletePlayers(@RequestBody List<Long> players){
        return ResponseEntity.ok(playerService.deletePlayers(players));
    }
}
