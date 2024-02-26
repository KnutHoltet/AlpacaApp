package no.uio.ifi.in2000.knuho.oblig2.ui.party

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.Alignment
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import no.uio.ifi.in2000.knuho.oblig2.Screen
import org.jetbrains.annotations.Async


// https://developer.android.com/jetpack/compose/components/app-bars - CenterAlignedTopAppBar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartyScreen(
    navController: NavController,
    partyId : String,
    partyViewModel: PartyViewModel = PartyViewModel(),
) {
    val partyInfoUiState: SinglePartyUiState by partyViewModel.singlePartyUiState.collectAsState()
    partyViewModel.loadSinglePartyInfo(partyId)

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())


    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Party list",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Screen.Home.route) }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        LazyColumn {
            items(partyInfoUiState.chosenParty) {chosenParty ->
                // Log.d("ASLØDFJASLØDKFJALØSKDJFALØKSJDF", "$chosenParty")
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(Color(android.graphics.Color.parseColor(chosenParty.color)))

                ){
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center

                    ){
                        
                        Text(text = chosenParty.name)

                        AsyncImage(
                            model = chosenParty.img,
                            contentDescription = null,
                            modifier = Modifier
                                .size(200.dp)
                        )


                        Text(text = "Leder : ${chosenParty.leader}")
                        Text(text = chosenParty.description)
                    }

                }
            }
        }
    }
}

