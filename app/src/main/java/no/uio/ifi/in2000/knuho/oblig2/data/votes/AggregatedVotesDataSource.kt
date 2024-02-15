package no.uio.ifi.in2000.knuho.oblig2.data.votes

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import no.uio.ifi.in2000.knuho.oblig2.model.votes.District
import no.uio.ifi.in2000.knuho.oblig2.model.votes.DistrictVotes


data class AggregatedVotes(val partyId: String, val votes: Int)
data class PartyVotes(val name: String, val votes: Int)
data class PartiesVotes(val partiesVotes: List<AggregatedVotes>)

class AggregatedVotesDataSource {
    private val url: String =
        "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppgaver/district3.json"

    private val client = HttpClient {
        install(ContentNegotiation) {
            gson()
        }
    }

    suspend fun fetchAggregatedVotes(): List<DistrictVotes> {

        val votes: PartiesVotes = try {
            client.get(url).body()
        } catch(e: Exception) {
            PartiesVotes(listOf())
        }

        return votes.partiesVotes.map {
            DistrictVotes(District.THREE, it.partyId, it.votes)
        }
    }
}