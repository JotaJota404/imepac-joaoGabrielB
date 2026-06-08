package br.com.faculdade.imepac

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    // Tempo de exibição da Splash em milissegundos
    private val DURACAO_SPLASH = 2000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        // Aguarda 2 segundos e navega automaticamente para o Login
        Handler(Looper.getMainLooper()).postDelayed({
            irParaLogin()
        }, DURACAO_SPLASH)
    }

    private fun irParaLogin() {
        val intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
        finish() // Remove a Splash da pilha para o botão voltar não retornar a ela
    }
}
