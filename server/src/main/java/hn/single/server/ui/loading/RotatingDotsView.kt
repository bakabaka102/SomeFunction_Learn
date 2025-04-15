package hn.single.server.ui.loading

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import hn.single.server.R
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import androidx.core.content.withStyledAttributes

class RotatingDotsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var dotCount = 12
    private var dotRadius = 12f  // Bán kính mỗi chấm (px)
    private var rotationAngle = 0f
    private var pulseTime = 0f // để điều khiển hiệu ứng chớp sáng
    private var rotateSpeed = 4000
    private var pulseEnabled = false


    init {

        attrs?.let {
            context.withStyledAttributes(it, R.styleable.RotatingDotsView) {
                dotCount = getInt(R.styleable.RotatingDotsView_dotCount, dotCount)
                dotRadius = getDimension(R.styleable.RotatingDotsView_dotRadius, dotRadius)
                rotateSpeed = getInt(R.styleable.RotatingDotsView_rotateSpeed, rotateSpeed)
                pulseEnabled = getBoolean(R.styleable.RotatingDotsView_pulseEnabled, pulseEnabled)
            }
        }
        startRotation()
    }

    private fun startRotation() {
        ValueAnimator.ofFloat(0f, 360f).apply {
            duration = 4000
            repeatCount = ValueAnimator.INFINITE
            interpolator = LinearInterpolator()
            addUpdateListener {
                rotationAngle = it.animatedValue as Float

                // Tăng thời gian để điều khiển chớp sáng
                pulseTime += 0.05f
                invalidate()

                invalidate()
            }
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f

        // Tính bán kính vòng tròn động theo kích thước View
        val maxRadius = min(centerX, centerY) - dotRadius * 2

        for (i in 0 until dotCount) {
            val angleDeg = (360.0 / dotCount) * i + rotationAngle
            val angleRad = Math.toRadians(angleDeg)

            val x = (centerX + maxRadius * cos(angleRad)).toFloat()
            val y = (centerY + maxRadius * sin(angleRad)).toFloat()

            // Gradient màu theo góc
            val hue = ((angleDeg % 360).toFloat())
            val baseColor = Color.HSVToColor(floatArrayOf(hue, 1f, 1f))

            // Hiệu ứng pulse: tính alpha dao động theo thời gian + offset theo vị trí
            val alpha = if (pulseEnabled) {
                val phaseOffset = i * (Math.PI * 2 / dotCount).toFloat()
                val pulseAlpha = (0.5f + 0.5f * sin(pulseTime + phaseOffset)).toFloat()
                (pulseAlpha * 255).toInt()
            } else {
                255
            }
            paint.color = (baseColor and 0x00FFFFFF) or (alpha shl 24)
            canvas.drawCircle(x, y, dotRadius, paint)
        }
    }

    //Nếu bạn dùng wrap_content, bạn nên override onMeasure() để cung cấp kích thước mặc định:
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val defaultSize = (dotRadius * 2 * 10).toInt() // ví dụ 240px
        val width = resolveSize(defaultSize, widthMeasureSpec)
        val height = resolveSize(defaultSize, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

}
