package no.uio.ifi.in2000.knuho.oblig2.ui.party

import android.util.Log
import androidx.annotation.MainThread
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
import java.io.IOException

//////////////////////////////
// PartyViewModel.kt        //
//////////////////////////////

data class SinglePartyUiState(
    val chosenParty: List<PartyInfo> = listOf()
)


class PartyViewModel(
) : ViewModel() {
    private val alpacaRepository: AlpacaPartiesRepository = AlpacaPartiesRepository()

    private val _singlePartyUiState = MutableStateFlow(SinglePartyUiState())

    val singlePartyUiState: StateFlow<SinglePartyUiState> = _singlePartyUiState.asStateFlow()


    private var initializedCalled = false

    @MainThread
    fun loadSinglePartyInfo(id: String) {

        Log.d("initializedCalled verdi før", "$initializedCalled")

        if(initializedCalled) return
        initializedCalled = true


        viewModelScope.launch(Dispatchers.IO) {
            _singlePartyUiState.update { currentPartyUiState ->

                val singleParty = alpacaRepository.getSingleParty(id)
                Log.d("API-kall", "API-kall i PartyViewModeAPI-kall i PartyViewModell")
                Log.d("initializedCalled verdi", "$initializedCalled")
                currentPartyUiState.copy(chosenParty = singleParty)


            }
        }

    }
}