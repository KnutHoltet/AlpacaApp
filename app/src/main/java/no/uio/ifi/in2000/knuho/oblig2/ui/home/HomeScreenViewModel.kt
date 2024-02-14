package no.uio.ifi.in2000.knuho.oblig2.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import no.uio.ifi.in2000.knuho.oblig2.data.alpacas.AlpacaPartiesRepository
import no.uio.ifi.in2000.knuho.oblig2.model.alpacas.PartyInfo

data class AlpacaUIState(
    val alpacaParties: List<PartyInfo> = listOf(),
    val wifiBoolean: Boolean = false
)

class HomeScreenViewModel : ViewModel() {
    // Oppretter et repository
    private val repository: AlpacaPartiesRepository = AlpacaPartiesRepository()

    // Brukergrensesnitt
    val alpacaUIState : StateFlow<AlpacaUIState> =
        combine(
            repository.observeAlpacaParties(),
            repository.observeFetchStatus()

        ) { alpacaParties, wifiBool ->
            AlpacaUIState(
                alpacaParties = alpacaParties,
                wifiBoolean = wifiBool
            )
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AlpacaUIState()
        )


    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.refreshAlpacaParties()
        }
    }
}