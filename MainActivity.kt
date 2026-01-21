package com.example.textgame

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var textOutput: TextView
    private lateinit var textInput: EditText
    private lateinit var buttonSend: Button

    // Текущее состояние игры
    private var gameState = "start"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textOutput = findViewById(R.id.textOutput)
        textInput = findViewById(R.id.textInput)
        buttonSend = findViewById(R.id.buttonSend)

        // Обработка нажатия кнопки или клавиши "Enter" на клавиатуре
        buttonSend.setOnClickListener { processInput() }
        textInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                processInput()
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun processInput() {
        val command = textInput.text.toString().trim().lowercase()
        textInput.text.clear()

        when (gameState) {
            "start" -> {
                when (command) {
                    "осмотреть" -> appendOutput("Вы видите дверь и старый стол.")
                    "открыть дверь" -> {
                        appendOutput("Вы вышли в коридор. Куда дальше?")
                        gameState = "corridor"
                    }
                    "посмотреть стол" -> appendOutput("На столе лежит ключ.")
                    else -> appendOutput("Не понимаю команду: $command")
                }
            }
            "corridor" -> {
                when (command) {
                    "идти налево" -> appendOutput("Вы нашли сокровище! Победа!")
                    "идти направо" -> appendOutput("Тут ловушка! Игра окончена.")
                    else -> appendOutput("Не понимаю команду: $command")
                }
            }
            else -> appendOutput("Игра завершилась.")
        }
    }

    private fun appendOutput(text: String) {
        textOutput.append("\n> $text")
    }
}