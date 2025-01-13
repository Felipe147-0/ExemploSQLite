package br.edu.ifsp.dmo1.exemplosqlite.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper (context: Context) : SQLiteOpenHelper(
    context,
        DATABASE_KEYS.DATABASE_NAME, //nome do proprio contrato que esta embaixo sem a necessidade do nome, mantem atualizado
        null,
        DATABASE_KEYS.DATABASE_VERSION){

    object DATABASE_KEYS {
        const val DATABASE_NAME = "exemplo_database.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "tb_meu_dado"
        const val COLUMN_TEXTO = "texto"
    }
    //sugestão que seja privado
    private companion object {
        const val CREATE_TABLE_V1 = "CREATE TABLE ${DATABASE_KEYS.TABLE_NAME} (${DATABASE_KEYS.COLUMN_TEXTO} TEXT)"
        const val DROP_TABLE = "DROP TABLE IF EXISTS ${DATABASE_KEYS.TABLE_NAME}";

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_V1)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE);
        onCreate(db);
    }
}