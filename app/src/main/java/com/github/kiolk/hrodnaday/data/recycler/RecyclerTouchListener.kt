package com.github.kiolk.hrodnaday.data.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View

interface ItemClickListener{
    fun onItemClick(date: Long)
}

interface ClickListener{
    fun onClick(view : View, position : Int)

    fun onLongClick(view : View, position : Int)
}

class RecyclerTouchListener() : RecyclerView.OnItemTouchListener{

    lateinit var detector : GestureDetector
    var listener : ClickListener? = null

    constructor(pContext: Context, recycler: RecyclerView, pListener: ClickListener?) : this(){
        listener = pListener

        detector = GestureDetector(pContext, object : SimpleOnGestureListener(){
            override fun onSingleTapUp(e: MotionEvent?): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                val childView = recycler.findChildViewUnder(e.x, e.y)
                if (childView != null) listener?.onLongClick(childView, recycler.getChildAdapterPosition(childView))
            }
        })
    }


    override fun onTouchEvent(rv: RecyclerView?, e: MotionEvent?) {
    }

    override fun onInterceptTouchEvent(rv: RecyclerView?, e: MotionEvent?): Boolean {
        val childView = e?.x?.let { rv?.findChildViewUnder(it, e.y) }
        if (childView != null && listener != null && detector.onTouchEvent(e)) rv?.getChildAdapterPosition(childView)?.let { listener?.onClick(childView, it) }
    return false
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }

}