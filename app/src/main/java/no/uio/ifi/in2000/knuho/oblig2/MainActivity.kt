package no.uio.ifi.in2000.knuho.oblig2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import no.uio.ifi.in2000.knuho.oblig2.ui.home.HomeScreen
import no.uio.ifi.in2000.knuho.oblig2.ui.home.HomeScreenViewModel
import no.uio.ifi.in2000.knuho.oblig2.ui.party.PartyScreen
import no.uio.ifi.in2000.knuho.oblig2.ui.party.PartyViewModel
import no.uio.ifi.in2000.knuho.oblig2.ui.theme.Knuho_oblig2Theme

sealed class Screen(val route: String, @StringRes val resourceId: Int){
    object Home : Screen("home", R.string.home_screen)
    object Party : Screen("party", R.string.party_screen)
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Knuho_oblig2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Greeting("Android")

                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route
                    ) {
                        composable(Screen.Home.route) { backStackEntry ->

                            HomeScreen(navController, HomeScreenViewModel())
                        }
                        composable("${Screen.Party.route}/{partyId}") { backStackEntry ->

                            val id = backStackEntry.arguments?.getString("partyId")

                            PartyScreen(navController, PartyViewModel(id))

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Knuho_oblig2Theme {
        Greeting("Android")
    }
}