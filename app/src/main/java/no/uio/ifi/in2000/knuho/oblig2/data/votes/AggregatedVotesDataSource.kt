package no.uio.ifi.in2000.knuho.oblig2.data.votes

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import no.uio.ifi.in2000.knuho.oblig2.model.votes.District
import no.uio.ifi.in2000.knuho.oblig2.model.votes.DistrictVotes

data class AggregatedVotes(val partyId: String, val votes: Int)
data class ApiResponse(val parties: List<AggregatedVotes>)

class AggregatedVotesDataSource {
    private val url: String =
        "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppgaver/district3.json"

    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            gson()
        }
    }

    suspend fun fetchAggregatedVotes(): List<DistrictVotes> {

        val response: ApiResponse = try {
            client.get(url).body()
        } catch (e: Exception) {
            ApiResponse(listOf())
        }

        return response.parties.map {
            DistrictVotes(District.THREE, it.partyId, it.votes)
        }




        /*
        val votes: PartiesVotes = try {
            client.get(url).body()
        } catch(e: Exception) {
            PartiesVotes(listOf())
        }

        // return votes.partiesVotes?.map {
        //     DistrictVotes(District.THREE, it.partyId, it.votes)
        // } ?: listOf()

        return votes.partiesVotes.map {
            DistrictVotes(District.THREE, it.partyId, it.votes)
        }
         */

    }
}

//////////////////////
// Test funksjon    //
//////////////////////
suspend fun main() {
    val dataSource = AggregatedVotesDataSource()
    println(dataSource.fetchAggregatedVotes().toString())
}
