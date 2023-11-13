package com.cbdn.reports.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.cbdn.reports.data.datamodel.Report
import com.cbdn.reports.data.datamodel.VictimInfo
import com.cbdn.reports.ui.navigation.Destinations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SubmitNewUiState(

    // VARIABLES FOR UI FORM
    var currentScreen: String? = Destinations.SubmitNewDispatch.name,
    var dispatchComplete: Boolean = false,
    var locationComplete: Boolean = false,
    var onSceneComplete: Boolean = false,
    var submitComplete: Boolean = false,
    var reportComplete: Boolean = false,
    // dispatch
    var categoryIndex: Int = 0,
    // location
    // on scene
    var policeCheck: Boolean = false,
    var ambulanceCheck: Boolean = false,
    var electricCompanyCheck: Boolean = false,
    var transitPoliceCheck: Boolean = false,
    // submittal
)

class SubmitNewViewModel : ViewModel() {
    private var _reportState = MutableStateFlow(Report())
    private var _uiState = MutableStateFlow(SubmitNewUiState())
    var reportState: StateFlow<Report> = _reportState.asStateFlow()
    var uiState: StateFlow<SubmitNewUiState> = _uiState.asStateFlow()

    fun setCurrentScreen(screen: String?) {
        _uiState.update {
            it.copy(currentScreen = screen)
        }
    }
    private fun isReportComplete() {
        if (
            this.uiState.value.dispatchComplete &&
            this.uiState.value.locationComplete &&
            this.uiState.value.onSceneComplete &&
            this.uiState.value.submitComplete
            ) {
            _uiState.update {
                it.copy(reportComplete = true)
            }
        } else {
            _uiState.update {
                it.copy(reportComplete = false)
            }
        }
    }
    private fun isDispatchComplete() {
        if (
            this.reportState.value.respondingTruck != null &&
            this.reportState.value.commandingOfficer != null &&
            this.reportState.value.datetimeDispatch != null &&
            this.reportState.value.emergencyCode != null
        ) {
            _uiState.update {
                it.copy(dispatchComplete = true)
            }
        } else {
            _uiState.update {
                it.copy(dispatchComplete = false)
            }
        }
        isReportComplete()
    }
    private fun isLocationComplete() {
        if (
            this.reportState.value.location != null
        ) {
            _uiState.update {
                it.copy(locationComplete = true)
            }
        } else {
            _uiState.update {
                it.copy(locationComplete = false)
            }
        }
        isReportComplete()
    }
    private fun isOnSceneComplete() {
        if (
            this.reportState.value.datetimeArrival != null &&
            this.reportState.value.policePresent != null &&
            this.reportState.value.ambulancePresent != null &&
            this.reportState.value.electricCompanyPresent != null &&
            this.reportState.value.transitPolicePresent != null &&
            this.reportState.value.notes != null
        ) {
            _uiState.update {
                it.copy(onSceneComplete = true)
            }
        } else {
            _uiState.update {
                it.copy(onSceneComplete = false)
            }
        }
        isReportComplete()
    }
    private fun isSubmitComplete() {
        if (
            this.reportState.value.datetimeReturn != null &&
            this.reportState.value.author != null
        ) {
            _uiState.update {
                it.copy(submitComplete = true)
            }
            isReportComplete()
        }
    }

    fun setRespondingTruck(input: String) {
        _reportState.update {
            it.copy(respondingTruck = input.ifEmpty { null })
        }
        isDispatchComplete()
    }

    fun setCommandingOfficer(input: String) {
        _reportState.update {
            it.copy(commandingOfficer = input.ifEmpty { null })
        }
        isDispatchComplete()
    }

    fun setDatetimeDispatch(input: Long) {
        _reportState.update {
            it.copy(datetimeDispatch = input)
        }
        isDispatchComplete()
    }
    fun setCategoryIndex(input: Int) {
        _uiState.update {
            it.copy(categoryIndex = input)
        }
    }
    fun setEmergencyCode(input: String) {
        _reportState.update {
            it.copy(emergencyCode = input.ifEmpty { null })
        }
        isDispatchComplete()
    }
    fun setLocation(input: String) {
        _reportState.update {
            it.copy(location = input.ifEmpty { null })
        }
        isLocationComplete()
    }
    fun setDatetimeArrival(input: Long) {
        _reportState.update {
            it.copy(datetimeArrival = input)
        }
        isOnSceneComplete()
    }
    fun setPoliceCheck(input: Boolean) {
        _uiState.update {
            it.copy(policeCheck = input)
        }
        if (input) setPolicePresent(null)
        else setPolicePresent("")
    }
    fun setPolicePresent(input: String?) {
        _reportState.update {
            it.copy(policePresent = input)
        }
        isOnSceneComplete()
    }
    fun setAmbulanceCheck(input: Boolean) {
        _uiState.update {
            it.copy(ambulanceCheck = input)
        }
        if (input) setAmbulancePresent(null)
        else setAmbulancePresent("")
    }
    fun setAmbulancePresent(input: String?) {
        _reportState.update {
            it.copy(ambulancePresent = input)
        }
        isOnSceneComplete()
    }
    fun setElectricCompanyCheck(input: Boolean) {
        _uiState.update {
            it.copy(electricCompanyCheck = input)
        }
        if (input) setElectricCompanyPresent(null)
        else setElectricCompanyPresent("")
    }
    fun setElectricCompanyPresent(input: String?) {
        _reportState.update {
            it.copy(electricCompanyPresent = input)
        }
        isOnSceneComplete()
    }
    fun setTransitPoliceCheck(input: Boolean) {
        _uiState.update {
            it.copy(transitPoliceCheck = input)
        }
        if (input) setTransitPolicePresent(null)
        else setTransitPolicePresent("")
    }
    fun setTransitPolicePresent(input: String?) {
        _reportState.update {
            it.copy(transitPolicePresent = input)
        }
        isOnSceneComplete()
    }
    fun setNotes(input: String) {
        _reportState.update {
            it.copy(notes = input.ifEmpty { null })
        }
        isOnSceneComplete()
    }
    fun setVictimCount(input: Int) {
        if (input == 1) {
            _reportState.update{
                it.copy(
                    victimInfo = it.victimInfo + VictimInfo(
                        statusCode = null,
                        name = null,
                        age = null,
                        identification = null
                    )
                )
            }
        } else if (input == -1) {
            _reportState.update {
                it.copy(
                    victimInfo = it.victimInfo.dropLast(1)
                )
            }
        }
    }
    fun setVictimStatusByIndex(index: Int, input: String) {
        _reportState.value.victimInfo[index].statusCode = input
    }
    fun setVictimNameByIndex(index: Int, input: String) {
        _reportState.value.victimInfo[index].name = input
    }
    fun setVictimAgeByIndex(index: Int, input: String) {
        _reportState.value.victimInfo[index].age = input
    }
    fun setVictimIdentificationByIndex(index: Int, input: String) {
        _reportState.value.victimInfo[index].identification = input
    }
    fun setDatetimeReturn(input: Long) {
        _reportState.update {
            it.copy(datetimeReturn = input)
        }
        isSubmitComplete()
    }
    fun setAuthor(input: String) {
        _reportState.update {
            it.copy(author = input.ifEmpty { null })
        }
        isSubmitComplete()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("Developer", "onCleared")
    }
}