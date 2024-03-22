package capstone.example.EF.repository;

import capstone.example.EF.domain.LiveEmotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveEmotionRepository extends JpaRepository<LiveEmotion, Long> {
}
