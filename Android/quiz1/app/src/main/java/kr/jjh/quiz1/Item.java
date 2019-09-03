package kr.jjh.quiz1;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class Item {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    public Integer id;

    @NonNull
    @ColumnInfo(name = "drawable")
    public String drawable;

    @NonNull
    @ColumnInfo(name = "fullName")
    public String fullName;

    @NonNull
    @ColumnInfo(name = "isIngredient")
    public Integer isIngredient;

    @ColumnInfo(name = "idIngredientA")
    public Integer idIngredientA;

    @ColumnInfo(name = "idIngredientB")
    public Integer idIngredientB;
}
