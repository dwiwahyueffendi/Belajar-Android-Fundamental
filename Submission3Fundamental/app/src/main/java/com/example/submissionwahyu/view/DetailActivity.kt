package com.example.submissionwahyu.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.submissionwahyu.R
import com.example.submissionwahyu.adapter.SectionPagerAdapter
import com.example.submissionwahyu.databinding.ActivityDetailBinding
import com.example.submissionwahyu.viewmodel.DetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_PHOTO_USER = "extra_photo_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val photoUser = intent.getStringExtra(EXTRA_PHOTO_USER)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        username?.let { viewModel.setUserDetail(it) }
        viewModel.getUserDetail().observe(this, {

            binding.apply {
                tvName.text = it.name
                tvUsername.text = it.username
                tvCompany.text = it.company
                tvLocation.text = it.location
                tvFollowers.text = StringBuilder("${it.followers}")
                tvFollowing.text = StringBuilder("${it.following}")
                Glide.with(this@DetailActivity)
                    .load(it.photoUser)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(ivProfile)
            }
        })

        binding.apply {
            var _isChecked = false
            CoroutineScope(Dispatchers.IO).launch {
                val count = viewModel.checkFavoriteUser(id)
                withContext(Dispatchers.Main){
                    if (count != null){
                        if (0 < count){
                            btnFavorite.isChecked = true
                            _isChecked = true
                        } else{
                            btnFavorite.isChecked = false
                            _isChecked = false
                        }
                    }
                }
            }

            btnFavorite.setOnClickListener{
                _isChecked = !_isChecked
                if (_isChecked){
                    username?.let {
                            it1 -> photoUser?.let {
                            it2 -> viewModel.addFavoriteUser(it1, id, it2)
                    } }
                    Toast.makeText(this@DetailActivity, R.string.toggle_favorite, Toast.LENGTH_SHORT).show()
                } else{
                    viewModel.deleteFavoriteUser(id)
                    Toast.makeText(this@DetailActivity, R.string.toggle_unfavorite, Toast.LENGTH_SHORT).show()
                }
                btnFavorite.isChecked = _isChecked
            }
        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }

        val actionBar = supportActionBar
        actionBar!!.setTitle(R.string.profile)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_list_favorite ->{
                Intent(this, ListFavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.Setting ->{
                Intent(this, SettingActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}