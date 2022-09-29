package dev.mariorez.component

import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import ktx.collections.GdxArray

data class Animate(
    var animations: MutableMap<String, GdxArray<TextureRegion>> = mutableMapOf(),
    var current: String = "",
    var loop: Boolean = false,
    var frameDuration: Float = 0.1f,
    var stateTime: Float = 0f
) : Component<Animate> {
    override fun type() = Animate

    companion object : ComponentType<Animate>()
}
