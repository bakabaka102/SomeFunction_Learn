package hn.single.server.ui.waveline

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import kotlin.math.sin

class WaveLineView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLUE
        strokeWidth = 4f
        style = Paint.Style.FILL
    }
    val path = Path()
    private val waveSpeed = 0.05f
    private val waveAmplitude = 50f // Độ cao của sóng
    private val waveLength = 2 * Math.PI // Chiều dài sóng

    private var phase = 0f // Để tạo chuyển động sóng

    private val updateRunnable = object : Runnable {
        override fun run() {
            phase += waveSpeed
            invalidate() // Redraw the view
            handler.postDelayed(this, 16) // khoảng 60 FPS
        }
    }

    private val handler = Handler(Looper.getMainLooper())

    init {
        handler.post(updateRunnable)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width.toFloat()
        val height = height.toFloat()
        path.moveTo(0f, height / 2)

        // Vẽ đường sóng
        for (x in 0 until width.toInt()) {
            val y = (waveAmplitude * sin((x + phase) * waveLength / width)).toFloat() + height / 2
            path.lineTo(x.toFloat(), y)
        }

        canvas.drawPath(path, paint)
    }
}
