package com.app.func.view.chart.stock

import android.animation.Animator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.view.Gravity
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.app.func.R
import com.app.func.utils.Utils.formatTemperature
import kotlin.math.cos
import kotlin.math.sin
import androidx.core.graphics.toColorInt
import androidx.core.view.isGone
import androidx.core.view.isVisible
import kotlin.math.abs

class TemperatureView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    private lateinit var textViewTemperature: TextView
    private lateinit var textViewHint: TextView
    private val rect: Rect = Rect()
    private val paintBorderBackground = Paint()
    private val paintBorder = Paint().apply {
        style = Paint.Style.STROKE
        isAntiAlias = true
    }
    private val paintLine = Paint()
    private val paintText = Paint()
    private val paintCircle = Paint()
    private val paintCircleOutline = Paint()
    private val paintPointCurrent = Paint()
    private var colorFrom = "#FF7373".toColorInt()
    private var colorMid = "#FF7373".toColorInt()
    private var colorTo = "#D62020".toColorInt()
    private val colorCircleOutline = "#33FFFFFF".toColorInt()
    private val colorCircleProgress = "#CAE2CA".toColorInt()
    private var colorBackgroundBorder = "#CAE2CA".toColorInt()
    private var gradientColors = intArrayOf(colorFrom, colorTo)
    private var gradientPositions = floatArrayOf(0 / 360f, 360f / 360f)
    private var start = 0f
    private var angle = 0f
    private var oval: RectF = RectF()
    private var centerX: Float = 0f
    private var centerY: Float = 0f
    private var radius: Float = 0f
    private val progress = 66f
    private var mListValue = arrayListOf<String>()

    private var _currentAngle = 0f
    private var _newSweepAngle = 0f
    private var _targetSettingAngle = 0f

    private var minValue = 0
    private var maxValue = 0

    private var _state: State = State.STABLE
    private var _currentTemp: Int? = null
    private var _targetTemp: Int? = null
    private var _inProgressSetting = false
    private var _animator: ValueAnimator? = null
    private var _runningState: RunningMode = RunningMode.IDLE
    private var strokeWidthBorder = 0f
    private var _padding32dp = 0f
    private var _paddingBottom40dp = 0f
    private var _radiusDotWhite = 0f
    private var _radiusShaderDotWhite = 0f
    private var widthScreen = 0
    private var _progressGradientColors = intArrayOf(
        "#84CCF5".toColorInt(),
        "#6AB8C6".toColorInt(),
        "#64B22A".toColorInt(),
    )
    private var _progressGradientPositions = floatArrayOf(0f / 360f, 180f / 360f, 360f / 360f)

    init {
        widthScreen = Resources.getSystem().displayMetrics.widthPixels
        strokeWidthBorder = context.resources.getDimension(R.dimen.dimen_14sp)
        _radiusDotWhite = context.resources.getDimension(R.dimen.dimen_14dp)
        _radiusShaderDotWhite = context.resources.getDimension(R.dimen.dimen_10dp)
        _padding32dp = RATIO_PADDING_TOP * widthScreen
        _paddingBottom40dp = RATIO_PADDING_BOTTOM * widthScreen
        initColorByState()
        initPaint()
        initText()
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthScreen = Resources.getSystem().displayMetrics.widthPixels
        radius = (RATIO_CIRCLE_PROGRESS * widthScreen) / 2
        val height = radius * 2 + _paddingBottom40dp
        super.setMeasuredDimension(width, height.toInt())

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        radius = (RATIO_CIRCLE_PROGRESS * widthScreen) / 2
        centerX = width.toFloat()/ 2
        centerY = (height.toFloat() - _paddingBottom40dp) / 2 + _padding32dp

        oval.set(centerX - radius, centerY - radius, centerX + radius, centerY + radius)

        val heightOfTextViews = textViewTemperature.height + textViewHint.height
        if (textViewHint.isGone) {
            textViewTemperature.y = (centerY - radius * RATIO_CIRCLE_IN) +
                    (radius * RATIO_CIRCLE_IN * 2 - textViewTemperature.height) / 2
        } else {
            textViewTemperature.y = (centerY - radius * RATIO_CIRCLE_IN) +
                    (radius * RATIO_CIRCLE_IN * 2 - heightOfTextViews) / 2

            textViewHint.y = textViewTemperature.y + textViewTemperature.height
        }
        canvas.let {
            drawCircleCenterOut(it)
            drawCircleCenterIn(it)
            drawBorderBackground(it)
            drawNumeral(it)
            if (_runningState == RunningMode.REDUCE) {
                drawBorder(it)
                drawTextSettingTemp(it)
                drawDotCircle(formatStringTemp(_targetTemp ?: 0), canvas)
            }
            if (_runningState == RunningMode.INCREASE) {
                drawTextSettingTemp(it)
                drawDotCircle(formatStringTemp(_targetTemp ?: 0), canvas)
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initShader()
        initValues()
    }

    fun getRunningState(): RunningMode {
        return _runningState
    }

    private fun initPaint() {
        paintCircleOutline.apply {
            strokeWidth = strokeWidthBorder
            style = Paint.Style.FILL_AND_STROKE
            strokeCap = Paint.Cap.ROUND
            color = colorCircleOutline
            isAntiAlias = true
        }

        paintCircle.apply {
            strokeWidth = strokeWidthBorder
            style = Paint.Style.FILL_AND_STROKE
            strokeCap = Paint.Cap.ROUND
            color = colorFrom
            isAntiAlias = true
        }

        paintBorder.apply {
            strokeWidth = strokeWidthBorder
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            color = colorCircleProgress
            isAntiAlias = true
        }

        paintBorderBackground.apply {
            strokeWidth = strokeWidthBorder
            color = colorBackgroundBorder// Color.GRAY // default
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            isAntiAlias = true
        }

        paintLine.apply {
            strokeWidth = 5f
            color = colorFrom // default
            style = Paint.Style.STROKE
            strokeCap = Paint.Cap.ROUND
            isAntiAlias = true
        }

        paintText.apply {
            typeface = ResourcesCompat.getFont(context, R.font.roboto_medium)
            strokeWidth = 3f
            color = Color.BLACK// default
            style = Paint.Style.FILL
            isAntiAlias = true
            textSize = resources.getDimensionPixelSize(R.dimen.dimen_14sp).toFloat()
        }

        paintPointCurrent.apply {
            color = Color.WHITE
            style = Paint.Style.FILL_AND_STROKE
            strokeCap = Paint.Cap.ROUND
            isAntiAlias = true
            setShadowLayer(_radiusShaderDotWhite, 0f, 0f, "#664F5979".toColorInt())
            setLayerType(LAYER_TYPE_NONE, this)
        }
    }

    private fun initColorByState() {
        when (_state) {
            State.STABLE, State.UNSTABLE -> {
                colorFrom = "#84CCF5".toColorInt()
                colorMid = "#6AB8C6".toColorInt()
                colorTo = "#64B22A".toColorInt()
                colorBackgroundBorder = "#CAE2CA".toColorInt()
                gradientColors = intArrayOf(colorFrom, colorMid, colorTo)
                gradientPositions = floatArrayOf(0f / 360f, 180f / 360f, 360f / 360f)
            }

            State.WARNING_RED, State.STOP_WORKING -> {
                colorFrom = "#FF7373".toColorInt()
                colorMid = "#F24648".toColorInt()
                colorTo = "#D62020".toColorInt()
                colorBackgroundBorder = "#F9D7D7".toColorInt()
                gradientColors = intArrayOf(colorFrom, colorMid, colorTo)
                gradientPositions = floatArrayOf(0f / 360f, 180f / 360f, 360f / 360f)
            }

            State.NO_SIGNAL -> {
                colorFrom = "#E6EBED".toColorInt()
                colorTo = "#ACBAC2".toColorInt()
                colorBackgroundBorder = "#DEE5EA".toColorInt()
                gradientColors = intArrayOf(colorFrom, colorTo)
                gradientPositions = floatArrayOf(0f / 360f, 360f / 360f)
            }

            State.WARNING_YELLOW -> {
                colorFrom = "#FFD585".toColorInt()
                colorMid = "#FFBA34".toColorInt()
                colorTo = "#FCA600".toColorInt()
                colorBackgroundBorder = "#F8E8C9".toColorInt()
                gradientColors = intArrayOf(colorFrom, colorMid, colorTo)
                gradientPositions = floatArrayOf(0f / 360f, 180f / 360f, 360f / 360f)
            }
        }
    }

    private fun drawCircleCenterOut(canvas: Canvas) {
        canvas.drawCircle(centerX, centerY, radius * 0.75f, paintCircle)
    }

    private fun drawCircleCenterIn(canvas: Canvas) {
        canvas.drawCircle(centerX, centerY, radius * RATIO_CIRCLE_IN, paintCircleOutline)
    }

    private fun drawBorder(canvas: Canvas) {
        canvas.drawArc(oval, _currentAngle, _newSweepAngle, false, paintBorder)
    }

    private fun drawBorderBackground(canvas: Canvas) {
        canvas.drawArc(oval, start, angle, false, paintBorderBackground)
    }

    private fun drawNumeral(canvas: Canvas) {
        mListValue.clear()
        for (i in minValue..maxValue) {
            mListValue.add("${i.formatTemperature()}°C")
        }
        if (mListValue.isNotEmpty()) {
            drawMaxMinValue(canvas)
        }
    }

    private fun drawTextSettingTemp(canvas: Canvas) {
        drawTextTempSelected(
            temp = _targetTemp ?: 0,
            canvas = canvas,
        )
    }

    private fun drawMaxMinValue(canvas: Canvas) {
        drawTextTempSelected(maxValue, canvas)
        drawTextTempSelected(minValue, canvas)
    }

    private fun drawTextTempSelected(
        temp: Int,
        canvas: Canvas
    ) {
        val item = formatStringTemp(temp)
        val position = mListValue.indexOf(item) //getPosition item
        val total = 240f// 2/3 circle
        val edge = ((total / (mListValue.size - 1))) * (position) //edge pos
        val angle = edge + 150f //angle now
        val radians = angle * (Math.PI / 180) // calc radians
        val radiusX = radius * 1.3
        val radiusY = radius * 1.10
        paintText.getTextBounds(item, 0, item.length, rect)
        val xText = width.toFloat() / 2 + cos(radians) * radiusX - rect.width().toFloat() / 2
        val yTEXT = if (angle in 210f..330f) {
            centerY + sin(radians) * radiusY - rect.height().toFloat() / 2
        } else centerY + sin(radians) * radius + rect.height().toFloat() / 2
        canvas.drawText(item, xText.toFloat(), yTEXT.toFloat(), paintText)
    }

    private fun drawDotCircle(item: String, canvas: Canvas) {
        val position = mListValue.indexOf(item) //getPosition item
        val total = progress * 360 / 100 // 66f == 2/3 circle
        val edge = ((total / (mListValue.size)) * 1.05) * position //edge pos
        val angle = edge + 150f //angle now
        val radians = angle * (Math.PI / 180) // calc radians

        val xCircle = (width / 2 + cos(radians) * radius).toInt()
        val yCircle = (centerY + sin(radians) * radius).toInt()

        canvas.drawCircle(xCircle.toFloat(), yCircle.toFloat(), _radiusDotWhite, paintPointCurrent)
    }

    private fun calculateAngle(temp: Int): Float {
        val values = formatStringTemp(temp)
        val position = mListValue.indexOf(values) //getPosition item
        val total = progress * 360 / 100 // 66f == 2/3 circle
        val edge = ((total / (mListValue.size))) * position //edge pos
        val angle = edge + 150f //angle now
        return angle.toFloat()
    }

    fun setMinMaxProgress(minValue: Int, maxValue: Int) {
        this.minValue = minValue
        this.maxValue = maxValue
        mListValue.clear()
        for (i in minValue..maxValue) {
            mListValue.add("${i.formatTemperature()}°C")
        }
        invalidate()
    }

    private fun initValues() {
        angle = progress * 360 / 100 // total
        val remain = 360 - angle
        val startAngle = remain / 2
        start = 90f + startAngle
    }

    private fun initShader() {
        if (width <= 0 || height <= 0) return
        val shader =
            SweepGradient(
                width / 2f, height / 2f, _progressGradientColors,
                _progressGradientPositions
            ).apply {
                val rotate = 90f
                val gradientMatrix = Matrix()
                gradientMatrix.preRotate(rotate, width / 2F, height / 2F)
                setLocalMatrix(gradientMatrix)
            }
        paintBorder.shader = shader
        paintLine.shader = shader

        val shaderCircle = RadialGradient(
            (width / 2).toFloat(), 1f,
            (height / 3).toFloat(), colorTo, colorFrom, Shader.TileMode.MIRROR
        )
        paintCircle.shader = shaderCircle
    }

    @SuppressLint("ResourceType")
    private fun initText() {
        val layoutParamsTitle = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        val layoutParamsDescription =
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        layoutParamsTitle.addRule(CENTER_HORIZONTAL)
        val tempString = if (_currentTemp == null) {
            "--°C"
        } else formatStringTemp(_currentTemp ?: 0)
        textViewTemperature = TextView(context).apply {
            layoutParams = layoutParamsTitle
            typeface = ResourcesCompat.getFont(context, R.font.roboto_bold)
            id = 1
            textSize = 52f
            includeFontPadding = false
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            text = tempString
        }
        layoutParamsDescription.addRule(CENTER_HORIZONTAL)
        layoutParamsDescription.addRule(BELOW, textViewTemperature.id)
        textViewHint = TextView(context).apply {
            layoutParams = layoutParamsDescription
            typeface = ResourcesCompat.getFont(context, R.font.roboto_medium)
            includeFontPadding = false
            textSize = 14f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
            text = context.resources.getString(R.string.current_temperature)
        }
        removeAllViews()
        addView(textViewTemperature)
        addView(textViewHint)
    }

    fun updateState(state: State) {
        if (_state == state) return
        invalidate()
        _state = state
        if (_state == State.NO_SIGNAL || _state == State.STOP_WORKING) {
            textViewHint.isVisible = _state != State.NO_SIGNAL
            textViewTemperature.text = "--°C"
        } else {
            textViewHint.isVisible = true
        }
        initColorByState()
        initPaint()
        initShader()
        initValues()
    }

    fun setTargetTemp(temp: Int) {
        if (_currentTemp == null) return
        _targetTemp = temp
        _inProgressSetting = true
        _runningState = when {
            _targetTemp == _currentTemp -> RunningMode.IDLE
            _targetTemp!! > _currentTemp!! -> RunningMode.INCREASE
            _targetTemp!! < _currentTemp!! -> RunningMode.REDUCE
            else -> RunningMode.IDLE
        }
        _currentAngle = if ((_currentTemp ?: 0 )> maxValue) {
            calculateAngle(maxValue)
        } else {
            calculateAngle(_currentTemp ?: 0)
        }
        _targetSettingAngle = calculateAngle(_targetTemp ?: 0)
        _newSweepAngle = -abs(_targetSettingAngle - _currentAngle)
        if (_runningState == RunningMode.INCREASE) {
            invalidate()
            return
        }
        if (_runningState == RunningMode.REDUCE) {
            val updateListener: (Float) -> Unit = { animatedValue ->
                _newSweepAngle = animatedValue
                invalidate()
            }
            val endListener: () -> Unit = {
            }
            startAnimation(0f, _newSweepAngle, updateListener, endListener)
        }
    }

    fun setCurrentTemp(value: Int) {
        if (!_inProgressSetting) {
            _currentTemp = value
            _currentAngle = calculateAngle(value)
        }
        setTemperatureTitle(formatStringTemp(value))
    }

    private fun startAnimation(
        start: Float,
        end: Float,
        updateListener: (Float) -> Unit,
        endListener: () -> Unit
    ) {
        _animator = ValueAnimator.ofFloat(start, end).apply {
            cancel()
            addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {
                    endListener.invoke()
                }

                override fun onAnimationCancel(animation: Animator) {
                }

                override fun onAnimationRepeat(animation: Animator) {
                }
            })
            addUpdateListener {
                updateListener.invoke(it.animatedValue as Float)
            }
            repeatMode = ValueAnimator.REVERSE
            repeatCount = ValueAnimator.INFINITE
            duration = 1000L
            start()
        }
    }

    fun cancelSettingTemp() {
        _runningState = RunningMode.IDLE
        _inProgressSetting = false
        _animator?.cancel()
        _animator = null
        invalidate()
    }

    fun resetItemInView() {
        _currentAngle = 0f
        _newSweepAngle = 0f
    }

    private fun setTemperatureTitle(title: String) {
        textViewTemperature.text = title
    }

    private fun formatStringTemp(temp: Int): String {
        return String.format(
            context.resources.getString(R.string.temp_format), temp.formatTemperature()
        )
    }

    enum class RunningMode {
        REDUCE,
        INCREASE,
        IDLE
    }

    enum class State {
        STABLE,
        UNSTABLE,
        WARNING_RED,
        WARNING_YELLOW,
        NO_SIGNAL,
        STOP_WORKING
    }

    companion object {
        private const val RATIO_CIRCLE_IN = 180f / 237f
        private const val RATIO_CIRCLE_PROGRESS = 237f / 375f
        private const val RATIO_PADDING_TOP = 32f / 375f
        private const val RATIO_PADDING_BOTTOM = 32f / 375f
    }
}