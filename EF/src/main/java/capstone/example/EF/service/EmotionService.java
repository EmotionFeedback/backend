package capstone.example.EF.service;

import capstone.example.EF.domain.Emotion;
import capstone.example.EF.domain.member.Member;
import capstone.example.EF.exception.CustomException;
import capstone.example.EF.repository.EmotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EmotionService {

    private final EmotionRepository emotionRepository;

    @Transactional
    public Long join(Emotion emotion){
        Emotion saved = emotionRepository.save(emotion);
        return saved.getId();
    }

    @Transactional
    public Boolean deleteEmotionById(Long id){
        if(emotionRepository.existsById(id)){
            emotionRepository.deleteById(id);
            return true;
        }
        else{
            throw new CustomException("해당 id가 존재하지 않습니다.");
        }
    }

    public Emotion findByMaleAndFemale(Member male, Member female){
        if(emotionRepository.existsByMale(male) && emotionRepository.existsByFemale(female)){
            return emotionRepository.findByMaleAndFemale(male,female);
        }
        else{
            throw new CustomException("유저가 존재하지 않습니다.");
        }
    }

}
