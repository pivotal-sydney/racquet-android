package io.pivotal.racquetandroid.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import io.pivotal.racquetandroid.R;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.model.response.Player;
import io.pivotal.racquetandroid.util.CircleTransform;

public class FilteredPlayerAdapter extends BaseAdapter implements Filterable {
    @Inject
    Picasso picasso;

    private Set<Player> players;
    private List<Player> filteredPlayers = new ArrayList<>();
    private PlayerFilter playerFilter;

    public FilteredPlayerAdapter(Set<Player> players) {
        super();
        this.players = players;
        RacquetApplication.getApplication().getApplicationComponent().inject(this);
        playerFilter = new PlayerFilter();
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    @Override
    public Filter getFilter() {
        return playerFilter;
    }

    @Override
    public int getCount() {
        return filteredPlayers.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredPlayers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return filteredPlayers.get(position).hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.view_filtered_player, null);
        }
        Player player = filteredPlayers.get(position);
        ((TextView) convertView.findViewById(R.id.name)).setText(player.getName());
        ((TextView) convertView.findViewById(R.id.twitter_handle)).setText(parent.getContext().getString(R.string.twitter_handle, player.getTwitterHandle()));
        picasso.load(player.getProfileImageUrl()).error(R.drawable.club).transform(new CircleTransform()).into((ImageView) convertView.findViewById(R.id.profile));
        return convertView;
    }

    class PlayerFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults result = new FilterResults();
            List<Player> filteredPlayers = new ArrayList<>();
            if (constraint != null) {
                String stringConstraint = constraint.toString().toLowerCase();
                for (Player player : players) {
                    String twitterHandle = "@" + player.getTwitterHandle();
                    if (player.getName().toLowerCase().contains(stringConstraint) || twitterHandle.toLowerCase().contains(stringConstraint)) {
                        filteredPlayers.add(player);
                    }
                }
            }
            result.values = filteredPlayers;
            return result;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredPlayers = (List<Player>) results.values;
            notifyDataSetChanged();
        }
    }
}
