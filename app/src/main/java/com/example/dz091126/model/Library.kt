package com.example.dz091126.model

class Library {
    private val books = mutableListOf<Book>()
    private var nextId = 1

    fun addBook(title: String, author: String): Book {
        val book = Book(nextId++, title, author)
        books.add(book)
        return book
    }

    fun borrowBook(id: Int): Boolean {
        val book = books.find { it.id == id } ?: return false
        if (!book.isAvailable) return false
        book.isAvailable = false
        return true
    }

    fun returnBook(id: Int): Boolean {
        val book = books.find { it.id == id } ?: return false
        if (book.isAvailable) return false
        book.isAvailable = true
        return true
    }

    fun getAllBooks(): List<Book> = books.toList()
}