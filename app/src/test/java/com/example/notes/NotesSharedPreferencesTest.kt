package com.example.notes

import android.content.Context
import android.content.SharedPreferences
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.Mockito.`when` as whenever
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NotesSharedPreferencesTest {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesEditor: SharedPreferences.Editor
    private lateinit var context: Context

    @Before
    fun setUp() {
        context = mock(Context::class.java)
        sharedPreferences = mock(SharedPreferences::class.java)
        sharedPreferencesEditor = mock(SharedPreferences.Editor::class.java)

        whenever(context.getSharedPreferences("NotesSharedPreferences", Context.MODE_PRIVATE))
            .thenReturn(sharedPreferences)
        whenever(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)
    }

    @Test
    fun saveNote_savesNoteCorrectly() {
        val note = Note("Test Note", "This is the content")
        val noteKey = "note_${note.name}"

        NotesSharedPreferences.saveNote(context, note)

        verify(sharedPreferencesEditor).putString(noteKey, note.content)
        verify(sharedPreferencesEditor).apply()
    }
}