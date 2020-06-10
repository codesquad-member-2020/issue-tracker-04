package com.codesquad.issue04.domain.firstcollections;

import com.codesquad.issue04.domain.entity.Emoji;
import lombok.*;

import javax.persistence.Embeddable;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Embeddable
public class Emojis {

    private List<Emoji> emojiList;
    private Long commentId;

}
