package no.uio.ifi.in2000.knuho.oblig2.data.alpacas

//////////////////////////////
// Imports                  //
//////////////////////////////

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.gson.gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import no.uio.ifi.in2000.knuho.oblig2.model.alpacas.Parties

//////////////////////////////
// Nettverksoperasjoner     //
//////////////////////////////

////////////////////////////////
// AlpacaPartiesDataSource.kt //
////////////////////////////////

// https://ktor.io/docs/http-client-engines.html#okhttp
class AlpacaPartiesDataSource {
    private val client = HttpClient {
        install(ContentNegotiation) {
            gson()
        }
    }

    suspend fun fetchAlpacaParties(): Parties? {
        return try {
            withContext(Dispatchers.IO) {
                client.get("https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppgaver/alpacaparties.json").body()
            }
        } catch (e: Exception) {
            println("Failed to fetch data")
            null
        }
    }
}

//////////////////////////////
// Test funksjon            //
//////////////////////////////
suspend fun main(){
    val alpaca = AlpacaPartiesDataSource()
    println(alpaca.fetchAlpacaParties())
}
