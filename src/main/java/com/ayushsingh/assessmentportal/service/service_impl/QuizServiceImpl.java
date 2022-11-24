package com.ayushsingh.assessmentportal.service.service_impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayushsingh.assessmentportal.dto.QuizDto;
import com.ayushsingh.assessmentportal.dto.UserDto;
import com.ayushsingh.assessmentportal.exceptions.ResourceNotFoundException;
import com.ayushsingh.assessmentportal.model.Category;
import com.ayushsingh.assessmentportal.model.Quiz;
import com.ayushsingh.assessmentportal.model.User;
import com.ayushsingh.assessmentportal.repository.CategoryRepository;
import com.ayushsingh.assessmentportal.repository.QuizRepository;
import com.ayushsingh.assessmentportal.repository.UserRepository;
import com.ayushsingh.assessmentportal.service.QuizService;
@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public QuizDto addQuiz(QuizDto quizDto,Long userid) {
        User user=userRepository.findById(userid).get();
        System.out.println("User obtained: "+user+" userid: "+userid);
        //set the admin user
        //doing this because the
        //line this.quizToDto(quiz) invokes the ovrridden method isEnabled() in User class,
        //it throws an error of enabled attribute being null.
        //Two problems-
        //1. atomatic saving of user is not working
        //2. why the isEnabled() method is invoked?
        quizDto.setAdminUser(this.usertoDto(user));
        System.out.println("quizDto created: "+quizDto);

        Quiz quiz=this.quizRepository.save(this.dtoToQuiz(quizDto));
        System.out.println("quiz created: "+quiz);
        return this.quizToDto(quiz);
    }

    @Override
    public void deleteQuiz(String quizId) {
       Long id=Long.parseLong(quizId);
       Optional<Quiz> temp=this.quizRepository.findById(id);
       System.out.println("Quiz found: "+temp.get().getQuizId());
       if(temp.isPresent()){
        Category category=this.categoryRepository.findById(temp.get().getCategory().getCategoryId()).get();
        category.deleteQuiz(temp.get());
        this.quizRepository.delete(temp.get());
       }
       else{
        throw new ResourceNotFoundException("Quiz", "quiz id", quizId);
       }
        
    }

    @Override
    public QuizDto getQuizById(String quizId) {
        Long id=Long.parseLong(quizId);
        Optional<Quiz> temp=this.quizRepository.findById(id);
       if(temp.isPresent()){
        
        return this.quizToDto(temp.get());
       }
       else{
        throw new ResourceNotFoundException("Quiz", "quiz id", quizId);
       }
    }

    @Override
    public List<QuizDto> getQuizzes() {
       List<Quiz> quizzes=this.quizRepository.findAll();
       List<QuizDto> quizDtos=new ArrayList<>();
       for(Quiz quiz: quizzes){
        quizDtos.add(this.quizToDto(quiz));
       }
       return quizDtos;
    }

    @Override
    public QuizDto updateQuiz(QuizDto quizDto, String quizId) {
      Quiz quiz=this.dtoToQuiz(quizDto);
      Quiz updatedQuiz=this.quizRepository.save(quiz);//either save new or update existing
      return this.quizToDto(updatedQuiz);
    }
    

    private QuizDto quizToDto(Quiz quiz){
        return this.modelMapper.map(quiz, QuizDto.class);
        
        // QuizDto quizDto=new QuizDto();
        // quizDto.setActive(quiz.isActive());
        // quizDto.setAdminUser(this.usertoDto(quiz.getAdminUser()));
        // // quizDto.setCategory(this.categoryToDto(quiz.getCategory()));
        // quizDto.setDescription(quiz.getDescription());
        // quizDto.setMaxMarks(quiz.getMaxMarks());
        // quizDto.setNumberOfQuestions(quiz.getNumberOfQuestions());
        // // quizDto.setQuestions(quiz.getQuestions());
        // quizDto.setQuizId(quiz.getQuizId());
        // quizDto.setTitle(quiz.getTitle());
        // return quizDto;

    }
    private Quiz dtoToQuiz(QuizDto quizDto){
        return this.modelMapper.map(quizDto,Quiz.class);
    }
    public UserDto usertoDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }
}
