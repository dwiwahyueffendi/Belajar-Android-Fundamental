package com.example.submissionwahyu.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
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

        setFavoriteUI()
        setFavoriteActionBar()
    }

    private fun setFavoriteUI() {
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
            Glide.with(this@ListFavoriteActivity)
                .load(
                    ContextCompat.getDrawable(
                        this@ListFavoriteActivity,
                        R.drawable.empty
                    )
                )
                .into(ivPlaceholder)


            rvUser.layoutManager = LinearLayoutManager(this@ListFavoriteActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this){ userFavorite ->
            if (userFavorite != null && userFavorite.isNotEmpty()){
                val listFavoriteUser = mapListUser(userFavorite)
                adapter.setList(listFavoriteUser)
                loadingState(true)
            } else{
                loadingState(false)
            }
        }
    }

    private fun setFavoriteActionBar() {
        val actionBar = supportActionBar
        actionBar!!.setTitle(R.string.list_favorite)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    private fun loadingState(state: Boolean) {
        binding.apply {
            if(state){
                rvUser.visibility = View.VISIBLE
                ivPlaceholder.visibility = View.GONE
                tvPlaceholder.visibility = View.GONE
            }else{
                rvUser.visibility = View.GONE
                ivPlaceholder.visibility = View.VISIBLE
                tvPlaceholder.visibility = View.VISIBLE
            }
        }
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