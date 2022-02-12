package misc

import brain.brainItems.BrainItem
import brain.brainItems.BrainItemRenderer.Companion.render
import main.GameConfig
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultComponent
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy

class ItemBox constructor(
    brainItem: BrainItem,
    metadata: ComponentMetadata,
    renderingStrategy: DefaultComponentRenderingStrategy<ItemBox>
): Component, DefaultComponent(
    metadata = metadata,
    renderer = renderingStrategy
) {

    override fun convertColorTheme(colorTheme: ColorTheme) = colorTheme.toPrimaryContentStyle()
//    override fun focusGiven(): UIEventResponse {
//        Brain.Debug.replaceWithDebugItem(brain, brainGridX, brainGridY)
//        return UIEventResponse.pass()
//    }
//
////    override fun mouseWheelRotatedDown(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
////        brain.getBrainItemAt(brainGridX, brainGridY).rotateCircuitry()
////        return super.mouseWheelRotatedDown(event, phase)
////    }
//
//    override fun keyPressed(event: KeyboardEvent, phase: UIEventPhase): UIEventResponse {
//        if(event.code == KeyCode.KEY_R){
//            brain.rotateCircuitry(brainGridX, brainGridY)
//        }
//        return super.keyPressed(event, phase)
//    }
//
//    override fun activated(): UIEventResponse {
//        return super.activated()
//    }
}

class ItemBoxBuilder(
    private val brainItem: BrainItem
): BaseComponentBuilder<ItemBox, ItemBoxBuilder>(
    ItemBoxRenderer(brainItem)
) {

    init {
        withPreferredSize(GameConfig.BRAIN_ITEM_WIDTH, GameConfig.BRAIN_ITEM_HEIGHT)
    }

    override fun createCopy(): Builder<ItemBox> {
        println("BrainBoxBuilder calling 'createCopy()") //Want to see when and if this is ever called
        return ItemBoxBuilder(brainItem)
    }

    override fun build(): ItemBox {
        return ItemBox(
            brainItem,
            metadata = createMetadata(),
            renderingStrategy = createRenderingStrategy())
    }
}

class ItemBoxRenderer (private val brainItem: BrainItem) : ComponentRenderer<ItemBox> {


    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<ItemBox>) {
        brainItem.render(tileGraphics, false)
    }
}