package main.model;

import com.google.gson.annotations.SerializedName;

/**
 * Class representing the data for an enigma challenge.
 */
public class EnigmaData {
    /** The enigma question */
    private final String question;
    /** The correct answer to the enigma */
    private final String answer;
    /** An array of wrong answers */
    @SerializedName("wrong_answers")
    private String[] wrongAnswers;

    /**
     * Constructs an EnigmaData object with the given question, answer, and wrong answers.
     * @param question the enigma question
     * @param answer the correct answer
     * @param wrongAnswers an array of wrong answers
     */
    public EnigmaData(String question, String answer, String[] wrongAnswers) {
        this.question = question;
        this.answer = answer;
        this.wrongAnswers = wrongAnswers;
    }

    /**
     * Gets the enigma question.
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Gets the correct answer to the enigma.
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Gets the array of wrong answers.
     * @return the wrong answers
     */
    public String[] getWrongAnswers() {
        return wrongAnswers;
    }
}
