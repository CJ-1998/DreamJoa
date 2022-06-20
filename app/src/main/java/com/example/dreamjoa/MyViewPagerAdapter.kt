package com.example.dreamjoa

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MyViewPagerAdapter(fragmentActivity: FragmentActivity)
    :FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
        //fragment 4개 부착
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0-> PageFragment()
            1-> NoticeFragment()
            2->KeywordFragment()
            3-> SettingFragment()
            else->PageFragment()
            //tab의 위치에 따른 fragment 부착
        }
    }
}