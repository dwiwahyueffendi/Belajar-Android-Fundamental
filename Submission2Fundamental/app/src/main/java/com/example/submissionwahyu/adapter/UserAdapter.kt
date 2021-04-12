package com.example.submissionwahyu.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.example.submissionwahyu.R
import com.example.submissionwahyu.data.User
import com.example.submissionwahyu.databinding.ListItemUserBinding


class UserAdapter : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    private var list = ArrayList<User>()

    private var onItemClickCallback: OnItemClickCallback? = null
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    fun setList(users: ArrayList<User>){
        list.clear()
        list.addAll(users)
        notifyDataSetChanged()
    }
    inner class UserViewHolder(val binding: ListItemUserBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: User){
            binding.root.setOnClickListener{
                onItemClickCallback?.onItemClicked(user)
            }

            binding.apply {
                val requestOptions = RequestOptions()
                requestOptions.placeholder(R.drawable.ic_account)
                requestOptions.error(R.drawable.ic_error)

                Glide.with((itemView))
                    .load(user.photoUser)
                    .apply(RequestOptions()
                        .placeholder(R.drawable.ic_account)
                        .error(R.drawable.ic_error))
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivUser)
                tvUsername.text = user.username
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder((view))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickCallback{
        fun onItemClicked(data: User)
    }
}