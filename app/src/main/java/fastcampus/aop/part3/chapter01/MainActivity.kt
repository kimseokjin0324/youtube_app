package fastcampus.aop.part3.chapter01

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fastcampus.aop.part3.chapter01.adapter.VideoAdapter
import fastcampus.aop.part3.chapter01.dto.VideoDto
import fastcampus.aop.part3.chapter01.service.VideoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {


    private lateinit var videoAdapter: VideoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, PlayerFragment())
            .commit()

        videoAdapter = VideoAdapter(callback = { url, title ->
            // Activity에 모든 fragment를 가져온다음  PlayerFragment인것을 확인한 후 (Fragment 의) play함수를 호출
            supportFragmentManager.fragments.find {it is PlayerFragment}?.let {
                (it as PlayerFragment).play(url,title)
            }
        })
        //리사이클러뷰 초기화
        findViewById<RecyclerView>(R.id.mainRecyclerView).apply {

            adapter = videoAdapter
            layoutManager = LinearLayoutManager(context)
        }
        getVideoList()
    }


    private fun getVideoList() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://run.mocky.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        retrofit.create(VideoService::class.java).also {
            it.listVideos()
                .enqueue(object : Callback<VideoDto> {
                    override fun onResponse(call: Call<VideoDto>, response: Response<VideoDto>) {
                        if (response.isSuccessful.not()) {
                            Log.d("MainActivity", "response fail")
                            return
                        }

                        response.body()?.let { videoDto ->

                            videoAdapter.submitList(videoDto.videos)
                        }


                    }

                    override fun onFailure(call: Call<VideoDto>, t: Throwable) {
                        // 예외처리
                    }

                })
        }
    }

}