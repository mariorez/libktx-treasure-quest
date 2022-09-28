package dev.mariorez.system

import com.badlogic.gdx.graphics.g2d.Sprite
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World
import com.github.quillraven.fleks.World.Companion.inject
import dev.mariorez.Sizes
import dev.mariorez.component.Player
import dev.mariorez.component.Render
import dev.mariorez.component.Transform

class BoundToWorldSystem(
    private val sizes: Sizes = inject()
) : IteratingSystem(
    World.family { any(Player) }
) {

    private val sprites = mutableMapOf<Entity, Sprite>()

    override fun onTickEntity(entity: Entity) {

        val sprite = sprites.getOrPut(entity) { entity[Render].sprite }

        entity[Transform].position.apply {
            x = x.coerceIn(0f, sizes.worldWidth - sprite.width)
            y = y.coerceIn(0f, sizes.worldHeight - sprite.height)
        }
    }
}
