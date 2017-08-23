package model.bean


//歌曲
data class Song(val id:Long, val name:String, val artists: List<Artist>, val album: Album, val duration:Int, val status:Int, val mvId:Int, val fee:Int)


//专辑
data class Album(val id:Long, val name:String,val publishTime:Long, val size:Int, val status:Int,
                val picId: Long,val PicUrl:String,val companyId:Long,val company:String,val artists:List<Artist>,
                 val copyrightId:Int,val paid:Boolean,val onSale:Boolean,val containedSong:String)

//艺术家
data class Artist(val id:Long, val name:String, val picUrl:String, val albumSize:Int, val picId:Int, val img1v1Url:String)

//个人账户
data class Account(val id:Long,val createTime:Long,val vipType:Int,val vipTypeVersion: Int)

//个人资料
data class Profile(val avatarUrl:String,val userType:Int,val gender:Int,val birthday:Long,val city:Int,
                    val province:Int,val vipType:Int,val userId:Int,val signature:String,val nickname:String)

//热门评论
data class HotComment(val beReplied: List<BeReplied>,val user:User,val liked:Boolean,val likedCount:Long,val time:Long,val content:String)

//最新评论
data class Comment(val beReplied: List<BeReplied>,val liked: Boolean,val commentId: Int,val likedCount: Int,val time: Long,val content: String)

//榜单歌曲
data class Track(val name:String,val id:Long,val position:Int,val fee:Int,val commentThreadId:String,
                  val score:Int,val duration:Long)

//歌单信息
data class PlayList(val tags:List<String>,val trackNumberUpdateTime:Long,val trackCount:Int,val coverImageUrl:String,
                    val playCount:Long,val userId:Int,val coverImageId:String,val trackUpdateTime: Long,
                    val description:String,val name:String,val id:Long,val shareCount:Int,val commentCount:Int)

data class Binding(val userId: Long,val url: String,val tokenJsonStr: String,val expired: Boolean,val expiresIn: Int,
                   val refreshTime:Long,val id:Long,val type: Int)
//评论用户信息
data class User(val locationInfo: String,val userType:Int,val nickname:String,val vipType: Int,val experTags:String,
                val remarkName:String,val authStatus:Int,val userId:Long,val avatarUrl: String)

//评论回复
data class BeReplied(val user:User,val content: String,val status: Int)

//搜索用户信息
data class UserProfile(val defaultAvatar:Boolean,val province: Int,val avatarUrl:String,val gender: Int,
                        val city: Int,val birthday: Long,val userId: Int,val userType: Int,val nickname: String,
                        val signature: String,val description: String,val backgroundUrl:String)

//搜索反馈
data class SearchCode(val code:Int)

//song_result
data class SongsResult(val songs:List<Song>,val songCount:Int)

data class AlbumResult(val albums:List<Album>,val albumCount:Int)

data class PlaylistResult(val playlist:List<PlayList>,val playListCount:Int)

data class UserProfileResult(val userProfile:List<Profile>,val userProfileCount:Int)

data class ArtistsResult(val artists:List<Artist>,val artistCount:Int)

//热评result
data class HotCommentResult(val hotComments:List<HotComment>,val isMusician:Boolean,val moreHot: Boolean,
                            val comments:List<Comment>,val total:Long,val more:Boolean,val code:Int)

//最新评论
data class CommentResult(val isMusician:Boolean,val moreHot: Boolean,
                         val comments:List<Comment>,val total:Long,val more:Boolean,val code:Int)

//登陆result
data class LoginResult(val loginType:Int,val code:Int,val account: Account,val profile: Profile,val bindings: List<Binding>)

//歌单result
data class TracksResult(val creator:Creator,val tracks:List<Track>,val trackNumberUpdateTime: Long,
                        val ordered:Boolean,val tags: List<String>,val userId: Long,val createTime: Long,
                        val trackUpdateTime: Long,val trackCount: Int,val playCount: Long,val description: String,
                        val subscribedCount:Long,val name:String,val id: Long,val shareCount: Int,val commentCount: Int)
//歌单创建者
data class Creator(val defaultAvatar: Boolean,val province: Int,val gender: Int,val city: Int,val userId: Int,val birthday: Long,
                   val userType: Int,val nickname: String,val signature: String,val avatarUrl: String,val backgroundUrl: String)

