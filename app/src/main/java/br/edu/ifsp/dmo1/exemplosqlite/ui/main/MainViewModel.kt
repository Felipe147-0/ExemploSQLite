package br.edu.ifsp.dmo1.exemplosqlite.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.edu.ifsp.dmo1.exemplosqlite.data.model.MeuDado
import br.edu.ifsp.dmo1.exemplosqlite.data.repository.MeuDadoRepository

class MainViewModel (application: Application) : AndroidViewModel(application){  //nunca passar um "context" para não gerar um estouro apenas application
    private var repository: MeuDadoRepository
    private var toDeleteMeuDado: MeuDado? = null

    private val _dados = MutableLiveData<List<MeuDado>>()
    val dados: LiveData<List<MeuDado>>
        get() = _dados

    private val _toDeleteTexto = MutableLiveData<String>()
    val toDeleteTexto: LiveData<String> = _toDeleteTexto

    private val _deleted = MutableLiveData<Boolean>()
    val deleted: LiveData<Boolean> = _deleted


    init {
        repository = MeuDadoRepository(application)
    }

    fun load() {                         //lista não ficar vazia
        _dados.value = repository.getAllMeusDados()
    }

    fun addDado(texto: String) {
        repository.addMeuDado(MeuDado(-1,texto))
        load()
    }

    fun updateDado(id: Int ,texto: String){
        val dado = MeuDado(id, texto)
        repository.updateMeuDado(dado)
        load()
    }

    fun notifyDelete(id: Int){
        toDeleteMeuDado = repository.getMeuDadoById(id)
        if (toDeleteMeuDado != null) {
            _toDeleteTexto.value = toDeleteMeuDado!!.texto
        }
    }

    fun confirmDelete() {
        if (toDeleteMeuDado != null) {
            repository.deleteMeuDado(toDeleteMeuDado!!)
            toDeleteMeuDado = null
            _deleted.value = true
            load()
        }
    }
}