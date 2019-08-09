package kr.jjh.quiz1;

import android.provider.BaseColumns;

public final class QuestionsContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private QuestionsContract() {}

    /* Inner class that defines the table contents */
    public static class QuestionEntry implements BaseColumns {
        public static final String TABLE_NAME = "questions";
        public static final String COLUMN_NAME_QUESTIONSTR = "QuestionString";
        public static final String COLUMN_NAME_QUESTIONIMG = "QuestionImage";
        public static final String COLUMN_NAME_NOANSWERS = "NumberOfAnswers";
    }
}