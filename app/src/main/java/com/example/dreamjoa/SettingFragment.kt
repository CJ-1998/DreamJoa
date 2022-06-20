package com.example.dreamjoa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.dreamjoa.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    val myViewModel:MyViewModel by activityViewModels()

    var binding: FragmentSettingBinding?=null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSettingBinding.inflate(layoutInflater,container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.apply {

            sizebutton.setOnClickListener {
                myViewModel.setLiveData2(binding!!.setTextSize.getText().toString().toFloat())
            }

            radioGroup.setOnCheckedChangeListener { radioGroup, i ->
                when (i) {
                    //버튼 눌러서 바뀌어야 하는 일을 보자
                    R.id.radioButton1 -> {
                        myViewModel.setLiveData(1)
                    }
                    R.id.radioButton2 -> {
                        myViewModel.setLiveData(2)
                    }
                    R.id.radioButton3 -> {
                        myViewModel.setLiveData(3)
                    }
                }
            }
        }
    }
}