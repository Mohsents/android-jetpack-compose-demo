package com.mohsents.androidjetpackcomposedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.mohsents.androidjetpackcomposedemo.ui.theme.AndroidJetpackComposeDemoTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidJetpackComposeDemoTheme {
                ScrollingList()
            }
        }
    }

    @Composable
    private fun MyOwnColumn(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    ) {
        Layout(
            modifier = modifier,
            content = content
        ) { measurables, constraints ->
            // Don't constrain child views further, measure them with given constraints
            // List of measured children
            val placeables = measurables.map { measurable ->
                // Measure each child
                measurable.measure(constraints)
            }

            // Track the y co-ord we have placed children up to
            var yPosition = 0

            // Set the size of the layout as big as it can
            layout(constraints.maxWidth, constraints.maxHeight) {
                // Place children in the parent layout
                placeables.forEach { placeable ->
                    // Position item on the screen
                    placeable.placeRelative(x = 0, y = yPosition)

                    // Record the y co-ord placed up to
                    yPosition += placeable.height
                }
            }
        }
    }

    private fun Modifier.firstBaselineToTop(
        firstBaselineToTop: Dp
    ) = this.then(
        layout { measurable, constraints ->
            val placeable = measurable.measure(constraints)

            // Check the composable has a first baseline
            check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
            val firstBaseline = placeable[FirstBaseline]

            // Height of the composable with padding - first baseline
            val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
            val height = placeable.height + placeableY
            layout(placeable.width, height) {
                // Where the composable gets placed
                // If you don't call placeRelative, the composable won't be visible.
                placeable.placeRelative(0, placeableY)
            }
        }
    )

    @Preview(showBackground = true)
    @Composable
    private fun TextWithPaddingToBaselinePreview() {
        AndroidJetpackComposeDemoTheme {
            Text("Hi there!", Modifier.firstBaselineToTop(32.dp))
        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun TextWithNormalPaddingPreview() {
        AndroidJetpackComposeDemoTheme {
            Text("Hi there!", Modifier.padding(top = 32.dp))
        }
    }

    @Composable
    fun ScrollingList() {
        val listSize = 100
        // We save the scrolling position with this state
        val scrollState = rememberLazyListState()
        // We save the coroutine scope where our animated scroll will be executed
        // This CoroutineScope will follow the lifecycle of the call site.
        val coroutineScope = rememberCoroutineScope()

        Column {
            Row {
                Button(onClick = {
                    coroutineScope.launch {
                        // 0 is the first item index
                        scrollState.animateScrollToItem(0)
                    }
                }) {
                    Text(text = "Scroll to Top")
                }
                Button(onClick = {
                    coroutineScope.launch {
                        // listSize - 1 is the last index of the list
                        scrollState.animateScrollToItem(listSize - 1)
                    }
                }) {
                    Text(text = "Scroll to End")
                }
            }

            LazyColumn(state = scrollState) {
                items(100) {
                    ImageListItem(index = it)
                }
            }
        }
    }

    @Composable
    fun ImageListItem(index: Int) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = rememberImagePainter(
                    data = "https://developer.android.com/images/brand/Android_Robot.png"
                ),
                contentDescription = "Android Logo",
                modifier = Modifier.size(50.dp)
            )
            Spacer(Modifier.width(10.dp))
            Text("Item #$index", style = MaterialTheme.typography.subtitle1)
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

    @Preview(showBackground = true)
    @Composable
    private fun ListPreview() {
        ScrollingList()
    }

    @Composable
    private fun AppLayoutBody(modifier: Modifier = Modifier) {
        MyOwnColumn(modifier.padding(8.dp)) {
            Text("MyOwnColumn")
            Text("places items")
            Text("vertically.")
            Text("We've done it by hand!")
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
