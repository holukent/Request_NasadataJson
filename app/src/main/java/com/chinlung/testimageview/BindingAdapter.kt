package com.chinlung.testimageview


import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.chinlung.testimageview.model.NasaDataItem


@BindingAdapter(value = ["setImage"])
fun setImage(imageView: ImageView, nasaDataItem: NasaDataItem) {

    imageView.setImageBitmap(null)
    imageView.setImageURI(
        Uri.parse("file:///data/data/com.chinlung.testimageview/files/${nasaDataItem.date}.jpg")
    )
}