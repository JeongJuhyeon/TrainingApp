package kr.jjh.quiz1;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class QuizDataRepository {
    private QuestionDao questionDao;
    private AnswerDao answerDao;
    private ItemDao itemDao;

    private List<Question> allQuestions;
    private List<Answer> allAnswers;
    private List<Item> allItems;

    public QuizDataRepository(Application application) {
        QuizDatabase db = QuizDatabase.getDatabase(application);
        questionDao = db.questionDao();
        answerDao = db.answerDao();
        itemDao = db.itemDao();

        try {
            allQuestions = new getAllQuestionsTask(questionDao).execute().get();
            allAnswers = new getAllAnswersTask(answerDao).execute().get();
            allItems = new getAllItemsTask(itemDao).execute().get();
        } catch (ExecutionException e) {
            Log.e("QuizDataRepository", e.getLocalizedMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.e("QuizDataRepository", e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    List<Question> getAllQuestions() {
        return allQuestions;
    }

    List<Item> getAllItems() {
        return allItems;
    }

    List<Answer> getAllAnswers() {
        return allAnswers;
    }

    List<Question> getQuestionsOfType(String type) {
        try {
            return new getQuestionsOfTypeTask(questionDao).execute(type).get();
        } catch (ExecutionException e) {
            Log.e("QuizDataRepository", e.getLocalizedMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.e("QuizDataRepository", e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }

    List<Answer> getAnswersOfQuestionWithId(int id) {
        try {
            return new getAnswersOfQuestionWithIdTask(answerDao).execute(id).get();
        } catch (ExecutionException e) {
            Log.e("QuizDataRepository", e.getLocalizedMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.e("QuizDataRepository", e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }

    List<Item> getCompletedItems() {
        try {
            return new getCompletedItemsTask(itemDao).execute().get();
        } catch (ExecutionException e) {
            Log.e("QuizDataRepository", e.getLocalizedMessage());
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.e("QuizDataRepository", e.getLocalizedMessage());
            e.printStackTrace();
        }
        return null;
    }

    private static class getCompletedItemsTask extends AsyncTask<Void, Integer, List<Item>> {
        private ItemDao asyncTaskDao;

        public getCompletedItemsTask(ItemDao asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected List<Item> doInBackground(Void... voids) {
            List<Item> completedItems = asyncTaskDao.getCompletedItems();
            return completedItems;
        }
    }

    private static class getQuestionsOfTypeTask extends AsyncTask<String, Void, List<Question>> {
        private QuestionDao asyncTaskDao;

        public getQuestionsOfTypeTask(QuestionDao asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected List<Question> doInBackground(String... params) {
            return asyncTaskDao.getQuestionsOfType(params[0]);
        }
    }

    private static class getAnswersOfQuestionWithIdTask extends AsyncTask<Integer, Void, List<Answer>> {
        private AnswerDao asyncTaskDao;

        public getAnswersOfQuestionWithIdTask(AnswerDao asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected List<Answer> doInBackground(Integer... params) {
            return asyncTaskDao.getAnswersOfQuestionWithId(params[0]);
        }
    }

    private static class getAllItemsTask extends AsyncTask<Void, Integer, List<Item>> {
        private ItemDao asyncTaskDao;

        public getAllItemsTask(ItemDao asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected List<Item> doInBackground(Void... voids) {
            return asyncTaskDao.getAllItems();
        }
    }

    private static class getAllQuestionsTask extends AsyncTask<Void, Integer, List<Question>> {
        private QuestionDao asyncTaskDao;

        public getAllQuestionsTask(QuestionDao asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected List<Question> doInBackground(Void... voids) {
            return asyncTaskDao.getAllQuestions();
        }
    }

    private static class getAllAnswersTask extends AsyncTask<Void, Integer, List<Answer>> {
        private AnswerDao asyncTaskDao;

        public getAllAnswersTask(AnswerDao asyncTaskDao) {
            this.asyncTaskDao = asyncTaskDao;
        }

        @Override
        protected List<Answer> doInBackground(Void... voids) {
            return asyncTaskDao.getAllAnswers();
        }
    }
}
