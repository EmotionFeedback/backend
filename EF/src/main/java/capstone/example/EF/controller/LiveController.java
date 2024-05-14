package capstone.example.EF.controller;

import capstone.example.EF.domain.live.Content;
import capstone.example.EF.domain.live.LiveEmotion;
import capstone.example.EF.domain.live.LiveRoom;
import capstone.example.EF.domain.member.Member;
import capstone.example.EF.dto.live.response.ContentResponseDto;
import capstone.example.EF.dto.live.response.EmotionResponseDto;
import capstone.example.EF.dto.live.response.LiveRoomResponseDto;
import capstone.example.EF.dto.live.request.ContentRequestDto;
import capstone.example.EF.dto.live.request.EmotionRequestDto;
import capstone.example.EF.service.LiveService;
import capstone.example.EF.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @PostMapping("/live/{room-id}/{user-id}/save/content")
    public Boolean saveContent(@PathVariable("room-id") Long liveRoomId,@PathVariable("user-id") Long memberId,@RequestBody ContentRequestDto contentRequestDto){
        LiveRoom liveRoom = liveService.findByLiveRoomId(liveRoomId);
        Content content1 = ContentRequestDto.toEntity(liveRoom,contentRequestDto,memberId);

        liveService.contentJoin(content1);

        return true;
    }

    @PostMapping("/live/{room-id}/{user-id}/save/emotion")
    public Boolean saveEmotion(@PathVariable("room-id") Long liveRoomId, @PathVariable("user-id") Long memberId,@RequestBody EmotionRequestDto emotionRequestDto){
        LiveRoom liveRoom = liveService.findByLiveRoomId(liveRoomId);
        LiveEmotion liveEmotion = EmotionRequestDto.toEntity(liveRoom,emotionRequestDto, memberId);

        liveService.liveEmotionJoin(liveEmotion);

        return true;
    }

    @GetMapping("/live/{room-id}/{user-id}/all-content")
    public ContentResponseDto findAllContent(@PathVariable("room-id") Long liveRoomId,@PathVariable("user-id") Long memberId){
        LiveRoom room = liveService.findByLiveRoomId(liveRoomId);
        List<Content> contents = room.getContents();
        List<String> re = new ArrayList<>();

        for (Content content : contents) {
            if(Objects.equals(content.getMemberId(), memberId)) {
                re.add(content.getContent());
            }
        }

        return new ContentResponseDto(room,re);
    }

    @GetMapping("/live/{room-id}/{user-id}/all-emotion")
    public EmotionResponseDto findAllEmotion(@PathVariable("room-id") Long liveRoomId, @PathVariable("user-id") Long memberId){
        LiveRoom room = liveService.findByLiveRoomId(liveRoomId);
        List<LiveEmotion> liveEmotions = room.getLiveEmotions();

        List<Integer> image = new ArrayList<>();
        List<Integer> voice = new ArrayList<>();

        for (LiveEmotion liveEmotion : liveEmotions) {
            if(Objects.equals(liveEmotion.getMemberId(), memberId)) {
                image.add(liveEmotion.getImage());
                voice.add(liveEmotion.getVoice());
            }
        }

        return new EmotionResponseDto(image,voice);
    }

    @DeleteMapping("/live/{id}")
    public void deleteLiveRoom(@PathVariable("id") Long id){
        LiveRoom byLiveRoomId = liveService.findByLiveRoomId(id);
        memberService.updateCallingPoint(byLiveRoomId.getMaleUser(),1);
        memberService.updateCallingPoint(byLiveRoomId.getFemaleUser(),1);
        liveService.deleteLiveRoomById(id);
    }

}
