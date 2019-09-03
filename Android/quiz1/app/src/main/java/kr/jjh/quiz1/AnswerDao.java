package kr.jjh.quiz1;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AnswerDao {
    @Query("SELECT * FROM answers")
    List<Answer> getAllAnswers();

    @Query("SELECT * FROM answers WHERE questionID = :id")
    List<Answer> getAnswersOfQuestionWithId(int id);

    @Insert
    void insert(Answer answer);
}
