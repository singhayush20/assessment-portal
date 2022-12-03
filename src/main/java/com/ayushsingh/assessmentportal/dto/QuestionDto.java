package com.ayushsingh.assessmentportal.dto;

import javax.validation.constraints.NotBlank;



public class QuestionDto {
    
    private Long questionId;
    @NotBlank(message = "Question descp cannot be empty")
    private String content;
    private String image;

    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String answer;
    private QuizDto quiz;
    //add the submittedAnswer field which will
    //be used to evaluate the quiz
    private String submittedAnswer;
    public Long getQuestionId() {
        return questionId;
    }
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getOption1() {
        return option1;
    }
    public void setOption1(String option1) {
        this.option1 = option1;
    }
    public String getOption2() {
        return option2;
    }
    public void setOption2(String option2) {
        this.option2 = option2;
    }
    public String getOption3() {
        return option3;
    }
    public void setOption3(String option3) {
        this.option3 = option3;
    }
    public String getOption4() {
        return option4;
    }
    public void setOption4(String option4) {
        this.option4 = option4;
    }

    //This will be used during serialization, therefore
    //igone the answer
    // @JsonIgnore
    public String getAnswer() {
        return answer;
    }

    //required during de-serialization, we need the value
    //json to object
    // @JsonProperty("answer")
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public QuizDto getQuiz() {
        return quiz;
    }
    public void setQuiz(QuizDto quiz) {
        this.quiz = quiz;
    }
    public String getSubmittedAnswer() {
        return submittedAnswer;
    }
    public void setSubmittedAnswer(String submittedAnswer) {
        this.submittedAnswer = submittedAnswer;
    }

    
}
