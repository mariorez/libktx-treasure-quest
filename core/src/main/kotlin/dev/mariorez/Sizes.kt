package dev.mariorez

import com.badlogic.gdx.Gdx
import kotlin.math.max

data class Sizes(
    val windowWidth: Float,
    val windowHeight: Float,
    var worldWidth: Float = windowWidth,
    var worldHeight: Float = windowHeight
) {
    fun unitsPerPixel(): Float = max(windowWidth / Gdx.graphics.width, windowHeight / Gdx.graphics.height)
}
