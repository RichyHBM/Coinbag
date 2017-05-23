package uk.co.richyhbm.coinbag.fragments

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.text.Layout
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.github.paolorotolo.appintro.AppIntroBaseFragment
import com.github.paolorotolo.appintro.AppIntroFragment
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder
import com.mikepenz.community_material_typeface_library.CommunityMaterial
import com.mikepenz.iconics.IconicsDrawable
import com.mikepenz.iconics.typeface.IIcon

import uk.co.richyhbm.coinbag.R
import uk.co.richyhbm.coinbag.utils.Icons


class WelcomeSlideFragment(val icon: IIcon?) : AppIntroBaseFragment() {


    companion object {
        fun newInstance(title: CharSequence, description: CharSequence,
                        @DrawableRes imageDrawable: Int, @ColorInt bgColor: Int,
                        @ColorInt titleColor: Int, @ColorInt descColor: Int): WelcomeSlideFragment {
            val slide = WelcomeSlideFragment(null)
            val args = Bundle()
            args.putString(AppIntroBaseFragment.ARG_TITLE, title.toString())
            args.putString(AppIntroBaseFragment.ARG_TITLE_TYPEFACE, null)
            args.putString(AppIntroBaseFragment.ARG_DESC, description.toString())
            args.putString(AppIntroBaseFragment.ARG_DESC_TYPEFACE, null)
            args.putInt(AppIntroBaseFragment.ARG_DRAWABLE, imageDrawable)
            args.putInt(AppIntroBaseFragment.ARG_BG_COLOR, bgColor)
            args.putInt(AppIntroBaseFragment.ARG_TITLE_COLOR, titleColor)
            args.putInt(AppIntroBaseFragment.ARG_DESC_COLOR, descColor)
            slide.arguments = args

            return slide
        }

        fun newInstance(title: CharSequence, description: CharSequence,
                        imageDrawable: IIcon, @ColorInt bgColor: Int,
                        @ColorInt titleColor: Int, @ColorInt descColor: Int): WelcomeSlideFragment {
            val slide = WelcomeSlideFragment(imageDrawable)
            val args = Bundle()
            args.putString(AppIntroBaseFragment.ARG_TITLE, title.toString())
            args.putString(AppIntroBaseFragment.ARG_TITLE_TYPEFACE, null)
            args.putString(AppIntroBaseFragment.ARG_DESC, description.toString())
            args.putString(AppIntroBaseFragment.ARG_DESC_TYPEFACE, null)
            args.putInt(AppIntroBaseFragment.ARG_DRAWABLE, R.drawable.ic_done_white)
            args.putInt(AppIntroBaseFragment.ARG_BG_COLOR, bgColor)
            args.putInt(AppIntroBaseFragment.ARG_TITLE_COLOR, titleColor)
            args.putInt(AppIntroBaseFragment.ARG_DESC_COLOR, descColor)
            slide.arguments = args

            return slide
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val v =  super.onCreateView(inflater, container, savedInstanceState)

        if(v != null){
            if(icon != null){
                val i = v.findViewById(com.github.paolorotolo.appintro.R.id.image) as ImageView
                i.setImageDrawable(Icons.getIcon(this.context, icon, R.color.black, 180))
            }
        }

        return v
    }

    override fun getLayoutId(): Int {
        return com.github.paolorotolo.appintro.R.layout.fragment_intro
    }
}
