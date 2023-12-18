package com.swiss.system.swisssystem2.controllers;

import com.swiss.system.swisssystem2.dtos.Matchup;
import com.swiss.system.swisssystem2.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/game")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("start-game")
    public ResponseEntity<List<Matchup>> startGame(){
        return ResponseEntity.ok(gameService.startGame());
    }
}
