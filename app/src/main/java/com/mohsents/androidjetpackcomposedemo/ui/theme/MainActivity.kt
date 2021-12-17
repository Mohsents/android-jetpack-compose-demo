package com.mohsents.androidjetpackcomposedemo.ui.theme

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApp()
        }
    }

    /*
    * By making small reusable components it's easy to build up a library of UI elements
    * used in your app. Each one is responsible for one small part of the screen
    * and can be edited independently.
    */
    @Composable
    fun MyApp(names: List<String> = listOf("World", "Compose")) {
        Column {
            for (name in names) {
                Greeting(name = name)
            }
        }
    }

    @Composable
    private fun Greeting(name: String) {
        // Preserve state across recompositions, remember the mutable state using remember().
        // remember() is used to guard against recomposition, so the state is not reset.
        // Each Greeting maintains its own expanded state, because they belong to different UI elements.
        val expanded = remember { mutableStateOf(false) }
        // Add extra padding to the column based on expanded state.
        val extraPadding = if (expanded.value) 48.dp else 0.dp
        // Using Surface() to change the background of elements.
        Surface(
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            // Each child inside of a Row() will be placed horizontally.
            // To add multiple modifiers to an element, you simply chain them.
            Row(modifier = Modifier.padding(24.dp)) {
                Column(
                    Modifier.weight(1f).padding(bottom = extraPadding)) {
                    // Modifiers tell a UI element how to lay out, display, or behave within its parent layout.
                    Text(text = "Hello, ")
                    Text(text = name)
                }
                OutlinedButton(
                    onClick = { expanded.value = !expanded.value }
                ) {
                    Text(text = if (expanded.value) "Show less" else "Show more")
                }
            }
        }
    }

    @Preview(widthDp = 320)
    @Composable
    private fun PreviewGreeting() {
        AndroidJetpackComposeDemoTheme {
            MyApp()
        }
    }
}