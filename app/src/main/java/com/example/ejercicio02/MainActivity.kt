package com.example.ejercicio02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ejercicio02.ui.theme.Ejercicio02Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Ejercicio02Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ComparandoEstados(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ComparandoEstados(modifier: Modifier = Modifier) {
    // Contador normal (NO persiste en rotación)
    var ephemeralCounter by remember { mutableStateOf(0) }

    // Contador que persiste en rotación
    var persistentCounter by rememberSaveable { mutableStateOf(0) }

    // Estado de bloqueo
    var isLocked by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("🔢 Comparando remember vs rememberSaveable", fontSize = 20.sp)

        Spacer(modifier = Modifier.height(20.dp))

        CounterRow(
            label = "Ephemeral (remember):",
            count = ephemeralCounter,
            enabled = !isLocked
        ) { ephemeralCounter++ }

        Spacer(modifier = Modifier.height(20.dp))

        CounterRow(
            label = "Persistente (rememberSaveable):",
            count = persistentCounter,
            enabled = !isLocked
        ) { persistentCounter++ }

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = { isLocked = !isLocked },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isLocked) Color.Red else Color(0xFF6200EE)
            ),
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text(if (isLocked) "Desbloquear" else "Bloquear")
        }

        if (isLocked) {
            Spacer(modifier = Modifier.height(16.dp))
            Text("⛔ Contadores bloqueados", color = Color.Red)
        }
    }
}

@Composable
fun CounterRow(label: String, count: Int, enabled: Boolean, onIncrement: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(12.dp))
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("$label $count")
        Button(onClick = onIncrement, enabled = enabled) {
            Text("+1")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewComparandoEstados() {
    Ejercicio02Theme {
        ComparandoEstados()
    }
}
