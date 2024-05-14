package capstone.example.EF.controller;

import capstone.example.EF.CallApi;
import capstone.example.EF.domain.live.LiveRoom;
import capstone.example.EF.domain.member.Hobby;
import capstone.example.EF.domain.member.Member;
import capstone.example.EF.dto.live.request.ContentRequestDto;
import capstone.example.EF.dto.subject.request.ProfileSubjectRequestDto;
import capstone.example.EF.dto.subject.request.ProfileSubjectRequestDtoTest;
import capstone.example.EF.dto.subject.request.ScenarioSubjectRequestDto;
import capstone.example.EF.dto.subject.response.ProfileSubjectResponseDto;
import capstone.example.EF.dto.subject.response.ScenarioSubjectResponseDto;
import capstone.example.EF.service.LiveService;
import capstone.example.EF.service.MemberService;
import capstone.example.EF.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;
    private final MemberService memberService;
    private final LiveService liveService;

    @GetMapping("/subject/{id}/profile")
    public ProfileSubjectResponseDto suggestProfileSubject(@PathVariable("id") Long opponentId){
        Member byId = memberService.findById(opponentId);
        String url = "http://localhost:8080/test";

        List<String> response = CallApi.sendPostRequestToUrlWithHobby(url, new ProfileSubjectRequestDto(byId.getJob(), byId.getHobbies()));

        return new ProfileSubjectResponseDto(byId, response);

    }

    @GetMapping("/subject/{room-id}/{user-id}/scenario")
    public ScenarioSubjectResponseDto suggestScenarioSubject(@PathVariable("room-id") Long liveRoomId, @PathVariable("user-id") Long selfId){
        LiveRoom byLiveRoomId = liveService.findByLiveRoomId(liveRoomId);
        Member byId = memberService.findById(selfId);
        List<String> content = new ArrayList<>();
        List<Integer> image = new ArrayList<>();
        List<Integer> voice = new ArrayList<>();
        int mean = 0;

        String url = "http://localhost:8080/test2";

        for(int i = 0;i<byLiveRoomId.getContents().size() && i<byLiveRoomId.getLiveEmotions().size();i++) {
            if (Objects.equals(byLiveRoomId.getContents().get(i).getMemberId(), selfId)) {
                content.add(byLiveRoomId.getContents().get(i).getContent());
            }
            if (Objects.equals(byLiveRoomId.getLiveEmotions().get(i).getMemberId(), selfId)) {
                image.add(byLiveRoomId.getLiveEmotions().get(i).getImage());
                voice.add(byLiveRoomId.getLiveEmotions().get(i).getVoice());
            }
        }

        for(int i = byId.getCallingPoint()+1;i<image.size();i++){
            int image1 = byLiveRoomId.getLiveEmotions().get(i).getImage();
            int voice1 = byLiveRoomId.getLiveEmotions().get(i).getVoice();
            mean = (image1 + voice1) / 2;

            if(mean > 66){
                memberService.updateCallingPoint(byId,i);

                List<String> response = CallApi.sendPostRequestToUrlWithContent(url, new ScenarioSubjectRequestDto(content,image,voice,i));

                return new ScenarioSubjectResponseDto(byLiveRoomId, response);
            }
        }
        List<String> subject = new ArrayList<>();
        subject.add(subjectService.suggestSubject(byId));

        return new ScenarioSubjectResponseDto(byLiveRoomId, subject);

    }

//    @PostMapping("/test")
//    public List<String> test(@RequestBody ProfileSubjectRequestDtoTest dto){
//        String job = dto.getJob();
//        List<String> hobbies = dto.getHobby();
//        hobbies.add(job);
//
//        return hobbies;
//    }

    @PostMapping("/test2")
    public List<String> test(@RequestBody ProfileSubjectRequestDtoTest dto){
        List<String> hobbies = dto.getContent();
        hobbies.add(dto.getImageL().toString());
        hobbies.add(dto.getVoiceL().toString());

        return hobbies;
    }

    @PostMapping("/hobby/{id}")
    public void saveHobby(@PathVariable("id") Long id, @RequestBody ContentRequestDto dto){
        Member byId = memberService.findById(id);
        Hobby hobby = Hobby.createHobby(dto.getContent(), byId);
        memberService.hobbyJoin(hobby);
    }


}
