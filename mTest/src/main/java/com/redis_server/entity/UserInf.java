package com.redis_server.entity;

// default package

import java.sql.Timestamp;
import java.util.Date;

/**
 * UserInf entity. @author MyEclipse Persistence Tools
 */

public class UserInf implements java.io.Serializable {

	private static final long serialVersionUID = -1667267884755531087L;
	
	// Fields
	private Integer uuid;
	private String account;
	private String password;
	private String email;
	private String nickname;
	private String headerImg;
	private String provinceString;
	private String cityString;
	private String regionString;
	private String sexString;
	private Date birthdayTime;
	private String birthdayString;
	private String heightString;
	private String weightString;
	private String summary;
	private String professionalString;
	private String salaryString;
	private String company;
	private Integer totalCoin;
	private Integer freezeCoin;
	private Integer posiHeight;
	private String onlineStatusString;
	private String sessionId;
	private String loginSourceString;
    private Short province;
    private Short city;
    private Short region;
    private Short sex;
    private Short height;
    private Short weight;
    private Short professional;
    private Short salary;
    private Double posiLongitude;
    private Double posiLatitude;
    private Short onlineStatus;
    private Timestamp lastLoginTime;
    private Short loginSource;
    private Timestamp createTime;
    private Byte age;
    private Byte posiStatus;
    private Byte dataFull;
    private Byte status;

	// Constructors

	/** default constructor */
	public UserInf() {
	}

	public UserInf(Integer uuid, String account, String password, String email, String nickname, String headerImg, String provinceString, String cityString, String regionString, String sexString, Date birthdayTime, String birthdayString, String heightString, String weightString, String summary, String professionalString, String salaryString, String company, Integer totalCoin, Integer freezeCoin, Integer posiHeight, String onlineStatusString, String sessionId, String loginSourceString, Short province, Short city, Short region, Short sex, Short height, Short weight, Short professional, Short salary, Double posiLongitude, Double posiLatitude, Short onlineStatus, Timestamp lastLoginTime, Short loginSource, Timestamp createTime, Byte age, Byte posiStatus, Byte dataFull, Byte status)
	{
		super();
		this.uuid = uuid;
		this.account = account;
		this.password = password;
		this.email = email;
		this.nickname = nickname;
		this.headerImg = headerImg;
		this.provinceString = provinceString;
		this.cityString = cityString;
		this.regionString = regionString;
		this.sexString = sexString;
		this.birthdayTime = birthdayTime;
		this.birthdayString = birthdayString;
		this.heightString = heightString;
		this.weightString = weightString;
		this.summary = summary;
		this.professionalString = professionalString;
		this.salaryString = salaryString;
		this.company = company;
		this.totalCoin = totalCoin;
		this.freezeCoin = freezeCoin;
		this.posiHeight = posiHeight;
		this.onlineStatusString = onlineStatusString;
		this.sessionId = sessionId;
		this.loginSourceString = loginSourceString;
		this.province = province;
		this.city = city;
		this.region = region;
		this.sex = sex;
		this.height = height;
		this.weight = weight;
		this.professional = professional;
		this.salary = salary;
		this.posiLongitude = posiLongitude;
		this.posiLatitude = posiLatitude;
		this.onlineStatus = onlineStatus;
		this.lastLoginTime = lastLoginTime;
		this.loginSource = loginSource;
		this.createTime = createTime;
		this.age = age;
		this.posiStatus = posiStatus;
		this.dataFull = dataFull;
		this.status = status;
	}

	/** minimal constructor */
	public UserInf(String account, String password, Short province, Short city, Short region, Short sex, Short height, Short weight, Short professional, Short salary,String company,Integer totalCoin, Integer freezeCoin, Short onlineStatus, Timestamp lastLoginTime, String sessionId, Short loginSource, Timestamp createTime,Byte posiStatus, Byte dataFull, Byte status) {
        this.account = account;
        this.password = password;
        this.province = province;
        this.city = city;
        this.region = region;
        this.sex = sex;
        this.height = height;
        this.weight = weight;
        this.professional = professional;
        this.salary = salary;
        this.company=company;
        this.totalCoin = totalCoin;
        this.freezeCoin = freezeCoin;
        this.onlineStatus = onlineStatus;
        this.lastLoginTime = lastLoginTime;
        this.sessionId = sessionId;
        this.loginSource = loginSource;
        this.createTime = createTime;
        this.posiStatus=posiStatus;
        this.dataFull = dataFull;
        this.status = status;
    }
    
    /** full constructor */
    public UserInf(String account, String password, String email, String nickname, String headerImg, Short province, Short city, Short region, Short sex, Date birthdayTime, Short height, Short weight, String summary, Short professional, Short salary,String company,Integer totalCoin, Integer freezeCoin, Double posiLongitude, Double posiLatitude, Integer posiHeight, Short onlineStatus, Timestamp lastLoginTime, String sessionId, Short loginSource, Timestamp createTime,Byte posiStatus, Byte dataFull, Byte status) {
        this.account = account;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.headerImg = headerImg;
        this.province = province;
        this.city = city;
        this.region = region;
        this.sex = sex;
        this.birthdayTime = birthdayTime;
        this.height = height;
        this.weight = weight;
        this.summary = summary;
        this.professional = professional;
        this.salary = salary;
        this.company=company;
        this.totalCoin = totalCoin;
        this.freezeCoin = freezeCoin;
        this.posiLongitude = posiLongitude;
        this.posiLatitude = posiLatitude;
        this.posiHeight = posiHeight;
        this.onlineStatus = onlineStatus;
        this.lastLoginTime = lastLoginTime;
        this.sessionId = sessionId;
        this.loginSource = loginSource;
        this.createTime = createTime;
        this.posiStatus = posiStatus;
        this.dataFull = dataFull;
        this.status = status;
    }

