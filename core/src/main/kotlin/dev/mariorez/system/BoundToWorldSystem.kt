package dev.mariorez.system

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import dev.mariorez.Sizes
import dev.mariorez.component.Flyer
import dev.mariorez.component.Player
import dev.mariorez.component.Render
import dev.mariorez.component.Transform

class BoundToWorldSystem(
    private val sizes: Sizes = inject()
) : IteratingSystem(
    family { any(Player, Flyer) }
) {

    override fun onTickEntity(entity: Entity) {
        entity[Transform].position.apply {
            x = x.coerceIn(0f, sizes.worldWidth - entity[Render].sprite.width)
            y = y.coerceIn(0f, sizes.worldHeight - entity[Render].sprite.height)
        }
    }
}
