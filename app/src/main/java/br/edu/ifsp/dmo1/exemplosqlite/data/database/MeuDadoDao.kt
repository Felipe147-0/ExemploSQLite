package br.edu.ifsp.dmo1.exemplosqlite.data.database

import android.content.ContentValues
import br.edu.ifsp.dmo1.exemplosqlite.data.model.MeuDado

class MeuDadoDao(private val dbHelper: DatabaseHelper) {

    fun insert(meuDado: MeuDado) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_TEXTO, meuDado.texto)
        }

        db.insert(DatabaseHelper.DATABASE_KEYS.TABLE_NAME, null, values)
    }

    fun getAll(): List<MeuDado> {
        val db = dbHelper.readableDatabase
        val columns = arrayOf(
            DatabaseHelper.DATABASE_KEYS.COLUMN_ID,         //V2
            DatabaseHelper.DATABASE_KEYS.COLUMN_TEXTO)

        val cursor = db.query(                         // executando uma consulta
            DatabaseHelper.DATABASE_KEYS.TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null
        )
        val dados = mutableListOf<MeuDado>()

        cursor.use {            //"use" apenas para facilitar, para não usar varias vezes
            while (it.moveToNext()) {
                dados.add(
                    MeuDado(id = it.getInt(0), texto = it.getString(1))
                )
            }
        }

        return dados
    }

    fun getById(id: Int): MeuDado? {
        val dado: MeuDado?
        val db = dbHelper.readableDatabase
        val columns = arrayOf(
            DatabaseHelper.DATABASE_KEYS.COLUMN_ID,
            DatabaseHelper.DATABASE_KEYS.COLUMN_TEXTO
        )

        val where = "${DatabaseHelper.DATABASE_KEYS.COLUMN_ID} = ?"
        val whereArgs = arrayOf(id.toString())

        val cursor = db.query(
            DatabaseHelper.DATABASE_KEYS.TABLE_NAME,
            columns,
            where,
            whereArgs,
            null,
            null,
            null
        )

        cursor.use {
            dado = if (cursor.moveToNext()) {
                MeuDado(cursor.getInt(0), cursor.getString(1))
            }else {
                null
            }
        }

        return dado
    }

    fun update(meuDado: MeuDado) {

        val values = ContentValues().apply {
            put(DatabaseHelper.DATABASE_KEYS.COLUMN_TEXTO, meuDado.texto)
            //put("endereço","via expressa")  //exemplo de coluna e valor
        }

        val where = "${DatabaseHelper.DATABASE_KEYS.COLUMN_ID} = ?"
        val whereArgs = arrayOf(meuDado.id.toString())

        val db = dbHelper.writableDatabase
        db.update(
            DatabaseHelper.DATABASE_KEYS.TABLE_NAME,
            values,
            where,
            whereArgs //ou null
        )
    }

    fun delete(meuDado: MeuDado){
        val where = "${DatabaseHelper.DATABASE_KEYS.COLUMN_ID} = ?"
        val whereArgs = arrayOf(meuDado.id.toString())
        val db = dbHelper.writableDatabase
        db.delete(
            DatabaseHelper.DATABASE_KEYS.TABLE_NAME,
            where,
            whereArgs
        )
    }
}