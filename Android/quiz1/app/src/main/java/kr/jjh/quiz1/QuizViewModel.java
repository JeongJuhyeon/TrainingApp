package kr.jjh.quiz1;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class QuizViewModel extends AndroidViewModel {
    private QuizDataRepository repository;

    private final List<Question> allQuestions;
    private final List<Answer> allAnswers;
    private final List<Item> allItems;

    public QuizViewModel(Application application) {
        super(application);
        repository = new QuizDataRepository(application);

        allQuestions = repository.getAllQuestions();
        allAnswers = repository.getAllAnswers();
        allItems = repository.getAllItems();
    }

    List<Question> getAllQuestions(){return allQuestions; };
    List<Item> getAllItems(){return allItems; }
    List<Answer> getAllAnswers(){return allAnswers; }

    List<Item> getCompletedItems(){return repository.getCompletedItems(); }
    List<Question> getQuestionsOfType(String type){return repository.getQuestionsOfType(type); }
    List<Answer> getAnswersOfQuestionWithId(int id){return repository.getAnswersOfQuestionWithId(id); }
}
