package com.example.submission1fundamental

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_detail_user.*
import com.bumptech.glide.Glide

class DetailUser : AppCompatActivity() {

    private lateinit var content: String

    companion object {
        const val DETAIL_USER = "detail_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)

        supportActionBar?.title = "Detail User"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val users = intent.getParcelableExtra<GithubUserData>(DETAIL_USER)
        //val users = intent.getParcelableExtra(DETAIL_USER) as GithubUserData
        //users?.Avatar?.let { img_photo.setImageResource(it) }
        val image = users?.Avatar
        Glide.with(this).load(image).into(img_photo)

        tv_name_detail.text = users?.Name
        tv_username_detail.text = "@" + users?.Username
        tv_repo_follow.text = users?.Repository + " Repository " + users?.Follower + " Follower " + users?.Following + " Following"
        tv_location.text = "Location: " + users?.Location
        tv_company_detail.text = "Company: " + users?.Company

        content = "${users?.Name.toString()}\n" +
                "${users?.Username.toString()}\n" +
                "${users?.Repository.toString()}\n" +
                "${users?.Follower.toString()}\n" +
                "${users?.Following.toString()}\n" +
                "${users?.Location.toString()}\n" +
                "${users?.Company.toString()}\n"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.share, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bar_share -> {
                val shareUser = "Github User:\n$content"
                val shareIntent = Intent(Intent.ACTION_SEND)
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareUser)
                shareIntent.type = "text/html"
                startActivity(Intent.createChooser(shareIntent, "Share using"))
            }
        }
        return super.onOptionsItemSelected(item)
    }
}