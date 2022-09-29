package dev.mariorez.system

import com.badlogic.gdx.math.Vector2
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import dev.mariorez.component.AnimationBag
import dev.mariorez.component.Player
import dev.mariorez.component.Transform

class InputSystem : IteratingSystem(
    family { all(Player) }
) {

    private val speedUp = Vector2()

    override fun onTickEntity(entity: Entity) {
        val animate = entity[AnimationBag].animations[entity[AnimationBag].current]

        if (entity[Player].isStopped) {
            animate!!.loop = false
            return
        }

        entity[Transform].apply {
            speedUp.set(acceleration, 0f).also { speed ->
                if (entity[Player].right) {
                    accelerator.add(speed.setAngleDeg(0f))
                }
                if (entity[Player].up) {
                    accelerator.add(speed.setAngleDeg(90f))
                }
                if (entity[Player].left) {
                    accelerator.add(speed.setAngleDeg(180f))
                }
                if (entity[Player].down) {
                    accelerator.add(speed.setAngleDeg(270f))
                }

            }

            when (velocity.angleDeg()) {
                in 0.0..70.0 -> entity[AnimationBag].current = "east"
                in 70.1..110.0 -> entity[AnimationBag].current = "north"
                in 110.1..250.0 -> entity[AnimationBag].current = "west"
                in 250.1..290.0 -> entity[AnimationBag].current = "south"
                in 290.1..360.0 -> entity[AnimationBag].current = "east"
            }
        }

        animate!!.loop = true
    }
}
