package com.example.dz091126

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dz091126.model.Library
import com.example.dz091126.users.*

class MainActivity : AppCompatActivity() {

    private val library = Library()

    private val borrower: BookBorrower = LibraryBorrower(library)
    private val adder: BookAdder = LibraryBookAdder(library)

    private val reader = Reader("Петр", borrower)
    private val admin = Administrator("Админ", adder)

    private lateinit var tvRole: TextView
    private lateinit var adminPanel: View
    private lateinit var booksContainer: LinearLayout
    private lateinit var etTitle: EditText
    private lateinit var etAuthor: EditText
    private var isAdminMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvRole = findViewById(R.id.tvRole)
        adminPanel = findViewById(R.id.adminPanel)
        booksContainer = findViewById(R.id.booksContainer)
        etTitle = findViewById(R.id.etTitle)
        etAuthor = findViewById(R.id.etAuthor)

        admin.addBook("Мастер и Маргарита", "М. Булгаков")
        admin.addBook("1984", "Дж. Оруэлл")

        findViewById<Button>(R.id.btnReader).setOnClickListener {
            isAdminMode = false; updateMode()
        }
        findViewById<Button>(R.id.btnAdmin).setOnClickListener {
            isAdminMode = true; updateMode()
        }
        findViewById<Button>(R.id.btnAddBook).setOnClickListener {
            val title = etTitle.text.toString().trim()
            val author = etAuthor.text.toString().trim()
            if (title.isNotEmpty() && author.isNotEmpty()) {
                admin.addBook(title, author)
                etTitle.text.clear()
                etAuthor.text.clear()
                renderBooks()
            } else {
                Toast.makeText(this, "Заполните поля", Toast.LENGTH_SHORT).show()
            }
        }

        updateMode()
    }

    @SuppressLint("SetTextI18n")
    private fun updateMode() {
        if (isAdminMode) {
            tvRole.text = "Роль: Администратор (${admin.name})"
            adminPanel.visibility = View.VISIBLE
        } else {
            tvRole.text = "Роль: Читатель (${reader.name})"
            adminPanel.visibility = View.GONE
        }
        renderBooks()
    }

    @SuppressLint("SetTextI18n")
    private fun renderBooks() {
        booksContainer.removeAllViews()
        for (book in library.getAllBooks()) {
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                setPadding(0, 8, 0, 8)
            }
            row.addView(TextView(this).apply {
                text = "${book.title} — ${book.author} " +
                        "[${if (book.isAvailable) "доступна" else "выдана"}]"
                layoutParams = LinearLayout.LayoutParams(0, -2, 1f)
            })

            val actionBtn = Button(this).apply {
                if (isAdminMode) {
                    visibility = View.GONE
                } else {
                    text = if (book.isAvailable) "Взять" else "Вернуть"
                    setOnClickListener {
                        val ok = if (book.isAvailable) reader.borrowBook(book.id)
                        else reader.returnBook(book.id)
                        Toast.makeText(
                            this@MainActivity,
                            if (ok) "Готово" else "Ошибка",
                            Toast.LENGTH_SHORT
                        ).show()
                        renderBooks()
                    }
                }
            }
            row.addView(actionBtn)
            booksContainer.addView(row)
        }
    }
}