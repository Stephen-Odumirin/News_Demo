package com.stdev.newsdemo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.stdev.newsdemo.data.model.Article
import com.stdev.newsdemo.databinding.FragmentInfoBinding
import com.stdev.newsdemo.presentation.viewmodel.NewsViewModel
import javax.inject.Inject

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

        bindng.infoWebView.apply {
            webViewClient = WebViewClient()
            if(article.url != null){
                loadUrl(article.url!!)
            }

        }

        bindng.infoWebSave.setOnClickListener {
            viewModel.saveArticle(article)
            Snackbar.make(view,"Save Successfully!",Snackbar.LENGTH_SHORT).show()
        }



        Toast.makeText(requireContext(),"$article",Toast.LENGTH_SHORT).show()

    }

}