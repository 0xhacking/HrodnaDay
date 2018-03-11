package com.github.kiolk.hrodnaday.ui.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import com.github.kiolk.hrodnaday.R

class LeavingMessageFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var dialog = AlertDialog.Builder(activity)
        dialog.setMessage(R.string.LEAVE_MESSAGE)
        dialog.setPositiveButton(R.string.LEAVE, object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                activity.finish()
            }
        })
        dialog.setNegativeButton(R.string.NO, object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                fragmentManager.fragments.clear()
            }
        })
        return dialog.create()
    }
}