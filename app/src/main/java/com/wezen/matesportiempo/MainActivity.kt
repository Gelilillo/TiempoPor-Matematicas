package com.wezen.matesportiempo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wezen.matesportiempo.navigation.MatesPorTiempoNavigation
import com.wezen.matesportiempo.ui.theme.MatesPorTiempoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MatesPorTiempoTheme {
                Surface (
                    modifier= Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){

                    MatesPorTiempoNavigation()
                }
            }
        }
    }
}
