package com.example.simondice

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private var contaRonda: Int = 0
    var player = false
    var secArrayComproba = arrayOf<Int>()
    var secArray = arrayOf<Int>()
    private var countSecuencia: Int = 0
    var compo = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val empezarPartida: Button = findViewById(R.id.bJugar)
        empezarPartida.setOnClickListener {

            Log.i("Estado", "Boton pulsado: Jugar")
            mostrarRonda()


            GlobalScope.launch(Dispatchers.Main) {

                empezarPartida.visibility = Button.INVISIBLE
                ejecutarSecuencia()
                mensajeUsuario(2)
                player = true
            }
        }
        val bcomprobar: Button = findViewById(R.id.botonComprobar)
        bcomprobar.setOnClickListener {
            if (secArray.size == secArrayComproba.size) {
                comprobarSecuencia()
            } else if (secArray.size > secArrayComproba.size) {
                mensajeUsuario(5)
            } else {
                finalizarPartida()
            }
        }
        val botonVerde: Button = findViewById(R.id.botonVerde)
        botonVerde.setOnClickListener {
            if (player) {
                secArrayComproba += 1
            }
            Log.i("Estado", "Boton pulsado: verde")
        }
        val botonRojo: Button = findViewById(R.id.botonRojo)
        botonRojo.setOnClickListener {
            if (player) {
                secArrayComproba += 2
            }
            Log.i("Estado", "Boton pulsado: rojo")
        }
        val botonAmarillo: Button = findViewById(R.id.botonAmarillo)
        botonAmarillo.setOnClickListener {
            if (player) {
                secArrayComproba += 3
            }
            Log.i("Estado", "Boton pulsado: amarillo")
        }
        val botonAzul: Button = findViewById(R.id.botonAzul)
        botonAzul.setOnClickListener {
            if (player) {
                secArrayComproba += 4
            }
            Log.i("Estado", "Boton pulsado: azul")
        }
    }

    private fun mostrarRonda() {
        contaRonda++
        Log.i("Estado", "Mostrar Numero de Rondas")
        val t: TextView = findViewById(R.id.numeroRonda)
        t.setText("Ronda: " + contaRonda.toString())
        t.visibility = TextView.VISIBLE

        secArray += Random.nextInt(1, 4)
        Log.i("Estado", "Incremeto Array")
        Log.i("Estado", "Fin del metodo mostrarRonda")
    }

    suspend fun ejecutarSecuencia() {
        mensajeUsuario(1)

        Log.i("Estado", "Empieza el juego")

        for (b in secArray) {
            when {
                b == 1 -> {
                    val bVerde: Button = findViewById(R.id.botonVerde)
                    bVerde.setBackgroundColor(Color.parseColor("#0E5005"))
                    delay(1000L)
                    bVerde.setBackgroundColor(Color.parseColor("#179205"))
                    delay(1000L)
                    Log.i("Estado", "Verde")
                }
                b == 2 -> {
                    val bRojo: Button = findViewById(R.id.botonRojo)
                    bRojo.setBackgroundColor(Color.parseColor("#FF0400"))
                    delay(1000L)
                    bRojo.setBackgroundColor(Color.parseColor("#C50505"))
                    delay(1000L)
                    Log.i("Estado", "Rojo")
                }
                b == 3 -> {
                    val bAmarillo: Button = findViewById(R.id.botonAmarillo)
                    bAmarillo.setBackgroundColor(Color.parseColor("#747408"))
                    delay(1000L)
                    bAmarillo.setBackgroundColor(Color.parseColor("#FFFE00"))
                    delay(1000L)
                    Log.i("Estado", "Amarillo")
                }
                b == 4 -> {
                    val bAzul: Button = findViewById(R.id.botonAzul)
                    bAzul.setBackgroundColor(Color.parseColor("#032BFE"))
                    delay(1000L)
                    bAzul.setBackgroundColor(Color.parseColor("#03FEED"))
                    delay(1000L)
                    Log.i("Estado", "Azul")
                }
            }

        }


    }

    private fun mensajeUsuario(key: Int) {
        Log.i("Estado", "Mensaje por toast al usuario")
        val mensaje = when {
            key == 1 -> "Estás en una Ronda"
            key == 2 -> "Te toca!"
            key == 3 -> "Genial!"
            key == 5 -> "Faltan botones"
            else -> "Ñiii!! Fallaste :("
        }
        Toast.makeText(this.applicationContext, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun comprobarSecuencia() {
        Log.i("Estado", "Comprueba secuencia del jugador")
        for (s in secArray) {
            if (s == secArrayComproba.get(countSecuencia)) {
                Log.i("Estado", s.toString() + " " + secArrayComproba.get(countSecuencia))
                countSecuencia++

            } else {
                Log.i("Estado", "No coincide")
                mensajeUsuario(4)
                compo = false
            }
        }

        if (compo) {
            Log.i("Estado", "Seguinte ronda")
            player = false
            mostrarRonda()
            mensajeUsuario(3)
            countSecuencia = 0
            secArrayComproba = arrayOf()
            corrutina()
        } else {
            player = false
            finalizarPartida()
        }
    }

    private fun finalizarPartida() {
        Log.i("Estado", "Game Over")
        secArray = arrayOf()
        secArrayComproba = arrayOf()
        contaRonda = 0
        compo = true
        val t: TextView = findViewById(R.id.numeroRonda)
        t.visibility = TextView.INVISIBLE
        val empezarPartida: Button = findViewById(R.id.bJugar)
        empezarPartida.visibility = Button.VISIBLE
    }

    fun corrutina() {
        GlobalScope.launch(Dispatchers.Main) {
            ejecutarSecuencia()
            mensajeUsuario(2)
            player = true
        }
    }
}