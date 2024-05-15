package capstone.example.EF.auth.utils;

import capstone.example.EF.domain.member.Email;
import capstone.example.EF.domain.member.Member;
import capstone.example.EF.repository.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UsernamePwdAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MemberRepository customerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        Member customer = customerRepository.findByEmail(Email.from(username));
        if(customer != null) {
            if (passwordEncoder.matches(pwd, customer.getPassword())) {
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                return new UsernamePasswordAuthenticationToken(username, pwd, authorities);

            } else {
                throw new BadCredentialsException("유효하지 않은 비밀번호입니다.");
            }
        }else{
            throw  new BadCredentialsException("일치하는 이메일이 존재하지 않습니다.");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
