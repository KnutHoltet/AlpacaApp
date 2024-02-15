package no.uio.ifi.in2000.knuho.oblig2.data.alpacas

//////////////////////////////
// Imports                  //
//////////////////////////////

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import java.io.Closeable
import kotlin.coroutines.CoroutineContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import no.uio.ifi.in2000.knuho.oblig2.model.alpacas.Parties
import no.uio.ifi.in2000.knuho.oblig2.model.alpacas.PartyInfo

//////////////////////////////
// Nettverksoperasjoner     //
//////////////////////////////

////////////////////////////////
// AlpacaPartiesDataSource.kt //
////////////////////////////////

// https://ktor.io/docs/http-client-engines.html#okhttp
class AlpacaPartiesDataSource : ViewModel() {
    private val url: String = "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppgaver/alpacaparties.json"



    private val client = HttpClient {
        install(ContentNegotiation) {
            gson()
        }
    }

    suspend fun fetchAlpacaParties(): List<PartyInfo> {

        val parties: Parties = try {
            // Viewmodel.viewModelScope.launch(Dispatchers.IO) {
            /*
            withContext(Dispatchers.IO) {
                client.get(url).body()
            }
             */
            client.get(url).body()


        } catch (e: Exception) {
            println("Failed to fetch data")
            Parties(listOf())
        }
        return parties.parties
    }
}

//////////////////////////////
// Test funksjon            //
//////////////////////////////
suspend fun main(){
    val alpaca = AlpacaPartiesDataSource()
    println(alpaca.fetchAlpacaParties())
}

