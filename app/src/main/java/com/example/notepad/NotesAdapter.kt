package com.example.notepad

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteDatabase.*
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.notepad.activities.MainActivity
import com.example.notepad.activities.NewNoteActivity
import com.google.gson.Gson

class NotesAdapter(private val notesList: ArrayList<NotesListItem>, private val context: Context) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    var sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.title)
        val noteContent: TextView = itemView.findViewById(R.id.note_content);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note: NotesListItem = notesList[position]
        holder?.title.text = note.title
        holder?.noteContent.text = note.noteContent

        holder.itemView.setOnClickListener {

            var editIntent = Intent(context, NewNoteActivity::class.java)
            editIntent.putExtra("edit_position", position)
            context.startActivity(editIntent)
            (context as MainActivity).finish()
        }

        holder.itemView.setOnLongClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Delete?")
            builder.setMessage("Do you want to delete this note?")

            builder.setNegativeButton("No") { _,_ ->
                return@setNegativeButton
            }

            builder.setPositiveButton("Yes") { _,_ ->
                notesList.remove(notesList[position])
                notifyDataSetChanged()
                saveArrayList(notesList)
                return@setPositiveButton
            }

            builder.show()
            true
        }
    }

    private fun saveArrayList(notesList: ArrayList<NotesListItem>) {
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json: String? = gson.toJson(notesList)
        editor.putString("100", json)
        editor.apply()
    }
}