package com.ayushsingh.assessmentportal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayushsingh.assessmentportal.constants.AppConstants;
import com.ayushsingh.assessmentportal.dto.QuizHistoryDto;
import com.ayushsingh.assessmentportal.exceptions.ApiResponse;
import com.ayushsingh.assessmentportal.exceptions.SuccessResponse;
import com.ayushsingh.assessmentportal.service.QuizHistoryService;

@RestController
@RequestMapping("assessmentportal/quizresult")
public class QuizHistoryController {

    @Autowired
    private QuizHistoryService quizHistoryService;

    @PostMapping("/save-result")
    public ResponseEntity<?> saveResult(@RequestBody QuizHistoryDto quizHistoryDto,@RequestParam(name="userid") Long userid, @RequestParam(name="quizid") Long quizid) {
        QuizHistoryDto qHistoryDto = this.quizHistoryService.saveNewRecord(quizHistoryDto,userid,quizid);

        SuccessResponse<QuizHistoryDto> successResponse = new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, qHistoryDto);
        return new ResponseEntity<SuccessResponse<QuizHistoryDto>>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/getByUser")
    public ResponseEntity<?> getResultsForUser(@RequestParam(name = "userId") Long userId) {
        List<QuizHistoryDto> quizzes = this.quizHistoryService.getQuizzesByUserId(userId);
        SuccessResponse<List<QuizHistoryDto>> successResponse = new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, quizzes);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/getByQuiz")
    public ResponseEntity<?> getResultsByQuiz(@RequestParam(name="quizId")Long quizId){
        List<QuizHistoryDto> quizzes=this.quizHistoryService.getAllUsersForQuiz(quizId);
        SuccessResponse<List<QuizHistoryDto>> successResponse = new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, quizzes);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/check-attempt")
    public ResponseEntity<?> checkIfQuizIsAttempted(@RequestParam(name="quizId") Long quizId, @RequestParam(name="userId") Long userId){
        boolean result=this.quizHistoryService.checkIfQuizIsAttempted(quizId, userId);
        ApiResponse successResponse=new ApiResponse(AppConstants.SUCCESS_CODE, AppConstants.SUCCESS_MESSAGE, Boolean.toString(result));
        return new ResponseEntity<ApiResponse>(successResponse, HttpStatus.OK);
    }
}
