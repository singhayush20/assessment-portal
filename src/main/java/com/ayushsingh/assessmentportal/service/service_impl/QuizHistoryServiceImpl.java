package com.ayushsingh.assessmentportal.service.service_impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayushsingh.assessmentportal.dto.QuizHistoryDto;
import com.ayushsingh.assessmentportal.exceptions.ResourceNotFoundException;
import com.ayushsingh.assessmentportal.model.Quiz;
import com.ayushsingh.assessmentportal.model.QuizHistory;
import com.ayushsingh.assessmentportal.model.QuizHistoryKey;
import com.ayushsingh.assessmentportal.model.User;
import com.ayushsingh.assessmentportal.repository.QuizHistoryRepository;
import com.ayushsingh.assessmentportal.repository.QuizRepository;
import com.ayushsingh.assessmentportal.repository.UserRepository;
import com.ayushsingh.assessmentportal.service.QuizHistoryService;

@Service
public class QuizHistoryServiceImpl implements QuizHistoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private QuizHistoryRepository quizHistoryRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuizRepository quizRepository;

    @Override
    public List<QuizHistoryDto> getQuizzesByUserId(Long userId) {
        List<QuizHistory> histories = this.quizHistoryRepository.findAllById_UserId(userId);
        System.out.println("find by user id: Number of history entries found: " + histories.size());
        List<QuizHistoryDto> quizHistoryDtos = new ArrayList<>();
        for (QuizHistory quizHistory : histories) {
            quizHistory.setUser(null);//do not send user data
            quizHistoryDtos.add(this.quizHistoryToDto(quizHistory));
        }
        return quizHistoryDtos;
    }

    @Override
    public List<QuizHistoryDto> getAllUsersForQuiz(Long quizId) {
        List<QuizHistory> histories = this.quizHistoryRepository.findAllById_QuizId(quizId);
        System.out.println("find by quiz id: Number of history entries found: " + histories.size());
        List<QuizHistoryDto> quizHistoryDtos = new ArrayList<>();
        for (QuizHistory quizHistory : histories) {
            quizHistory.setQuiz(null);//do not send quiz data
            quizHistoryDtos.add(this.quizHistoryToDto(quizHistory));
        }
        return quizHistoryDtos;
    }

    @Override
    public QuizHistoryDto saveNewRecord(QuizHistoryDto newRecord, Long userid, Long quizId) {
        Optional<User> user = this.userRepository.findById(userid);
        QuizHistory quizHistory;
        if (user.isPresent()) {
            Optional<Quiz> quiz = this.quizRepository.findById(quizId);
            if (quiz.isPresent()) {
                quizHistory = this.dtoToQuizHistory(newRecord);
                quizHistory.setId(new QuizHistoryKey(userid, quizId));
                quizHistory.setQuiz(quiz.get());
                quizHistory.setUser(user.get());
                System.out.println("Saving record: " + newRecord.toString());
                quizHistory = this.quizHistoryRepository.save(quizHistory);
            } else {
                throw new ResourceNotFoundException("Quiz", "quiz id", Long.toString(quizId));
            }
        } else {
            throw new ResourceNotFoundException("User", "user id", Long.toString(userid));
        }

        return this.quizHistoryToDto(quizHistory);
    }

    



    @Override
    public boolean checkIfQuizIsAttempted(Long quizId, Long userId) {
        QuizHistoryKey quizHistoryKey=new QuizHistoryKey();
        quizHistoryKey.setQuizId(quizId);
        quizHistoryKey.setUserId(userId);
        Optional<QuizHistory> optional=this.quizHistoryRepository.findById(quizHistoryKey);
        if(optional.isEmpty()){
            return false;
        }
        return true;
    }

    private QuizHistory dtoToQuizHistory(QuizHistoryDto quizHistoryDto) {
        return this.modelMapper.map(quizHistoryDto, QuizHistory.class);
    }

    private QuizHistoryDto quizHistoryToDto(QuizHistory quizHistory) {
        return this.modelMapper.map(quizHistory, QuizHistoryDto.class);
    }
}
