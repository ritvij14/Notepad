package com.example.notepad.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import com.example.notepad.R
import kotlinx.android.synthetic.main.activity_new_note_actvity.*

class NewNoteActivity : AppCompatActivity() {

    private lateinit var back: ImageButton
    private lateinit var saveNote: ImageButton
    private lateinit var title: EditText
    private lateinit var note: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_note_actvity)

        back = back_button
        saveNote = save_note_button
        title = title_content
        note = note_content

        back.setOnClickListener { v ->
            var returnIntent: Intent = Intent(this, MainActivity::class.java)
            setResult(Activity.RESULT_CANCELED, returnIntent)
            finish()
        }

        saveNote.setOnClickListener { v ->
            var returnIntent: Intent = Intent(this, MainActivity::class.java)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }

    }
}