package views.brainView

import brain.Brain
import main.GameConfig
import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.internal.component.impl.DefaultComponent
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy

class FocusedItemBox(
    private val brain: Brain,
    metadata: ComponentMetadata,
    renderingStrategy: DefaultComponentRenderingStrategy<FocusedItemBox>
): Component, DefaultComponent(
    metadata = metadata,
    renderer = renderingStrategy
) {
    override fun convertColorTheme(colorTheme: ColorTheme) = colorTheme.toPrimaryContentStyle()

    companion object {
        val NO_FOCUSED_POSITION = Position.create(GameConfig.BRAIN_WIDTH +1, GameConfig.BRAIN_HEIGHT +1)
    }
    var focusedItemPosition = NO_FOCUSED_POSITION

    fun hasFocusedItem(): Boolean {
        return focusedItemPosition != NO_FOCUSED_POSITION
    }
}

class FocusedItemBoxBuilder(
    private val brain: Brain
): BaseComponentBuilder<FocusedItemBox, FocusedItemBoxBuilder>(
    FocusedItemBoxRenderer(brain)
) {

    init {
        withPreferredSize(GameConfig.BRAIN_ITEM_WIDTH, GameConfig.BRAIN_ITEM_HEIGHT)
    }

    override fun createCopy(): Builder<FocusedItemBox> {
        println("BrainBoxBuilder calling 'createCopy()") //Want to see when and if this is ever called
        return FocusedItemBoxBuilder(brain)
    }

    override fun build(): FocusedItemBox {
        return FocusedItemBox(
            brain,
            metadata = createMetadata(),
            renderingStrategy = createRenderingStrategy())
    }
}

class FocusedItemBoxRenderer (val brain: Brain) : ComponentRenderer<FocusedItemBox> {

    override fun render(tileGraphics: TileGraphics, context: ComponentRenderContext<FocusedItemBox>) {
        val focusedItemBox = context.component

        if(focusedItemBox.hasFocusedItem())
            brain.callRender(focusedItemBox.focusedItemPosition.x, focusedItemBox.focusedItemPosition.y, tileGraphics)
    }
}