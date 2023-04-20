package com.jdbc.model.dao;

import static com.jdbc.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.jdbc.common.JDBCTemplate;
import com.jdbc.model.dto.MemberDTO;

public class MemberDao {
	private Properties sql = new Properties();
	
	{
		try {  // 루트밑에 빈밑에 패키지 밑에 member 밑에 파일이름
			String path=MemberDao.class.getResource("/sql/member/member_sql.properties").getPath();
			sql.load(new FileReader(path));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public List<MemberDTO> selectAllMember(Connection conn){
		Statement stmt = null;
		ResultSet rs = null;
		// String sql = "SELECT * FROM MEMBER";
		String sql = this.sql.getProperty("selectMemberAll");
		List<MemberDTO> members = new ArrayList();
		
		
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				members.add(getMember(rs));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(stmt);
		}
		return members;
	}
	
    
	
	public MemberDTO selectMemberById(Connection conn, String id) {
		// sql문에 변수값을 대입할 때 자료형맞춰 편리하게 대입할 수 있는 PreparedStatement 이용하기
		// PreparedStatement는 Statement를 상속받은 클래스
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemberDTO m = null;
		
		// PreparedStatement 객체를 이용해서 sql문을 실행하려면 기본 statement와 차이가 있음
		// 외부의 값을 받아서 sql문을 구성할 때 표현과 값 대입 방식이 다름.
		// statement : "SELECT * FROM MEMBER WHERE MEMBER_ID = '"+변수명+"'";
		// PreparedStatement : 외부값을 ?표시해서 쿼리문을 작성한다.
		// : "SELECT * FROM MEMBER WHERE MEMBER_ID=?"     
		// ? -> 위치홀더 ㅋㅋ
		//String sql = "SELECT * FROM MEMBER WHERE MEMBER_ID=?";
		String sql = this.sql.getProperty("selectMemberById");
		
		// Dao 패키지는 sql안의 값을 가져와서 실행하는 역할만함
		
		try {
			// PreparedStatement는 conn.prepareStatement() 메소드를 이용
			// 인수로 sql문을 대입해줌
			pstmt = conn.prepareStatement(sql);  
			// ?표시되어 있는 곳에 값을 대입해줘야한다.
			// 대입은 PreparedStatement가 제공하는 메소드를 이용
			// set 자료형(String, int, Date...)(위치값, 대입할 값);
			// 위치값은 1부터 시작함.!! 왼쪽부터 시작
			pstmt.setString(1,id);
			
			
			//위치 홀더로 표시한 값이 있으면 반드시 값을 대입해줘야한다
			// 대입하지않으면 exception 발생
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				m = getMember(rs);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);			
		}
		return m;
	}
	
	
	
	public List<MemberDTO> selectMemberByName(Connection conn, String name){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<MemberDTO> members = new ArrayList();
		
		//String sql = "SELECT * FROM MEMBER WHERE MEMBER_NAME LIKE ?";
		String sql = this.sql.getProperty("selectMemberByName");
		
		// "SELECT * FROM MEMBER WHERE MEMBER_NAME LIKE '%'최'%'"; -> 라고 인식됨
		// "SELECT * FROM MEMBER WHERE MEMBER_NAME LIKE '%'||?||'%'";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+name+"%"); // '%
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				members.add(getMember(rs));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rs);
			JDBCTemplate.close(pstmt);			
		}
		return members;
	}
	
	
	
	
	
	
	public int insertMember(Connection conn, MemberDTO m) {
		PreparedStatement pstmt = null;
		int result = 0;
		//String sql = "INSERT INTO MEMBER VALUES(?,?,?,?,?,?,?,?,?,SYSDATE)"; // 왼쪽부터 인덱스번호 기준 1임
		
		String sql = this.sql.getProperty("insertMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,m.getMemberId());
			pstmt.setString(2, m.getMemberPwd());
			pstmt.setString(3, m.getMemberName());
			pstmt.setString(4, String.valueOf(m.getGender())); // 기본자료형에서 String으로 변환시킬때 사용
			pstmt.setInt(5, m.getAge());
			pstmt.setString(6, m.getEmail());
			pstmt.setString(7, m.getPhone());
			pstmt.setString(8, m.getAddress());
			pstmt.setString(9, String.join(",",m.getHobby()));
			
			result = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	

	
	// 이름, 나이, 이메일, 주소 수정
	public int updateMember(Connection conn ,MemberDTO m) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		//String sql = "UPDATE MEMBER SET member_name = ?, age = ?, email = ?, address = ? WHERE member_id = ?";
		String sql = this.sql.getProperty("updateMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,m.getMemberName());
			pstmt.setInt(2, m.getAge()); 
			pstmt.setString(3, m.getEmail());
			pstmt.setString(4, m.getAddress()); 
			pstmt.setString(5, m.getMemberId());

			result = pstmt.executeUpdate();

		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	
	
	
	
	private MemberDTO getMember(ResultSet rs) throws SQLException{
		MemberDTO m = new MemberDTO();
		m.setMemberId(rs.getString("member_id"));
		m.setMemberPwd(rs.getString("member_pwd"));
		m.setMemberName(rs.getString("member_name"));
		m.setGender(rs.getString("gender").charAt(0));
		m.setAge(rs.getInt("age"));
		m.setEmail(rs.getString("email"));
		m.setPhone(rs.getString("phone"));
		m.setAddress(rs.getString("address"));
		m.setHobby(rs.getString("hobby").split(","));
		m.setEnrollDate(rs.getDate("enroll_date"));
		return m;
	}
	
	

}
