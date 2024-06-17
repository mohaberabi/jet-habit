package com.mohaberabi.jethabbit.jethabbit.activity.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.jethabbit.features.auth.presentation.navigation.AuthRoute
import com.mohaberabi.jethabbit.features.habit.presentation.home.navigation.HomeRoute
import com.mohaberabi.jethabbit.jethabbit.activity.presentation.viewmodel.MainActivityViewModel
import com.mohaberabi.jethabbit.jethabbit.app.JetHabitComposeApp
import com.mohaberabi.jethabbit.jethabbit.app.rememberJetHabitState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainActivityViewModel by viewModels<MainActivityViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                !mainActivityViewModel.loadedData
            }
        }
        setContent {
            val rememberJetHabitState = rememberJetHabitState()
            if (mainActivityViewModel.loadedData) {
                JetHabitComposeApp(
                    jethabitState = rememberJetHabitState,
                    startRoute = if (mainActivityViewModel.hasLoggedIn) HomeRoute else AuthRoute
                )
            }

        }
    }
}

