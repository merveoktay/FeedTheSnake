import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.feedthesnake.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class AppNavHostTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun appNavHost_navigatesToSecondSplashScreen() {
        composeTestRule.onNodeWithText("Splash Screen").assertExists()

        composeTestRule.onNodeWithText("Next").performClick()

        composeTestRule.onNodeWithText("Second Splash Screen").assertExists()
    }

    @Test
    fun appNavHost_navigatesToGameScreenWithName() {
        val testName = "Player1"

        composeTestRule.onNodeWithText("Enter Name").performTextInput(testName)
        composeTestRule.onNodeWithText("Start Game").performClick()

        composeTestRule.onNodeWithText("Game Screen - $testName").assertExists()
    }

    @Test
    fun appNavHost_navigatesToGameOverScreenWithScore() {
        val testScore = 100

        composeTestRule.onNodeWithText("End Game").performClick()

        composeTestRule.onNodeWithText("Game Over - Score: $testScore").assertExists()
    }
}
