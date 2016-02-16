package io.pivotal.racquetandroid.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.pivotal.racquetandroid.R;

public class DividerTextView extends LinearLayout {

    @Bind(R.id.text)
    TextView textView;

    public DividerTextView(Context context) {
        super(context);
        init();
    }

    public DividerTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.DividerTextView, 0, 0);
        try {
            setText(attributes.getString(R.styleable.DividerTextView_text));
        } finally {
            attributes.recycle();
        }
    }

    public void setText(String text) {
        textView.setText(text);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_divider_text, this);
        ButterKnife.bind(this);
    }
}
