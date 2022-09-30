package dev.mariorez.component

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

data class Solid(
    var name: String = ""
) : Component<Solid> {
    override fun type() = Solid

    companion object : ComponentType<Solid>()
}
