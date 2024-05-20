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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Tag(name = "라이브 채팅")
@RestController
@RequiredArgsConstructor
public class LiveController {

    private final LiveService liveService;
    private final MemberService memberService;
    @Operation(summary = "새 화상채팅 방 생성")
    @PostMapping("/live/{mid}/{wid}/new-room")
    public LiveRoomResponseDto makeNewLiveRoom(@Parameter(description = "남자 회원 아이디") @PathVariable("mid") Long maleId, @Parameter(description = "여자 회원 아이디") @PathVariable("wid") Long femaleId){
        Member male = memberService.findById(maleId);
        Member female = memberService.findById(femaleId);

        LiveRoom liveRoom = LiveRoom.createLiveRoom(male, female);
        liveService.liveRoomJoin(liveRoom);

        return new LiveRoomResponseDto(liveRoom,male,female);
    }
    @Operation(summary = "실시간 상대방의 대화 내용 저장")
    @PostMapping("/live/{room-id}/{user-id}/save/content")
    public Boolean saveContent(@Parameter(description = "화상채팅 방 아이디") @PathVariable("room-id") Long liveRoomId,
                               @Parameter(description = "회원 아이디") @PathVariable("user-id") Long memberId,
                               @Parameter(description = "대화 내용") @RequestBody ContentRequestDto contentRequestDto){
        LiveRoom liveRoom = liveService.findByLiveRoomId(liveRoomId);
        Content content1 = ContentRequestDto.toEntity(liveRoom,contentRequestDto,memberId);

        liveService.contentJoin(content1);

        return true;
    }
    @Operation(summary = "실시간 상대방의 감정 저장")
    @PostMapping("/live/{room-id}/{user-id}/save/emotion")
    public Boolean saveEmotion(@Parameter(description = "화상채팅 방 아이디") @PathVariable("room-id") Long liveRoomId,
                               @Parameter(description = "회원 아이디") @PathVariable("user-id") Long memberId,
                               @Parameter(description = "이미지와 목소리 감정 수치 Integer") @RequestBody EmotionRequestDto emotionRequestDto){
        LiveRoom liveRoom = liveService.findByLiveRoomId(liveRoomId);
        LiveEmotion liveEmotion = EmotionRequestDto.toEntity(liveRoom,emotionRequestDto, memberId);

        liveService.liveEmotionJoin(liveEmotion);

        return true;
    }
    @Operation(summary = "현재까지 모든 대화내용 조회, 해당 회원의 대화 내용이 아닌 해당 회원의 상대방의 대화 내용이 반환됨")
    @GetMapping("/live/{room-id}/{user-id}/all-content")
    public ContentResponseDto findAllContent(@Parameter(description = "화상채팅 방 아이디") @PathVariable("room-id") Long liveRoomId,
                                             @Parameter(description = "회원 아이디") @PathVariable("user-id") Long memberId){
        LiveRoom room = liveService.findByLiveRoomId(liveRoomId);
        List<Content> contents = room.getContents();
        List<String> re = new ArrayList<>();
        List<Integer> millisec = new ArrayList<>();

        for (Content content : contents) {
            if(Objects.equals(content.getMemberId(), memberId)) {
                re.add(content.getContent());
                millisec.add(content.getMillisec());
            }
        }

        return new ContentResponseDto(room,re,millisec);
    }
    @Operation(summary = "현재까지 모든 감정 조회, 해당 회원의 감정이 아닌 해당 회원의 상대방의 감정이 반환됨")
    @GetMapping("/live/{room-id}/{user-id}/all-emotion")
    public EmotionResponseDto findAllEmotion(@Parameter(description = "화상채팅 방 아이디") @PathVariable("room-id") Long liveRoomId,
                                             @Parameter(description = "회원 아이디") @PathVariable("user-id") Long memberId){
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
    @Operation(summary = "대화가 다 끝나고 마지막 페이지에서 감정까지 다 확인하고 난 후 회원이 메인페이지로 돌아가면 그 때 화상채팅 방 삭제 해야함.")
    @DeleteMapping("/live/{id}")
    public void deleteLiveRoom(@Parameter(description = "화상채팅 방 아이디") @PathVariable("id") Long id){
        LiveRoom byLiveRoomId = liveService.findByLiveRoomId(id);
        memberService.updateCallingPoint(byLiveRoomId.getMaleUser(),1);
        memberService.updateCallingPoint(byLiveRoomId.getFemaleUser(),1);
        liveService.deleteLiveRoomById(id);
    }

}
