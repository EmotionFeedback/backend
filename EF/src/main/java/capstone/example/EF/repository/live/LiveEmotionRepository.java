package capstone.example.EF.repository.live;

import capstone.example.EF.domain.live.LiveEmotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveEmotionRepository extends JpaRepository<LiveEmotion, Long> {
}
