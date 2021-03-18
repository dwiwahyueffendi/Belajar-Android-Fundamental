package com.example.submission1fundamental

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.activity_user.view.*


class GithubUserAdapter constructor(private val context: Context) : BaseAdapter() {
    var gitUsers = arrayListOf<GithubUserData>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.activity_user, parent, false)
        }

        val viewHolder = ViewHolder(itemView as View)

        val gituser = getItem(position) as GithubUserData
        viewHolder.bind(gituser)
        return itemView
    }

    private inner class ViewHolder constructor(private val view: View) {
        fun bind(githubUser: GithubUserData) {
            with(view) {
                img_user.setImageResource(githubUser.Avatar)
                tv_name_detail.text = githubUser.Name
                tv_username.text = "@ " + githubUser.Username
                tv_company.text = githubUser.Company
                tv_repository.text = githubUser.Repository + " Repository"
            }
        }
    }

    override fun getItem(position: Int): Any {
        return gitUsers[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return gitUsers.size
    }
}