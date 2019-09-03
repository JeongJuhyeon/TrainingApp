package kr.jjh.quiz1;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "questions")
public class Question {
    // Don't use NonNull annotations on _id, somehow they screw things up

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    public Integer id;

    @ColumnInfo(name = "drawable")
    public String drawable;

    @ColumnInfo(name = "questionString")
    public String questionString;

    @ColumnInfo(name = "numberOfAnswers")
    public Integer numberOfAnswers;

    @NonNull
    @ColumnInfo(name = "questionType")
    public String questionType;

    public Question(@NonNull Integer id, String drawable, String questionString, Integer numberOfAnswers, @NonNull String questionType) {
        this.id = id;
        this.drawable = drawable;
        this.questionString = questionString;
        this.numberOfAnswers = numberOfAnswers;
        this.questionType = questionType;
    }
}
