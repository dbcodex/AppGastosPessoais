package com.aps.despesaspessoais;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CadastroDAO {

    @Query("SELECT * FROM cadastro")
    List<Cadastro> getAll();

//    @Query("SELECT * FROM Cadastro WHERE id IN (:userIds)")
//    List<Cadastro> loadAllByIds(int[] userIds);
//
//    @Query("SELECT * FROM Cadastro WHERE nome LIKE :first LIMIT 1")
//    Cadastro findByName(String first, String last);

    @Insert
    void insert(Cadastro... users);

    @Delete
    void delete(Cadastro user);
}