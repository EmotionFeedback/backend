package capstone.example.EF.repository;

import capstone.example.EF.domain.LiveRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LiveRoomRepository extends JpaRepository<LiveRoom,Long> {
}
