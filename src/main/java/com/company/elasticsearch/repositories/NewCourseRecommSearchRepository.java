package com.company.elasticsearch.repositories;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import com.company.elasticsearch.domain.CourseSearch;
import com.company.elasticsearch.domain.NewCourseRecommend;
@Repository
public interface NewCourseRecommSearchRepository extends ElasticsearchRepository<NewCourseRecommend, String> {

	
}
