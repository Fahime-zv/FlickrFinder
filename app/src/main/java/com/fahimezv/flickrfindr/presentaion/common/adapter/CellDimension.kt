package com.fahimezv.flickrfindr.presentaion.common.adapter


class CellDimension(
    val widthRatio: Float?,
    val width: Int,
    val height: Int?,
    val spacing: Int,
) {

    data class Size(val width: Int, val height: Int)

    data class Dimension(
        val size: Size,
        val cols: Int,
        val spacing: Int, // horizontal spacing between columns, and the side margins
    )

    /* Optimal Cell Sizing Algorithm */
    fun dimensions(effectiveWidth: Int): Dimension {
        val colNumber = effectiveWidth / width
        val sumOfCells = effectiveWidth - (spacing * (colNumber + 1))
        val cellWidth = sumOfCells / colNumber
        val cellHeight = if (widthRatio == null) height ?: throw IllegalArgumentException("Fill widthRatio or height") else (cellWidth / widthRatio).toInt()
        return Dimension(Size(cellWidth, cellHeight), colNumber, spacing)
    }

}