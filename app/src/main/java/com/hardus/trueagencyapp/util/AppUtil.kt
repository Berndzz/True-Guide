package com.hardus.trueagencyapp.util

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper

fun Context.findActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("no activity")
}

object URL_API{
    const val Base = "https://trueguide-846cb-default-rtdb.firebaseio.com/"
}
