package com.example.lab13_animaciones.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lab13_animaciones.R
import kotlinx.coroutines.delay
import kotlin.random.Random

private enum class AlienPage { HOME, PLAY, RESULT }

@Composable
fun WhackAlienScreen(modifier: Modifier = Modifier) {
    var page by remember { mutableStateOf(AlienPage.HOME) }
    var score by remember { mutableIntStateOf(0) }
    var seconds by remember { mutableIntStateOf(45) }
    var lives by remember { mutableIntStateOf(3) }
    var hole by remember { mutableIntStateOf(-1) }
    var alienType by remember { mutableIntStateOf(0) }
    var shown by remember { mutableStateOf(false) }
    var hitIndex by remember { mutableIntStateOf(-1) }
    var isHardMode by remember { mutableStateOf(false) }

    val level = score / 100 + 1
    val sky by animateColorAsState(listOf(Color(0xFF18254E), Color(0xFF29245E), Color(0xFF183C58))[((level-1)/2)%3], label="levelSky")

    LaunchedEffect(page) {
        if (page == AlienPage.PLAY) {
            while (seconds > 0 && lives > 0) { delay(1_000); seconds-- }
            shown = false
            page = AlienPage.RESULT
        }
    }
    LaunchedEffect(page, shown) {
        if (page == AlienPage.PLAY && !shown) {
            delay(if (isHardMode) 100L else 200L)
            val next = if (hole >= 0) Random.nextInt(8) else Random.nextInt(9)
            hole = if (hole >= 0 && next >= hole) next + 1 else next
            alienType = Random.nextInt(4)
            shown = true
            hitIndex = -1
        }
    }
    LaunchedEffect(page, hole, shown, level, isHardMode) {
        if (page == AlienPage.PLAY && shown) {
            // Tiempo en el que el alien permanece visible en pantalla
            val hideDelay = if (isHardMode) {
                (300L - (level - 1) * 10L).coerceAtLeast(10L) // Modo Difícil: de ms a 350ms
            } else {
                (1_350L - (level - 1) * 100L).coerceAtLeast(600L) // Modo Fácil: de 1350ms a 600ms
            }
            delay(hideDelay)
            if (shown) { shown = false; lives-- }
        }
    }

    Box(modifier.fillMaxSize().background(Brush.verticalGradient(listOf(sky, Color(0xFF46335D), Color(0xFFB96D50))))) {
        SpaceStars(Modifier.fillMaxSize())
        Column(Modifier.fillMaxSize().padding(16.dp), horizontalAlignment=Alignment.CenterHorizontally) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement=Arrangement.SpaceBetween, verticalAlignment=Alignment.CenterVertically) {
                Column {
                    Text("SECTOR 09 · CAZA ALIENÍGENA", color=Color(0xFFB9E8FF), fontSize=10.sp, fontWeight=FontWeight.Bold, letterSpacing=2.sp)
                    Text("WHACK-A-MOLE", color=Color.White, fontSize=20.sp, fontWeight=FontWeight.Black)
                }
                if (page == AlienPage.PLAY) {
                    Button(
                        onClick = { page = AlienPage.HOME },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                        shape = RoundedCornerShape(12.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text("Inicio", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            AnimatedContent(page, transitionSpec={ (fadeIn(tween(240))+scaleIn()) togetherWith (fadeOut(tween(160))+scaleOut()) }, label="alienScreens") { state ->
                when(state) {
                    AlienPage.HOME -> HomePanel(
                        isHardMode = isHardMode,
                        onDifficultyChange = { hard -> isHardMode = hard },
                        start = { score=0; seconds=45; lives=3; hole=-1; shown=false; page=AlienPage.PLAY }
                    )
                    AlienPage.PLAY -> PlayPanel(score, seconds, lives, level, isHardMode, hole, shown, alienType, hitIndex, Modifier.fillMaxSize(),
                        onHit = { idx ->
                            if (shown && idx == hole) {
                                score += 10
                                hitIndex = idx
                                shown = false
                            }
                        },
                        onRestart = { score=0; seconds=45; lives=3; hole=-1; shown=false; hitIndex=-1 }
                    )
                    AlienPage.RESULT -> ResultPanel(score, lives==0,
                        restart = { score=0; seconds=45; lives=3; hole=-1; shown=false; hitIndex=-1; page=AlienPage.PLAY },
                        home = { page = AlienPage.HOME }
                    )
                }
            }
        }
    }
}

@Composable private fun SpaceStars(modifier: Modifier) {
    Canvas(modifier) {
        val stars=listOf(.08f to .12f,.21f to .28f,.82f to .09f,.92f to .32f,.12f to .54f,.76f to .48f,.45f to .16f,.61f to .36f)
        stars.forEachIndexed { i,p -> drawCircle(Color.White.copy(alpha=if(i%2==0).55f else .3f), radius=if(i%3==0) 3.dp.toPx() else 1.7.dp.toPx(), center=androidx.compose.ui.geometry.Offset(size.width*p.first,size.height*p.second)) }
    }
}

@Composable private fun HomePanel(
    isHardMode: Boolean,
    onDifficultyChange: (Boolean) -> Unit,
    start: () -> Unit
) {
    Column(Modifier.fillMaxSize(), horizontalAlignment=Alignment.CenterHorizontally, verticalArrangement=Arrangement.Center) {
        Image(painterResource(R.drawable.inicio), "Alien verde", Modifier.size(120.dp), contentScale=ContentScale.Fit)
        Spacer(Modifier.height(8.dp))
        Text("¡Defiende la colonia!", color=Color.White, fontSize=22.sp, fontWeight=FontWeight.Black, textAlign=TextAlign.Center)
        Text("Toca a los aliens antes de que escapen. Selecciona la dificultad:", color=Color(0xFFDCE7FF), fontSize=12.sp, textAlign=TextAlign.Center, modifier=Modifier.padding(horizontal=12.dp, vertical=4.dp))
        
        Spacer(Modifier.height(10.dp))
        
        // Seleccionador de Dificultad
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = { onDifficultyChange(false) },
                modifier = Modifier.weight(1f).height(44.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!isHardMode) Color(0xFF22C55E) else Color(0xFF334155)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("FÁCIL", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
            }
            Button(
                onClick = { onDifficultyChange(true) },
                modifier = Modifier.weight(1f).height(44.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isHardMode) Color(0xFFEF4444) else Color(0xFF334155)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("DIFÍCIL", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Color.White)
            }
        }

        Spacer(Modifier.height(16.dp))
        
        Button(
            onClick = start,
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFB547)),
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(18.dp)
        ) {
            Text(
                text = if (isHardMode) "INICIAR MISIÓN (DIFÍCIL)" else "INICIAR MISIÓN (FÁCIL)",
                color = Color(0xFF302449),
                fontWeight = FontWeight.Black,
                fontSize = 15.sp
            )
        }
    }
}

