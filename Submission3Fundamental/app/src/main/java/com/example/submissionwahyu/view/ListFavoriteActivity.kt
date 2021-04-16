package com.example.submissionwahyu.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionwahyu.R
import com.example.submissionwahyu.adapter.UserAdapter
import com.example.submissionwahyu.data.database.FavoriteDatabase
import com.example.submissionwahyu.data.endpoint.User
import com.example.submissionwahyu.databinding.ActivityListFavoriteBinding
import com.example.submissionwahyu.viewmodel.FavoriteViewModel

class ListFavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@ListFavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, data.username)
                    it.putExtra(DetailActivity.EXTRA_ID, data.id)
                    it.putExtra(DetailActivity.EXTRA_PHOTO_USER, data.photoUser)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(this@ListFavoriteActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this, {
            if (it!=null){
                val listFavoriteUser = mapListUser(it)
                adapter.setList(listFavoriteUser)
            }
        })

        val actionBar = supportActionBar
        actionBar!!.setTitle(R.string.list_favorite)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    private fun mapListUser(users: List<FavoriteDatabase>): ArrayList<User> {
        val listUsers = ArrayList<User>()
        for(user in users){
            val userMap = User(
                user.username,
                user.id,
                user.photoUser
            )
            listUsers.add(userMap)
        }
        return listUsers
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}