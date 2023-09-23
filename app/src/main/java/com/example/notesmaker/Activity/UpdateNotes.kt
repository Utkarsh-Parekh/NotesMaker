package com.example.notesmaker.Activity

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.notesmaker.Helper.NotesDatabaseHelper
import com.example.notesmaker.R
import com.example.notesmaker.databinding.ActivityMainBinding
import com.example.notesmaker.databinding.ActivityUpdateNotesBinding
import com.example.notesmaker.dataclass.Notes

class UpdateNotes : AppCompatActivity() {
    lateinit var binding: ActivityUpdateNotesBinding
    lateinit var database:NotesDatabaseHelper
    var noteid:Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_notes)

        binding = ActivityUpdateNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = NotesDatabaseHelper(this)
        noteid = intent.getIntExtra("Name_id",-1)
        Log.d(TAG, "onCreate: Noteid is " + noteid)
        if (noteid == -1) {
            finish()
            return
        }

        val updatednotes = database.getNoteById(noteid)
        binding.updatenotesedittext.setText(updatednotes.title)
        binding.updatenotesdescription.setText(updatednotes.content)


        binding.updatesavebutton.setOnClickListener{
            val newtitle = binding.updatenotesedittext.text.toString()
            Log.d(TAG, "onCreate: New title is " + newtitle)
            val newcontent = binding.updatenotesdescription.text.toString()
            Log.d(TAG, "onCreate: New content is " + newcontent)

            val updatethenote = Notes(noteid,newtitle,newcontent)
            database.updateNotes(updatethenote)
            finish()
            Toast.makeText(this,"Note Updated Successfully",Toast.LENGTH_SHORT).show()
        }
    }



}