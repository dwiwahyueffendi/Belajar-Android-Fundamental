package com.example.submissionwahyu.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import kotlinx.coroutines.*

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var share: String
    private lateinit var userName: String
    private var state: Boolean = false

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_PHOTO_USER = "extra_photo_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setDetailActionBar()
        setDetailUI()
    }

    private fun setDetailActionBar() {
        val actionBar = supportActionBar
        actionBar!!.setTitle(R.string.profile)
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun setDetailUI() {
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val mBundle = Bundle()
        mBundle.putString(EXTRA_USERNAME, username)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val photoUser = intent.getStringExtra(EXTRA_PHOTO_USER)

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        username?.let { detailViewModel.setUserDetail(it, this@DetailActivity) }
        detailViewModel.getUserDetail().observe(this, {
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

                share = StringBuilder(" ${it.name}").append("\nName: ${it.username}").toString()
                userName = it.username
            }
        })

        binding.apply {

            //IO = db, network, file IO
            GlobalScope.launch(Dispatchers.IO) {
                val count = detailViewModel.checkFavoriteUser(id)
                //Main = UI
                withContext(Dispatchers.Main){
                    if (count != null){
                        if (0 < count){
                            //btnFavorite.isChecked = false
                            //state = false
                            btnFavorite.isChecked = true
                            state = true
                        } else{
                            //btnFavorite.isChecked = true
                            //state = true
                            btnFavorite.isChecked = false
                            state = false
                        }
                    }
                }
            }

            btnFavorite.setOnClickListener{
                //state = !state
                if (!state){
                    username?.let { it1 ->
                        photoUser?.let { it2 ->
                            detailViewModel.addFavoriteUser(it1, id, it2)
                        } }
                    Toast.makeText(this@DetailActivity, R.string.toggle_favorite, Toast.LENGTH_SHORT).show()
                } else{
                    detailViewModel.deleteFavoriteUser(id)
                    Toast.makeText(this@DetailActivity, R.string.toggle_unfavorite, Toast.LENGTH_SHORT).show()
                }
                btnFavorite.isChecked = !state
            }

            btnShare.setOnClickListener {
                val shareUser = "Username: $share"
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareUser)
                shareIntent.type = "text/html"
                startActivity(Intent.createChooser(shareIntent, "Share using"))
            }

            btnWeb.setOnClickListener {
                val url = "https://github.com/$userName"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }

        }

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, mBundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }
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