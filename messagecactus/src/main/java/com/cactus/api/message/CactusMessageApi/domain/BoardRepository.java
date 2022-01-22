package com.cactus.api.message.CactusMessageApi.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

/**
 * Class: BoardRepository
 * Description:
 */
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTypeOrderByInsertTimeDesc(BoardType type);

    Optional<Board> findByBoardId(Long id);
}