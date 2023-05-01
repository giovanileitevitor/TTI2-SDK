package com.timwe.tti2sdk.ui.prizes.fragments.adapter

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.style.TypefaceSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Priority
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.gson.Gson
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.PrizeFlow
import com.timwe.tti2sdk.data.model.response.AvailableReward
import com.timwe.tti2sdk.data.model.response.HistoryReward
import com.timwe.tti2sdk.ui.prizes.fragments.viewholder.AvailableViewHolder
import com.timwe.tti2sdk.ui.prizes.fragments.viewholder.HistoryBottomViewHolder
import com.timwe.tti2sdk.ui.prizes.fragments.viewholder.HistoryMiddleViewHolder
import com.timwe.tti2sdk.ui.prizes.fragments.viewholder.HistoryTopViewHolder
import com.timwe.utils.onDebouncedListener
import com.timwe.utils.toDateString
import com.timwe.utils.toHourString

class AdapterPrizes(
    private val context: Context,
    private val resource: Int = 0,
    private var prize: PrizeFlow,
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

        when (typeViewHolder) {
            PRIZES_AVAILABLE -> {
                val viewHolder = inflater.inflate(resource, parent, false)
                return AvailableViewHolder(viewHolder)
            }
            PRIZES_HISTORY -> {

                try {

                    val anterior = prize.historyRewards[viewType - 1].day
                    val atual = prize.historyRewards[viewType].day
                    val proximo = prize.historyRewards[viewType + 1].day

                    if(atual == proximo && atual != anterior) {
                        //top
                        val viewHolder = inflater.inflate(R.layout.item_list_prizes_top_history, parent, false)
                        return HistoryTopViewHolder(viewHolder)
                    }else if(atual == proximo && atual == anterior) {
                        //midle
                        val viewHolder = inflater.inflate(R.layout.item_list_prizes_midle_history, parent, false)
                        return HistoryMiddleViewHolder(viewHolder)
                    }else if(atual != proximo && atual == anterior) {
                        //bottom
                        val viewHolder = inflater.inflate(R.layout.item_list_prizes_bottom_history, parent, false)
                        return HistoryBottomViewHolder(viewHolder)
                    } else{
                        //midle
                        val viewHolder = inflater.inflate(R.layout.item_list_prizes_midle_history, parent, false)
                        return HistoryMiddleViewHolder(viewHolder)
                    }

                } catch (e: java.lang.Exception) {
                    if (viewType == 0) {
                        //top
                        val viewHolder = inflater.inflate(R.layout.item_list_prizes_top_history, parent, false)
                        return HistoryTopViewHolder(viewHolder)
                    } else {
                        //bottom
                        val viewHolder = inflater.inflate(R.layout.item_list_prizes_bottom_history, parent, false)
                        return HistoryBottomViewHolder(viewHolder)
                    }
                }
            }
            else -> throw Exception("View holder not exists")
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

        if(PRIZES_AVAILABLE == typeViewHolder){
            options = options as AvailableReward
            val viewHolder = holder as AvailableViewHolder

            val gson = Gson()
            val mMineUserEntity1 = gson.toJson(options, AvailableReward::class.java)
            println(mMineUserEntity1)

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

            mGlide.load((options as AvailableReward).cardLayout.genericPrizeIconUrl!!)
                .priority(Priority.HIGH)
                .listener(object : RequestListener<Drawable> {

                    override fun onLoadFailed(e: GlideException?, model: Any?,
                                              target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        viewHolder.progress?.visibility = View.INVISIBLE
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                                 dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        viewHolder.progress?.visibility = View.INVISIBLE
                        return false
                    }
                })
                .into(viewHolder.ivIconTop!!)

        }else{

            options = options as HistoryReward

            if(holder is HistoryTopViewHolder){
                val holderaux = holder as HistoryTopViewHolder

                holderaux.tvTitle?.text = options.day.toDateString()
                holderaux.tvTitleTop?.text = options.cardLayout.historyPrizeTitle
                holderaux.tvMessageTop?.text = options.cardLayout.historyPrizeDescription
                holderaux.tvHoursTop?.text = options.attributionDate.toHourString()

                Log.i("ab", options.cardLayout.historyPrizeIconUrl)


                mGlide.load((options as HistoryReward).cardLayout.historyPrizeIconUrl)
                    .priority(Priority.HIGH)
                    .listener(object : RequestListener<Drawable> {

                        override fun onLoadFailed(e: GlideException?, model: Any?,
                                                  target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                                     dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            return false
                        }
                    })
                    .into( holderaux.ivIconTop!!)

            }else if(holder is HistoryMiddleViewHolder){
                val holderaux = holder as HistoryMiddleViewHolder

                holderaux.tvTitleMiddle?.text = options.cardLayout.historyPrizeTitle
                holderaux.tvMessageMiddle?.text = options.cardLayout.historyPrizeDescription
                holderaux.tvHoursMiddle?.text = options.attributionDate.toHourString()

                mGlide.load((options as HistoryReward).cardLayout.historyPrizeIconUrl!!)
                    .priority(Priority.HIGH)
                    .listener(object : RequestListener<Drawable> {

                        override fun onLoadFailed(e: GlideException?, model: Any?,
                                                  target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                                     dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            return false
                        }
                    })
                    .into(holderaux.ivIconMiddle!!)

            }else{
                val holderaux = holder as HistoryBottomViewHolder

                holderaux.tvTitleBottom?.text = options.cardLayout.historyPrizeTitle
                holderaux.tvMessageBottom?.text = options.cardLayout.historyPrizeDescription
                holderaux.tvHoursBottom?.text = options.attributionDate.toHourString()

                mGlide.load((options as HistoryReward).cardLayout.historyPrizeIconUrl)
                    .priority(Priority.HIGH)
                    .listener(object : RequestListener<Drawable> {

                        override fun onLoadFailed(e: GlideException?, model: Any?,
                                                  target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            return false
                        }

                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?,
                                                     dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            return false
                        }
                    })
                    .into(holderaux.ivIconBottom!!)

            }

        }

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}