package com.wezen.matesportiempo.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.Log
import com.wezen.matesportiempo.ui.theme.Fondo1
import com.wezen.matesportiempo.ui.theme.Fondo2
import kotlin.random.Random

@Composable
fun SumaScreen(userId: Int, onBack: () -> Unit) {

    var numCifras = 2
    var conLlevada = false
    var sumaCorregida by remember { mutableStateOf(false) }
    var erroresPorCifra by remember { mutableStateOf(listOf<Boolean>()) }

    val contexto = LocalContext.current

    //Para saber los valores de los cuadros de las respuestas
    // Recordamos los valores de los 4 campos
    val valores = remember {
        mutableStateListOf(*Array(numCifras + 1) { "" })
    }

    // ESTADOS PARA LOS VALORES GENERADOS
    var a by remember { mutableIntStateOf(0) } //Sumando1
    var b by remember { mutableIntStateOf(0) } //Sumanddo2
    var c by remember { mutableIntStateOf(0) } //Resultado

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
        val (sumando1, sumando2, resultado) = generarNumeros(numCifras, conLlevada)
        a = sumando1
        b = sumando2
        c = resultado

        sumaCorregida = false
        erroresPorCifra = List(numCifras + 1) { false }
        for (i in valores.indices) {
            valores[i] = ""
        }
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
                            .width(50.dp)
                            .height(2.dp)
                            .background(Color.Black)
                    )

                    // Espacio entre la linea y los
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(2.dp)
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
                                .width(50.dp)
                                .height(2.dp)
                                .background(Color.Black)
                        )
                    }
                }

            }
            Row (
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.Top
            ){
                repeat(numCifras+1) { index ->

                    // CASILLA PARA RESULTADO
                    Row {
                        OutlinedTextField(
                            value = valores[index],

                            onValueChange = { nuevo ->
                                if (nuevo.length <= 1 && nuevo.all { it.isDigit() }) {
                                    valores[index] = nuevo
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
                                focusedBorderColor = if (erroresPorCifra.getOrNull(index) == true)
                                    Color(0xFFFF0000) else Color(0xFF6C4A4A),
                                unfocusedBorderColor = if (erroresPorCifra.getOrNull(index) == true)
                                    Color(0xFFFF0000) else Color(0xFF6C4A4A),
                                cursorColor = Color(0xFF2196F3)
                            ),
                            modifier = Modifier
                                .width(50.dp)
                                .height(60.dp)
                        )
                    }
                }
            }

            // Texto instructivo
            Text(
                text = "Solo un número por casilla",
                fontSize = 14.sp,
                color = Color(0xFF666666),
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (!sumaCorregida) {
                Button(
                    onClick = {
                        val (todosCorrectos, errores) = corregirSuma(valores, c)
                        erroresPorCifra = errores

                        if (todosCorrectos) {
                            sumaCorregida = true
                            Toast.makeText(contexto, "¡Muy bien! Suma correcta ✓", Toast.LENGTH_SHORT).show()

                        } else {
                            val numErrores = errores.count { it }
                            Toast.makeText(
                                contexto,
                                "Hay $numErrores error${if (numErrores > 1) "es" else ""} marcado${if (numErrores > 1) "s" else ""} en rojo",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) {
                    Text("Corregir", color = Color.White, modifier = Modifier.padding(vertical = 8.dp))
                }
            } else {
                // Botones cuando la suma está correcta
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = onBack,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF757575))
                    ) {
                        Text("Atrás", color = Color.White, modifier = Modifier.padding(vertical = 8.dp))
                    }

                    Button(
                        onClick = {
                            regenerarValores()
                            sumaCorregida = false

                                  },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
                    ) {
                        Text("Otra Suma!", color = Color.White, modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
            }
        }

    }
}

fun generarNumeros(numCifras: Int, permitirLlevada: Boolean): Triple<Int, Int, Int> {
    require(numCifras > 0) { "El número de cifras debe ser mayor que 0" }

    val digitos1 = mutableListOf<Int>()
    val digitos2 = mutableListOf<Int>()
    var resultado: Int

    repeat(numCifras) {
        var d1: Int
        var d2: Int

        if (permitirLlevada) {
            // cualquier dígito 0–9
            d1 = Random.nextInt(0, 10)
            d2 = Random.nextInt(0, 10)
        } else {
            // sin llevada -> suma de dígitos < 10
            d1 = Random.nextInt(0, 10)
            d2 = Random.nextInt(0, 10 - d1) // aseguramos que d1+d2 < 10
        }
        //Para asegurar que el numero de arriba sea siempre mas grande que el de abajo.
        d1=maxOf(d2,d1)
        d2=minOf(d2,d1)


        digitos1.add(d1)
        digitos2.add(d2)

    }



    // Convertimos listas de dígitos en números
    val num1 = digitos1.joinToString("").toInt()
    val num2 = digitos2.joinToString("").toInt()

    resultado = num1 + num2

    Log.d("Suma", "Los numero son $num1 y $num2 el resultado es: $resultado")

    return Triple(num1 , num2, resultado)
}

fun corregirSuma(valores: List<String>, resultadoCorrecto: Int): Pair<Boolean, List<Boolean>> {
    // Convertir el resultado correcto a string con padding
    val resultadoStr = resultadoCorrecto.toString().padStart(valores.size, '0')

    // Lista para marcar qué casillas tienen error
    val errores = MutableList(valores.size) { false }
    var todosCorrectos = true

    // Comparar cada cifra
    valores.forEachIndexed { index, valorUsuario ->
        val digitoCorrecto = resultadoStr.getOrNull(index)?.toString() ?: "0"

        // Si el usuario no ha introducido nada o es incorrecto
        if (valorUsuario.isEmpty() || valorUsuario != digitoCorrecto) {
            errores[index] = true
            todosCorrectos = false
        }
    }

    return Pair(todosCorrectos, errores)
}
