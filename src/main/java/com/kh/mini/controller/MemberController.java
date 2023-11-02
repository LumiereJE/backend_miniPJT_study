package com.kh.mini.controller;

import com.kh.mini.dao.MemberDAO;
import com.kh.mini.vo.MemberVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// 프론트(3000)과 백(8111)의 번호가 다르기 때문에 3000 -> 8000을 경유해서 가야해서 이상한거 아니냐고 오류나는것을 방지
// 동일 출처 에러가 나서 막기때문에 풀어달라고 해야함
// 3001번으로 접근하면 백이 못 찾아서 오류남
// 나중엔 이 방법 말고 필터에 거는데 그땐 보안이 걸리기 때문에 이 방법과 중복으로 쓰면 안됨
@CrossOrigin(origins = "http://localhost:3000")
// 일반 컨트롤러는 레스트 컨트롤러(자동지원 프론트사이드 지원하는 애들이랑 호환이 쉬움)와 다름
@RestController
// 공통구간에 대한 것임
@RequestMapping ("/users")

public class MemberController {
    // POST 로그인
    // 경로만 지정해줌
    @PostMapping("/login")
    // @RequestBody에 값을 받음
    // Map은 키와 값이 있는 자료구조
    // map은 10개의 데이터를 열면 10개 데이터를 반환하는 애
    // loginData부분은 매개변수라 아무 이름이나 넣어도 됨
    // Map<String, String>은 프론트의 id, pwd를 뜻함. 맞게 넣어주면 됨
    public ResponseEntity<Boolean> memberLogin(@RequestBody Map<String, String> loginData) {
        String id = loginData.get("id");
        String pwd = loginData.get("pwd");
        System.out.println("ID : " + id);
        System.out.println("PWD : " + pwd);

        MemberDAO dao = new MemberDAO();
        boolean rst = dao.loginCheck(id, pwd);
        return new ResponseEntity<>(rst, HttpStatus.OK);
        // rst 라고 해줘야 id, pwd 일치 확인을 함
    }


    // GET : 회원조회
    @GetMapping("/member")
    public ResponseEntity<List<MemberVO>> memberList(@RequestParam String id) {
        System.out.println("ID : " + id);
        MemberDAO dao = new MemberDAO();
        List<MemberVO> list = dao.memberSelect(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}

