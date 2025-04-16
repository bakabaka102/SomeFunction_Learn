package hn.single.server.ui.explosion

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import hn.single.server.R
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin
import kotlin.random.Random
import androidx.core.graphics.toColorInt

class ParticleExplosionView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val particles = mutableListOf<Particle>()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var isExploded = false
    private var shockwave: Shockwave? = null

    private val particleCount = 100
    private val handler = Handler(Looper.getMainLooper())

    // 👇 Thêm: hình ảnh bom
    //java.lang.NullPointerException: decodeResource(...) must not be null do image is vector .xml
    //private val bombBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_skype)
    private val bombDrawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_bomb)
    private val bombSize = 120 // pixels (tuỳ chỉnh theo kích thước bom)
    //private val bombRect = Rect()

    val baseColor = Color.rgb(Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))

    private val updateRunnable = object : Runnable {
        override fun run() {
            updateParticles()
            invalidate()
            if (particles.any { it.alpha > 0 }) {
                handler.postDelayed(this, 16)
            }
            shockwave?.update()
            if (shockwave?.isFinished() == true) shockwave = null
        }
    }

    fun explode(centerX: Float, centerY: Float) {
        if (isExploded) return
        isExploded = true
        particles.clear()
        shockwave = Shockwave(
            centerX = centerX,
            centerY = centerY,
            maxRadius = max(width, height).toFloat()
        )
        (0 until particleCount).forEach { i ->
            val angle = Random.nextFloat() * 360f
            val speed = Random.nextFloat() * 15f + 5f
            val vx = cos(Math.toRadians(angle.toDouble())).toFloat() * speed
            val vy = sin(Math.toRadians(angle.toDouble())).toFloat() * speed

            particles.add(Particle(centerX, centerY, vx, vy))
        }

        handler.post(updateRunnable)
    }

    private fun updateParticles() {
        particles.forEach { it.update() }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f

        if (!isExploded) {
            // 👇 Thay vì vẽ vòng tròn, ta vẽ hình bom
            val left = (centerX - bombSize / 2).toInt()
            val top = (centerY - bombSize / 2).toInt()
            val right = left + bombSize
            val bottom = top + bombSize
            /*bombRect.set(left, top, right, bottom)
            canvas.drawBitmap(bombBitmap, null, bombRect, paint)*/
            bombDrawable?.setBounds(left, top, right, bottom)
            bombDrawable?.draw(canvas)
        }

        particles.forEach {
            val finalColor = (baseColor and 0x00FFFFFF) or (it.alpha shl 24)
            paint.color = finalColor
            paint.color = ColorUtils.setAlphaComponent(Color.RED, it.alpha)
            //paint.color = (Color.GRAY and 0x00FFFFFF) or (it.alpha shl 24)
            //paint.color = Color.RED
            canvas.drawCircle(it.x, it.y, it.size, paint)
        }

        shockwave?.let { sw ->
            paint.apply {
                //paint.color = Color.parseColor("#66CCFF") // xanh dương nhạt
                paint.color = "#FFCC00".toColorInt() // cam sáng
                //color = Color.WHITE
                alpha = sw.alpha
                style = Paint.Style.STROKE
                strokeWidth = sw.strokeWidth
            }
            canvas.drawCircle(sw.centerX, sw.centerY, sw.radius, paint)
        }
        paint.style = Paint.Style.FILL
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when {
            event.action == MotionEvent.ACTION_DOWN && !isExploded -> {
                explode(width / 2f, height / 2f)
                return true
            }

            event.action == MotionEvent.ACTION_UP -> {
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
}

data class Particle(
    var x: Float,
    var y: Float,
    val vx: Float,
    val vy: Float,
    var alpha: Int = 255,
    val size: Float = Random.nextFloat() * 8f + 2f
) {
    fun update() {
        x += vx
        y += vy
        alpha = (alpha - 6).coerceAtLeast(0)
    }
}

data class Shockwave(
    var radius: Float = 0f,
    var alpha: Int = 255,
    val maxRadius: Float,
    val centerX: Float,
    val centerY: Float,
    val strokeWidth: Float = 8f
) {
    fun update() {
        radius += 12f
        alpha = (alpha - 8).coerceAtLeast(0)
    }

    fun isFinished(): Boolean = alpha <= 0
}

class ParticleExplosionView2 @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : View(context, attrs) {

    private val particles = mutableListOf<Particle>()
    private val smokes = mutableListOf<SmokeParticle>()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var isExploded = false
    private var flashAlpha = 0

    private val particleCount = 100
    private val handler = Handler(Looper.getMainLooper())

    private val bombDrawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.ic_bomb)
    private val bombSize = 120 // pixels

    private val updateRunnable = object : Runnable {
        override fun run() {
            updateParticles()
            invalidate()
            if (particles.any { it.alpha > 0 } || smokes.any { it.alpha > 0 } || flashAlpha > 0) {
                handler.postDelayed(this, 16)
            }
        }
    }

    fun explode(centerX: Float, centerY: Float) {
        if (isExploded) return
        isExploded = true
        particles.clear()
        smokes.clear()

        repeat(particleCount) {
            val angle = Random.nextFloat() * 360f
            val speed = Random.nextFloat() * 15f + 5f
            val vx = cos(Math.toRadians(angle.toDouble())).toFloat() * speed
            val vy = sin(Math.toRadians(angle.toDouble())).toFloat() * speed

            particles.add(Particle(centerX, centerY, vx, vy))
        }

        repeat(10) {
            smokes.add(
                SmokeParticle(
                    x = centerX + Random.nextFloat() * 40f - 20f,
                    y = centerY + Random.nextFloat() * 10f
                )
            )
        }

        flashAlpha = 180
        handler.post(updateRunnable)
    }

    private fun updateParticles() {
        particles.forEach { it.update() }
        smokes.forEach { it.update() }
        smokes.removeAll { it.isFaded() }
        if (flashAlpha > 0) flashAlpha -= 40
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f

        if (!isExploded) {
            val left = (centerX - bombSize / 2).toInt()
            val top = (centerY - bombSize / 2).toInt()
            val right = left + bombSize
            val bottom = top + bombSize
            bombDrawable?.setBounds(left, top, right, bottom)
            bombDrawable?.draw(canvas)
        }

        smokes.forEach {
            paint.apply {
                color = Color.GRAY
                alpha = it.alpha
                style = Paint.Style.FILL
            }
            canvas.drawCircle(it.x, it.y, it.size, paint)
        }

        particles.forEach {
            val colorWithAlpha = (Color.RED and 0x00FFFFFF) or (it.alpha shl 24)
            paint.color = colorWithAlpha
            paint.style = Paint.Style.FILL
            canvas.drawCircle(it.x, it.y, it.size, paint)
        }

        if (flashAlpha > 0) {
            paint.color = Color.WHITE
            paint.alpha = flashAlpha
            paint.style = Paint.Style.FILL
            canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN && !isExploded) {
            explode(width / 2f, height / 2f)
            return true
        }
        return super.onTouchEvent(event)
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    data class Particle(
        var x: Float,
        var y: Float,
        val vx: Float,
        val vy: Float,
        var alpha: Int = 255,
        val size: Float = Random.nextFloat() * 8f + 2f
    ) {
        fun update() {
            x += vx
            y += vy
            alpha = (alpha - 6).coerceAtLeast(0)
        }
    }

    data class SmokeParticle(
        var x: Float,
        var y: Float,
        var vy: Float = -2f,
        var alpha: Int = 200,
        var size: Float = Random.nextFloat() * 30f + 20f
    ) {
        fun update() {
            y += vy
            alpha = (alpha - 2).coerceAtLeast(0)
        }

        fun isFaded(): Boolean = alpha <= 0
    }
}



