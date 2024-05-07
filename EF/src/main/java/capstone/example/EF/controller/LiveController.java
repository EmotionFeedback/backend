package capstone.example.EF.controller;

import capstone.example.EF.domain.live.Content;
import capstone.example.EF.domain.live.LiveEmotion;
import capstone.example.EF.domain.live.LiveRoom;
import capstone.example.EF.domain.member.Member;
import capstone.example.EF.dto.live.ContentResponseDto;
import capstone.example.EF.dto.live.EmotionResponseDto;
import capstone.example.EF.dto.live.LiveRoomResponseDto;
import capstone.example.EF.dto.subject.response.RandomSubjectResponseDto;
import capstone.example.EF.service.LiveService;
import capstone.example.EF.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LiveController {

    private final LiveService liveService;
    private final MemberService memberService;

    @PostMapping("/live/{mid}/{wid}/new-room")
    public LiveRoomResponseDto makeNewLiveRoom(@PathVariable("mid") Long maleId, @PathVariable("wid") Long femaleId){
        Member male = memberService.findById(maleId);
        Member female = memberService.findById(femaleId);

        LiveRoom liveRoom = LiveRoom.createLiveRoom(male, female);
        liveService.liveRoomJoin(liveRoom);

        return new LiveRoomResponseDto(liveRoom,male,female);
    }

    @PostMapping("/live/{id}/save/content")
    public Boolean saveContent(@PathVariable("id") Long liveRoomId, @RequestBody String content){
        LiveRoom liveRoom = liveService.findByLiveRoomId(liveRoomId);
        Content content1 = Content.createContent(liveRoom, content);

        liveService.contentJoin(content1);

        return true;
    }

    @PostMapping("/live/{id}/save/emotion")
    public Boolean saveEmotion(@PathVariable("id") Long liveRoomId, @RequestBody Integer emotion){
        LiveRoom liveRoom = liveService.findByLiveRoomId(liveRoomId);
        LiveEmotion liveEmotion = LiveEmotion.createLiveEmotion(liveRoom, emotion);

        liveService.liveEmotionJoin(liveEmotion);

        return true;
    }

    @GetMapping("/live/{id}/all-content")
    public ContentResponseDto findAllContent(@PathVariable("id") Long liveRoomId){
        LiveRoom room = liveService.findByLiveRoomId(liveRoomId);
        List<Content> contents = room.getContents();
        List<String> re = new ArrayList<>();

        for (Content content : contents) {
            re.add(content.getContent());
        }

        return new ContentResponseDto(room,re);
    }

    @GetMapping("/live/{id}/all-emotion")
    public EmotionResponseDto findAllEmotion(@PathVariable("id") Long liveRoomId){
        LiveRoom room = liveService.findByLiveRoomId(liveRoomId);
        List<LiveEmotion> liveEmotions = room.getLiveEmotions();

        List<Long> re = new ArrayList<>();
        for (LiveEmotion liveEmotion : liveEmotions) {
            re.add((long) liveEmotion.getLiveEmotion());
        }

        Long mean = liveService.calculateMean(liveRoomId);

        return new EmotionResponseDto(room, mean, re);
    }

    @DeleteMapping("/live/{id}")
    public void deleteLiveRoom(@PathVariable("id") Long id){
        liveService.deleteLiveRoomById(id);
    }

}
