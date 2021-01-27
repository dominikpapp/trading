package at.fhtw.bif3.controller.dto;

import java.util.Objects;

public class StatsDTO {
    private final int numberOfGamesPlayed;
    private final int elo;

    public StatsDTO(int numberOfGamesPlayed, int elo) {
        this.numberOfGamesPlayed = numberOfGamesPlayed;
        this.elo = elo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatsDTO statsDTO = (StatsDTO) o;
        return numberOfGamesPlayed == statsDTO.numberOfGamesPlayed && elo == statsDTO.elo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfGamesPlayed, elo);
    }

    @Override
    public String toString() {
        return "StatsDTO{" +
                "numberOfGamesPlayed=" + numberOfGamesPlayed +
                ", elo=" + elo +
                '}';
    }
}
