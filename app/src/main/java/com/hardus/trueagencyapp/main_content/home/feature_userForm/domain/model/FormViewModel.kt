package com.hardus.trueagencyapp.main_content.home.feature_userForm.domain.model

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hardus.trueagencyapp.main_content.home.feature_userForm.data.FormQuestion
import com.hardus.trueagencyapp.main_content.home.feature_userForm.data.PersonalData
import java.time.LocalDate

class FormViewModel() : ViewModel() {

    private val formOrder: List<FormQuestion> = listOf(
        FormQuestion.PAGE_ONE, FormQuestion.PAGE_TWO, FormQuestion.PAGE_THREE
    )

    private var formIndex = 0

    // Page One
    private val _fullNameResponse = mutableStateOf("")
    val fullNameResponse: String
        get() = _fullNameResponse.value

    private val _addressResponse = mutableStateOf("")
    val addressResponse: String
        get() = _addressResponse.value

    private val _dateOfBirth = mutableStateOf<LocalDate>(LocalDate.now())
    val dateOfBirth: LocalDate
        get() = _dateOfBirth.value

    private val _isBusinessPartner = mutableStateOf<Boolean?>(null)

    private val _isLeader = mutableStateOf(false)

    private val _leaderTitle = mutableStateOf("")
    val leaderTitle: String
        get() = _leaderTitle.value

    val isLeader: Boolean
        get() = _isLeader.value
    val isBusinessPartner: Boolean?
        get() = _isBusinessPartner.value

    fun onFullNameChange(newName: String) {
        _fullNameResponse.value = newName
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onAddressChange(newAddress: String) {
        _addressResponse.value = newAddress
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onDateOfBirthChanged(newDate: LocalDate) {
        _dateOfBirth.value = newDate
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onRoleChanged(isBusinessPartner: Boolean) {
        _isBusinessPartner.value = isBusinessPartner
        _isLeader.value = !isBusinessPartner // asumsi jika bukan business partner maka leader
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onLeaderTitleChanged(newTitle: String) {
        _leaderTitle.value = newTitle
    }

    // Page Two
    private val _agentCodeResponse = mutableStateOf("")
    val agentCodeResponse: String
        get() = _agentCodeResponse.value

    private val _ajjExamDateResponse = mutableStateOf<LocalDate>(LocalDate.now())
    val ajjExamDateResponse: LocalDate
        get() = _ajjExamDateResponse.value

    private val _aasiExamDateResponse = mutableStateOf<LocalDate>(LocalDate.now())
    val aasiExamDateResponse: LocalDate
        get() = _aasiExamDateResponse.value

    private val _selectedUnit = mutableStateOf<String?>(null)
    val selectedUnit: String?
        get() = _selectedUnit.value

    private val _unitOptions = mutableStateListOf("Nama 1", "Nama 2", "Nama 3", "Nama 4")
    val unitOptions: List<String>
        get() = _unitOptions

    fun onAgentCodeChanged(newCode: String) {
        _agentCodeResponse.value = newCode
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onAjjExamDateChanged(newDate: LocalDate) {
        _ajjExamDateResponse.value = newDate
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onAasiExamDateChanged(newDate: LocalDate) {
        _aasiExamDateResponse.value = newDate
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onUnitSelected(unit: String) {
        _selectedUnit.value = unit
        _isNextEnabled.value = getIsNextEnabled()
    }


    // Page Three
    private val _visiResponse = mutableStateOf("")
    val visiResponse: String
        get() = _visiResponse.value

    private val _lifeMottoResponse = mutableStateOf("")
    val lifeMottoResponse: String
        get() = _lifeMottoResponse.value

    fun onVisiChanged(newVisi: String) {
        _visiResponse.value = newVisi
        _isNextEnabled.value = getIsNextEnabled()
    }

    fun onLifeMottoChanged(newMotto: String) {
        _lifeMottoResponse.value = newMotto
        _isNextEnabled.value = getIsNextEnabled()
    }

    // ----- Form status exposed as State -----

    private val _personalDataScreenData = mutableStateOf(createPersonalDataScreenData())
    val personalDataScreenData: PersonalData?
        get() = _personalDataScreenData.value

    private val _isNextEnabled = mutableStateOf(false)
    val isNextEnabled: Boolean
        get() = _isNextEnabled.value


    fun onBackPressed(): Boolean {
        if (formIndex == 0) {
            return false
        }
        formPagePersonalData(formIndex - 1)
        return true
    }

    fun onPreviousPressed() {
        if (formIndex == 0) {
            throw IllegalStateException("onPreviousPressed when on formQuestion 0")
        }
        formPagePersonalData(formIndex - 1)
    }

    fun onNextPressed() {
        formPagePersonalData(formIndex + 1)
    }

    fun onDonePressed(onFormComplete: () -> Unit) {
        onFormComplete()
    }

    private fun formPagePersonalData(newFormIndex: Int) {
        formIndex = newFormIndex
        _isNextEnabled.value = getIsNextEnabled()
        _personalDataScreenData.value = createPersonalDataScreenData()
    }

    private fun getIsNextEnabled(): Boolean {
        return when (formOrder[formIndex]) {
            FormQuestion.PAGE_ONE -> {
                _fullNameResponse.value.isNotEmpty() && _addressResponse.value.isNotEmpty() && _dateOfBirth.value != LocalDate.now() && _isBusinessPartner.value != null // Memastikan bahwa pengguna telah memilih salah satu opsi
            }

            FormQuestion.PAGE_TWO -> {
                _agentCodeResponse.value.isNotEmpty() && _aasiExamDateResponse.value != LocalDate.now() && _ajjExamDateResponse.value != LocalDate.now() && _selectedUnit.value != null
            }

            FormQuestion.PAGE_THREE -> {
                _visiResponse.value.isNotEmpty() && _lifeMottoResponse.value.isNotEmpty()
            }

            else -> false
        }
    }


    private fun createPersonalDataScreenData(): PersonalData {
        return PersonalData(
            formIndex = formIndex,
            formCount = formOrder.size,
            shouldShowPreviousButton = formIndex > 0,
            shouldShowDoneButton = formIndex == formOrder.size - 1,
            formQuestion = formOrder[formIndex],
        )
    }
}

