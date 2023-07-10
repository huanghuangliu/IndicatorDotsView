package com.example.indicatordotsviewdemo

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import java.util.*

/**
 * 小圆点
 * @author lf
 * @date 2022/5/5
 */
class IndicatorDotsView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    LinearLayout(context, attrs) {
    private var selectedRes: Int = 0
    private var unselectedRes: Int = 0
    private var dotViews: MutableList<ImageView>? = null
    private var dotHeight = 6
    private var dotCount = 0

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : this(context, null) {
        dotHeight = dotHeight.dp2px
        gravity = Gravity.CENTER

        val typedArray = context.obtainStyledAttributes(attrs,R.styleable.IndicatorDotsView,0,0)
        dotCount = typedArray.getInteger(R.styleable.IndicatorDotsView_dot_count,4)
        dotHeight = typedArray.getDimensionPixelSize(R.styleable.IndicatorDotsView_dot_height,6)
        selectedRes = typedArray.getResourceId(R.styleable.IndicatorDotsView_dot_selected_res,R.drawable.dot_indicator_selected)
        unselectedRes = typedArray.getResourceId(R.styleable.IndicatorDotsView_dot_selected_res,R.drawable.dot_indicator_unselected)

        init()
    }

    /**
     * 初始化
     */
    private fun init() {
        this.removeAllViews()
        dotViews = ArrayList()
        for (i in 0 until dotCount) {
            val rl = RelativeLayout(context)
            val params = LayoutParams(dotHeight, dotHeight)
            params.setMargins(4.dp2px, 0, 4.dp2px, 0)
            val layoutParams = RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
            val imageView = ImageView(context)
            if (i == 0) {
                imageView.setImageResource(selectedRes)
                rl.addView(imageView, layoutParams)
            } else {
                imageView.setImageResource(unselectedRes)
                rl.addView(imageView, layoutParams)
            }
            this.addView(rl, params)
            dotViews?.add(imageView)
        }
    }

    /**
     * 更改圆点数量
     */
    fun updateIndicator(count: Int) {
        if (dotViews == null) {
            return
        }
        for (i in dotViews!!.indices) {
            if (i >= count) {
                dotViews!![i].visibility = GONE
                (dotViews!![i].parent as View).visibility = GONE
            } else {
                dotViews!![i].visibility = VISIBLE
                (dotViews!![i].parent as View).visibility = VISIBLE
            }
        }
        if (count > dotViews!!.size) {
            val diff = count - dotViews!!.size
            for (i in 0 until diff) {
                val rl = RelativeLayout(context)
                val params = LayoutParams(dotHeight, dotHeight)
                val layoutParams = RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT
                )
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT)
                val imageView: ImageView = ImageView(context)
                imageView.setImageResource(unselectedRes)
                rl.addView(imageView, layoutParams)
                rl.visibility = GONE
                imageView.visibility = GONE
                this.addView(rl, params)
                dotViews!!.add(imageView)
            }
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    /**
     * 选中
     */
    fun selectTo(position: Int) {
        if (dotViews.isNullOrEmpty()){
            return
        }
        for (iv in dotViews!!) {
            iv.setImageResource(unselectedRes)
        }
        dotViews!![position].setImageResource(selectedRes)
    }

    /**
     * 选中
     */
    fun selectTo(startPosition: Int, targetPostion: Int) {
        if (dotViews.isNullOrEmpty()){
            return
        }
        val startView = dotViews!![startPosition]
        val targetView = dotViews!![targetPostion]
        startView.setImageResource(unselectedRes)
        targetView.setImageResource(selectedRes)
    }
}