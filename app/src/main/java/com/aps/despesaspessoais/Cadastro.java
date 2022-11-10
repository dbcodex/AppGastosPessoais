package com.aps.despesaspessoais;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity

public class Cadastro {
    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "chaveFirebase")
    public String chaveFirebase;

    @ColumnInfo(name = "nome")
    public String nome;

    @ColumnInfo(name = "email")
    public String email;
}
