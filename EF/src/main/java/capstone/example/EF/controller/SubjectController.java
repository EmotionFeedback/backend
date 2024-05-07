package capstone.example.EF.controller;

import capstone.example.EF.CallApi;
import capstone.example.EF.domain.live.LiveRoom;
import capstone.example.EF.domain.member.Member;
import capstone.example.EF.dto.subject.request.ProfileSubjectRequestDto;
import capstone.example.EF.dto.subject.request.ScenarioSubjectRequestDto;
import capstone.example.EF.dto.subject.response.ProfileSubjectResponseDto;
import capstone.example.EF.dto.subject.response.RandomSubjectResponseDto;
import capstone.example.EF.dto.subject.response.ScenarioSubjectResponseDto;
import capstone.example.EF.service.LiveService;
import capstone.example.EF.service.MemberService;
import capstone.example.EF.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;
    private final MemberService memberService;
    private final LiveService liveService;

    @GetMapping("/subject/{id}/random")
    public RandomSubjectResponseDto suggestRandomSubject(@PathVariable("id") Long selfId){
        Member byId = memberService.findById(selfId);
        String subject = subjectService.suggestSubject(byId);

        return new RandomSubjectResponseDto(byId,subject);
    }

    @GetMapping("/subject/{id}/profile")
    public ProfileSubjectResponseDto suggestProfileSubject(@PathVariable("id") Long opponentId){
        Member byId = memberService.findById(opponentId);
        String url = "http://localhost:8080/test";

        List<String> response = CallApi.sendPostRequestToUrlWithHobby(url, new ProfileSubjectRequestDto(byId.getJob(), byId.getHobbies()));

        return new ProfileSubjectResponseDto(byId, response);

    }

    @GetMapping("/subject/{id}/scenario")
    public ScenarioSubjectResponseDto suggestScenarioSubject(@PathVariable("id") Long liveRoomId){
        LiveRoom byLiveRoomId = liveService.findByLiveRoomId(liveRoomId);
        String url = "http://localhost:8080/test";

        List<String> response = CallApi.sendPostRequestToUrlWithContent(url, new ScenarioSubjectRequestDto(byLiveRoomId.getContents()));

        return new ScenarioSubjectResponseDto(byLiveRoomId, response);

    }

    @PostMapping("/test")
    public List<String> test(){
        List<String> a = new ArrayList<>();
        String a1 = "아이고 사장님";
        String a2 = "뭘도 이리 많이 주셨어";
        a.add(a1);
        a.add(a2);

        return a;
    }


}
