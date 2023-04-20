package com.jdbc.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.model.vo.Member;

public class BasicJdbcTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//jdbc이용해서 오라클과 연동해보기
		//1.오라클에서 제공하는 ojdbc.jar파일을 버전에 맞춰서 다운로드
		//2.이클립스에서 프로젝트를 생성하고 생성된 프로젝트 라이브러리에 다운받은 jar파일을 추가한다.
		//프로젝트 우클릭>build path>configuration build path>build path>add external jar해도 가능
		
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;//사용후 반드시 닫아줘야하는 셋이어서 try문안의 finally에 들어가야하는데, try문 안에 있으면 에러나므로 밖에 선언
		
		//프로젝트(어플리케이션)에서 DB에 접속하기
		//1.jar파일이 제공하는 클래스가 있는 지 확인하기
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");//이건 고정값, 찾을수 없다면 예외발생함
			//2.DriverManager클래스를 이용해서 접속하는 객체를 만들어준다.
			//DriverManager클래스가 제공하는 getConnection() static메소드를 이용해서 Connection객체를 가져온다(생성없이 공용으로 사용하려고)
			//->getConnection()메소드는 Connection 객체를 반환한다
			//getConnection이용하기->3개의 매개변수가 선언되어있음
			//첫번째 인수:접속할 DB의 주소, 버전정보,포트번호 포함 String타입
			//접속할 DBMS별로 문자열 패턴이 정해져있음
			//오라클 패턴: jdbc:oracle:thin:@ip주소:포트번호:버전
			//두번째 인수:DB접속 계정명 String
			//세번째 인수:DB접속 계정 비밀번호 string
			conn=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","student","student");
			conn.setAutoCommit(false);//transaction을 개발자가 직접 처리하겠다!
			System.out.println("DB접속 확인 완료!");
			//3.접속된 DB에 sql문을 실행하고 결과를 가져와야 함
			//sql문을 실행하기 위해서 실행할 객체가 필요함
			//Statement, PreparedStatement : 문자열로 작성한 sql구문을 연결된 DB에서 실행하는 객체
			//sql문을 실행하려면 Statement의 멤버 메소드 executeQuery()또는 excuteUpdate()메소드를 이용한다
			//SELECT : executeQuery("sql문")->ResultSet객체를 반환
			//INSERT, UPDATE, DELETE : executeUpate("sql문")->int 반환
			
			//1)쿼리문 작성하기
			//MEMBER테이블에서 아이디가 admin인 회원 조회하기
			//문자열 변수에 sql문을 저장할때는 ;을 생략한다!
			//String sql="SELECT * FROM MEMBER WHERE MEMBER_ID='admin'";
			//MEMBER테이블에서 전체조회하기
			String sql="SELECT * FROM MEMBER";
			//2)Statement객체 가져오기
			//Connection클래스가 제공하는 멤버 메소드인 createStatement메소드를 이용한다
			stmt=conn.createStatement();
			//3)쿼리문 실행시키기
			//Statement가 제공하는 executeQuery()실행하고 반환은 ResultSet객체로 받는다
			rs=stmt.executeQuery(sql);
			System.out.println(rs);
			
			//4.ResultSet이용하기
			//반환된 SELECT문의 실행 결과는 ResultSet의 객체가 제공하는 메소드를 이용해서 컬럼별 값을 가져온다
			//테이블=클래스, 컬럼=변수, 행=객체
			//next() : 이터레이터에서 보던 그 넥스트, 데이터의 row를 지정->row데이터를 가져옴. 반환형은 boolean. 반복문에 활용가능
			//get자료형[String,Int,Date]("컬럼명"||인덱스번호) : 
			//getString():char,varchar2,nchar,nvarchar2 자료형을 가져올 때
			//getInt()/getDouble():number자료형을 가져올 때
			//getDate()/getTimeStamp():date, timestamp 자료형을 가져올 때
			//rs.next(); 1번째 row지칭
//			while(rs.next()) {
//			
//				String memberId=rs.getString("member_id");
//				String memberPwd=rs.getString("member_pwd");
//				int age=rs.getInt("age");
//				Date enrollDate=rs.getDate("enroll_date");
//				System.out.println(memberId+" "+memberPwd+" "+age+" "+enrollDate);
//			}
//			System.out.println(rs.next());
//			memberId=rs.getString("member_id");
//			memberPwd=rs.getString("member_pwd");
//			age=rs.getInt("age");
//			enrollDate=rs.getDate("enroll_date");
//			System.out.println(memberId+" "+memberPwd+" "+age+" "+enrollDate);
			
			//DB의 row를 가져왔을 때 자바에서는 클래스로 저장해서 관리
			//하나의 row만 가져온다면?
			
			
			List<Member> members=new ArrayList();//List로는 생성 불가, 인터페이스니까 그안의 ArrayList, LinkedList, Vector중에서 생성해야함
			
			while(rs.next()) {
				Member m=new Member();
				m.setMemberId(rs.getString("member_id"));
				m.setMemberPwd(rs.getString("member_pwd"));
				m.setMemberName(rs.getString("member_name"));
				m.setGender(rs.getString("gender"));
				m.setAge(rs.getInt("age"));
				m.setEmail(rs.getString("email"));
				m.setPhone(rs.getString("phone"));
				m.setAddress(rs.getString("address"));
				m.setHobby(rs.getString("hobby"));
				m.setEnrollDate(rs.getDate("enroll_date"));
				members.add(m);
			}
			members.forEach((m)->System.out.println(m));//toString오버라이딩 되어있어야 함, alt+s+s+s
			
			
			//DML구문 실행하기 : insert, update, delete *트랜젝션 처리(commit, rollback)를 반드시 해야한다!
			//Connection 객체에서 연결이 끊기면 자동으로 auto commit해주긴 함
			//->conn.setAutoCommit(false);써서 직접 처리할수 있도록 바꿔줘야 가능함
			//->리터럴 형태에 맞춰서 작성해야한다 '문자열',숫자,'년년/월월/일일'
			sql="INSERT INTO MEMBER VALUES('inhoru','inhoru','최인호','M',26,'inhoru@inhoru.com','0101234145','금천구','영화감상,애니감상,코딩',SYSDATE)";
			int result=stmt.executeUpdate(sql);
			//트랜젝션구문으로 처리하기, 하나의 트랜젝션안에 여러개의 구문이 있을때(실제 상황) 반드시 필요함
			if(result>0) conn.commit();
			else conn.rollback();
			System.out.println(result);
			
			//5.생성한 객체를 반드시 반환해줘야 한다
			//(1)(3)Connection, (2)(2)Statement, (3)(1)[ResultSet-셀렉트 했을때 만]->(열때)(닫을때)
			//close()메소드를 이용해서 반환을 한다
			//생성의 역순으로 반환해줘야 한다
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}//jar파일 정상등록여부 확인과정
		catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(stmt!=null)stmt.close();
				if(conn!=null)conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
