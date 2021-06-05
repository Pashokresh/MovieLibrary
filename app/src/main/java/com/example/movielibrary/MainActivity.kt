package com.example.movielibrary

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), MoviesListFragment.ButtonsClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onMovieList()
    }

    private fun onMovieList() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, MoviesListFragment(), MoviesListFragment.TAG)
            .commit()
    }

    override fun onMovieClick(movieItem: MovieItem) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, MovieDetailsFragment.newInstance(movieItem), MovieDetailsFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    override fun onFavoritesClick() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentContainer, FavoritesFragment(), FavoritesFragment.TAG)
            .addToBackStack(null)
            .commit()
    }

    override fun onInviteClick() {
        val smsIntent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:"))
        smsIntent.putExtra("sms_body", getString(R.string.invitation))
        startActivity(smsIntent)
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.findFragmentById(R.id.fragmentContainer)?.let {
                if (it.isVisible && it is NavigationListener) {
                    it.onBackPressed()
                }
            }
            supportFragmentManager.popBackStack()
        } else {
            AlertDialog.Builder(this)
                .setTitle(R.string.exit)
                .setMessage(R.string.exitMessage)
                .setPositiveButton(R.string.yes) { _, _ -> super.onBackPressed() }
                .setNegativeButton(R.string.no) { _, _ -> }
                .create()
                .show()
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

    interface NavigationListener {
        fun onBackPressed()
    }
}
