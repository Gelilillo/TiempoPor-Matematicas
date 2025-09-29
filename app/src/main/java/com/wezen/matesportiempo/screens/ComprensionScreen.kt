package com.wezen.matesportiempo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wezen.matesportiempo.data.GeminiAI
import com.wezen.matesportiempo.ui.theme.Fondo1
import com.wezen.matesportiempo.ui.theme.Fondo2



@Composable
fun ComprensionScreen (userId: Int, onBack: () -> Unit){

    var historia by remember { mutableStateOf("") }
    var respuesta by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxWidth()
        .background(
            brush = Brush.verticalGradient(
                colors = listOf( Fondo1,Fondo2

                )
            )
        ))
    {

        Spacer(modifier = Modifier.height(height = 8.dp))

        Button(onClick = {
            GeminiAI.sendPrompt() { hist, resp ->
                println("GEMINI Historia: $hist")
                println("GEMINI Respuesta: $resp")

                // Actualizar las variables de estado
                historia = hist ?: "Error al cargar historia"
                respuesta = resp ?: "Error al cargar respuesta"
            }
        }) {
            Text(text = "Enviar")
        }
        Spacer(modifier = Modifier.height(height = 16.dp))
        Text(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
            textAlign = TextAlign.Justify,
            fontSize = 30.sp,
            text = historia,
            color = Color.White)
        Spacer(modifier = Modifier.height(height = 16.dp))
        //Text(text = respuesta, color = Color.White )
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Column() {
                Button(onClick = { onBack() }) { Text(text = "Sumar") }
                Button(onClick = { onBack() }) { Text("Multiplicar") }
            }
            Column() {
                Button(onClick = { onBack() }) { Text(text = "Restar") }
                Button(onClick = { onBack() }) { Text("Dividir") }
            }

        }
    }



}

