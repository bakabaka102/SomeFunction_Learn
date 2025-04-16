package com.app.func.view.animations_custom

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.content.withStyledAttributes
import androidx.core.graphics.toColorInt
import com.app.func.R
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

class ArcStartPulseGaugeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    // XML attrs
    private var minValue = 0
    private var maxValue = 100
    private var timeAnim = 1500
    private var targetValue = 75

    private var currentSweep = 0f
    private var animator: ValueAnimator? = null

    private val arcPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val pulsePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val labelPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val markerPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val arcRect = RectF()
    private val baseStartAngle = 150f
    private val arcSweep = 240f
    private var arcColor = Color.LTGRAY
    private var pulseColor = "#ee4724".toColorInt()
    private var textColor = "#ee4724".toColorInt()
    private var markerColor = Color.WHITE
    private var duration = 1500
    private val textBounds = Rect()
    var onValueChangedListener: ((Int) -> Unit)? = null


    init {
        // Đọc từ XML
        attrs?.let {
            context.withStyledAttributes(it, R.styleable.ArcStartPulseGaugeView) {
                minValue = getInt(R.styleable.ArcStartPulseGaugeView_minValue, minValue)
                maxValue = getInt(R.styleable.ArcStartPulseGaugeView_maxValue, maxValue)
                timeAnim = getInt(R.styleable.ArcStartPulseGaugeView_timeAnim, duration)
                targetValue = getInt(R.styleable.ArcStartPulseGaugeView_targetValue, targetValue)
                arcColor = getColor(R.styleable.ArcStartPulseGaugeView_gaugeColor, arcColor)
                pulseColor = getColor(R.styleable.ArcStartPulseGaugeView_pulseColor, pulseColor)
                textColor = getColor(R.styleable.ArcStartPulseGaugeView_textGaugeColor, textColor)
                markerColor =
                    getColor(R.styleable.ArcStartPulseGaugeView_markerPointColor, markerColor)
            }
        }
        setupPaints()
        startAnim()
    }

    private fun setupPaints() {
        arcPaint.apply {
            color = arcColor
            style = Paint.Style.STROKE
            strokeWidth = 24f
            strokeCap = Paint.Cap.ROUND
        }

        pulsePaint.apply {
            color = pulseColor
            style = Paint.Style.STROKE
            strokeWidth = 24f
            strokeCap = Paint.Cap.ROUND
        }

        textPaint.apply {
            color = textColor
            textSize = 100f
            textAlign = Paint.Align.CENTER
            typeface = Typeface.DEFAULT_BOLD
        }

        labelPaint.apply {
            color = textColor
            textSize = 40f
            typeface = Typeface.DEFAULT_BOLD
            textAlign = Paint.Align.CENTER
        }

        markerPaint.apply {
            color = markerColor
            style = Paint.Style.FILL
            setShadowLayer(
                10f,     // độ lan của bóng
                0f,          // dịch ngang
                0f,          // dịch dọc
                Color.argb(128, 0, 0, 0) // màu bóng (đen mờ)
            )
        }
    }

    init {
        startAnim()
    }

    fun setTargetValueAnimated(value: Int) {
        targetValue = value.coerceIn(minValue, maxValue)
        startAnim() // chạy lại animation tới giá trị mới
    }


    private fun startAnim() {
        val targetPercent = (targetValue - minValue).toFloat() / (maxValue - minValue)
        val maxSweep = arcSweep * targetPercent

        animator = ValueAnimator.ofFloat(0f, maxSweep).apply {
            duration = timeAnim.toLong()
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            interpolator = AccelerateDecelerateInterpolator()
            addUpdateListener {
                currentSweep = it.animatedValue as Float
                invalidate()
            }
            start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val padding = 40f
        val arcSize = width - (padding * 2)
        val extraTopSpace = 60f //Space to drawText on Top of marker
        //arcRect.set(padding, padding + extraTopSpace, width - padding, height - padding)
        arcRect.set(padding, padding + extraTopSpace,
            padding + arcSize,
            padding + extraTopSpace + arcSize)
       /* val centerX = width / 2f
        val centerY = height / 2f
        val radius = (width - padding * 2) / 2f*/

        val centerX = arcRect.centerX()
        val centerY = arcRect.centerY()
        val radius = arcRect.width() / 2f

        // 1. Vẽ cung nền
        canvas.drawArc(arcRect, baseStartAngle, arcSweep, false, arcPaint)
        // 2. Vẽ đoạn pulse (animation)
        canvas.drawArc(arcRect, baseStartAngle, currentSweep, false, pulsePaint)

        // 3. Tính giá trị hiện tại
        val percent = currentSweep / arcSweep
        val currentValue = (minValue + (percent * (maxValue - minValue))).roundToInt()
        canvas.drawText("$currentValue", centerX, centerY + textPaint.textSize / 3, textPaint)

        // 4. Vẽ điểm marker cho targetValue
        val targetAngle =
            baseStartAngle + arcSweep * (targetValue - minValue).toFloat() / (maxValue - minValue)
        val rad = Math.toRadians(targetAngle.toDouble())
        val markerX = centerX + radius * cos(rad).toFloat()
        val markerY = centerY + radius * sin(rad).toFloat()
        canvas.drawCircle(markerX, markerY, 20f, markerPaint)

        // 5. Vẽ text phía trên marker
        val offsetY = 10f // khoảng cách phía trên marker
        val textAboveMarker = targetValue.toString()
        // Tính vị trí text phía trên marker (dọc theo cung)
        labelPaint.getTextBounds(textAboveMarker, 0, textAboveMarker.length, textBounds)
        val textHeight = textBounds.height()
        //val offset = 16f
        val textX = markerX
        val textY = markerY - offsetY - textHeight
        /*
        val showAbove = targetAngle.toFloat() !in 225f..315f
        val textY = if (showAbove) {
            markerY - offsetY - textHeight
        } else {
            markerY + offsetY + textHeight
        }*/
        canvas.drawText(textAboveMarker, textX, textY, labelPaint)

        // 6. Vẽ min/max dưới hai đầu cung
        drawMinMaxText(canvas, centerX, centerY, radius)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                val x = event.x - width / 2f
                val y = event.y - height / 2f

                val touchAngle = (Math.toDegrees(atan2(y.toDouble(), x.toDouble())) + 360) % 360

                // Kiểm tra nếu touchAngle nằm trong cung sweep
                val startAngle = (baseStartAngle + 360) % 360
                val endAngle = (startAngle + arcSweep) % 360
                val inSweep = if (endAngle > startAngle) {
                    touchAngle in startAngle..endAngle
                } else {
                    touchAngle >= startAngle || touchAngle <= endAngle
                }

                if (inSweep) {
                    // Convert angle to value
                    val ratio = ((touchAngle - baseStartAngle + 360) % 360) / arcSweep
                    val newValue = (minValue + ratio * (maxValue - minValue)).toInt()

                    if (newValue != targetValue) {
                        setTargetValueAnimated(newValue)
                        onValueChangedListener?.invoke(newValue) // optional callback
                    }
                }
                return true
            }

            MotionEvent.ACTION_UP -> {
                // Notify accessibility framework
                performClick()
            }

        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }


    private fun drawMinMaxText(canvas: Canvas, centerX: Float, centerY: Float, radius: Float) {
        val minRad = Math.toRadians(baseStartAngle.toDouble())
        val maxRad = Math.toRadians((baseStartAngle + arcSweep).toDouble())

        val labelOffset = 40f
        canvas.drawText(
            "$minValue",
            centerX + radius * cos(minRad).toFloat(),
            centerY + radius * sin(minRad).toFloat() + labelOffset,
            labelPaint,
        )
        canvas.drawText(
            "$maxValue",
            centerX + radius * cos(maxRad).toFloat(),
            centerY + radius * sin(maxRad).toFloat() + labelOffset,
            labelPaint,
        )
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        animator?.cancel()
    }
}
