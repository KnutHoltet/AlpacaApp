package no.uio.ifi.in2000.knuho.oblig2.ui.party

import kotlinx.coroutines.flow.StateFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import no.uio.ifi.in2000.knuho.oblig2.data.alpacas.AlpacaPartiesRepository
import no.uio.ifi.in2000.knuho.oblig2.model.alpacas.PartyInfo
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

//////////////////////////////
// PartyViewModel.kt        //
//////////////////////////////

data class SinglePartyUiState(
    val chosenParty: List<PartyInfo> = listOf()
)


class PartyViewModel(
    private val partyId: String
) : ViewModel() {
    private val alpacaRepository: AlpacaPartiesRepository = AlpacaPartiesRepository()

    private val _singlePartyUiState = MutableStateFlow(SinglePartyUiState())

    val singlePartyUiState: StateFlow<SinglePartyUiState> = _singlePartyUiState.asStateFlow()

    init {
        loadSinglePartyInfo(partyId)
    }

    private fun loadSinglePartyInfo(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _singlePartyUiState.update { currentPartyUiState ->
                val singleParty = alpacaRepository.getSingleParty(id)

                currentPartyUiState.copy(chosenParty = singleParty)
            }
        }
    }


}