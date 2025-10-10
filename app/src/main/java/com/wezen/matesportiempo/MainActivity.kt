package com.wezen.matesportiempo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import com.wezen.matesportiempo.data.Database.DatabaseHelper
import com.wezen.matesportiempo.navigation.MatesPorTiempoNavigation
import com.wezen.matesportiempo.ui.theme.MatesPorTiempoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbHelper = DatabaseHelper.getInstance(this)

        // Esto inicializa la base si no existe aún
        val db = dbHelper.writableDatabase

        dbHelper.createUser("Juan")

        print(dbHelper.getAllUsers())

        setContent {
            MatesPorTiempoTheme {
                Surface (
                    modifier= Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    Log.d(("MainActivity"), "empezamos¡")
                    MatesPorTiempoNavigation(dbHelper)
                }
            }
        }
    }
}
