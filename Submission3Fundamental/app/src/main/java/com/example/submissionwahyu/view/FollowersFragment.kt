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
    private lateinit var viewModel: FollowersViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argmns = arguments
        username = argmns?.getString(DetailActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)

        setFollowersUI()
    }

    private fun setFollowersUI() {
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvUser.setHasFixedSize(true)
            rvUser.layoutManager = LinearLayoutManager(activity)
            rvUser.adapter = adapter
        }

        viewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)

        viewModel.setListFollowers(username)

    /*viewModel.getListFollowers().observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.setList(it)
                loadingState(false)
            }
        })*/
        viewModel.getListFollowers().observe(viewLifecycleOwner){ userFollowers ->
            if (userFollowers != null && userFollowers.isNotEmpty()){
                adapter.setList(userFollowers)
                loadingState(true)
            } else{
                loadingState(false)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
}