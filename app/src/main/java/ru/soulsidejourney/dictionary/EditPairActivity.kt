package ru.soulsidejourney.dictionary

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import ru.soulsidejourney.dictionary.data.Pair
import ru.soulsidejourney.dictionary.databinding.ActivityEditPairBinding
import ru.soulsidejourney.dictionary.db.DictionaryDbTable
import java.lang.IllegalArgumentException

class EditPairActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditPairBinding
    private val TAG = CreatePairActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditPairBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_edit_pair)

        val pair = intent?.getParcelableExtra<Pair>(KEY)
            ?: throw IllegalArgumentException("Missing argument")

        findViewById<TextView>(R.id.tv_pair_id).text = pair.id
        findViewById<EditText>(R.id.et_first_word).setText(pair.firstWord)
        findViewById<EditText>(R.id.et_second_word).setText(pair.secondWord)
    }

    fun editPair(view: View) {
        Log.d(TAG, "Попытка перезаписи пары")

        if(binding.etFirstWord.isBlank() || binding.etSecondWord.isBlank()) {
            Log.d(TAG, "Пара слов не сохранена: не все поля заполнены")
            displayErrorMessage("Нужно заполнить все поля")
            return
        }

        //Сохраняем пару слов в БД
        val pairId = binding.tvPairId.text.toString()
        val firstWord = binding.etFirstWord.text.toString()
        val secondWord = binding.etSecondWord.text.toString()
        val pair = ru.soulsidejourney.dictionary.data.Pair(pairId, firstWord, secondWord)

        val id = DictionaryDbTable(this).edit(pair)

        if (id == -1){
            displayErrorMessage("Ошибка редактирования")
        } else {
            //Вываливаемся обратно в главное меню
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun deletePair(view: View) {
        Log.d(TAG, "Попытка удаления пары")

        //Удаление пары слов из БД
        val pairId = binding.tvPairId.text.toString()

        val id = DictionaryDbTable(this).delete(pairId)

        if (id == -1){
            displayErrorMessage("Ошибка удаления")
        } else {
            //Вываливаемся обратно в главное меню
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun displayErrorMessage(message: String) {
        binding.tvError.text = message
        binding.tvError.visibility = View.VISIBLE
    }

    companion object {
        private const val KEY = "KEY"

        fun createIntent(context: Context, pair: Pair): Intent {
            val intent = Intent(context, EditPairActivity::class.java)
            intent.putExtra(KEY, pair)
            return intent
        }
    }

}

private fun EditText.isBlank() = this.text.isBlank()