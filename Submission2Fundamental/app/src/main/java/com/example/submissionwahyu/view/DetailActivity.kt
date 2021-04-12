package com.example.submissionwahyu.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.submissionwahyu.R
import com.example.submissionwahyu.adapter.SectionPagerAdapter
import com.example.submissionwahyu.databinding.ActivityDetailBinding
import com.example.submissionwahyu.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        username?.let { viewModel.setUserDetail(it) }
        viewModel.getUserDetail().observe(this, {

            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.username
                    if (it.company == null){
                        tvCompany.visibility = View.GONE
                    }else{
                        tvCompany.text = it.company
                    }
                    if (it.location == null){
                        tvLocation.visibility = View.GONE
                    }else{
                        tvLocation.text = it.location
                    }
                    tvFollowers.text = StringBuilder("${it.followers}")
                    tvFollowing.text = StringBuilder("${it.following}")
                    Glide.with(this@DetailActivity)
                        .load(it.photoUser)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(ivProfile)
                }
            }
        })

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
        if (item.itemId == R.id.change_language) {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        return super.onOptionsItemSelected(item)
    }
}