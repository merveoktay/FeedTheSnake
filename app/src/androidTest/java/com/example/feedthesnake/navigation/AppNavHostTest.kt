import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.feedthesnake.navigation.AppNavHost
import com.example.feedthesnake.ui.presentation.GameOverScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AppNavHostTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Before
    fun setupAppNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            AppNavHost()
        }
    }

    @Test
    fun appNavHost_verifyStartDestination() {
        composeTestRule
            .onNodeWithContentDescription("Splash Screen")
            .assertIsDisplayed()
    }

    @Test
    fun testNavigation_fromSplash_toSecondSplash() {
        composeTestRule.setContent {
            AppNavHost()
        }

        composeTestRule.onNodeWithTag("SplashScreen").assertIsDisplayed()

    }

    @Test
    fun testNavigation_fromSecondSplash_toHome() {
        composeTestRule.setContent {
            AppNavHost()
        }

        // SplashScreen'den SecondSplashScreen'e geçiş yap
        composeTestRule.onNodeWithTag("SplashScreen").performClick()

        // SecondSplashScreen'in yüklenmesini bekleyin
        composeTestRule.waitUntil(5000) {
            composeTestRule.onAllNodesWithTag("SecondSplashScreen").fetchSemanticsNodes().isNotEmpty()
        }

        composeTestRule.onNodeWithTag("SecondSplashScreen").assertIsDisplayed()
    }

    @Test
    fun testNavigation_fromHome_toNameEntry() {
        composeTestRule.setContent {
            AppNavHost()
        }
        composeTestRule.onNodeWithTag("HomeScreen").assertIsDisplayed()

        // "Enter Name" butonuna tıklama (testTag kullanarak)
        composeTestRule.onNodeWithTag("New Game").performClick()

        // NameEntry ekranındaki testTag "name_entry_screen" ile doğrulama yap
        composeTestRule.onNodeWithTag("NameEntryScreen").assertIsDisplayed()
    }

    @Test
    fun testNavigation_fromNameEntry_toGame() {
        // setContent çağırarak NameEntry ekranını gösteriyoruz
        composeTestRule.setContent {
            AppNavHost()
        }

        // NameEntry ekranındaki testTag "name_entry_screen" ile doğrulama yap
        composeTestRule.onNodeWithTag("NameEntryScreen").assertIsDisplayed()

        // "Start Game" butonuna tıklama (testTag kullanarak)
        composeTestRule.onNodeWithTag("Start").performClick()

        // Game ekranındaki testTag "game_screen" ile doğrulama yap
        composeTestRule.onNodeWithTag("GameScreen").assertIsDisplayed()
    }

    @Test
    fun testGameOverNavigation() {
        // setContent çağırarak Game ekranını gösteriyoruz
        composeTestRule.setContent {
            AppNavHost()
        }

        // Game ekranındaki testTag "game_screen" ile doğrulama yap
        composeTestRule.onNodeWithTag("GameScreen").assertIsDisplayed()

        // "Game Over" butonuna tıklama (testTag kullanarak)
        composeTestRule.onNodeWithTag("game_over_button").performClick()

        // Game Over ekranındaki testTag "game_over_screen" ile doğrulama yap
        composeTestRule.onNodeWithTag("GameOverScreen").assertIsDisplayed()
    }

    @Test
    fun testNavigation_fromGameOver_toScoreTable() {
        // AppNavHost'u başlat
        composeTestRule.setContent {
            AppNavHost()
        }


            composeTestRule.onAllNodesWithTag("GameOverScreen").fetchSemanticsNodes().isNotEmpty()


        // GameOverScreen'in görüntülendiğini doğrula
        composeTestRule.onNodeWithTag("GameOverScreen").assertIsDisplayed()

        // "Try Again" butonuna tıklama (testTag kullanarak)
        composeTestRule.onNodeWithTag("Try Again").performClick()

        // NameEntryScreen'in yüklenmesini bekleyin
        composeTestRule.waitUntil(10000) {
            composeTestRule.onAllNodesWithTag("NameEntryScreen").fetchSemanticsNodes().isNotEmpty()
        }

        // NameEntryScreen'in görüntülendiğini doğrula
        composeTestRule.onNodeWithTag("NameEntryScreen").assertIsDisplayed()
    }
}
