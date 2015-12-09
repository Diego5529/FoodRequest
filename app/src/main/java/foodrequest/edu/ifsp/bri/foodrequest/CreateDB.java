package foodrequest.edu.ifsp.bri.foodrequest;

/**
 * Created by diego-macosx on 12/1/15.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CreateDB extends SQLiteOpenHelper{

    protected static final String NOME_BANCO = "food_request.db";

    //Produtos
    protected static final String TABELA_PRODUTOS = "produtos";
    protected static final String ID_PRODUTO = "_id";
    protected static final String NOME_PRODUTO = "nome";
    protected static final String PRECO_PRODUTO = "preco";

    //Pedidos
    protected static final String TABELA_PEDIDOS = "pedidos";
    protected static final String ID_PEDIDO = "id_pedido";
    protected static final String ID_PRODUTO_PEDIDO = "id_prod";
    protected static final String QUANTIDADE = "quantidade";
    protected static final String PRECO_PEDIDO = "preco_pedido";
    protected static final String TOTAL = "total";
    protected static final String MESA = "mesa";

    protected static final int VERSAO = 1;

    public CreateDB(Context context){
        super(context, NOME_BANCO,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTableProdutos(db);
        createTablePedidos(db);
    }

    public void createTableProdutos(SQLiteDatabase db){
        String sql = "CREATE TABLE "+TABELA_PRODUTOS+"("
                + ID_PRODUTO + " integer primary key autoincrement,"
                + NOME_PRODUTO + " text,"
                + PRECO_PRODUTO + " decimal(10,5)"
                +")";

        db.execSQL(sql);
    }

    public void createTablePedidos(SQLiteDatabase db){
        //Cadastro de Pedidos (id, id_prod, quantidade, preço, total, mesa) – com inclusão, consulta

        String sql = "CREATE TABLE "+TABELA_PEDIDOS+"("
                + ID_PEDIDO + " integer primary key autoincrement,"
                + ID_PRODUTO_PEDIDO + " integer,"
                + QUANTIDADE + " integer,"
                + PRECO_PEDIDO + " decimal(10,5),"
                + TOTAL + " decimal(10,5),"
                + MESA + " integer"
                +")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABELA_PRODUTOS);
        db.execSQL("DROP TABLE IF EXISTS" + TABELA_PEDIDOS);

        onCreate(db);
    }
}
