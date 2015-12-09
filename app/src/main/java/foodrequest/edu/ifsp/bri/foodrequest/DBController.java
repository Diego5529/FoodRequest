package foodrequest.edu.ifsp.bri.foodrequest;

/**
 * Created by diego-macosx on 12/1/15.
 */

import android.content.ContentValues;
import android.database.Cursor;

import android.database.sqlite.SQLiteDatabase;
import android.content.Context;

import java.util.ArrayList;

public class DBController {

    private SQLiteDatabase db;
    private CreateDB banco;

    public DBController(Context context){

        banco = new CreateDB(context);
    }

    public String insereDadoProduto(String nome, String preco){

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

    public String insereDadoPedido(int id_produto, int quantidade,  double preco, double total, int mesa){

        ContentValues valores;

        long resultado;

        db = banco.getWritableDatabase();

        valores = new ContentValues();

        valores.put(banco.ID_PRODUTO_PEDIDO, id_produto);
        valores.put(banco.QUANTIDADE, quantidade);
        valores.put(banco.PRECO_PEDIDO, preco);
        valores.put(banco.TOTAL, total);
        valores.put(banco.MESA, mesa);

        resultado = db.insertOrThrow(banco.TABELA_PEDIDOS, null, valores);
        db.close();

        if (resultado ==-1)

            return "Erro ao inserir registro";
        else

            return "Registro Inserido com sucesso";
    }

    public ArrayList<Pedido> listAllPedidos(){
        String query = "SELECT * FROM "+ banco.TABELA_PEDIDOS;
        Cursor cursor;

        db = banco.getReadableDatabase();
        cursor = db.rawQuery(query,null);
        ArrayList<Pedido> list =new ArrayList<Pedido>();

        if(cursor != null && cursor.moveToFirst()){

            do
            {
                Pedido pedido = new Pedido();

                pedido.setIdPedido(cursor.getInt(0));
                pedido.setIdProduto(cursor.getInt(1));
                pedido.setQuantidade(cursor.getInt(2));
                pedido.setPreco(cursor.getDouble(3));
                pedido.setTotal(cursor.getDouble(4));
                pedido.setMesa(cursor.getInt(5));
                list.add(pedido);

            }while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public Cursor carregaDadosProdutos() {
        Cursor cursor;

        String[] campos = {banco.ID_PRODUTO, banco.NOME_PRODUTO};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_PRODUTOS, campos, null, null, null, null, null, null);
        if (cursor != null) {

            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor carregaDadosPedidos() {
        Cursor cursor;

        String[] campos = {banco.ID_PEDIDO, banco.ID_PRODUTO_PEDIDO};
        db = banco.getReadableDatabase();
        cursor = db.query(banco.TABELA_PEDIDOS, campos, null, null, null, null, null, null);
        if (cursor != null) {

            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor carregaDadoByIdProduto(int id){
        Cursor cursor;

        String[] campos =  {banco.ID_PRODUTO, banco.NOME_PRODUTO, banco.PRECO_PRODUTO};

        String where = CreateDB.ID_PRODUTO + "=" + id;
        db = banco.getReadableDatabase();
        cursor = db.query(CreateDB.TABELA_PRODUTOS, campos, where, null, null, null, null, null);

        if (!cursor.moveToFirst())
            cursor = null;

        return cursor;
    }

    public Cursor carregaDadoByIdPedido(int id){
        Cursor cursor;

        String[] campos =  {banco.ID_PEDIDO, banco.ID_PRODUTO_PEDIDO, banco.QUANTIDADE, banco.PRECO_PEDIDO, banco.TOTAL, banco.MESA};

        String where = CreateDB.ID_PEDIDO + "=" + id;
        db = banco.getReadableDatabase();
        cursor = db.query(CreateDB.TABELA_PEDIDOS, campos, where, null, null, null, null, null);

        if (!cursor.moveToFirst())
            cursor = null;

        return cursor;
    }

    public void alteraRegistroProduto(int id, String nome, String preco){
        ContentValues valores;
        String where;
        db = banco.getWritableDatabase();

        where = CreateDB.ID_PRODUTO + "=" + id;

        valores = new ContentValues();
        valores.put(banco.NOME_PRODUTO, nome);
        valores.put(banco.PRECO_PRODUTO, preco);
        db.update(CreateDB.TABELA_PRODUTOS, valores, where, null);
    }

    public void deletaRegistroProduto(int id){
        String where = CreateDB.ID_PRODUTO + "=" + id;
        db = banco.getReadableDatabase();
        db.delete(CreateDB.TABELA_PRODUTOS, where, null);
    }
}