package com.haiqi.base.widget.shape

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.haiqi.base.R
import com.haiqi.base.utils.getColor

/**
 * Created by zhutao on 2017/8/4.
 * 用来替代shape 的view
 * shape 默认设置圆角
 */
class ShapeView(ctx: Context,attrs: AttributeSet?): View(ctx,attrs){

    constructor(ctx: Context): this(ctx,null)

     var radius = 0f
     var soild = 0;
    private var strokeWidth = 0f
    private var strokeColor = 0


     var sv_top_left = false;
     var sv_top_right = false;
     var sv_bottom_left = false;
     var sv_bottom_right = false;

    var paint: Paint? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.shapeview)
        radius = typedArray.getDimension(R.styleable.shapeview_sv_radius,0f)
        soild = typedArray.getColor(R.styleable.shapeview_sv_soild_color, getColor(R.color.transparent))
        strokeColor = typedArray.getColor(R.styleable.shapeview_sv_stoke_color,R.color.transparent)
        strokeWidth = typedArray.getDimension(R.styleable.shapeview_sv_stoke_width,0f)
        sv_top_left = typedArray.getBoolean(R.styleable.shapeview_sv_top_left,false)
        sv_top_right = typedArray.getBoolean(R.styleable.shapeview_sv_top_right,false)
        sv_bottom_left = typedArray.getBoolean(R.styleable.shapeview_sv_bottom_left,false)
        sv_bottom_right = typedArray.getBoolean(R.styleable.shapeview_sv_bottom_right,false)
        typedArray.recycle()

        paint = Paint()
        initPaint()
    }

    /*   <attr name="sv_radius" format="dimension"/>
       <attr name="sv_stoke_color" format="reference|color"/>
       <attr name="sv_stoke_width" format="dimension"/>
       <attr name="sv_soild_color" format="reference|color"/>
       <attr name="sv_top_left" format="boolean"/>
       <attr name="sv_top_right" format="boolean"/>
       <attr name="sv_bottom_left" format="boolean"/>
       <attr name="sv_bottom_right" format="boolean"/>*/




    var topleftX = 0f; //x 轴的 半径
    var topleftY = 0f; // y 轴的 半径

    var toprightX = 0f;
    var toprightY = 0f;

    var bottomleftX = 0f;
    var bottomleftY = 0f;

    var bottomrightX = 0f;
    var bottomrightY = 0f;



    override fun onDraw(canvas: Canvas?) {
        canvas?.let{
            drawStrokeLine(it)
            drawSolid(it)
        }
        super.onDraw(canvas)
    }

    val solidPath by lazy{
        Path()
    }

    val strokeWidthPath by lazy{
        Path()
    }
    var solidRectF: RectF? = null
    var strokeLineRectF: RectF? = null


    val corners by lazy{
        FloatArray(8)
    }

    private fun drawStrokeLine(canvas: Canvas) {
        if (strokeWidth > 0) {
            strokeWidthPath.reset()
            if (strokeLineRectF == null) {
                strokeLineRectF = RectF()
            } else {
                strokeLineRectF?.setEmpty()
            }
            strokeLineRectF?.set(strokeWidth / 2, strokeWidth / 2, width - strokeWidth / 2,
                    height - strokeWidth / 2)
            getRadius(radius)
            strokeWidthPath.addRoundRect(strokeLineRectF, corners, Path.Direction.CW)
            initPaint()
            paint?.style = Paint.Style.STROKE
            paint?.color = strokeColor
            paint?.strokeWidth = strokeWidth
            canvas.drawPath(strokeWidthPath, paint)
        }
    }


    private fun drawSolid(canvas: Canvas) {
        solidPath.reset()
        if (solidRectF == null) {
            solidRectF = RectF()
        } else {
            solidRectF?.setEmpty()
        }
        solidRectF?.set(strokeWidth, strokeWidth, width - strokeWidth, height - strokeWidth)
        getRadius(radius - strokeWidth / 2)
        solidPath.addRoundRect(solidRectF, corners, Path.Direction.CW)

        initPaint()
        paint?.style = Paint.Style.FILL
        paint?.color = soild
        canvas.drawPath(solidPath, paint)
    }

    private fun initPaint() {
        paint?.reset()
        paint?.isAntiAlias = true
        paint?.isDither = true
    }


    /*   private var sv_top_left = false;
       private var sv_top_right = false;
       private var sv_bottom_left = false;
       private var sv_bottom_right = false;*/

    fun getRadius(radius: Float){
        val can = sv_top_left || sv_top_right || sv_bottom_left
                ||sv_bottom_right
        if(can) {
            if (sv_top_left) {
                topleftX = radius
                topleftY = radius
            }
            if (sv_top_right) {
                toprightX = radius
                toprightY = radius
            }
            if (sv_bottom_left) {
                bottomleftX = radius
                bottomleftY = radius
            }
            if (sv_bottom_right) {
                bottomrightX = radius
                bottomrightY = radius
            }
        }
        else{
            topleftX = radius; //x 轴的 半径
            topleftY = radius; // y 轴的 半径
            toprightX = radius;
            toprightY = radius;
            bottomleftX = radius;
            bottomleftY = radius;
            bottomrightX = radius;
            bottomrightY = radius;
        }

        corners[0] = topleftX
        corners[1] = topleftY
        corners[2] = toprightX
        corners[3] = toprightY
        corners[4] = bottomrightX
        corners[5] = bottomrightY
        corners[6] = bottomleftX
        corners[7] = bottomleftY
    }
}