package kr.jjh.quiz1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private LayoutInflater inflater;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        setUpToolbar();
        inflater = getLayoutInflater();
        inflateConstraintLayout(R.layout.question_textandimage, R.id.questionLayout);
        inflateConstraintLayout(R.layout.answer_itemcombination, R.id.answerLayout);
    }

    private void inflateConstraintLayout(int layoutToInflate, int targetLayout){
        ViewGroup parentView = findViewById(targetLayout);
        parentView.removeAllViews();
        final ConstraintLayout innerArea = (ConstraintLayout)
                inflater.inflate(layoutToInflate, parentView, false);
        parentView.addView(innerArea);

        switch (layoutToInflate) {
            case R.layout.answer_mc:
                ((Guideline) findViewById(R.id.guidelineBetweenQuestionAnswer))
                        .setGuidelinePercent(0.3f);
                break;
            case R.layout.answer_itemcombination:
                setupItemCombinationQuestion();
                break;
        }
    }

    private void setupItemCombinationQuestion() {
        ((Guideline) findViewById(R.id.guidelineBetweenQuestionAnswer))
                .setGuidelinePercent(0.35f);

        int[] radioGroups = {R.id.upperGridRow1, R.id.upperGridRow2, R.id.lowerGridRow1, R.id.lowerGridRow2};
        Map<Integer, ArrayList<Integer>> buttonIDs = getButtonIDMap();

        for (int radioGroupID : radioGroups) {
            RadioGroup radioGroup = (RadioGroup) findViewById(radioGroupID);
            radioGroup.setOnCheckedChangeListener(itemGridRadioGroupCheckedChangeListener(buttonIDs));
        }

    }

    @NotNull
    private RadioGroup.OnCheckedChangeListener itemGridRadioGroupCheckedChangeListener(Map<Integer, ArrayList<Integer>> buttonIDs) {
        return new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Log.d("#######Group#####", " " + group.getId());
                Log.d("########checkedId#####", " " + checkedId);

                // checkedId is the RadioButton selected
                for (Integer buttonId : buttonIDs.get(group.getId())) {
                    findViewById(buttonId).setAlpha(0.5f);
                }
                if (checkedId == -1)
                    return;

                findViewById(checkedId).setAlpha(1);
                switch (group.getId()) {
                    case R.id.upperGridRow1:
                        uncheckOtherItemGridRow(R.id.upperGridRow2);
                        break;
                    case R.id.upperGridRow2:
                        uncheckOtherItemGridRow(R.id.upperGridRow1);
                        break;
                    case R.id.lowerGridRow1:
                        uncheckOtherItemGridRow(R.id.lowerGridRow2);
                        break;
                    case R.id.lowerGridRow2:
                        uncheckOtherItemGridRow(R.id.lowerGridRow1);
                        break;
                }

                Log.d("####CheckedInGroup1", " " + ((RadioGroup) findViewById(R.id.upperGridRow1)).getCheckedRadioButtonId());
            }

            private void uncheckOtherItemGridRow(int otherRowId) {
                RadioGroup otherRowRadioGroup = (RadioGroup) findViewById(otherRowId);
                int checkedIdOtherRow = otherRowRadioGroup.getCheckedRadioButtonId();
                if (checkedIdOtherRow != -1) {
                    RadioButton checkedButtonRow1 = findViewById(checkedIdOtherRow);
                    checkedButtonRow1.setAlpha(0.5f);
                    otherRowRadioGroup.setOnCheckedChangeListener(null);
                    otherRowRadioGroup.clearCheck();
                    otherRowRadioGroup.setOnCheckedChangeListener(this);
                }
            }
        };
    }

    @NotNull
    private Map<Integer, ArrayList<Integer>> getButtonIDMap() {
        Map<Integer, ArrayList<Integer>> buttonIDs = new HashMap<Integer, ArrayList<Integer>>();
        buttonIDs.put(R.id.upperGridRow1, new ArrayList<Integer>(
                Arrays.asList(R.id.buttonSword1, R.id.buttonBow1, R.id.buttonRod1, R.id.buttonTear1)));
        buttonIDs.put(R.id.upperGridRow2, new ArrayList<Integer>(
                Arrays.asList(R.id.buttonVest1, R.id.buttonBelt1, R.id.buttonCloak1, R.id.buttonSpatula1)));
        buttonIDs.put(R.id.lowerGridRow1, new ArrayList<Integer>(
                Arrays.asList(R.id.buttonSword2, R.id.buttonBow2, R.id.buttonRod2, R.id.buttonTear2)));
        buttonIDs.put(R.id.lowerGridRow2, new ArrayList<Integer>(
                Arrays.asList(R.id.buttonVest2, R.id.buttonBelt2, R.id.buttonCloak2, R.id.buttonSpatula2)));
        return buttonIDs;
    }


    private void testChangingGuideline() {
        setContentView(R.layout.activity_task);
//        Button buttonBack = findViewById(R.id.backButton);
//        buttonBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Guideline guidelineAboveAds = findViewById(R.id.guidelineAboveAds);
//                guidelineAboveAds.setGuidelinePercent(0.3f);
//            }
//        });
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.app_bar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        setSupportActionBar(toolbar);
    }


    private void DBTester() {
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
