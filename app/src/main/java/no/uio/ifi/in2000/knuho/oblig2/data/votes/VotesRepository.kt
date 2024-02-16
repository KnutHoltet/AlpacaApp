package no.uio.ifi.in2000.knuho.oblig2.data.votes

import no.uio.ifi.in2000.knuho.oblig2.model.votes.District
import no.uio.ifi.in2000.knuho.oblig2.model.votes.DistrictVotes


class VotesRepository {
    private val individualVotes = IndividualVotesDataSource()
    private val aggregatedVotes = AggregatedVotesDataSource()

    suspend fun getIndividualVotesOne() : List<DistrictVotes> {
        return individualVotes.fetchDistrictOneVotes()
    }

    suspend fun getIndividualVotesTwo() : List<DistrictVotes> {
        return individualVotes.fetchDistrictTwoVotes()
    }

    suspend fun getAggregatedVotes(): List<DistrictVotes> {
        return aggregatedVotes.fetchAggregatedVotes()
    }

    suspend fun getDistrictVotes(district: District): List<DistrictVotes> {
        val districtOne: List<DistrictVotes> = getIndividualVotesOne()
        val districtTwo: List<DistrictVotes> = getIndividualVotesTwo()
        val districtThree: List<DistrictVotes> = getAggregatedVotes()

        return when(district) {
            District.ONE -> districtOne
            District.TWO -> districtTwo
            District.THREE -> districtThree
        }

    }
}