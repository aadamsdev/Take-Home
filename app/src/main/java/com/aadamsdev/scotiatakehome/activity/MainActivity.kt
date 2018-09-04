package com.aadamsdev.scotiatakehome.activity

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.PopupWindow
import android.widget.Toast
import com.aadamsdev.scotiatakehome.R
import com.aadamsdev.scotiatakehome.activity.base.BaseActivity
import com.aadamsdev.scotiatakehome.repository.entity.GithubRepositoryEntity
import com.aadamsdev.scotiatakehome.repository.entity.UserEntity
import com.aadamsdev.scotiatakehome.ui.adapter.GithubRepositoryAdapter
import com.aadamsdev.scotiatakehome.ui.views.RepositoryOverlay
import com.aadamsdev.scotiatakehome.util.GlideUtil
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainActivityContract.View, GithubRepositoryAdapter.GithubRepositoryOnClickDelegate {

    @Inject
    lateinit var presenter: MainActivityPresenter

    private var overlayPopupWindow: PopupWindow? = null

    override fun getLayoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        search_button.setOnClickListener {
            hideKeyboard()
            presenter.getUserData(search_input.text.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onViewAttached(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.onViewDetached()
        presenter.clearDisposable()
    }

    override fun setUserProfile(user: UserEntity) {
        GlideUtil.loadImage(this, user.avatarUrl, object : GlideUtil.ImageLoadCallback {
            override fun onResourceReady(resource: Drawable) {
                profile_name?.text = user.name
                profile_image?.setImageDrawable(resource)

                val animation = AnimationSet(true).apply {
                    addAnimation(AlphaAnimation(0f, 1f))
                    addAnimation(TranslateAnimation(0f, 0f, 100f, 0f))
                    duration = 1000
                }

                profile_name.startAnimation(animation)
                profile_image.startAnimation(animation)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {

            }
        })
    }

    override fun setGithubRepositories(repositoryEntities: List<GithubRepositoryEntity>) {
        content_recycler_view?.layoutManager = LinearLayoutManager(this)
        content_recycler_view?.adapter = GithubRepositoryAdapter(repositoryEntities, this)

        content_recycler_view.startAnimation(AnimationSet(true).apply {
            addAnimation(AlphaAnimation(0f, 1f))
            addAnimation(TranslateAnimation(0f, 0f, 100f, 0f))
            duration = 1000
            startOffset = 750
        })
    }

    override fun onGithubRepositoryClicked(repositoryEntity: GithubRepositoryEntity) {
        showRepositoryInfoOverlay(repositoryEntity)
    }

    override fun showErrorToast() {
        Toast.makeText(this, R.string.error_toast, Toast.LENGTH_SHORT).show()
    }

    private fun showRepositoryInfoOverlay(repositoryEntity: GithubRepositoryEntity) {
        val repositoryOverlay = RepositoryOverlay(this,
                {
                    overlayPopupWindow?.dismiss()
                    overlay_dimming_background?.startAnimation(AnimationSet(true).apply{
                        addAnimation(AlphaAnimation(1f, 0f))
                        duration = 500
                        fillAfter = true
                    })
                },
                repositoryEntity)

        // Only create the popup window once, from then on just re-assign the content view
        overlayPopupWindow?.let {
            it.contentView = repositoryOverlay
        } ?: kotlin.run {
            overlayPopupWindow = PopupWindow(
                    repositoryOverlay,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    false)
        }

        overlayPopupWindow?.animationStyle = R.style.pop_up_window_animation
        overlayPopupWindow?.showAtLocation(root_view, Gravity.BOTTOM, 0, 0)

        // Dimmed background is separate from popup window so the animation doesn't apply
        overlay_dimming_background?.startAnimation(AnimationSet(true).apply{
            addAnimation(AlphaAnimation(0f, 1f))
            duration = 500
            fillAfter = true
        })
    }

    private fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(search_input.windowToken, 0)
    }
}
