package com.example.submissionwahyu.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.submissionwahyu.R
import com.example.submissionwahyu.databinding.ActivityDetailBinding
import com.example.submissionwahyu.viewmodel.DetailViewModel
import kotlinx.coroutines.*
import com.shashank.sony.fancytoastlib.FancyToast

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

        @StringRes
        private val TAB_TITLES = listOf(
            R.string.followers,
            R.string.following
        )
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
                            btnFavorite.isChecked = true
                            state = true
                        } else {
                            btnFavorite.isChecked = false
                            state = false
                        }
                    }
                }
            }

            btnFavorite.setOnClickListener{
                if (!state){
                    username?.let { it1 ->
                        photoUser?.let { it2 ->
                            detailViewModel.addFavoriteUser(it1, id, it2)
                        }
                    }
                    FancyToast.makeText(
                        this@DetailActivity, resources.getString(R.string.toggle_favorite),
                        FancyToast.LENGTH_SHORT, FancyToast.SUCCESS, false).show()
                } else{
                    detailViewModel.deleteFavoriteUser(id)
                    FancyToast.makeText(
                        this@DetailActivity, resources.getString(R.string.toggle_unfavorite),
                        FancyToast.LENGTH_SHORT, FancyToast.ERROR, false).show()
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

        val sectionPager = SectionPagerAdapter(supportFragmentManager, mBundle)
        binding.apply {
            viewPager.adapter = sectionPager
            tabs.setupWithViewPager(viewPager)
        }
    }

    inner class SectionPagerAdapter(
        fm: FragmentManager,
        data: Bundle
    ): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private var fragmentBundle: Bundle = data

        override fun getCount(): Int = TAB_TITLES.size

        override fun getItem(position: Int): Fragment {
            var fragment: Fragment? = null
            when (position) {
                0 -> fragment = FollowersFragment()
                1 -> fragment = FollowingFragment()
            }
            fragment?.arguments = this.fragmentBundle
            return fragment as Fragment
        }

        override fun getPageTitle(position: Int): CharSequence {
            return resources.getString(TAB_TITLES[position])
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