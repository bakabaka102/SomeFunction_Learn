package com.app.func.view.animations_custom

import android.annotation.SuppressLint
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView

@SuppressLint("AppCompatCustomView")
class ExplosionView(context: Context) : ImageView(context) {

    fun setLocation(top: Int, left: Int) {
        setFrame(left, top, left + 40, top + 40)
    }

}

class LayoutListener : View.OnTouchListener{

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        //firstly， u have to stop the animation,if the animation
        //is starting ,u can not start it again!
        //exv1.setVisibility(View.INVISIBLE)
        //exa1.stop()
        val x = event?.x
        val y = event?.y
        //exv1.setLocation(y.toInt() - 20, x.toInt() - 20)
        //exv1.setVisibility(View.VISIBLE)
        //exa1.start()
        return false

    }

}