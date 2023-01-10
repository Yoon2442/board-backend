package com.example.boardbackend.services;

import com.example.boardbackend.entity.BoardEntity;
import com.example.boardbackend.entity.BoardRepository;
import com.example.boardbackend.web.dtos.BoardDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * 게시글 목록 가져오기
     */
    public List<BoardDto> getBoardList() {
        List<BoardEntity> boardEntities = boardRepository.findAll();
        List<BoardDto> dtos = new ArrayList<>();

        for (BoardEntity entity : boardEntities) {
            BoardDto dto = BoardDto.builder()
                    .id(entity.getId())
                    .user_id(entity.getUser_id())
                    .title(entity.getTitle())
                    .body(entity.getBody())
                    .build();
            dtos.add(dto);
        }

        return dtos;
    }

    /**
     * 게시글 가져오기
     */
    public BoardDto getBoard(Long id) {
        BoardEntity entity = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        return BoardDto.builder()
                .id(entity.getId())
                .user_id(entity.getUser_id())
                .title(entity.getTitle())
                .body(entity.getBody())
                .build();
    }

    /**
     * 게시글 등록
     */
    public BoardEntity create(BoardDto boardDto) {
        BoardEntity entity = BoardEntity.builder()
                .user_id(boardDto.getUser_id())
                .title(boardDto.getTitle())
                .body(boardDto.getBody())
                .build();
        return boardRepository.save(entity);
    }

    /**
     * 게시글 수정
     */
    public BoardEntity update(BoardDto boardDto) {
        System.out.println("print : "+boardRepository.findById(boardDto.getId()));
        BoardEntity entity = boardRepository.findById(boardDto.getId()).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        entity.setUser_id(boardDto.getUser_id());
        entity.setTitle(boardDto.getTitle());
        entity.setBody(boardDto.getBody());
        return boardRepository.save(entity);
    }

    /**
     * 게시글 삭제
     */
    public void delete(Long id) {
        BoardEntity entity = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        boardRepository.delete(entity);
    }
}
