package com.haiqi.base.widget.timer

/**
 * Created by zhutao on 2017/9/7.
 * 定时器
 */
class CountDownTimer(millisInFuture: Long):
        android.os.CountDownTimer(millisInFuture,1000){

    internal val dayMills = (24 * 60 * 60 * 1000).toLong()
    internal val hourMills = (60 * 60 * 1000).toLong()
    internal val minuteMills = (60 * 1000).toLong()
    internal val secondMills: Long = 1000

    override fun onFinish() {
        onFinished?.let {
            it()
        }
    }

    override fun onTick(mills: Long) {
        var newmills = mills
        val days = newmills / dayMills
        newmills %= dayMills
        val hours = newmills / hourMills
        newmills %= hourMills
        val minutes = newmills / minuteMills
        newmills %= minuteMills
        val seconds = newmills / secondMills

        val daysStr = timeStrFormat(days.toString())
        val hoursStr = timeStrFormat(hours.toString())
        val minutesStr = timeStrFormat(minutes.toString())
        val secondStr = timeStrFormat(seconds.toString())

        onGetTime?.let{
            it(daysStr,hoursStr,minutesStr,secondStr)
        }
    }


    private var onGetTime: ((String,String,String,String) -> Unit)? = null

    fun setOnCountDownTimer(onGetTime: ((String,String,String,String) -> Unit)
    ,onFinished: (() -> Unit)? = null){
        this.onGetTime = onGetTime
        this.onFinished = onFinished
    }

    private var onFinished: (() -> Unit)? = null


    private fun timeStrFormat(timeStr: String): String {
        var timeStr = timeStr
        if(timeStr.length == 1) {
            timeStr = "0" + timeStr
        }
        return timeStr
    }

}