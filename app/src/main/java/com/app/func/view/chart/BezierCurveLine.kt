package com.app.func.view.chart

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

class BezierCurveLine @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paintLineHorizontal = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 3f
        color = Color.RED
    }
    private val paintLineVertical = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = 6f
        color = Color.RED
    }

    private val listData =
        arrayOf(
            DataInfo(120f, 150f),
            DataInfo(121f, 183f),
            DataInfo(123f, 191f),
            DataInfo(124f, 185f),
            DataInfo(125f, 234f),
            DataInfo(126f, 266f),
            DataInfo(127f, 192f),
            DataInfo(128f, 188f),
            DataInfo(129f, 322f)
        )

    init {

    }

    var paint: Paint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.RED
        strokeWidth = 3f
    }
    var path: Path = Path()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        path.moveTo(34f, 259f)
        path.cubicTo(68f, 151f, 286f, 350f, 336f, 252f)
//        for (i in listData.indices - 3) {
//            path.cubicTo(
//                listData[i].cx,
//                listData[i].cy,
//                listData[i + 1].cx,
//                listData[i + 1].cy,
//                listData[i + 2].cx,
//                listData[i + 2].cy
//            )
//        }
        canvas?.drawPath(path, paint)
        for (i in 1..5) {
            canvas?.drawLine(
                0 + 20f,
                ((height - 30f) / 5f) * i,
                width - 50f,
                ((height - 30f) / 5f) * i,
                paintLineHorizontal
            )
        }

        canvas?.drawLine(
            0 + 20f,
            ((height - 30f) / 5f) - 30f,
            0 + 20f,
            height - 30f,
            paintLineVertical
        )

    }
}

data class DataInfo(val cx: Float, val cy: Float) {

//    fun createData(){
//        thi
//    }
}
