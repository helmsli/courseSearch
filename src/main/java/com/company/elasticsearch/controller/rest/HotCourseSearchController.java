package com.company.elasticsearch.controller.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.elasticsearch.domain.CourseSearch;
import com.company.elasticsearch.domain.HotCourseRecommend;
import com.company.elasticsearch.domain.NewCourseRecommend;
import com.company.elasticsearch.domain.SearchRequest;
import com.company.elasticsearch.domain.SearchResult;
import com.company.elasticsearch.repositories.HotCourseRecommSearchRepository;
import com.company.elasticsearch.service.SearchEByDefaultService;
import com.company.elasticsearch.service.SearchEByHotCourseService;
import com.company.elasticsearch.service.SearchEByNewCourseService;
import com.google.gson.reflect.TypeToken;
import com.xinwei.nnl.common.domain.ProcessResult;
import com.xinwei.nnl.common.util.JsonUtil;

@RestController
@RequestMapping("/hotCourseSearch")
public class HotCourseSearchController {
	@Autowired
	private SearchEByHotCourseService searchEByHotCourseService;
	
	@PostMapping(value = "/saveCourse")
	public  ProcessResult saveCourse(@RequestBody HotCourseRecommend courses) {
		try {
			searchEByHotCourseService.saveCourse(courses);
			return ControllerUtils.getSuccessResponse(null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ControllerUtils.getFromResponse(e, SearchResult.RESULT_FAILURE, null);
		}
	}
	
	@PostMapping(value = "/search")
	public  ProcessResult searchCourse(@RequestBody SearchRequest searchContent) {
		try {
			return searchEByHotCourseService.searchCourse(searchContent.getKeyword(), searchContent);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ControllerUtils.getFromResponse(e, SearchResult.RESULT_FAILURE, null);
		}
	}
	
	@PostMapping(value = "/queryOnCourse")
	public  ProcessResult queryOneCourse(@RequestBody String courseId) {
		try {
			HotCourseRecommend courseSearch = searchEByHotCourseService.findOne(courseId);
			 if(courseSearch!=null)
			 {
				 ProcessResult ProcessResult =ControllerUtils.getSuccessResponse(null);
				 ProcessResult.setResponseInfo(courseSearch);
				 return ProcessResult;
			 }
			 return ControllerUtils.getErrorResponse(SearchResult.RESULT_INT_NOT_FOUND, SearchResult.RESULT_STRING_NOT_FOUND);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ControllerUtils.getFromResponse(e, SearchResult.RESULT_FAILURE, null);
		}
	}
	
	@PostMapping(value = "/{courseId}/updateCourse")
	public  ProcessResult queryOneCourse(@PathVariable String courseId,  @RequestBody String updateParameters) {
		try {
			java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>() {  
		       }.getType();  
		       Map<String, String> updateMaps = JsonUtil.fromJson(updateParameters, type);  
			 return searchEByHotCourseService.updateParameters(courseId, updateMaps);
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ControllerUtils.getFromResponse(e, SearchResult.RESULT_FAILURE, null);
		}
	}
}
