package kr.jjh.quiz1;

public class TextQuestion extends Question {
    private String questionString;
    private String answerString;

    @Override
    public boolean correctAnswer() {
        return questionString.equals(answerString);
    }

    private void setAnswer(String answerString) {
        this.answerString = answerString;
    }
}
