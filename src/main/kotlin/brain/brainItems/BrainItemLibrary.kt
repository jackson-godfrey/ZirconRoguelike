package brain.brainItems

import main.CardinalDirection
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.Symbols

object BrainItemLibrary {

    object EmptyBrainItem : BrainItem("Empty Brain Item",
        object: BrainItemRenderer {
            override val glyph = Symbols.SOLID_SQUARE
            //override val highlightForeground = TileColor.transparent()
            override val bordering = ItemBordering.EMPTY
        })
    { }

    val allBrainItems: List<BrainItem>
    private val builderMap = HashMap<String, () -> BrainItem> ()

    init {
        val allBrainItems: MutableList<BrainItem> = mutableListOf(EmptyBrainItem)
        allBrainItems.run{
            addAll(initialiseMemories())
            addAll(initialisePhobias())
            addAll(initialiseCores())
        }

        this.allBrainItems = allBrainItems.toList()
    }

    /**
     * Returns a BrainItem constructed using [block] and adds a key-value pair corresponding to [block] to the builderMap
     *
     * @param[T] Sub-class of BrainItem
     * @param[block] A lambda expression of the form {name -> T(..., name, ...)}, where T() is a constructor call for T
     */
    private fun <T:BrainItem> buildBrainItem(block: () -> T) : T {
        val brainItem: T = block.invoke()

        val key = brainItem.inGameName
        if (builderMap[key]!= null) throw Exception("Overriding key: $key of builderMap")
        else builderMap[key] = block

        return brainItem
    }

    fun buildFromExistingBrainItem(item: BrainItem): BrainItem {
        if(item == EmptyBrainItem) return EmptyBrainItem
        val key = item.inGameName

        return builderMap[key]?.invoke() ?: throw Exception("No value corresponding to $key in builderMap")
    }

    //region fun initialiseMemories()
    private fun initialiseMemories() : List<Memory> {
        return listOf(

            buildBrainItem{
                -> Memory("Shop Memory",
                outletDirections = mutableSetOf(CardinalDirection.NORTH, CardinalDirection.EAST),
                passiveAbilityHolder = object : PassiveAbilityHolder {
                    override fun extraShopOptions() = 1
                },

                renderer = object : BrainItemRenderer {
                    override val glyph = 'M'
                }
            )
            }

        )
    }
    //endregion

    //region fun initialisePhobias()
    private fun initialisePhobias(): List<Phobia> {
        return listOf(

            buildBrainItem{
                -> Phobia("Shop Phobia",
                outletDirections = mutableSetOf(CardinalDirection.NORTH),
                passiveAbilityHolder = object: PassiveAbilityHolder {
                    override fun extraShopOptions() = -1
                },

                renderer = object: BrainItemRenderer {
                    override val glyph = 'P'
                    override val highlight = TileColor.create(234, 100, 150)
                }
            )
            },

            buildBrainItem{
                -> Phobia("Cool New Phobia",
                outletDirections = mutableSetOf(CardinalDirection.NORTH),
                passiveAbilityHolder = object: PassiveAbilityHolder{},
                renderer = object: BrainItemRenderer {
                    override val glyph = 'C'
                    override val bordering = ItemBordering(
                        cornerGlyphs = CornerGlyphSet.DOUBLE_LINE,
                        nonCircuitryCardinalGlyphs = CardinalGlyphSet.ROTATED_DOUBLE_LINE,
                        circuitryCardinalGlyphs = CardinalGlyphSet.JUNCTION_DOUBLE_LINE,
                        cornersAreCircuitry = true
                    )
                }
            )
            }

        )
    }
    //endregion

    private fun initialiseCores() : List<Core> {
        return listOf(
            buildBrainItem {
                Core(
                    "Mirror Core",
                    outletDirections = mutableSetOf(CardinalDirection.NORTH, CardinalDirection.SOUTH),
                    renderer = object: BrainItemRenderer {
                         override val glyph = 'M'
                    }
                )
            }
        )
    }
}