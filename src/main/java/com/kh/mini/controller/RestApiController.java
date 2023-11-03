package com.kh.mini.controller;
import com.kh.mini.dao.MemberDAO;
import com.kh.mini.vo.MemberVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// 컨트롤러 : 요청, 응답만 존재
//          DAO와 컨트롤러 사이에 비즈니스 로직이 존재해야 함 (컨트롤러에 있지않으면 됨)


@RestController              // : REST API 기능을 포함함
@RequestMapping("/test")     // : 별다른 선언없이 HTTP의 요청을 받음, url 당겨옴. 공통된 경로를 넣어주면 됨
public class RestApiController {
    @GetMapping("/name")   // GET방식으로 요청
    public String getHello() {
        return "GET 방식에 대해서 응답 합니다.";
    }
    // 페이지 번호, 게시글 or 댓글 번호
    @GetMapping("/board/{pageNo}/{commNo}")
    public String getVariable(@PathVariable String pageNo, @PathVariable String commNo) {
        return "GET 방식 : " + pageNo + " / " + commNo;
    }
    // 검색기능 (검색결과 보여줌)
    @GetMapping("/search")
    public String getRequestParam(
            @RequestParam String model,
            @RequestParam String price,
            @RequestParam String company) {
        return "model : " + model + ", " + "price : " + price + ", " + "제조사 : " + company;
    }
    // Post : 회원가입 추가
    @PostMapping("/member")
    public ResponseEntity<Boolean> setMember(@RequestBody Map<String, String> regData) {
        String getId = regData.get("id");
        String getPwd = regData.get("pwd");
        String getName = regData.get("name");
        String getEmail =regData.get("email");
        MemberDAO dao = new MemberDAO();
        boolean isReg = dao.memberRegister(getId, getPwd, getName, getEmail);
        return new ResponseEntity<>(isReg, HttpStatus.OK);
    }
    // 회원가입 데이터 받은거 확인 (DB에 정보 추가 되어야 함)
    @GetMapping("/member")
    public ResponseEntity<List<MemberVO>> getMemberList() {
        List<MemberVO> list = new ArrayList<>();
        MemberDAO dao = new MemberDAO();
        list = dao.memberSelect("All");
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
