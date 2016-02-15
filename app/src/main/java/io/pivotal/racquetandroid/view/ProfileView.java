package io.pivotal.racquetandroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.pivotal.racquetandroid.R;
import io.pivotal.racquetandroid.RacquetApplication;
import io.pivotal.racquetandroid.util.CircleTransform;

public class ProfileView extends LinearLayout {

    @Bind(R.id.profile_name)
    TextView profileNameView;

    @Bind(R.id.profile_image)
    ImageView profileImageView;

    @Inject
    Picasso picasso;

    public ProfileView(Context context) {
        super(context);
        init();
    }

    public ProfileView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ProfileView, 0, 0);
        init();
        try {
            setProfileName(attributes.getString(R.styleable.ProfileView_profile_name));
            setProfileImageUrl(attributes.getString(R.styleable.ProfileView_profile_image_src));
        } finally {
            attributes.recycle();
        }
    }

    private void init() {
        RacquetApplication.getApplication().getApplicationComponent().inject(this);
        LayoutInflater.from(getContext()).inflate(R.layout.view_profile, this);
        ButterKnife.bind(this);
    }

    public void setProfileName(String name) {
        profileNameView.setText(name);
    }

    public TextView getProfileName() {
        return profileNameView;
    }

    public void setProfileImageUrl(String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            picasso.load(imageUrl).transform(new CircleTransform()).placeholder(R.drawable.club).into(profileImageView);
        }
    }

    public ImageView getProfileImageView() {
        return profileImageView;
    }
}
