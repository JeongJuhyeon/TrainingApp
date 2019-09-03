package kr.jjh.quiz1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ItemCombinationQuiz {
    private QuizViewModel quizViewModel;
    private QuizActivity quizActivity;

    private List<Question> allItemQuestions;
    private List<Question> questionsToSolve;
    private List<Item> allItems;

    private Question currentQuestion;
    private Item currentQuestionItem;

    private static final List<Integer> BUTTON_ID_ORDER  = Arrays.asList(R.id.buttonSword1, R.id.buttonBow1, R.id.buttonRod1, R.id.buttonTear1,
            R.id.buttonVest1, R.id.buttonCloak1, R.id.buttonBelt1, R.id.buttonSpatula1,
            R.id.buttonSword2, R.id.buttonBow2, R.id.buttonRod2, R.id.buttonTear2,
            R.id.buttonVest2, R.id.buttonCloak2, R.id.buttonBelt2, R.id.buttonSpatula2);

    public ItemCombinationQuiz(QuizViewModel quizViewModel, QuizActivity quizActivity) {
        this.quizViewModel = quizViewModel;
        this.quizActivity = quizActivity;

        this.allItemQuestions = quizViewModel.getQuestionsOfType("textandimage");
        this.allItems = quizViewModel.getAllItems();
        this.questionsToSolve = drawRandomQuestions(10);
    }

    public void runQuiz(){
        quizActivity.inflateConstraintLayout(R.layout.question_textandimage, R.id.questionLayout);
        quizActivity.inflateConstraintLayout(R.layout.answer_itemcombination, R.id.answerLayout);

        MediaPlayer mpGood = MediaPlayer.create(quizActivity.getApplicationContext(), R.raw.success);

        Button buttonSubmit = quizActivity.findViewById(R.id.checkCombinationButton);
        buttonSubmit.setOnClickListener(view -> {
            boolean gotItRight = selectedCombinationIsCorrect(currentQuestionItem);
            if (gotItRight) {
                mpGood.start();
                Log.d("##########", "CORRECT!");
            }
            else {
                Log.d("##########", "WRONG!");
            }

            setNextCurrentQuestion();
            clearSelections();
        });

        setNextCurrentQuestion();
    }

    private void setNextCurrentQuestion(){
        currentQuestion = questionsToSolve.get(0);
        try {
            Answer currentAnswer = quizViewModel.getAnswersOfQuestionWithId(currentQuestion.id).get(0);
            currentQuestionItem = allItems.get(currentAnswer.itemID - 1);
        }
        catch (IndexOutOfBoundsException e) {
            Log.e("ItemCombinationQuiz", "#####" + e.getMessage());
            Log.d("questionsToSolve l", "" + questionsToSolve.size());
            System.exit(-1);
        }

        TextView questionText = quizActivity.findViewById(R.id.questionText);
        questionText.setText(currentQuestion.questionString);
        ImageView questionImage = quizActivity.findViewById(R.id.questionImage);
        questionImage.setImageDrawable(Util.getDrawable(quizActivity.getApplicationContext(),
                currentQuestion.drawable));

        questionsToSolve.remove(currentQuestion);
    }

    private boolean selectedCombinationIsCorrect(Item currentQuestionItemLocal){
        RadioGroup upperGridRow1 = quizActivity.findViewById(R.id.upperGridRow1);
        RadioGroup upperGridRow2 = quizActivity.findViewById(R.id.upperGridRow2);
        RadioGroup lowerGridRow1 = quizActivity.findViewById(R.id.lowerGridRow1);
        RadioGroup lowerGridRow2 = quizActivity.findViewById(R.id.lowerGridRow2);

        int clickedIngredientA = Math.max(upperGridRow1.getCheckedRadioButtonId(),
                                             upperGridRow2.getCheckedRadioButtonId());
        int clickedIngredientB = Math.max(lowerGridRow1.getCheckedRadioButtonId(),
                lowerGridRow2.getCheckedRadioButtonId());

        clickedIngredientA = BUTTON_ID_ORDER.indexOf(clickedIngredientA) + 1;
        clickedIngredientB = BUTTON_ID_ORDER.indexOf(clickedIngredientB) - 8 + 1;


        return (clickedIngredientA == currentQuestionItemLocal.idIngredientA &&
                clickedIngredientB == currentQuestionItemLocal.idIngredientB) ||
               (clickedIngredientA == currentQuestionItemLocal.idIngredientB &&
                clickedIngredientB == currentQuestionItemLocal.idIngredientA);
    }

    private List<Question> drawRandomQuestions(int n){
        List<Question> randomItemQuestions = new ArrayList<>();
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < allItemQuestions.size(); i++) {
            list.add(Integer.valueOf(i));
        }

        Collections.shuffle(list);
        for (int i = 0; i < n; i++) {
            randomItemQuestions.add(allItemQuestions.get(list.get(i)));
        }
        return randomItemQuestions;
    }

    private void clearSelections() {
        int[] Radiogroups = {R.id.upperGridRow1, R.id.upperGridRow2, R.id.lowerGridRow1, R.id.lowerGridRow2};
        RadioGroup radioGroup;

        for (int radiogroupID : Radiogroups) {
            radioGroup = quizActivity.findViewById(radiogroupID);
            radioGroup.clearCheck();
        }
        Button button;
        for (Integer buttonId : BUTTON_ID_ORDER) {
            button = quizActivity.findViewById(buttonId);
            button.setAlpha(0.5f);
        }
    }
}
