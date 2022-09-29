package dev.mariorez.system

import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import dev.mariorez.component.AnimationBag
import dev.mariorez.component.Render

class AnimationSystem : IteratingSystem(
    family { all(AnimationBag, Render) }
) {

    override fun onTickEntity(entity: Entity) {

        val animate = with(entity[AnimationBag]) {
            animations.getOrElse(current) {
                return
            }
        }

        animate.stateTime += deltaTime

        val textureRegion = animate.animation.getKeyFrame(animate.stateTime, animate.loop)

        entity[Render].sprite.apply {
            setRegion(textureRegion)
            setSize(textureRegion.regionWidth.toFloat(), textureRegion.regionHeight.toFloat())
        }
    }
}
