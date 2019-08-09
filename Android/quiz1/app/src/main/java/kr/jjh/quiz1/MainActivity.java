package kr.jjh.quiz1;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDBHelper = new DatabaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getQuestionsFromDB();
            }
        });
    }

    private void getQuestionsFromDB() {
        String[] projection = {
                BaseColumns._ID,
                QuestionsContract.QuestionEntry.COLUMN_NAME_QUESTIONSTR
        };

        Cursor cursor = mDb.query(
                QuestionsContract.QuestionEntry.TABLE_NAME,
                null, // Return all columns
                null, // No where clause, i.e. all columns
                null,
                null,
                null,
                null
        );

        while(cursor.moveToNext()) {
            int index;

            index = cursor.getColumnIndexOrThrow(QuestionsContract.QuestionEntry._ID);
            int id = cursor.getInt(index);

            index = cursor.getColumnIndexOrThrow(QuestionsContract.QuestionEntry.COLUMN_NAME_QUESTIONSTR);
            String questionString = cursor.getString(index);

            System.out.println(id);

            if (questionString != null)
                System.out.println(questionString);
        }
    }


}
