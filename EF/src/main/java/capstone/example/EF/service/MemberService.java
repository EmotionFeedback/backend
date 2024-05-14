package capstone.example.EF.service;

import capstone.example.EF.domain.member.Email;
import capstone.example.EF.domain.member.Hobby;
import capstone.example.EF.domain.member.Member;
import capstone.example.EF.exception.CustomException;
import capstone.example.EF.repository.member.HobbyRepository;
import capstone.example.EF.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository userRepository;
    private final HobbyRepository hobbyRepository;

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
    public Boolean deleteMember(Email email){
        Member byEmail = userRepository.findByEmail(email);
        if(byEmail == null){
            return false;
        }
        userRepository.delete(byEmail);
        return true;
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
}
