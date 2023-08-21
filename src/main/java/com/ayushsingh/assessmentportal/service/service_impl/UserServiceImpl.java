package com.ayushsingh.assessmentportal.service.service_impl;

import com.ayushsingh.assessmentportal.constants.AppConstants;
import com.ayushsingh.assessmentportal.dto.CategoryDto;
import com.ayushsingh.assessmentportal.dto.QuizDto;
import com.ayushsingh.assessmentportal.dto.UserDto;
import com.ayushsingh.assessmentportal.exceptions.DuplicateResourceException;
import com.ayushsingh.assessmentportal.exceptions.NoAdminPermissionException;
import com.ayushsingh.assessmentportal.exceptions.ResourceNotFoundException;
import com.ayushsingh.assessmentportal.model.Category;
import com.ayushsingh.assessmentportal.model.Quiz;
import com.ayushsingh.assessmentportal.model.Role;
import com.ayushsingh.assessmentportal.model.User;
import com.ayushsingh.assessmentportal.repository.CategoryRepository;
import com.ayushsingh.assessmentportal.repository.RoleRepository;
import com.ayushsingh.assessmentportal.repository.UserRepository;
import com.ayushsingh.assessmentportal.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private final String CLASS_NAME = UserServiceImpl.class.getName();
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public void deleteUserbyId(String id) {
        Long userid = Long.parseLong(id);
        Optional<User> local = this.userRepository.findById(userid);
        System.out.println("local: " + local);
        if (!local.isPresent()) {
            System.out.println("User not found");
            throw new ResourceNotFoundException("User", "userid", id);
        } else {
            this.userRepository.deleteById(userid);
        }

    }


    @Override
    public UserDto findByEmail(String email) {
        System.out.println("UserServiceImpl: Loading user for email: " + email);
        List<User> userList = userRepository.findByEmail(email);
        System.out.println("findByEmail: userList: " + userList);
        if (userList == null || userList.size() == 0) {
            throw new ResourceNotFoundException("User", "email", email);
        }
        return this.usertoDto(userList.get(0));
    }

    private User dtoToUser(UserDto userDto) {
        return this.modelMapper.map(userDto, User.class);
    }

    public UserDto usertoDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String id) {
        User newUser = this.dtoToUser(userDto);
        Long userid = Long.parseLong(id);
        User oldUser = null;
        Optional<User> local = this.userRepository.findById(userid);
        System.out.println("local: " + local);
        if (!local.isPresent()) {
            System.out.println("User not found");
            throw new ResourceNotFoundException("User", "userid", id);
        } else {
            oldUser = local.get();
            oldUser.setEmail(newUser.getEmail());
            oldUser.setFirstName(newUser.getFirstName());
            oldUser.setLastName(newUser.getLastName());
            oldUser.setPhone(newUser.getPhone());
            oldUser.setEnabled(newUser.getEnabled());
            oldUser.setProfile(newUser.getProfile());
            User temp = userRepository.findByUser_name(newUser.getUser_name());
            if (temp != null) {
                throw new DuplicateResourceException("user", "username", newUser.getUser_name());
            }
            oldUser.setUser_name(newUser.getUser_name());
            newUser = this.userRepository.save(oldUser);
        }
        return this.usertoDto(oldUser);
    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {

        User user = this.modelMapper.map(userDto, User.class);
        List<User> local = this.userRepository.findByEmail(user.getEmail());
        User newUser = null;
        if (local.size() == 0) {
            // encode the password
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));

            // roles
            // find the NORMAL ROLE with ID
            Role role = this.roleRepository.findById(AppConstants.NORMAL_ROLE_ID).get();
            user.getRoles().add(role);
            newUser = this.userRepository.save(user);
        } else {
            // i.e., the user with this username is already present
            System.out.println(CLASS_NAME + " is user present: " + local);

            System.out.println("User already exists");
            // throw an exception which will be handled by any
            // method which invokes this method
            throw new DuplicateResourceException("User", "username", user.getEmail());
        }

        return this.usertoDto(newUser);
    }

    //To get the complete details of the user including authentication
    @Override
    public User getUserByUsername(String username) {
        User user = userRepository.findByUser_name(username);
        if (user == null) {
            throw new ResourceNotFoundException("User", "username", username);
        }
        return user;
    }

    @Override
    public UserDto registerAdminUser(UserDto userDto, String key) {
        System.out.println("UserDto username: " + userDto.getUser_name());
        if (key.equals(AppConstants.ADMIN_KEY)) {
            throw new NoAdminPermissionException("Admin Key required");

        }
        User user = this.modelMapper.map(userDto, User.class);
        System.out.println("Saving user with username: " + user.getUser_name());
        List<User> local = this.userRepository.findByEmail(user.getEmail());
        User newUser = null;
        if (local.size() == 0) {
            // encode the password
            user.setPassword(this.passwordEncoder.encode(user.getPassword()));

            // roles
            // find the NORMAL ROLE with ID
            Role role = this.roleRepository.findById(AppConstants.ADMIN_ROLE_ID).get();
            user.getRoles().add(role);
            newUser = this.userRepository.save(user);
        } else {
            // i.e., the user with this username is already present
            System.out.println(CLASS_NAME + " is user present: " + local);

            System.out.println("User already exists");
            // throw an exception which will be handled by any
            // method which invokes this method
            throw new DuplicateResourceException("User", "username", user.getUser_name());
        }

        return this.usertoDto(newUser);
    }

    @Override
    public List<QuizDto> getQuizzesByAdminAndCategory(Long adminid, Long categoryId) {
        User user = userRepository.findById(adminid).get();
        System.out.println("category id: " + categoryId + " userid: " + adminid);
        List<Quiz> createdQuizzes = user.getCreatedQuizzes();
        System.out.println("Number of quizzes: " + createdQuizzes.size());
        List<QuizDto> quizzes = new ArrayList<>();
        for (Quiz quiz : createdQuizzes) {
            System.out.println("Quiz: quiz id: " + quiz.getQuizId() + " categoryId: " + quiz.getCategory().getCategoryId());
            if (quiz.getCategory().getCategoryId().equals(categoryId)) {
                System.out.println("adding Quiz: quiz id: " + quiz.getQuizId() + " categoryId: " + quiz.getCategory().getCategoryId());

                quizzes.add(this.quizToDto(quiz));
            }
        }
        return quizzes;
    }


    //add the user to a particular category
    @Override
    public void addEnrolledCategory(Long userid, Long categoryid) {
        Optional<Category> category = this.categoryRepository.findById(categoryid);
        if (category.isPresent()) {
            //Load user
            User user = this.userRepository.findById(userid).get();
            user.getEnrolledCategories().add(category.get());
            //save the user
            user = this.userRepository.save(user);
        } else {
            throw new ResourceNotFoundException("Category", "category id", Long.toString(categoryid));
        }
    }


    //get all the categories in which the user is enrolled
    @Override
    public List<CategoryDto> getEnrolledCategories(Long userid) {
        User user = this.userRepository.findById(userid).get();
        Set<Category> enrolledCategories = user.getEnrolledCategories();
        List<CategoryDto> eCategoryDtos = new ArrayList<>();
        for (Category enCategory : enrolledCategories) {
            eCategoryDtos.add(this.categoryToDto(enCategory));
        }
        return eCategoryDtos;
    }


    private QuizDto quizToDto(Quiz quiz) {
        return this.modelMapper.map(quiz, QuizDto.class);
    }

    public CategoryDto categoryToDto(Category category) {
        CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
        return categoryDto;
    }

}
