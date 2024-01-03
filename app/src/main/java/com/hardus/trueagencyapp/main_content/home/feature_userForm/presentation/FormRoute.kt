package com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hardus.trueagencyapp.main_content.home.feature_userForm.data.FormQuestion
import com.hardus.trueagencyapp.main_content.home.feature_userForm.domain.model.FormViewModel
import com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation.components.FormPersonalDataScreen
import com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation.components.PersonalScreenOne
import com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation.components.PersonalScreenThree
import com.hardus.trueagencyapp.main_content.home.feature_userForm.presentation.components.PersonalScreenTwo

private const val CONTENT_ANIMATION_DURATION = 300

@Composable
fun FormRoute(
    onFormComplete: () -> Unit, onNavUp: () -> Unit
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
    FormPersonalDataScreen(personalData = personalDataScreenData,
        isNextEnabled = viewModel.isNextEnabled,
        onClosePressed = {
            onNavUp()
        },
        onPreviousPressed = { viewModel.onPreviousPressed() },
        onNextPressed = { viewModel.onNextPressed() },
        onDonePressed = { viewModel.onDonePressed(onFormComplete) }) { paddingValues ->
        val modifier = Modifier.padding(paddingValues)
        AnimatedContent(
            targetState = personalDataScreenData, transitionSpec = {
                val animationSpec: TweenSpec<IntOffset> = tween(CONTENT_ANIMATION_DURATION)

                val direction = getTransitionDirection(
                    initialIndex = initialState.formIndex,
                    targetIndex = targetState.formCount,
                )

                slideIntoContainer(
                    towards = direction,
                    animationSpec = animationSpec,
                ) togetherWith slideOutOfContainer(
                    towards = direction, animationSpec = animationSpec
                )
            }, label = "formScreenDataAnimation"
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

private fun getTransitionDirection(
    initialIndex: Int, targetIndex: Int
): AnimatedContentTransitionScope.SlideDirection {
    return if (targetIndex > initialIndex) {
        // Going forwards in the survey: Set the initial offset to start
        // at the size of the content so it slides in from right to left, and
        // slides out from the left of the screen to -fullWidth
        AnimatedContentTransitionScope.SlideDirection.Left
    } else {
        // Going back to the previous question in the set, we do the same
        // transition as above, but with different offsets - the inverse of
        // above, negative fullWidth to enter, and fullWidth to exit.
        AnimatedContentTransitionScope.SlideDirection.Right
    }
}
