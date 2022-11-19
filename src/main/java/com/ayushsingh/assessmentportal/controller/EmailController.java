package com.ayushsingh.assessmentportal.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayushsingh.assessmentportal.constants.AppConstants;
import com.ayushsingh.assessmentportal.exceptions.ApiResponse;
import com.ayushsingh.assessmentportal.model.User;
import com.ayushsingh.assessmentportal.repository.UserRepository;
import com.ayushsingh.assessmentportal.service.EmailService;
import com.ayushsingh.assessmentportal.service.OtpService;

@RestController
@RequestMapping("/assessmentportal/authenticate/verifyemail")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private OtpService otpService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    // Send OTP for email verification
    @PostMapping("/sendotp")
    public ResponseEntity<ApiResponse> sendOTP(@RequestParam("email") String email, HttpSession session) {

     
        System.out.println("Sending OTP for email id: " + email);
        
        //generate the otp
        int otp=this.otpService.generateOTP(email);
        String subject = "Email Verification OTP";
        String message = "" +
                "<div style='border:1px solid #e2e2e2; padding:20px'>"
                + "<h2>"
                + "Your email verification OTP is "
                + "<b>"
                + otp
                + "</b>"
                + "</h2>"
                + "</div>";
        // session.setAttribute("storedOTP", otp);
        // session.setAttribute("email", email);
        boolean result = this.emailService.sendEmail(subject, message, email);
        return (result)
                ? new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.SUCCESS_CODE,
                        "Otp sent successfully on email", AppConstants.SUCCESS_MESSAGE), HttpStatus.OK)
                : new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.FAILURE_CODE,
                        "Otp not sent due to some problem!", AppConstants.FAILURE_MESSAGE), HttpStatus.OK);
    }

    // Verify OTP for email verification
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse> verifyOtp(@RequestParam("otp") int otp, @RequestParam("email") String email) {
        boolean flag = false;
        // int myOtp = (int) session.getAttribute("storedOTP");
        // String email=(String)session.getAttribute("email");
        // if (myOtp == otp) {
        //     System.out.println("Otp is verified");
        //     flag = true;
        // }
        int storedOTP=this.otpService.getOTP(email);
        System.out.println("Stored OTP: "+storedOTP+" sent otp: "+otp);
        if(storedOTP==otp){
                System.out.println("OTP is verified");
                flag=true;
        }
        ApiResponse apiResponse = (flag)
                ? new ApiResponse(AppConstants.SUCCESS_CODE, "Email verified successfully",
                        AppConstants.SUCCESS_MESSAGE)
                : new ApiResponse(AppConstants.FAILURE_CODE, "Email verification failed!",
                        AppConstants.FAILURE_MESSAGE);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    // Send OTP for password reset
    @PostMapping("/reset-password-otp")
    public ResponseEntity<ApiResponse> resetPasswordOTP(@RequestParam("email") String email) {
        boolean result = false;
        System.out.println("Sending OTP on email id: " + email);
        List<User> userList = userRepository.findByEmail(email);
        if (userList.size() != 0) {
            // Generate random no.
        //     Random random = new Random(1000);
        //     int otp = random.nextInt(999999);
        //     System.out.println("Generated OTP: " + otp);
        int otp=this.otpService.generateOTP(email);
            String subject = "Reset Password OTP";
            String message = "" +
                    "<div style='border:1px solid #e2e2e2; padding:20px'>"
                    + "<h2>"
                    + "Your verification OTP is "
                    + "<b>"
                    + otp
                    + "</b>"
                    + "</h2>"
                    + "</div>";
        //     session.setAttribute("storedOTP", otp);
            // session.setAttribute("email", email);
            result = this.emailService.sendEmail(subject, message, email);
        }
        return (result)
                ? new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.SUCCESS_CODE,
                        "Otp sent successfully on email", AppConstants.SUCCESS_MESSAGE), HttpStatus.OK)
                : new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.FAILURE_CODE,
                        "Otp not sent due to some problem!", AppConstants.FAILURE_MESSAGE), HttpStatus.OK);
    }
    //Reset passoword, send the email id, new password and the otp
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPasswordOTP(@RequestParam("otp") int otp, @RequestParam("email") String email,@RequestParam("password") String password, HttpSession session) {
        boolean flag = false;
        // int myOtp = (int) session.getAttribute("storedOTP");
        // String email=(String)session.getAttribute("email");
        int storedOTP=this.otpService.getOTP(email);
       
        if (storedOTP == otp) {
            System.out.println("Otp is verified");
            List<User> userList=userRepository.findByEmail(email);
            User user=userList.get(0);
            System.out.println("User found: "+user.getEmail()+" "+user.getUserId());
            String encryptedPassword=passwordEncoder.encode(password);
            System.out.println("New encrypted password: "+encryptedPassword);
            user.setPassword(encryptedPassword);
            userRepository.save(user);
            flag = true;
        }
        ApiResponse apiResponse = (flag)
                ? new ApiResponse(AppConstants.SUCCESS_CODE, "Password Reset Successfully",
                        AppConstants.SUCCESS_MESSAGE)
                : new ApiResponse(AppConstants.FAILURE_CODE, "Password updation failed!",
                        AppConstants.FAILURE_MESSAGE);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

}
