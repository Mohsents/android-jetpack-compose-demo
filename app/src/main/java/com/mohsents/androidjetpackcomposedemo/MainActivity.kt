package com.mohsents.androidjetpackcomposedemo

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohsents.androidjetpackcomposedemo.ui.theme.AndroidJetpackComposeDemoTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidJetpackComposeDemoTheme {
                MyApp()
            }
        }
    }

    /*
    * By making small reusable components it's easy to build up a library of UI elements
    * used in your app. Each one is responsible for one small part of the screen
    * and can be edited independently.
    */
    @Composable
    fun MyApp(names: List<String> = listOf("World", "Compose")) {
        /*
        * The remember() function works only as long as the composable is kept in the Composition.
        * The source of truth belongs to whoever creates and controls that state.
        * Instead of using remember() you can use rememberSaveable().
        * This will save each state surviving configuration changes (such as rotations) and process death.
        */
        var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

        if (shouldShowOnboarding) {
            OnboardingScreen { shouldShowOnboarding = false }
        } else {
            Greetings()
        }
    }

    @Composable
    private fun Greetings(names: List<String> = List(1000) { "$it" }) {
        /*
         * LazyColumn doesn't recycle its children like RecyclerView.
         * It emits new Composables as you scroll through it and is still performant,
         * as emitting Composables is relatively cheap compared to instantiating Android Views.
         */
        LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
            items(items = names) { name ->
                Greeting(name = name)
            }
        }
    }

    @Composable
    private fun Greeting(name: String) {
        // Preserve state across recompositions, remember the mutable state using remember().
        // remember() is used to guard against recomposition, so the state is not reset.
        // Each Greeting maintains its own expanded state, because they belong to different UI elements.
        var expanded by rememberSaveable { mutableStateOf(false) }
        // Add extra padding with animation to the column based on expanded state.
        val extraPadding by animateDpAsState(
            if (expanded) 48.dp else 0.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessMedium
            )
        )
        // Using Surface() to change the background of elements.
        Surface(
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            // Each child inside of a Row() will be placed horizontally.
            // To add multiple modifiers to an element, you simply chain them.
            Row(modifier = Modifier.padding(24.dp)) {
                Column(
                    Modifier
                        .weight(1f)
                        // Ensure that padding greater than zero, otherwise app crash.
                        .padding(bottom = extraPadding.coerceAtLeast(0.dp))
                ) {
                    // Modifiers tell a UI element how to lay out, display, or behave within its parent layout.
                    Text(text = "Hello, ")
                    Text(
                        text = name,
                        // Modify a predefined style by using the copy() function.
                        // This way if you need to change the font family or any other attribute of h4,
                        // you don't have to worry about the small deviations.
                        style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraBold)
                    )
                }
                OutlinedButton(
                    onClick = { expanded = !expanded }
                ) {
                    Text(text = if (expanded) "Show less" else "Show more")
                }
            }
        }
    }

    @Composable
    fun OnboardingScreen(onContinueClicked: () -> Unit) {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Welcome to the Basics Codelab!")
                Button(
                    modifier = Modifier.padding(vertical = 24.dp),
                    onClick = onContinueClicked
                ) {
                    Text("Continue")
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun OnboardingPreview() {
        AndroidJetpackComposeDemoTheme {
            OnboardingScreen(onContinueClicked = {}) // Do nothing on click.
        }
    }

    @Preview(
        showBackground = true,
        uiMode = UI_MODE_NIGHT_YES,
        name = "DefaultPreviewDark"
    )
    @Preview()
    @Composable
    private fun PreviewGreeting() {
        AndroidJetpackComposeDemoTheme {
            Greetings()
        }
    }
}