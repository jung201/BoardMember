package jung.gyu.board.dao;

import jung.gyu.board.vo.BoardVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BoardDAO {

    // 1. 전체 게시물 조회
    List<BoardVO> getAllBoard(@Param("offset") int offset, @Param("limit") int limit);

    // 2. 카테고리별 게시글 조희
    List<BoardVO> getBoardByCategory(
            @Param("category") String category,
            @Param("offset") int offset,
            @Param("limit") int limit);

    // 3. 전체 게시글 개수 조회
    int getTotalBoardCount();

    // 4. 카테고리별 게시글 개수 조회
    int getCategoryBoardCount(
            @Param("category") String category);
}
