package com.stdev.newsdemo.presentation.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.stdev.newsdemo.R
import com.stdev.newsdemo.databinding.FragmentSavedBinding
import com.stdev.newsdemo.presentation.adapter.SavedNewsAdapter
import com.stdev.newsdemo.presentation.viewmodel.NewsViewModel

class SavedFragment : Fragment() {

    private lateinit var binding: FragmentSavedBinding
    private lateinit var viewModel: NewsViewModel
    private lateinit var newsAdapter: SavedNewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSavedBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel
        newsAdapter = (activity as MainActivity).savedNewsAdapter
        newsAdapter.setOnItemClickListener {
            if (it!=null){
                val action = SavedFragmentDirections.actionSavedFragmentToInfoFragment(article = it)
                findNavController().navigate(action)
            }
//            try {
//                val action = it?.let { it1 ->
//                    SavedFragmentDirections.actionSavedFragmentToInfoFragment(
//                        it1
//                    )
//                }
//                if (action != null) {
//                    findNavController().navigate(action)
//                }
//            }catch (exception : Exception){
//                Log.i("NewsFragment",exception.localizedMessage)
////                Toast.makeText(requireContext(),"${exception.localizedMessage}",Toast.LENGTH_SHORT).show()
//            }

        }

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticles(article)
                Snackbar.make(view,"Deleted",Snackbar.LENGTH_LONG).apply {
                    setAction("Undo"){
                        viewModel.saveArticle(article)
                    }
                    show()
                }
            }

        }

        binding.savedBack.setOnClickListener {
            findNavController().navigateUp()
        }

        initRecyclerView()
        viewModel.getSavedNews().observe(viewLifecycleOwner){
            newsAdapter.differ.submitList(it)
        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.savedRecyclerView)

    }

    private fun initRecyclerView() {
        binding.savedRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,true)
            adapter = newsAdapter

        }
    }


}














