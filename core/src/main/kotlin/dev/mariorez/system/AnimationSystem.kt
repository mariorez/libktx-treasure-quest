package dev.mariorez.system

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import dev.mariorez.component.Animate
import dev.mariorez.component.Render

class AnimationSystem : IteratingSystem(
    family { all(Animate, Render) }
) {

    private val animationCache = mutableMapOf<String, Animation<TextureRegion>>()
    override fun onTickEntity(entity: Entity) {
        entity[Animate].stateTime += deltaTime

        val animation = animationCache.getOrPut(entity[Animate].current) {
            Animation(
                entity[Animate].frameDuration,
                entity[Animate].animations[entity[Animate].current]
            )
        }

        val textureRegion = animation.getKeyFrame(
            entity[Animate].stateTime,
            entity[Animate].loop
        )

        entity[Render].sprite.apply {
            setRegion(textureRegion)
            setSize(textureRegion.regionWidth.toFloat(), textureRegion.regionHeight.toFloat())
        }
    }
}
