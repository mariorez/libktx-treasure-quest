package dev.mariorez.system

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World
import dev.mariorez.component.Flyer
import dev.mariorez.component.Transform
import kotlin.random.Random

class RandomMoveSystem : IteratingSystem(
    World.family { all(Flyer) }
) {

    override fun onTickEntity(entity: Entity) {
        if (Random.nextInt(1, 120) == 1) {
            entity[Transform].apply {
                setMotionAngle(Random.nextInt(0, 360).toFloat())
            }
        }
    }
}
