package com.swiss.system.swisssystem2.controllers;

import com.swiss.system.swisssystem2.dtos.Player;
import com.swiss.system.swisssystem2.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/list-all-players")
    public ResponseEntity<List<Player>> listAllPlayers() {
        return ResponseEntity.ok(gameService.listAllPlayers());
    }

    @PostMapping("/add-players")
    public ResponseEntity<String> addPlayers(@RequestBody List<Player> players) {
        return ResponseEntity.ok(gameService.addPlayers(players));
    }

    @PutMapping("/update-players")
    public ResponseEntity<String> updatePlayers(@RequestBody List<Player> players) {
        return ResponseEntity.ok(gameService.updatePlayers(players));
    }

    @DeleteMapping("/delete-players")
    public ResponseEntity<String> deletePlayers(@RequestBody List<Player> players){
        return ResponseEntity.ok(gameService.)
    }
}
