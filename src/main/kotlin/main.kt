package ru.netology;

open class Note(
    var id: Int,
    val title: String = "",
    val text: String,
    val noteId: Int = 0,
    var isDeleted: Boolean = false,
    val privacy: Int = 0,
    val commentPrivacy: Int = 0,
) {
    override fun toString(): String {
        return "\nid=$id title=$title text=$text privacy=$privacy commentPrivacy=$commentPrivacy"
    }
}

class Comment(
    id: Int, text: String, noteId: Int
) : Note(id, "", text, noteId) {
    override fun toString(): String {
        return "\nid=$id text=$text noteId=$noteId isDeleted=$isDeleted privacy=$privacy commentPrivacy=$commentPrivacy"
    }
}

class NoteService<T : Note> {
    private var elements = mutableListOf<T>()
    private var lastId = 0

    fun add(element: T): Int {
        element.id = ++lastId
        elements.add(element)
        return elements.last().id
    }

    fun edit(element: T): Int {
        for ((i, note) in elements.withIndex()) {
            if (note.id == element.id && !note.isDeleted) {
                elements[i] = element
                return 1
            }
        }
        return 180
    }

    fun get(needElementIds: MutableList<Int>): MutableList<T>? {
        val resultsNotes = mutableListOf<T>()
        for (note in elements) {
            for (needId in needElementIds) {
                if (note.id == needId) {
                    resultsNotes.add(note)
                }
            }
        }
        return resultsNotes
    }

    fun getById(needElementId: Int): T? {
        val needId = mutableListOf(needElementId)
        val result = get(needId)
        if (!result.isNullOrEmpty()) {
            return result[0]
        }
        return null
    }


    fun delete(id: Int): Int {
        for ((index, note) in elements.withIndex()) {
            if (note.id == id) {
                elements.removeAt(index)
                return 1
            }
        }
        return 180
    }

    fun deleteComment(id: Int): Int {
        val comment = getById(id)
        if (comment != null) {
            comment.isDeleted = true
            return 1
        } else {
            return 180
        }
    }

    fun restoreComment(id: Int): Int {
        val comment = getById(id)
        if (comment != null && comment.isDeleted == true) {
            comment.isDeleted = false
            return 1
        } else {
            return 183
        }
    }

    fun getComments(id: Int): MutableList<T>? {
        val resultsComments = mutableListOf<T>()
        for (comment in elements) {
            if (comment.noteId == id && !comment.isDeleted) {
                resultsComments.add(comment)
            }
        }
        return resultsComments
    }

    fun clear() {
        elements = mutableListOf<T>()
        lastId = 0
    }
}

fun main() {
    //объявляем наш сервис для заметок
    val noteService = NoteService<Note>()
    val commentService = NoteService<Comment>()

    //добавляем наши начальные данные заметки
    noteService.add(Note(0, "Первая заметка", "Текст первой заметки"))
    noteService.add(Note(0, "Вторая заметка", "Текст второй заметки"))
    noteService.add(Note(0, "Третья заметка", "Текст третьей заметки"))

    //get
    println(noteService.get(mutableListOf(10)))

    //getById
    println("Достаем заметки с id=2")
    println(noteService.getById(2))

    //delete
    println("Удаляем заметку с id=1")
    println(noteService.delete(1))

    //проверяем, удалили или нет
    println("Проверяем удаление заметки с id=1")
    println(noteService.getById(1))

    //Редактируем запись с id = 2
    println("Редактируем запись с id=2")
    println(noteService.edit(Note(2, "Вторая заметка плюс", "Текст второй заметки плюс")))
    println(noteService.getById(2))

    /*******комментарии********/
    //добавляем комментарии
    commentService.add(Comment(0, "Текст первого комментарий к 1 записи", 1))
    commentService.add(Comment(0, "Текст второго комментария к 1 записи", 1))

    commentService.add(Comment(0, "Текст первого комментарий к 2 записи", 2))

    //проверяем удаление
    commentService.deleteComment(1)
    println(commentService.getById(1))

    //проверяем попытку редактирования удаленного комментария
    println(commentService.edit(Comment(1, "Текст первого комментарий к 2 записи пдюс", 2)))

    //восстанавливаем комментарий
    commentService.restoreComment(1)
    println(commentService.getById(1))

    //забираем все комментарии к заметке=1
    println(commentService.getComments(1))
}