package com.company.elasticsearch.domain;

import java.io.Serializable;
import java.util.Arrays;

import org.springframework.data.elasticsearch.annotations.Document;

import com.company.videodb.domain.Courses;

@Document(indexName = "coursesearchindex", type = "courseSearch", shards = 1, replicas = 0, refreshInterval = "-1")
public class CourseSearch extends Courses implements Serializable{
	private String id;
	
	/*
	 * 
	 */
	private int sellAmount=1;
	/**
	 * 综合排名
	 */
	private int totalRank=1;
	/**
	 * 信用
	 */
	
	private int credit=1;
	
	@Override
	public void setCourseId(String courseId)
	{
		super.setCourseId(courseId);
		this.setId(courseId);
	}
	/**
	 * 
	 */
	public int getSellAmount() {
		return sellAmount;
	}
	public void setSellAmount(int sellAmount) {
		this.sellAmount = sellAmount;
	}
	public int getTotalRank() {
		return totalRank;
	}
	public void setTotalRank(int totalRank) {
		this.totalRank = totalRank;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public String toString() {
		return "CourseSearch [id=" + id + ", sellAmount=" + sellAmount + ", totalRank=" + totalRank + ", credit="
				+ credit + ", getSellAmount()=" + getSellAmount() + ", getTotalRank()=" + getTotalRank()
				+ ", getCredit()=" + getCredit() + ", getId()=" + getId() + ", getCourseId()=" + getCourseId()
				+ ", getTitle()=" + getTitle() + ", getCourseInfo()=" + getCourseInfo() + ", getCourseChapter()="
				+ getCourseChapter() + ", getDetail()=" + getDetail() + ", getFitPeople()=" + getFitPeople()
				+ ", getSearchKeys()=" + getSearchKeys() + ", getCategory()=" + getCategory() + ", getCourseAvatar()="
				+ getCourseAvatar() + ", getDifficultyLevel()=" + getDifficultyLevel() + ", getOwner()=" + getOwner()
				+ ", getCreateTime()=" + getCreateTime() + ", getOriginalPrice()=" + getOriginalPrice()
				+ ", getRealPrice()=" + getRealPrice() + ", getExpireDate()=" + getExpireDate() + ", getPriceVer()="
				+ getPriceVer() + ", getCheckCrc()=" + getCheckCrc() + ", getStatus()=" + getStatus()
				+ ", getTeacherName()=" + getTeacherName() + ", getTeacherResume()=" + getTeacherResume()
				+ ", getPartitionId()=" + getPartitionId() + ", toString()=" + super.toString()
				+ ", getCourseChapterByte()=" + Arrays.toString(getCourseChapterByte()) + ", getDetailByte()="
				+ Arrays.toString(getDetailByte()) + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	
}
