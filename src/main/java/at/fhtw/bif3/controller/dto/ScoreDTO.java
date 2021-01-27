package at.fhtw.bif3.controller.dto;

public class ScoreDTO {
    private final int place;
    private final String username;
    private final int elo;

    public ScoreDTO(int place, String username, int elo) {
        this.place = place;
        this.username = username;
        this.elo = elo;
    }

    @Override
    public String toString() {
        return "ScoreDTO{" +
                "place=" + place +
                ", username='" + username + '\'' +
                ", elo=" + elo +
                '}';
    }
}

