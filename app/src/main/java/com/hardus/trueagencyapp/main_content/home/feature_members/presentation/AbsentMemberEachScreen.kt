package com.hardus.trueagencyapp.main_content.home.feature_members.presentation

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.hardus.trueagencyapp.main_content.home.feature_members.presentation.components.AbsentContentMember
import com.hardus.trueagencyapp.ui.theme.TrueAgencyAppTheme

@Composable
fun AbsentMemberEachScreen(
    navBackStackEntry: NavBackStackEntry,
    viewModel: MembersViewModel,
    onNavigate: () -> Unit,
) {

    val memberIdAsal = navBackStackEntry.arguments?.getString("memberIdAsal") ?: ""
    val memberFullname = navBackStackEntry.arguments?.getString("memberFullname") ?: ""

    val uiStateTab by viewModel.uiStateTab.collectAsState()
    val userName = Firebase.auth.currentUser?.displayName

    LaunchedEffect(userName) {
        userName?.let { viewModel.loadScansForUnit(it, memberIdAsal) }
    }

    Scaffold(topBar = {
        TopAppBarMembersAbsent(
            onNavigateBack = onNavigate,
            nameAppBar = memberFullname
        )
    }, content = { paddingValue ->
        AbsentContentMember(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue),
            onBreedSelected = { selectedBreed ->
                viewModel.setSelectedBreedMember(selectedBreed)
            },
            selectedBreed = uiStateTab.selectedBreedMember,
            absentBreeds = uiStateTab.absentBreedsMember,
        )
    })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBarMembersAbsent(
    onNavigateBack: () -> Unit,
    nameAppBar: String,
    modifier: Modifier = Modifier
) {


    CenterAlignedTopAppBar(modifier = modifier, colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.onPrimary,
        titleContentColor = MaterialTheme.colorScheme.onBackground,
    ), title = {
        Text(
            text = nameAppBar,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }, navigationIcon = {
        IconButton(onClick = onNavigateBack) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(modifier = Modifier.padding(2.dp))

    }
    )
}

@Preview
@Composable
fun CheckAbsentMemberEachScreen() {
    TrueAgencyAppTheme {

    }
}