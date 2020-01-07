package chapter1.part5;

import java.util.ArrayList;
import java.util.List;

public class Boss {

	public void commandCheckNumber(TeamLeader teamLeader) {
		// 模拟boss一页一页往下翻页，TeamLeader实时统计
		List<Course> courseList = new ArrayList<Course>();
		for(int  i = 0; i < 20; i++) {
			courseList.add(new Course());
		}
		teamLeader.checkNumberOfCourses(courseList);
	}
	
	public void commandCheckNumber2(TeamLeader teamLeader) {
		
		teamLeader.checkNumberOfCourses2();
	}
	
	public static void main(String[] args) {
		Boss boss = new Boss();
		TeamLeader teamLeader = new TeamLeader();
		boss.commandCheckNumber(teamLeader);
		
		boss.commandCheckNumber2(teamLeader);
	}
}
