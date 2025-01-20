package br.edu.ifsp.dmo1.exemplosqlite.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.dmo1.exemplosqlite.R
import br.edu.ifsp.dmo1.exemplosqlite.databinding.ActivityDetailsBinding
import br.edu.ifsp.dmo1.exemplosqlite.databinding.ActivityMainBinding
// faz a leitura e devolve

class DetailsActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_details)

        verifyBundle()

        binding.buttonSave.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        if (v.id == binding.buttonSave.id) {
            val string = binding.textTexto.text.toString()
            if (string.isNotEmpty()) {
                val mIntent = Intent()
                mIntent.putExtra("texto", string)

                if (binding.buttonSave.text.toString().equals("atualizar")){
                    val id = binding.textId.text.toString().toInt()
                    mIntent.putExtra("id", id)
                }

                setResult(RESULT_OK, mIntent)
            } else {
                Toast.makeText(this, "Não foi inserido texto.", Toast.LENGTH_SHORT).show()
                setResult(RESULT_CANCELED)
            }
            finish()
        }
    }

    private fun verifyBundle() {
        if (intent.extras != null) {
            val id = intent.getIntExtra("id", -1)
            val texto = intent.getStringExtra("texto")

            binding.buttonSave.setText("Atualizar")
            binding.textlayoutId.visibility = VISIBLE
            binding.textId.setText(id.toString())
            binding.textTexto.setText(texto)
        }
    }
}