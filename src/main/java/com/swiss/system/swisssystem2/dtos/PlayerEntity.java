package com.swiss.system.swisssystem2.dtos;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerEntity {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private long playerId;

    @Column(name = "name")
    private String name;

    @Column(name = "records")
    @ElementCollection(targetClass = Integer.class, fetch = FetchType.EAGER)
    private List<Integer> records;

    @Column(name = "list_of_played_players")
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    private List<String> listOfPlayedPlayers;

}
