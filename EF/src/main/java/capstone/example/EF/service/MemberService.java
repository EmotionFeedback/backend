package capstone.example.EF.service;

import capstone.example.EF.auth.domain.JwtToken;
import capstone.example.EF.auth.utils.JwtTokenProvider;
import capstone.example.EF.domain.member.Email;
import capstone.example.EF.domain.member.Hobby;
import capstone.example.EF.domain.member.Member;
import capstone.example.EF.dto.member.SignUpDto;
import capstone.example.EF.dto.member.UpdateMemberDto;
import capstone.example.EF.exception.CustomException;
import capstone.example.EF.repository.member.HobbyRepository;
import capstone.example.EF.repository.member.MemberRepository;
import capstone.example.EF.repository.subject.SubjectRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository userRepository;
    private final HobbyRepository hobbyRepository;
    private final SubjectRepository subjectRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(Member user){
        Member savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Transactional
    public Long hobbyJoin(Hobby hobby){
        Hobby saved = hobbyRepository.save(hobby);
        return saved.getId();
    }

    @Transactional
    public Boolean deleteMember(Long id){
        Optional<Member> byId = userRepository.findById(id);
        if(byId.isEmpty()){
            throw new CustomException("해당 Id를 가진 유저가 존재하지 않습니다.");
        }
        else{
            Member member = byId.get();

            userRepository.delete(member);
            return true;
        }
    }

    @Transactional
    public Member updateMemberInfo(Long id, UpdateMemberDto updateMemberDto){
        Optional<Member> byId = userRepository.findById(id);
        if(byId.isEmpty()){
            throw new CustomException("해당 Id를 가진 유저가 존재하지 않습니다.");
        }
        else{
            Member member = byId.get();
            return member.updateUserInfo(updateMemberDto);
        }
    }



    @Transactional
    public void updateCallingPoint(Member member, Integer callingPoint){
        member.updateCallingPoint(callingPoint);
    }

    public Member findById(Long id){
        Member member = userRepository.findById(id).orElseThrow(() -> new CustomException("해당 id가 존재하지 않습니다."));
        return member;
    }

    public Member findByEmail(Email email){

        if(userRepository.existsByEmail(email)){
            return userRepository.findByEmail(email);
        }
        else{
            throw new CustomException("해당 email이 존재하지 않습니다.");
        }
    }

    @Transactional
    public JwtToken signIn(String username, String password) {
        // 1. username + password 를 기반으로 Authentication 객체 생성
        // 이때 authentication 은 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        // 2. 실제 검증. authenticate() 메서드를 통해 요청된 Member 에 대한 검증 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }

    @Transactional
    public Member signUp(SignUpDto signUpDto){
        if (userRepository.existsByEmail(Email.from(signUpDto.getEmail()))) {
            throw new IllegalArgumentException("이미 사용 중인 사용자 이름입니다.");
        }
        // Password 암호화
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());

        return userRepository.save(signUpDto.toEntity(encodedPassword));
    }


}
