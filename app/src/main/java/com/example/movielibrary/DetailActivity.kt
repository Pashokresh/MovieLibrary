package com.example.movielibrary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    companion object {
        const val FEEDBACK_VAL = "FEEDBACK_VAL"
    }

    private val imageView: ImageView by lazy {
        findViewById(R.id.movieImg)
    }

    private val titleView: TextView by lazy {
        findViewById(R.id.movieTitle)
    }

    private val descriptionView: TextView by lazy {
        findViewById(R.id.movieDescription)
    }

    private val likeCheckBox: CheckBox by lazy {
        findViewById(R.id.likeCheckBox)
    }

    private val commentField: EditText by lazy {
        findViewById(R.id.commentField)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        actionBar?.setDisplayHomeAsUpEnabled(true)

        val movieModel = intent.getParcelableExtra<MovieItem>(MainActivity.MOVIE_MODEL)
        movieModel?.let {
            imageView.setImageResource(it.imgSource)
            titleView.text = it.title
            descriptionView.text = it.description
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            setResult(MainActivity.RESULT_DETAIL_CODE,
                Intent().putExtra(FEEDBACK_VAL, FeedBackModel(likeCheckBox.isChecked, commentField.text.toString())))
            finish()

            return true
        }
        return super.onOptionsItemSelected(item)
    }
}