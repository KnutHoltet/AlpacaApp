package no.uio.ifi.in2000.knuho.oblig2.data.votes

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
}