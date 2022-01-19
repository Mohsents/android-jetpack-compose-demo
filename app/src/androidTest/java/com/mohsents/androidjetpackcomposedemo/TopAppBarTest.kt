package com.mohsents.androidjetpackcomposedemo

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.mohsents.androidjetpackcomposedemo.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test

class TopAppBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun rallyTopAppBarTest() {
        val allScreen = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreen,
                onTabSelected = {},
                currentScreen = RallyScreen.Accounts
            )
        }
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .assertIsDisplayed()
    }

    @Test
    fun rallyTopAppBarTest_currentLabelExists() {
        val allScreen = RallyScreen.values().toList()
        composeTestRule.setContent {
            RallyTopAppBar(
                allScreens = allScreen,
                onTabSelected = {},
                currentScreen = RallyScreen.Accounts
            )
        }
        // Log all nods for 'composeTestRule's content to debug.
        composeTestRule.onRoot().printToLog("currentLabelExists")
        composeTestRule.onNode(
            hasText(RallyScreen.Accounts.name.uppercase()) and
                    hasParent(
                        hasContentDescription(
                            RallyScreen.Accounts.name
                        )
                    ), useUnmergedTree = true
        ).assertExists()
    }

    @Test
    fun rallyTopAppBarTest_selectAccountsTab_navigateToAccountsScreen() {
        composeTestRule.setContent {
            RallyApp()
        }
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Accounts.name)
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("Accounts Screen")
            .assertIsDisplayed()
    }

    @Test
    fun rallyTopAppBarTest_selectBillsTab_navigateToBillsScreen() {
        composeTestRule.setContent {
            RallyApp()
        }
        composeTestRule
            .onNodeWithContentDescription(RallyScreen.Bills.name)
            .performClick()

        composeTestRule
            .onNodeWithContentDescription("All Bills")
            .assertIsDisplayed()
    }
}
