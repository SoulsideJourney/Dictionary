package ru.soulsidejourney.dictionary

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import ru.soulsidejourney.dictionary.databinding.ActivityCreatePairBinding
import ru.soulsidejourney.dictionary.databinding.ActivityEditPairBinding
import ru.soulsidejourney.dictionary.db.DictionaryDb
import ru.soulsidejourney.dictionary.db.DictionaryDbTable


class CreatePairActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCreatePairBinding
    private val TAG = CreatePairActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePairBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_create_pair)
    }

    fun storePair(v: View) {
        Log.d(TAG, "Функция нажатия на кнопочку")
        if(binding.etFirstWord.isBlank() || binding.etSecondWord.isBlank()) {
            Log.d(TAG, "Пара слов не сохранена: не все поля заполнены")
            displayErrorMessage("Нужно заполнить все поля")
            return
        }

        //Сохраняем пару слов в БД
        val firstWord = binding.etFirstWord.text.toString()
        val secondWord = binding.etSecondWord.text.toString()
        val pair = ru.soulsidejourney.dictionary.data.Pair(null, firstWord, secondWord)

        val id = DictionaryDbTable(this).store(pair)

        if (id == -1L){
            displayErrorMessage("Ошибка")
        } else {
            //Вываливаемся обратно в главное меню
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //tv_error.visibility = View.INVISIBLE
    }

    private fun displayErrorMessage(message: String) {
        binding.tvError.text = message
        binding.tvError.visibility = View.VISIBLE
    }
}

private fun EditText.isBlank() = this.text.isBlank()