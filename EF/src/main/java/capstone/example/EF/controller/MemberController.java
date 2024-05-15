package capstone.example.EF.controller;

import capstone.example.EF.auth.domain.JwtToken;
import capstone.example.EF.domain.member.Hobby;
import capstone.example.EF.domain.member.Member;
import capstone.example.EF.dto.member.*;
import capstone.example.EF.service.MemberService;
import capstone.example.EF.service.SubjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final SubjectService subjectService;

    @PostMapping("/sign-in")
    public JwtToken signIn(@RequestBody SignInDto signInDto) {
        String username = signInDto.getUsername();
        String password = signInDto.getPassword();
        JwtToken jwtToken = memberService.signIn(username, password);
        log.info("request username = {}, password = {}", username, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<MemberDto> signUp(@RequestBody SignUpDto signUpDto) {
        Member savedMember = memberService.signUp(signUpDto);
        subjectService.createChecker(savedMember);

        return ResponseEntity.ok(MemberDto.toDto(savedMember));
    }

    @PostMapping("/{id}/save/hobby")
    public Boolean saveHobby(@PathVariable("id") Long id, @RequestBody SaveHobbyDto saveHobbyDto){
        Member byId = memberService.findById(id);
        Hobby entity = saveHobbyDto.toEntity(byId);
        memberService.hobbyJoin(entity);

        return true;
    }

    @GetMapping("/{id}")
    public MemberDto getMember(@PathVariable("id") Long id){
        return MemberDto.toDto(memberService.findById(id));
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable("id") Long id){
        memberService.deleteMember(id);
    }

    @PutMapping("/{id}")
    public MemberDto updateMember(@PathVariable("id") Long id, @RequestBody UpdateMemberDto memberDto){

        return MemberDto.toDto(memberService.updateMemberInfo(id,memberDto));
    }


}