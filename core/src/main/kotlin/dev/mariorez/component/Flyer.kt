package dev.mariorez.component

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

class Flyer : Component<Flyer> {
    override fun type() = Flyer

    companion object : ComponentType<Flyer>()
}
