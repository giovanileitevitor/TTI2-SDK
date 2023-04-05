package com.timwe.tti2sdk.ui.prizes.fragments.adapter

import android.R
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StyleSpan
import android.text.style.TypefaceSpan
import android.text.style.UnderlineSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.timwe.tti2sdk.data.entity.Prize
import com.timwe.tti2sdk.data.model.response.AvailableReward
import com.timwe.tti2sdk.data.model.response.HistoryReward
import com.timwe.tti2sdk.ui.prizes.fragments.viewholder.AvailableViewHolder
import com.timwe.tti2sdk.ui.prizes.fragments.viewholder.HistoryViewHolder
import com.timwe.utils.onDebouncedListener
import com.timwe.utils.toDateString
import com.timwe.utils.toHourString


class AdapterPrizes(
    private val context: Context,
    private val resource: Int,
    private var prize: Prize,
    private val mGlide: RequestManager,
    private val typeViewHolder: Int,
    private val clickListener: (AvailableReward) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val PRIZES_AVAILABLE = 0
        const val PRIZES_HISTORY = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewHolder = inflater.inflate(resource, parent, false)

        return when(typeViewHolder){
            PRIZES_AVAILABLE -> {
                AvailableViewHolder(viewHolder)
            }
            PRIZES_HISTORY -> {
                HistoryViewHolder(viewHolder)
            }
            else -> {
                throw Exception("View holder not exists")
            }
        }
    }

    override fun getItemCount(): Int {
        return when(typeViewHolder){
            PRIZES_AVAILABLE -> {
                prize.availableRewards.size
            }
            PRIZES_HISTORY -> {
                prize.historyRewards.size
            }
            else -> {
                throw Exception("View holder not exists")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var options = when(typeViewHolder){
            PRIZES_AVAILABLE -> prize.availableRewards[position]
            PRIZES_HISTORY -> prize.historyRewards[position]
            else -> throw Exception("View holder not exists")
        }

        when(typeViewHolder){
            PRIZES_AVAILABLE -> {
                options = options as AvailableReward
                val viewHolder = holder as AvailableViewHolder

                if(options.descr.isNullOrEmpty()){
                    viewHolder.tvTitleVoucher?.text = options.name

                }else{
                    val spanString = SpannableString("${options.name}\n${options.descr}")
                    val typefaceBold = Typeface.create(ResourcesCompat.getFont(context, com.timwe.tti2sdk.R.font.poppins_bold), Typeface.NORMAL)
                    val typefaceRegular = Typeface.create(ResourcesCompat.getFont(context, com.timwe.tti2sdk.R.font.poppins_regular), Typeface.NORMAL)
                    spanString.setSpan(TypefaceSpan(typefaceBold), 0, options.name.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
                    spanString.setSpan(TypefaceSpan(typefaceRegular), (options.name.length+1), spanString.length, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
                    viewHolder.tvTitleVoucher?.text = spanString
                }

                viewHolder.btnPrize?.onDebouncedListener{
                    clickListener.invoke((options as AvailableReward))
                }

                mGlide
                    .load(options.additionalProperties.prizeIcon)
                    .priority(Priority.HIGH)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            viewHolder.progress?.visibility = View.GONE
                            return false
                        }
                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            viewHolder.progress?.visibility = View.GONE
                            return false
                        }
                    })
                    .into(viewHolder.ivIconTop!!)

            }
            PRIZES_HISTORY -> {
                options = options as HistoryReward
                val viewHolder = holder as HistoryViewHolder

                viewHolder.tvTitle?.text = options.day.toDateString()

                viewHolder.tvTitleTop?.text = options.rewards.first().cardLayout.historyPrizeTitle
                viewHolder.tvMessageTop?.text = options.rewards.first().cardLayout.historyPrizeDescription
                viewHolder.tvHoursTop?.text = options.rewards.first().attributionDate.toHourString()


//
//                viewHolder.tvTitleMiddle.text =
//                viewHolder.tvMessageMiddle.text =
//                viewHolder.tvHoursMiddle.text =
//
//                viewHolder.tvTitleBottom.text =
//                viewHolder.tvMessageBottom.text =
//                viewHolder.tvHoursBottom.text =

                mGlide
                    .load(options.rewards.first().cardLayout.genericPrizeIconUrl)
                    .priority(Priority.HIGH)
                    .into( viewHolder.ivIconTop!!)

                mGlide
                    .load("")
                    .priority(Priority.HIGH)
                    .into(viewHolder.ivIconMiddle!!)

                mGlide
                    .load("")
                    .priority(Priority.HIGH)
                    .into(viewHolder.ivIconBottom!!)

            }
            else -> throw Exception("View holder not exists")
        }

    }

}