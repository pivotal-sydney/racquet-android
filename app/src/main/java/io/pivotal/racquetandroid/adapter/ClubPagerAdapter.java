package io.pivotal.racquetandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import io.pivotal.racquetandroid.fragment.FeedFragment;
import io.pivotal.racquetandroid.fragment.LeaderboardFragment;
import io.pivotal.racquetandroid.model.Club;

public class ClubPagerAdapter extends FragmentPagerAdapter {
    private Club club;

    public ClubPagerAdapter(Club club, FragmentManager fm) {
        super(fm);
        this.club = club;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0 ){
            return FeedFragment.newInstance(club);
        }
        else{
            return LeaderboardFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
