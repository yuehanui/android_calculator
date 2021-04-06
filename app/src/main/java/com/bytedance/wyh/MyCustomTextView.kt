package com.bytedance.wyh

import android.animation.FloatEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.icu.util.Measure
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView

class MyCustomTextView(context : Context, attributeSet: AttributeSet) : AppCompatTextView(context, attributeSet) {


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = getMySize(100, widthMeasureSpec)
        var height = getMySize(100, heightMeasureSpec)

        if (width > height) width = height
        else if (height > width) height = width

        setMeasuredDimension(width, height)
    }



    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val action = event?.action
        if (action == MotionEvent.ACTION_DOWN) {
            ObjectAnimator.ofFloat(this, "scaleX", 0.9f).apply {
                duration = 10
                start()
            }
            ObjectAnimator.ofFloat(this, "scaleY", 0.9f).apply {
                duration = 10
                start()
            }
        } else if (action == MotionEvent.ACTION_UP) {
            ObjectAnimator.ofFloat(this, "scaleX", 1f).apply {
                duration = 10
                start()
            }
            ObjectAnimator.ofFloat(this, "scaleY", 1f).apply {
                duration = 10
                start()
            }
        }
        return super.onTouchEvent(event)
    }



    // helper function for onMeasure
    private fun getMySize(defaultSize : Int, measureSpec : Int) : Int {
        var mySize = defaultSize

        var mode = MeasureSpec.getMode(measureSpec)
        var size = MeasureSpec.getSize(measureSpec)

        when (mode) {
            MeasureSpec.EXACTLY -> mySize = size

            MeasureSpec.AT_MOST -> mySize = size

            MeasureSpec.UNSPECIFIED -> mySize = defaultSize
        }
        return mySize
    }

}