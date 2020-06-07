package com.example.notepad.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import com.example.notepad.NotesListItem
import com.example.notepad.R
import com.example.notepad.databinding.ActivityNewNoteBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NewNoteActivity : AppCompatActivity() {

    private lateinit var activityNewNoteBinding: ActivityNewNoteBinding
    private lateinit var title: String
    private lateinit var content: String
    private var pos: Int = 0
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityNewNoteBinding = ActivityNewNoteBinding.inflate(layoutInflater)
        val view = activityNewNoteBinding.root
        setContentView(view)

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)

        if(intent.hasExtra("edit_position")) {
            pos = intent.getIntExtra("edit_position", 0)
            activityNewNoteBinding.titleContent.setText(getArrayList()!![pos].title)
            activityNewNoteBinding.noteContent.setText(getArrayList()!![pos].noteContent)
        }

        activityNewNoteBinding.backButton.setOnClickListener {

            finish()
        }

        activityNewNoteBinding.saveNoteButton.setOnClickListener { v ->

            if (activityNewNoteBinding.noteContent.text.isEmpty()) {
                Toast.makeText(this, "Please add content for the note", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(activityNewNoteBinding.titleContent.text.isEmpty()) {
                title = ""
            } else {
                title = activityNewNoteBinding.titleContent.text.toString()
            }

            content = activityNewNoteBinding.noteContent.text.toString()

            if (intent.hasExtra("edit_position")) {
                saveEdit(pos)
            } else {
                var list: ArrayList<NotesListItem>? = ArrayList()
                if(getArrayList() != null) {
                    list = getArrayList()
                }
                list!!.add(NotesListItem(title, content))
                saveArrayList(list!!)
            }

            val returnIntent = Intent(this, MainActivity::class.java)
            startActivity(returnIntent)
            finish()
        }
    }

    private fun getArrayList(): ArrayList<NotesListItem>? {
        val gson = Gson()
        val json: String? = sharedPreferences.getString("100", null)
        val type = object:TypeToken<ArrayList<NotesListItem>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun saveEdit(position: Int) {
        val list: ArrayList<NotesListItem>? = getArrayList()
        list!![position].title = title
        list!![position].noteContent = content
        saveArrayList(list)
    }

    private fun saveArrayList(notesList: ArrayList<NotesListItem>) {
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json: String? = gson.toJson(notesList)
        editor.putString("100", json)
        editor.apply()
    }
}