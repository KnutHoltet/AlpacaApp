package no.uio.ifi.in2000.knuho.oblig2.ui.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.knuho.oblig2.Screen
import no.uio.ifi.in2000.knuho.oblig2.model.alpacas.PartyInfo
import no.uio.ifi.in2000.knuho.oblig2.model.votes.District


// https://developer.android.com/jetpack/compose/components/snackbar
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    homeScreenViewModel: HomeScreenViewModel = HomeScreenViewModel()
) {

    val alpacaUiState: AlpacaUiState by homeScreenViewModel.partiesUiState.collectAsState()
    homeScreenViewModel.loadParties()
    homeScreenViewModel.loadVotes()

    val votesUiStateOne: VotesUiState by homeScreenViewModel.districtOneUiState.collectAsState()
    val votesUiStateTwo: VotesUiState by homeScreenViewModel.districtTwoUiState.collectAsState()
    val votesUiStateThree: VotesUiState by homeScreenViewModel.districtThreeUiState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var isExpanded by remember { mutableStateOf(false) }
    val district: List<String> = listOf("District 1", "District 2", "District 3")
    var selectedDistrict by remember { mutableStateOf("") }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },


        floatingActionButton = {
            if(alpacaUiState.alpacaParties.isEmpty()) {
                ExtendedFloatingActionButton(
                    text = { Text("no internett - refresh") },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "") },
                    onClick = {
                        scope.launch {
                            val result = snackbarHostState
                                .showSnackbar(
                                    message = "No internett connection",
                                    actionLabel = "Refresh",
                                    // Defaults to SnackbarDuration.Short
                                    duration = SnackbarDuration.Indefinite,
                                )
                            when (result) {
                                SnackbarResult.ActionPerformed -> {
                                    /* Handle snackbar action performed */
                                    navController.navigate(Screen.Home.route)
                                }
                                SnackbarResult.Dismissed -> {
                                    /* Handle snackbar dismissed */
                                }
                            }
                        }
                    }
                )
            } else{
                snackbarHostState.currentSnackbarData?.dismiss()
            }
        }

    ) { contentPadding ->
        // Screen content

        Column (modifier = Modifier.padding(contentPadding)){
            ExposedDropdownMenuBox(
                expanded = isExpanded,
                onExpandedChange = { isExpanded = it && alpacaUiState.alpacaParties.isNotEmpty() },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = selectedDistrict,
                    onValueChange = {},
                    placeholder = {Text(text = "Velg district")},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    district.filter { it != selectedDistrict }.forEach {valgtDistrict ->
                        DropdownMenuItem(
                            modifier = Modifier.fillMaxWidth(),
                            text = { Text(text = valgtDistrict, modifier = Modifier.fillMaxWidth()) },
                            onClick = {
                                selectedDistrict = valgtDistrict
                                isExpanded = false
                                /*
                                when(district.indexOf(selectedDistrict)) {

                                    0 -> homeScreenViewModel.getPartyVotes(District.ONE)
                                    1 -> homeScreenViewModel.getPartyVotes(District.TWO)
                                    2 -> homeScreenViewModel.getPartyVotes(District.THREE)

                                }

                                 */
                            }
                        )
                    }
                }
            }
            VoteList(homeScreenViewModel, selectedDistrict)

            LazyColumn(modifier = Modifier.fillMaxSize()){
                items(alpacaUiState.alpacaParties) { partyInfo ->
                    AlpacaPartyCard(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                navController.navigate("${Screen.Party.route}/${partyInfo.id}")
                            }
                            .padding(contentPadding),
                        alpacaParty = partyInfo,
                        navCon = navController
                    )


                }
            }
        }
    }
}

@Composable
fun AlpacaPartyCard(modifier: Modifier = Modifier, alpacaParty : PartyInfo, navCon: NavController){
    OutlinedCard(
        modifier = modifier,
            // .clickable {
            //     navCon.navigate("${Screen.Party.route}/${alpacaParty.id}")
            // },
        colors = CardDefaults.cardColors(
            Color(
                android.graphics.Color.parseColor(
                    alpacaParty.color
                )
            )
        )
    ){
        Row (
            modifier = Modifier
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            AsyncImage(
                modifier = Modifier
                    .size(96.dp)
                    .clip(CircleShape),
                model = alpacaParty.img,
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Column {
                /*
                TODO:
                Legg inn mer beskrivende tekst
                 */
                Text("Parti navn: ${alpacaParty.name}")
                Text("Parti leder: ${alpacaParty.leader}")
            }

        }
    }
}
