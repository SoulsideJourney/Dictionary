package ru.soulsidejourney.dictionary

import android.content.Intent
import android.os.Bundle
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_pair.*
import ru.soulsidejourney.dictionary.db.DictionaryDb
import ru.soulsidejourney.dictionary.db.DictionaryDbTable


class CreatePairActivity : AppCompatActivity() {
    private val TAG = CreatePairActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_pair)
    }

    fun storePair(v: View) {
        Log.d(TAG, "Функция нажатия на кнопочку")
        if(et_first_word.isBlank() || et_second_word.isBlank()) {
            Log.d(TAG, "Пара слов не сохранена: не все поля заполнены")
            displayErrorMessage("Нужно заполнить все поля")
            return
        }

        //Сохраняем пару слов в БД
        val firstWord = et_first_word.text.toString()
        val secondWord = et_second_word.text.toString()
        val pair = ru.soulsidejourney.dictionary.data.Pair(firstWord, secondWord)

        val id = DictionaryDbTable(this).store(pair)

        if (id == -1L){
            displayErrorMessage("Херня какая-то")
        } else {
            //Вываливаемся обратно в главное меню
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        //tv_error.visibility = View.INVISIBLE
    }

    private fun displayErrorMessage(message: String) {
        tv_error.text = message
        tv_error.visibility = View.VISIBLE
    }
}

private fun EditText.isBlank() = this.text.isBlank()