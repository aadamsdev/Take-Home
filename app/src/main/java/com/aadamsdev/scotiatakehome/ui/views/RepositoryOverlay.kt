package com.aadamsdev.scotiatakehome.ui.views

import android.content.Context
import android.widget.FrameLayout
import com.aadamsdev.scotiatakehome.R
import com.aadamsdev.scotiatakehome.repository.entity.GithubRepositoryEntity
import com.aadamsdev.scotiatakehome.util.DateFormatUtil
import kotlinx.android.synthetic.main.repository_overlay.view.*


class RepositoryOverlay(context: Context,
                        private val closeAction: () -> Unit,
                        repositoryEntity: GithubRepositoryEntity) : FrameLayout(context) {

    init {
        inflate(context, R.layout.repository_overlay, this)

        rootView?.setOnClickListener { closeAction() }

        last_updated_subtext?.text = DateFormatUtil.formatDate(repositoryEntity.updatedAt)
        stars_subtext?.text = repositoryEntity.starGazersCount.toString()
        forks_subtext?.text = repositoryEntity.forks.toString()
    }
}