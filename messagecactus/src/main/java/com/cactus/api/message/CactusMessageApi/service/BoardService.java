package com.cactus.api.message.CactusMessageApi.service;

import com.cactus.api.message.CactusMessageApi.domain.Board;
import com.cactus.api.message.CactusMessageApi.domain.BoardType;

import java.util.List;
import java.util.Optional;

/**
 * Class: BoardService
 * Description:
 */
public interface BoardService {
    List<Board> findByTypeOrderByInsertTimeDesc(BoardType type);
    Optional<Board> save(Board board);
    Optional<Board> findByBoardId(Long boardId);
    Optional<Board> addViewCount(Long boardId);

}
