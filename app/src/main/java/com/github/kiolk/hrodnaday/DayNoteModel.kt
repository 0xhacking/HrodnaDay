package com.github.kiolk.hrodnaday

data class DayNoteModel(var day : Long = 0,
           var pictureUrl : String = "Default url",
           var title : String = "title",
           var description : String = "description",
           var museum : String = "museum")