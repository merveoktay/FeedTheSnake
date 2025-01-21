import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.feedthesnake.ui.presentation.HomeScreen
import com.example.feedthesnake.ui.presentation.NameEntryScreen
import com.example.feedthesnake.navigation.AppNavHost
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testNavigationFromHomeToNameEntry() {
        composeTestRule.setContent {
            AppNavHost()
        }

        // HomeScreen'deki "Start Game" butonuna tıklayın
        composeTestRule.onNodeWithText("Start Game", useUnmergedTree = true).performClick()

        // NameEntryScreen'e geçişi kontrol edin
        composeTestRule.waitForIdle()  // UI'nin tamamen yüklenmesini bekleyin
        composeTestRule.onNodeWithText("Enter Name").assertIsDisplayed()
    }

    @Test
    fun testNavigationFromNameEntryToGameScreen() {
        composeTestRule.setContent {
            AppNavHost()
        }

        // HomeScreen'deki "Start Game" butonuna tıklayın
        composeTestRule.onNodeWithText("Start Game", useUnmergedTree = true).performClick()

        // NameEntryScreen'deki input alanına bir isim girin
        composeTestRule.onNodeWithText("Enter Name").performTextInput("Player1")

        // "Start Game" butonuna tıklayın
        composeTestRule.onNodeWithText("Start Game", useUnmergedTree = true).performClick()

        // GameScreen'e geçişi kontrol edin
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Game Screen").assertIsDisplayed()
    }

    @Test
    fun testNavigationFromGameToGameOver() {
        composeTestRule.setContent {
            AppNavHost()
        }

        // İlk olarak, "game/{name}" sayfasına git
        composeTestRule.onNodeWithText("Start Game", useUnmergedTree = true).performClick()
        composeTestRule.onNodeWithText("Enter Name").performTextInput("Player1")
        composeTestRule.onNodeWithText("Start Game", useUnmergedTree = true).performClick()

        // Oyun bittiğinde "game_over" ekranına geçişi kontrol et
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Game Over").assertIsDisplayed()
    }
}
