package com.example.day4task1booksfilterapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val authorName = findViewById<TextInputLayout>(R.id.authorInputLayout)
        val language = findViewById<TextInputLayout>(R.id.languageInputLayout)
        val filterButton = findViewById<Button>(R.id.filterButton)
        val bookCount = findViewById<TextView>(R.id.resultCountView)
        val booksView = findViewById<TextView>(R.id.booksView)

        filterButton.setOnClickListener {
            val myApplication = application as MyApplication
            val httpApiService = myApplication.httpApiService

            CoroutineScope(Dispatchers.IO).launch{
                val decodedJsonResult = httpApiService.getBooks()
                val author = authorName.editText?.text?.toString()
                val selectedLanguage = language.editText?.text?.toString()
                var count = 0;
                var booksList = mutableListOf<String>()
                decodedJsonResult.forEach {
                    if(it.author == author || it.language == selectedLanguage) {
                        count++
                        if (count <= 3){
                            booksList.add(it.title)
                        }

                    }
                }
                var books = ""
                booksList.forEach {
                    books = "Result: $it\n" + books
                }
                withContext(Dispatchers.Main){
                    bookCount.text = "Results: $count"
                    booksView.text = "$books"
                }
            }
        }
    }

}