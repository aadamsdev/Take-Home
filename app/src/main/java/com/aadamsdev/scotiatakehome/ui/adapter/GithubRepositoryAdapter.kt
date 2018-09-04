package com.aadamsdev.scotiatakehome.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.aadamsdev.scotiatakehome.R
import com.aadamsdev.scotiatakehome.repository.entity.GithubRepositoryEntity
import com.aadamsdev.scotiatakehome.ui.viewholder.GithubRepositoryViewHolder

class GithubRepositoryAdapter(private val githubRepositoryEntities: List<GithubRepositoryEntity>,
                              private val delegate: GithubRepositoryOnClickDelegate) : RecyclerView.Adapter<GithubRepositoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubRepositoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return GithubRepositoryViewHolder(layoutInflater.inflate(R.layout.github_repository, parent, false), delegate)
    }

    override fun getItemCount() = githubRepositoryEntities.size

    override fun onBindViewHolder(holder: GithubRepositoryViewHolder, position: Int) {
        holder.onBind(githubRepositoryEntities[position])
    }

    interface GithubRepositoryOnClickDelegate {
        fun onGithubRepositoryClicked(repositoryEntity: GithubRepositoryEntity)
    }
}