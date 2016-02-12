package io.pivotal.racquetandroid.util;

import java.util.ArrayList;
import java.util.List;

import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.model.Logo;
import io.pivotal.racquetandroid.model.StandardLogo;
import io.pivotal.racquetandroid.model.response.Match;
import io.pivotal.racquetandroid.model.response.Matches;
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

    public static List<Club> getClubs(int size, String name) {
        List<Club> clubs = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            clubs.add(getClub(i, name + i));
        }
        return clubs;
    }

    public static Matches getMatches(int size, String playerOne, String playerTwo) {
        List<Match> matchList = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            Match match;
            if (i % 2 == 0) {
                match = ModelBuilder.getMatch(playerOne, playerTwo);
            } else {
                match = ModelBuilder.getMatch(playerTwo, playerOne);
            }
            matchList.add(match);
        }
        Matches matches = new Matches();
        matches.setResults(matchList);
        return matches;
    }

    public static Match getMatch(String playerOne, String playerTwo) {
        Match match = new Match();
        match.setWinnerData(getPlayer(playerOne));
        match.setLoserData(getPlayer(playerTwo));
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
