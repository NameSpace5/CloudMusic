
import util.MusicApi


fun main(args: Array<String>) {

    val api = MusicApi()

//    音乐搜索
    val a = api.songSearch("好久不见",1,20,1)
    println(a.songs[1].name)

//    歌单歌曲
    val b = api.TracksMusic("832569787")
    println(b.tracks[1].name)

//    热门评论
    val c = api.hotComments("65538",1,20)
    println(c.hotComments[1].content)

//    手机登陆
    val d = api.songSearch("好久不见",1,20,1)
    for (num in 0 until d.songs.size) println(d.songs[num].name)

}