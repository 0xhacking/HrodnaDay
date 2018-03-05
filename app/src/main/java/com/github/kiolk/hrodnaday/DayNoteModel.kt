package com.github.kiolk.hrodnaday

import java.io.Serializable

data class DayNoteModel(var day : Long = 0,
           var pictureUrl : String = "Default url",
           var title : String = "title",
           var author : String = "author",
           var creating : String = "creating date",
           var description : String = "description",
           var size : String = "size",
           var materials : String = "materials",
           var museum : String = "museum",
           var museumUrl : String = "museumUrl",
           var museumCoordinates : String = "museumCoordinates",
           var articleAuthor : String = "articleCoordinates") : Serializable