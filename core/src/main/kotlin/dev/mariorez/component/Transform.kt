package dev.mariorez.component

import com.badlogic.gdx.math.Vector2
import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

data class Transform(
    var position: Vector2 = Vector2(),
    var zIndex: Float = 0f,
    var velocity: Vector2 = Vector2(),
    var accelerator: Vector2 = Vector2(),
    var acceleration: Float = 0f,
    var deceleration: Float = 0f,
    var maxSpeed: Float = 0f,
    var rotation: Float = 0f
) : Component<Transform> {

    fun setSpeed(speed: Float) {
        if (velocity.len() == 0f) velocity.set(speed, 0f)
        else velocity.setLength(speed)
    }

    override fun type() = Transform

    companion object : ComponentType<Transform>()
}
