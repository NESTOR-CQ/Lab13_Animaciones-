package com.example.lab13_animaciones.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab13_animaciones.ui.theme.Lab13_AnimacionesTheme

@Composable
fun Ejercicio2Screen(modifier: Modifier = Modifier) {
    var isBlueState by remember { mutableStateOf(true) }
    var useSpringSpec by remember { mutableStateOf(false) }

    // Definición de colores objetivo (Azul y Verde)
    val targetColor = if (isBlueState) Color(0xFF1E88E5) else Color(0xFF4CAF50)

    // Animación continua del color usando animateColorAsState
    val animatedBackgroundColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = if (useSpringSpec) {
            spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        } else {
            tween(durationMillis = 700)
        },
        label = "colorAnimation"
    )

    Surface(
        modifier = modifier.fillMaxSize(),
        color = Color(0xFF0F172A)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Encabezado
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = "COLOR TRANSITION STUDIO",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF38BDF8),
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Animación de Color de Fondo",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Transición fluida con animateColorAsState (Tween / Spring)",
                    fontSize = 13.sp,
                    color = Color(0xFF94A3B8),
                    textAlign = TextAlign.Center
                )
            }

            // Elemento central: Cuadro con animación de color
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .shadow(16.dp, shape = RoundedCornerShape(24.dp), spotColor = animatedBackgroundColor)
                    .border(
                        width = 2.dp,
                        color = animatedBackgroundColor.copy(alpha = 0.6f),
                        shape = RoundedCornerShape(24.dp)
                    ),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = animatedBackgroundColor)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (isBlueState) "AZUL CIAN" else "VERDE ESMERALDA",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = if (useSpringSpec) "Modo de animación: SPRING (Rebote)" else "Modo de animación: TWEEN (Suave)",
                        fontSize = 13.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Panel de Control e Interacción
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                // Selector de especificación de animación (Tween vs Spring)
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Button(
                        onClick = { useSpringSpec = false },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (!useSpringSpec) Color(0xFF38BDF8) else Color(0xFF334155)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Tween", color = Color.White, fontSize = 13.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Button(
                        onClick = { useSpringSpec = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (useSpringSpec) Color(0xFF38BDF8) else Color(0xFF334155)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Spring", color = Color.White, fontSize = 13.sp)
                    }
                }

                // Botón principal de cambio de color
                Button(
                    onClick = { isBlueState = !isBlueState },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isBlueState) Color(0xFF4CAF50) else Color(0xFF1E88E5)
                    )
                ) {
                    Text(
                        text = if (isBlueState) "Cambiar a Verde" else "Cambiar a Azul",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Ejercicio2ScreenPreview() {
    Lab13_AnimacionesTheme {
        Ejercicio2Screen()
    }
}
