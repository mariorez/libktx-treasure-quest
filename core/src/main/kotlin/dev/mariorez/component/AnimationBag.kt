package dev.mariorez.component

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType
import ktx.collections.gdxArrayOf

data class AnimationBag(
    var animations: MutableMap<String, Animate> = mutableMapOf(),
    var current: String = ""
) : Component<AnimationBag> {
    override fun type() = AnimationBag

    companion object : ComponentType<AnimationBag>()

    data class Animate(
        var animation: Animation<TextureRegion> = Animation(0f, gdxArrayOf()),
        var stateTime: Float = 0f,
        var loop: Boolean = false
    )
}
