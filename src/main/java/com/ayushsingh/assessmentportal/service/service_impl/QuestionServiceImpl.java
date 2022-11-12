package com.ayushsingh.assessmentportal.service.service_impl;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayushsingh.assessmentportal.dto.QuestionDto;
import com.ayushsingh.assessmentportal.dto.QuizDto;
import com.ayushsingh.assessmentportal.exceptions.ResourceNotFoundException;
import com.ayushsingh.assessmentportal.model.Question;
import com.ayushsingh.assessmentportal.model.Quiz;
import com.ayushsingh.assessmentportal.repository.QuestionRepository;
import com.ayushsingh.assessmentportal.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public QuestionDto addQuestion(QuestionDto questionDto) {
        Question question = this.dtoToQuestion(questionDto);
        return this.questionToDto(this.questionRepository.save(question));
    }

    @Override
    public QuestionDto updaQuestion(QuestionDto questionDto) {
        return this.questionToDto(this.questionRepository.save(this.dtoToQuestion(questionDto)));
    }

    @Override
    public List<QuestionDto> getQuestions() {
        List<Question> questions = this.questionRepository.findAll();
        List<QuestionDto> questionDtos = new ArrayList<>();
        for (Question question : questions) {
            questionDtos.add(this.questionToDto(question));
        }
        return questionDtos;
    }

    @Override
    public QuestionDto getQuestion(String questionId) {
        Long qId = Long.parseLong(questionId);
        Optional<Question> question = this.questionRepository.findById(qId);
        if (question.isPresent()) {
            return this.questionToDto(question.get());
        } else {
            throw new ResourceNotFoundException("question", "question id", questionId);
        }
    }

    @Override
    public Set<QuestionDto> getQuestionsOfQuiz(QuizDto quizDto) {
        Set<Question> questions = this.questionRepository.findByQuiz(this.dtoToQuiz(quizDto));
        Set<QuestionDto> questionDtos = new LinkedHashSet<>();
        for (Question question : questions) {
            questionDtos.add(this.questionToDto(question));
        }
        return questionDtos;
    }
    @Override
    public void deleteQuestion(String questionId) {
        Long qId = Long.parseLong(questionId);
        Optional<Question> question = this.questionRepository.findById(qId);
        if (question.isPresent()) {
            this.questionRepository.deleteById(qId);
        } else {
            throw new ResourceNotFoundException("question", "question id", questionId);
        }
        
    }

    QuestionDto questionToDto(Question question) {
        return this.modelMapper.map(question, QuestionDto.class);
    }

    Question dtoToQuestion(QuestionDto questionDto) {
        return this.modelMapper.map(questionDto, Question.class);
    }

    private Quiz dtoToQuiz(QuizDto quizDto) {
        return this.modelMapper.map(quizDto, Quiz.class);
    }

  

}
