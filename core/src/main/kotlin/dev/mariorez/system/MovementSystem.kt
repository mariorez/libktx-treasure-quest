package dev.mariorez.system

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import dev.mariorez.component.Flyer
import dev.mariorez.component.Player
import dev.mariorez.component.Transform

class MovementSystem : IteratingSystem(
    family { any(Player, Flyer) }
) {

    override fun onTickEntity(entity: Entity) {
        entity[Transform].apply {
            // apply acceleration
            velocity.add(
                accelerator.x * deltaTime,
                accelerator.y * deltaTime
            )

            var speed = velocity.len()

            // decrease speed (decelerate) when not accelerating
            if (accelerator.len() == 0f) {
                speed -= deceleration * deltaTime
            }

            // keep speed within set bounds
            speed = speed.coerceIn(0f, maxSpeed)

            // update velocity
            setSpeed(speed)

            // move by
            if (velocity.x != 0f || velocity.y != 0f) {
                position.add(velocity.x * deltaTime, velocity.y * deltaTime)
            }

            // reset acceleration
            accelerator.set(0f, 0f)
        }
    }
}
