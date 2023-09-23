package com.example.notesmaker.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.notesmaker.Helper.NotesDatabaseHelper
import com.example.notesmaker.databinding.ActivityAddnotesactivityBinding
import com.example.notesmaker.dataclass.Notes

class Addnotesactivity : AppCompatActivity() {

    lateinit var binding: ActivityAddnotesactivityBinding
    private lateinit var database: NotesDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddnotesactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = NotesDatabaseHelper(this)



        //Functionality to save the notes to database
        binding.savebutton.setOnClickListener(object : View.OnClickListener{

            override fun onClick(v: View?) {
                val notetitle = binding.notestitle.text.toString()
                val notescontent = binding.notesdescription.text.toString()

                //crete a object of Notes dataclass

                val note = Notes(0,notetitle,notescontent)

                //save the notes to database
                database.InsertNotes(note)
                finish()
                Toast.makeText(this@Addnotesactivity,"Notes saved Successfully",Toast.LENGTH_SHORT).show()
            }
        })



    }
}