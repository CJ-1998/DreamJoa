package com.example.dreamjoa

import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dreamjoa.databinding.FragmentPageBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PageFragment : Fragment() {

    lateinit var adapter: PageFragmentAdapter
    //recyclerview에 부착학 adapter class 미리 선언
    val arrayList = arrayListOf<MyData>()
    //이 recyclerview의 데이터를 가지고 있을 arraylist

    val url1="http://cse.konkuk.ac.kr/noticeList.do?siteId=CSE&boardSeq=882&menuSeq=6097"  //학과공지
    val url2="http://cse.konkuk.ac.kr/noticeList.do?siteId=CSE&boardSeq=883&menuSeq=6099"  //채용정보
    val url3="http://cse.konkuk.ac.kr/noticeList.do?siteId=CSE&boardSeq=912&menuSeq=6277"  //학과소식
    val url4="http://counsel.konkuk.ac.kr/noticeList.do?siteId=COUNSEL&boardSeq=982&menuSeq=6772"  //학생상담센터
    val url5="http://ceei.konkuk.ac.kr/noticeList.do?siteId=CEEI&boardSeq=1227&menuSeq=8579"  //공학교육혁신사업단
    val url6="http://kuhuman.konkuk.ac.kr/noticeList.do?siteId=K-HUMAN&boardSeq=426&menuSeq=2905"  //인권센터
    val url7="http://swedu.konkuk.ac.kr/noticeList.do?siteId=SWEDU&boardSeq=399&menuSeq=2604"  //sw중심대학 사업단
    val urls = arrayOf(url1,url2,url3,url4,url5,url6,url7)
    val urllist = arrayOf("컴퓨터공학과 공지","컴퓨터공학과 채용정보","컴퓨터공학과 소식","학생상담센터","공학교육혁신사업단","인권센터","SW중심대학 사업단")

    val scope= CoroutineScope(Dispatchers.IO)
    //웹 파싱 관련 변수

    val myViewModel:MyViewModel by activityViewModels()
    var binding: FragmentPageBinding?=null
    var fontcheck=0;
    var sizecheck=16.0f;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentPageBinding.inflate(layoutInflater,container,false)
        binding!!.recyclerview.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        binding!!.recyclerview.adapter=adapter

        return binding!!.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter=PageFragmentAdapter(arrayList,fontcheck,sizecheck)
        //adpter class를 만들어 adapter를 초기화하는 것이다.
        initPage()
    }

    private fun initPage() {
        scope.launch {
            adapter.items.add(MyData(urllist[0],urls[0]))
            adapter.items.add(MyData(urllist[1],urls[1]))
            adapter.items.add(MyData(urllist[2],urls[2]))
            adapter.items.add(MyData(urllist[3],urls[3]))
            adapter.items.add(MyData(urllist[4],urls[4]))
            adapter.items.add(MyData(urllist[5],urls[5]))
            adapter.items.add(MyData(urllist[6],urls[6]))
            adapter.itemClickListener = object :PageFragmentAdapter.OnItemClickListener{
                override fun OnItemClick(data: MyData, position: Int) {
                    myViewModel.setLiveData3(adapter.items[position])
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myViewModel.selectedFont.observe(viewLifecycleOwner, Observer {
            when (it) {
                1 -> {
                    adapter.check=1
                    fontcheck=1
                }
                2 -> {
                    adapter.check=2
                    fontcheck=2
                }
                3 -> {
                    adapter.check=3
                    fontcheck=3
                }
            }
            when (it) {
                1 -> binding!!.textView.setTypeface(null, Typeface.NORMAL)
                2 -> binding!!.textView.setTypeface(null, Typeface.BOLD)
                3 -> binding!!.textView.setTypeface(null, Typeface.ITALIC)
            }
            adapter.notifyDataSetChanged()
        })

        myViewModel.selectedTextSize.observe(viewLifecycleOwner, Observer {
            adapter.cSize=it
            sizecheck=it
            adapter.notifyDataSetChanged()
        })
    }


}