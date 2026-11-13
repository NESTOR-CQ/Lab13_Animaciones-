package com.example.lab13_animaciones.ui.screens

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab13_animaciones.ui.theme.Lab13_AnimacionesTheme

@Composable
fun Ejercicio3Screen(modifier: Modifier = Modifier) {
    var isExpanded by remember { mutableStateOf(false) }

    // Animación de tamaño utilizando animateDpAsState
    val animatedSize by animateDpAsState(
        targetValue = if (isExpanded) 200.dp else 110.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "sizeAnimation"
    )

    // Animación de posición (offset X e Y) utilizando animateDpAsState
    val animatedOffsetX by animateDpAsState(
        targetValue = if (isExpanded) 50.dp else 0.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "offsetXAnimation"
    )

    val animatedOffsetY by animateDpAsState(
        targetValue = if (isExpanded) 30.dp else 0.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "offsetYAnimation"
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
                    text = "MOTION & SIZE STUDIO",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF38BDF8),
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Animación de Tamaño y Posición",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Uso de animateDpAsState y Modifier.offset",
                    fontSize = 13.sp,
                    color = Color(0xFF94A3B8),
                    textAlign = TextAlign.Center
                )
            }

            // Área del elemento animado
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color(0xFF1E293B), shape = RoundedCornerShape(24.dp))
                    .padding(16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                // Cuadro que cambia de tamaño y posicion
                Card(
                    modifier = Modifier
                        .offset(x = animatedOffsetX, y = animatedOffsetY)
                        .size(animatedSize)
                        .shadow(12.dp, shape = RoundedCornerShape(20.dp), spotColor = Color(0xFF38BDF8))
                        .border(
                            width = 1.5.dp,
                            color = Color(0xFF38BDF8),
                            shape = RoundedCornerShape(20.dp)
                        ),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0EA5E9))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(Color(0xFF0EA5E9), Color(0xFF6366F1))
                                )
                            )
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = if (isExpanded) "📦" else "🟦",
                                fontSize = if (isExpanded) 36.sp else 24.sp
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = if (isExpanded) "Expandido y Desplazado" else "Normal",
                                fontSize = if (isExpanded) 13.sp else 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            // Panel de control inferior
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text(
                        text = "Estado: ",
                        fontSize = 13.sp,
                        color = Color(0xFF94A3B8)
                    )
                    Text(
                        text = if (isExpanded) "MOVIDO / EXPANDIDO" else "POSICION INICIAL",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isExpanded) Color(0xFF38BDF8) else Color(0xFFA78BFA)
                    )
                }

                Button(
                    onClick = { isExpanded = !isExpanded },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isExpanded) Color(0xFF6366F1) else Color(0xFF0EA5E9)
                    )
                ) {
                    Text(
                        text = if (isExpanded) "Restablecer Posición y Tamaño" else "Mover y Ampliar Cuadro",
                        fontSize = 15.sp,
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
fun Ejercicio3ScreenPreview() {
    Lab13_AnimacionesTheme {
        Ejercicio3Screen()
    }
}
