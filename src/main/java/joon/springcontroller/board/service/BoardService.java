package joon.springcontroller.board.service;

import joon.springcontroller.board.entity.*;
import joon.springcontroller.board.model.*;
import joon.springcontroller.board.repository.*;
import joon.springcontroller.user.entity.User;
import joon.springcontroller.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardTypeRepository boardTypeRepository;
    private final BoardRepository boardRepository ;
    private final UserRepository userRepository;
    private final BoardHitsRepository boardHitsRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final BoardBadReportRepository boardBadReportRepository;
    private final BoardScrapRepository boardScrapRepository;
    private final BoardBookMarkRepository boardBookMarkRepository;

    @Transactional
    public ServiceResult addBoardType(BoardTypeInput boardTypeInput) {
        Optional<BoardType> optionalBoardType = boardTypeRepository.findByBoardName(boardTypeInput.getName());
        if(optionalBoardType.isPresent()){
            return ServiceResult.fail("이미 해당 이름에 보드타입이 있습니다.");
        }

        boardTypeRepository.save(BoardType.of(boardTypeInput.getName()));

        return ServiceResult.success();
    }
    @Transactional
    public ServiceResult updateBoardType(Long boardTypeId, BoardTypeInput boardTypeInput) {
        Optional<BoardType> optionalBoardType = boardTypeRepository.findById(boardTypeId);
        if(optionalBoardType.isEmpty()){
            return ServiceResult.fail("해당 보드 타입이 없습니다.");
        }
        BoardType boardType=optionalBoardType.get();
        boardType.updateName(boardTypeInput.getName());
        return ServiceResult.success();
    }
    @Transactional
    public ServiceResult deleteBoardType(Long id) {
        Optional<BoardType> optionalBoardType = boardTypeRepository.findById(id);
        if(optionalBoardType.isEmpty()){
            return ServiceResult.fail("삭제할 게시판 타입이 없습니다.");
        }
        BoardType boardType = optionalBoardType.get();
        Long countByBoardType = boardRepository.countByBoardType(boardType);
        if(countByBoardType>0){
            return ServiceResult.fail("삭제할 게시판 타입의 게시글이 존재합니다.");
        }
        boardTypeRepository.delete(boardType);
        return ServiceResult.success();
    }

    public List<BoardType> getBoardTypes() {
        return boardTypeRepository.findAll();
    }

    public ServiceResult usingBoardType(Long boardTypeId, boolean usingYn) {
        Optional<BoardType> optionalBoardType = boardTypeRepository.findById(boardTypeId);
        if(optionalBoardType.isEmpty()){
            return ServiceResult.fail("해당 게시판 타입은 찾지못하였습니다.");
        }
        BoardType boardType = optionalBoardType.get();
        if(boardType.isUsingYn()==usingYn){
            return ServiceResult.fail("이미 게시판 사용 여부가 반영되었습니다.");
        }
        return ServiceResult.success();
    }

    public List<BoardTypeCount> getBoardTypeCount() {
        List<Object[]> boardTypeCount = boardTypeRepository.findBoardTypeCount();
        List<BoardTypeCount> boardTypeCountList = boardTypeCount.stream().map(
                objects -> BoardTypeCount.of(objects))
                .collect(Collectors.toList());

        return boardTypeCountList;
    }
    @Transactional
    public ServiceResult setBoardTop(Long boardId, boolean topYn) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if(optionalBoard.isEmpty()){
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();
        if(board.isTopYn()==topYn){
            if (topYn) {
                return ServiceResult.fail("이미 게시글이 최상단에 배치되어 있습니다.");
            } else {
                return ServiceResult.fail("이미 게시글이 최산단 배치가 헤제되어 있습니다.");
            }
        }
        board.updateTop();
        return ServiceResult.success();
    }
    @Transactional
    public ServiceResult setBoardPeriod(Long boardId, BoardPeriod boardPeriod) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if(optionalBoard.isEmpty()){
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();
        board.setPublish(boardPeriod.getStartDate(), boardPeriod.getEndDate());
        return ServiceResult.success();
    }
    @Transactional
    public ServiceResult setBoardHits(Long boardId, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if(optionalBoard.isEmpty()){
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isEmpty()){
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        User user = optionalUser.get();
        if (boardHitsRepository.countByBoardAndUser(board, user)>0){
            return ServiceResult.fail("이미 조회수가 있습니다.");
        }
        boardHitsRepository.save(BoardHits.of(user, board));
        return ServiceResult.success();
    }
    @Transactional
    public ServiceResult setBoardLike(Long boardId, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if(optionalBoard.isEmpty()){
            return ServiceResult.fail("해당 게시글이 없습니다.");
        }
        Board board = optionalBoard.get();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()){
           return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        User user = optionalUser.get();
        long count=boardLikeRepository.countByUserAndBoard(user, board);

        if(count>0){
           return ServiceResult.fail("이미 좋아요한 내용이 있습니다.");
        }
        BoardLike boardLike = BoardLike.of(user, board);
        boardLikeRepository.save(boardLike);
        return ServiceResult.success();
    }
    @Transactional
    public ServiceResult setBoardUnLike(Long boardId, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if(optionalBoard.isEmpty()){
            return ServiceResult.fail("해당 게시글이 없습니다.");
        }
        Board board = optionalBoard.get();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()){
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        User user = optionalUser.get();
        Optional<BoardLike> optionalBoardLike=boardLikeRepository.findByUserAndBoard(user, board);

        if(optionalBoardLike.isEmpty()){
            return ServiceResult.fail("좋아요한 내용이 없습니다");
        }
        BoardLike boardLike = optionalBoardLike.get();
        boardLikeRepository.delete(boardLike);
        return ServiceResult.success();
    }
    @Transactional
    public ServiceResult addBadBoard(Long boardId, String email, BoardBadReportInput badReportInput) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if(optionalBoard.isEmpty()){
            return ServiceResult.fail("해당 게시글이 없습니다.");
        }
        Board board = optionalBoard.get();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()){
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        User user = optionalUser.get();

        BoardBadReport boardBadReport = BoardBadReport.of(user, board, badReportInput.getComments());
        boardBadReportRepository.save(boardBadReport);

        return ServiceResult.success();
    }

    public List<BoardBadReport> badReportList() {
        return boardBadReportRepository.findAll();
    }
    @Transactional
    public ServiceResult scrap(Long boardId, String email) {

        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if(optionalBoard.isEmpty()){
            return ServiceResult.fail("해당 게시글이 없습니다.");
        }
        Board board = optionalBoard.get();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()){
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        User user = optionalUser.get();
        BoardScrap scrap = BoardScrap.of(user, board);
        boardScrapRepository.save(scrap);
        return ServiceResult.success();
    }
    @Transactional
    public ServiceResult removeScrap(Long boardId, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(boardId);
        if(optionalBoard.isEmpty()){
            return ServiceResult.fail("해당 게시글이 없습니다.");
        }
        Board board = optionalBoard.get();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()){
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        User user = optionalUser.get();
        BoardScrap boardScrap = BoardScrap.of(user, board);
        boardScrapRepository.delete(boardScrap);
        return ServiceResult.success();
    }
    @Transactional
    public ServiceResult addBookMark(Long id, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(optionalBoard.isEmpty()){
            return ServiceResult.fail("해당 게시글이 없습니다.");
        }
        Board board = optionalBoard.get();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()){
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        User user = optionalUser.get();
        BoardBookmark boardBookmark=BoardBookmark.of(user, board);
        boardBookMarkRepository.save(boardBookmark);
        return ServiceResult.success();
    }

    public ServiceResult removeBookMark(Long id, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(optionalBoard.isEmpty()){
            return ServiceResult.fail("해당 게시글이 없습니다.");
        }
        Board board = optionalBoard.get();
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if(optionalUser.isEmpty()){
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        User user = optionalUser.get();
        BoardBookmark boardBookmark=BoardBookmark.of(user, board);
        boardBookMarkRepository.delete(boardBookmark);
        return ServiceResult.success();
    }
}
