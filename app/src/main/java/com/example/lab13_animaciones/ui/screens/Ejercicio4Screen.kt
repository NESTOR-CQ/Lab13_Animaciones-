package com.example.lab13_animaciones.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
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
import androidx.compose.material3.CircularProgressIndicator
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

enum class UiState {
    CARGANDO,
    CONTENIDO,
    ERROR
}

@Composable
fun Ejercicio4Screen(modifier: Modifier = Modifier) {
    var currentState by remember { mutableStateOf(UiState.CARGANDO) }

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
                    text = "STATE TRANSITION STUDIO",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF38BDF8),
                    letterSpacing = 2.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Cambio de Contenido Animado",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Transiciones entre estados mediante AnimatedContent",
                    fontSize = 13.sp,
                    color = Color(0xFF94A3B8),
                    textAlign = TextAlign.Center
                )
            }

            // Área central con AnimatedContent
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = currentState,
                    transitionSpec = {
                        (fadeIn(animationSpec = tween(durationMillis = 500)) +
                                scaleIn(initialScale = 0.92f, animationSpec = tween(durationMillis = 500))) togetherWith
                                (fadeOut(animationSpec = tween(durationMillis = 500)) +
                                        scaleOut(targetScale = 0.92f, animationSpec = tween(durationMillis = 500)))
                    },
                    label = "stateContentTransition"
                ) { targetState ->
                    when (targetState) {
                        UiState.CARGANDO -> StateCard(
                            title = "Cargando información...",
                            subtitle = "Por favor espera mientras obtenemos los datos",
                            cardColor = Color(0xFF0284C7),
                            borderColor = Color(0xFF38BDF8)
                        ) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(48.dp),
                                strokeWidth = 4.dp
                            )
                        }

                        UiState.CONTENIDO -> StateCard(
                            title = "¡Carga Completada!",
                            subtitle = "Los datos del sistema se han procesado exitosamente.",
                            cardColor = Color(0xFF16A34A),
                            borderColor = Color(0xFF4ADE80)
                        ) {
                            Text(text = "✅", fontSize = 42.sp)
                        }

                        UiState.ERROR -> StateCard(
                            title = "Error de Conexión",
                            subtitle = "No se pudo conectar con el servidor. Intenta de nuevo.",
                            cardColor = Color(0xFFDC2626),
                            borderColor = Color(0xFFF87171)
                        ) {
                            Text(text = "⚠️", fontSize = 42.sp)
                        }
                    }
                }
            }

            // Panel de botones para cambiar de estado
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Text(
                    text = "Selecciona un estado para simular:",
                    fontSize = 13.sp,
                    color = Color(0xFF94A3B8),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { currentState = UiState.CARGANDO },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (currentState == UiState.CARGANDO) Color(0xFF0284C7) else Color(0xFF334155)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cargando", fontSize = 12.sp, color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { currentState = UiState.CONTENIDO },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (currentState == UiState.CONTENIDO) Color(0xFF16A34A) else Color(0xFF334155)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Contenido", fontSize = 12.sp, color = Color.White)
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { currentState = UiState.ERROR },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (currentState == UiState.ERROR) Color(0xFFDC2626) else Color(0xFF334155)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Error", fontSize = 12.sp, color = Color.White)
                    }
                }
            }
        }
    }
}

@Composable
private fun StateCard(
    title: String,
    subtitle: String,
    cardColor: Color,
    borderColor: Color,
    iconContent: @Composable () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .shadow(16.dp, shape = RoundedCornerShape(24.dp), spotColor = borderColor)
            .border(
                width = 1.5.dp,
                color = borderColor,
                shape = RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            iconContent()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = subtitle,
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.9f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Ejercicio4ScreenPreview() {
    Lab13_AnimacionesTheme {
        Ejercicio4Screen()
    }
}
