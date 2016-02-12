package io.pivotal.racquetandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.pivotal.racquetandroid.R;
import io.pivotal.racquetandroid.model.response.Match;
import io.pivotal.racquetandroid.model.response.Matches;
import io.pivotal.racquetandroid.view.ProfileView;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MatchesViewHolder> {

    private Matches matches;

    public MatchesAdapter() {
        this(new Matches());
    }

    public MatchesAdapter(Matches matches) {
        this.matches = matches;
    }

    public void setMatches(Matches matches) {
        this.matches = matches;
        notifyDataSetChanged();
    }

    @Override
    public MatchesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MatchesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_match, parent, false));
    }

    @Override
    public void onBindViewHolder(MatchesViewHolder holder, int position) {
        holder.bind(matches.getResults().get(position));
    }

    @Override
    public int getItemCount() {
        return matches.getResults().size();
    }

    public static class MatchesViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.winner)
        ProfileView winner;
        @Bind(R.id.loser)
        ProfileView loser;
        @Bind(R.id.defeated)
        TextView defeated;

        public MatchesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Match match) {
            winner.setProfileName(match.getWinnerData().getName());
            loser.setProfileName(match.getLoserData().getName());
            winner.setProfileImageUrl(match.getWinnerData().getProfileImageUrl());
            loser.setProfileImageUrl(match.getLoserData().getProfileImageUrl());
        }
    }
}
