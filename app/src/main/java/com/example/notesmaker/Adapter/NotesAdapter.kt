package com.example.notesmaker.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesmaker.R
import com.example.notesmaker.dataclass.Notes

class NotesAdapter(private var notes:List<Notes>,context: Context) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){

    class NoteViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val titletextview :  TextView  = itemView.findViewById(R.id.titletextview)
        val contenttextview : TextView = itemView.findViewById(R.id.contenttextview)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val views = LayoutInflater.from(parent.context).inflate(R.layout.notes_item,parent,false)
        return NoteViewHolder(views)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
       val recyclerNotes = notes[position]
        holder.titletextview.text = recyclerNotes.title
        holder.contenttextview.text = recyclerNotes.content
    }

    override fun getItemCount(): Int {
       return notes.size
    }



    //refreshing notes
    fun refreshingNotes(newNotes : List<Notes>){
        notes = newNotes
        notifyDataSetChanged()
    }
}