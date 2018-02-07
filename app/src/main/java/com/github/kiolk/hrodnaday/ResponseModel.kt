package com.github.kiolk.hrodnaday

class ResponseModel(val objects: DayNoteModel?, val exception : Exception?, val callback: ResultCallback<ResponseModel>)