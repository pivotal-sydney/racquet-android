package io.pivotal.racquetandroid.adapter;

import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.pivotal.racquetandroid.R;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.model.Leaderboard;
import io.pivotal.racquetandroid.model.Ranking;
import io.pivotal.racquetandroid.model.response.Player;
import io.pivotal.racquetandroid.util.CircleTransform;

public class LeaderboardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static final int MAJOR_LEAGUE_HEADER = 1;
    static final int MAJOR_LEAGUE_ITEM = 2;
    static final int MINOR_LEAGUE_ITEMS = 3;
    private Leaderboard leaderboard;

    @Inject
    Picasso picasso;

    public LeaderboardAdapter(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
        RacquetApplication.getApplication().getApplicationComponent().inject(this);
    }

    public LeaderboardAdapter() {
        this.leaderboard = new Leaderboard();
        RacquetApplication.getApplication().getApplicationComponent().inject(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MAJOR_LEAGUE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_leaderboard_major_item, parent, false);
            return new MajorLeagueItemViewHolder(view);
        } else if (viewType == MAJOR_LEAGUE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_leaderboard_major_header, parent, false);
            return new MajorLeagueHeaderViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_leaderboard_minor, parent, false);
            return new MinorLeaguesViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MajorLeagueItemViewHolder) {
            // Major items start in the recyclerview after the first item which is a major league header
            // So the model position is actually one position behind the list view position
            ((MajorLeagueItemViewHolder) holder).bind(leaderboard.getMajors().get(position - 1), picasso, position - 1);
        } else if (holder instanceof MinorLeaguesViewHolder) {
            ((MinorLeaguesViewHolder) holder).bind(leaderboard.getMinors(), picasso);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return MAJOR_LEAGUE_HEADER;
        }
        if (position == getItemCount() - 1) {
            return MINOR_LEAGUE_ITEMS;
        }
        return MAJOR_LEAGUE_ITEM;
    }

    @Override
    public int getItemCount() {
        // Size accounts for header and footers
        return leaderboard.getMajors().size() + 2;
    }

    public void setLeaderboard(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
        notifyDataSetChanged();
    }

    static class MinorLeaguesViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.minor_list)
        LinearLayout list;

        @Bind(R.id.profile)
        ImageView profile;
        private final LinearLayout.LayoutParams params;

        public MinorLeaguesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            params = (LinearLayout.LayoutParams) profile.getLayoutParams();
        }

        public void bind(List<Ranking> minors, Picasso picasso) {
            int size = Math.min(minors.size(), 8);
            for (int i = 0; i < size; i++) {
                ImageView imageView = new ImageView(itemView.getContext());
                imageView.setLayoutParams(params);
                list.addView(imageView);
                picasso.load(minors.get(i).getMember().getProfileImageUrl()).error(R.drawable.club).transform(new CircleTransform()).into(imageView);
            }
        }
    }

    static class MajorLeagueItemViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.rank)
        public TextView rank;
        @Bind(R.id.profile)
        public ImageView profile;
        @Bind(R.id.name)
        public TextView name;
        @Bind(R.id.wins)
        public TextView wins;
        @Bind(R.id.losses)
        public TextView losses;
        @Bind(R.id.percentage)
        public TextView percentage;
        @Bind(R.id.points)
        public TextView points;

        public MajorLeagueItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Ranking ranking, Picasso picasso, int position) {
            Resources resources = RacquetApplication.getApplication().getResources();
            rank.setText(String.valueOf(position + 1));
            Player member = ranking.getMember();
            picasso.load(member.getProfileImageUrl()).error(R.drawable.club).transform(new CircleTransform()).into(profile);
            wins.setText(resources.getString(R.string.wins, member.getWins()));
            losses.setText(resources.getString(R.string.losses, member.getLosses()));
            name.setText(member.getName());
            DecimalFormat decim = new DecimalFormat(".000");
            percentage.setText(decim.format(member.getWinningPercentage()));
            points.setText(resources.getString(R.string.points, member.getPoints()));
        }
    }

    public class MajorLeagueHeaderViewHolder extends RecyclerView.ViewHolder {
        public MajorLeagueHeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
}
