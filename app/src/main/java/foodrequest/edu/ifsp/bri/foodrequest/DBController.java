package foodrequest.edu.ifsp.bri.foodrequest;

/**
 * Created by diego-macosx on 12/1/15.
 */

import android.content.ContentValues;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

public class DBController {

    private SQLiteDatabase db;
    private CreateDB banco;

    public DBController(Context context){

        banco = new CreateDB(context);
    }

    public String insereDado(String nome, String preco){

        ContentValues valores;

        long resultado;

        db = banco.getWritableDatabase();

        valores = new ContentValues();

        valores.put(banco.NOME_PRODUTO, nome);

        valores.put(banco.PRECO_PRODUTO, preco);

        resultado = db.insertOrThrow(banco.TABELA_PRODUTOS, null, valores);
        db.close();

        if (resultado ==-1)

            return "Erro ao inserir registro";
        else

            return "Registro Inserido com sucesso";
    }

    public Cursor carregaDados() {
        Cursor cursor;

        String[] campos = {banco.ID_PRODUTO, banco.NOME_PRODUTO};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_PRODUTOS, campos, null, null, null, null, null, null);
        if (cursor != null) {

            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor carregaDadoById(int id){
        Cursor cursor;

        String[] campos =  {banco.ID_PRODUTO, banco.NOME_PRODUTO, banco.PRECO_PRODUTO};

        String where = CreateDB.ID_PRODUTO + "=" + id;
        db = banco.getReadableDatabase();
        cursor = db.query(CreateDB.TABELA_PRODUTOS, campos, where, null, null, null, null, null);

        if (!cursor.moveToFirst())
            cursor = null;

        return cursor;
    }

    public void alteraRegistro(int id, String nome, String preco){
        ContentValues valores;
        String where;
        db = banco.getWritableDatabase();

        where = CreateDB.ID_PRODUTO + "=" + id;

        valores = new ContentValues();
        valores.put(banco.NOME_PRODUTO, nome);
        valores.put(banco.PRECO_PRODUTO, preco);
        db.update(CreateDB.TABELA_PRODUTOS, valores, where, null);
    }

    public void deletaRegistro(int id){
        String where = CreateDB.ID_PRODUTO + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(CreateDB.TABELA_PRODUTOS, where, null);
    }
}