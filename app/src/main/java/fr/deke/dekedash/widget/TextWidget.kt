package fr.deke.dekedash.widget

import androidx.compose.ui.geometry.Offset

class TextWidget(
    id: Int,
    title: String,
    width: Int = 1,
    height: Int = 1,
    position: Offset = Offset.Zero
) :
    AbstractWidget(id, title, width, height, position) {
}