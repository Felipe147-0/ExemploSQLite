package br.edu.ifsp.dmo1.exemplosqlite.ui.main

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
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

class MainActivity : AppCompatActivity(), ItemListDadoClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: MeuDadoAdapter
    private lateinit var addResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var updateResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setupRecyclerView()
        setupObservers()
        setupListeners()
        setupLauncher()
        viewModel.load()
    }

    override fun clickEditItemList(id: Int, texto: String) {
        val mIntent = Intent(this, DetailsActivity::class.java)
        mIntent.putExtra("id", id)
        mIntent.putExtra("texto", texto)
        updateResultLauncher.launch(mIntent)
    }

    override fun clickDeleteItemList(id: Int) {
        viewModel.notifyDelete(id)
    }

    private fun setupLauncher() {
        addResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                if (it.resultCode == RESULT_OK) {
                    val texto = it.data?.getStringExtra("texto") ?: ""
                    viewModel.addDado(texto)
                }
            }
        )

        updateResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {
                if (it.resultCode == RESULT_OK) {
                    val texto = it.data?.getStringExtra("texto") ?: ""
                    val id = it.data?.getIntExtra("id", -1) ?: -1
                    viewModel.updateDado(id, texto)
                }
            }
        )
    }

    private fun setupListeners() {
        binding.buttonAdd.setOnClickListener {
            val mIntent = Intent(this, DetailsActivity::class.java)
            addResultLauncher.launch(mIntent)
        }
    }

    private fun setupObservers() {
        viewModel.dados.observe(this, Observer {
            adapter.updateDados(it)
        })

        viewModel.toDeleteTexto.observe(this, Observer {
            showDeleteConfirm(it)
        })

        viewModel.deleted.observe(this, Observer {
            Toast.makeText(this, "Dado apagado com sucesso.", Toast.LENGTH_SHORT).show()
        })
    }

    private fun setupRecyclerView() {
        adapter = MeuDadoAdapter(mutableListOf(), this)
        binding.listDados.adapter = adapter
        binding.listDados.layoutManager = LinearLayoutManager(this)
    }

    private fun showDeleteConfirm(texto: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmação de Exclusão")
        builder.setMessage("Tem certeza que deseja apagar o Dado: $texto?")

        builder.setPositiveButton("Excluir", DialogInterface.OnClickListener { dialog, which ->
            viewModel.confirmDelete()
            dialog.dismiss()
        })

        builder.setNeutralButton("Cancelar", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        builder.create().show()
    }

}