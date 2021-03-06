package io.pivotal.racquetandroid.util;

import java.util.ArrayList;
import java.util.List;

import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.model.Clubs;
import io.pivotal.racquetandroid.model.Leaderboard;
import io.pivotal.racquetandroid.model.Logo;
import io.pivotal.racquetandroid.model.Ranking;
import io.pivotal.racquetandroid.model.StandardLogo;
import io.pivotal.racquetandroid.model.response.Match;
import io.pivotal.racquetandroid.model.response.Player;

public class ModelBuilder {

    public static Club getClub(int id, String name) {
        Club club = new Club();
        club.setName(name);
        Logo logo = new Logo();
        logo.setUrl("http://logo/url" + id);
        StandardLogo standardLogo = new StandardLogo();
        standardLogo.setUrl("http://logo/standard/url" + id);
        logo.setStandard(standardLogo);
        club.setLogo(logo);
        club.setId(id);
        club.setSlug(name);
        return club;
    }

    public static Clubs getClubs(int size, String name) {
        Clubs clubs = new Clubs();
        List<Club> clubList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            clubList.add(getClub(i, name + i));
        }
        clubs.setClubs(clubList);
        return clubs;
    }

    public static List<Match> getMatches(int size, String playerOne, String playerTwo) {
        List<Match> matches = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Match match;
            if (i % 2 == 0) {
                match = ModelBuilder.getMatch(playerOne, playerTwo);
            } else {
                match = ModelBuilder.getMatch(playerTwo, playerOne);
            }
            matches.add(match);
        }
        return matches;
    }

    public static Match getMatch(String playerOne, String playerTwo) {
        Match match = new Match();
        match.setWinner(getPlayer(playerOne, 1));
        match.setLoser(getPlayer(playerTwo, 2));
        return match;
    }

    public static Player getPlayer(String name, int rank) {
        Player player = new Player();
        player.setName(name);
        player.setProfileImageUrl("http://profile/url/" + name);
        player.setTwitterHandle("twitter_" + name);
        player.setWins(rank);
        player.setLosses(rank * 2);
        player.setWinningPercentage(0.50012345);
        player.setPoints(rank * 100);
        return player;
    }

    public static List<Ranking> getRankings(int size) {
        List<Ranking> rankings = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            rankings.add(getRanking("name"+i, i));
        }
        return rankings;
    }

    public static Ranking getRanking(String name, int rank) {
        Ranking ranking = new Ranking();
        ranking.setMember(getPlayer(name, rank));
        ranking.setRank(rank);
        return ranking;
    }

    public static Leaderboard getLeaderboard(List<Ranking> majors, List<Ranking> minors) {
        Leaderboard leaderboard = new Leaderboard();
        leaderboard.setMajors(majors);
        leaderboard.setMinors(minors);
        return leaderboard;
    }
}
