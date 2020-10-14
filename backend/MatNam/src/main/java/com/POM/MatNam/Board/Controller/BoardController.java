package com.POM.MatNam.Board.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.POM.MatNam.Board.DAO.BoardDao;
import com.POM.MatNam.Board.DTO.Board;
import com.POM.MatNam.Board.Response.BasicResponse;


import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin(origins = { "*" })
@RequestMapping("feature/board")
public class BoardController {
   @Autowired
   BoardDao boardDao;
  
   
   @ApiOperation(value="수정하기", response = BoardController.class)
   @PutMapping("/update")
   public Object like(@RequestBody Board request) throws Exception {
      Optional<Board> board = boardDao.findById(request.getId());
      ResponseEntity response = null;
      final BasicResponse result = new BasicResponse();
      if (board.isPresent()) {
         Board b = board.get();
         b.setSubject(request.getSubject());
         b.setContent(request.getContent());
         boardDao.save(b);
         result.status = true;
         result.data = "success";
         response = new ResponseEntity<>(result, HttpStatus.OK);
         return response;
      }
      result.status = false;
      result.data = "fail to Update";
      response = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
      return response;
   }
	
	@ApiOperation(value = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping("list")
	public List<Board> getBoardList() throws Exception {
		System.out.println("뭐가 실행이 되는거지? getBoardList()");
		List<Board> list = boardDao.findAll();
		return list;
	}

	@ApiOperation(value = "게시글번호에 해당하는 게시글의 정보를 반환한다.", response = BoardController.class)
	@GetMapping("/list/{id}")
	public Object detailBoard(@PathVariable int id) {
		System.out.println(id);
		Optional<Board> board = boardDao.findById(id);

			
			return board;

		
	}

	@ApiOperation(value = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/write")
	public Board writeBoard(@RequestBody Board board) {

		return boardDao.save(board);
	}

	@ApiOperation(value = "게시글번호에 해당하는 게시글의 정보를 삭제한다.", response = String.class)
	@DeleteMapping("delete/{id}")
	public Optional<Board> deleteBoard(@PathVariable("id") int id) {
		Optional<Board> boardId = boardDao.findById(id);

		boardDao.deleteById(id);
		return null;
	}

}
