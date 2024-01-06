package com.hardus.trueagencyapp.main_content.home.feature_userForm.domain.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hardus.trueagencyapp.main_content.home.feature_userForm.data.DirectionPage
import com.hardus.trueagencyapp.main_content.home.feature_userForm.data.FormQuestion
import com.hardus.trueagencyapp.main_content.home.feature_userForm.data.PersonalData
import com.hardus.trueagencyapp.main_content.home.feature_userForm.data.UserOption
import com.hardus.trueagencyapp.main_content.home.presentation.util.toTimestamp
import com.hardus.trueagencyapp.util.FirestoreAuth
import com.hardus.trueagencyapp.util.FirestoreService
import java.time.LocalDate

class FormViewModel() : ViewModel() {

    private val formOrder: List<FormQuestion> = listOf(
        FormQuestion.PAGE_ONE, FormQuestion.PAGE_TWO, FormQuestion.PAGE_THREE
    )

    private var formIndex = 0

    private val _userOptionsMapping = mutableMapOf<String, String>()
    private val _selectedUserId = mutableStateOf<String?>(null)

    private val _personalData = mutableStateOf<PersonalData?>(null)
    val personalData: State<PersonalData?> = _personalData

    init {
        // Inisialisasi data ketika ViewModel dibuat
        loadPersonalDataForCurrentUser()
    }

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

    private val _unitOptions = mutableStateListOf<String>()
    val unitOptions: List<String>
        get() = _unitOptions

    private val _selectedUnit = mutableStateOf<String?>(null)
    val selectedUnit: String?
        get() = _selectedUnit.value

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

    fun onUnitSelected(displayName: String) {
        val userId = _userOptionsMapping[displayName]
        // Simpan userId yang terkait dengan displayName ke dalam _selectedUserId
        _selectedUnit.value = displayName
        _selectedUserId.value = userId
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
    val directionPage: DirectionPage?
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
        savePersonalDataToFirestore { success ->
            if (success) {
                onFormComplete() // Panggilan ini akan menavigasi pengguna keluar dari form atau menampilkan pesan sukses
            } else {
                // Tangani kasus ketika penyimpanan gagal, misalnya dengan menampilkan pesan error
            }
        }
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

        }
    }


    private fun createPersonalDataScreenData(): DirectionPage {
        return DirectionPage(
            formIndex = formIndex,
            formCount = formOrder.size,
            shouldShowPreviousButton = formIndex > 0,
            shouldShowDoneButton = formIndex == formOrder.size - 1,
            formQuestion = formOrder[formIndex],
        )
    }

    private fun loadPersonalDataForCurrentUser() {
        val userId = FirestoreAuth.db.currentUser?.uid // ID dari pengguna yang sedang login
        val firestore = FirestoreService.db

        // Menggunakan userId untuk mengambil data spesifik pengguna
        firestore.collection("usersData")
            .document(userId!!)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                _personalData.value =
                    documentSnapshot.toObject(PersonalData::class.java) // Convert Firestore document to PersonalData object
            }
            .addOnFailureListener {
                // Tangani error
            }
    }

    fun savePersonalDataToFirestore(onComplete: (Boolean) -> Unit) {
        val userId = FirestoreAuth.db.currentUser?.uid
        val userIda = _selectedUserId.value
        if (userId == null) {
            onComplete(false)
            return
        }

        val personalData = PersonalData(
            userId = userIda,
            fullName = _fullNameResponse.value,
            address = _addressResponse.value,
            dateOfBirth = _dateOfBirth.value.toTimestamp(), // Konversi LocalDate ke Date atau Timestamp
            leaderStatus = if (_isLeader.value) "Leader" else "Business Partner",
            leaderTitle = _leaderTitle.value,
            isBusinessPartner = _isBusinessPartner.value ?: false,
            agentCode = _agentCodeResponse.value,
            ajjExamDate = _ajjExamDateResponse.value.toTimestamp(), // Gunakan konversi yang sesuai untuk LocalDate ke Date
            aasiExamDate = _aasiExamDateResponse.value.toTimestamp(), // Gunakan konversi yang sesuai untuk LocalDate ke Date
            selectedUnit = _selectedUnit.value.orEmpty(),
            vision = _visiResponse.value,
            lifeMoto = _lifeMottoResponse.value
        )

        val firestore = FirestoreService.db
        firestore.collection("usersData")
            .document(userId)
            .set(personalData)
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }

    fun fetchUnitOptions() {
        val firestore = FirestoreService.db
        firestore.collection("profil")
            .get()
            .addOnSuccessListener { documents ->
                val userOptions = documents.mapNotNull { doc ->
                    val userId = doc.id
                    val userD = doc.getString("id_user") ?: return@mapNotNull null
                    val displayName = doc.getString("name_user") ?: return@mapNotNull null
                    UserOption(userId = userId, userD = userD, displayName = displayName)
                }
                userOptions.forEach { userOption ->
                    _userOptionsMapping[userOption.displayName] = userOption.userId
                }
                // Update the state here with the new list
                _unitOptions.clear()
                _unitOptions.addAll(userOptions.map { it.displayName })
                // You might need to store the mapping of names to IDs as well
            }
            .addOnFailureListener {
                // Handle the error here
            }
    }
}

