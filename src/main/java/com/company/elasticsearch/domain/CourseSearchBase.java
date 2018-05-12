package com.company.elasticsearch.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.elasticsearch.annotations.Field;

import com.xinwei.nnl.common.util.JsonUtil;

public class CourseSearchBase implements Serializable {
private String id;
	
	/**
	 * course的对象
	 */
	 private  transient final String DEFAULT_CHARSET = "utf-8";
		
	/**
	 * 分区ID
	 */
   private String partitionId;

	/** 课程编号. */
	private String courseId;

	/** 课程标题. */
	@Field(analyzer="ik_smart", searchAnalyzer="ik_smart")
	private String title;

	/** 课程简介. */
	@Field(analyzer="ik_smart", searchAnalyzer="ik_smart")
	private String courseInfo;

	/** 课程章节信息. */
	@Field(analyzer="ik_smart", searchAnalyzer="ik_smart")
	private String courseChapter=null;


	/** 课程详情. */
	@Field(analyzer="ik_smart", searchAnalyzer="ik_smart")
	private String detail=null;

	/** 适用人群. */
	private String fitPeople;

	/** 标签. */
	@Field(analyzer="ik_smart", searchAnalyzer="ik_smart")
	private String searchKeys;

	/** 课程分类. */
	@Field(analyzer="ik_smart", searchAnalyzer="ik_smart")
	private String category;

	/** 课程头像. */
	private String courseAvatar;

	/** 课程难度级别. */
	private String difficultyLevel;

	/** 发布者. */
	private String owner;

	/** 发布时间. */
	private Date createTime;

	/** 发布的原价. */
	private float originalPrice=0;

	/** 实际成交价格. */
	private float realPrice=0;

	/** 课程下架时间. */
	private Date expireDate=null;

	/** 价格版本. */
	private long priceVer=0;

	/** 课程保护字段. */
	private transient String checkCrc;

	/**
	 * 1--准备发布
2--已经发布
3--已经发布后修改待二次发布
4--下架
255-已经全部购买
	 */
	private int status=0;
	
	@Field(analyzer="ik_smart", searchAnalyzer="ik_smart") 
	private String teacherName;
	
	@Field(analyzer="ik_smart", searchAnalyzer="ik_smart") 
	private String teacherResume;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
		this.partitionId = changePrititionId(courseId);
		this.setId(courseId);
	}

	public static String changePrititionId(String srcCourseId)
	{
		String retPartitionId="000";
		if(!StringUtils.isEmpty(srcCourseId)&&srcCourseId.length()>=7)
		{
			 retPartitionId = srcCourseId.substring(srcCourseId.length() - 7, srcCourseId.length() - 4);
		}
		return retPartitionId;
	}
	
	public static String getDbId(String courseId)
	{
		return courseId.substring(courseId.length() - 4, courseId.length());
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		
		this.title = title;
	}

	public String getCourseInfo() {
		return courseInfo;
	}

	public void setCourseInfo(String courseInfo) {
		this.courseInfo = courseInfo;
	}

	
	public void setCourseChapter(String courseChapter) {
		this.courseChapter = courseChapter;
	}
	public String getCourseChapter()
	{
		return courseChapter;
	}

	
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getDetail()
	{
		return this.detail;
	}

	public String getFitPeople() {
		return fitPeople;
	}

	public void setFitPeople(String fitPeople) {
		this.fitPeople = fitPeople;
	}
	

	public String getSearchKeys() {
		return searchKeys;
	}

	public void setSearchKeys(String searchKeys) {
		this.searchKeys = searchKeys;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCourseAvatar() {
		return courseAvatar;
	}

	public void setCourseAvatar(String courseAvatar) {
		this.courseAvatar = courseAvatar;
	}

	public String getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(String difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public float getOriginalPrice() {
		return originalPrice;
	}

	public void setOriginalPrice(float originalPrice) {
		this.originalPrice = originalPrice;
	}

	public float getRealPrice() {
		return realPrice;
	}

	public void setRealPrice(float realPrice) {
		this.realPrice = realPrice;
	}

	public Date getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}

	public long getPriceVer() {
		return priceVer;
	}

	public void setPriceVer(long priceVer) {
		this.priceVer = priceVer;
	}

	public String getCheckCrc() {
		return checkCrc;
	}

	public void setCheckCrc(String checkCrc) {
		this.checkCrc = checkCrc;
	}
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getTeacherResume() {
		return teacherResume;
	}

	public void setTeacherResume(String teacherResume) {
		this.teacherResume = teacherResume;
	}
	
	

	public String getPartitionId() {
		return partitionId;
	}

	public void setPartitionId(String partitionId) {
		this.partitionId = partitionId;
	}

	//end course
	
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
	
	public void format()
	{
		this.setDetail(JsonUtil.toJson(this));
		this.setSearchKeys(this.formatLower(this.getSearchKeys()));
		this.setCategory(formatLower(this.getCategory()));
		this.setTitle(formatLower(this.getTitle()));
		this.setCourseInfo(formatLower(this.getCourseInfo()));
		this.setTeacherName(formatLower(this.getTeacherName()));

	}
	public String formatLower(String source)
	{
		if(StringUtils.isEmpty(source))
		{
			return null;
		}
		return StringUtils.lowerCase(source);
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
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}
	

}
