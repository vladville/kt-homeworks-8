package ru.netology

import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test


class NoteServiceTest {

    @Before
    fun clearBeforeTest() {
        val service = NoteService<Note>()
        service.clear()
    }

    @Test
    fun add() {
        val service = NoteService<Note>()
        service.add(Note(0, "Первая заметка", "Текст первой заметки"))
        val result = service.add(Note(0, "Первая заметка", "Текст первой заметки"))
        assertEquals(2, result)
    }

    @Test
    fun edit() {
        val service = NoteService<Note>()
        service.add(Note(0, "Первая заметка", "Текст первой заметки"))
        service.add(Note(0, "Вторая", "Текст первой заметки"))
        val result = service.edit(Note(2, "Вторая заметка плюс", "Текст второй заметки плюс"))
        assertEquals(1, result)
    }

    @Test
    fun get() {
        val service = NoteService<Note>()
        service.add(Note(0, "Первая заметка", "Текст первой заметки"))
        service.add(Note(0, "Вторая", "Текст первой заметки"))
        val result = service.get(mutableListOf(1))
        assertFalse(result.isNullOrEmpty());
    }

    @Test
    fun getById() {
        val service = NoteService<Note>()
        service.add(Note(0, "Первая заметка", "Текст первой заметки"))
        service.add(Note(0, "Вторая", "Текст первой заметки"))
        service.add(Note(0, "Вторая", "Текст первой заметки"))
        val result = service.getById(2)
        assertEquals(2, result?.id)
    }

    @Test
    fun delete() {
        val service = NoteService<Note>()
        service.add(Note(0, "Первая заметка", "Текст первой заметки"))
        service.add(Note(0, "Вторая", "Текст первой заметки"))
        service.add(Note(0, "Вторая", "Текст первой заметки"))
        val result = service.delete(2)
        assertEquals(1, result)
    }

    @Test
    fun deleteComment() {
        val service = NoteService<Comment>()
        service.add(Comment(0, "1 заметка", 1))
        service.add(Comment(0, "2 заметка", 1))
        service.add(Comment(0, "3 заметка", 1))
        val result = service.deleteComment(2)
        assertEquals(1, result)
    }

    @Test
    fun restoreComment() {
        val service = NoteService<Comment>()
        service.add(Comment(0, "1 заметка", 1))
        service.add(Comment(0, "2 заметка", 1))
        service.add(Comment(0, "3 заметка", 1))
        service.deleteComment(2)
        val result = service.restoreComment(2)
        assertEquals(1, result)
    }

    @Test
    fun getComments() {
        val noteService = NoteService<Note>()
        noteService.add(Note(0, "Первая заметка", "Текст первой заметки"))
        noteService.add(Note(0, "Вторая", "Текст первой заметки"))
        noteService.add(Note(0, "Вторая", "Текст первой заметки"))

        val service = NoteService<Comment>()
        service.add(Comment(0, "1 заметка", 1))
        service.add(Comment(0, "2 заметка", 1))
        service.add(Comment(0, "3 заметка", 1))

        val result = service.getComments(1)?.size
        assertEquals(3, result)
    }
}