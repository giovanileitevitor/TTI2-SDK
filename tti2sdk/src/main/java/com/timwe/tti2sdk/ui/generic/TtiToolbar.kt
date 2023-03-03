package com.timwe.tti2sdk.ui.generic

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.timwe.tti2sdk.R
import com.timwe.utils.Utils

class TtiToolbar: RelativeLayout {

    private val TAG = "TtiToolbar"

    enum class LeftIconType {
        ARROW_BACK_EXIT, CLOSE
    }

    enum class RightItem {
        BELL
    }

    interface TtiToolbarListener {
        fun leftIconClicked()
    }

    private var ttiToolbarListener: TtiToolbarListener? = null
    private var titleTextView: TextView? = null
    private var leftIcon: ImageView? = null
    private var rightIcon: ImageView? = null

    constructor(context: Context?) : super(context){
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle){
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
        init(context)
    }

    fun init(context: Context?) {
        val inflater = LayoutInflater.from(context)
        val v = inflater.inflate(R.layout.tti_toolbar, this, true)
        titleTextView = v.findViewById(R.id.textView1)
        leftIcon = v.findViewById(R.id.tti_toolbar_left_icon)
        rightIcon = v.findViewById(R.id.tti_toolbar_right_icon)
    }

    fun setTtiToolbar(
        context: Context,
        type: LeftIconType?,
        title: String?,
        ttiToolbarListener: TtiToolbarListener?,
        rightItem: RightItem?
    ) {
        setInboxIcon(context, rightItem)
        setTitle(title)
        setLeftIcon(context, type)
        this.ttiToolbarListener = ttiToolbarListener
        if (ttiToolbarListener != null) {
            leftIcon!!.setOnClickListener { view: View? -> this.ttiToolbarListener!!.leftIconClicked() }
        }
    }

    fun setTitle(title: String?) {
        if (title == null) {
            titleTextView!!.visibility = INVISIBLE
        } else {
            titleTextView!!.visibility = VISIBLE
            titleTextView!!.text = title
        }
    }

    private fun setLeftIcon(context: Context, type: LeftIconType?) {
        if (type == null) {
            leftIcon!!.visibility = GONE
            return
        }
        leftIcon!!.visibility = VISIBLE
        if (type == LeftIconType.ARROW_BACK_EXIT) {
            leftIcon!!.setImageDrawable(Utils.getDrawableFromId(context, R.drawable.ic_back_arrow))
        } else if (type == LeftIconType.CLOSE) {
            leftIcon!!.setImageDrawable(Utils.getDrawableFromId(context, R.drawable.icon_star))
        }
    }

    private fun setInboxIcon(context: Context, rightItem: RightItem?) {
        if (rightItem == null) {
            rightIcon!!.visibility = INVISIBLE
        } else {
            if (rightItem == RightItem.BELL) {
                rightIcon!!.setImageDrawable(Utils.getDrawableFromId(context, R.drawable.icon_star))
            }
            rightIcon!!.visibility = VISIBLE
        }
    }

    fun setRightClickListener(listener: OnClickListener?) {
        rightIcon!!.setOnClickListener(listener)
    }

}