package no.uio.ifi.in2000.knuho.oblig2.data.alpacas

import androidx.lifecycle.ViewModel
import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.gson.gson
import no.uio.ifi.in2000.knuho.oblig2.model.alpacas.Parties
import no.uio.ifi.in2000.knuho.oblig2.model.alpacas.PartyInfo

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

