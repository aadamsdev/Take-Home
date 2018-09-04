package com.aadamsdev.scotiatakehome.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

object GlideUtil {
    fun loadImage(context: Context?, url: String, callback: ImageLoadCallback) {
        if (context == null) {
            return
        }

        Glide.with(context).load(url).into(object : SimpleTarget<Drawable>() {
            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                callback.onResourceReady(resource)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                super.onLoadFailed(errorDrawable)
                callback.onLoadFailed(errorDrawable)
            }
        })
    }

    interface ImageLoadCallback {
        fun onResourceReady(resource: Drawable)

        fun onLoadFailed(errorDrawable: Drawable?)
    }

}
