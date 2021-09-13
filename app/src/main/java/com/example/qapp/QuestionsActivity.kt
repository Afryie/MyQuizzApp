package com.example.qapp

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.provider.BaseColumns
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.qapp.databinding.ActivityQuestionsBinding

class QuestionsActivity : AppCompatActivity() {



    private var currentQuestion = 1
    private var poprawna = 0
    private var wybrana = 0
    private var QuestionCounter =0
    private var CorrectAnswers = 0
    private val begin = System.currentTimeMillis()

    private lateinit var binding: ActivityQuestionsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setQuestion()
        countQuestions()
        if(QuestionCounter == 0)
        {
            addBasicQuestions()
            setQuestion()
            countQuestions()
        }

        defaultOptionsView()



        binding.odp1.setOnClickListener{
            selectOption(1)
            wybrana=1
        }
        binding.odp2.setOnClickListener{
            selectOption(2)
            wybrana=2
        }
        binding.odp3.setOnClickListener{
            selectOption(3)
            wybrana=3
        }
        binding.odp4.setOnClickListener{
            selectOption(4)
            wybrana=4
        }


        binding.zatwierdz.setOnClickListener{

            if(currentQuestion >= QuestionCounter){
                if(wybrana==poprawna){ CorrectAnswers++ }
                wybrana=0
                val end= System.currentTimeMillis()
                val timePassed = (end.toDouble() - begin.toDouble()) / 1000
                Toast.makeText(this,"Skończyłeś Quiz! Odpowiedziałeś poprawnie na " + CorrectAnswers + "/" + QuestionCounter +" pytań. Zajęło Ci to: " + timePassed + "s.",Toast.LENGTH_LONG).show()
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
            else{
            currentQuestion++
            if(wybrana==poprawna){ CorrectAnswers++ }
                wybrana=0
            setQuestion()
            defaultOptionsView()
            }
        }


    }



     private fun setQuestion() {
         val dbHelper = DataBaseHelper(applicationContext)
         val db = dbHelper.writableDatabase
         val cursor = db.query(TableInfo.TABLE_NAME, null, BaseColumns._ID + "=?", arrayOf(currentQuestion.toString()), null, null, null)

        if (cursor.moveToFirst()) {

            binding.question.setText(cursor.getString(1))
            binding.odp1.setText(cursor.getString(2))
            binding.odp2.setText(cursor.getString(3))
            binding.odp3.setText(cursor.getString(4))
            binding.odp4.setText(cursor.getString(5))
            poprawna = cursor.getString(6).toInt()
        }

    }



    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        options.add(0,binding.odp1)
        options.add(1,binding.odp2)
        options.add(2,binding.odp3)
        options.add(3,binding.odp4)

        for (option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.background = ContextCompat.getDrawable(this,R.drawable.default_option_border_bg)
        }

    }


    private fun selectOption(num: Int){
        defaultOptionsView()
        val options = ArrayList<TextView>()
        options.add(0,binding.odp1)
        options.add(1,binding.odp2)
        options.add(2,binding.odp3)
        options.add(3,binding.odp4)

        options[num-1].setTextColor(Color.parseColor("#363A43"))
        options[num-1].background = ContextCompat.getDrawable(this,R.drawable.selected_option_border_bg)


    }

    private fun countQuestions(){

        val sql = "SELECT COUNT(*) FROM " + TableInfo.TABLE_NAME
        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase
        val cursor2 =  db.rawQuery(sql,null)

        if(cursor2.count > 0){
            cursor2.moveToFirst()
            QuestionCounter = cursor2.getInt(0);
        }


    }



    private fun addBasicQuestions(){

        addQ("Jakie jest rozwinięcie skrótu 'PUT'?",
                "Poznański Uniwersytet Techniczny",
                "Poznan University of Technology",
                "Poznan University of Technics",
                "Poznański Uniwersytet Tramwajów",
                2
        )

        addQ(
                "Urządzenie służące do rozgałęzienia sygnału w sieci to:",
                "terminal",
                "serwer",
                "hub",
                "host",
                3
        )

        addQ(
                "Jakie systemy plików nie są obsługiwane przez system Windows?",
                "FAT32",
                "FAT16",
                "EXT4",
                "NTFS",
                3
        )
       addQ(" Który z systemów nie należy do dystrybucji Linux?",
                "DOS",
                " Parrot",
                "Debian",
                "Red Hat",
                1
        )
       addQ(
                " Która podanych nazw nie jest rodzajem GUI?",
                "KDE",
                "GNOME",
                "Luna",
                "InterGUI",
                4
        )
        addQ(
                "Maskotką Linuksa jest pingwin:",
                "Rux",
                "Tux",
                "Mux",
                "Lux",
                2
        )
       addQ(
                "Którego protokołu należy użyć do odbioru poczty elektronicznej z swojego serwera?",
                "SMTP",
                "FTP",
                "SNMP",
                "POP3",
                4
        )
        addQ(
                "Jaki system jest najlepszy?",
                "Windows",
                "Linux",
                "Android",
                "Mac",
                2
        )
        addQ(
                "Wskaż bezpołączeniowy protokół",
                "UDP",
                "TCP",
                "IP",
                "NCP",
                1
        )
        addQ(
                "Jaka pierwsza wersja Androida została specjalnie zoptymalizowana pod względem tabletów?",
                "Eclair",
                "Honeycomb",
                "Marshmellow",
                "Froyo",
                2
        )


    }


    private fun addQ(pytanie :String,A1 :String,A2 :String,A3 :String,A4 :String,poprawnaodp : Int){
        val dbHelper = DataBaseHelper(applicationContext)
        val db = dbHelper.writableDatabase

        val value = ContentValues()
        value.put("pytanie", pytanie)
        value.put("Odp1", A1)
        value.put("Odp2", A2)
        value.put("Odp3", A3)
        value.put("Odp4", A4)
        value.put("numer", poprawnaodp)


        db.insertOrThrow(TableInfo.TABLE_NAME, null, value)
    }

}
