package dev.mariorez.system

import com.badlogic.gdx.graphics.OrthographicCamera
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import com.github.quillraven.fleks.World.Companion.inject
import dev.mariorez.Sizes
import dev.mariorez.component.Player
import dev.mariorez.component.Transform

class CameraSystem(
    private val camera: OrthographicCamera = inject(),
    private val sizes: Sizes = inject()
) : IteratingSystem(
    family { all(Player) }
) {

    private val middleWidth = camera.viewportWidth / 2
    private val middleHeight = camera.viewportHeight / 2

    override fun onTickEntity(entity: Entity) {
        entity[Transform].position.apply {
            camera.position.x = x.coerceIn(middleWidth, sizes.worldWidth - middleWidth)
            camera.position.y = y.coerceIn(middleHeight, sizes.worldHeight - middleHeight)
            camera.update()
        }
    }
}
