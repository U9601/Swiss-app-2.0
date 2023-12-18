package com.swiss.system.swisssystem2.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Matchup {
    private Player player1;
    private Player player2;
    private boolean hasByeRound;
}
