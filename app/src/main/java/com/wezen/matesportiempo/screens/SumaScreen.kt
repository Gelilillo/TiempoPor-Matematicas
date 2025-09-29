package com.wezen.matesportiempo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wezen.matesportiempo.ui.theme.Fondo1
import com.wezen.matesportiempo.ui.theme.Fondo2
import kotlin.random.Random

@Composable
fun SumaScreen(userId: Int, onBack: () -> Unit) {

    var numCifras = 5
    var conLlevada = false

    // ESTADOS PARA LOS VALORES GENERADOS
    var a by remember { mutableIntStateOf(0) }
    var b by remember { mutableIntStateOf(0) }
    var c by remember { mutableIntStateOf(0) }

    // TRIGGER PARA REGENERAR
    var regenerarTrigger by remember { mutableIntStateOf(0) }

    // FUNCIÓN PARA REGENERAR
    val regenerarValores = remember {
        {
            regenerarTrigger++
        }
    }

    // GENERAR VALORES - Se ejecuta al cargar Y cuando cambia el trigger
    LaunchedEffect(regenerarTrigger) {
        val (nuevoA, nuevoB, nuevoC) = generarNumeros(numCifras, conLlevada)
        a = nuevoA
        b = nuevoB
        c = nuevoC
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            //.padding(16.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf( Fondo1,Fondo2

                    )
                )
            )
    ) {
        // Header con botón atrás
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Text("⬅️", fontSize = 24.sp)
            }
            Text(
                text = "➕ Sumas",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
// --- ICONO DE LLEVADA ---
        if (conLlevada) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {

                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Face,
                            contentDescription = "Llevada",
                            tint = Color.Red,
                            modifier = Modifier.size(24.dp)
                        )
                    }

            }

        }
        //DIBUJAMOS LA SUMA

        val str1 = a.toString().padStart(numCifras, ' ')
        val str2 = b.toString().padStart(numCifras, ' ')


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.Top
            ) {
                // PRIMERA COLUMNA: Signo + en la segunda fila
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Espacio vacío en la primera fila
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(40.dp)
                    )

                    // Signo + en la segunda fila
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "+",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF4CAF50)
                        )
                    }

                    // Línea separadora
                    Box(
                        modifier = Modifier
                            .width(46.dp)
                            .height(2.dp)
                            .background(Color.Black)
                    )

                    // Espacio vacío para alineación
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(60.dp)
                    )
                }

                // COLUMNAS DE NÚMEROS
                repeat(numCifras) { index ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // PRIMERA CIFRA
                        Box(
                            modifier = Modifier
                                .width(50.dp)
                                .height(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            val char1 = str1.getOrNull(index)
                            if (char1 != null && char1 != ' ') {
                                Text(
                                    text = char1.toString(),
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        // SEGUNDA CIFRA
                        Box(
                            modifier = Modifier
                                .width(50.dp)
                                .height(40.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            val char2 = str2.getOrNull(index)
                            if (char2 != null && char2 != ' ') {
                                Text(
                                    text = char2.toString(),
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }

                        // LÍNEA SEPARADORA
                        Box(
                            modifier = Modifier
                                .width(46.dp)
                                .height(2.dp)
                                .background(Color.Black)
                        )

                        // CASILLA PARA RESULTADO
                        var valor by remember { mutableStateOf("") }

                        OutlinedTextField(
                            value = valor,
                            onValueChange = { nuevo ->
                                if (nuevo.length <= 1 && nuevo.all { it.isDigit() }) {
                                    valor = nuevo
                                }
                            },
                            singleLine = true,
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontSize = 24.sp,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number
                            ),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFFE53935),
                                unfocusedBorderColor = Color(0xFFE53935),
                                cursorColor = Color(0xFF2196F3)
                            ),
                            modifier = Modifier
                                .width(50.dp)
                                .height(60.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Texto instructivo
            Text(
                text = "Solo un número por casilla",
                fontSize = 14.sp,
                color = Color(0xFF666666),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {//TODO corregir y sumar estadisticas de la suma.
                 },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp).height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {

                Text("Corregir", color = Color.Cyan,modifier = Modifier.padding(vertical = 8.dp))
            }
        }

    }
}

fun generarNumeros(numCifras: Int, permitirLlevada: Boolean): Triple<Int, Int, Int> {
    require(numCifras > 0) { "El número de cifras debe ser mayor que 0" }

    val digitos1 = mutableListOf<Int>()
    val digitos2 = mutableListOf<Int>()
    val digitos3 = mutableListOf<Int>()

    repeat(numCifras) {
        var d1: Int
        var d2: Int
        var d3: Int

        if (permitirLlevada) {
            // cualquier dígito 0–9
            d1 = Random.nextInt(0, 10)
            d2 = Random.nextInt(0, 10)
        } else {
            // sin llevada -> suma de dígitos < 10
            d1 = Random.nextInt(0, 10)
            d2 = Random.nextInt(0, 10 - d1) // aseguramos que d1+d2 < 10
        }
        d3= d1+d2
        digitos1.add(d1)
        digitos2.add(d2)
        digitos3.add(d3)
    }

    // Convertimos listas de dígitos en números
    val num1 = digitos1.joinToString("").toInt()
    val num2 = digitos2.joinToString("").toInt()
    val num3 = digitos3.joinToString("").toInt()

    return Triple(num1 , num2, num3)
}