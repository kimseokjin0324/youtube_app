package fastcampus.aop.part3.chapter01.service

import fastcampus.aop.part3.chapter01.dto.VideoDto
import retrofit2.Call
import retrofit2.http.GET

// retrofit 을 이용해서 api를 호출하는 서비스를 인터페이스로 만듬
interface VideoService {

    @GET("/v3/039ca01b-e68a-42a2-bd06-5580db426673")
    fun listVideos() : Call<VideoDto>
}
