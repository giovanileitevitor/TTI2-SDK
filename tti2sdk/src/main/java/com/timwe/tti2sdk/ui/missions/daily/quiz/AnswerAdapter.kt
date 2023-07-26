package com.timwe.tti2sdk.ui.missions.daily.quiz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.Answer
import com.timwe.tti2sdk.ui.missions.DailyMissionAdapter

class AnswerAdapter(
    private val context: Context,
    private val data: List<Answer> = emptyList(),
    private val mGlide: RequestManager,
    private val itemListener: (Answer) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view : View = inflater.inflate(R.layout.item_answer_quiz, parent, false)
        return DefaultVH(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        processDefault(holder, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private inner class DefaultVH(itemView: View): RecyclerView.ViewHolder(itemView) {

        var container: ConstraintLayout = itemView.findViewById(R.id.containerAnswer)
        var txtAnswerNumber: TextView = itemView.findViewById(R.id.txtAnswerNumber)
        var txtAnswerValue: TextView = itemView.findViewById(R.id.txtAnswerValue)

        init{
            container.setOnClickListener {
                val position = bindingAdapterPosition
                val item = data[position]
                itemListener.invoke(item)
                notifyDataSetChanged()
            }
        }

    }

    private fun processDefault(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        val defaultVH = holder as DefaultVH

        defaultVH.txtAnswerNumber.text = (position + 1).toString()
        defaultVH.txtAnswerValue.text = item.answer

    }
}