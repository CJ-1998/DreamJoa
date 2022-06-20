package com.example.dreamjoa

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {
    val selectedFont=MutableLiveData<Int>(0)
    val selectedTextSize=MutableLiveData<Float>(16.0f)
    val selectedInfo= MutableLiveData<MyData>()

    fun setLiveData(font:Int)
    {
        selectedFont.value=font
    }

    fun setLiveData2(size:Float)
    {
        selectedTextSize.value=size
    }

    fun setLiveData3(data:MyData)
    {
        selectedInfo.value=data
    }
}
