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
import no.uio.ifi.in2000.knuho.oblig2.model.alpacas.PartyInfo

//////////////////////////////
// HomeScreenViewModel.kt   //
//////////////////////////////
data class AlpacaUIState(
    val alpacaParties: List<PartyInfo> = listOf(),
    val wifiBoolean: Boolean = false
)

class HomeScreenViewModel : ViewModel() {
    private val alpacaRepository: AlpacaPartiesRepository = AlpacaPartiesRepository()

    private val _partiesUIstate = MutableStateFlow(AlpacaUIState())

    val  partiesUiState: StateFlow<AlpacaUIState> = _partiesUIstate.asStateFlow()

    init {
        Log.d("HomeScreenViewModel", "Calling loadParties")
        loadParties()
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
}