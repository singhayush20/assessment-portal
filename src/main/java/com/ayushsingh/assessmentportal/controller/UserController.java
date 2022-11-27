package com.ayushsingh.assessmentportal.controller;



import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayushsingh.assessmentportal.constants.AppConstants;
import com.ayushsingh.assessmentportal.dto.CategoryDto;
import com.ayushsingh.assessmentportal.dto.UserDto;
import com.ayushsingh.assessmentportal.exceptions.ApiResponse;
import com.ayushsingh.assessmentportal.exceptions.SuccessResponse;
import com.ayushsingh.assessmentportal.model.User;
import com.ayushsingh.assessmentportal.service.UserService;

@RestController
@RequestMapping("/assessmentportal/users")
public class UserController {
    private final String CLASS_NAME=UserController.class.getName();
    @Autowired
    private UserService userService;

	

    // @PostMapping("/create")
    // public ResponseEntity<SuccessResponse<UserDto>> createUser(@Valid @RequestBody UserDto userDto) throws Exception {
        
        
	// 	UserDto newuser=this.userService.createUser(userDto);
	// 	SuccessResponse<UserDto> respone=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,newuser);
	// 	return new ResponseEntity<SuccessResponse<UserDto>>(respone,HttpStatus.OK);
    // }

	@GetMapping("/{email}")
	public ResponseEntity<SuccessResponse<UserDto>> getUser(@PathVariable(name = "email") String email){
	
			// UserDto user = this.userService.findByUsername(username);
			System.out.println(CLASS_NAME+"Fetching user for email: "+email);
			UserDto user=this.userService.findByEmail(email);
			SuccessResponse<UserDto> response=new SuccessResponse<UserDto>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,user);
		return new ResponseEntity<SuccessResponse<UserDto>>(response,HttpStatus.OK);
	}
	@GetMapping("/details/{username}")
	public ResponseEntity<SuccessResponse<User>> getCompleteUser(@PathVariable(name = "username") String username){
	
			// UserDto user = this.userService.findByUsername(username);
			User user=this.userService.getUserByUsername(username);
			SuccessResponse<User> response=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,user);
		return new ResponseEntity<SuccessResponse<User>>(response,HttpStatus.OK);
	}
	// @DeleteMapping("/delete/username/{username}")
	// public ResponseEntity<ApiResponse> deleteUser(@PathVariable(name="username") String username){
	// 	this.userService.deleteUserByUsername(username);
	// 	return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.SUCCESS_CODE, "User deleted successfully", AppConstants.SUCCESS_MESSAGE), HttpStatus.OK);
	// }
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse> deleteUserbyId(@PathVariable(name="id") String id){
		this.userService.deleteUserbyId(id);
		return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.SUCCESS_CODE, "User deleted successfully", AppConstants.SUCCESS_MESSAGE), HttpStatus.OK);
	}

	@PutMapping("/update/{id}")
	
	public ResponseEntity<SuccessResponse<UserDto>> updateUser(@Valid @RequestBody UserDto userDto,@PathVariable(name="id") String id) throws Exception {
        
        
		UserDto newuser=this.userService.updateUser(userDto, id);
		SuccessResponse<UserDto> respone=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,newuser);
		return new ResponseEntity<SuccessResponse<UserDto>>(respone,HttpStatus.OK);
    }

	@GetMapping("/enrolledcategories/all/{userid}")
	public ResponseEntity<SuccessResponse<List<CategoryDto>>> getAllEnrolledCategories(@PathVariable(name="userid") Long userid){
		List<CategoryDto> categoryDtos=this.userService.getEnrolledCategories(userid);
		SuccessResponse<List<CategoryDto>> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE,AppConstants.SUCCESS_MESSAGE,categoryDtos);
		return new ResponseEntity<SuccessResponse<List<CategoryDto>>>(successResponse,HttpStatus.OK);
	}

	@PutMapping("/enrolledcategories/all/add")
	public ResponseEntity<ApiResponse> addUserbyIdToCategory(@RequestParam(name = "userid") Long userid, @RequestParam(name="categoryid") Long categoryid){
		this.userService.addEnrolledCategory(userid, categoryid);
		return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.SUCCESS_CODE, "Category added successfully", AppConstants.SUCCESS_MESSAGE), HttpStatus.OK);
	}
	
}
