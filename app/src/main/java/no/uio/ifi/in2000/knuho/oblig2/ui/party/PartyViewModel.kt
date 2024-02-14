package no.uio.ifi.in2000.knuho.oblig2.ui.party

import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import no.uio.ifi.in2000.knuho.oblig2.data.alpacas.AlpacaPartiesRepository
import no.uio.ifi.in2000.knuho.oblig2.model.alpacas.PartyInfo
import androidx.media3.common.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

//////////////////////////////
// PartyViewModel.kt        //
//////////////////////////////

data class PartyUIState(
    val chosenParty: List<PartyInfo> = listOf()
)



class PartyViewModel(id: String?) : ViewModel() {
    private val repository: AlpacaPartiesRepository = AlpacaPartiesRepository()

    val partyUIState : StateFlow<PartyUIState> =
        repository.observeChosenPartyInfo()
            .map {
                PartyUIState(
                    chosenParty = it
                )
            }
            .stateIn(
                viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PartyUIState()
            )

    // Tilby mulighet til å velge parti
    fun choosePartyInfo(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.choosePartyInfo(id)
        }
    }
}