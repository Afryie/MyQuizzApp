package com.example.qapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.BaseColumns
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.qapp.databinding.ActivityDeleteBinding

class DeleteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteBinding
    private var askedQuestion =""
    private var delete: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.deleteId.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                askedQuestion=binding.deleteId.text.toString()

                val dbHelper = DataBaseHelper(applicationContext)
                val db = dbHelper.writableDatabase
                val cursor = db.query(TableInfo.TABLE_NAME, null, BaseColumns._ID + "=?", arrayOf(askedQuestion.toString()), null, null, null)


                if (cursor.moveToFirst()) {

                    binding.deleteQuestion.setText( cursor.getString(1))
                    binding.deleteA1.setText(cursor.getString(2))
                    binding.deleteA2.setText(cursor.getString(3))
                    binding.deleteA3.setText(cursor.getString(4))
                    binding.deleteA4.setText(cursor.getString(5))
                    binding.deleteButton.setText("Usuń")
                    delete=true
                }
                else
                {
                    binding.deleteQuestion.setText("Brak pytania w bazie")
                    binding.deleteA1.setText("")
                    binding.deleteA2.setText("")
                    binding.deleteA3.setText("")
                    binding.deleteA4.setText("")
                    delete=false
                }

            }

            override fun afterTextChanged(p0: Editable?) {

            }


        })

        binding.deleteButton.setOnClickListener{
            if(delete==true){
                val dbHelper = DataBaseHelper(applicationContext)
                val db = dbHelper.writableDatabase
                db.delete(TableInfo.TABLE_NAME,BaseColumns._ID + "=?",arrayOf(askedQuestion.toString()))
                Toast.makeText(this,"Pytanie zostało usunięte!",Toast.LENGTH_SHORT).show()
                binding.deleteQuestion.setText("Brak pytania w bazie")
                binding.deleteA1.setText("")
                binding.deleteA2.setText("")
                binding.deleteA3.setText("")
                binding.deleteA4.setText("")
                delete=false

            }
            else{
                Toast.makeText(this,"Błąd. Podaj poprawne ID",Toast.LENGTH_SHORT).show()
            }

        }








    }
}