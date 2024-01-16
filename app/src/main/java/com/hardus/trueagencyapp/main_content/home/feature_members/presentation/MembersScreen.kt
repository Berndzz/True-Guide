package com.hardus.trueagencyapp.main_content.home.feature_members.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.hardus.trueagencyapp.R
import com.hardus.trueagencyapp.main_content.home.feature_userForm.data.PersonalData
import com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation.formatToStrinAg
import com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation.toDateA
import com.hardus.trueagencyapp.util.MemberContentType
import kotlinx.coroutines.delay


@Composable
fun MembersScreen(
    onNavigate: () -> Unit,
    windowSize: WindowWidthSizeClass,
    onBackPressed: () -> Unit,
    viewModel: MembersViewModel
) {
    val uiState = viewModel.uiStateMember.collectAsState().value
    val isLoading = viewModel.isLoading.value
    val errorMessage = viewModel.errorMessage.collectAsState().value
    val userName = Firebase.auth.currentUser?.displayName

    val contentType = when (windowSize) {
        WindowWidthSizeClass.Compact, WindowWidthSizeClass.Medium -> MemberContentType.ListOnly

        WindowWidthSizeClass.Expanded -> MemberContentType.ListAndDetail
        else -> MemberContentType.ListOnly
    }

    LaunchedEffect(userName) {
        userName?.let { viewModel.loadMembersForUnit(userName) }
    }

    LaunchedEffect(uiState.memberList, isLoading) {
        delay(3000) // Tunggu selama 3 detik
        if (uiState.memberList.isEmpty() && isLoading) {
            viewModel.isLoading.value = false // Hentikan loading jika daftar masih kosong
            viewModel.setErrorMessage("Data tidak ada") // Set pesan error atau status di viewModel
        }
    }

    Scaffold(topBar = {
        TopAppBarMembers(
            onBackButtonClick = { viewModel.navigateToListPage() },
            onNavigateBack = onNavigate,
            isShowingListPage = uiState.isShowingMemberListPage,
            windowSize = windowSize,
            viewModel = viewModel
        )
    }, content = { paddingValue ->
        if (isLoading) {
            // Tampilkan indikator loading
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.memberList.isEmpty()) {
            Text("Data tidak ada")
        } else {
            // Tampilkan daftar member
            if (contentType == MemberContentType.ListAndDetail) {
                uiState.currentMember?.let {
                    MemberListAndDetail(
                        members = uiState.memberList,
                        selectedMember = it,
                        onMemberClick = {
                            viewModel.updateCurrentMember(it)
                        },
                        onBackPressed = onBackPressed,
                        contentPadding = paddingValue,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            } else {
                if (uiState.isShowingMemberListPage) {
                    MemberList(
                        members = uiState.memberList,
                        onMemberClick = {
                            viewModel.updateCurrentMember(it)
                            viewModel.navigateToDetailPage()
                        },
                        contentPadding = paddingValue,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = dimensionResource(id = R.dimen.padding_medium),
                                start = dimensionResource(id = R.dimen.padding_medium),
                                end = dimensionResource(id = R.dimen.padding_medium)
                            )
                    )
                } else {
                    if (uiState.isShowingMemberListPage) {
                        MemberList(
                            members = uiState.memberList,
                            onMemberClick = {
                                viewModel.updateCurrentMember(it)
                                viewModel.navigateToDetailPage()
                            },
                            contentPadding = paddingValue,
                            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_medium))
                        )
                    } else {
                        uiState.currentMember?.let { currentMember ->
                            MemberDetail(
                                selectedMember = currentMember,
                                onBackPressed = { viewModel.navigateToListPage() },
                                contentPadding = paddingValue
                            )
                        } ?: run {
                            Text("No Member Selected")
                        }
                    }
                }
            }
        }
        errorMessage?.let {
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    })
}

@Composable
private fun MemberListAndDetail(
    members: List<PersonalData>,
    selectedMember: PersonalData,
    onMemberClick: (PersonalData) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    Row(
        modifier = modifier
    ) {
        MemberList(
            members = members,
            onMemberClick = onMemberClick,
            modifier = Modifier
                .weight(2f)
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_medium)
                )
        )
        MemberDetail(
            selectedMember = selectedMember,
            onBackPressed = onBackPressed,
            contentPadding = contentPadding,
            modifier = Modifier.weight(3f)
        )
    }
}

