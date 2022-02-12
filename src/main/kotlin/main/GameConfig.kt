package main

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.graphics.StyleSet

object GameConfig {

    private val TILESET = CP437TilesetResources.rogueYun16x16()
    val THEME = ColorThemes.monokaiOrange()

    val brainStyleSet = StyleSet.newBuilder()
        .withForegroundColor(THEME.primaryForegroundColor)
        .withBackgroundColor(THEME.primaryBackgroundColor)
        .build()
//    val brainBoxFillerTile = TileBuilder.newBuilder()
//        .withForegroundColor(brainStyleSet.)

    //Sizing for the main application's window
    const val BASE_WINDOW_WIDTH = 80
    const val BASE_WINDOW_HEIGHT = 60

    const val DEBUG_EXTRA_WIDTH = 25
    const val DEBUG_EXTRA_HEIGHT = 0

    private const val DEBUG_MODE = 1
    fun isDebugMode(): Boolean = DEBUG_MODE == 1

//    infix fun toInt(v: Boolean) : Int{
//        return if(v) 1
//        else 0
//    }

    const val WINDOW_HEIGHT = BASE_WINDOW_HEIGHT + DEBUG_MODE * DEBUG_EXTRA_HEIGHT
    const val WINDOW_WIDTH = BASE_WINDOW_WIDTH + DEBUG_MODE * DEBUG_EXTRA_WIDTH


    fun buildAppConfig() = AppConfig.newBuilder()
        .withDefaultTileset(TILESET)
        .withSize(WINDOW_WIDTH, WINDOW_HEIGHT)
        .build()

    //Sizing of the brain with respect to brain items
    const val BRAIN_WIDTH = 10
    const val BRAIN_HEIGHT = 10

    const val BRAIN_ITEM_WIDTH = 5
    const val BRAIN_ITEM_HEIGHT = BRAIN_ITEM_WIDTH




}

typealias Array2D<T> = Array<Array<T>>

inline fun <reified T> create2DArray(width: Int, height: Int, fill: T) : Array2D<T> {
    return Array<Array<T>>(width) { row ->
        Array<T>(height) { col ->
            fill
        }
    }
}

private fun <T> Array2D<T>.width() = this.size
private fun <T> Array2D<T>.height() : Int{
    return if(this.size > 1) this[0].size
    else 0
}

fun <T> Array2D<T>.loop( block: T.(Int, Int) -> Unit ) {
    for(x in 0 until width())
        for(y in 0 until height())
            block.invoke(this[x][y], x, y)
}