@Composable private fun PlayPanel(
    score: Int,
    seconds: Int,
    lives: Int,
    level: Int,
    isHardMode: Boolean,
    hole: Int,
    shown: Boolean,
    type: Int,
    hitIndex: Int,
    modifier: Modifier,
    onHit: (Int) -> Unit,
    onRestart: () -> Unit
) {
    Column(modifier, horizontalAlignment=Alignment.CenterHorizontally) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement=Arrangement.spacedBy(8.dp)) {
            Hud("PUNTOS", score.toString().padStart(4,'0'), R.drawable.puntos, Modifier.weight(1f))
            Hud("TIEMPO", "00:${seconds.toString().padStart(2,'0')}", R.drawable.tiempo, Modifier.weight(1f))
            Hud("VIDAS", "$lives", R.drawable.vida, Modifier.weight(1f))
        }
        Spacer(Modifier.height(8.dp))
        Row(Modifier.fillMaxWidth(), horizontalArrangement=Arrangement.SpaceBetween, verticalAlignment=Alignment.CenterVertically) {
            Text("NIVEL $level · MODO ${if(isHardMode) "DIFÍCIL" else "FÁCIL"}", color=Color.White, fontWeight=FontWeight.Bold, fontSize=12.sp)
            Button(onClick=onRestart, colors=ButtonDefaults.buttonColors(containerColor=Color(0xFF38BDF8)), contentPadding=PaddingValues(horizontal=10.dp, vertical=4.dp), shape=RoundedCornerShape(10.dp)) {
                Text("Reiniciar", fontSize=11.sp, fontWeight=FontWeight.Bold, color=Color.DarkGray)
            }
        }
        Spacer(Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0x3345D5C2))
                .padding(8.dp)
        ) {
            Column(Modifier.fillMaxSize(), verticalArrangement=Arrangement.spacedBy(4.dp)) {
                repeat(3) { r ->
                    Row(Modifier.weight(1f), horizontalArrangement=Arrangement.spacedBy(4.dp)) {
                        repeat(3) { c ->
                            val i = r * 3 + c
                            HoleCell(
                                active = i == hole && shown,
                                type = type,
                                isHit = i == hitIndex,
                                modifier = Modifier.weight(1f).fillMaxHeight()
                            ) {
                                onHit(i)
                            }
                        }
                    }
                }
            }
        }
        Text("Toca rápido · El martillo aparece al golpear", color=Color(0xFFDCE7FF), fontSize=11.sp, modifier=Modifier.padding(6.dp))
    }
}