@Composable
private fun MemberDetail(
    selectedMember: PersonalData,
    onBackPressed: () -> Unit,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier
) {
    BackHandler {
        onBackPressed()
    }
    val scrollState = rememberScrollState()
    val layoutDirection = LocalLayoutDirection.current
    Box(
        modifier = modifier
            .verticalScroll(state = scrollState)
            .padding(top = contentPadding.calculateTopPadding())
    ) {
        Column(
            modifier = Modifier.padding(
                bottom = contentPadding.calculateTopPadding(),
                start = contentPadding.calculateStartPadding(layoutDirection),
                end = contentPadding.calculateEndPadding(layoutDirection)
            )
        ) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(MaterialTheme.shapes.medium)
            ) {
                Column {
                    Text(
                        text = "Nama Lengkap: ${
                            selectedMember.fullName
                        }", fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                    Text("Kode Agent: ${selectedMember.agentCode}")
                    Text(
                        "Tanggal Ujian AAJI: ${
                            selectedMember.ajjExamDate?.toDateA()
                                ?.formatToStrinAg() ?: stringResource(R.string.tidak_tersedia)
                        }"
                    )
                    Text(
                        "Tanggal Ujian AASI: ${
                            selectedMember.aasiExamDate?.toDateA()
                                ?.formatToStrinAg() ?: stringResource(R.string.tidak_tersedia)
                        }"
                    )
                    Text("Unit: ${selectedMember.selectedUnit}")
                    Spacer(modifier = Modifier.padding(20.dp))
                    Text("Alamat: ${selectedMember.address}")
                    Text(
                        "Tanggal Lahir: ${
                            selectedMember.dateOfBirth?.toDateA()
                                ?.formatToStrinAg() ?: stringResource(R.string.tidak_tersedia)
                        }"
                    )
                    Text(
                        "Partner Business / Leader: ${
                            selectedMember.leaderStatus
                        }"
                    )
                    if (selectedMember.leaderTitle.isNotEmpty()) {
                        Text(
                            "Status Leader: ${
                                selectedMember.leaderTitle
                            }"
                        )
                    }

                    Spacer(modifier = Modifier.padding(20.dp))
                    Text("Visi: ${selectedMember.vision}")
                    Text("Moto Hidup: ${selectedMember.lifeMoto}")
                }
            }
        }
    }
}

@Composable
private fun MemberList(
    members: List<PersonalData>,
    onMemberClick: (PersonalData) -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp),
) {
    LazyColumn(
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_small)),
        modifier = modifier
    ) {
        items(members, key = { member -> member.fullName }) { member ->
            MemberItem(member = member, onMemberClick = onMemberClick)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MemberItem(
    member: PersonalData,
    onMemberClick: (PersonalData) -> Unit,
    modifier: Modifier = Modifier
) {
// Layout untuk tiap item member
    // Misalnya, Card dengan beberapa Text untuk menampilkan nama dan detail member
    Card(modifier = modifier,
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = { onMemberClick(member) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Text(text = member.fullName)
            }
            Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = "Go to details",
                modifier = Modifier.size(24.dp) // Atur ukuran ikon jika perlu
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarMembers(
    onBackButtonClick: () -> Unit,
    onNavigateBack: () -> Unit,
    isShowingListPage: Boolean,
    windowSize: WindowWidthSizeClass,
    viewModel: MembersViewModel,
    modifier: Modifier = Modifier
) {
    val isShowingDetailPage = windowSize != WindowWidthSizeClass.Expanded && !isShowingListPage
    val uiState by viewModel.uiStateMember.collectAsState()

    val displayedTitle = if (!isShowingDetailPage) {
        stringResource(id = R.string.members)
    } else {
        uiState.currentMember?.fullName
    }

    CenterAlignedTopAppBar(modifier = modifier, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.onPrimary,
        titleContentColor = MaterialTheme.colorScheme.onBackground,
    ), title = {
        Text(
            text = displayedTitle
                ?: stringResource(id = R.string.default_title), // Default title jika null
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }, navigationIcon = if (!isShowingListPage) {
        {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
            Spacer(modifier = Modifier.padding(2.dp))

        }
    } else {
        {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    })
}

@Preview
@Composable
fun CheckMemberScreen() {
    val viewModel = MembersViewModel()
    MembersScreen(
        onNavigate = {},
        windowSize = WindowWidthSizeClass.Compact,
        onBackPressed = { /*TODO*/ },
        viewModel = viewModel
    )
}
