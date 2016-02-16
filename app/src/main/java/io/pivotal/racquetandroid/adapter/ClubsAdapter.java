package io.pivotal.racquetandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.pivotal.racquetandroid.R;
import io.pivotal.racquetandroid.activity.ClubActivity;
import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.view.ProfileView;

public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ClubViewHolder> {
    private List<Club> clubs;

    public ClubsAdapter(List<Club> clubs) {
        this.clubs = clubs;
    }

    @Override
    public ClubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClubViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_club_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ClubViewHolder holder, int position) {
        Club club = clubs.get(position);
        holder.bind(club);
    }

    @Override
    public int getItemCount() {
        return clubs.size();
    }

    public void setClubs(List<Club> clubs) {
        this.clubs = clubs;
        notifyDataSetChanged();
    }

    public static class ClubViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.club)
        ProfileView profileView;

        private Club club;

        public ClubViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClubActivity.startActivity(v.getContext(), club);
                }
            });
        }

        public void bind(Club club) {
            this.club = club;
            profileView.setProfileName(club.getName());
            profileView.setProfileImageUrl(club.getLogo().getStandard().getUrl());
        }
    }
}
