package dev.mariorez.system

import com.badlogic.gdx.math.Intersector
import com.badlogic.gdx.math.Intersector.MinimumTranslationVector
import com.badlogic.gdx.math.Polygon
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.IteratingSystem
import com.github.quillraven.fleks.World.Companion.family
import dev.mariorez.component.Render
import dev.mariorez.component.Solid
import dev.mariorez.component.Transform
import kotlin.properties.Delegates

class CollisionSystem : IteratingSystem(
    family { all(Solid) }
) {

    var player: Entity by Delegates.notNull()

    override fun onTickEntity(entity: Entity) {

        val playerBox = player[Render].getPolygon(8)
        val solidBox = entity[Render].getPolygon()

        val mtv = MinimumTranslationVector()
        if (!overlaps(playerBox, solidBox, mtv)) return

        player[Transform].apply {
            position.x += mtv.normal.x * mtv.depth
            position.y += mtv.normal.y * mtv.depth
        }
    }

    private fun overlaps(
        playerBox: Polygon,
        otherBox: Polygon,
        mtv: MinimumTranslationVector
    ): Boolean {
        // initial test to improve performance
        if (playerBox.boundingRectangle.overlaps(otherBox.boundingRectangle)) {
            return Intersector.overlapConvexPolygons(playerBox, otherBox, mtv)
        }
        return false
    }
}
