package com.example.bible.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.bible.R
import com.example.bible.ui.books.BookFragment
import com.example.bible.utils.SAVE_PAGE_FALSE
import com.example.bible.utils.SAVE_PAGE_TRUE
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_books,
                R.id.nav_search,
                R.id.reading_page
            )
        )
//        setupActionBarWithNavController(navController, appBarConfiguration)

        bottom_navigation.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         this.menuInflater.inflate(R.menu.reade_book_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val bundle = Bundle()
        bundle.putBoolean("savePage", SAVE_PAGE_TRUE)

        navController.navigate(R.id.action_reading_page_to_nav_books,bundle)
        return true
    }
}