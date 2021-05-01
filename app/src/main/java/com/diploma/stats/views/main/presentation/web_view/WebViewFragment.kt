package com.diploma.stats.views.main.presentation.web_view

import android.os.Bundle
import android.os.Handler
import com.diploma.stats.R
import com.diploma.stats.global.base.BaseFragment
import com.diploma.stats.model.web_view.Data
import kotlinx.android.synthetic.main.fragment_web_view.*

class WebViewFragment : BaseFragment() {

    private lateinit var handler1: Handler
    private lateinit var runnable: Runnable

    companion object {
        val TAG = "WebViewFragment"
        const val webResponse = "webResponse"
        fun newInstance(response: Data): WebViewFragment {
            val fragment = WebViewFragment()
            val args = Bundle()
            args.putParcelable(webResponse, response)
            fragment.arguments = args
            return fragment
        }
    }

    override val layoutRes: Int = R.layout.fragment_web_view

    override fun setUp(savedInstanceState: Bundle?) {
        val obj: Data = arguments?.getParcelable(webResponse)!!
        backToMain.setOnClickListener { requireActivity().onBackPressed() }
        helpTitle.text = obj.title
        webView.loadDataWithBaseURL("", "<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no\" name=\"viewport\">\n" +
                "\n" +
                "\n" +
                "    <style>\n" +
                "      iframe{\n" +
                "        width: 100%;\n" +
                "        min-height: 150px;\n" +
                "      }\n" +
                "      img{\n" +
                "        display: block;\n" +
                "        max-width: 100%;\n" +
                "        height: auto;\n" +
                "      }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>"+obj.html+"</body>\n" +
                "</html>", "text/html", "UTF-8", "")

        handler1 = Handler()
        runnable = Runnable {
            apply {
                showProgressBar(false)
            }
        }
        handler1.postDelayed(
            runnable, 500
        )
    }
}