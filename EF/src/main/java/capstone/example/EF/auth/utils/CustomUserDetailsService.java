package capstone.example.EF.auth.utils;

import capstone.example.EF.domain.member.Email;
import capstone.example.EF.domain.member.Member;
import capstone.example.EF.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member byEmail = memberRepository.findByEmail(Email.from(username));

        if(byEmail != null){
            return this.createUserDetails(byEmail);
        }
        else{
            throw new UsernameNotFoundException(username);
        }
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 return
    private UserDetails createUserDetails(Member member) {
        return User.builder()
                .username(member.getEmail().getValue())
                .password(passwordEncoder.encode(member.getPassword()))
                .roles("")
                .build();
    }

}
