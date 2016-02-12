package io.pivotal.racquetandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.pivotal.racquetandroid.R;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.activity.ClubActivity;
import io.pivotal.racquetandroid.model.Club;
import io.pivotal.racquetandroid.util.CircleTransform;

public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.ClubViewHolder> {

    @Inject
    Picasso picasso;

    private List<Club> clubs;

    public ClubsAdapter(List<Club> clubs) {
        this.clubs = clubs;
        RacquetApplication.getApplication().getApplicationComponent().inject(this);
    }

    @Override
    public ClubViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.view_club_item, null);
        return new ClubViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ClubViewHolder holder, int position) {
        Club club = clubs.get(position);
        holder.bind(club, picasso);
    }

    @Override
    public int getItemCount() {
        return clubs.size();
    }

    public static class ClubViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.name)
        TextView name;

        @Bind(R.id.image)
        ImageView image;

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

        public void bind(Club club, Picasso picasso) {
            this.club = club;
            name.setText(club.getName());
            picasso.load(club.getLogo().getStandard().getUrl()).placeholder(R.drawable.club).transform(new CircleTransform()).into(image);
        }
    }
}
