package fr.deke.dekedash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import fr.deke.dekedash.ui.grid.WidgetItem
import fr.deke.dekedash.ui.theme.DekeDashTheme
import fr.deke.dekedash.widget.TextWidget
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DekeDashTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    HomeScreen()
                }
            }
        }
    }
}

@Composable
fun HomeScreen() {
    // Taille d'une cellule de la grille
    val density = LocalDensity.current.density
    val cellSize = 80.dp.value * density

    // Liste initiale de widgets
    var widgets by remember {
        mutableStateOf(
            listOf(
                TextWidget(id = 0, title = "Widget 0", width = 2, height = 1),
                TextWidget(id = 1, title = "Widget 1", width = 1, height = 2),
                TextWidget(id = 2, title = "Widget 2", width = 2, height = 2),
                TextWidget(id = 3, title = "Widget 3", width = 1, height = 1)
            )
        )
    }

    var draggedWidgetIndex by remember { mutableStateOf<Int?>(null) }
    var dragOffset by remember { mutableStateOf(Offset.Zero) }

    // Surface de la grille qui occupe presque tout l'écran
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {
        // Affichage de chaque widget avec positionnement absolu
        widgets.forEachIndexed { index, widget ->
            val isDragged = draggedWidgetIndex == index
            val widgetOffset = if (isDragged) dragOffset else widget.position

            // Composable pour un widget avec déplacement
            WidgetItem(
                widget = widget,
                offset = widgetOffset,
                onDragStart = {
                    draggedWidgetIndex = index
                    dragOffset = widget.position
                },
                onDrag = { dragAmount ->
                    dragOffset += dragAmount
                },
                onDragEnd = {
                    // Fin du déplacement, ajuster la position du widget pour l'aligner avec la grille
                    val alignedPosition = alignToGrid(dragOffset, cellSize)
                    widget.updatePosition(alignedPosition) // Mise à jour de la position alignée
                    draggedWidgetIndex = null
                    dragOffset = Offset.Zero
                }
            )
        }
    }
}

// Fonction pour aligner une position au plus proche de la grille
fun alignToGrid(offset: Offset, cellSize: Float): Offset {
    val alignedX = (offset.x / cellSize).roundToInt() * cellSize
    val alignedY = (offset.y / cellSize).roundToInt() * cellSize
    return Offset(alignedX, alignedY)
}