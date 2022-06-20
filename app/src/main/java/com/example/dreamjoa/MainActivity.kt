package com.example.dreamjoa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.dreamjoa.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val myViewModel: MyViewModel by viewModels()

    val textarr= arrayListOf<String>("홈페이지 선택","공지사항","키워드 검색","설정")
    //tab에 text에 넣을 배열

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }
    private fun initLayout() {
        binding.viewpager.adapter= MyViewPagerAdapter(this)
        //viewpager에 adpater 부착

        TabLayoutMediator(binding.tabLayout,binding.viewpager){
                tab,position->
            tab.text=textarr[position]
            //tab에 text를 위의 배열의 값으로 설정
        }.attach()
        //tab을 mainactivity에 부착

    }
}