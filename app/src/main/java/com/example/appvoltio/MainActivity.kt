package com.example.appvoltio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.appvoltio.ui.navigation.AppNavigation
import com.example.appvoltio.ui.theme.AppVoltioTheme
import com.example.appvoltio.viewmodel.RegistroEquipoViewModel
import com.example.appvoltio.viewmodel.RegistroEquipoViewModelFactory
import com.example.appvoltio.viewmodel.ListaEquiposViewModel
import com.example.appvoltio.viewmodel.ListaEquiposViewModelFactory

class MainActivity : ComponentActivity() {

    private val registroEquipoViewModel: RegistroEquipoViewModel by viewModels {
        RegistroEquipoViewModelFactory(application)
    }

    private val listaEquiposViewModel: ListaEquiposViewModel by viewModels {
        ListaEquiposViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppVoltioTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        AppNavigation(registroEquipoViewModel, listaEquiposViewModel)
                    }
                }
            }
        }
    }
}
