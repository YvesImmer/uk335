package ch.band.inf2019.uk335.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CategorieDao {

    @Insert
    void insert(Categorie... categories);

    @Update
    void updateCategories(Categorie... categories);

    @Delete
    void deleteCategories(Categorie... categories);

    /**
     * List of all Categories
     * @return All Categories ordered by there ID
     */
    @Query("SELECT * FROM categorie_table ORDER BY id ASC")
    LiveData<List<Categorie>> getAllCategories();


}
