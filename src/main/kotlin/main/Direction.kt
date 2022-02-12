package main

import org.hexworks.zircon.api.data.Position

interface Direction {
    fun toRelativePosition(): Position
}

enum class DiagonalDirection: Direction {
    NORTH_WEST, NORTH_EAST, SOUTH_EAST, SOUTH_WEST;

    override fun toRelativePosition() : Position {
        return when(this){
            NORTH_WEST -> Position.create(-1, -1)
            NORTH_EAST -> Position.create(1, -1)
            SOUTH_EAST -> Position.create(1, 1)
            SOUTH_WEST -> Position.create(-1, 1)
        }
    }
}

enum class CardinalDirection : Direction {
    NORTH, EAST, SOUTH, WEST;

    companion object {
        fun fromInt(value: Int) = CardinalDirection.values().first { it.ordinal == (value%4) }
    }

    override fun toRelativePosition() : Position {
        return when(this){
            NORTH   -> Position.create(0, -1)
            EAST    -> Position.create(1, 0)
            SOUTH   -> Position.create(0, 1)
            WEST    -> Position.create(-1, 0)
        }
    }

    fun oppositeDirection() = this + SOUTH

    operator fun plus(toAdd: CardinalDirection) = fromInt(toAdd.ordinal + this.ordinal)
    operator fun inc() = fromInt(this.ordinal + 1)
}