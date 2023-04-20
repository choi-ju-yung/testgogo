package com.jdbc.model.service;

import java.sql.Connection;
import java.util.List;

import com.jdbc.common.JDBCTemplate;
import com.jdbc.model.dao.MemberDao;
import com.jdbc.model.dto.MemberDTO;
// import static com.jdbc.common.JDBCTemplate.getConnection;  // 이 static 메소드는 클래스명을 생략가능 
import static com.jdbc.common.JDBCTemplate.*;  // JDBCTemplate 클래스안의 static 메소드들을 클래스명 생략없이 사용 가능


public class MemberService {
	
	private MemberDao dao = new MemberDao();
	
	//1. DB에 연결하는 Connection 객체를 관리
	//2 .트렌젝션처리(commit, rollback)를 여기서함
	//3. 서비스에 해당하는 DAO 클래스를 호출해서 연결 DB에서 SQL문을 실행시키는 기능
	
	public List<MemberDTO> selectAllMember(){
		Connection conn = getConnection();
		List<MemberDTO> members = dao.selectAllMember(conn);
		close(conn);
		return members;
	}
	
	
	public MemberDTO selectMemberById(String id) {
		Connection conn = getConnection();
		MemberDTO m = dao.selectMemberById(conn, id);
		close(conn);
		return m;
	}
	
	
	public List<MemberDTO> selectMemberByName(String name){
		Connection conn = getConnection();
		List<MemberDTO> members =  dao.selectMemberByName(conn, name);
		close(conn);
		return members;
	}
	
	
	
	
	public int insertMember(MemberDTO m) {
		Connection conn = getConnection();
		int result = dao.insertMember(conn,m);
		
		// 트렌젝션처리
		if(result>0)commit(conn);
		else rollback(conn);
		
		close(conn);
		return result;
	}
	
	
	public int updateMember(MemberDTO m) {
		Connection conn = getConnection();
		int result = dao.updateMember(conn,m);
		
		// 트렌젝션처리
		if(result>0)commit(conn);
		else rollback(conn);
		
		close(conn);
		return result;
	}
	
}
