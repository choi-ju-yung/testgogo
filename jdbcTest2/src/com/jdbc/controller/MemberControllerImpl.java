package com.jdbc.controller;

import java.util.List;

import com.jdbc.common.MemberController;
import com.jdbc.model.dto.MemberDTO;
import com.jdbc.model.service.MemberService;
import com.jdbc.view.MainView;

public class MemberControllerImpl implements MemberController{

	//private MemberDao dao = new MemberDao();
	private MemberService service = new MemberService();
	
	
	public void mainMenu() {
		new MainView().mainMenu();
	}
	
	
	@Override
	public void selectAllMember() {
		// TODO Auto-generated method stub
		List<MemberDTO> members = service.selectAllMember();
		new MainView().printMembers(members);
	}
	
	
	// 알트 + 쉬프트 + s + v
	@Override
	public void selectMemberById() {
		String id = new MainView().inputData("아이디");
		MemberDTO m = service.selectMemberById(id);
		new MainView().printMember(m);
	}


	public void selectMemberByName() {
		String name = new MainView().inputData("이름");
		List<MemberDTO> members = service.selectMemberByName(name);
		new MainView().printMembers(members); 
	}
	

	
	@Override
	public void insertMember() {
		MemberDTO m = new MainView().addMember();
		int result = service.insertMember(m);
		new MainView().printMsg(result>0?"회원가입 성공" : "회원가입 실패");
	}

	@Override
	public void updateMember() {
		MemberDTO m = new MainView().updateData();
		int result = service.updateMember(m);
		new MainView().printMsg(result>0?"회원 수정 성공" : "회원 수정 실패");
		
	}



	
}
