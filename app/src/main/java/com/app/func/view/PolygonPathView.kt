package com.app.func.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

class PolygonPathView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val maxNumOfSides = 8
    private var size = 600

    private val polygons = listOf(
        Polygon(3, 20f, Color.WHITE),
        Polygon(4, 40f, Color.BLUE),
        Polygon(5, 60f, Color.GREEN),
        Polygon(6, 80f, Color.RED),
        Polygon(7, 100f, Color.MAGENTA),
        Polygon(8, 120f, Color.CYAN)
    )

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawColor(Color.BLACK)
        paint.style = Paint.Style.STROKE
        for (i in 0..(maxNumOfSides - 3)) {
            val polygon = polygons[i]
            paint.color = polygon.color
            val path = createPathAndEffect(polygon, paint)
            canvas?.drawPath(path, paint)
        }
        super.onDraw(canvas)

    }

    private fun createPathAndEffect(polygon: Polygon, paint: Paint): Path {
        val path = Path()
        val angle = 2f * Math.PI / polygon.side
        val radius = polygon.radius
        val cx = pivotX
        val cy = pivotY
        path.moveTo(cx + (radius * cos(0f)), cy + (radius * sin(0f)))
        for (i in 1 until polygon.side) {
            path.lineTo(
                cx + (radius * cos(angle * i)).toFloat(),
                cy + (radius * sin(angle * i)).toFloat()
            )
        }
        path.close()
        return path
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //Step 1
        size = min(measuredWidth, measuredHeight)
        //Step 2
        setMeasuredDimension(size, size)
    }
}