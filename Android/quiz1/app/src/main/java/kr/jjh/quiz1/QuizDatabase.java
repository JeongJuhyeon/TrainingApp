package kr.jjh.quiz1;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.huma.room_for_asset.RoomAsset;

@Database(entities = {Question.class, Answer.class, Item.class}, version = 2)
public abstract class QuizDatabase extends RoomDatabase{
    // Abstract getter (seemingly generated?)
    public abstract QuestionDao questionDao();
    public abstract AnswerDao answerDao();
    public abstract ItemDao itemDao();

    //-----------Make the QuizDatabase a Singleton--------
    private static volatile QuizDatabase INSTANCE;

    static QuizDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (QuizDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = RoomAsset.databaseBuilder(context.getApplicationContext(),
                            QuizDatabase.class, "quizdb2.sqlite3", 2).build();
                }
            }
        }
        return INSTANCE;
    }
}
