package com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.with
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation.components.FormPersonalDataScreen
import com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation.components.PersonalScreenOne
import com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation.components.PersonalScreenThree
import com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation.components.PersonalScreenTwo
import com.hardus.trueagencyapp.main_content.home.feature_userForm.data.FormQuestion
import com.hardus.trueagencyapp.main_content.home.feature_userForm.domain.model.FormViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FormRoute(
    onFormComplete: () -> Unit,
    onNavUp: () -> Unit
) {
    // Mendapatkan instance dari FormViewModel
    val viewModel: FormViewModel = viewModel()

    // Mendapatkan data screen saat ini dari ViewModel
    val personalDataScreenData = viewModel.personalDataScreenData ?: return

    // Menangani back press
    BackHandler {
        if (!viewModel.onBackPressed()) {
            onNavUp()
        }
    }
    FormPersonalDataScreen(
        personalData = personalDataScreenData,
        isNextEnabled = viewModel.isNextEnabled,
        onClosePressed = {
            onNavUp()
        },
        onPreviousPressed = { viewModel.onPreviousPressed() },
        onNextPressed = { viewModel.onNextPressed() },
        onDonePressed = { viewModel.onDonePressed(onFormComplete) }
    ) { paddingValues ->
        val modifier = Modifier.padding(paddingValues)
        AnimatedContent(
            targetState = personalDataScreenData,
            transitionSpec = {
                slideInHorizontally() with slideOutHorizontally()
            },
            label = "formScreenDataAnimation"
        ) { targetState ->
            // Render screen yang sesuai berdasarkan form question saat ini
            when (targetState.formQuestion) {
                FormQuestion.PAGE_ONE -> PersonalScreenOne(viewModel, modifier = modifier)
                FormQuestion.PAGE_TWO -> PersonalScreenTwo(viewModel, modifier = modifier)
                FormQuestion.PAGE_THREE -> PersonalScreenThree(viewModel, modifier = modifier)
                // tambahkan konten untuk setiap halaman sesuai dengan kebutuhan Anda
            }
        }
    }
}