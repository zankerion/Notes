package com.example.notes

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import android.app.AlertDialog
class MainActivity : AppCompatActivity() {

    private lateinit var notesListView: ListView
    private lateinit var newNoteButton: Button
    private var notesList: MutableList<String> = mutableListOf()
    private fun showNoteActionsDialog(note: Note) {
        val options = arrayOf("Edit", "Delete")
        AlertDialog.Builder(this)
            .setTitle("Select an Option")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> editNote(note)
                    1 -> deleteNote(note)
                }
                dialog.dismiss()
            }
            .show()
    }

    private fun editNote(note: Note) {
        // Start AddNoteActivity with intention to edit the selected note
        val editIntent = Intent(this, AddNoteActivity::class.java).apply {
            putExtra("NOTE_NAME", note.name)
            putExtra("NOTE_CONTENT", note.content)
        }
        startActivity(editIntent)
    }

    private fun deleteNote(note: Note) {
        // Delete selected note from SharedPreferences and refresh list
        NotesSharedPreferences.deleteNote(this, note.name)
        loadNotes()  // Call loadNotes here to refresh the ListView
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newNoteButton = findViewById(R.id.newNote)
        notesListView = findViewById(R.id.notesListView)
        notesListView.setOnItemClickListener { parent, view, position, id ->
            val selectedNote = NotesSharedPreferences.getNotes(this)[position]
            showNoteActionsDialog(selectedNote)
        }

        newNoteButton.setOnClickListener {
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadNotes()


    }

    private fun loadNotes() {
        // Retrieve the notes from SharedPreferences
        val notesFromPrefs = NotesSharedPreferences.getNotes(this)
        // Extract the note names and sort them, or sort by other criteria if needed
        notesList.clear()
        notesList.addAll(notesFromPrefs.map { "${it.name}\n${it.content}" }.sorted())
        val notesAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, notesList)
        notesListView.adapter = notesAdapter
    }
}