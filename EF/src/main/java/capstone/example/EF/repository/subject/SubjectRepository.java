package capstone.example.EF.repository.subject;

import capstone.example.EF.domain.Emotion;
import capstone.example.EF.domain.subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
}

