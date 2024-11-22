package com.degel.my_custom_content_provider

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.degel.my_custom_content_provider.presentation.MainScreen
import com.degel.my_custom_content_provider.presentation.MainViewModel
import com.degel.my_custom_content_provider.ui.theme.NotesAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesAppTheme {
                val viewModel = hiltViewModel<MainViewModel>()
                MainScreen(viewModel = viewModel)
            }
        }
    }
}

