package capstone.example.EF.service;

import capstone.example.EF.domain.live.Content;
import capstone.example.EF.domain.live.LiveEmotion;
import capstone.example.EF.domain.live.LiveRoom;
import capstone.example.EF.exception.CustomException;
import capstone.example.EF.repository.live.ContentRepository;
import capstone.example.EF.repository.live.LiveEmotionRepository;
import capstone.example.EF.repository.live.LiveRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LiveService {

    private final LiveEmotionRepository liveEmotionRepository;
    private final LiveRoomRepository liveRoomRepository;
    private final ContentRepository contentRepository;

    @Transactional
    public Long contentJoin(Content content){
        Content saved = contentRepository.save(content);
        return saved.getId();
    }

    @Transactional
    public Long liveEmotionJoin(LiveEmotion liveEmotion){
        LiveEmotion saved = liveEmotionRepository.save(liveEmotion);
        return saved.getId();
    }

    @Transactional
    public Long liveRoomJoin(LiveRoom liveRoom){
        LiveRoom saved = liveRoomRepository.save(liveRoom);
        return saved.getId();
    }

    @Transactional
    public Boolean deleteLiveRoomById(Long id){
        if(liveRoomRepository.existsById(id)){
            liveRoomRepository.deleteById(id);
            return true;
        }
        else{
            throw new CustomException("해당 liveRoomId가 존재하지 않습니다.");
        }
    }

    public LiveRoom findByLiveRoomId(Long liveRoomId){
        if(liveRoomRepository.existsById(liveRoomId)) {
            Optional<LiveRoom> byId = liveRoomRepository.findById(liveRoomId);
            LiveRoom room = byId.get();
            return room;
        }
        else{
            throw new CustomException("해당 liveRoomId가 존재하지 않습니다.");
        }
    }

    public Long calculateMean(Long liveRoomId){
        if(liveRoomRepository.existsById(liveRoomId)){
            Optional<LiveRoom> byId = liveRoomRepository.findById(liveRoomId);
            LiveRoom room = byId.get();
            List<LiveEmotion> liveEmotions = room.getLiveEmotions();
            Long mean = 0L;
            Long sum = 0L;
            for (LiveEmotion liveEmotion : liveEmotions) {
                sum += liveEmotion.getLiveEmotion();
            }
            mean = sum / liveEmotions.size();
            return mean;
        }
        else{
            throw new CustomException("해당 liveRoomId가 존재하지 않습니다.");
        }
    }
}
