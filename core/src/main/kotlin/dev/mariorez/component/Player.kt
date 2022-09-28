package dev.mariorez.component

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

data class Player(
    var up: Boolean = false,
    var down: Boolean = false,
    var left: Boolean = false,
    var right: Boolean = false
) : Component<Player> {

    val isMoving: Boolean get() = up || down || left || right
    val isStopped: Boolean get() = !isMoving

    override fun type() = Player

    companion object : ComponentType<Player>()
}
