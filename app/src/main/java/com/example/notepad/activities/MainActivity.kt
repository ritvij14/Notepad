package com.example.notepad.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notepad.NotesAdapter
import com.example.notepad.NotesListItem
import com.example.notepad.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var gridLayoutButton: ImageButton
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var logoutButton: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var notesRecycler: RecyclerView
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var notes: ArrayList<NotesListItem>
    private lateinit var sharedPreferences: SharedPreferences

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayoutButton = grid_layout_button
        floatingActionButton = add_note_button
        logoutButton = logout_button
        firebaseAuth = FirebaseAuth.getInstance()
        notesRecycler = notes_list
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = sharedPreferences.edit()

        if(sharedPreferences.getBoolean("list", true)) {
            notesRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
            gridLayoutButton.setImageResource(R.drawable.ic_baseline_grid_on_24)
        } else {
            notesRecycler.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
            gridLayoutButton.setImageResource(R.drawable.ic_round_list_alt_24)
        }

        notes = ArrayList<NotesListItem>()

        if (sharedPreferences.contains("100")) {
            notes = getArrayList()
        }

        //Dummy members for arraylist
        /*for (i in 1..2) {
            notes.add(NotesListItem("Dummy Title", "Lorem Ipsum dummy text"))
        }*/

        notesAdapter = NotesAdapter(notes, this)

        notesRecycler.adapter = notesAdapter

        gridLayoutButton.setOnClickListener {
            if(sharedPreferences.getBoolean("list", true)) {
                notesRecycler.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
                gridLayoutButton.setImageResource(R.drawable.ic_round_list_alt_24)
                editor.putBoolean("list", false).apply()
            } else {
                notesRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                gridLayoutButton.setImageResource(R.drawable.ic_baseline_grid_on_24)
                editor.putBoolean("list", true).apply()
            }
        }

        floatingActionButton.setOnClickListener {
            startActivity(Intent(this, NewNoteActivity::class.java))
            finish()
        }

        logoutButton.setOnClickListener {v ->
            firebaseAuth.signOut()
            if (firebaseAuth.currentUser == null) {
                finish()
            }
        }
    }

    override fun onBackPressed() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit?")
        builder.setMessage("Do you want to exit? You will stay logged in.")

        builder.setPositiveButton("Yes") { _, _ ->
                val exitIntent = Intent(this, LoginActivity::class.java)
                exitIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                exitIntent.putExtra("EXIT", true)
                startActivity(exitIntent)
        }

        builder.setNeutralButton("Cancel") { _, _ ->
            return@setNeutralButton
        }
        builder.show()
    }

    private fun getArrayList(): ArrayList<NotesListItem> {
        val gson = Gson()
        val json: String? = sharedPreferences.getString("100", null)
        val type = object: TypeToken<ArrayList<NotesListItem>>() {}.type
        return gson.fromJson(json, type)
    }
}