package kr.jjh.quiz1;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestionDao {

    @Query("SELECT * FROM questions")
    List<Question> getAllQuestions();

    @Query("SELECT * FROM questions WHERE questionType = :type")
    List<Question> getQuestionsOfType(String type);

    @Insert
    void insert(Question question);
}
