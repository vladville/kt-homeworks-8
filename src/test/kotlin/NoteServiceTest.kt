package ru.netology;

import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class NoteServiceTest {

    /*@Before
    fun clearBeforeTest() {
        NoteService.clear()
    }*/
    @Test
    fun NoteAdd() {
        val service = NoteService
        val result = service.add(Note(0, "Первая заметка", "Текст первой заметки"))

        assertEquals(1, result.id)
    }

}