package com.cactus.api.message.CactusMessageApi.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * CactusMessageApi
 * Class: Board
 * Created by hapo on 2019-12-03.
 * Description:
 */

@Setter
@Getter
@Entity
@NoArgsConstructor
public class Board {
    @Id
    @Column(name = "board_id")
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long boardId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private BoardType type;

    @Column(name = "title")
    private String title;

    @Column(name = "contents")
    private String contents;

    @Column(name = "insert_time")
    private LocalDateTime insertTime;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "view_count")
    private int viewCount;

    @Builder
    public Board(BoardType type, String title, String contents, LocalDateTime insertTime, String imgUrl, int viewCount) {
        this.type = type;
        this.title = title;
        this.contents = contents;
        this.insertTime = insertTime;
        this.imgUrl = imgUrl;
        this.viewCount = viewCount;
    }
}
