package com.example.pparemusnoc.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.pparemusnoc.R
import com.example.pparemusnoc.adapter.UserAdapter
import com.example.pparemusnoc.databinding.ActivityMainBinding
import com.example.pparemusnoc.viewmodel.FavoriteViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: UserAdapter
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFavoriteUI()
        setFavoriteActionBar()
    }

    private fun setFavoriteUI() {
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        binding.apply {
            Glide.with(this@MainActivity)
                .load(
                    ContextCompat.getDrawable(
                        this@MainActivity,
                        R.drawable.emptyfavorite
                    )
                )
                .into(ivPlaceholderFavorite)

            rvUser.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }

        favoriteViewModel.setFavoriteUser(this)
        favoriteViewModel.getFavoriteUser().observe(this){ userFavorite ->
            if (userFavorite != null && userFavorite.isNotEmpty()){
                adapter.setList(userFavorite)
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
                ivPlaceholderFavorite.visibility = View.GONE
                tvPlaceholderFavorite.visibility = View.GONE
            }else{
                rvUser.visibility = View.GONE
                ivPlaceholderFavorite.visibility = View.VISIBLE
                tvPlaceholderFavorite.visibility = View.VISIBLE
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}