package com.ayushsingh.assessmentportal.controller;

import java.security.Principal;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayushsingh.assessmentportal.configs.JwtUtil;
import com.ayushsingh.assessmentportal.constants.AppConstants;
import com.ayushsingh.assessmentportal.dto.UserDto;
import com.ayushsingh.assessmentportal.exceptions.JWTAuthenticationException;
import com.ayushsingh.assessmentportal.exceptions.SuccessResponse;
import com.ayushsingh.assessmentportal.model.JwtRequest;
import com.ayushsingh.assessmentportal.model.JwtResponse;
import com.ayushsingh.assessmentportal.model.User;
import com.ayushsingh.assessmentportal.service.UserService;
import com.ayushsingh.assessmentportal.service.service_impl.UserDetailsServiceImpl;


@RestController
@RequestMapping("/assessmentportal/authenticate")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    // private void authenticate(String username, String password) throws Exception
    // {
    // try {
    // authenticationManager.authenticate(new
    // UsernamePasswordAuthenticationToken(username, password));

    // } catch (DisabledException e) {
    // throw new Exception("USER DISABLED: " + e.getMessage());

    // } catch (BadCredentialsException e) {
    // throw new Exception("Invalid Credentials " + e.getMessage());
    // }
    // catch(AuthenticationException e){
    // throw new
    // }
    // }

    // generate token
    @PostMapping("/login")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            // authenticate(jwtRequest.getUsername(), jwtRequest.getPassword());
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        // } catch (UsernameNotFoundException e) {
            // e.printStackTrace();
        //     throw new Exception("user not found");
        // } catch (DisabledException e) {
        //     throw new Exception("USER DISABLED: " + e.getMessage());

        // } catch (BadCredentialsException e) {
        //     throw new Exception("Invalid Credentials " + e.getMessage());
        // }
        }
        catch(BadCredentialsException ex){
            throw new JWTAuthenticationException(AppConstants.FAILURE_MESSAGE,ex.getMessage());
        }

        UserDetails userDetails = this.userDetailsServiceImpl.loadUserByUsername(jwtRequest.getUsername());
        String token = this.jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    // Register new user api
    @PostMapping("/register/normal")
    public ResponseEntity<SuccessResponse<UserDto>> registerNormalUser(@RequestBody UserDto userDto) {
        UserDto registeredUser = this.userService.registerNewUser(userDto);
        SuccessResponse<UserDto> response = new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, registeredUser);
        return new ResponseEntity<SuccessResponse<UserDto>>(response, HttpStatus.OK);
    }

    @PostMapping("/register/admin")
    public ResponseEntity<SuccessResponse<UserDto>> registerAdminUser(@RequestBody UserDto userDto,
            @RequestParam(name = "key") String key) {
        UserDto registeredUser = this.userService.registerAdminUser(userDto, key);
        SuccessResponse<UserDto> response = new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, registeredUser);
        return new ResponseEntity<SuccessResponse<UserDto>>(response, HttpStatus.OK);
    }

    // Get the details of the logged in user (current user)
    @GetMapping("/current-user")
    public ResponseEntity<SuccessResponse<User>> getCurrentUser(Principal principal) {
        User user = (User) this.userDetailsServiceImpl.loadUserByUsername(principal.getName());
        SuccessResponse<User> response = new SuccessResponse<>(AppConstants.SUCCESS_CODE, AppConstants.SUCCESS_MESSAGE,
                user);
        return new ResponseEntity<SuccessResponse<User>>(response, HttpStatus.OK);
    }

    // //new
    // @RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
    // public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws
    // Exception {
    // // From the HttpRequest get the claims
    // DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims)
    // request.getAttribute("claims");

    // Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
    // String token = jwtUtil.doGenerateRefreshToken(expectedMap,
    // expectedMap.get("sub").toString());
    // return ResponseEntity.ok(new JwtResponse(token));
    // }
    // //new
    // public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims
    // claims) {
    // Map<String, Object> expectedMap = new HashMap<String, Object>();
    // for (Entry<String, Object> entry : claims.entrySet()) {
    // expectedMap.put(entry.getKey(), entry.getValue());
    // }
    // return expectedMap;
    // }
}
