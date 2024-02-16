package no.uio.ifi.in2000.knuho.oblig2.ui.home

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import no.uio.ifi.in2000.knuho.oblig2.model.votes.District

@Composable
fun VoteList(homeScreenViewModel: HomeScreenViewModel, district: String) {
    val votesUiStateOne: VotesUiState by homeScreenViewModel.districtOneUiState.collectAsState()
    val votesUiStateTwo: VotesUiState by homeScreenViewModel.districtTwoUiState.collectAsState()
    val votesUiStateThree: VotesUiState by homeScreenViewModel.districtThreeUiState.collectAsState()

    val alpacaUiState: AlpacaUiState by homeScreenViewModel.partiesUiState.collectAsState()

    val votesUiState = when (district) {
        "District 1" -> homeScreenViewModel.districtOneUiState.collectAsState()
        "District 2" -> homeScreenViewModel.districtTwoUiState.collectAsState()
        "District 3" -> homeScreenViewModel.districtThreeUiState.collectAsState()
        else -> homeScreenViewModel.districtOneUiState.collectAsState() // Default or handle error
    }.value

    Log.d("stemmer ;(", "${homeScreenViewModel.getPartyVotes(District.ONE)}")
    Log.d("TEST OM DEN GÅR INN HER I DET HELE TATT", "sløkjaøldkfjaslødkfjaslødkfjaslødkfj")
    Log.d("DISTRICT VERDI ER : ", "$district")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Text(text = "Parties", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Text(text = "Votes per party", fontWeight = FontWeight.Bold)
        }



        if (district == "District 1") {
            val partier = alpacaUiState.alpacaParties
            var teller = 0
            votesUiState.districtOneVotes.let {listVotes ->
                listVotes.forEach {districtVotes ->
                    Row(modifier = Modifier.padding(8.dp)){

                        Text(text = partier[teller].name)
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(text = districtVotes.numberOfVotesForParty.toString())

                        teller++
                    }
                }
            }
        }

        if (district == "District 2") {
            val partier = alpacaUiState.alpacaParties
            var teller = 0
            votesUiState.districtTwoVotes.let {listVotes ->
                listVotes.forEach {districtVotes ->
                    Row(modifier = Modifier.padding(8.dp)){

                        Text(text = partier[teller].name)
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(text = districtVotes.numberOfVotesForParty.toString())

                        teller++
                    }
                }
            }
        }

        if (district == "District 3") {
            val partier = alpacaUiState.alpacaParties
            var teller = 0
            votesUiState.districtThreeVotes.let {listVotes ->
                listVotes.forEach {districtVotes ->
                    Row(modifier = Modifier.padding(8.dp)){

                        Text(text = partier[teller].name)
                        Spacer(modifier = Modifier.padding(8.dp))
                        Text(text = districtVotes.numberOfVotesForParty.toString())

                        teller++
                    }
                }
            }
        }






    }
}

