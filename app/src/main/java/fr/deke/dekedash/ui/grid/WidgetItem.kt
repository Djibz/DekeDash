package fr.deke.dekedash.ui.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.deke.dekedash.widget.AbstractWidget
import kotlin.math.roundToInt

@Composable
fun WidgetItem(
    widget: AbstractWidget,
    offset: Offset,
    onDragStart: () -> Unit,
    onDrag: (Offset) -> Unit,
    onDragEnd: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(
                (80.dp * widget.width),
                (80.dp * widget.height)
            ) // Taille du widget en fonction de sa taille en cellules
            .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) } // Position absolue
            .background(Color.Blue)
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { onDragStart() },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        onDrag(dragAmount)
                    },
                    onDragEnd = { onDragEnd() }
                )
            }
    ) {
        Text(
            text = widget.title,
            fontSize = 14.sp,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}