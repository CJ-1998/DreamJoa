package com.example.dreamjoa

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dreamjoa.databinding.KeywordfragmentBinding


class KeywordFragmentAdapter (val items:ArrayList<MyData>, val font:Int, size:Float) : RecyclerView.Adapter<KeywordFragmentAdapter.MyViewHolder>()
{
    //items에 앱정보가 가득 찬 상태로 전달되는 것이다.

    //인터페이스를 정의하는 것이다.
    interface OnItemClickListener
    {
        fun OnItemClick(position: Int)
    }

    var itemClickListener:OnItemClickListener?=null


    var check:Int=font;
    var cSize=size;

    inner class MyViewHolder(val binding: KeywordfragmentBinding): RecyclerView.ViewHolder(binding.root)
    {
        init{
            //newtitle 클릭하면 이 아래 함수 호출하는데 position 정보 하나 넘긴다
            binding.keywordtitle.setOnClickListener {
                itemClickListener?.OnItemClick(adapterPosition)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val binding=KeywordfragmentBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        holder.binding.keywordtitle.text=items[position].noticetitle
        //여기는 newtitle을 textview에 넣는 것이다.
        changeFont(holder, check)
        changeSize(holder,cSize)
    }

    override fun getItemCount(): Int
    {
        return items.size
    }

    fun changeFont(holder:MyViewHolder,i:Int)
    {
        when(i)
        {
            0->holder.binding.keywordtitle.setTypeface(null, Typeface.NORMAL)
            1->holder.binding.keywordtitle.setTypeface(null, Typeface.NORMAL)
            2->holder.binding.keywordtitle.setTypeface(null, Typeface.BOLD)
            3->holder.binding.keywordtitle.setTypeface(null, Typeface.ITALIC)
        }
    }

    fun changeSize(holder:MyViewHolder, i:Float)
    {
        holder.binding.keywordtitle.textSize=i;
    }
}