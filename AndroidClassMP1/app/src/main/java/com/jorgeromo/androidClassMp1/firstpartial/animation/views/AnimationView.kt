package com.jorgeromo.androidClassMp1.firstpartial.animation.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.airbnb.lottie.compose.*
import com.jorgeromo.androidClassMp1.R
import com.jorgeromo.androidClassMp1.firstpartial.animation.viewmodel.AnimationViewModel

@Composable
fun AnimatedView(
    animationResId: Int,
    isPlaying: Boolean = true,
    iterations: Int = LottieConstants.IterateForever
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationResId))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = iterations,
        isPlaying = isPlaying
    )

    LottieAnimation(
        composition = composition,
        progress = { progress }
    )
}

@Composable
fun AnimationView(viewModel: AnimationViewModel = viewModel()) {
    val uiState = viewModel.uiState.collectAsState()

    if (uiState.value.isLoading) {
        AnimatedView(animationResId = R.raw.sandyloading)
    }
}