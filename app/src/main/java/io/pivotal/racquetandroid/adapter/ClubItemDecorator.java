package io.pivotal.racquetandroid.adapter;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import io.pivotal.racquetandroid.R;

public class ClubItemDecorator extends RecyclerView.ItemDecoration {
    private final int gridSize;

    private int gridSpacingPx;
    private boolean needsLeftSpacing;

    public ClubItemDecorator(Context context, int gridSize) {
        this.gridSize = gridSize;
        gridSpacingPx = context.getResources().getDimensionPixelSize(R.dimen.grid_spacing);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int frameWidth = (int) ((parent.getWidth() - (float) gridSpacingPx * (gridSize - 1)) / gridSize);
        int padding = parent.getWidth() / gridSize - frameWidth;
        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewAdapterPosition();
        if (itemPosition < gridSize) {
            outRect.top = 0;
        } else {
            outRect.top = gridSpacingPx;
        }
        if (itemPosition % gridSize == 0) {
            outRect.left = 0;
            outRect.right = padding;
            needsLeftSpacing = true;
        } else if ((itemPosition + 1) % gridSize == 0) {
            needsLeftSpacing = false;
            outRect.right = 0;
            outRect.left = padding;
        } else if (needsLeftSpacing) {
            needsLeftSpacing = false;
            outRect.left = gridSpacingPx - padding;
            if ((itemPosition + 2) % gridSize == 0) {
                outRect.right = gridSpacingPx - padding;
            } else {
                outRect.right = gridSpacingPx / 2;
            }
        } else if ((itemPosition + 2) % gridSize == 0) {
            needsLeftSpacing = false;
            outRect.left = gridSpacingPx / 2;
            outRect.right = gridSpacingPx - padding;
        } else {
            needsLeftSpacing = false;
            outRect.left = gridSpacingPx / 2;
            outRect.right = gridSpacingPx / 2;
        }
        outRect.bottom = 0;
    }
}
