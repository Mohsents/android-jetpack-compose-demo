package com.mohsents.androidjetpackcomposedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohsents.androidjetpackcomposedemo.ui.theme.AndroidJetpackComposeDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppLayout()
        }
    }

    @Composable
    private fun AppLayout() {
        // Scaffold() is building block for creating composables on top of Material component specs.
        Scaffold(
            topBar = {
                TopAppBar(title = {
                    Text(text = "Main Screen")
                }, actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Favorite"
                        )
                    }
                })
            }, bottomBar = {
                BottomNavigation {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = "Home"
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "Info"
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            }
        ) { innerPadding ->
            AppLayoutBody(
                Modifier
                    .padding(innerPadding)
                    .padding(8.dp)
            )
        }
    }

    @Composable
    private fun AppLayoutBody(modifier: Modifier = Modifier) {
        Column(modifier = modifier) {
            Text(text = "Hi there!")
            Text(text = "Thanks for going through the Layouts codelab")
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun AppLayoutPreview() {
        AppLayout()
    }

    /**
     * By specifying [modifier] parameter, enabling the caller to modify composable properties.
     * By convention, the modifier is specified as the first optional parameter of a function.
     * This enables you to specify a modifier on a composable without having to name all parameters.
     */
    @Composable
    fun PhotographerCard(modifier: Modifier = Modifier) {
        Row(modifier = Modifier
            .padding(8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(MaterialTheme.colors.surface)
            .clickable { }
            .padding(16.dp)) {
            Surface(
                modifier = modifier.size(50.dp),
                shape = CircleShape,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
            ) {

            }
            Column(
                modifier = modifier
                    .padding(start = 8.dp)
                    .align(Alignment.CenterVertically)
            )
            {
                Text("Alfred Sisley", fontWeight = FontWeight.Bold)
                // LocalContentAlpha is defining opacity level of its children
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text("3 minutes ago", style = MaterialTheme.typography.body2)
                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun PhotographerCardPreview() {
        AndroidJetpackComposeDemoTheme {
            PhotographerCard()
        }
    }
}
