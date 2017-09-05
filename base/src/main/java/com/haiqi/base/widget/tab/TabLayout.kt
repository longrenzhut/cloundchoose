package com.haiqi.base.widget.tab

import android.content.Context
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.haiqi.base.R
import com.haiqi.base.common.adapter.BaseAdapter
import com.haiqi.base.common.adapter.BaseViewHolder
import com.haiqi.base.common.adapter.viewpager.BaseVpAdapter
import com.haiqi.base.common.adapter.viewpager.FraModel
import com.haiqi.base.utils.anim.AnimHelper
import com.haiqi.base.utils.dip2px
import kotlin.properties.Delegates

/**
 * Created by zhutao on 2017/8/14.
 *  通用的tab
 */

/*<attr name="tab_line_stroke" format="dimension"/>
<attr name="tab_line_color" format="reference|color"/>
<attr name="tab_line_width" format="dimension"/>
<attr name="tab_color" format="reference|color"/>
<attr name="tab_selected_color" format="reference|color"/>
<attr name="tab_style" format="boolean"/>
<attr name="tab_padding" format="dimension"/>*/
class TabLayout(ctx: Context,attrs: AttributeSet?): LinearLayout(ctx,attrs){

    constructor(ctx: Context): this(ctx,null)

    private var color = 0
    var selectedcolor = 0;
    private var lineStroke = 0
    private var lineWidth = 0;
    private var lineColor = 0;

    private var DEFALUT_COLOR = 0xf0f0f0
    private var tabStyle = false;
    private var tabTvSize = 12
    private var tabPadding = 0


    private val mRvTab by lazy(LazyThreadSafetyMode.NONE){
        RecyclerView(context)
    }
    var hasline = false;

    private val line by lazy(LazyThreadSafetyMode.NONE){
        View(context)
    }

    private val layout by lazy(LazyThreadSafetyMode.NONE){
        GridLayoutManager(context,1)
    }

    private var tabcount = 0;

    private val mAdapter by lazy(LazyThreadSafetyMode.NONE){
        TabAdapter<String>(tabPadding,tabTvSize,color,selectedcolor,tabStyle)
    }

    private val helper by lazy(LazyThreadSafetyMode.NONE){
        AnimHelper()
    }

    init {
        val typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.tabmylayout)

        color = typedArray.getColor(R.styleable.tabmylayout_tab_color,DEFALUT_COLOR)
        selectedcolor = typedArray.getColor(R.styleable.tabmylayout_tab_selected_color,DEFALUT_COLOR)
        lineColor = typedArray.getColor(R.styleable.tabmylayout_tab_line_color,DEFALUT_COLOR)
        lineStroke = typedArray.getDimensionPixelOffset(R.styleable.tabmylayout_tab_line_stroke
                ,0)
        lineWidth = typedArray.getDimensionPixelOffset(R.styleable.tabmylayout_tab_line_width
                ,0)
        tabTvSize = typedArray.getDimensionPixelOffset(R.styleable.tabmylayout_tab_textsize
                ,dip2px(12))
        tabStyle = typedArray.getBoolean(R.styleable.tabmylayout_tab_style,true)
        tabPadding = typedArray.getDimensionPixelOffset(R.styleable.tabmylayout_tab_padding,0)
        typedArray.recycle()

        orientation = VERTICAL
        gravity = Gravity.CENTER_VERTICAL

        val lp = LinearLayout.LayoutParams(-1,-2)
        mRvTab.layoutParams = lp
        addView(mRvTab)

        mRvTab.layoutManager = layout
        mRvTab.adapter = mAdapter

        addView(line)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        drawline(width,height)
    }

    private  fun drawline(w: Int,h: Int){
        if(tabcount == 0 || lineStroke == 0 || hasline)
            return
        val lp = LinearLayout.LayoutParams(-1,-1)
        lp.width = if(lineWidth == 0) w/tabcount else lineWidth
        lp.height = lineStroke

        val margin = if(lineWidth == 0){
            0
        }
        else{
            (w/tabcount - lineWidth)/2
        }
        lp.leftMargin = margin
        line.layoutParams = lp
        line.setBackgroundColor(lineColor)
        hasline = true
    }

    fun setViewPage(vp: ViewPager,onPageSelected: ((position: Int) -> Unit)? = null){
        vp.adapter?.let {
            val mVpAdapter = it as BaseVpAdapter<String>
            tabcount = it.count
            layout.spanCount = tabcount;

            vp.adapter.count
            mAdapter.clear()
            mAdapter.notifyItems(mVpAdapter.list)

            vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                override fun onPageScrollStateChanged(state: Int) {
                }

                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    if (hasline) {
                        val off = (width / tabcount) * (position + positionOffset)
                        helper.translationX(line, 0, off)
                    }
                }

                override fun onPageSelected(position: Int) {
                    mAdapter.index = position
                    onPageSelected?.let {
                        it(position)
                    }
                }

            })
            mAdapter.setOnItemClickListener(object : BaseAdapter.OnItemClickListener<FraModel<String>> {
                override fun onItemClick(itemView: View, pos: Int, model: FraModel<String>) {
                    vp.currentItem = pos
                }
            })
        }
    }


    class TabAdapter<T>(val padding: Int,val tabTvSize: Int,val color: Int,val selectedcolor: Int,val tabStyle: Boolean)
    : BaseAdapter<FraModel<T>>(){

        var index by Delegates.observable<Int>(0) {
            d, old, new ->
            notifyDataSetChanged();
        }

        override fun inflaterItemLayout(viewType: Int): Int {
            return 0
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder? {
            val tv = TextView(parent?.context)
            val lp = ViewGroup.LayoutParams(-1,-2)
            tv.layoutParams = lp
            tv.gravity = Gravity.CENTER
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX,tabTvSize.toFloat())
            if(tabStyle){
                val outValue = TypedValue()
                parent!!.context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground,
                        outValue, true)
                tv.setBackgroundResource(outValue.resourceId)
            }
            if(padding != 0)
                tv.setPadding(0,0,0,padding)

            return BaseViewHolder(tv)
        }

        override fun bindData(holder: BaseViewHolder, position: Int, model: FraModel<T>) {
            val mTvName = holder.itemView as TextView
            if(index == position)
                mTvName.setTextColor(selectedcolor)
            else{
                mTvName.setTextColor(color)
            }
            mTvName.text = model.name
        }

        override fun onItemClickListener(itemView: View, pos: Int, model: FraModel<T>) {

        }

    }


}