package dev.mariorez

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputMultiplexer
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.Texture.TextureFilter.Linear
import com.badlogic.gdx.maps.tiled.TiledMap
import com.badlogic.gdx.maps.tiled.TmxMapLoader
import dev.mariorez.screen.FirstScreen
import ktx.app.KtxGame
import ktx.app.KtxInputAdapter
import ktx.app.KtxScreen
import ktx.assets.async.AssetStorage
import ktx.async.KtxAsync

class GameBoot : KtxGame<KtxScreen>() {

    private val assets = AssetStorage()
    private val sizes = Sizes(
        windowWidth = 960f,
        windowHeight = 540f
    )

    override fun create() {
        Gdx.input.inputProcessor = InputMultiplexer(object : KtxInputAdapter {
            override fun keyDown(keycode: Int): Boolean {
                (currentScreen as BaseScreen).apply {
                    actionMap[keycode]?.let {
                        it.starting = true
                        doAction(it)
                    }
                }
                return super.keyDown(keycode)
            }

            override fun keyUp(keycode: Int): Boolean {
                (currentScreen as BaseScreen).apply {
                    actionMap[keycode]?.let {
                        it.starting = false
                        doAction(it)
                    }
                }
                return super.keyUp(keycode)
            }
        })

        KtxAsync.initiate()

        assets.apply {
            setLoader<TiledMap> { TmxMapLoader(fileResolver) }
            loadSync<TiledMap>("map.tmx")
            loadSync<Texture>("hero.png").setFilter(Linear, Linear)
            loadSync<Texture>("bush.png").setFilter(Linear, Linear)
            loadSync<Texture>("rock.png").setFilter(Linear, Linear)
            loadSync<Texture>("gatekeeper.png").setFilter(Linear, Linear)
            loadSync<Texture>("shopkeeper.png").setFilter(Linear, Linear)
            loadSync<Texture>("flyer.png").setFilter(Linear, Linear)
            loadSync<Texture>("treasure.png").setFilter(Linear, Linear)
            loadSync<Texture>("heart-icon.png").setFilter(Linear, Linear)
            loadSync<Texture>("arrow-icon.png").setFilter(Linear, Linear)
            loadSync<Texture>("coin.png").setFilter(Linear, Linear)
        }

        addScreen(FirstScreen(sizes, assets))
        setScreen<FirstScreen>()
    }
}