@Composable private fun Hud(title:String, value:String, icon:Int, modifier:Modifier) {
    Surface(modifier, shape=RoundedCornerShape(14.dp), color=Color(0xCCFFFFFF)) {
        Column(Modifier.padding(6.dp), horizontalAlignment=Alignment.CenterHorizontally) {
            Image(painterResource(icon), title, Modifier.size(24.dp))
            Text(title, color=Color(0xFF51446D), fontSize=8.sp, fontWeight=FontWeight.Black)
            Text(value, color=Color(0xFF27233E), fontSize=15.sp, fontWeight=FontWeight.Black)
        }
    }
}

@Composable private fun HoleCell(
    active: Boolean,
    type: Int,
    isHit: Boolean,
    modifier: Modifier,
    onTap: () -> Unit
) {
    val alienSize by animateDpAsState(
        targetValue = if (isHit) 45.dp else 80.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "alienHitSize"
    )

    Box(modifier, contentAlignment=Alignment.BottomCenter) {
        Image(
            painterResource(R.drawable.agujero),
            "Agujero",
            Modifier.fillMaxWidth(.96f).height(68.dp).align(Alignment.BottomCenter),
            contentScale = ContentScale.FillBounds
        )
        AnimatedVisibility(
            visible = active,
            enter = fadeIn(tween(120)) + scaleIn(initialScale = 0.2f),
            exit = fadeOut(tween(120)) + scaleOut(targetScale = 0.2f)
        ) {
            Box(
                Modifier.fillMaxSize().clickable(onClick = onTap),
                contentAlignment = Alignment.BottomCenter
            ) {
                val alienRes = when(type % 3) {
                    0 -> R.drawable.alien_verde
                    1 -> R.drawable.alien_azul
                    else -> R.drawable.alien_rojo
                }
                Image(
                    painterResource(alienRes),
                    null,
                    Modifier.size(alienSize).align(Alignment.BottomCenter),
                    contentScale = ContentScale.Fit
                )
            }
        }
        AnimatedVisibility(
            visible = isHit,
            enter = scaleIn(initialScale = 1.3f) + fadeIn(),
            exit = fadeOut()
        ) {
            Image(
                painterResource(R.drawable.martillo),
                "Martillo golpeando",
                Modifier.size(52.dp).align(Alignment.TopCenter),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable private fun ResultPanel(
    score: Int,
    outOfLives: Boolean,
    restart: () -> Unit,
    home: () -> Unit
) {
    Column(Modifier.fillMaxSize(), horizontalAlignment=Alignment.CenterHorizontally, verticalArrangement=Arrangement.SpaceEvenly) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(if(outOfLives) "¡MISIÓN TERMINADA!" else "¡TIEMPO AGOTADO!", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Black)
            Spacer(Modifier.height(12.dp))
            Text("PUNTAJE FINAL", color = Color(0xFFFFD166), fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Text(score.toString(), color = Color.White, fontSize = 52.sp, fontWeight = FontWeight.Black)
        }
        Row(Modifier.fillMaxWidth().padding(horizontal = 16.dp), horizontalArrangement=Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = restart,
                modifier = Modifier.weight(1f).height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFB547)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("REINTENTAR", color = Color(0xFF302449), fontWeight = FontWeight.Black)
            }
            Button(
                onClick = home,
                modifier = Modifier.weight(1f).height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF334155)),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("INICIO", color = Color.White, fontWeight = FontWeight.Black)
            }
        }
    }
}
