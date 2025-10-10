package com.wezen.matesportiempo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wezen.matesportiempo.data.Database.DatabaseHelper
import com.wezen.matesportiempo.data.User
import com.wezen.matesportiempo.ui.theme.*

@Composable
fun LoginScreen (onUserSelected: (Long?) -> Unit, dbHelper: DatabaseHelper){

    var showCreateUser by remember { mutableStateOf(false) }
    var newUserName by remember { mutableStateOf("") }

    var selectedAvatar by remember { mutableStateOf("😀") }

    // Lista de avatares disponibles
    val avatares = listOf(
        "😀", "😎", "🤓", "😺", "🦊", "🐻", "🐼", "🦁","🎀",
        "🐯", "🐨", "🐸", "🦄", "🐲", "🚀", "⚽", "🎮"
    )

    //cargamos los usuarios existentes de la base de datos TODO leer de la BBDD i cargar los usuarios existentes en el listado
    //Por el momento metemos a cascoporrro 3
    val listadoUsuarios = remember {
        mutableStateListOf(
            User(1, "Alya", "🎀",54,0,0,0,0,0,0,0,0,"","","",null),
            User(2, "Dubhe", "🎀",54,0,0,0,0,0,0,0,0,"","","",null)

        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf( Fondo1,Fondo2

                    )
                )
            )
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            // Título principal
            Text(
                text = "🧮 Mates Por Tiempo",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "¡Aprende matemáticas divirtiéndote!",
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.9f),
                modifier = Modifier.padding(bottom = 40.dp)
            )

            // Card con usuarios
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Text(
                        text = "Selecciona tu perfil:",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Lista de usuarios existentes
                    listadoUsuarios.forEach { user ->
                        UserCard(
                            user = user,
                            onClick = { onUserSelected(user.id) }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    // Botón crear nuevo usuario
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showCreateUser = true },
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        MaterialTheme.colorScheme.primary,
                                        CircleShape
                                    )
                                    .padding(8.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "Crear nuevo usuario",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
        }
    }

// Dialog para crear usuario
        if (showCreateUser) {
            AlertDialog(
                onDismissRequest = { showCreateUser = false },
                title = { Text("Nuevo Usuario") },
                text = {
                    Column {
                        Text("Escribe tu nombre:")
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = newUserName,
                            onValueChange = { newUserName = it },
                            placeholder = { Text("Nombre") }
                        )
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (newUserName.isNotBlank()) {
                                val newUser = User(
                                    nombre = newUserName,
                                )
                                dbHelper.createUser(newUserName)
                                showCreateUser = false
                                newUserName = ""
                                onUserSelected(newUser.id)
                                //TODO ingresar al usuario en la BBDD
                            }
                        }
                    ) {
                        Text("Crear")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showCreateUser = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
fun UserCard(
    user: User,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = user.avatar ?: "",
                fontSize = 32.sp,
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        CircleShape
                    )
                    .wrapContentSize(Alignment.Center)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = user.nombre,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}