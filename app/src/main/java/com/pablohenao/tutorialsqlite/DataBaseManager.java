package com.pablohenao.tutorialsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Pabloh on 24/05/2015.
 */
public class DataBaseManager {
    public static final String TABLE_NAME = "contactos";
    public static final String CN_ID = "_id";  // Nombre columna
    public static final String CN_NAME = "nombre";
    public static final String CN_PHONE = "telefono";

    // create table contactos(
    //                          _id integer primary key autoincrement,
    //                          nombre text not null,
    //                          telefono text);

    public static final String CREATE_TABLE = "create table "+ TABLE_NAME+ " ("
            + CN_ID + " integer primary key autoincrement,"
            + CN_NAME + " text not null,"
            + CN_PHONE + " text);";

    private DbHelper helper;
    private SQLiteDatabase db;

    public DataBaseManager(Context context){

        helper = new DbHelper(context);
        db = helper.getWritableDatabase();

    //    db.insert()
    //    db.execSQL();
    //    db.rawQuery();

    }

    public ContentValues generarContentValues(String nombre, String telefono){

        ContentValues valores = new ContentValues();
        valores.put(CN_NAME, nombre);
        valores.put(CN_PHONE, telefono);

        return valores;

    }

    public void insertar(String nombre, String telefono){

        //bd.insert(TABLA, NullColumnHack, ContentValues);
        //insert into contactos; insert into contactos (telefono) values(null)
        db.insert(TABLE_NAME, null, generarContentValues(nombre, telefono) );

    }

    public void insertar2 (String nombre, String telefono){
        //INSERT INTO contactos VALUES (null, 'paco', 9999)
        db.execSQL("insert into "+TABLE_NAME+" values (null,'"+nombre+"',"+telefono+")");
    }

    public Cursor cargarCursorContactos(){
        String [] columnas = new String[]{CN_ID,CN_NAME,CN_PHONE};
        return db.query(TABLE_NAME,columnas,null,null,null,null,null);
    }

    public Cursor buscarContacto(String Nombre){
        String [] columnas = new String[]{CN_ID,CN_NAME,CN_PHONE};
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return db.query(TABLE_NAME,columnas,CN_NAME + "=?",new String[]{Nombre},null,null,null);
    }

    public void eliminar(String nombre){
        //bd.delete (Tabla, clausula Where, Argumentos Where)
        db.delete(TABLE_NAME, CN_NAME + "=?", new String[]{nombre});
    }

    public void modificarTelefono (String nombre, String nuevoTelefono){
        //bd.update(TABLA, ContentValues, Clausula Where, Argumentos Where
        db.update(TABLE_NAME, generarContentValues(nombre,nuevoTelefono), CN_NAME + "=?", new String[]{nombre});
    }


}
