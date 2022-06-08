package com.stdev.newsdemo.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.stdev.newsdemo.R
import com.stdev.newsdemo.data.model.Article
import com.stdev.newsdemo.databinding.FragmentInfoBinding
import com.stdev.newsdemo.presentation.viewmodel.NewsViewModel

class InfoFragment : Fragment() {

    private lateinit var article : Article
    private lateinit var bindng : FragmentInfoBinding
    private lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        article = InfoFragmentArgs.fromBundle(requireArguments()).article
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindng = FragmentInfoBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel

        setDetails()

        bindng.infoBookmark.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view,"Save Successfully!",Snackbar.LENGTH_SHORT).show()
        }
        
        bindng.infoViewWeb.setOnClickListener {
            val action = InfoFragmentDirections.actionInfoFragmentToWebViewFragment(article)
            findNavController().navigate(action)
        }

        bindng.infoBack.setOnClickListener {
            findNavController().navigateUp()
        }

        bindng.infoShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            val shareBody = "Check out this news ${article.title}\n${article.url}"
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, article.title)
            intent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(intent, "Share Using"))
        }



    }

    private fun setDetails() {
        bindng.infoTitle.text = article.title
        bindng.infoDesc.text = article.description
        bindng.infoContent.text = article.content
        Glide.with(bindng.infoImageView)
            .load(article.urlToImage)
            .into(bindng.infoImageView)


    }

}