package capstone.example.EF.repository.member;

import capstone.example.EF.domain.member.Email;
import capstone.example.EF.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Boolean existsByEmail(Email email);
    Member findByEmail(Email email);
}
