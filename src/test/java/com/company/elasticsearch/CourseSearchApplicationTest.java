package com.company.elasticsearch;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.company.elasticsearch.domain.CourseSearch;
import com.company.elasticsearch.service.SearchEByDefaultService;
@SpringBootTest
@RunWith(SpringRunner.class)
public class CourseSearchApplicationTest {
	@Autowired
	private SearchEByDefaultService searchEByDefaultService;
	
	@Test
	public void test() {
		CourseSearch courseSearch = new CourseSearch();
		courseSearch.setCourseId("0123456");
		String ls = searchEByDefaultService.saveCourse(courseSearch);
		System.out.println("**********:" + searchEByDefaultService.findOne("0123456"));
		searchEByDefaultService.updateTotalRank("0123456", 100);
		System.out.println("abc");
		System.out.println("----------:" + searchEByDefaultService.findOne("0123456"));
		
	}

}
