package capstone.example.EF.repository.subject;

import capstone.example.EF.domain.member.Email;
import capstone.example.EF.domain.member.Member;
import capstone.example.EF.domain.subject.SubjectCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface SubjectCheckRepository extends JpaRepository<SubjectCheck,Long> {
    SubjectCheck findByMemberAndChecker(Member member,Long checker);

}
