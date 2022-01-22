package com.cactus.api.message.CactusMessageApi.service;

import com.cactus.api.message.CactusMessageApi.domain.Board;
import com.cactus.api.message.CactusMessageApi.domain.BoardRepository;
import com.cactus.api.message.CactusMessageApi.domain.BoardType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * CactusMessageApi
 * Class: BoardServiceImpl
 * Created by hapo on 2019-12-03.
 * Description:
 */

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardRepository boardRepository;

    @Override
    public List<Board> findByTypeOrderByInsertTimeDesc(BoardType type) {
        return boardRepository.findByTypeOrderByInsertTimeDesc(type);
    }

    @Override
    public Optional<Board> save(Board board) {
        board.setInsertTime(LocalDateTime.now());
        return Optional.of(boardRepository.save(board));
    }

    @Override
    public Optional<Board> findByBoardId(Long boardId) {
        return boardRepository.findByBoardId(boardId);
    }

    @Override
    public Optional<Board> addViewCount(Long boardId) {
        Optional<Board> board = boardRepository.findByBoardId(boardId);
        int viewCount = board.get().getViewCount();
        viewCount = viewCount++;
        board.get().setViewCount(viewCount);


        return Optional.of(boardRepository.save(board.get()));
    }
}
