package com.example.notes

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class DeleteNoteActivity : AppCompatActivity() {

    private lateinit var noteEditText: EditText
    private lateinit var deleteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Making the ListView and New Note button invisible, only for DeleteNoteActivity
        findViewById<ListView>(R.id.notesListView).visibility = View.GONE
        findViewById<Button>(R.id.newNote).visibility = View.GONE

        // Making the EditText and Delete button visible for note deletion
        noteEditText = findViewById(R.id.editTextText)
        deleteButton = findViewById(R.id.deleteNote)
        noteEditText.visibility = View.VISIBLE
        deleteButton.visibility = View.VISIBLE

        noteEditText.hint = "Enter note name to delete"
        deleteButton.text = "Delete Note"

        deleteButton.setOnClickListener {
            val nameToDelete = noteEditText.text.toString().trim()
            if (nameToDelete.isNotEmpty()) {
                NotesSharedPreferences.deleteNote(this, nameToDelete)
                finish() // Return to MainActivity to refresh list
            }
        }
    }
}