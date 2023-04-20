package com.jdbc.view;

import java.util.List;
import java.util.Scanner;

import com.jdbc.common.MemberController;
import com.jdbc.controller.MemberControllerImpl;
import com.jdbc.model.dto.MemberDTO;

public class MainView {
	Scanner sc = new Scanner(System.in);
	private MemberController controller = new MemberControllerImpl();;
		
	public void mainMenu() {
		
		while(true) {
			System.out.println("==== JDBC 회원관리 프로그램 ====");
			System.out.println("1. 전체 회원조회");
			System.out.println("2. 아이디로 회원조회");
			System.out.println("3. 이름으로 회원조회");
			System.out.println("4. 회원등록");
			System.out.println("5. 회원수정(이름, 나이, 이메일, 주소)");
			System.out.println("6. 주영씨 주석좀 하세요! 공부해야지요!");
			System.out.println("0. 프로그램 종료");
			System.out.print("메뉴선택 : ");
			int cho = sc.nextInt();
			
			switch(cho) {
				case 1: controller.selectAllMember(); break;
				case 2: controller.selectMemberById(); break;
				case 3: controller.selectMemberByName(); break;
				case 4: controller.insertMember(); break;
				case 5: controller.updateMember(); break;
				case 0: System.out.println("프로그램을 종료합니다."); return;
				default : System.out.println("0~5사이의 메뉴를 입력하세요");
			}
		}
	}
	
	
	public void printMembers(List<MemberDTO> members) {
		System.out.println("==== 조회된 결과 ====");
		members.forEach((m)-> System.out.println(m));
		System.out.println("=================");
	}
	
	
	public void printMember(MemberDTO m) {
		System.out.println("==== 조회된 결과 ====");
		System.out.println(m != null ? m : "검색 결과가 없습니다.");
		System.out.println("=================");
	}
	
	
	public String inputData(String title) {
		System.out.println("==== 검색할 "+title+"입력 ====");
		System.out.print("입력 : ");
		return sc.nextLine();
	}
	
	
	public MemberDTO addMember() {
		MemberDTO m = new MemberDTO();
		System.out.println("===== 회원등록 =====");
		System.out.print("아이디 입력 : ");
		String memberId = sc.nextLine();
		m.setMemberId(memberId);
		System.out.print("비밀번호 입력 : ");
		String memberPwd = sc.nextLine();
		m.setMemberPwd(memberPwd);
		System.out.print("이름 입력 : ");
		String memberName = sc.nextLine();
		m.setMemberName(memberName);
		System.out.print("성별 입력(M/F) : ");
		char gender = sc.next().charAt(0);
		m.setGender(gender);
		System.out.print("나이 입력 : ");
		int age = sc.nextInt();
		m.setAge(age);
		sc.nextLine();
		System.out.print("이메일 입력 : ");
		String email = sc.nextLine();
		m.setEmail(email);
		System.out.print("전화번호 입력 : ");
		String phone = sc.nextLine();
		m.setPhone(phone);
		System.out.print("주소 입력 : ");
		String address = sc.nextLine();
		m.setAddress(address);
		System.out.print("취미(,로 구분) : ");
		String[] hobby = sc.nextLine().split(",");
		m.setHobby(hobby);
		
		//return new MemberDTO(memberId,memberPwd,memberName,gender,age,email,phone,address,hobby,null);
		return m;
	}
	

	public void printMsg(String msg) {
		System.out.println("==== 시스템 메세지 ====");
		System.out.println(msg);
		System.out.println("==================");
	}
	
	
	
	public MemberDTO updateData(){
		MemberDTO m = new MemberDTO();
		
		System.out.println("===== 회원정보 수정 =====");
		System.out.print("수정할 회원 아이디 : ");
		m.setMemberId(sc.nextLine());
		System.out.print("새 이름 : ");
		m.setMemberName(sc.nextLine());
		System.out.print("새 나이 : ");
		m.setAge(sc.nextInt());
		sc.nextLine();
		System.out.print("새 주소 : ");
		m.setAddress(sc.nextLine());
		System.out.print("새 이메일 : ");
		m.setEmail(sc.nextLine());
				
		return m;
	}
	
	
	
	
	
	
}
