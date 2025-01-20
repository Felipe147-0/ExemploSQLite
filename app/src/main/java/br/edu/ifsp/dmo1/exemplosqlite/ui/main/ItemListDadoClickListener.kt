package br.edu.ifsp.dmo1.exemplosqlite.ui.main

interface ItemListDadoClickListener {

    fun clickEditItemList(id: Int, texto: String)

    fun clickDeleteItemList(id: Int)

}