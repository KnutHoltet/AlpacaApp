package no.uio.ifi.in2000.knuho.oblig2.data.alpacas

//////////////////////////////
// Imports                  //
//////////////////////////////
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import no.uio.ifi.in2000.knuho.oblig2.model.alpacas.PartyInfo

//////////////////////////////
// AlpacaPartiesRepository  //
//////////////////////////////
class AlpacaPartiesRepository {

    // Intern flow som er alpaca partier
    private val alpacaParties = MutableStateFlow<List<PartyInfo>>(listOf())
    private val fetchStatus = MutableStateFlow<Boolean>(false)

    // Tom liste med et enkelt element, også når du replaserer med .update med en ny liste med et element
    private val chosenPartyInfo = MutableStateFlow<List<PartyInfo>>(listOf())

    // Oensker å sende denne videre utover - tilbyr ting som noen kan gjoere på de
    fun observeAlpacaParties() : StateFlow<List<PartyInfo>> = alpacaParties.asStateFlow()
    fun observeFetchStatus() : StateFlow<Boolean> = fetchStatus.asStateFlow()
    fun observeChosenPartyInfo() : StateFlow<List<PartyInfo>> = chosenPartyInfo.asStateFlow()

    // Parties = val parties: List<PartyInfo>
    suspend fun refreshAlpacaParties(): Boolean {
        val dataSource = AlpacaPartiesDataSource()

        // Henter List<PartyInfo> fra AlpacaPartiesDataSource - som kan vaere null -
        // Returnerer true hvis det er data, false hvis det ikke er data -
        // Returnerer List<PartyInfo> hvis det er data
        return withContext(Dispatchers.IO) {
            dataSource.fetchAlpacaParties()?.let {
                alpacaParties.value = it.parties
                true

            } ?: run {
                false
            }
        }
    }

    // AlpacaParties har bare et parti i lista med en funksjon
    suspend fun choosePartyInfo(id: String) {
        Log.d("Valgt Parti", "Valgt parti med id: $id")
        return withContext(Dispatchers.IO) {
            alpacaParties.value.find { it.id == id }?.let {
                chosenPartyInfo.value = listOf(it)
            }
        }
    }
}