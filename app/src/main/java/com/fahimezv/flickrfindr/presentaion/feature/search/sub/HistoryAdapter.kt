package com.fahimezv.flickrfindr.presentaion.feature.search.sub

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fahimezv.flickrfindr.core.model.Term
import com.fahimezv.flickrfindr.databinding.TermItemBinding

class HistoryAdapter(
    private val onItemClick: (String)->Unit
) : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    private val list = mutableListOf<Term>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryAdapter.ViewHolder {
        val itemBinding = TermItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        holder.bind(list[position])

    }

    @SuppressLint("NotifyDataSetChanged")
    fun bind(terms: List<Term>) {
        list.clear()
        list.addAll(terms)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val itemBinding:TermItemBinding) : RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {


        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            onItemClick(list[bindingAdapterPosition].text)
        }


        fun bind(term:Term) {
            itemBinding.termTextView.text=term.text

        }
    }


}
