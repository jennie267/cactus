package com.cactus.api.message.CactusMessageApi.api;

import com.cactus.api.message.CactusMessageApi.domain.Board;
import com.cactus.api.message.CactusMessageApi.domain.BoardType;
import com.cactus.api.message.CactusMessageApi.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Class: BoardController
 * Description:
 */

@RestController
@RequestMapping(value = "/message/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @GetMapping(value = "/notice")
    public Map findNoticeList(){
        List<Board> list = boardService.findByTypeOrderByInsertTimeDesc(BoardType.NOTICE);

        Map<String, List<Board>> noticesMap = new HashMap<>();
        noticesMap.put("boards", list);

        return noticesMap;
    }

    @GetMapping(value = "/event")
    public Map findEventList(){
        List<Board> list = boardService.findByTypeOrderByInsertTimeDesc(BoardType.EVENT);

        Map<String, List<Board>> eventsMap = new HashMap<>();
        eventsMap.put("boards", list);

        return eventsMap;
    }

    @PostMapping
    public Optional<Board> save(@RequestBody Board board){
        return boardService.save(board);
    }

    @PutMapping
    public Optional<Board> update(@RequestBody Board board){
        return boardService.save(board);
    }

    @PutMapping(value = "/add-view/{id}")
    public Optional<Board> addViewCount(@PathVariable Long id){
        return boardService.addViewCount(id);
    }

}
