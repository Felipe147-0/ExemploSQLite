package br.edu.ifsp.dmo1.exemplosqlite.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.edu.ifsp.dmo1.exemplosqlite.data.model.MeuDado
import br.edu.ifsp.dmo1.exemplosqlite.data.repository.MeuDadoRepository

class MainViewModel (application: Application) : AndroidViewModel(application){
    private lateinit var repository: MeuDadoRepository

    private val _dados = MutableLiveData<List<MeuDado>>()
    val dados: LiveData<List<MeuDado>>
        get() = _dados

    init {
        repository = MeuDadoRepository(application)
    }

    fun load() {                         //lista não ficar vazia
        _dados.value = repository.getAllMeusDados()
    }

    fun addDado(texto: String) {
        repository.addMeuDado(MeuDado(texto))
        _dados.value = repository.getAllMeusDados()
    }
}