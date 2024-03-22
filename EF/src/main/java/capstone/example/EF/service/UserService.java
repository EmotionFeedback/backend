package capstone.example.EF.service;

import capstone.example.EF.domain.User;
import capstone.example.EF.exception.UserException;
import capstone.example.EF.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public Long join(User user){
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Transactional
    public Boolean deleteUser(String email){
        User byEmail = userRepository.findByEmail(email);
        if(byEmail == null){
            return false;
        }
        userRepository.delete(byEmail);
        return true;
    }

    public User findById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("해당 id가 존재하지 않습니다."));
        return user;
    }

    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
