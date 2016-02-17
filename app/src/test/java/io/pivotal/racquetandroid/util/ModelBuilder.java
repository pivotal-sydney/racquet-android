package io.pivotal.racquetandroid.util;

import java.util.ArrayList;
import java.util.List;

import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.model.Clubs;
import io.pivotal.racquetandroid.model.Logo;
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
        match.setWinner(getPlayer(playerOne));
        match.setLoser(getPlayer(playerTwo));
        return match;
    }

    public static Player getPlayer(String name) {
        Player player = new Player();
        player.setName(name);
        player.setProfileImageUrl("http://profile/url/" + name);
        player.setTwitterHandle("twitter_" + name);
        return player;
    }
}
