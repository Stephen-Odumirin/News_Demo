package com.stdev.newsdemo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.SearchView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.stdev.newsdemo.data.util.Resource
import com.stdev.newsdemo.databinding.FragmentNewsBinding
import com.stdev.newsdemo.presentation.adapter.NewsAdapter
import com.stdev.newsdemo.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFragment : Fragment() {

    private lateinit var viewModel : NewsViewModel
    private lateinit var binding : FragmentNewsBinding
    private lateinit var adapter: NewsAdapter
    private var country = "us"
    private var page = 1
    private var isScrolling = false
    private var isLoading = false
    private var isLastPage = false
    private var pages = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNewsBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        adapter = (activity as MainActivity).newsAdapter
        initRecyclerView()
        viewNewsList()
        adapter.setOnItemClickListener {
            try {
                val action = it?.let { it1 ->
                    NewsFragmentDirections.actionNewsFragmentToInfoFragment(
                        it1
                    )
                }
                if (action != null) {
                    findNavController().navigate(action)
                }
            }catch (exception : Exception){
                Log.i("NewsFragment",exception.localizedMessage)
//                Toast.makeText(requireContext(),"${exception.localizedMessage}",Toast.LENGTH_SHORT).show()
            }

        }
        setSearchView()


    }

//    private val onScrollListener = object : RecyclerView.OnScrollListener() {
//        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//            super.onScrollStateChanged(recyclerView, newState)
//            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
//                isScrolling = true
//            }
//        }
//
//        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            super.onScrolled(recyclerView, dx, dy)
//            val layoutManager = binding.newsRecyclerView.layoutManager as LinearLayoutManager
//            val sizeOfTheCurrentList = layoutManager.itemCount
//            val visibleItems = layoutManager.childCount
//            val topPosition = layoutManager.findFirstVisibleItemPosition()
//
//            val hasReachedToEnd = topPosition+visibleItems >= sizeOfTheCurrentList
//            val shouldPaginate = !isLoading && !isLastPage && hasReachedToEnd && isScrolling
//            if (shouldPaginate){
//                page++
//                viewModel.getNewsHeadlines(country,page,"general")
//                isScrolling = false
//            }
//
//        }
//    }

    private fun setSearchView(){

        binding.newsSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.searchedNewsHeadlines(country,"general",page,p0.toString())
                viewSearchList()
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                MainScope().launch {
                    delay(2000)
                    viewModel.searchedNewsHeadlines(country,"general",page,p0.toString())
                    viewSearchList()
                }
                return false
            }

        })

        binding.newsSearchView.setOnCloseListener {
            initRecyclerView()
            viewNewsList()
            false
        }

    }

    private fun viewSearchList(){

        viewModel.searchedNews.observe(viewLifecycleOwner){response->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        adapter.differ.submitList(it.articles.toList())
                        pages = if (it.totalResults%20 == 0) {
                            it.totalResults/20
                        }else{
                            it.totalResults/20 +1
                        }
                        isLastPage = page == pages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity,"An error occured : $it",Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun viewNewsList(){
        viewModel.getNewsHeadlines(country,page,"general")
        viewModel.newsHeadlines.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let {
                        adapter.differ.submitList(it.articles.toList())
                        pages = if (it.totalResults%20 == 0) {
                            it.totalResults/20
                        }else{
                            it.totalResults/20 +1
                        }
                        isLastPage = page == pages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity,"An error occured : $it",Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
    }

    private fun initRecyclerView() {
        //adapter = NewsAdapter()
        binding.newsRecyclerView.adapter = adapter
        binding.newsRecyclerView.layoutManager = LinearLayoutManager(activity)
        //binding.newsRecyclerView.addOnScrollListener(this@NewsFragment.onScrollListener)

    }

    private fun showProgressBar(){
        isLoading = true
        binding.newsProgressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar(){
        isLoading = false
        binding.newsProgressBar.visibility = View.INVISIBLE
    }

}