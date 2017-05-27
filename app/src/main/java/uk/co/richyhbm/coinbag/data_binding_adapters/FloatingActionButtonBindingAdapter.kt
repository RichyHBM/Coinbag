package uk.co.richyhbm.coinbag.data_binding_adapters

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.design.widget.FloatingActionButton

object FloatingActionButtonBindingAdapter {
    @BindingAdapter("android:src")
    @JvmStatic
    fun setImageUri(view: FloatingActionButton, imageUri: String?) {
        if (imageUri == null) {
            view.setImageURI(null)
        } else {
            view.setImageURI(Uri.parse(imageUri))
        }
    }

    @BindingAdapter("android:src")
    @JvmStatic
    fun setImageUri(view: FloatingActionButton, imageUri: Uri) {
        view.setImageURI(imageUri)
    }

    @BindingAdapter("android:src")
    @JvmStatic
    fun setImageDrawable(view: FloatingActionButton, drawable: Drawable) {
        view.setImageDrawable(drawable)
    }

    @BindingAdapter("android:src")
    @JvmStatic
    fun setImageResource(imageView: FloatingActionButton, resource: Int) {
        imageView.setImageResource(resource)
    }
}