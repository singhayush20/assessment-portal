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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayushsingh.assessmentportal.constants.AppConstants;
import com.ayushsingh.assessmentportal.dto.QuestionDto;
import com.ayushsingh.assessmentportal.dto.QuizHistoryDto;
import com.ayushsingh.assessmentportal.exceptions.ApiResponse;
import com.ayushsingh.assessmentportal.exceptions.SuccessResponse;
import com.ayushsingh.assessmentportal.service.QuestionService;
import com.ayushsingh.assessmentportal.service.QuizHistoryService;
import com.ayushsingh.assessmentportal.service.QuizService;

@RestController
@RequestMapping("assessmentportal/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @Autowired
    QuizService quizService;

    @Autowired
    QuizHistoryService quizHistoryService;
    

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
    public ResponseEntity<SuccessResponse<QuizHistoryDto>> evalQuiz(@RequestBody Map<Long, String> questions,
            @RequestParam(name = "quizId") Long quizId, @RequestParam(name = "maxMarks") Long maxMarks,
            @RequestParam(name = "userId") Long userId) {

        int marksObtained = 0;
        int correctAnswers = 0;
        int attempted = 0;
        double marksSingle = maxMarks / questions.size();
        for (Map.Entry<Long, String> entry : questions.entrySet()) {
            QuestionDto question = this.questionService.getQuestionById(entry.getKey());
            if (question.getAnswer().trim().equals(entry.getValue())) {
                // increase the number of correct answers
                correctAnswers++;
                attempted++;
               marksObtained+=marksSingle;
        }
            else if (entry.getValue() != null || !entry.getValue().trim().equals("")) {
            attempted++;
        }
          
    }
    //Save the record to database
    QuizHistoryDto newRecord=new QuizHistoryDto();
    newRecord.setMarksObtained(marksObtained);
    newRecord.setMaxMarks(Integer.parseInt(Long.toString(maxMarks)));
    newRecord.setCorrectQuestions(correctAnswers);
    newRecord.setQuestionsAttempted(attempted);
    newRecord.setNoOfQuestions(questions.size());
        QuizHistoryDto quizHistoryDto=this.quizHistoryService.saveNewRecord(newRecord, userId, quizId);

        // Map<String, Integer> result = Map.of("marks obtained", marksObtained, "correct answers", correctAnswers,
        //         "attempted", attempted);
        SuccessResponse<QuizHistoryDto> successResponse = new SuccessResponse<>(
                AppConstants.SUCCESS_CODE, AppConstants.SUCCESS_MESSAGE, quizHistoryDto);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }


   
}
