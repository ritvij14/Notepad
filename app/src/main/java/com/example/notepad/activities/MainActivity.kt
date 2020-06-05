package com.example.notepad.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.NotesAdapter
import com.example.notepad.NotesListItem
import com.example.notepad.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var gridLayoutButton: ImageButton
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var notesRecycler: RecyclerView
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var checkBoxView: View
    private lateinit var checkBox: CheckBox
    private var listType: Boolean = false
    val SAVE_NOTE = 1

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gridLayoutButton = grid_layout_button
        floatingActionButton = add_note_button
        firebaseAuth = FirebaseAuth.getInstance()
        notesRecycler = notes_list
        notesRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        checkBoxView = View.inflate(this, R.layout.checkbox, null)
        checkBox = checkBoxView.findViewById(R.id.check_box)

        val notes = ArrayList<NotesListItem>()

        for (i in 1..10) {
            notes.add(NotesListItem("Dummy Title", "Lorem Ipsum dummy text"))
        }

        notesAdapter = NotesAdapter(notes)

        notesRecycler.adapter = notesAdapter

        gridLayoutButton.setOnClickListener { v ->
            if(!listType) {
                notesRecycler.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL, false)
                gridLayoutButton.setImageResource(R.drawable.ic_round_list_alt_24)
                listType = true
            } else {
                notesRecycler.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                gridLayoutButton.setImageResource(R.drawable.ic_baseline_grid_on_24)
                listType = false
            }
        }

        floatingActionButton.setOnClickListener { v ->
            startActivityForResult(Intent(this, NewNoteActivity::class.java), SAVE_NOTE)
        }

        checkBox.text = "Keep me logged in"
    }

    override fun onBackPressed() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit?")
        builder.setMessage("Do you want to logout and exit?").setView(checkBoxView)

        builder.setPositiveButton("Yes") { _, _ ->
            firebaseAuth.signOut()
            if(firebaseAuth.currentUser == null) {
                var exitIntent: Intent = Intent(this, LoginActivity::class.java)
                exitIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                exitIntent.putExtra("EXIT", true)
                startActivity(exitIntent)
            }
        }

        builder.setNegativeButton("No") { _, _ ->
            val parentView = checkBoxView.parent as ViewGroup
            parentView.removeView(checkBoxView)
            return@setNegativeButton
        }

        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SAVE_NOTE) {
            if (resultCode == Activity.RESULT_OK) {
                Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
            }
        }
    }
}