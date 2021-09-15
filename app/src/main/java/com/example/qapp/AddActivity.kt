package com.example.qapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.qapp.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase
        val save_info_Toast = Toast.makeText(applicationContext,"Dodano Pytanie!", Toast.LENGTH_SHORT)

        binding.addSubmit.setOnClickListener{
            val pytanie = binding.addQuestion.text.toString()
            val A1 = binding.addOne.text.toString()
            val A2 = binding.addTwo.text.toString()
            val A3 = binding.addThree.text.toString()
            val A4 = binding.addFour.text.toString()
            val poprawna = binding.addCorrect.text.toString()


            if(correctData(pytanie,A1,A2,A3,A4,poprawna)) {







                val value = ContentValues()
                value.put("pytanie", pytanie)
                value.put("Odp1", A1)
                value.put("Odp2", A2)
                value.put("Odp3", A3)
                value.put("Odp4", A4)
                value.put("numer", poprawna)


                db.insertOrThrow(TableInfo.TABLE_NAME, null, value)
                save_info_Toast.show()

                binding.addQuestion.setText("")
                binding.addOne.setText("")
                binding.addTwo.setText("")
                binding.addThree.setText("")
                binding.addFour.setText("")
                binding.addCorrect.setText("")


            }

            }
            }

            private fun correctData(pytanie: String,A1: String,A2: String,A3: String,A4: String,poprawna: String) : Boolean{
                if(!pytanie.isNullOrEmpty() && !A1.isNullOrEmpty() && !A2.isNullOrEmpty() && !A3.isNullOrEmpty() && !A4.isNullOrEmpty() && !poprawna.isNullOrEmpty())
                {
                    if(poprawna.toInt() >4 || poprawna.toInt() <1){
                        Toast.makeText(applicationContext, "Odpowiedź musi być z zakresu 1-4 !",Toast.LENGTH_SHORT).show()
                        return false
                    }
                    else{
                        return true
                    }
                }
                else{
                    Toast.makeText(applicationContext, "Uzupełnij wszystkie pola!",Toast.LENGTH_SHORT).show()
                    return false
                }

            }

    }