package com.example.notesmaker.Helper

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import com.example.notesmaker.dataclass.Notes
import kotlin.math.log

class NotesDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "Notes.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "Allnotes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
    }

    override fun onCreate(db: SQLiteDatabase?) {
       val createtablequery = "CREATE TABLE $TABLE_NAME($COLUMN_ID INTEGER PRIMARY KEY,$COLUMN_TITLE TEXT,$COLUMN_CONTENT TEXT)"
        db?.execSQL(createtablequery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
       val droptablequery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(droptablequery)
        onCreate(db)
    }


    fun InsertNotes(notes: Notes){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,notes.title)
            put(COLUMN_CONTENT,notes.content)
        }
        db.insert(TABLE_NAME,null,values)
        db.close()
    }



    fun getAllNotes() : List<Notes>{
        val Notelist = mutableListOf<Notes>()
        val dbread = readableDatabase
        val queryread = "SELECT * FROM $TABLE_NAME"
        val cursor = dbread.rawQuery(queryread,null)


        while(cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val notesread = Notes(id, title, content)
            Notelist.add(notesread)

        }

        cursor.close()
        dbread.close()
        Log.d(TAG, "getAllNotes: " + Notelist.size)
        return Notelist

    }

    //code for update database on update
    fun updateNotes(notes: Notes){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE,notes.title)
            put(COLUMN_CONTENT,notes.content)
        }

        val whereclause  = "$COLUMN_ID"
        val whereargs = arrayOf(notes.id.toString())

        db.update(TABLE_NAME,values,COLUMN_ID + "=?", arrayOf(COLUMN_ID.toString()))
        db.close()
    }


    fun getNoteById(noteid : Int) : Notes{
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteid"
        val cursor = db.rawQuery(query,null)
        cursor.moveToFirst()
        Log.d(TAG, "getnotebyid : Noteid is " + noteid)
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
        val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

        cursor.close()
        db.close()
        return Notes(id,title,content)

    }


    fun deleteTask(id: Int){
        val databasedelete = writableDatabase
        val deleted = databasedelete.delete(TABLE_NAME, COLUMN_ID + "=?", arrayOf(id.toString()))
        databasedelete.close()
    }

}