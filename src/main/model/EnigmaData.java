package main.model;

import com.google.gson.annotations.SerializedName;

public class EnigmaData {
    private final String question;
    private final String answer;
    @SerializedName("wrong_answers")
    private String[] wrongAnswers;

    public EnigmaData(String question, String answer, String[] wrongAnswers) {
        this.question = question;
        this.answer = answer;
        this.wrongAnswers = wrongAnswers;
    }

    public String getQuestion() {
        return question;
    }
    public String getAnswer() {
        return answer;
    }
    public String[] getWrongAnswers() {
        return wrongAnswers;
    }
}
