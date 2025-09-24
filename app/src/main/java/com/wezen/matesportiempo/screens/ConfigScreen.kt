package com.wezen.matesportiempo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wezen.matesportiempo.data.*

import com.wezen.matesportiempo.data.User

@Composable

fun ConfigScreen(
    userId: Int,
    onBack: () -> Unit
){

    var sumaConfig by remember { mutableStateOf("TODO configuracion suma") }
    var restaConfig by remember { mutableStateOf("TDO configuracion resta") }
    var multiConfig by remember { mutableStateOf("TODO configuracion multi") }
    var divisionConfig by remember { mutableStateOf("TODO configuracion division") }

    //leo la configuracion del usuario de la BBDD
    val configuracionUsuario = Configuraciones(
        usuario = 1,
        suma = ConfiguracionSuma(cifras = 2, tiempoextra = 5, conllevadas = true),
        resta = ConfiguracionResta(cifras = 2, tiempoextra = 5, conllevadas = false),
        multiplicacion = ConfiguracionMultiplicacion(cifrasMultiplicando = 1, cifrasMultiplicador = 2, tiempoextra = 10, conllevadas = false),
        division = ConfiguracionDivision(cifrasDividendo = 2, cifrasDivisor = 1, tiempoextra = 10, soloExactas = true)
    )




    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
            .padding(16.dp)
    ) {
        // Header
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
                text = "⚙️ Configuración",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ConfigSection(
                    title = "➕ Configuración de Sumas",
                    content = {
                        Column {
                            SliderConfig(
                                label = "Número de cifras",
                                value = configuracionUsuario.suma.cifras,
                                range = 1f..4f,
                                onValueChange = { // TODO sumaConfig = sumaConfig.copy(cifras = it.toInt())
                                     }
                            )

                            SwitchConfig(
                                label = "Con llevadas",
                                checked = configuracionUsuario.suma.conllevadas,
                                onCheckedChange = {// TODO  sumaConfig = sumaConfig.copy(conLlevadas = it)
                                }
                            )
                        }
                    }
                )
            }

            item {
                ConfigSection(
                    title = "➖ Configuración de Restas",
                    content = {
                        Column {
                            SliderConfig(
                                label = "Número de cifras",
                                value = configuracionUsuario.resta.cifras,
                                range = 1f..4f,
                                onValueChange = {//TODO restaConfig = restaConfig.copy(cifras = it.toInt())
                                }
                            )

                            SwitchConfig(
                                label = "Con llevadas",
                                checked = configuracionUsuario.resta.conllevadas,
                                onCheckedChange = { // TODO restaConfig = restaConfig.copy(conLlevadas = it)
                                }
                            )
                        }
                    }
                )
            }

            item {
                ConfigSection(
                    title = "✖️ Configuración de Multiplicaciones",
                    content = {
                        Column {
                            SliderConfig(
                                label = "Cifras multiplicando",
                                value = configuracionUsuario.multiplicacion.cifrasMultiplicando,
                                range = 1f..4f,
                                onValueChange = { // TODO multiConfig = multiConfig.copy(cifrasMultiplicando = it.toInt())
                                     }
                            )

                            SliderConfig(
                                label = "Cifras multiplicador",
                                value = configuracionUsuario.multiplicacion.cifrasMultiplicador,
                                range = 1f..3f,
                                onValueChange = {// TODO multiConfig = multiConfig.copy(cifrasMultiplicador = it.toInt())
                                }
                            )

                            SwitchConfig(
                                label = "Con llevadas",
                                checked = configuracionUsuario.multiplicacion.conllevadas,
                                onCheckedChange = { //TODO multiConfig = multiConfig.copy(conLlevadas = it)
                                }
                            )
                        }
                    }
                )
            }

            item {
                ConfigSection(
                    title = "➗ Configuración de Divisiones",
                    content = {
                        Column {
                            SliderConfig(
                                label = "Cifras dividendo",
                                value = configuracionUsuario.division.cifrasDividendo,
                                range = 2f..4f,
                                onValueChange = {// TODO divisionConfig = divisionConfig.copy(cifrasDividendo = it.toInt())
                                }
                            )

                            SliderConfig(
                                label = "Cifras divisor",
                                value = configuracionUsuario.division.cifrasDivisor,
                                range = 1f..2f,
                                onValueChange = {//TODO divisionConfig = divisionConfig.copy(cifrasDivisor = it.toInt())
                                }
                            )

                            SwitchConfig(
                                label = "Solo divisiones exactas",
                                checked = configuracionUsuario.division.soloExactas,
                                onCheckedChange = { //TODO divisionConfig = divisionConfig.copy(soloExactas = it)
                                }
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun ConfigSection(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            content()
        }
    }
}

@Composable
fun SliderConfig(
    label: String,
    value: Int,
    range: ClosedFloatingPointRange<Float>,
    onValueChange: (Float) -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, fontSize = 16.sp)
            Text(
                text = value.toString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Slider(
            value = value.toFloat(),
            onValueChange = onValueChange,
            valueRange = range,
            steps = (range.endInclusive - range.start - 1).toInt(),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun SwitchConfig(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, fontSize = 16.sp)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}