package br.edu.ifsp.dmo1.exemplosqlite.ui.details

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.edu.ifsp.dmo1.exemplosqlite.R
import br.edu.ifsp.dmo1.exemplosqlite.databinding.ActivityDetailsBinding
import br.edu.ifsp.dmo1.exemplosqlite.databinding.ActivityMainBinding

class DetailsActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_details)

        binding.buttonSave.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        if (v.id == binding.buttonSave.id) {
            val string = binding.textTexto.text.toString()
            if (string.isNotEmpty()) {
                val mIntent = Intent()
                mIntent.putExtra("texto", string)
                setResult(RESULT_OK, mIntent)
            } else {
                Toast.makeText(this, "Não foi inserido texto.", Toast.LENGTH_SHORT).show()
                setResult(RESULT_CANCELED)
            }
            finish()
        }
    }
}