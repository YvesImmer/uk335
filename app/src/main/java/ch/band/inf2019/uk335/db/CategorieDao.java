package ch.band.inf2019.uk335.db;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

public interface CategorieDao {

    @Insert
    void insert(Categorie... categories);

    @Update
    void updateCategories(Categorie... categories);

    @Delete
    void deleteCategories(Categorie... categories);

    @Query("SELECT * FROM categorie_table")
    LiveData<List<Categorie>> getAllCategories();
}