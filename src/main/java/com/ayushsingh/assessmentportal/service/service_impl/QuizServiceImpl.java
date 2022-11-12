package com.ayushsingh.assessmentportal.service.service_impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayushsingh.assessmentportal.dto.QuizDto;
import com.ayushsingh.assessmentportal.exceptions.ResourceNotFoundException;
import com.ayushsingh.assessmentportal.model.Category;
import com.ayushsingh.assessmentportal.model.Quiz;
import com.ayushsingh.assessmentportal.repository.CategoryRepository;
import com.ayushsingh.assessmentportal.repository.QuizRepository;
import com.ayushsingh.assessmentportal.service.QuizService;
@Service
public class QuizServiceImpl implements QuizService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    @Override
    public QuizDto addQuiz(QuizDto quizDto) {
        Quiz quiz=this.quizRepository.save(this.dtoToQuiz(quizDto));
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
    }
    private Quiz dtoToQuiz(QuizDto quizDto){
        return this.modelMapper.map(quizDto,Quiz.class);
    }
}
