package com.ayushsingh.assessmentportal.controller;

import java.util.List;


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
import com.ayushsingh.assessmentportal.dto.QuizDto;
import com.ayushsingh.assessmentportal.exceptions.ApiResponse;
import com.ayushsingh.assessmentportal.exceptions.SuccessResponse;
import com.ayushsingh.assessmentportal.service.CategoryService;
import com.ayushsingh.assessmentportal.service.QuizService;
import com.ayushsingh.assessmentportal.service.UserService;

@RestController
@RequestMapping("assessmentportal/quiz")
public class QuizController {
   
    @Autowired
    QuizService quizService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse<QuizDto>> create(@RequestBody QuizDto quizDto,@RequestParam("userid") Long userid){
        System.out.println("QuizController: Creating quiz: "+quizDto+" userid: "+userid+" time: "+quizDto.getTime());
        quizDto=this.quizService.addQuiz(quizDto,userid);
        SuccessResponse<QuizDto> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,quizDto);
        return new ResponseEntity<SuccessResponse<QuizDto>>(successResponse,HttpStatus.OK);
    }
    @PutMapping("/update")
public ResponseEntity<SuccessResponse<QuizDto>> updateQuiz(@RequestBody QuizDto quizDto){
    quizDto=this.quizService.updateQuiz(quizDto,quizDto.getQuizId().toString());
    SuccessResponse<QuizDto> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,quizDto);
    return new ResponseEntity<SuccessResponse<QuizDto>>(successResponse,HttpStatus.OK);
}
    @GetMapping("/")
public ResponseEntity<SuccessResponse<List<QuizDto>>> getQuizzes(){
    List<QuizDto> quizDtos=this.quizService.getQuizzes();
    SuccessResponse<List<QuizDto>> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,quizDtos);
    return new ResponseEntity<SuccessResponse<List<QuizDto>>>(successResponse,HttpStatus.OK);

}
    @GetMapping("/{quizId}")
public ResponseEntity<SuccessResponse<QuizDto>> getQuizById(@PathVariable(name="quizId") String quizId){
    QuizDto quizDto=this.quizService.getQuizById(quizId);
    SuccessResponse<QuizDto> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,quizDto);
    return new ResponseEntity<SuccessResponse<QuizDto>>(successResponse,HttpStatus.OK);
}
    @DeleteMapping("/delete/{quizId}")
    public ResponseEntity<ApiResponse> deleteQuiz(@PathVariable String quizId){
        this.quizService.deleteQuiz(quizId);
        return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.SUCCESS_CODE, "Quiz deleted Successfully", quizId), HttpStatus.OK);
    }

    @GetMapping("/getByCategory/{categoryId}")
    public ResponseEntity<SuccessResponse<List<QuizDto>>> getQuizzesForCategory(@PathVariable("categoryId") Long categoryId){
        List<QuizDto> quizzes=this.categoryService.getQuizzes(categoryId);
        SuccessResponse<List<QuizDto>> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,quizzes);
        return new ResponseEntity<SuccessResponse<List<QuizDto>>>(successResponse,HttpStatus.OK);
    }

    @GetMapping("/getByAdmin")
    public ResponseEntity<SuccessResponse<List<QuizDto>>> getQuizzesForAdminByCategory(@RequestParam("adminid") Long adminid,@RequestParam("categoryid") Long categoryId){
       List<QuizDto> quizzes=this.userService.getQuizzesByAdminAndCategory(adminid,categoryId);
        SuccessResponse<List<QuizDto>> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,quizzes);
        return new ResponseEntity<SuccessResponse<List<QuizDto>>>(successResponse,HttpStatus.OK);
    }
    
    @GetMapping("/active")
    public ResponseEntity<SuccessResponse<List<QuizDto>>> getQuizzesForAdminByCategory(@RequestParam("categoryId") Long categoryId){
        List<QuizDto> quizzes=this.quizService.getActiveQuizzesByCategory(categoryId);
         SuccessResponse<List<QuizDto>> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,quizzes);
         return new ResponseEntity<SuccessResponse<List<QuizDto>>>(successResponse,HttpStatus.OK);
     }
}
