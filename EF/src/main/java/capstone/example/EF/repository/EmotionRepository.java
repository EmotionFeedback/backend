package capstone.example.EF.repository;

import capstone.example.EF.domain.Emotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmotionRepository extends JpaRepository<Emotion, Long> {
}
