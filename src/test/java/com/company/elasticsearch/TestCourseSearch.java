package com.company.elasticsearch;

import java.util.Calendar;

import org.springframework.web.client.RestTemplate;

import com.company.elasticsearch.domain.CourseSearch;
import com.company.elasticsearch.domain.SearchRequest;
import com.xinwei.nnl.common.domain.ProcessResult;

public class TestCourseSearch {
	private String baseUrl = "http://www.chunzeacademy.com:8080/courseSearch/saveCourse";
	private RestTemplate restTemplate = new RestTemplate();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TestCourseSearch testCourseSearch  =new TestCourseSearch();
		testCourseSearch.saveCourseSearch();
		testCourseSearch.searchContent(null);;
	}
	
	public void saveCourseSearch()
	
	{
		String baseUrl = "http://www.chunzeacademy.com:8080/courseSearch/saveCourse";
		
		CourseSearch courseSearch = new CourseSearch();
		
		courseSearch.setCategory("category1");
		courseSearch.setCheckCrc("checkCrc");
		courseSearch.setCourseAvatar("courseAvatar");
		courseSearch.setCourseChapter("chapter");
		courseSearch.setCourseId("courseId");
		courseSearch.setCourseInfo("wo shi course Info");
		courseSearch.setCreateTime(Calendar.getInstance().getTime());
		courseSearch.setCredit(100);
		courseSearch.setDetail("detail");
		courseSearch.setDifficultyLevel("defficultylevel");
		courseSearch.setExpireDate(Calendar.getInstance().getTime());
		courseSearch.setFitPeople("fitPeople");
		courseSearch.setOriginalPrice(12.12f);
		courseSearch.setOwner("liguogiang");
		courseSearch.setPriceVer(100);
		courseSearch.setRealPrice(12.11f);
		courseSearch.setSearchKeys("abcdef");
		courseSearch.setSellAmount(100);
		courseSearch.setStatus(1);
		courseSearch.setTeacherName("李国强 刘凤芹");
		courseSearch.setTitle("人工智能真好 大数据真好");
		courseSearch.setTotalRank(1000);
		courseSearch.setCourseId("1234578");
		courseSearch.setId(courseSearch.getCourseId());
		baseUrl = "http://www.chunzeacademy.com:8080/courseSearch/saveCourse";
		ProcessResult processResult = restTemplate.postForObject(baseUrl, courseSearch, ProcessResult.class);
		System.out.println(processResult);
		
	}
	
	private void searchContent(String content)
	{
		String baseUrl = "http://www.chunzeacademy.com:8080/courseSearch/search";
		SearchRequest searchRequest = new SearchRequest();
		searchRequest.setKeyword("真好");
		searchRequest.setPageNum(1);
		searchRequest.setPageSize(100);
		ProcessResult processResult = restTemplate.postForObject(baseUrl, searchRequest, ProcessResult.class);
		System.out.println(processResult);
		
	}

}
