package com.example.rincon_verde2.ui.feature.eventdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rincon_verde2.data.repository.EventRepository
import com.example.rincon_verde2.domain.model.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class EventDetailUiState {
    object Loading : EventDetailUiState()
    data class Success(val event: Event) : EventDetailUiState()
    data class Error(val message: String) : EventDetailUiState()
}

@HiltViewModel
class EventDetailViewModel @Inject constructor(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<EventDetailUiState>(EventDetailUiState.Loading)
    val uiState: StateFlow<EventDetailUiState> = _uiState.asStateFlow()

    fun loadEventDetail(eventId: String) {
        viewModelScope.launch {
            _uiState.value = EventDetailUiState.Loading
            val event = eventRepository.getEventById(eventId)
            if (event != null) {
                _uiState.value = EventDetailUiState.Success(event)
            } else {
                _uiState.value = EventDetailUiState.Error("Evento no encontrado")
            }
        }
    }
}
