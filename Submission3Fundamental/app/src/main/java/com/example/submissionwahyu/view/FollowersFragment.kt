package com.example.submissionwahyu.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionwahyu.R
import com.example.submissionwahyu.adapter.UserAdapter
import com.example.submissionwahyu.databinding.FragmentFollowBinding
import com.example.submissionwahyu.viewmodel.FollowersViewModel

class FollowersFragment : Fragment(R.layout.fragment_follow) {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var username: String
    private lateinit var adapter: UserAdapter
    private lateinit var followersViewModel: FollowersViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(DetailActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)

        setFollowersUI()
    }

    private fun setFollowersUI() {
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.setHasFixedSize(true)
            rvUser.adapter = adapter
        }

        followersViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)

        followersViewModel.setListFollowers(username)

        followersViewModel.getListFollowers().observe(viewLifecycleOwner) { userFollowers ->
            if (userFollowers != null && userFollowers.isNotEmpty()) {
                adapter.setList(userFollowers)
                loadingState(true)
            } else {
                loadingState(false)
            }
        }
    }

    private fun loadingState(state: Boolean) {
        binding.apply {
            if (state) {
                rvUser.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                ivPlaceholderFollow.visibility = View.GONE
                tvPlaceholderFollow.visibility = View.GONE
            } else {
                rvUser.visibility = View.GONE
                progressBar.visibility = View.GONE
                ivPlaceholderFollow.visibility = View.VISIBLE
                tvPlaceholderFollow.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}