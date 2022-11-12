package com.ayushsingh.assessmentportal.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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
import com.ayushsingh.assessmentportal.dto.QuizDto;
import com.ayushsingh.assessmentportal.exceptions.ApiResponse;
import com.ayushsingh.assessmentportal.exceptions.SuccessResponse;
import com.ayushsingh.assessmentportal.model.Quiz;
import com.ayushsingh.assessmentportal.service.QuestionService;
import com.ayushsingh.assessmentportal.service.QuizService;


@RestController
@RequestMapping("assessmentportal/question")
public class QuestionController {
    
    @Autowired
    QuestionService questionService;
    @Autowired QuizService quizService;
    @PostMapping("/create")
    public ResponseEntity<SuccessResponse<QuestionDto>> createCategory(@RequestBody QuestionDto questionDto){
        questionDto=this.questionService.addQuestion(questionDto);
        SuccessResponse<QuestionDto> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,questionDto);
        return new ResponseEntity<SuccessResponse<QuestionDto>>(successResponse,HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<SuccessResponse<QuestionDto>> updateCategory(@RequestBody QuestionDto questionDto){
        questionDto=this.questionService.updaQuestion(questionDto);
        SuccessResponse<QuestionDto> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,questionDto);
        return new ResponseEntity<SuccessResponse<QuestionDto>>(successResponse,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{questionId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable(name = "questionId") String questionId){
       this.questionService.deleteQuestion(questionId);
       return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.SUCCESS_CODE, "Question Deleted Successfully", AppConstants.SUCCESS_MESSAGE), HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<SuccessResponse<List<QuestionDto>>> getQuestions(){
        List<QuestionDto> questionDtos=this.questionService.getQuestions();
        SuccessResponse<List<QuestionDto>> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,questionDtos);
        return new ResponseEntity<SuccessResponse<List<QuestionDto>>>(successResponse,HttpStatus.OK);
    }

    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<SuccessResponse<List<QuestionDto>>> getQuestionsByQuiz(@PathVariable String quizId){
       QuizDto quizDto=this.quizService.getQuizById(quizId);
        // Set<QuestionDto> questionDtos=this.questionService.getQuestionsOfQuiz(quizDto);
        List<QuestionDto> questionDtos=quizDto.getQuestions();
        List<QuestionDto> questionList=new ArrayList<>(questionDtos);
        //If the number of questions in the quiz is less than the number of questions in
        //the database
        if(questionList.size()>Integer.parseInt(quizDto.getNumberOfQuestions())){
            questionList.subList(0, Integer.parseInt(quizDto.getNumberOfQuestions()+1));
        }
        Collections.shuffle(questionList);//to randomize the order of questions
        SuccessResponse<List<QuestionDto>> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,questionList);
        return new ResponseEntity<SuccessResponse<List<QuestionDto>>>(successResponse,HttpStatus.OK);
    }

    
    
    
}
