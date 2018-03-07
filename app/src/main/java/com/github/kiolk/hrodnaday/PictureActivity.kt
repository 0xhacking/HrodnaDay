package com.github.kiolk.hrodnaday

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.kiolk.hrodnaday.ui.fragments.PICTURE_URL
import kiolk.com.github.pen.utils.MD5Util
import kotlinx.android.synthetic.main.activity_picture.*

class PictureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture)

        val pictureUrl = intent.getStringExtra(PICTURE_URL)

        val urlFolder :  String = baseContext.cacheDir.canonicalPath
        val url : String = "file://$urlFolder/ImageCache/"
        val tag = MD5Util.getHashString(pictureUrl)

        Log.d("MyLogs", pictureUrl)
        val data : String = "<body bgcolor=\"#000000\"><div class=\"centered-content\" align=\"middle\" ><img src=\"$tag.jpg\"/></div></body>"
        picture_web_view.loadDataWithBaseURL(url, data,  "text/html", "UTF-8", null )
//        picture_web_view.loadUrl("https://loremflickr.com/320/240")
        picture_web_view.settings.builtInZoomControls = true
//        picture_web_view.settings.displayZoomControls = true
        picture_web_view.settings.useWideViewPort = true
        picture_web_view.settings.loadWithOverviewMode = true

    }
}
