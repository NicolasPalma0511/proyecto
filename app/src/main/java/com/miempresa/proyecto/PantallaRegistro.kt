package com.miempresa.proyecto

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun PantallaRegistro(navController: NavController) {
    var emailNuevo by remember { mutableStateOf("") }
    var passwordNuevo by remember { mutableStateOf("") }
    var mensajeRegistro by remember { mutableStateOf("") }
    var mensajeFirestore by remember { mutableStateOf("") }

    val registerUser = {
        val auth = Firebase.auth
        auth.createUserWithEmailAndPassword(emailNuevo, passwordNuevo)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    mensajeRegistro = "Usuario creado exitosamente"

                    val db = FirebaseFirestore.getInstance()

                    val user = hashMapOf(
                        "email" to emailNuevo,
                        "password" to passwordNuevo,
                    )

                    db.collection("usuarios").document(auth.currentUser!!.uid)
                        .set(user)
                        .addOnSuccessListener {
                            mensajeFirestore = "Datos almacenados en Firestore correctamente"
                        }
                } else {
                    mensajeRegistro = "Fallo en el registro: ${task.exception?.message}"
                }
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Cocktails",
                style = TextStyle(
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Monospace
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 20.dp)

            )
            Text(
                text = "Registro",
                style = TextStyle(
                    fontSize = 40.sp,
                    fontFamily = FontFamily.Monospace
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 20.dp)

            )

            TextField(
                label = { Text(text = "Correo electrónico") },
                value = emailNuevo, onValueChange = { emailNuevo = it },

                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            )

            TextField(
                label = { Text(text = "Contraseña") },
                value = passwordNuevo, onValueChange = { passwordNuevo = it },
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
            )

            Button(
                onClick = { registerUser() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(40.dp, 0.dp, 40.dp, 0.dp)
            ) {
                Text(text = "Registrar")
            }

            Button(
                onClick = {
                    navController.navigate("pantalla1")
                },
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text(text = "←")
            }

            Text(
                text = "$mensajeRegistro\n$mensajeFirestore",
                modifier = Modifier.padding(vertical = 8.dp),
                textAlign = TextAlign.Center
            )
        }
    }
}





