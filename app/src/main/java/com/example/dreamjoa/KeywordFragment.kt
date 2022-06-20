package com.example.dreamjoa

import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dreamjoa.databinding.FragmentKeywordBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class KeywordFragment : Fragment() {
    lateinit var adapter: KeywordFragmentAdapter
    val arrayList = arrayListOf<MyData>()
    val scope= CoroutineScope(Dispatchers.IO)
    var binding: FragmentKeywordBinding?=null
    val myViewModel: MyViewModel by activityViewModels()
    val changedArrayList = arrayListOf<MyData>()
    var fontcheck=0;
    var sizecheck=16.0f;
    var url = "http://cse.konkuk.ac.kr/noticeList.do?siteId=CSE&boardSeq=882&menuSeq=6097"  //받았다치고
    var title ="학과 공지"  //  홈페이지이름에 들어갈타이틀

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentKeywordBinding.inflate(layoutInflater,container, false)
        binding!!.recyclerview.layoutManager=LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        binding!!.recyclerview.adapter=adapter

        adapter.itemClickListener = object :KeywordFragmentAdapter.OnItemClickListener{
            override fun OnItemClick(position: Int){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(adapter.items[position].url))
                startActivity(intent)
            }
        }

        return binding!!.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter=KeywordFragmentAdapter(arrayList,fontcheck,sizecheck)
    }

    fun getnews(){
        scope.launch {
            adapter.items.clear()
            var doc = Jsoup.connect(url).get()  //데이타 가져오기#noticeList > tr:nth-child(2) > td.subject > a
            var headlines = doc.select("#dispList > tr")
            for(news in headlines){
                var head = news.select("td.subject>a")
                  //앵커태그로 가져와서 text가 있고, href에 있는 실제 기사 주소 가져오겠다
                var t = head.text()
                var link = url+"&searchBy=&searchValue=&categorySeq=0&curBoardDispType=LIST&curPage=12&pageNum=1&seq="+head.attr("data-itsp-view-link")
                var d = news.select("td:nth-child(4)").text()
                var v = news.select("td:nth-child(5)").text().toInt()

                adapter.items.add(MyData(t,link,v,d))
            }

            withContext(Dispatchers.Main){
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                changedArrayList.clear()
                for(i in arrayList)
                {
                    if(i.noticetitle.contains(query.toString()))
                    {
                        changedArrayList.add(i)
                    }
                }
                adapter=KeywordFragmentAdapter(changedArrayList,fontcheck,sizecheck)
                adapter.itemClickListener = object :KeywordFragmentAdapter.OnItemClickListener{
                    override fun OnItemClick(position: Int){
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(adapter.items[position].url))
                        startActivity(intent)
                    }
                }
                binding!!.recyclerview.adapter=adapter
                adapter.notifyDataSetChanged()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                clearList()
                return true
            }

            fun clearList()
            {
                adapter=KeywordFragmentAdapter(arrayList,fontcheck,sizecheck)
                adapter.itemClickListener = object :KeywordFragmentAdapter.OnItemClickListener{
                    override fun OnItemClick(position: Int){
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(adapter.items[position].url))
                        startActivity(intent)
                    }
                }
                binding!!.recyclerview.adapter=adapter
                adapter.notifyDataSetChanged()
            }
        })

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
                1 -> binding!!.textView3.setTypeface(null, Typeface.NORMAL)
                2 -> binding!!.textView3.setTypeface(null, Typeface.BOLD)
                3 -> binding!!.textView3.setTypeface(null, Typeface.ITALIC)
            }
            adapter.notifyDataSetChanged()
        })

        myViewModel.selectedTextSize.observe(viewLifecycleOwner, Observer {
            adapter.cSize=it
            sizecheck=it
            adapter.notifyDataSetChanged()
        })

        myViewModel.selectedInfo.observe(viewLifecycleOwner, Observer {
            url=it.url
            title=it.noticetitle
            binding!!.textView3.text=it.noticetitle
            getnews()
        })
    }
}