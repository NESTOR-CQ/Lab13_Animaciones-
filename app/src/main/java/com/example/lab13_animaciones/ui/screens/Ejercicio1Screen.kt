package com.example.lab13_animaciones.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab13_animaciones.ui.theme.Lab13_AnimacionesTheme

@Composable
fun Ejercicio1Screen(modifier: Modifier = Modifier) {
    var isVisible by remember { mutableStateOf(true) }

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
            // Cabecera estilizada y moderna
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = "VISIBILITY DOCK",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF38BDF8),
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Control de Visibilidad Animada",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Transiciones suaves con AnimatedVisibility (fadeIn y fadeOut)",
                    fontSize = 13.sp,
                    color = Color(0xFF94A3B8),
                    textAlign = TextAlign.Center
                )
            }

            // Sección central con el elemento animado (Cuadro App Vault)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(durationMillis = 650)) +
                            scaleIn(initialScale = 0.85f, animationSpec = tween(durationMillis = 650)) +
                            expandVertically(expandFrom = Alignment.CenterVertically, animationSpec = tween(durationMillis = 650)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 650)) +
                            scaleOut(targetScale = 0.85f, animationSpec = tween(durationMillis = 650)) +
                            shrinkVertically(shrinkTowards = Alignment.CenterVertically, animationSpec = tween(durationMillis = 650))
                ) {
                    // Cuadro estilo App Vault / Tarjeta espacial con aplicaciones
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(24.dp, shape = RoundedCornerShape(28.dp), spotColor = Color(0xFF38BDF8))
                            .border(
                                width = 1.5.dp,
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color(0xFF38BDF8), Color(0xFF818CF8), Color(0xFFC084FC))
                                ),
                                shape = RoundedCornerShape(28.dp)
                            ),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Brush.verticalGradient(
                                        colors = listOf(
                                            Color(0xFF1E293B),
                                            Color(0xFF0F172A)
                                        )
                                    )
                                )
                                .padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "ROCKET APP DASHBOARD",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFA78BFA),
                                letterSpacing = 1.5.sp
                            )
                            Spacer(modifier = Modifier.height(16.dp))

                            // Fila de mini-iconos de aplicaciones estilo Cuadro
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                AppIconItem("🚀", "Rocket", Color(0xFFEF4444), Color(0xFFF97316))
                                AppIconItem("💎", "Gem", Color(0xFF3B82F6), Color(0xFF06B6D4))
                                AppIconItem("⚡", "Energy", Color(0xFFEAB308), Color(0xFFF59E0B))
                                AppIconItem("🎨", "Studio", Color(0xFFEC4899), Color(0xFF8B5CF6))
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color(0xFF334155).copy(alpha = 0.5f))
                                    .padding(14.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Este cuadro interrumpe o reaparece fluidamente mediante la interpolacion de fade, escala y expansion vertical.",
                                    color = Color(0xFFE2E8F0),
                                    fontSize = 13.sp,
                                    textAlign = TextAlign.Center,
                                    lineHeight = 18.sp
                                )
                            }
                        }
                    }
                }
            }

            // Botón Circular Unico e Indicador Inferior
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                // Indicador de Estado
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .clip(CircleShape)
                            .background(if (isVisible) Color(0xFF22C55E) else Color(0xFFEF4444))
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isVisible) "ESTADO: VISIBLE" else "ESTADO: OCULTO",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (isVisible) Color(0xFF4ADE80) else Color(0xFFF87171),
                        letterSpacing = 1.sp
                    )
                }

                // Botón Circular interactivo con efecto Neón
                Box(
                    modifier = Modifier
                        .size(76.dp)
                        .shadow(16.dp, shape = CircleShape, spotColor = if (isVisible) Color(0xFFEF4444) else Color(0xFF38BDF8))
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = if (isVisible) {
                                    listOf(Color(0xFFEF4444), Color(0xFFDC2626))
                                } else {
                                    listOf(Color(0xFF0284C7), Color(0xFF3B82F6))
                                }
                            )
                        )
                        .clickable { isVisible = !isVisible },
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.size(32.dp)) {
                        val width = size.width
                        val height = size.height
                        val path = Path()

                        if (isVisible) {
                            // Icono X / Ocultar
                            drawLine(
                                color = Color.White,
                                start = Offset(width * 0.25f, height * 0.25f),
                                end = Offset(width * 0.75f, height * 0.75f),
                                strokeWidth = 8f
                            )
                            drawLine(
                                color = Color.White,
                                start = Offset(width * 0.75f, height * 0.25f),
                                end = Offset(width * 0.25f, height * 0.75f),
                                strokeWidth = 8f
                            )
                        } else {
                            // Icono Play / Mostrar
                            path.moveTo(width * 0.35f, height * 0.25f)
                            path.lineTo(width * 0.75f, height * 0.5f)
                            path.lineTo(width * 0.35f, height * 0.75f)
                            path.close()
                            drawPath(path = path, color = Color.White)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (isVisible) "Toca para ocultar" else "Toca para mostrar",
                    fontSize = 12.sp,
                    color = Color(0xFF64748B)
                )
            }
        }
    }
}

@Composable
private fun AppIconItem(
    emoji: String,
    label: String,
    startColor: Color,
    endColor: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Brush.linearGradient(listOf(startColor, endColor))),
            contentAlignment = Alignment.Center
        ) {
            Text(text = emoji, fontSize = 24.sp)
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            color = Color(0xFF94A3B8),
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Ejercicio1ScreenPreview() {
    Lab13_AnimacionesTheme {
        Ejercicio1Screen()
    }
}
