package no.uio.ifi.in2000.knuho.oblig2.data.alpacas

import android.util.Log
import androidx.lifecycle.ViewModel
import no.uio.ifi.in2000.knuho.oblig2.model.alpacas.PartyInfo

//////////////////////////////
// AlpacaPartiesRepository  //
//////////////////////////////
class AlpacaPartiesRepository {
    private val alpacaPartyData = AlpacaPartiesDataSource()


    // suspend fun getAlpacaParties() : List<PartyInfo> = alpacaPartyData.fetchAlpacaParties()
    suspend fun getAlpacaParties() : List<PartyInfo> {

        Log.d("ALPACA_PARTIES_REPOSITORY", "Calling alpacaPartyData.fetchAlpacaParties()")

        return alpacaPartyData.fetchAlpacaParties()
    }


    suspend fun getSingleParty(id: String) : List<PartyInfo> = alpacaPartyData.fetchAlpacaParties().filter { it.id == id }
}

////////////////////////////
// Test funksjon          //
////////////////////////////
suspend fun main(){
    val alpaca = AlpacaPartiesRepository()
    // println(alpaca.refreshAlpacaParties())
    // println(alpaca.observeAlpacaParties())
    // println(alpaca.observeFetchStatus())
    // println(alpaca.observeChosenPartyInfo())
    // println(alpaca.choosePartyInfo("1"))
}

