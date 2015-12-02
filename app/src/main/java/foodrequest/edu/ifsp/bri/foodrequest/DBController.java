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
}
