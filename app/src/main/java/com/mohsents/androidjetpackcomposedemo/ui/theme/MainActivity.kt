package com.mohsents.androidjetpackcomposedemo.ui.theme

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Greeting(name = "Android")
        }
    }

    @Composable
    private fun Greeting(name: String) {
        // Using Surface() to change the background of elements.
        Surface(color = MaterialTheme.colors.primary) {
            Text(text = "Hello $name!")
        }
    }

    @Preview
    @Composable
    private fun PreviewGreeting() {
        Greeting(name = "Android")
    }
}