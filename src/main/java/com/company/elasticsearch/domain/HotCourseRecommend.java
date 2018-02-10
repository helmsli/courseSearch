package com.company.elasticsearch.domain;

import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "hotcoursecommindex", type = "hotCourseRecommend", shards = 1, replicas = 0, refreshInterval = "-1")
public class HotCourseRecommend extends CourseSearchBase {

}
