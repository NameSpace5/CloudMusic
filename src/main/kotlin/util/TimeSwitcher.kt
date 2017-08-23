package util

import java.text.SimpleDateFormat
import java.util.*

/**
 * 返回指定格式时间 如"yyyy-MM-dd"
 */
fun timeStamp2Date(timeString:String,format:String): String {
    val timeStamp = timeString.toLong()
    return SimpleDateFormat(format).format(Date(timeStamp))
}

/**
 * 返回默认yyyy-MM-dd HH:mm:ss格式时间
 */
fun timeStamp2Date(timeString: String):String{
    val timeStamp = timeString.toLong()
    val format = "yyyy-MM-dd HH:mm:ss"
    return SimpleDateFormat(format).format(Date(timeStamp))
}

/**
 * 返回指定时间时间戳 pattern为指定格式 如"yyyy-MM-dd HH:mm:ss"
 */
fun date2TimeStamp(date:String,pattern:String):Long{
    val simpleDateFormat = SimpleDateFormat(pattern)
    val res = simpleDateFormat.parse(date)
    return (simpleDateFormat.parse(simpleDateFormat.format(res))).time /1000
}

/**
 * 返回当前时间时间戳
 */
fun date2TimeStamp():Long{
    return Date().time
}