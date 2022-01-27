package com.sg.trendingandroidrepos.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sg.trendingandroidrepos.R
import com.sg.trendingandroidrepos.data.local.entity.GithubRepoEntity
import com.sg.trendingandroidrepos.databinding.RepoRecyclerItemLayoutBinding

class RepoListAdapter : RecyclerView.Adapter<RepoListAdapter.MyViewHolder>() {

    companion object {
        private const val TAG = "RepoListAdapter"
    }

    var listener: OnRepoSelectionListener? = null
    private var list: List<GithubRepoEntity> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<GithubRepoEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val binding =
            RepoRecyclerItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int,
        payloads: List<Any?>
    ) {

        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            updateView(holder, position, payloads[0])
        }
    }

    private fun updateView(holder: MyViewHolder, position: Int, any: Any?) {
        //for single item ui update with data
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]

        with(holder) {
            binding.textViewName.text = item.fullName

            Glide.with(binding.imageOwner.context).load(item.owner.avatarUrl)
                .circleCrop()
                .error(R.mipmap.ic_launcher_round)
                .placeholder(R.mipmap.ic_launcher_round)
                .into(binding.imageOwner)

            binding.layoutConstraint.setOnClickListener {
                listener?.onRepoSelected(item)
            }
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: RepoRecyclerItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnRepoSelectionListener {
        fun onRepoSelected(item: GithubRepoEntity)
    }

    fun setOnRepoSelectionListener(listener: OnRepoSelectionListener) {
        this.listener = listener
    }

}