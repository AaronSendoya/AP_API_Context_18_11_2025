package com.example.api_conexion;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "productos.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_PRODUCTOS = "Productos";
    public static final String COLUMN_ID_PRODUCTO = "IdProducto";
    public static final String COLUMN_ID_STORES = "IdStores";
    public static final String COLUMN_PRODUCIO = "Producio";
    public static final String COLUMN_DESCRIPCION = "Descripcion";
    public static final String COLUMN_PRECIO = "Precio";
    public static final String COLUMN_CANTIDAD_STOCK = "CantidadStock";
    public static final String COLUMN_UNIDAD_MEDIDA = "UnidadMedida";
    public static final String COLUMN_FECHA_VENCIMIENTO = "FechaVencimiento";
    public static final String COLUMN_IMAGEN = "Imagen";
    public static final String COLUMN_ESTADO = "Estado";
    public static final String COLUMN_CATEGORIA = "Categoria";
    public static final String COLUMN_ID_CATEGORIA = "IdCategoria";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_PRODUCTOS + " (" +
                    COLUMN_ID_PRODUCTO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ID_STORES + " INTEGER, " +
                    COLUMN_PRODUCIO + " TEXT, " +
                    COLUMN_DESCRIPCION + " TEXT, " +
                    COLUMN_PRECIO + " REAL, " +
                    COLUMN_CANTIDAD_STOCK + " INTEGER, " +
                    COLUMN_UNIDAD_MEDIDA + " TEXT, " +
                    COLUMN_FECHA_VENCIMIENTO + " TEXT, " +
                    COLUMN_IMAGEN + " TEXT, " +
                    COLUMN_ESTADO + " TEXT, " +
                    COLUMN_CATEGORIA + " TEXT, " +
                    COLUMN_ID_CATEGORIA + " INTEGER" +
                    ");";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTOS);
        onCreate(db);
    }
}
