package no.uio.ifi.in2000.knuho.oblig2.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import no.uio.ifi.in2000.knuho.oblig2.Screen
import no.uio.ifi.in2000.knuho.oblig2.model.alpacas.PartyInfo

@Composable
fun HomeScreen(
    navController: NavController,
    homeScreenViewModel: HomeScreenViewModel
) {
    val alpacaUIState by homeScreenViewModel.alpacaUIState.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()){
        items(alpacaUIState.alpacaParties) {partyInfo ->
            AlpacaPartyCard(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                alpacaParty = partyInfo,
                navController = navController
            )
        }
    }

}

@Composable
fun AlpacaPartyCard(modifier: Modifier = Modifier, alpacaParty : PartyInfo, navController: NavController){
    OutlinedCard(modifier = modifier){
        Row (
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    navController.navigate("${Screen.Party.route}/${alpacaParty.id}")
                    //navController.navigate(Screen.Party.route)
                },
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
            }

        }
    }
}
