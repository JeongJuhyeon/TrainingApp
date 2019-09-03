package kr.jjh.quiz1;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("SELECT * FROM items ORDER BY _id")
    List<Item> getAllItems();

    @Query("SELECT * FROM items WHERE _id = :id")
    Item getItemWithId(int id);

    @Query("SELECT * FROM items WHERE isIngredient = 0 ORDER BY _id")
    List<Item> getCompletedItems();

    @Insert
    void insert(Item item);
}
