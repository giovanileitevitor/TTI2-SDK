package com.timwe.tti2sdk.ui.generic;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.timwe.tti2sdk.R;
import com.timwe.utils.Utils;

/**
 * Created by Roberto Fernandes on 4/26/2021.
 */
public class TtiToolbar extends RelativeLayout {

    private static final String TAG = "TtiToolbar";

    public enum LeftIconType {
        ARROW_BACK_EXIT,
        CLOSE
    }

    public enum RightItem {
        BELL
    }


    public interface TtiToolbarListener {
        void leftIconClicked();
    }

    private TtiToolbarListener ttiToolbarListener;

    private TextView titleTextView;
    private ImageView leftIcon;
    private ImageView rightIcon;

    public TtiToolbar(Context context) {
        super(context);
        init(context);
    }

    public TtiToolbar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public TtiToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.tti_toolbar, this, true);
        titleTextView = v.findViewById(R.id.textView1);
        leftIcon = v.findViewById(R.id.tti_toolbar_left_icon);
        rightIcon = v.findViewById(R.id.tti_toolbar_right_icon);
    }

    public void setTtiToolbar(Context context
            , LeftIconType type
            , String title
            , TtiToolbarListener ttiToolbarListener
            , RightItem rightItem
    ) {
        setInboxIcon(context, rightItem);
        setTitle(title);
        setLeftIcon(context, type);
        this.ttiToolbarListener = ttiToolbarListener;
        if (ttiToolbarListener != null) {
            leftIcon.setOnClickListener(view -> this.ttiToolbarListener.leftIconClicked());
        }
    }

    public void setTitle(String title) {
        if (title == null) {
            titleTextView.setVisibility(View.INVISIBLE);
        } else {
            titleTextView.setVisibility(View.VISIBLE);
            titleTextView.setText(title);
        }
    }

    private void setLeftIcon(Context context, LeftIconType type) {
        if (type == null) {
            leftIcon.setVisibility(View.GONE);
            return;
        }

        leftIcon.setVisibility(View.VISIBLE);

        if (type == LeftIconType.ARROW_BACK_EXIT) {
            leftIcon.setImageDrawable(Utils.getDrawableFromId(context, R.drawable.ic_back_arrow));
        } else if (type == LeftIconType.CLOSE) {
            leftIcon.setImageDrawable(Utils.getDrawableFromId(context, R.drawable.icon_star));
        }
    }

    private void setInboxIcon(Context context, RightItem rightItem) {
        if (rightItem == null) {
            rightIcon.setVisibility(View.INVISIBLE);
        } else {
            if (rightItem == RightItem.BELL) {
                rightIcon.setImageDrawable(Utils.getDrawableFromId(context,R.drawable.icon_star));
            }
            rightIcon.setVisibility(View.VISIBLE);
        }
    }

    public void setRightClickListener(OnClickListener listener) {
        rightIcon.setOnClickListener(listener);
    }

}