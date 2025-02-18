package jung.gyu.board.service;

import jung.gyu.board.dao.BoardDAO;
import jung.gyu.board.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    private static final int PAGE_SIZE = 10;
    @Autowired
    BoardDAO boardDAO;

    // 1. 전체 게시물 조회
    public List<BoardVO> getAllBoard(int page) {
        int offset = (page - 1) * PAGE_SIZE;
        return boardDAO.getAllBoard(offset, PAGE_SIZE);
    }

    // 2. 카테고리별 게시글 조회
    public List<BoardVO> getBoardByCategory(int page, String category) {
        int offset = (page - 1) * PAGE_SIZE;
        return boardDAO.getBoardByCategory(category, offset, PAGE_SIZE);
    }

    // 3. 전체 게시글 개수 조회
    public int getTotalPages(String category, String searchType, String keyword) {
        int totalCount;

        if (searchType != null && !searchType.isEmpty()) {
            totalCount = boardDAO.getSearchBoardCount(searchType, keyword);
        } else if (category != null && !category.isEmpty()) {
            totalCount = boardDAO.getCategoryBoardCount(category);
        } else {
            totalCount = boardDAO.getTotalBoardCount();
        }

        return (int) Math.ceil((double) totalCount / PAGE_SIZE);
    }

    // 4. 검색 기능 (페이징)
    public List<BoardVO> searchBoard(String searchType, String keyword, int page) {
        int offset = (page - 1) * PAGE_SIZE;
        return boardDAO.searchBoard(searchType, "%" + keyword + "%", offset, PAGE_SIZE);
    }

    // 5. 게시글 등록
    public boolean writePost(BoardVO boardVO) {
        int result = boardDAO.insertPost(boardVO);
        return result > 0;
    }

    // 6. 보기 팝업
    public BoardVO getBoardById(int bNo){
        return boardDAO.selectBoardById(bNo);
    }

    // 7. 게시글 삭제
    public boolean deletePost(int bNo) {
        int result = boardDAO.deletePost(bNo);
        return result > 0;
    }
}
