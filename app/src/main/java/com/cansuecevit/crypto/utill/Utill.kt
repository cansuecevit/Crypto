package com.cansuecevit.crypto.utill

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideContext
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.cansuecevit.crypto.R
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlinx.android.synthetic.main.fragment_home.view.*


fun ImageView.downloadFromUrl(url:String?,progressDrawable: CircularProgressDrawable){
    val options =RequestOptions
        .placeholderOf(progressDrawable)
        .error(R.mipmap.ic_launcher_round)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .into(this)

}
fun  placeholderProgressBar(context: Context):CircularProgressDrawable{

    return CircularProgressDrawable(context).apply {

        strokeWidth=8f
        centerRadius=40f
        start()
    }

}
@BindingAdapter("android:downloadUrl")
fun downloadUrl(view: ImageView,url: String?){
    view.downloadFromUrl(url, placeholderProgressBar(view.context))
}

