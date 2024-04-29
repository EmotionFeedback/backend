package capstone.example.EF.repository;

import capstone.example.EF.domain.Emotion;
import capstone.example.EF.domain.member.Email;
import capstone.example.EF.domain.member.Member;
import capstone.example.EF.domain.subject.SubjectCheck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionRepository extends JpaRepository<Emotion, Long> {
    Emotion findByMaleAndFemale(Member Male, Member Female);

    Boolean existsByMale(Member Male);

    Boolean existsByFemale(Member Female);


}
