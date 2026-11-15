package com.example.dz091126.users

import com.example.dz091126.model.Book
import com.example.dz091126.model.Library

// --- Интерфейсы поведения ---
interface BookBorrower {
    fun borrowBook(id: Int): Boolean
    fun returnBook(id: Int): Boolean
}

interface BookAdder {
    fun addBook(title: String, author: String): Book
}

// --- Делегаты ---

class LibraryBorrower(private val library: Library) : BookBorrower {
    override fun borrowBook(id: Int) = library.borrowBook(id)
    override fun returnBook(id: Int) = library.returnBook(id)
}

class LibraryBookAdder(private val library: Library) : BookAdder {
    override fun addBook(title: String, author: String) =
        library.addBook(title, author)
}