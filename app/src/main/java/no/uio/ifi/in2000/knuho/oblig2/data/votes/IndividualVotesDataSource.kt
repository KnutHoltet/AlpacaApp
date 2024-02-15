package no.uio.ifi.in2000.knuho.oblig2.data.votes

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import io.ktor.websocket.WebSocketDeflateExtension.Companion.install
import no.uio.ifi.in2000.knuho.oblig2.model.votes.District
import no.uio.ifi.in2000.knuho.oblig2.model.votes.DistrictVotes


data class IndividualVotes(val id: String)

class IndividualVotesDataSource {
    private val urlOne: String =
        "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppgaver/district1.json"

    private val urlTwo: String =
        "https://www.uio.no/studier/emner/matnat/ifi/IN2000/v24/obligatoriske-oppgaver/district2.json"


    private val client = HttpClient {
        install(ContentNegotiation) {
            gson()
        }
    }


    suspend fun fetchDistrictOneVotes(): List<DistrictVotes> {
        val votes: List<IndividualVotes> = try {
            client.get(urlOne).body()

        } catch(e: Exception) {
            listOf()
        }

        return listOf("1", "2", "3", "4").map {id ->
            DistrictVotes(District.ONE, id, votes.count {it.id == id})

        }
    }


    suspend fun fetchDistrictTwoVotes(): List<DistrictVotes> {
        val votes: List<IndividualVotes> = try {
            client.get(urlTwo).body()
        } catch(e: Exception) {
            listOf()
        }

        return listOf("1", "2", "3", "4").map {id ->
            DistrictVotes(District.TWO, id, votes.count {it.id == id})
        }
    }
}