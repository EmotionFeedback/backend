package capstone.example.EF.dto.live.request;

import capstone.example.EF.domain.live.Content;
import capstone.example.EF.domain.live.LiveRoom;
import lombok.Getter;

@Getter
public class ContentRequestDto {

    private String content;

    public static Content toEntity(LiveRoom liveRoom, ContentRequestDto contentRequestDto, Long memberId) {
        return Content.createContent(liveRoom,contentRequestDto.getContent(),memberId);
    }
}