	// Property accessors

	public Integer getUuid() {
		return this.uuid;
	}

	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeaderImg() {
		return this.headerImg;
	}

	public void setHeaderImg(String headerImg) {
		this.headerImg = headerImg;
	}

	public String getProvinceString() {
		return provinceString;
	}

	public void setProvinceString(String provinceString) {
		this.provinceString = provinceString;
	}

	public String getCityString() {
		return cityString;
	}

	public void setCityString(String cityString) {
		this.cityString = cityString;
	}

	public String getRegionString() {
		return regionString;
	}

	public void setRegionString(String regionString) {
		this.regionString = regionString;
	}

	public String getSexString() {
		return sexString;
	}

	public void setSexString(String sexString) {
		this.sexString = sexString;
	}

	public String getBirthdayString() {
		if (birthdayTime!=null) {
			java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
			this.birthdayString = df.format(this.birthdayTime);
			return birthdayString;
		}
		return "";
	}

	public void setBirthdayString(String birthdayString) {
		this.birthdayString = birthdayString;
	}

	@SuppressWarnings("deprecation")
	public Byte getAge() {
		byte age = -1;
		if (this.birthdayTime != null) {
			age = (byte) ((new java.util.Date()).getYear() - birthdayTime.getYear());
		}
		return age;
	}

	public String getHeightString() {
		return heightString;
	}

	public void setHeightString(String heightString) {
		this.heightString = heightString;
	}

	public String getWeightString() {
		return weightString;
	}

	public void setWeightString(String weightString) {
		this.weightString = weightString;
	}

	public String getProfessionalString() {
		return professionalString;
	}

	public void setProfessionalString(String professionalString) {
		this.professionalString = professionalString;
	}

	public String getSalaryString() {
		return salaryString;
	}

	public void setSalaryString(String salaryString) {
		this.salaryString = salaryString;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getOnlineStatusString() {
		return onlineStatusString;
	}

	public void setOnlineStatusString(String onlineStatusString) {
		this.onlineStatusString = onlineStatusString;
	}

	public String getLoginSourceString() {
		return loginSourceString;
	}

	public void setLoginSourceString(String loginSourceString) {
		this.loginSourceString = loginSourceString;
	}

	public Date getBirthdayTime() {
		return this.birthdayTime;
	}

	public void setBirthdayTime(Date birthdayTime) {
		this.birthdayTime = birthdayTime;
	}

	public String getSummary() {
		return this.summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Integer getTotalCoin() {
		return this.totalCoin;
	}

	public void setTotalCoin(Integer totalCoin) {
		this.totalCoin = totalCoin;
	}

	public Integer getFreezeCoin() {
		return this.freezeCoin;
	}

	public void setFreezeCoin(Integer freezeCoin) {
		this.freezeCoin = freezeCoin;
	}

	public Integer getPosiHeight() {
		return this.posiHeight;
	}

	public void setPosiHeight(Integer posiHeight) {
		this.posiHeight = posiHeight;
	}


	public Timestamp getLastLoginTime() {
		return this.lastLoginTime;
	}


	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public Short getProvince() {
		return province;
	}

	public void setProvince(Short province) {
		this.province = province;
	}

	public Short getCity() {
		return city;
	}

	public void setCity(Short city) {
		this.city = city;
	}

	public Short getRegion() {
		return region;
	}

	public void setRegion(Short region) {
		this.region = region;
	}

	public Short getSex() {
		return sex;
	}

	public void setSex(Short sex) {
		this.sex = sex;
	}

	public Short getHeight() {
		return height;
	}

	public void setHeight(Short height) {
		this.height = height;
	}

	public Short getWeight() {
		return weight;
	}

	public void setWeight(Short weight) {
		this.weight = weight;
	}

	public Short getProfessional() {
		return professional;
	}

	public void setProfessional(Short professional) {
		this.professional = professional;
	}

	public Short getSalary() {
		return salary;
	}

	public void setSalary(Short salary) {
		this.salary = salary;
	}

	public Double getPosiLongitude() {
		return posiLongitude;
	}

	public void setPosiLongitude(Double posiLongitude) {
		this.posiLongitude = posiLongitude;
	}

	public Double getPosiLatitude() {
		return posiLatitude;
	}

	public void setPosiLatitude(Double posiLatitude) {
		this.posiLatitude = posiLatitude;
	}

	public Short getOnlineStatus() {
		return onlineStatus;
	}

	public void setOnlineStatus(Short onlineStatus) {
		this.onlineStatus = onlineStatus;
	}

	public Short getLoginSource() {
		return loginSource;
	}

	public void setLoginSource(Short loginSource) {
		this.loginSource = loginSource;
	}

	public void setAge(Byte age) {
		this.age = age;
	}

	public Byte getPosiStatus() {
		return posiStatus;
	}

	public void setPosiStatus(Byte posiStatus) {
		this.posiStatus = posiStatus;
	}

	public Byte getDataFull() {
		return dataFull;
	}

	public void setDataFull(Byte dataFull) {
		this.dataFull = dataFull;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

}