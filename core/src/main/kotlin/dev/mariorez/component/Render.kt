package dev.mariorez.component

import com.badlogic.gdx.graphics.g2d.Sprite
import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

data class Render(
    var sprite: Sprite = Sprite()
) : Component<Render> {
    override fun type() = Render

    companion object : ComponentType<Render>()
}
