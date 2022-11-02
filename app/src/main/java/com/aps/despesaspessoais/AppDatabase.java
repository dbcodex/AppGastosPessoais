package com.aps.despesaspessoais;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Cadastro.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CadastroDAO cadastroDAO();
}
