package com.example.dreamjoa

//data class MyData(var noticetitle:String, var url:String)
////공지사항의 제목과 url을 저장할 데이터 class

data class MyData(var noticetitle:String, var url:String){
    var views = 0
    var date = "0000-00-00"

    constructor(noticetitle:String, url:String,views: Int, date:String):this(noticetitle,url){
        this.views = views
        this.date = date
    }
}
//공지사항의 제목과 url을 저장할 데이터 class