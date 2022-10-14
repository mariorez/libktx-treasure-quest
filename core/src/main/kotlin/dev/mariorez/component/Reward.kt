package dev.mariorez.component

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

data class Reward(
    var name: String = ""
) : Component<Reward> {
    override fun type() = Reward

    companion object : ComponentType<Reward>()
}
