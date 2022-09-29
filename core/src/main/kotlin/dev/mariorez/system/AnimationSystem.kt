package dev.mariorez.system

import com.badlogic.gdx.graphics.g2d.Animation
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import dev.mariorez.component.AnimationBag
import dev.mariorez.component.Render

class AnimationSystem : IteratingSystem(
    family { all(AnimationBag, Render) }
) {

    private val animationCache = mutableMapOf<String, Animation<TextureRegion>>()
    override fun onTickEntity(entity: Entity) {

        val animate = entity[AnimationBag].animations[entity[AnimationBag].current]

        animate!!.stateTime += deltaTime

        val animation = animationCache.getOrPut(entity[AnimationBag].current) {
            Animation(animate!!.frameDuration, animate.animation)
        }

        val textureRegion = animation.getKeyFrame(animate!!.stateTime, animate.loop)

        entity[Render].sprite.apply {
            setRegion(textureRegion)
            setSize(textureRegion.regionWidth.toFloat(), textureRegion.regionHeight.toFloat())
        }
    }
}
