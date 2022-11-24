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
import org.springframework.web.bind.annotation.RestController;

import com.ayushsingh.assessmentportal.constants.AppConstants;
import com.ayushsingh.assessmentportal.dto.CategoryDto;
import com.ayushsingh.assessmentportal.exceptions.ApiResponse;
import com.ayushsingh.assessmentportal.exceptions.SuccessResponse;
import com.ayushsingh.assessmentportal.service.CategoryService;

@RestController
@RequestMapping("assessmentportal/category")
public class CategoryController {
    
    @Autowired
    CategoryService categoryService;
    @PostMapping("/create/{adminId}")
    public ResponseEntity<SuccessResponse<CategoryDto>> createCategory(@RequestBody CategoryDto categoryDto,@PathVariable("adminId") Long adminId){
        categoryDto=this.categoryService.addCategory(categoryDto);
        SuccessResponse<CategoryDto> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,categoryDto);
        return new ResponseEntity<SuccessResponse<CategoryDto>>(successResponse,HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<SuccessResponse<CategoryDto>> updateCategory(@RequestBody CategoryDto categoryDto){
        categoryDto=this.categoryService.updateCategory(categoryDto,categoryDto.getCategoryId().toString());
        SuccessResponse<CategoryDto> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,categoryDto);
        return new ResponseEntity<SuccessResponse<CategoryDto>>(successResponse,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable(name = "categoryId") String categoryId){
       this.categoryService.deleteCategory(categoryId);
       return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.SUCCESS_CODE, "Category Deleted Successfully", AppConstants.SUCCESS_MESSAGE), HttpStatus.OK);
    }
    @GetMapping("/")
    public ResponseEntity<SuccessResponse<List<CategoryDto>>> getCategories(){
        List<CategoryDto> categoryDtos=this.categoryService.getCategories();
        SuccessResponse<List<CategoryDto>> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,categoryDtos);
        return new ResponseEntity<SuccessResponse<List<CategoryDto>>>(successResponse,HttpStatus.OK);
    }

    @GetMapping(value="/{categoryId}")
    public ResponseEntity<SuccessResponse<CategoryDto>>  getCategoryById(@PathVariable(name = "categoryId") String categoryId) {
        CategoryDto categoryDto=this.categoryService.getCategoryById(categoryId);
        SuccessResponse<CategoryDto> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,categoryDto);
        return new ResponseEntity<SuccessResponse<CategoryDto>>(successResponse,HttpStatus.OK);
    }
    
    
}
