package com.timwe.tti2sdk.data.entity

data class Position(
    var position: Int,
    var coordenateX: Float,
    var coordenateY: Float
)

fun mapPosition() : ArrayList<Position>{
    val positions = arrayListOf<Position>()
    positions.add(Position(1, 283.5f, 892.5f))
    positions.add(Position(2, 600.0f, 1005.5f))
    positions.add(Position(3, 799.0f, 1108.0f))
    positions.add(Position(4, 952.0f, 1192.0f))
    positions.add(Position(5, 1434.0f, 1339.0f))
    positions.add(Position(6, 1599.0f, 1428.0f))
    positions.add(Position(7, 2047.0f, 1416.0f))
    positions.add(Position(8, 2577.0f, 1390.0f))
    positions.add(Position(9, 2727.0f, 1367.0f))
    positions.add(Position(10, 2877.0f, 1353.0f))
    positions.add(Position(11, 2995.0f, 1347.0f))
    positions.add(Position(12, 3294.0f, 1422.0f))
    positions.add(Position(13, 3447.0f, 1431.0f))
    positions.add(Position(14, 3254.0f, 1281.0f))
    positions.add(Position(15, 3481.0f, 1264.0f))
    positions.add(Position(16, 2441.0f, 1039.0f))
    positions.add(Position(17, 2115.0f, 1054.0f))
    positions.add(Position(18, 2556.0f, 904.0f))
    positions.add(Position(19, 2588.0f, 722.0f))
    positions.add(Position(20, 2712.0f, 754.0f))
    positions.add(Position(21, 3031.0f, 789.0f))
    positions.add(Position(22, 2987.0f, 944.0f))
    positions.add(Position(23, 3267.0f, 1074.0f))
    positions.add(Position(24, 3532.0f, 1045.0f))
    positions.add(Position(25, 3152.0f, 705.0f))
    positions.add(Position(26, 3459.0f, 673.0f))
    positions.add(Position(27, 3545.0f, 553.0f))
    positions.add(Position(28, 3845.0f, 826.0f))
    positions.add(Position(29, 3776.0f, 604.0f))
    positions.add(Position(30, 4276.0f, 786.0f))

    return positions
}

fun findIndex(positions: ArrayList<Position>, xValue: Float, yValue: Float): Int{
    val tolerancia = 20f
    val valuesX = arrayListOf<Float>()
    val valuesY = arrayListOf<Float>()
    positions.forEach {
        valuesX.add(it.coordenateX)
        valuesY.add(it.coordenateY)
    }

    //Busca a posicao na lista X
    val valoresProximosEmX = valuesX.filterIndexed { index, valor -> kotlin.math.abs(valor - xValue ) <= tolerancia  }
    val posicoesValoresProximosX = valuesX.mapIndexedNotNull{ index, valor -> if(valoresProximosEmX.contains(valor)) index + 1 else null}

    val valoresProximosEmY = valuesY.filterIndexed { index, valor -> kotlin.math.abs(valor - yValue ) <= tolerancia  }
    val posicoesValoresProximosY = valuesY.mapIndexedNotNull{ index, valor -> if(valoresProximosEmY.contains(valor)) index + 1 else null}

    val a = 10

    return 1
}