package br.com.faculdade.imepac

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TelaPrincipal : AppCompatActivity() {

    private lateinit var text_boas_vindas: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela_principal)

        supportActionBar?.hide()
        IniciarComponentes()

        text_boas_vindas.setOnClickListener {
            irParaTelaPerfil()
        }
    }

    private fun irParaTelaPerfil() {
        val intent = Intent(this, TelaPerfil::class.java)
        startActivity(intent)
        finish()
    }

    private fun IniciarComponentes() {
        text_boas_vindas = findViewById(R.id.text_boas_vindas)
    }
}
