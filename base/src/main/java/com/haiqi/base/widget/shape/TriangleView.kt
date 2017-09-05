package com.haiqi.base.widget.shape

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Point
import android.util.AttributeSet
import android.view.View
import com.haiqi.base.R
import com.haiqi.base.utils.dip2px

/**
 * Created by zhutao on 2017/8/14.
 * 三角形
 *
 */

/*    <attr name="tri_stroke_color" format="reference|color"/>
    <attr name="tri_stroke_width" format="dimension"/>
    <attr name="tri_soild" format="reference|color"/>
    <attr name="tri_direction" format="enum">
    <enum name="left" value="0"/>
    <enum name="top" value="1"/>
    <enum name="right" value="2"/>
    <enum name="bottom" value="3"/>
    </attr>*/
class TriangleView(ctx:Context,attrs: AttributeSet?): View(ctx,attrs){

    constructor(ctx: Context): this(ctx,null)

    private var strokeColor = 0;
    private var strokeWidth = 0f;
    private var soild = 0;
    private var direction = 0

    private final val STROKE_COLOR = 0x00000000
    private final val SOILD = 0x00000000
    private final val STROKE_WIDHT = 0f
    private final val DIRECTION = 1

    private val mStokePaint by lazy(LazyThreadSafetyMode.NONE){
        Paint()
    }

    private val mSoildPaint by lazy(LazyThreadSafetyMode.NONE){
        Paint()
    }

    init {
        val typeArray = context.obtainStyledAttributes(attrs,
                R.styleable.triangleView)
        strokeColor = typeArray.getColor(R.styleable.triangleView_tri_stroke_color,
                STROKE_COLOR)
        strokeWidth = typeArray.getDimension(R.styleable.triangleView_tri_stroke_width,
                dip2px(STROKE_WIDHT))
        soild = typeArray.getColor(R.styleable.triangleView_tri_soild,
                SOILD)
        direction = typeArray.getInteger(R.styleable.triangleView_tri_direction,
                DIRECTION)
        typeArray.recycle()

        if(strokeWidth != 0f) {
            mStokePaint.color = strokeColor
            mStokePaint.strokeWidth = strokeWidth
            mStokePaint.isAntiAlias = true
            mStokePaint.isDither = true
            mStokePaint.style = Paint.Style.STROKE
        }

        mSoildPaint.color = soild
        mSoildPaint.isAntiAlias = true
        mSoildPaint.isDither = true
        mSoildPaint.style = Paint.Style.FILL
    }



    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val points = when(direction){
            0->{//left

                listOf(
                        Point(0,height/2),
                        Point(width,0),
                        Point(width,height))
            }
            1->{//top
                listOf(
                        Point(width/2,0),
                        Point(0,height),
                        Point(width,height))
            }
            3->{//right
                listOf(
                        Point(width,height/2),
                        Point(0,0),
                        Point(0,height))
            }
            else->{//bottom
                listOf(
                        Point(width/2,height/2),
                        Point(0,0),
                        Point(width,0))
            }
        }
        val path = Path()
        path.moveTo(points[0].x.toFloat(),points[0].y.toFloat())
        path.lineTo(points[1].x.toFloat(),points[1].y.toFloat())
        path.lineTo(points[2].x.toFloat(),points[2].y.toFloat())
        canvas?.let{
            it.drawPath(path, mSoildPaint)
            if(strokeWidth != 0f) {
                it.drawLine(points[0].x.toFloat(),points[0].y.toFloat(),
                        points[1].x.toFloat(), points[1].y.toFloat(), mStokePaint)
                it.drawLine(points[0].x.toFloat(),points[0].y.toFloat(),
                        points[2].x.toFloat(), points[2].y.toFloat(), mStokePaint)
            }
        }
        path.close()
    }

}