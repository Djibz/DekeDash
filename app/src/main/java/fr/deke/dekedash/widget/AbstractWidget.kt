package fr.deke.dekedash.widget

import androidx.compose.ui.geometry.Offset

abstract class AbstractWidget(
    var id: Int,
    var title: String,
    val width: Int = 1,
    val height: Int = 1,
    var position: Offset = Offset.Zero
) {
    fun updatePosition(newPosition: Offset) {
        position = newPosition
    }
}