package jung.gyu.board.service;

import jung.gyu.board.dao.BoardDAO;
import jung.gyu.board.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    BoardDAO boardDAO;

    private static final int PAGE_SIZE = 10;

    // 1. 전체 게시물 조회
    public List<BoardVO> getAllBoard(int page){
        int offset = (page -1) * PAGE_SIZE;
        return boardDAO.getAllBoard(offset, PAGE_SIZE);
    }

    // 2. 카테고리별 게시글 조회
    public List<BoardVO> getBoardByCategory(int page, String category) {
        int offset = (page -1) * PAGE_SIZE;
        return boardDAO.getBoardByCategory(category, offset, PAGE_SIZE);
    }

    // 3. 전체 게시글 개수 조회
    public int  getTotalPages(String category) {
        int totalCount = (category == null || category.isEmpty()) ?
                boardDAO.getTotalBoardCount() :
                boardDAO.getCategoryBoardCount(category);

        return (int) Math.ceil((double) totalCount / PAGE_SIZE);
    }
}
