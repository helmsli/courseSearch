package com.company.elasticsearch.domain;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "newcoursecommindex", type = "newCourseRecommend", shards = 1, replicas = 0, refreshInterval = "-1")
public class NewCourseRecommend extends CourseSearchBase {

}
