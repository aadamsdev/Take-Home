package com.aadamsdev.scotiatakehome.ui.viewholder

import android.view.View
import android.widget.TextView
import com.aadamsdev.scotiatakehome.R
import com.aadamsdev.scotiatakehome.repository.entity.BaseListItem
import com.aadamsdev.scotiatakehome.repository.entity.GithubRepositoryEntity
import com.aadamsdev.scotiatakehome.ui.adapter.GithubRepositoryAdapter

class GithubRepositoryViewHolder(private val view: View,
                                 private val delegate: GithubRepositoryAdapter.GithubRepositoryOnClickDelegate) : BaseViewHolder(view) {

    override fun onBind(listItem: BaseListItem) {
        (listItem as? GithubRepositoryEntity)?.let { repository ->
            view.findViewById<TextView>(R.id.repository_name)?.text = repository.name
            view.findViewById<TextView>(R.id.repository_description)?.text = repository.description

            view.setOnClickListener { delegate.onGithubRepositoryClicked(repository) }
        }
    }
}