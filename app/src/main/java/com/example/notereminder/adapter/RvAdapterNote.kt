package com.example.notereminder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notereminder.Converters
import com.example.notereminder.R
import com.example.notereminder.model.Note

class RvAdapterNote(private val noteList: List<Note>) : RecyclerView.Adapter<RvAdapterNote.ViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.card_note, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = noteList[position].title
        holder.tvContent.text = noteList[position].content
        val convert = Converters()
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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val tvContent: TextView = itemView.findViewById(R.id.tv_content)
        val tvDateTime: TextView = itemView.findViewById(R.id.tv_date_time)
    }
}