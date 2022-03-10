package ru.soulsidejourney.dictionary

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import ru.soulsidejourney.dictionary.R
import ru.soulsidejourney.dictionary.data.DictionaryAdapter
import ru.soulsidejourney.dictionary.databinding.ActivityMainBinding
import ru.soulsidejourney.dictionary.db.DictionaryDbTable

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dictionary = DictionaryDbTable(this).readAllPairs()
        val adapter = DictionaryAdapter(this, dictionary) { position ->
            val pairs = dictionary[position]
            switchTo(CreatePairActivity::class.java)
        }

        val list = findViewById<RecyclerView>(R.id.list)
        list.adapter = adapter

        //val decoration = DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL)
        //decoration.setDrawable(ContextCompat.getDrawable(this, R.color.grey)!!)
        //list.addItemDecoration(decoration)
    }

    //Появление меню
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    //переключение активити при нажатии на пункт меню
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_pair) {
            switchTo(CreatePairActivity::class.java)
        }
        return true
    }



    private fun switchTo(c: Class<*>) {
        val intent = Intent(this, c)
        startActivity(intent)
    }
}