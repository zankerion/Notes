package com.example.notes

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
class AddNoteActivity : AppCompatActivity() {

    private lateinit var noteNameEditText: EditText
    private lateinit var noteContentEditText: EditText
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        noteNameEditText = findViewById(R.id.editTextNoteName)
        noteContentEditText = findViewById(R.id.editTextNoteContent)
        saveButton = findViewById(R.id.buttonSaveNote)

        val noteNameToEdit = intent.getStringExtra("NOTE_NAME")
        val noteContentToEdit = intent.getStringExtra("NOTE_CONTENT")

        if (!noteNameToEdit.isNullOrEmpty() && !noteContentToEdit.isNullOrEmpty()) {
            noteNameEditText.setText(noteNameToEdit)
            noteContentEditText.setText(noteContentToEdit)
        }

        setupSaveButton()
    }

    private fun setupSaveButton() {
        saveButton.setOnClickListener {
            val newNoteName = noteNameEditText.text.toString().trim()
            val newNoteContent = noteContentEditText.text.toString().trim()

            if (newNoteName.isNotEmpty() && newNoteContent.isNotEmpty()) {
                val newNote = Note(newNoteName, newNoteContent)
                NotesSharedPreferences.saveNote(this, newNote)
                finish()
            } else {
                Toast.makeText(this, "Both title and content are required.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

