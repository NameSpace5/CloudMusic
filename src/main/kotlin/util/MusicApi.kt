package util

import com.google.gson.*
import model.bean.*

class MusicApi {
    companion object {
        val ACTION_URL_TOP = "api/playlist/detail"
        val BASE_URL_163 = "http://music.163.com"
        val ACTION_URL_SEARCH = "api/search/get/"
        val ACTION_URL_LOGIN_CELLPHONE = "weapi/login/cellphone"
        val ACTION_URL_LOGIN = "weapi/login"
        val ACTION_URL_COMMENTS = "weapi/v1/resource/comments/R_SO_4_"
        val client = OkHttpManager.getInstance()
    }

    /**
     * 音乐搜索，返回SongResult
     * @Param name 搜索名
     * @param type 搜索类型 1-> 单曲  10->专辑 100->歌手 1000->歌单 1002->用户
     * @param count 搜索结果数目
     * @param page 显示页数
     */
    fun songSearch(name:String,type:Int,count:Int,page:Int):SongsResult{
        val song = SongsResult::class
        val map = mutableMapOf<String,String>()
        map["s"] = name
        map["limit"] = count.toString()
        map["type"] = type.toString()
        map["offset"] = page.toString()

        val temp = client.requestPostSyn(BASE_URL_163, ACTION_URL_SEARCH,map)
        val json = JsonParser().parse(temp).asJsonObject["result"].toString()
        return Gson().fromJson(json,song.javaObjectType)
    }
    /**
     * 手机登陆，封装加密方式
     * @Param phone
     * @Param password
     */
    fun loginPhone(phone:String,password:String):LoginResult{
        val profile = LoginResult::class

        val map = encrypted_request(0,phone,password)
        val result = client.requestPostSyn(BASE_URL_163, ACTION_URL_LOGIN_CELLPHONE,map)

        return Gson().fromJson(result,profile.javaObjectType)
    }

    /**
     * 邮箱登录?存在问题：非法登陆（云盾动态加密身份验证，由于资金问题先搁置）
     */
    fun login(username:String,password: String):String{
        val map = encrypted_request(1,username,password)
        return client.requestPostSyn(BASE_URL_163, ACTION_URL_LOGIN,map)
    }

    /**
     * 热门评论获取,返回CommentResult
     * @param musicId 目标评论的音乐Id
     */
    fun hotComments(musicId:String):HotCommentResult{
        val hotBean = HotCommentResult::class

        val action_url = ACTION_URL_COMMENTS + musicId
        val map = encrypted_comments(musicId,1,20)
        val result = client.requestPostSyn(BASE_URL_163, action_url,map)
        return Gson().fromJson(result, hotBean.javaObjectType)
    }

    /**
     * 评论获取，第一页comments为最新评论，hotComments为热门评论，第二页之后不再有hotComments
     * @param musicId 目标评论的音乐Id
     * @param page 页数
     * @param count 获取评论数量
     */
    fun comments(musicId: String,page: Int,count:Int):CommentResult{
        val commentBean = CommentResult::class

        val action_url = ACTION_URL_COMMENTS + musicId
        val map = encrypted_comments(musicId,page,count)
        val result = client.requestPostSyn(BASE_URL_163, action_url,map)
        return Gson().fromJson(result, commentBean.javaObjectType)
    }


    /**
     * 获取歌单歌曲，地址已内置，引用 类TopList.变量名 即可，如(TopList.MUSIC_HOT)
     * 当然也可以自定义地址，如自己创建的歌单id
     * @param id 歌曲id
     */
    fun TracksMusic(id:String):TracksResult{
        val tracks = TracksResult::class

        val map = mutableMapOf<String,String>()
        map["id"] = id
        val temp =  client.requestGetSyn(BASE_URL_163, ACTION_URL_TOP,map)
        val json = JsonParser().parse(temp).asJsonObject["result"].toString()

        return Gson().fromJson(json,tracks.javaObjectType)
    }
}