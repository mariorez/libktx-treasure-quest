package dev.mariorez.component

import com.github.quillraven.fleks.Component
import com.github.quillraven.fleks.ComponentType

class Enemy : Component<Enemy> {
    override fun type() = Enemy

    companion object : ComponentType<Enemy>()
}
