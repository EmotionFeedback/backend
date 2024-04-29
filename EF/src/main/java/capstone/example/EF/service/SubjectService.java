package capstone.example.EF.service;

import capstone.example.EF.domain.member.Member;
import capstone.example.EF.domain.subject.Subject;
import capstone.example.EF.domain.subject.SubjectCheck;
import capstone.example.EF.exception.CustomException;
import capstone.example.EF.repository.subject.SubjectCheckRepository;
import capstone.example.EF.repository.subject.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectCheckRepository subjectCheckRepository;
    private final SubjectRepository subjectRepository;

    @Transactional
    public Subject subjectJoin(Subject sj){
        Subject saved = subjectRepository.save(sj);
        return saved;
    }

    @Transactional
    public SubjectCheck subjectCheckerJoin(SubjectCheck sjCheck){
        SubjectCheck saved = subjectCheckRepository.save(sjCheck);
        return saved;
    }

    @Transactional
    public Boolean deleteSubjectById(Long id){
        if(subjectRepository.existsById(id)){
            subjectRepository.deleteById(id);
            return true;
        }
        else{
            throw new CustomException("해당 아이디가 존재하지 않습니다.");
        }
    }

    @Transactional
    public boolean deleteSubjectCheckerById(Long id){
        if(subjectCheckRepository.existsById(id)){
            subjectCheckRepository.deleteById(id);
            return true;
        }
        else{
            throw new CustomException("해당 아이디가 존재하지 않습니다.");
        }
    }


    @Transactional
    public String suggestSubject(Member member){
        Random random = new Random();
        int i = random.nextInt(5);

        if(member.getCheckers().size() > 5){
            Long i1 = member.getCheckers().get(i).getChecker();
            String re = subjectRepository.findById(i1).get().getSubject();
            member.getCheckers().remove(i);
            SubjectCheck byChecker = subjectCheckRepository.findByMemberAndChecker(member,i1);
            this.deleteSubjectCheckerById(byChecker.getId());

            return re;
        }
        else if(member.getCheckers().size() > 1){
            Long i1 = member.getCheckers().get(1).getChecker();
            String re = subjectRepository.findById(i1).get().getSubject();
            member.getCheckers().remove(1);
            SubjectCheck byChecker = subjectCheckRepository.findByMemberAndChecker(member,i1);
            this.deleteSubjectCheckerById(byChecker.getId());
            return re;
        }
        else{
            Long i1 = member.getCheckers().get(0).getChecker();
            String re = subjectRepository.findById(i1).get().getSubject();
            member.getCheckers().remove(0);
            SubjectCheck byChecker = subjectCheckRepository.findByMemberAndChecker(member,i1);
            this.deleteSubjectCheckerById(byChecker.getId());
            this.createChecker(member);
            return re;
        }
    }

    @Transactional
    public void createChecker(Member member){
        for(int i = 1;i<=50;i++) {
            SubjectCheck check = SubjectCheck.createSubjectCheck(member, (long) i);
            this.subjectCheckerJoin(check);
        }
    }
}
