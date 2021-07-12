package com.star.mvvm.mapdiary.model

data class DiaryModel(
    var title       : String,
    var description : String,
    var writer      : String,
    var createDate  : Long,
    var latitude    : Float,
    var longitude   : Float
){}
