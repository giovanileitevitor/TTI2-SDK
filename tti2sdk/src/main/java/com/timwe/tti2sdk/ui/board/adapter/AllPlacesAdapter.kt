package com.timwe.tti2sdk.ui.board.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.timwe.tti2sdk.R
import com.timwe.tti2sdk.data.entity.Board


class AllPlacesAdapter(
    private val context: Context,
    private val data: List<Board>,
    private val itemListener: (Board) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.item_alltime_board, parent, false)
        return DefaultVH(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        processDefault(holder, position)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    private inner class DefaultVH(itemView: View): RecyclerView.ViewHolder(itemView){
        var counter: TextView = itemView.findViewById(R.id.counter)
        var txtNameLeader: TextView = itemView.findViewById(R.id.txtNameLeader)
        var txtIdLeader: TextView = itemView.findViewById(R.id.txtIdLeader)
        var txtDistanceLeader: TextView = itemView.findViewById(R.id.txtDistanceLeader)
        var txtKmLeader: TextView = itemView.findViewById(R.id.txtKmLeader)
        var container: ConstraintLayout = itemView.findViewById(R.id.containerLeaderPlace)

        init{
            itemView.setOnClickListener {
                val position = bindingAdapterPosition
                val item = data[position]
                itemListener.invoke(item)
            }
        }
    }

    private fun processDefault(holder: RecyclerView.ViewHolder, position: Int){
        val item = data[position]
        val defaultVH = holder as AllPlacesAdapter.DefaultVH

        defaultVH.counter.text = item.counter.toString()
        defaultVH.txtNameLeader.text = item.boardName.toString()
        defaultVH.txtIdLeader.text = item.boardId.toString()
        defaultVH.txtDistanceLeader.text = item.kmBoard.toString()
        defaultVH.txtKmLeader.text = item.distanceUnit

        when(item.counter){
            1L -> { defaultVH.counter.background = context.getDrawable(R.drawable.circular_border_gold)
                    defaultVH.container.background = context.getDrawable(R.drawable.background_card_board_firsts)
            }
            2L -> { defaultVH.counter.background = context.getDrawable(R.drawable.circular_border_silver)
                    defaultVH.container.background = context.getDrawable(R.drawable.background_card_board_firsts)
            }
            3L -> { defaultVH.counter.background = context.getDrawable(R.drawable.circular_border_bronze)
                    defaultVH.container.background = context.getDrawable(R.drawable.background_card_board_firsts)
            }
            else -> { defaultVH.counter.background = context.getDrawable(R.drawable.circular_border)
                    defaultVH.container.background = context.getDrawable(R.drawable.background_card_board)
            }
        }




    }

}
