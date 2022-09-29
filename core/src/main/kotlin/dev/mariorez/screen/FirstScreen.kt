package dev.mariorez.screen

import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.renderers.OrthoCachedTiledMapRenderer
import com.github.quillraven.fleks.Entity
import com.github.quillraven.fleks.world
import dev.mariorez.Action
import dev.mariorez.Action.DOWN
import dev.mariorez.Action.LEFT
import dev.mariorez.Action.RIGHT
import dev.mariorez.Action.UP
import dev.mariorez.BaseScreen
import dev.mariorez.Sizes
import dev.mariorez.component.Animate
import dev.mariorez.component.Player
import dev.mariorez.component.Render
import dev.mariorez.component.Transform
import dev.mariorez.system.AnimationSystem
import dev.mariorez.system.BoundToWorldSystem
import dev.mariorez.system.CameraSystem
import dev.mariorez.system.InputSystem
import dev.mariorez.system.MovementSystem
import dev.mariorez.system.RenderSystem
import ktx.assets.async.AssetStorage
import ktx.assets.disposeSafely
import ktx.collections.gdxArrayOf
import ktx.tiled.forEachMapObject
import ktx.tiled.totalHeight
import ktx.tiled.totalWidth
import ktx.tiled.type
import ktx.tiled.x
import ktx.tiled.y
import kotlin.properties.Delegates

class FirstScreen(
    private val sizes: Sizes,
    private val assets: AssetStorage
) : BaseScreen(sizes) {
    private val tiledMap = assets.get<TiledMap>("map.tmx")
    private val mapRenderer = OrthoCachedTiledMapRenderer(tiledMap).apply { setBlending(true) }
    private var player: Entity by Delegates.notNull()
    private val world = world {
        injectables {
            add(batch)
            add(camera)
            add(mapRenderer)
            add(sizes.apply {
                worldWidth = tiledMap.totalWidth().toFloat()
                worldHeight = tiledMap.totalHeight().toFloat()
            })
        }

        systems {
            add(InputSystem())
            add(MovementSystem())
            add(BoundToWorldSystem())
            add(CameraSystem())
            add(AnimationSystem())
            add(RenderSystem())
        }
    }

    init {
        buildControls()
        spawnEntities()
    }

    override fun render(delta: Float) {
        world.update(delta)
        hudStage.draw()
    }

    override fun dispose() {
        super.dispose()
        mapRenderer.dispose()
        world.dispose()
        assets.disposeSafely()
    }

    private fun buildControls() {
        actionMap[Keys.UP] = UP
        actionMap[Keys.DOWN] = DOWN
        actionMap[Keys.LEFT] = LEFT
        actionMap[Keys.RIGHT] = RIGHT
        actionMap[Keys.W] = UP
        actionMap[Keys.S] = DOWN
        actionMap[Keys.A] = LEFT
        actionMap[Keys.D] = RIGHT
    }

    private fun spawnEntities() {
        tiledMap.forEachMapObject("objects") { obj ->
            when (obj.type) {
                "player" -> {
                    val cols = 4
                    val rows = 4
                    val hero = assets.get<Texture>("hero.png")
                    val regions = TextureRegion(hero).split(hero.width / cols, hero.height / rows)
                    val animate = Animate().apply {
                        animations.also {
                            (0 until cols).forEach { col ->
                                it.getOrPut("south") { gdxArrayOf() }.apply {
                                    add(TextureRegion(regions[0][col]))
                                }
                                it.getOrPut("west") { gdxArrayOf() }.apply {
                                    add(TextureRegion(regions[1][col]))
                                }
                                it.getOrPut("east") { gdxArrayOf() }.apply {
                                    add(TextureRegion(regions[2][col]))
                                }
                                it.getOrPut("north") { gdxArrayOf() }.apply {
                                    add(TextureRegion(regions[3][col]))
                                }
                            }
                        }
                    }

                    player = world.entity {
                        it += Player()
                        it += Render()
                        it += animate.apply {
                            current = "south"
                            frameDuration = 0.2f
                        }
                        it += Transform().apply {
                            position.set(obj.x, obj.y)
                            acceleration = 600f
                            deceleration = 600f
                            maxSpeed = 150f
                        }
                    }
                }
            }
        }
    }

    override fun doAction(action: Action) {
        with(world) {
            player[Player].apply {
                when (action) {
                    UP -> up = action.starting
                    DOWN -> down = action.starting
                    LEFT -> left = action.starting
                    RIGHT -> right = action.starting
                    else -> super.doAction(action)
                }
            }
        }
    }
}
