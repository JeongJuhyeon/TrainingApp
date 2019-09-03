package kr.jjh.quiz1;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "answers")
public class Answer {
    // Don't use NonNull annotations on _id, somehow they screw things up

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    public Integer id;

    @ColumnInfo(name = "questionID")
    public Integer questionID;

    @ColumnInfo(name = "answerNo")
    public Integer answerNo;

    @ColumnInfo(name = "correct")
    public Integer correct;

    @ColumnInfo(name = "answerString")
    public String answerString;

    @NonNull
    @ColumnInfo(name = "answerType")
    public String answerType;

    @ColumnInfo(name = "itemID")
    public Integer itemID;
}
