package com.example.submission1fundamental

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: GithubUserAdapter
    private lateinit var usernameArray: Array<String>
    private lateinit var nameArray : Array<String>
    private lateinit var locationArray: Array<String>
    private lateinit var repositoryArray: Array<String>
    private lateinit var companyArray: Array<String>
    private lateinit var followerArray: Array<String>
    private lateinit var followingArray: Array<String>
    private lateinit var avatarArray: TypedArray

    private var gitUsers = arrayListOf<GithubUserData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = GithubUserAdapter(this)
        listView.divider = null
        listView.adapter = adapter

        resourceItem()
        positionItem()

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val moveDetailUser = GithubUserData(
                    gitUsers[position].Username,
                    gitUsers[position].Name,
                    gitUsers[position].Location,
                    gitUsers[position].Repository,
                    gitUsers[position].Company,
                    gitUsers[position].Follower,
                    gitUsers[position].Following,
                    gitUsers[position].Avatar
                )

                val moveDetailActivity = Intent(this@MainActivity, DetailUser::class.java)
                moveDetailActivity.putExtra(DetailUser.DETAIL_USER, moveDetailUser)
                startActivity(moveDetailActivity)
            }
    }

    private fun resourceItem() {
        usernameArray = resources.getStringArray(R.array.username)
        nameArray = resources.getStringArray(R.array.name)
        locationArray = resources.getStringArray(R.array.location)
        repositoryArray = resources.getStringArray(R.array.repository)
        companyArray = resources.getStringArray(R.array.company)
        followerArray = resources.getStringArray(R.array.followers)
        followingArray = resources.getStringArray(R.array.following)
        avatarArray = resources.obtainTypedArray(R.array.avatar)
    }

    private fun positionItem() {
        for (position in nameArray.indices) {
            val gitUser = GithubUserData(
                usernameArray[position],
                nameArray[position],
                locationArray[position],
                repositoryArray[position],
                companyArray[position],
                followerArray[position],
                followingArray[position],
                avatarArray.getResourceId(position, -1),
            )
            gitUsers.add(gitUser)
        }
        adapter.gitUsers = gitUsers
    }
}