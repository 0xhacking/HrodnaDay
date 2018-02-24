package com.github.kiolk.hrodnaday

class ResponseModel(val objects: Array<DayNoteModel>?, val exception : Exception?, val callback: ResultCallback<ResponseModel>)