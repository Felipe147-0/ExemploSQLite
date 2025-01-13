package br.edu.ifsp.dmo1.exemplosqlite.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import br.edu.ifsp.dmo1.exemplosqlite.R
import br.edu.ifsp.dmo1.exemplosqlite.databinding.ActivityMainBinding
import br.edu.ifsp.dmo1.exemplosqlite.ui.details.DetailsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MeuDadoAdapter
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setupRecyclerView()
        setupObservers()
        setupListeners()
        viewModel.load()    //não esquecer para não vir a lista vazia na viewmodel tbm
    }

    private fun setupLauncher() {
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                if (it.resultCode == RESULT_OK) {
                    val texto = it.data?.getStringExtra("texto") ?: ""
                    viewModel.addDado(texto)
                }
            }
        )
    }

    private fun setupListeners() {
        binding.buttonAdd.setOnClickListener {
            val mIntent = Intent(this, DetailsActivity::class.java)
            resultLauncher.launch(mIntent)
        }
    }

    private fun setupObservers() {
        viewModel.dados.observe(this, Observer {
            adapter.updateDados(it)
        })
    }

    private fun setupRecyclerView() {
        adapter = MeuDadoAdapter(mutableListOf())
        binding.listDados.adapter = adapter
        binding.listDados.layoutManager = LinearLayoutManager(this)
    }
}