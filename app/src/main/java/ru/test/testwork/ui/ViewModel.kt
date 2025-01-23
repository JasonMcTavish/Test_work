package ru.test.testwork.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.test.testwork.api.ResponseInfoDto
import ru.test.testwork.domain.GetInfoUseCase
import javax.inject.Inject

@HiltViewModel
class ViewModel @Inject constructor(
    private val getInfoUseCase: GetInfoUseCase
) : ViewModel() {

    private val currentId: String = "436535"

    private val _infoFlow = MutableStateFlow<ResponseInfoDto?>(null)
    val infoFlow = _infoFlow.asStateFlow()

    private val _loadInfoState = MutableStateFlow<StateLoading>(StateLoading.Default)
    val loadInfoState = _loadInfoState.asStateFlow()

    fun getInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _loadInfoState.value = StateLoading.Loading
                val tempInfo = getInfoUseCase.executeInfo(currentId)
                _infoFlow.value = tempInfo
                _loadInfoState.value = StateLoading.Success
            } catch (e: Throwable) {
                _loadInfoState.value = StateLoading.Error(e.message.toString())
            }
        }
    }
}