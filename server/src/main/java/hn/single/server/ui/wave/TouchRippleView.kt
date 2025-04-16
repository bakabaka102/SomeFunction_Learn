package hn.single.server.ui.wave

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.toColorInt
import hn.single.server.R
import kotlin.math.max

class TouchRippleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val ripples = mutableListOf<Ripple>()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }

    private val handler = Handler(Looper.getMainLooper())
    private val maxRadius get() = max(width, height).toFloat()

    // Attributes
    private var rippleColor: Int = "#2196F3".toColorInt()
    private var rippleSpeed: Float = 0.012f
    private var rippleStrokeWidth: Float = 4f
    private var autoStart: Boolean = false
    private var rippleInterval: Int = 1200 // ms

    private val updateRunnable = object : Runnable {
        override fun run() {
            updateRipples()
            invalidate()
            handler.postDelayed(this, 16)
        }
    }

    private val autoRippleRunnable = object : Runnable {
        override fun run() {
            ripples.add(Ripple(width / 2f, height / 2f))
            handler.postDelayed(this, rippleInterval.toLong())
        }
    }

    init {
        context.withStyledAttributes(attrs, R.styleable.TouchRippleView) {
            rippleColor = getColor(R.styleable.TouchRippleView_rippleColor, rippleColor)
            rippleSpeed = getFloat(R.styleable.TouchRippleView_rippleSpeed, rippleSpeed)
            rippleStrokeWidth =
                getDimension(R.styleable.TouchRippleView_rippleStrokeWidth, rippleStrokeWidth)
            autoStart = getBoolean(R.styleable.TouchRippleView_autoStart, false)
            rippleInterval = getInt(R.styleable.TouchRippleView_rippleInterval, rippleInterval)
        }
        /*context.theme.obtainStyledAttributes(attrs, R.styleable.TouchRippleView, 0, 0).apply {
            try {
                rippleColor = getColor(R.styleable.TouchRippleView_rippleColor, rippleColor)
                rippleSpeed = getFloat(R.styleable.TouchRippleView_rippleSpeed, rippleSpeed)
                rippleStrokeWidth =
                    getDimension(R.styleable.TouchRippleView_rippleStrokeWidth, rippleStrokeWidth)
                autoStart = getBoolean(R.styleable.TouchRippleView_autoStart, false)
                rippleInterval = getInt(R.styleable.TouchRippleView_rippleInterval, rippleInterval)
            } finally {
                recycle()
            }
        }*/

        handler.post(updateRunnable)
        if (autoStart) handler.post(autoRippleRunnable)
    }

    // Tạo sóng khi chạm
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                ripples.add(Ripple(event.x, event.y))
                true
            }

            MotionEvent.ACTION_UP -> {
                // Notify accessibility framework
                performClick()
            }

            else -> super.onTouchEvent(event)
        }
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    private fun updateRipples() {
        val iterator = ripples.iterator()
        while (iterator.hasNext()) {
            val ripple = iterator.next()
            ripple.update()
            if (ripple.alpha <= 0) iterator.remove()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (ripple in ripples) {
            paint.color = ripple.color
            paint.strokeWidth = ripple.strokeWidth
            canvas.drawCircle(ripple.x, ripple.y, ripple.radius, paint)
        }
    }

    private inner class Ripple(val x: Float, val y: Float) {
        var progress = 0f
        val speed = rippleSpeed
        val strokeWidth = rippleStrokeWidth

        val radius: Float
            get() = progress * maxRadius

        val alpha: Int
            get() = ((1f - progress) * 255).toInt().coerceIn(0, 255)

        val color: Int
            get() = (rippleColor and 0x00FFFFFF) or (alpha shl 24)

        fun update() {
            progress += speed
        }
    }
}
