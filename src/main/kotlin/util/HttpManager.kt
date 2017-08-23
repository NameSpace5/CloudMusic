package util

import com.squareup.okhttp.*
import java.net.URLEncoder
import java.util.concurrent.TimeUnit

class OkHttpManager private constructor(){
    private var okClient:OkHttpClient = OkHttpClient()
    init {
        okClient.setConnectTimeout(10,TimeUnit.SECONDS)
        okClient.setReadTimeout(10,TimeUnit.SECONDS)
        okClient.setWriteTimeout(10,TimeUnit.SECONDS)
    }
    companion object {
        val MEDIA_TYPE_URLENCODED = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8")!!
        val MEDIA_TYPE_JSON = MediaType.parse("text/x-markdown; charset=utf-8")!!

        //单例模式静态内部类方式实例化OkHttpClient
        fun getInstance() = OkHttpHolder.instance
    }
    //静态内部类
    private object OkHttpHolder{
        val instance = OkHttpManager()
    }

    /**
     * 同步get封装
     */
    fun requestGetSyn(baseUrl:String,actionUrl: String,paramsMap: MutableMap<String, String>):String{
        val builder = StringBuilder()
        var sign = 0
        for ((k,v) in paramsMap){
            if (sign > 0) builder.append("&")
            //URLEncoder编码
            builder.append(String.format("%s=%s",k,URLEncoder.encode(v,"utf-8")))
            sign++
        }
        //完全的请求地址
        val requestUrl = String.format("%s/%s?%s",baseUrl,actionUrl,builder.toString())
        val request = Request.Builder()
                .url(requestUrl)
                .build()
        val response = okClient.newCall(request).execute()
        return response.body().string()
    }

    /**
     * 同步post封装
     */
    fun requestPostSyn(baseUrl: String,actionUrl: String,paramsMap: MutableMap<String, String>):String{
        val builder = StringBuilder()
        var sign = 0
        for ((k,v) in paramsMap){
            if (sign > 0) builder.append("&")
            builder.append(String.format("%s=%s", k, URLEncoder.encode(v, "utf-8")))
            sign++
        }
        //完全的请求地址
        val requestUrl = String.format("%s/%s",baseUrl,actionUrl)
        val requestBody = RequestBody.create(MEDIA_TYPE_URLENCODED, builder.toString())
        val request = Request.Builder()
                .url(requestUrl)
                .post(requestBody)
                .build()
        val response = okClient.newCall(request).execute()
        var result = "出错啦！"
        if (response.isSuccessful) result = response.body().string()
        return result
    }

    /**
     * 同步post表单封装
     */
    fun requestPostSynWithForm(baseUrl: String,actionUrl: String,paramsMap: MutableMap<String, String>):String{
        val formBuilder = FormEncodingBuilder()
        for ((k,v) in paramsMap){
            formBuilder.add(k,v)
        }
        val formBody = formBuilder.build()
        val requestUrl = String.format("%s/%s", baseUrl, actionUrl)
        val request = Request.Builder()
                .url(requestUrl)
                .post(formBody)
                .build()
        val response = okClient.newCall(request).execute()
        var result = "出错啦！"
        if (response.isSuccessful) result = response.body().string()
        return result
    }
}