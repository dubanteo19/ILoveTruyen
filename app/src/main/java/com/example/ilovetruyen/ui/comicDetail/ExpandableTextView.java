package com.example.ilovetruyen.ui.comicDetail;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ilovetruyen.R;
import com.google.android.material.button.MaterialButton;

public class ExpandableTextView extends LinearLayout {

    private static final int MAX_COLLAPSED_LINES = 3;

    private TextView tvContent;
    private MaterialButton summaryToggleBtn;
    private LinearLayout layoutDescriptionComic; // Thêm biến để tham chiếu đến LinearLayout

    private boolean isExpanded = false;

    public ExpandableTextView(Context context) {
        super(context);
        init(context);
    }

    public ExpandableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExpandableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        layoutDescriptionComic = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.expandable_text_view, this, true);

        tvContent = findViewById(R.id.tv_content);
        summaryToggleBtn = findViewById(R.id.summary_toggle_btn);

        // Thiết lập orientation cho LinearLayout
        layoutDescriptionComic.setOrientation(LinearLayout.VERTICAL); // Hoặc LinearLayout.HORIZONTAL tùy vào thiết kế

        summaryToggleBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isExpanded = !isExpanded;
                updateView();
            }
        });

        // Thiết lập văn bản mẫu
        setText("AppBarLayout also requires a separate scrolling sibling...");

        // Cập nhật trạng thái ban đầu
        updateView();
    }

    private void updateView() {
        if (isExpanded) {
            tvContent.setMaxLines(Integer.MAX_VALUE);
            summaryToggleBtn.setText("See Less");
        } else {
            tvContent.setMaxLines(MAX_COLLAPSED_LINES);
            summaryToggleBtn.setText("See More");
        }
    }

    public void setText(CharSequence text) {
        tvContent.setText(text);
    }

    public CharSequence getText() {
        return tvContent.getText();
    }
}
