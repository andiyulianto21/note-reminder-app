package com.example.notereminder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notereminder.R
import com.example.notereminder.databinding.ActivityMainBinding
import com.example.notereminder.model.Note

class RvAdapterNote(
    private val noteList: List<Note>,
    val mNoteClickedListener: OnNoteClickedListener
) : RecyclerView.Adapter<RvAdapterNote.ViewHolder>() {

    private lateinit var context: Context

    interface OnNoteClickedListener {
        fun onItemClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.card_note, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = noteList[position].title
        holder.tvContent.text = noteList[position].content
        val dateTimeNow = noteList[position].dateTime
        holder.tvDateTime.text = dateTimeNow

    }

    override fun getItemCount(): Int {
        return if(noteList.isEmpty()){
            0
        }else {
            noteList.size
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvContent: TextView = itemView.findViewById(R.id.tv_content)
        val tvDateTime: TextView = itemView.findViewById(R.id.tv_date_time)

        init {
            itemView.setOnClickListener { mNoteClickedListener.onItemClicked(adapterPosition) }
        }
    }
}