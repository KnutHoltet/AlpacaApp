package no.uio.ifi.in2000.knuho.oblig2.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.knuho.oblig2.data.alpacas.AlpacaPartiesRepository
import no.uio.ifi.in2000.knuho.oblig2.data.votes.IndividualVotes
import no.uio.ifi.in2000.knuho.oblig2.data.votes.VotesRepository
import no.uio.ifi.in2000.knuho.oblig2.model.alpacas.PartyInfo
import no.uio.ifi.in2000.knuho.oblig2.model.votes.District
import no.uio.ifi.in2000.knuho.oblig2.model.votes.DistrictVotes

//////////////////////////////
// HomeScreenViewModel.kt   //
//////////////////////////////
data class AlpacaUiState(
    val alpacaParties: List<PartyInfo> = listOf(),
)


data class VotesUiState(
    val districtOneVotes: List<DistrictVotes> = listOf(),
    val districtTwoVotes: List<DistrictVotes> = listOf(),
    val districtThreeVotes: List<DistrictVotes> = listOf()
)


class HomeScreenViewModel : ViewModel() {
    private val alpacaRepository: AlpacaPartiesRepository = AlpacaPartiesRepository()

    private val _partiesUIstate = MutableStateFlow(AlpacaUiState())

    val  partiesUiState: StateFlow<AlpacaUiState> = _partiesUIstate.asStateFlow()



    private val votesRepository: VotesRepository = VotesRepository()

    private val _votesUIState = MutableStateFlow(VotesUiState())

    val votesUIState: StateFlow<VotesUiState> = _votesUIState.asStateFlow()


    private val _districtOneUiState = MutableStateFlow(VotesUiState())
    private val _districtTwoUiState = MutableStateFlow(VotesUiState())
    private val _districtThreeUiState = MutableStateFlow(VotesUiState())

    val districtOneUiState: StateFlow<VotesUiState> = _districtOneUiState.asStateFlow()
    val districtTwoUiState: StateFlow<VotesUiState> = _districtTwoUiState.asStateFlow()
    val districtThreeUiState: StateFlow<VotesUiState> = _districtThreeUiState.asStateFlow()


    init {
        Log.d("HomeScreenViewModel", "Calling loadParties")
        loadParties()
        loadVotes()
    }

    private fun loadParties() {
        viewModelScope.launch(Dispatchers.IO) {
            _partiesUIstate.update { currentPartiesUiState ->
                Log.d("HomeScreenViewModel", "Calling alpacaRepository.getAlpacaParties()")

                val parties = alpacaRepository.getAlpacaParties()

                currentPartiesUiState.copy(alpacaParties = parties)
            }
        }
    }


    private fun loadVotes() {
        viewModelScope.launch(Dispatchers.IO)  {

            _districtOneUiState.update {getVotesUiState ->
                val votes = votesRepository.getIndividualVotesOne()
                getVotesUiState.copy(districtOneVotes = votes)
            }

            _districtTwoUiState.update { getVotesUiState ->
                val votes = votesRepository.getIndividualVotesTwo()

                getVotesUiState.copy(districtTwoVotes = votes)
            }

            _districtThreeUiState.update { getVotesUiState ->
                val votes = votesRepository.getAggregatedVotes()

                getVotesUiState.copy(districtThreeVotes = votes)
            }

        }
    }


    fun getPartyVotes(district: District) {
        viewModelScope.launch {
            votesRepository.getDistrictVotes(district)
        }
    }
}