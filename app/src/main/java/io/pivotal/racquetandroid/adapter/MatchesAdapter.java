package io.pivotal.racquetandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.pivotal.racquetandroid.R;
import io.pivotal.racquetandroid.model.response.Match;
import io.pivotal.racquetandroid.view.ProfileView;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.MatchesViewHolder> {

    private List<Match> matches;

    public MatchesAdapter() {
        this(new ArrayList<Match>());
    }

    public MatchesAdapter(List<Match> matches) {
        this.matches = matches;
        setHasStableIds(true);
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
        notifyDataSetChanged();
    }

    @Override
    public MatchesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MatchesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_match, parent, false));
    }

    @Override
    public void onBindViewHolder(MatchesViewHolder holder, int position) {
        holder.bind(matches.get(position));
    }

    @Override
    public long getItemId(int position) {
        return matches.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return matches.size();
    }

    public void addMatch(Match match) {
        matches.add(0, match);
        notifyDataSetChanged();
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
            winner.setProfileName(match.getWinner().getName());
            loser.setProfileName(match.getLoser().getName());
            winner.setProfileImageUrl(match.getWinner().getProfileImageUrl());
            loser.setProfileImageUrl(match.getLoser().getProfileImageUrl());
        }
    }
}