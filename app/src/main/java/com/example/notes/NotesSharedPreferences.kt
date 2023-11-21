package com.example.notes

import android.content.Context

object NotesSharedPreferences {
    private const val PREF_NAME = "NotesSharedPreferences"
    private const val NOTE_PREFIX = "note_"

    fun saveNote(context: Context, note: Note) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString(NOTE_PREFIX + note.name, note.content)
            apply()
        }
    }

    fun getNotes(context: Context): List<Note> {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.all.mapNotNull { (key, value) ->
            if (key.startsWith(NOTE_PREFIX)) {
                Note(name = key.removePrefix(NOTE_PREFIX), content = value as String)
            } else null
        }
    }

    fun deleteNote(context: Context, noteName: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            remove(NOTE_PREFIX + noteName)
            apply()
        }
    }
}