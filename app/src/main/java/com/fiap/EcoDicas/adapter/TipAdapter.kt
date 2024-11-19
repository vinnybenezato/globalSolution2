package com.fiap.EcoDicas.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.fiap.EcoDicas.R
import com.fiap.EcoDicas.model.Tip

class TipAdapter(
    private val context: Context,
    private val tips: List<Tip>
) : RecyclerView.Adapter<TipAdapter.TipViewHolder>() {

    class TipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle: TextView = itemView.findViewById(R.id.textTitle)
        val textDescription: TextView = itemView.findViewById(R.id.textDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.tip_item, parent, false)
        return TipViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TipViewHolder, position: Int) {
        val currentTip = tips[position]
        holder.textTitle.text = currentTip.title
        holder.textDescription.text = currentTip.description
        holder.itemView.setOnClickListener {
            Toast.makeText(context, currentTip.curiosity, Toast.LENGTH_SHORT).show()
        }
        holder.itemView.setOnLongClickListener {
            val openLinkIntent = Intent(Intent.ACTION_VIEW, Uri.parse(currentTip.link))
            context.startActivity(openLinkIntent)
            true
        }
    }

    override fun getItemCount(): Int {
        return tips.size
    }
}
