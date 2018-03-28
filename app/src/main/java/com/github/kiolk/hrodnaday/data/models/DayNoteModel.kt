package com.github.kiolk.hrodnaday.data.models

import java.io.Serializable

data class DayNoteModel(var day : Long = 0,
           var pictureUrl : String = "http://www.istpravda.ru/upload/medialibrary/579/5792a059eabdca6a09df29f5f98bcafa.jpg",
           var title : String = "title",
           var author : String = "author",
           var creating : String = "creating date",
           var description : String = "description",
           var size : String = "size",
           var materials : String = "materials",
           var museum : String = "author",
           var museumUrl : String = "museumUrl",
           var language : String = "language",
           var museumCoordinates : String = "museumCoordinates",
           var articleAuthor : String = "articleCoordinates") : Serializable