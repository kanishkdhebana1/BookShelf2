package com.example.bookshelf2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.bookshelf2.ui.theme.BookShelfTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BookShelfTheme(darkTheme = true) {
                BookShelfApp()
            }
        }
    }
}

