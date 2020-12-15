package com.example.gestaocheckin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public final class BD {

    protected SQLiteDatabase db;
    private final String DATABASE_NAME = "checkInDB";
    private static BD INSTANCE = new BD();

    private final String[] SCRIPT_DATABASE_CREATE = new String[] {

            "CREATE TABLE Checkin (Local TEXT PRIMARY KEY, qtdVisitas INTEGER NOT NULL, cat INTEGER NOT NULL," +
            " latitude TEXT NOT NULL, longitude TEXT NOT NULL, CONSTRAINT fkey0 FOREIGN KEY (cat)" +
            "REFERENCES Categoria (idCategoria));",

            "CREATE TABLE Categoria (idCategoria INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT NOT NULL);",

            "INSERT INTO Categoria (nome) VALUES ('Restaurante');",
            "INSERT INTO Categoria (nome) VALUES ('Bar');",
            "INSERT INTO Categoria (nome) VALUES ('Cinema');",
            "INSERT INTO Categoria (nome) VALUES ('Universidade');",
            "INSERT INTO Categoria (nome) VALUES ('Estádio');",
            "INSERT INTO Categoria (nome) VALUES ('Parque');",
            "INSERT INTO Categoria (nome) VALUES ('Outros');"
    };

    private BD() {

        Context ctx = MyApp.getAppContext();
        db = ctx.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        Cursor c = query("sqlite_master", null, "type = 'table'", "");

        if(c.getCount() == 1){
            for(int i = 0; i < SCRIPT_DATABASE_CREATE.length; i++)
                db.execSQL(SCRIPT_DATABASE_CREATE[i]);
            Log.i("BANCO_DADOS", "Criou tabelas do banco e as populou.");
        }

        c.close();
        Log.i("BANCO_DADOS", "Abriu conexão com o banco.");
    }

    public static BD getInstance(){
        return INSTANCE;
    }

    public long insert(String tabela, ContentValues valores) {
        long id = db.insert(tabela, null, valores);
        Log.i("BANCO_DADOS", "Cadastrou registro com o id [" + id + "]");
        return id;
    }

    public int update(String tabela, ContentValues valores, String where) {
        int count = db.update(tabela, valores, where, null);
        Log.i("BANCO_DADOS", "Atualizou [" + count + "] registros");
        return count;
    }

    public int delete(String tabela, String where) {
        int count = db.delete(tabela, where, null);

        Log.i("BANCO_DADOS", "Deletou [" + count + "] registros");
        return count;
    }

    public Cursor query(String tabela, String colunas[], String where, String orderBy) {
        Cursor c;
        if(!where.equals(""))
            c = db.query(tabela, colunas, where, null, null, null, orderBy);
        else
            c = db.query(tabela, colunas, null, null, null, null, orderBy);

        Log.i("BANCO_DADOS", "Realizou uma busca e retornou [" + c.getCount() + "] registros.");
        return c;
    }

    public void open() {
        Context ctx = MyApp.getAppContext();
        db = ctx.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        Log.i("BANCO_DADOS", "Abriu conexão com o banco.");
    }


    public void close() {
        if (db != null) {
            db.close();
            Log.i("BANCO_DADOS", "Fechou conexão com o Banco.");
        }
    }
}