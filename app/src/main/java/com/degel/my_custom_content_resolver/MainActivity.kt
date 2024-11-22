package com.degel.my_custom_content_resolver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.degel.my_custom_content_resolver.ui.theme.NotesCompanionAppTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesCompanionAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MainScreen(viewModel = mainViewModel)
                }
            }
        }
        lifecycleScope.launch {
            mainViewModel.notes.update { getAllNotesUsingContentResolver(this@MainActivity) }
        }

        lifecycleScope.launch {
            this@MainActivity.contentResolver.observe(this@MainActivity, CONTENT_URI)
                .collectLatest {notes->
                    mainViewModel.notes.update { notes }
                }
        }
    }
}

