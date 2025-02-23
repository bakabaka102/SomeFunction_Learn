package com.app.func.view.chart

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.Point
import android.graphics.Rect
import android.graphics.Shader
import android.util.AttributeSet
import android.view.Gravity.CENTER
import android.view.MotionEvent
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.Keep
import com.app.func.R
import com.app.func.view.chart.models.ReportResponse
import com.app.func.view.chart.models.ConsumeData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs
import kotlin.math.roundToInt

class StatisticsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    private val _paintTriangle = Paint().apply {
        style = Paint.Style.FILL
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 7f
        color = Color.parseColor("#4caf50")
        isAntiAlias = true
    }
    private val _paintCurve = Paint().apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 7f
        color = Color.parseColor("#53944E")
        isAntiAlias = true
        alpha = 200
    }
    private val _paintLine = Paint().apply {
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 3f
        color = Color.LTGRAY
        isAntiAlias = true
        alpha = 200
    }
    private val _paintText = Paint().apply {
        strokeWidth = 3f
        color = Color.parseColor("#b4b4b4")
        style = Paint.Style.FILL
        isAntiAlias = true
        textSize = context.resources.getDimension(R.dimen.dimen_12dp)
    }
    private val _paintFill = Paint().apply {
        color = Color.BLUE
        style = Paint.Style.FILL
        alpha = 150
        isAntiAlias = true
    }
    private val _paintCircle = Paint().apply {
        style = Paint.Style.FILL
        strokeCap = Paint.Cap.ROUND
        strokeWidth = 7f
        color = Color.parseColor("#FFFFFF")
        isAntiAlias = true
        setShadowLayer(20f, 0f, 0f, _shadowColor)
        setLayerType(LAYER_TYPE_SOFTWARE, this)
    }
    private val _paintTick = Paint().apply {
        style = Paint.Style.FILL_AND_STROKE
        strokeWidth = 2f
        strokeCap = Paint.Cap.ROUND
        color = Color.parseColor("#53944E")
        pathEffect = DashPathEffect(floatArrayOf(5f, 5f), 0f)
        isAntiAlias = true
    }
    private val _points = mutableListOf<Point>()
    private val _pointsAdjusted = mutableListOf<Point>()
    private val _chartRect = Rect()
    private val _curvePath = Path()
    private val _fillPath = Path()
    private var _trianglePath = Path()

    private var _currentPoint: Point = Point(0f, 0f)
    private var _startDate = ""
    private var _endDate = ""
    private var _textView: TextView? = null
    private var _listConsumeData: List<ConsumeData> = arrayListOf()
    private var _arrayListText: ArrayList<String> = arrayListOf()
    private var _xValueLongest = ""
    private var _xValueLongestWidth = 0
    private var _maxValue = 0
    private var _distanceFromMaxToMin = 0
    private var _isToday = false
    private var _paddingTop = 0f
    private var _paddingBottom = 0f
    private var _ratioBetweenCurvePathAndWidthRect = 0f
    private var _controlPoint1: ArrayList<Point> = arrayListOf()
    private var _controlPoint2: ArrayList<Point> = arrayListOf()
    private var _viewOnTouchEvent: Boolean = false
    private var _shadowColor = Color.parseColor("#664F5979")
    private var _arrayYValue: ArrayList<String> = arrayListOf()
    private var _arrayXValue: ArrayList<String> = arrayListOf()
    private var _needRecreateRect = false
    private var _chartType: ChartType = ChartType.TEMPERATURE

    init {
        //setLayerType(LAYER_TYPE_SOFTWARE, _paintCircle)
        addTextView()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        updateDrawingRect() // this function must call first
        adjustPoint()
        drawLine(canvas)
        drawCurve(canvas)
        eventTouchPoint(canvas)
    }

    private fun eventTouchPoint(canvas: Canvas) {
        if (_viewOnTouchEvent) {
            if (_currentPoint.y > 0) {
                canvas.drawLine(
                    _currentPoint.x,
                    _currentPoint.y,
                    _currentPoint.x,
                    _chartRect.bottom.toFloat(),
                    _paintTick
                )
                canvas.drawCircle(_currentPoint.x, _currentPoint.y, _RADIUS, _paintCircle)
                drawToolTip(canvas)
            }
        } else hideTextView()
    }

    private fun initShader() {
        val colorFrom = Color.parseColor("#63B128")
        val colorTo = Color.parseColor("#1A63B128")
        val linearGradient = LinearGradient(
            0f, height.toFloat(), 0f, 0f,
            colorTo, colorFrom, Shader.TileMode.CLAMP
        )
        _paintFill.shader = linearGradient
    }

    fun loadData(data: ReportResponse, isToday: Boolean = false, chartType: ChartType) {
        _chartType = chartType
        _needRecreateRect = true
        _listConsumeData = arrayListOf()
        _arrayXValue.clear()
        _arrayYValue.clear()
        _listConsumeData = when (_chartType) {
            ChartType.POWER -> simulateData(data.power)
            ChartType.TEMPERATURE -> simulateData(data.consumeData)
            ChartType.WATER_IN -> simulateData(data.consumeData)
            ChartType.WATER_OUT -> simulateData(data.power)
        }
        this._isToday = isToday

        _points.clear()
        _pointsAdjusted.clear()
        _listConsumeData.forEachIndexed { i, element ->
            val x = i.toFloat() // x la ngay tuong ung
            val y = element.value.toFloat()
            _points.add(Point(x, y))
            val xValue = when (_chartType) {
                ChartType.POWER -> "%.1f".format(Locale.ENGLISH, element.value.toFloatOrNull())
                    .plus("kW")

                ChartType.TEMPERATURE -> "%.2f".format(
                    Locale.ENGLISH,
                    element.value.toFloatOrNull()
                ).plus("°C")

                ChartType.WATER_OUT, ChartType.WATER_IN -> {
                    val floatData: Float = element.value.toFloatOrNull() ?: 0f
                    (floatData.roundToInt()).toString().plus(" TDS")
                }
            }
            _arrayXValue.add(xValue)
            if (!isToday) {
                _arrayYValue.add(Date(element.ts).toSString())
            } else {
                _arrayYValue.add(Date(element.ts).getHourReport())
            }
        }
        _startDate = if (!isToday) dateFromString(_arrayYValue.first()).toStringNotHaveYear() else _arrayYValue.first()
        _endDate = if (!isToday) dateFromString(_arrayYValue.last()).toStringNotHaveYear() else _arrayYValue.last()
        _points.sortWith(Point.comparator)
        invalidate()
    }

    private fun prepareData() {
        if (_points.isEmpty()) return
        var maxY = _points[0].y
        var minY = _points[0].y
        _points.forEach {
            if (it.y > maxY) {
                maxY = it.y
            }
            minY = minY.coerceAtMost(it.y)
        }
        _maxValue = when (_chartType) {
            ChartType.WATER_IN, ChartType.WATER_OUT -> {
                maxY.toInt() / 100 * 100 + 100
            }

            else -> {
                maxY.toInt() / 10 * 10 + 10
            }
        }

        var bottomValue =
            if (minY % 10 == -0f || minY % 10 == 0f) {
                minY.toInt() - 10
            } else minY.toInt() / 10 * 10

        if (
            _chartType == ChartType.POWER ||
            _chartType == ChartType.WATER_IN ||
            _chartType == ChartType.WATER_OUT
        ) {
            bottomValue = 0
        }

        val isSingleValue = _points.size == 1
        if (_maxValue == 0 && bottomValue == 0) {
            if (_chartType != ChartType.WATER_IN && _chartType != ChartType.WATER_OUT) {
                _maxValue += _VALUE_DEFAULT_IF_VALUE_EQUAL_0
            }
            // power have not negative value
            if (_chartType == ChartType.TEMPERATURE) {
                bottomValue -= _VALUE_DEFAULT_IF_VALUE_EQUAL_0
            }
        }
        if (isSingleValue) {
            _arrayXValue.add(_arrayXValue.first())
            _arrayYValue.add(_arrayYValue.first())
        }
        _distanceFromMaxToMin = abs(_maxValue - bottomValue)

        val span = _distanceFromMaxToMin.toDouble() / 4
        _arrayListText.clear()
        for (i in 0 until 5) {
            val value = if (_chartType == ChartType.WATER_IN || _chartType == ChartType.WATER_OUT) {
                (_maxValue - span * i).roundToInt().toString()
            } else "%.2f".format(locale = Locale.ENGLISH, _maxValue - span * i)
            if (value.length > _xValueLongest.length) {
                _xValueLongest = value
            }
            _arrayListText.add(value)
        }
        val _rect = Rect()
        _paintText.getTextBounds(_xValueLongest, 0, _xValueLongest.length, _rect)
        _xValueLongestWidth = _rect.width()
    }

    private fun simulateData(consumeData: List<ConsumeData>): List<ConsumeData> {
        // sort theo ngay
        consumeData.sortedWith(ConsumeData.comparatorSortDate)

        // gan lai du lieu ve ngay truoc do
        /*consumeData.forEachIndexed { index, element ->
            if (element.value == null) {
                if (index == 0) {
                    element.value = "0"
                } else {
                    element.value = consumeData[index - 1].value
                }
            }
        }*/
        return consumeData
    }

    private fun adjustPoint() {
        if (_points.isNotEmpty()) {
            val chartHeight: Int = _chartRect.bottom - _chartRect.top
            val chartWidth: Int = _chartRect.right - _chartRect.left - _RADIUS.toInt()

            _pointsAdjusted.clear()

            if (_points.size == 1) {
                val endPoint = Point(1f, _points.first().y)
                _points.add(endPoint)
            }
            val startPoint = _points[0]
            val endPoint = _points[_points.size - 1]
            val xStart = startPoint.x
            val xEnd = endPoint.x
            val axesSpan = xEnd - xStart // center x
            val chartLeft = _chartRect.left
            _points.forEach { point ->
                val xAdjusted = (point.x - xStart) * chartWidth / axesSpan + chartLeft
                val yAdjusted: Float = if (point.y > 0) {
                    _chartRect.top + ((_maxValue - point.y) / _distanceFromMaxToMin) * chartHeight
                } else {
                    _chartRect.top + ((_maxValue + abs(point.y)) / _distanceFromMaxToMin) * chartHeight
                }
                val newPointAdjusted = Point(xAdjusted, yAdjusted)
                _pointsAdjusted.add(newPointAdjusted)
            }
        }
    }

    private fun drawCurve(canvas: Canvas) {
        calcControlPoints()
        updateCurvePath(_fillPath, true)
        updateCurvePath(_curvePath, false)
        canvas.drawPath(_fillPath, _paintFill)
        canvas.drawPath(_curvePath, _paintCurve)
        calcRatioBetweenCurvePathAndWidthRect()
    }

    fun resetIndicatorView() {
        _viewOnTouchEvent = false
        invalidate()
    }

    private fun drawLine(canvas: Canvas) {
        val spaceLine = (_chartRect.bottom - _chartRect.top) / 4
        val x2 = width.toFloat() - _RADIUS
        val rect = Rect()
        _paintText.color = Color.parseColor("#7A868D")
        for (i in 0 until _arrayListText.size) {
            val y = i * spaceLine.toFloat() + _paddingTop
            // draw text
            val text = _arrayListText[i]
            _paintText.getTextBounds(_xValueLongest, 0, _xValueLongest.length, rect)
            val xText = if (text.length != _xValueLongest.length) {
                val _rect = Rect()
                _paintText.getTextBounds(text, 0, text.length, _rect)
                _chartRect.left.toFloat() - _BUFFER_PADDING_LEFT - _rect.width()
            } else {
                0f
            }
            val yText = y + rect.height() / 2
            val xLine = _chartRect.left
            canvas.drawText(text, xText, yText, _paintText)
            canvas.drawLine(xLine.toFloat(), y, x2, y, _paintLine)
        }
        _paintText.getTextBounds(_startDate, 0, _startDate.length, rect)

        val yTextBottom = height.toFloat()
        canvas.drawText(_startDate, _chartRect.left.toFloat(), yTextBottom, _paintText)
        canvas.drawText(
            _endDate,
            x2 - rect.width() - _PADDING_TEXT_RIGHT_BUFFER,
            yTextBottom,
            _paintText
        )
    }

    private fun drawToolTip(canvas: Canvas) {
        _textView?.apply {
            if (_textView?.visibility == VISIBLE) {
                val x1 = _currentPoint.x - _WIDTH_TRIANG / 2
                var inverted = true
                var y1 =
                    _currentPoint.y - _HEIGHT_TRIANG - _RADIUS - _PADDING_BETWEEN_DOT_CIRCLE_WITH_TRIANG
                if (y1 - (_textView?.height ?: 0) < _chartRect.top) {
                    y1 = _currentPoint.y + _HEIGHT_TRIANG + _RADIUS
                    inverted = false
                }
                drawTriangle(
                    x = x1.toInt(), y = y1.toInt(),
                    inverted = inverted, paint = _paintTriangle, canvas = canvas,
                )
            }
        }
    }

    private fun drawTriangle(
        x: Int,
        y: Int,
        width: Int = _WIDTH_TRIANG, // textView!!.height / 2
        height: Int = _HEIGHT_TRIANG,
        inverted: Boolean,
        paint: Paint,
        canvas: Canvas
    ) {
        val p1 = Point(x, y)
        val pointX = x + width / 2
        val pointY = if (inverted) y + height else y - height
        val p2 = Point(pointX, pointY)
        val p3 = Point(x + width, y)
        _trianglePath = Path()
        _trianglePath.fillType = Path.FillType.EVEN_ODD
        _trianglePath.moveTo(p1.x.toFloat(), p1.y.toFloat())
        _trianglePath.lineTo(p2.x.toFloat(), p2.y.toFloat())
        _trianglePath.lineTo(p3.x.toFloat(), p3.y.toFloat())
        _trianglePath.close()
        canvas.drawPath(_trianglePath, paint)
    }

    private fun calcRatioBetweenCurvePathAndWidthRect() {
        val widthRect = _chartRect.right - _chartRect.left
        val pm = PathMeasure(_curvePath, false)
        val curvePathLength = pm.length
        _ratioBetweenCurvePathAndWidthRect = widthRect / curvePathLength
    }

    private fun calcControlPoints() {
        _controlPoint1.clear()
        _controlPoint2.clear()
        for (i in 1 until _pointsAdjusted.size) {
            _controlPoint1.add(
                Point(
                    (_pointsAdjusted[i].x + _pointsAdjusted[i - 1].x) / 2,
                    _pointsAdjusted[i - 1].y
                )
            )
            _controlPoint2.add(
                Point(
                    (_pointsAdjusted[i].x + _pointsAdjusted[i - 1].x) / 2,
                    _pointsAdjusted[i].y
                )
            )
        }
    }

    /**
     * Please do not change order of this method in onDraw()
     * */
    private fun updateDrawingRect() {
        if (_needRecreateRect) {
            prepareData()
            getDrawingRect(_chartRect)
            val widthScreen = Resources.getSystem().displayMetrics.widthPixels
            _paddingTop = _RATIO_PADDING_TOP * widthScreen
            _paddingBottom = _RATIO_PADDING_BOTTOM * widthScreen
            _chartRect.left += _xValueLongestWidth + _BUFFER_PADDING_LEFT
            _chartRect.right = _chartRect.right
            _chartRect.top += _paddingTop.toInt()
            _chartRect.bottom -= _paddingBottom.toInt()
            _needRecreateRect = false
        }
    }

    private fun updateCurvePath(path: Path, fill: Boolean) {
        if (_pointsAdjusted.isNotEmpty()) {
            path.reset()
            path.moveTo(_pointsAdjusted.first().x, _pointsAdjusted.first().y)
            for (i in 1 until _pointsAdjusted.size) {
                path.cubicTo(
                    _controlPoint1[i - 1].x,
                    _controlPoint1[i - 1].y,
                    _controlPoint2[i - 1].x,
                    _controlPoint2[i - 1].y,
                    _pointsAdjusted[i].x,
                    _pointsAdjusted[i].y
                )
            }
            path.quadTo(
                _pointsAdjusted.last().x, _pointsAdjusted.last().y,
                _pointsAdjusted.last().x, _pointsAdjusted.last().y
            )
            if (fill) {
                path.lineTo(
                    _chartRect.right.toFloat() - _RADIUS,
                    _chartRect.bottom.toFloat()
                )
                path.lineTo(_chartRect.left.toFloat(), _chartRect.bottom.toFloat())
                path.lineTo(_chartRect.left.toFloat(), _pointsAdjusted[0].y)

                path.close()
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        initShader()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        parent.requestDisallowInterceptTouchEvent(true)
        touchOnScreen(event)
        return true
    }

    private fun touchOnScreen(event: MotionEvent) {
        /*if (event.action == MotionEvent.ACTION_CANCEL || event.action == MotionEvent.ACTION_UP) {
            _viewOnTouchEvent = false
            showTextView(false)
            invalidate()
        }*/
        if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE) {
            _viewOnTouchEvent = true
            val newX = event.x
            _currentPoint = findPointCorrespondingOnCurvePath(newX)
            invalidate()
            val p = _pointsAdjusted.find {
                it.x < _currentPoint.x + _BUFFER_PADDING_DETECT_POINT
                        && it.x > _currentPoint.x - _BUFFER_PADDING_DETECT_POINT
            }
            if (p != null) {
                val indexP = _pointsAdjusted.indexOf(p)
                val text = "${_arrayXValue[indexP]} | ${_arrayYValue[indexP]}"
                drawTextView(text)
            } else {
                hideTextView()
            }
        }
    }

    private fun findPointCorrespondingOnCurvePath(x: Float): Point {
        val pm = PathMeasure(_curvePath, false)
        val length = pm.length
        val width = _chartRect.right - _chartRect.left
        _ratioBetweenCurvePathAndWidthRect = width / length
        val distanceOnPath = (x - _chartRect.left) / _ratioBetweenCurvePathAndWidthRect
        val aCoordinates = FloatArray(2)
        pm.getPosTan(distanceOnPath, aCoordinates, null)
        return Point(aCoordinates[0], aCoordinates[1])
    }

    private fun addTextView() {
        if (_textView == null) {
            _textView = TextView(context)
            val textLayoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            textLayoutParams.addRule(CENTER_IN_PARENT)

            _textView?.apply {
                setBackgroundResource(R.drawable.shape_green)
                layoutParams = textLayoutParams
                textSize = 15f
                gravity = CENTER
                setTextColor(Color.WHITE)
                visibility = GONE
            }
            removeAllViews()
            addView(_textView)
        }
    }

    private fun showTextView(visible: Boolean) {
        if (_textView == null) return
        if (visible) {
            if (_textView?.visibility != VISIBLE) {
                _textView?.visibility = View.VISIBLE
            }
        } else {
            if (_textView?.visibility != GONE) {
                _textView?.visibility = View.GONE
            }
        }
    }

    private fun hideTextView() {
        if (_textView == null) return
        if (_textView?.visibility != GONE) {
            _textView?.visibility = View.GONE
        }
    }

    private fun drawTextView(text: String) {
        _textView?.apply {
            if (_textView?.visibility != VISIBLE) {
                _textView?.visibility = VISIBLE
            }
            this.text = text
        }
        _textView?.post {
            val maxX = _chartRect.right
            var widthText = 0
            if (_textView != null) {
                widthText = _textView!!.width
            }
            var newXText = _currentPoint.x
            var newYText =
                _currentPoint.y - _HEIGHT_TRIANG - _RADIUS -
                        _PADDING_BETWEEN_DOT_CIRCLE_WITH_TRIANG - _textView!!.height
            if (newYText < _chartRect.top) {
                newYText = _currentPoint.y + _HEIGHT_TRIANG + _RADIUS - 1F
            }
            if (newXText + widthText > maxX) {
                newXText = (maxX - widthText).toFloat()
            }
            if (newXText >= (_currentPoint.x - _WIDTH_TRIANG / 2)) {
                newXText -= _WIDTH_TRIANG
            }
            _textView?.apply {
                x = newXText
                y = newYText
            }
        }
    }

    /**
     * Point to draw
     * */
    data class Point(val x: Float = 0f, val y: Float = 0f) {

        override fun toString(): String {
            return "($x, $y)"
        }

        companion object {
            val comparator: Comparator<Point> = Comparator { lhs, rhs ->
                (lhs.x * 1000 - rhs.x * 1000).toInt()
            }
        }
    }

    companion object {
        private const val _PADDING_BETWEEN_DOT_CIRCLE_WITH_TRIANG = 15
        private const val _HEIGHT_TRIANG = 15
        private const val _WIDTH_TRIANG = 30
        private const val _RADIUS = 20f
        private const val _BUFFER_PADDING_LEFT = 15  // tu uoc luong con so cho phu hop
        private const val _RATIO_PADDING_TOP = 9f / 375f // baseline width. 375 is standard
        private const val _RATIO_PADDING_BOTTOM = 30f / 375f // baseline width. 375 is standard
        private const val _BUFFER_PADDING_DETECT_POINT = 5f // tu uoc luong con so cho phu hop

        // if neither min and max is equals 0, value default max is 5 and min is -5
        private const val _VALUE_DEFAULT_IF_VALUE_EQUAL_0 = 5
        private const val _VALUE_TDS_DEFAULT_IF_VALUE_EQUAL_0 = 400
        private const val _PADDING_TEXT_RIGHT_BUFFER = 5f
    }

}

@Keep
enum class ChartType {
    POWER,
    TEMPERATURE,
    WATER_IN,
    WATER_OUT
}

fun dateFromString(dateString: String): Date {
    val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.parse(dateString)
}

fun Date.fromStringHour(): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(this)
}

fun Date.getHourReport(): String {
    val sdf = SimpleDateFormat("HH:00", Locale.getDefault())
    return sdf.format(this)
}

fun Date.toSString(): String {
    val sdf = SimpleDateFormat("dd/MM/yy", Locale.getDefault())
    return sdf.format(this)
}

fun Date.toStringNotHaveYear(): String {
    val sdf = SimpleDateFormat("dd/MM", Locale.getDefault())
    return sdf.format(this)
}
