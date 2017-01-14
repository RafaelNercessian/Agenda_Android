package br.com.alura.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.agenda.modelo.Aluno;

/**
 * Created by Rafael on 10/12/2016.
 */

public class AlunoDAO extends SQLiteOpenHelper {


    public AlunoDAO(Context context) {
        super(context, "Agenda", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE Alunos(" +
                "id INTEGER PRIMARY KEY, " +
                "nome TEXT NOT NULL, " +
                "endereco TEXT, " +
                "telefone TEXT, " +
                "site TEXT, " +
                "nota REAL, " +
                "caminhoFoto TEXT);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch(oldVersion){
            case 1:
                String sql="ALTER TABLE Alunos ADD caminhoFoto TEXT;";
                db.execSQL(sql);
        }

    }

    public void insere(Aluno aluno){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues valores=new ContentValues();
        valores.put("nome",aluno.getNome());
        valores.put("endereco",aluno.getEndereco());
        valores.put("telefone",aluno.getTelefone());
        valores.put("site",aluno.getSite());
        valores.put("nota",aluno.getNota());
        valores.put("caminhoFoto",aluno.getCaminhoFoto());
        db.insert("Alunos",null,valores);
    }

    public List<Aluno> listaAlunosBanco(){
        String sql="SELECT * from Alunos;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Aluno> alunos=new ArrayList<>();
        while(cursor.moveToNext()){
            Aluno aluno=new Aluno();
            aluno.setId(cursor.getLong(cursor.getColumnIndex("id")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            aluno.setEndereco(cursor.getString(cursor.getColumnIndex("endereco")));
            aluno.setSite(cursor.getString(cursor.getColumnIndex("site")));
            aluno.setTelefone(cursor.getString(cursor.getColumnIndex("telefone")));
            aluno.setNota(cursor.getDouble(cursor.getColumnIndex("nota")));
            aluno.setCaminhoFoto(cursor.getString(cursor.getColumnIndex("caminhoFoto")));
            alunos.add(aluno);
        }
        cursor.close();
        return alunos;
    }

    public void remover(Aluno aluno){
        SQLiteDatabase db = getWritableDatabase();
        String params[]={aluno.getId().toString()};
        db.delete("Alunos","id=?",params);
    }

    public void altera(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("id",aluno.getId());
        values.put("nome",aluno.getNome());
        values.put("endereco",aluno.getEndereco());
        values.put("telefone",aluno.getTelefone());
        values.put("site",aluno.getSite());
        values.put("nota",aluno.getNota());
        values.put("caminhoFoto",aluno.getCaminhoFoto());
        String params[]={aluno.getId().toString()};
        db.update("Alunos",values,"id=?",params);
    }
}
