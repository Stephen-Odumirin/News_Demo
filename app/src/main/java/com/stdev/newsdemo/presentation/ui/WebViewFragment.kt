package com.stdev.newsdemo.presentation.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.stdev.newsdemo.R
import com.stdev.newsdemo.data.model.Article
import com.stdev.newsdemo.databinding.FragmentWebViewBinding
import com.stdev.newsdemo.presentation.viewmodel.NewsViewModel


class WebViewFragment : Fragment() {

    private lateinit var binding : FragmentWebViewBinding
    private lateinit var article: Article
    private lateinit var viewModel : NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        article = WebViewFragmentArgs.fromBundle(requireArguments()).article

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_web_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentWebViewBinding.bind(view)
        viewModel = (activity as MainActivity).viewModel

        binding.webView.apply {
            webViewClient = WebViewClient()
            if(article.url != null && !article.url.isNullOrEmpty()){
                loadUrl(article.url!!)
            }
        }

        binding.webBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.webBookmark.setOnClickListener {
            viewModel.saveArticle(article)
        }

        binding.webShare.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            val shareBody = "Check out this news ${article.title}\n${article.url}"
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, article.title)
            intent.putExtra(Intent.EXTRA_TEXT, shareBody)
            startActivity(Intent.createChooser(intent, "Share Using"))
        }

    }



}