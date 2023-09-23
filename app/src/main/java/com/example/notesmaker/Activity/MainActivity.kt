package com.example.notesmaker.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesmaker.Adapter.NotesAdapter
import com.example.notesmaker.Helper.NotesDatabaseHelper
import com.example.notesmaker.databinding.ActivityMainBinding
import com.example.notesmaker.dataclass.Notes

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var noteshelper : NotesDatabaseHelper
    private lateinit var adapterview: NotesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteshelper = NotesDatabaseHelper(this)
        adapterview = NotesAdapter(noteshelper.getAllNotes(),this)

        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapterview



        binding.addbtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent : Intent = Intent(this@MainActivity, Addnotesactivity::class.java)
                startActivity(intent)
            }
        })
    }


    override fun onResume() {
        super.onResume()
        adapterview.refreshingNotes(noteshelper.getAllNotes())
    }
}