package capstone.example.EF.controller;

import capstone.example.EF.auth.domain.JwtToken;
import capstone.example.EF.domain.member.Hobby;
import capstone.example.EF.domain.member.Member;
import capstone.example.EF.dto.member.*;
import capstone.example.EF.service.MemberService;
import capstone.example.EF.service.SubjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "회원관리")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final SubjectService subjectService;

    @Operation(summary = "로그인")
    @PostMapping("/sign-in")
    public JwtToken signIn(@Parameter(description = "로그인 정보") @RequestBody SignInDto signInDto) {
        String username = signInDto.getUsername();
        String password = signInDto.getPassword();
        JwtToken jwtToken = memberService.signIn(username, password);
        log.info("request username = {}, password = {}", username, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }
    @Operation(summary = "회원가입, 취미도 그냥 String으로 주쇼!")
    @PostMapping("/sign-up")
    public ResponseEntity<MemberDto> signUp(@Parameter(description = "회원가입할 유저 정보") @RequestBody SignUpDto signUpDto) {
        Member savedMember = memberService.signUp(signUpDto);
        subjectService.createChecker(savedMember);
        memberService.hobbyJoin(Hobby.createHobby(signUpDto.getHobby(),savedMember));

        return ResponseEntity.ok(MemberDto.toDto(savedMember));
    }
    @Operation(summary = "회원 취미 저장, 리스트로 저장해 둔 뒤 List<String> 형식으로 취미 전달")
    @PostMapping("/{id}/save/hobby")
    public Boolean saveHobby(@Parameter(description = "유저 id") @PathVariable("id") Long id, @Parameter(description = "취미 리스트")@RequestBody SaveHobbyDto saveHobbyDto){
        Member byId = memberService.findById(id);
        List<String> hobby = saveHobbyDto.getHobby();
        for (String h : hobby) {
            memberService.hobbyJoin(Hobby.createHobby(h,byId));
        }

        return true;
    }

    @Operation(summary = "유저 정보 조회")
    @GetMapping("/{id}")
    public MemberDto getMember(@Parameter(description = "유저 id") @PathVariable("id") Long id){
        return MemberDto.toDto(memberService.findById(id));
    }
    @Operation(summary = "유저 탈퇴")
    @DeleteMapping("/{id}")
    public void deleteMember(@Parameter(description = "유저 id") @PathVariable("id") Long id){
        memberService.deleteMember(id);
    }
    @Operation(summary = "유저 정보 수정")
    @PutMapping("/{id}")
    public MemberDto updateMember(@Parameter(description = "유저 id") @PathVariable("id") Long id, @Parameter(description = "업데이트할 유저 정보") @RequestBody UpdateMemberDto memberDto){

        return MemberDto.toDto(memberService.updateMemberInfo(id,memberDto));
    }


}