package ru.soulsidejourney.dictionary

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import ru.soulsidejourney.dictionary.data.Pair
import ru.soulsidejourney.dictionary.databinding.ActivityMainBinding
import ru.soulsidejourney.dictionary.db.DictionaryDbTable
import ru.soulsidejourney.dictionary.db.PairEntry.FIRST_WORD_COL
import ru.soulsidejourney.dictionary.db.PairEntry.SECOND_WORD_COL
import ru.soulsidejourney.dictionary.db.PairEntry._ID

class MainActivity : AppCompatActivity() {
    private val TAG = DictionaryDbTable::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dictionary = DictionaryDbTable(this).readAllPairs("$_ID ASC")
        setAdapter(dictionary)

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

        //test
        if (item.itemId == R.id.sort_by_first_word) {
            Log.d(TAG, "Попытка сортировки списка")
            val dictionary = DictionaryDbTable(this).readAllPairs("$FIRST_WORD_COL ASC")
            setAdapter(dictionary)
        }

        if (item.itemId == R.id.sort_by_second_word) {
            Log.d(TAG, "Попытка сортировки списка")
            val dictionary = DictionaryDbTable(this).readAllPairs("$SECOND_WORD_COL ASC")
            setAdapter(dictionary)
        }
        return true
    }

    private fun setAdapter(dictionary: List<Pair>) {
        val adapter = DictionaryAdapter(this, dictionary) { position ->
            val pairs = dictionary[position]
            val intent = EditPairActivity.createIntent(this, pairs)
            startActivity(intent)
        }

        val list = findViewById<RecyclerView>(R.id.list)
        list.adapter = adapter
    }


    private fun switchTo(c: Class<*>) {
        val intent = Intent(this, c)
        startActivity(intent)
    }
}