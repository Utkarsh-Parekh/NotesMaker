package com.example.notesmaker.Adapter

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.notesmaker.Activity.MainActivity
import com.example.notesmaker.Activity.UpdateNotes
import com.example.notesmaker.Helper.NotesDatabaseHelper
import com.example.notesmaker.R
import com.example.notesmaker.dataclass.Notes

class NotesAdapter(private var notes:List<Notes>,context: Context,private var onItemClickListnerrecyclerview: OnItemClickListnerrecyclerview) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>(){


    private val db : NotesDatabaseHelper = NotesDatabaseHelper(context)
    class NoteViewHolder(itemView : View,onItemClickListnerrecyclerview: OnItemClickListnerrecyclerview) : RecyclerView.ViewHolder(itemView){
        val titletextview :  TextView  = itemView.findViewById(R.id.titletextview)
        val contenttextview : TextView = itemView.findViewById(R.id.contenttextview)
        val deletebtn : ImageView = itemView.findViewById(R.id.deletebtn)

//        init {
//            itemView.setOnClickListener{
//                onItemClickListnerrecyclerview.Onitemclick(adapterPosition)
//            }
//
//            itemView.setOnLongClickListener {
//                onItemClickListnerrecyclerview.OnLongClick(adapterPosition)
//                return@setOnLongClickListener true
//            }
//        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val views = LayoutInflater.from(parent.context).inflate(R.layout.notes_item,parent,false)
        return NoteViewHolder(views,onItemClickListnerrecyclerview)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
       val recyclerNotes = notes[position]
        holder.titletextview.text = recyclerNotes.title
        holder.contenttextview.text = recyclerNotes.content

        holder.itemView.setOnClickListener{
            onItemClickListnerrecyclerview.Onitemclick(position)

            val intent = Intent(holder.itemView.context,UpdateNotes::class.java).apply {
                putExtra("Name_id",recyclerNotes.id)
                Log.d(ContentValues.TAG, "inside a inetent")
                putExtra("name_title",recyclerNotes.title)
                putExtra("name_content",recyclerNotes.content)
            }
            holder.itemView.context.startActivity(intent)
        }


        holder.deletebtn.setOnClickListener{
            db.deleteTask(recyclerNotes.id)
            refreshingNotes(db.getAllNotes())
        }

    }

    override fun getItemCount(): Int {
       return notes.size
    }



    //refreshing notes
    fun refreshingNotes(newNotes : List<Notes>){
        notes = newNotes
        notifyDataSetChanged()
    }


    interface OnItemClickListnerrecyclerview{
        fun Onitemclick(position: Int)
        fun OnLongClick(position: Int)
    }


}