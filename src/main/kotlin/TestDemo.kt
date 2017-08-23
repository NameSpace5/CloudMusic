
import util.MusicApi
import util.timeStamp2Date


fun main(args: Array<String>) {

    val api = MusicApi()

//    音乐搜索
//    val songs = api.songSearch("好久不见",1,20,1)
//    val song = songs.songs
//    for (num in 0 until song.size) println("歌曲："+song[num].name + "，专辑："+song[num].album.name)

//    歌单歌曲
//    val tracks = api.TracksMusic("832569787")
//    val tracks_name = tracks.name
//    val t = tracks.tracks
//    println("歌单名："+tracks_name+"包含歌曲名：")
//    for (num in 0 until t.size) println(t[num].name)
//
//    热门评论
//    val hot = api.hotComments("65538")
//    val comments = hot.hotComments
//    for (num in 0 until comments.size) println(comments[num].content)

    //最新评论
//    val comment = api.comments("65538",10,100)
//    val cs = comment.comments
//    for (num in 0 until cs.size) println(cs[num].content)
//
//    手机登陆
    val phone = api.loginPhone("13253515580","qq736225")
    val res = phone.profile
    println("用户名："+res.nickname+",生日："+ timeStamp2Date(res.birthday.toString())+",个性签名："+res.signature)

}