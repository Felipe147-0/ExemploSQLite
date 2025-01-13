package br.edu.ifsp.dmo1.exemplosqlite.data.repository

import android.content.Context
import br.edu.ifsp.dmo1.exemplosqlite.data.database.DatabaseHelper
import br.edu.ifsp.dmo1.exemplosqlite.data.database.MeuDadoDao
import br.edu.ifsp.dmo1.exemplosqlite.data.model.MeuDado

class MeuDadoRepository (context: Context) {

    private val dbHelper = DatabaseHelper(context)
    private val dao = MeuDadoDao(dbHelper)

    fun getAllMeusDados(): List<MeuDado> = dao.getAll()

    fun addMeuDado(dado: MeuDado) = dao.insert(dado)

    fun updateMeuDado(dado: MeuDado) = dao.update(dado)
}