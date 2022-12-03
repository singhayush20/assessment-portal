package com.ayushsingh.assessmentportal.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayushsingh.assessmentportal.constants.AppConstants;
import com.ayushsingh.assessmentportal.dto.QuestionDto;
import com.ayushsingh.assessmentportal.exceptions.ApiResponse;
import com.ayushsingh.assessmentportal.exceptions.SuccessResponse;
import com.ayushsingh.assessmentportal.service.QuestionService;
import com.ayushsingh.assessmentportal.service.QuizService;

@RestController
@RequestMapping("assessmentportal/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    QuizService quizService;

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse<QuestionDto>> createCategory(@RequestBody QuestionDto questionDto) {
        questionDto = this.questionService.addQuestion(questionDto);
        SuccessResponse<QuestionDto> successResponse = new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, questionDto);
        return new ResponseEntity<SuccessResponse<QuestionDto>>(successResponse, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<SuccessResponse<QuestionDto>> updateCategory(@RequestBody QuestionDto questionDto) {
        questionDto = this.questionService.updaQuestion(questionDto);
        SuccessResponse<QuestionDto> successResponse = new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, questionDto);
        return new ResponseEntity<SuccessResponse<QuestionDto>>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{questionId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable(name = "questionId") String questionId) {
        this.questionService.deleteQuestion(questionId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.SUCCESS_CODE,
                "Question Deleted Successfully", AppConstants.SUCCESS_MESSAGE), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<SuccessResponse<List<QuestionDto>>> getQuestions() {
        List<QuestionDto> questionDtos = this.questionService.getQuestions();
        SuccessResponse<List<QuestionDto>> successResponse = new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, questionDtos);
        return new ResponseEntity<SuccessResponse<List<QuestionDto>>>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<SuccessResponse<List<QuestionDto>>> getQuestionsByQuiz(@PathVariable Long quizId) {

        List<QuestionDto> questionList = this.questionService.getQuestionsOfQuiz(quizId);
        SuccessResponse<List<QuestionDto>> successResponse = new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, questionList);
        return new ResponseEntity<SuccessResponse<List<QuestionDto>>>(successResponse, HttpStatus.OK);
    }

    // evaluate quiz on server
    @PostMapping("/evaluate-quiz")
    public ResponseEntity<?> evalQuiz(@RequestBody List<QuestionDto> questions) {

        int marksObtained = 0;
        int correctAnswers = 0;
        int attempted = 0;
        for (QuestionDto q : questions) {
            QuestionDto question = this.questionService.getQuestionById(q.getQuestionId());
            if (question.getAnswer().trim().equals(q.getSubmittedAnswer())) {
                // increase the number of correct answers
                correctAnswers++;
                double marksSingle = Double.parseDouble(questions.get(0).getQuiz().getMaxMarks())/questions.size();
            } else if (q.getSubmittedAnswer() != null || !q.getSubmittedAnswer().trim().equals("")) {
                attempted++;
            }
        }
        Map<String,Integer> result=Map.of("marks obtained",marksObtained,"correct answers",correctAnswers,"attempted",attempted);
        SuccessResponse<Map<String,Integer>> successResponse=new SuccessResponse<Map<String,Integer>>(AppConstants.SUCCESS_CODE, AppConstants.SUCCESS_MESSAGE, result);
        return new ResponseEntity<SuccessResponse<Map<String,Integer>>>(successResponse,HttpStatus.OK);
    }

}
