package com.firebaseapp.mavenuptodate.mavenme.mavenSearch

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.firebaseapp.mavenuptodate.mavenme.R
import com.firebaseapp.mavenuptodate.mavenme.base.BaseProgressActivity
import kotlinx.android.synthetic.main.activity_base_progress.*
import kotlinx.android.synthetic.main.activity_maven_search.*
import java.util.*


class MavenSearchActivity : BaseProgressActivity(), MavenSearchContract.View {

    private val presenter = MavenSearchPresenter()
    private var timer: Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maven_search)

        setSupportActionBar(toolbar)
        supportActionBar?.setTitle(R.string.title_activity_maven_search)
        setupView()
        presenter.attach(this)
    }

    private fun setupView() {
        fab.visibility = GONE
        rcvSearchResults.adapter = MavenSearchAdapter(presenter.searchResults) {
            Toast.makeText(this, it.id, Toast.LENGTH_SHORT).show()
        }

        edtSearch.requestFocus()
        edtSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (timer != null) {
                    timer?.cancel()
                    timer = null
                }
                timer = Timer()
                timer?.schedule(object : TimerTask() {
                    override fun run() {
                        Handler(Looper.getMainLooper()).post { presenter.mavenSearch(s.toString()) }
                    }
                }, 650)
            }
        })

        imbClearSearch.setOnClickListener {
            edtSearch.setText("")
            edtSearch.requestFocus()
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(edtSearch, InputMethodManager.SHOW_IMPLICIT)
            rcvSuggestions.visibility = GONE
        }
    }

    override fun setProgress(active: Boolean) {
        if (active) showProgress() else hideProgress()
    }

    override fun showSearchResult() {
        rcvSuggestions.visibility = GONE
        rcvSearchResults.adapter.notifyDataSetChanged()
    }

    override fun showSuggestions(suggestions: ArrayList<String>) {
        rcvSuggestions.visibility = VISIBLE
        rcvSuggestions.adapter = MavenSuggestionAdapter(suggestions) {
            edtSearch.setText(it)
            edtSearch.setSelection(it.length)
            if (currentFocus != null) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
            }
            if (timer != null) {
                timer?.cancel()
                timer = null
            }
            presenter.mavenSearch(it)
            rcvSuggestions.visibility = GONE
        }
    }
}
