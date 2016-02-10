package io.pivotal.racquetandroid.util;

import java.util.ArrayList;
import java.util.List;

import io.pivotal.racquetandroid.R;
import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.model.Logo;
import io.pivotal.racquetandroid.model.StandardLogo;

public class ModelBuilder {

    public static Club getClub(String name) {
        Club club = new Club();
        club.setName(name);
        Logo logo = new Logo();
        logo.setUrl("android.resource://io.pivotal.racquetandroid/" + R.drawable.club);
        StandardLogo standardLogo = new StandardLogo();
        standardLogo.setUrl("android.resource://io.pivotal.racquetandroid/" + R.drawable.club);
        logo.setStandard(standardLogo);
        club.setLogo(logo);
        return club;
    }

    public static List<Club> getClubs(int size, String name) {
        List<Club> clubs = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            clubs.add(getClub(name + i));
        }
        return clubs;
    }
}
