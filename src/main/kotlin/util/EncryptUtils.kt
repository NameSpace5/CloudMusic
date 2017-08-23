package util

import com.google.gson.JsonObject
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec
import java.math.BigInteger
import java.nio.charset.Charset
import java.security.MessageDigest
import org.apache.commons.codec.binary.Base64

val modulus = "00e0b509f6259df8642dbc35662901477df22677ec152b5ff68ace615bb7b725152b3ab17a876ae" +
        "a8a5aa76d2e417629ec4ee341f56135fccf695280104e0312ecbda92557c93870114af6c9d05c4f7f0c3685" +
        "b7a46bee255932575cce10b424d813cfe4875d3e82047b97ddef52741d546b8e289dc6935b3ece0462db0a22b8e7"
val nonce = "0CoJUm6Qyw8W8jud"
val pubKey = "010001"

/**
 * 随机生成数
 */
fun createSecretKey(size:Int):String{
    val a = 10.0
    val b = size-1.toDouble()
    return ((Math.random()*9+1)*Math.pow(a,b)).toLong().toString()
}

/**
 * AES加密
 */
fun aesEncrypt(text:String,secKey:String):String{
    val sKeySpec = SecretKeySpec(secKey.toByteArray(Charset.forName("utf-8")),"AES")
    val iv= IvParameterSpec("0102030405060708".toByteArray(Charset.forName("utf-8")))

    val cipher:Cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
    cipher.init(Cipher.ENCRYPT_MODE,sKeySpec,iv)

    val encrypted = cipher.doFinal(text.toByteArray())
    return Base64.encodeBase64String(encrypted)
}


/**
 * RSA加密
 */
fun rsaEncrypt(text:String,pubKey:String,modulus:String):String{
    val res = StringBuilder(text).reverse().toString()
    val a = BigInteger(stringToHex(res),16)
    val b = BigInteger(pubKey,16)
    val c = BigInteger(modulus,16)
    val result = a.modPow(b,c)
    return addSign2String("0",result.toString(16),256)
}

/**
 * MD5加密
 */
fun md5Diggest(text: String):String{
    val bytes = text.toByteArray()
    val mDigest = MessageDigest.getInstance("MD5")
    mDigest.update(bytes)
    val result = mDigest.digest()
    //bytes-->hex-->string
    val builder = StringBuilder()
    for (num in 0 until result.size){
        val b = result[num]
        var tmp: String = Integer.toHexString(0xFF and b.toInt())
        if (tmp.length == 1) tmp = "0" +tmp
        builder.append(tmp)
    }
    return builder.toString()
}

/**
 * 请求数据封装加密
 */
fun encrypted_request(userType:Int,username:String,password:String):MutableMap<String,String>{
    val pwd = md5Diggest(password)
    val text = JsonObject()
    when(userType){
        0 -> {text.addProperty("phone",username);text.addProperty("password",pwd)}
        1 -> {text.addProperty("username",username);text.addProperty("password",pwd)}
        else ->{ println("输入有误")}
    }
    val secKey = createSecretKey(16)

    val encText:String = aesEncrypt(aesEncrypt(text.toString(),nonce),secKey)

    val encSecKey = rsaEncrypt(secKey, pubKey,modulus)
    val map:MutableMap<String,String> = mutableMapOf()
    map["params"] = encText
    map["encSecKey"] = encSecKey
    return map
}

/**
 * 评论数据封装加密
 */
fun encrypted_comments(musicId:String,page:Int,limit:Int):MutableMap<String,String>{
    val text = JsonObject()
    text.addProperty("sid",musicId)
    text.addProperty("limit",limit)

    when(page){
        0 -> {
            text.addProperty("offset",0)
            text.addProperty("total",true)
        }
        else -> {
            text.addProperty("offset",(page - 1) * 20)
            text.addProperty("total",false)
        }
    }
    val secKey = createSecretKey(16)
    val encText:String = aesEncrypt(aesEncrypt(text.toString(),nonce),secKey)

    val encSecKey = rsaEncrypt(secKey, pubKey,modulus)
    val map:MutableMap<String,String> = mutableMapOf()
    map["params"] = encText
    map["encSecKey"] = encSecKey
    return map
}

/**
 * 字符串转十六进制
 */
fun stringToHex(string:String):String{
    val str = StringBuilder()
    (0 until string.length)
            .map { string.toCharArray()[it] }
            .map { Integer.toHexString(it.toInt()) }
            .forEach { str.append(it) }
    return str.toString()
}

/**
 * 在指定字符前补sign字符
 */
fun addSign2String(sign:String,str:String,len:Int):String{
    var string = str
    var  length = string.length
    if (length < len){
        while (length < len){
            val builder = StringBuffer()
            builder.append(sign).append(string)
            string = builder.toString()
            length = string.length
        }
    }
    return string
}

