package com.example.movielibrary

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat

class MainActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_MODEL = "MOVIE_MODEL"
        const val RESULT_DETAIL_CODE = 123

        private const val SELECTED_MOVIE = "SELECTED_MOVIE"
    }

    private var selectedMovieIndex: Int? = null

    private val movieModels: Array<MovieModel> by lazy {
        arrayOf(
                MovieModel(title = getString(R.string.firstMovieTitle), description = getString(R.string.firstMovieDescription), imgSource = R.drawable.first),
                MovieModel(title = getString(R.string.secondMovieTitle), description = getString(R.string.secondMovieDescription), imgSource = R.drawable.second),
                MovieModel(title = getString(R.string.thirdMovieTitle), description = getString(R.string.thirdMovieDescription), imgSource = R.drawable.third))
    }

    private val imageViews: Array<ImageView> by lazy {
        arrayOf(findViewById(R.id.firstImg), findViewById(R.id.secondImg), findViewById(R.id.thirdImg))
    }


    private val textViews: Array<TextView> by lazy {
        arrayOf(findViewById(R.id.firstTitle), findViewById(R.id.secondTitle), findViewById(R.id.thirdTitle))
    }

    private val detailButtons: Array<Button> by lazy {
        arrayOf(findViewById(R.id.firstDetailBtn), findViewById(R.id.secondDetailBtn), findViewById(R.id.thirdDetailBtn))
    }

    private val inviteButton: Button by lazy {
        findViewById(R.id.invitationBtn)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setImageViews()
        setTextViews()
        setButtons()

        savedInstanceState?.let {
            selectedMovieIndex = it.getInt(SELECTED_MOVIE)
            updateTextViewColors()
        }
    }

    private fun setImageViews() {
        imageViews.forEachIndexed { index, imgView ->
            try {
                imgView.setImageResource(movieModels[index].imgSource)
            } catch (e: IndexOutOfBoundsException) {
                print(e.localizedMessage)
            }
        }
    }

    private fun setTextViews() {
        textViews.forEachIndexed { index, txtView ->
            try {
                txtView.text = movieModels[index].title
            } catch (e: IndexOutOfBoundsException) {
                print(e.localizedMessage)
            }
        }
    }

    private fun setButtons() {
        detailButtons.forEachIndexed { i, btn ->
            btn.setOnClickListener {
                onClickAction(i)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        selectedMovieIndex?.let {
            outState.putInt(SELECTED_MOVIE, it)
        }

        inviteButton.setOnClickListener {
            val smsIntent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:"))
            smsIntent.putExtra("sms_body", getString(R.string.invitation))
            startActivity(smsIntent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RESULT_DETAIL_CODE) {
            data?.getParcelableExtra<FeedBackModel>(DetailActivity.FEEDBACK_VAL)?.let {
                Log.d("MainActivity", "like: ${it.like}, comment: ${it.comment}")
            }
        }
    }

    private fun onClickAction(filmIndex: Int) {
        selectedMovieIndex = filmIndex
        updateTextViewColors()

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(MOVIE_MODEL, movieModels[filmIndex])
        startActivityForResult(intent, RESULT_DETAIL_CODE)
    }

    private fun updateTextViewColors() {
        textViews.forEachIndexed { index, textView ->
            textView.setTextColor(ResourcesCompat.getColor(resources, if (index == selectedMovieIndex)  R.color.selectedTextColor else R.color.textColor, null))
        }
    }
}
