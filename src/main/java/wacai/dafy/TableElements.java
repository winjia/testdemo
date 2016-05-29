package wacai.dafy;

public class TableElements {
	private String loanNumber;
	private String saleAddress;
	private String similarBusiness;
	private String name;
	private String gender;
	private String certiType;
	private String certiId;
	private String certiValidDate;
	private String registerType;
	private String customerType;
	private String socialIdstuId;
	private String education;
	private String marryStatus;
	private String childNum;
	private String phoneNumber;
	private String phoneNumberValidDate;
	private String phoneMonthFee;
	private String phoneNameIsReal;
	private String email;
	private String qqNumber;
	private String houseType;
	private String presentAddressTime;
	private String rent;
	private String addressSameRegister;
	private String commodityType1;
	private String commodityPrice1;
	private String commodityType2;
	private String commodityPrice2;
	private String TotalPrice;
	private String payByMyself;
	private String loanAmount;
	private String stageNumber;
	private String companyFullname;
	private String jobPosition;
	private String industryCategory;
	private String companyProperty;
	private String companyPhone;
	private String workTime;
	private String totalWorkTime;
	private String individualIncome;
	private String familyIncome;
	private String individualexpense;
	
	public TableElements() {
		
	}
	
	public void fillTableField(String fieldname, String fieldvalue) {
		if (fieldname.equals("合同号")) {
			this.setLoanNumber(fieldvalue);
		} else if (fieldname.equals("销售点地址")) {
			this.setSaleAddress(fieldvalue);
		} else if (fieldname.equals("姓名")) {
			this.setName(fieldvalue);
		} else if (fieldname.equals("性别")) {
			this.setGender(fieldvalue);
		} else if (fieldname.equals("身份证号码")) {
			this.setCertiType("身份证");
			this.setCertiId(fieldvalue);
		} else if (fieldname.equals("身份证有效期至")) {
			this.setCertiValidDate(fieldvalue);
		} else if (fieldname.equals("社保号码学生号码")) {
			this.setSocialIdstuId(fieldvalue);
		} else if (fieldname.equals("教育程度")) {
			this.setEducation(fieldvalue);
		} else if (fieldname.equals("婚姻状况")) {
			this.setMarryStatus(fieldvalue);
		} else if (fieldname.equals("子女数目")) {
			this.setChildNum(fieldvalue);
		} else if (fieldname.equals("手机号")) {
			this.setPhoneNumber(fieldvalue);
		} else if (fieldname.equals("电子邮箱")) {
			this.setEmail(fieldvalue);
		} else if (fieldname.equals("QQ号码")) {
			this.setQqNumber(fieldvalue);
		} else if (fieldname.equals("住房状况")) {
			this.setHouseType(fieldvalue);
		} else if (fieldname.equals("现居住地址是否与户籍地址相同")) {
			this.setAddressSameRegister(fieldvalue);
		} else if (fieldname.equals("商品类型1")) {
			this.setCommodityType1(fieldvalue);
		} else if (fieldname.equals("商品价格1")) {
			this.setCommodityPrice1(fieldvalue);
		} else if (fieldname.equals("商品类型2")) {
			this.setCommodityType2(fieldvalue);
		} else if (fieldname.equals("商品价格2")) {
			this.setCommodityPrice2(fieldvalue);
		} else if (fieldname.equals("商品总价")) {
			this.setTotalPrice(fieldvalue);
		} else if (fieldname.equals("自付金额")) {
			this.setPayByMyself(fieldvalue);
		} else if (fieldname.equals("贷款本金")) {
			this.setLoanAmount(fieldvalue);
		} else if (fieldname.equals("分期期数")) {
			this.setStageNumber(fieldvalue);
		} else if (fieldname.equals("单位学校个体全称")) {
			this.setCompanyFullname(fieldvalue);
		} else if (fieldname.equals("职位")) {
			this.setJobPosition(fieldvalue);
		} else if (fieldname.equals("行业类别")) {
			this.setIndustryCategory(fieldvalue);
		} else if (fieldname.equals("单位学校性质")) {
			this.setCompanyProperty(fieldvalue);
		} else if (fieldname.equals("单位院系电话")) {
			this.setCompanyPhone(fieldvalue);
		} else if (fieldname.equals("距离毕业时间现公司工作时间")) {
			this.setWorkTime(fieldvalue);
		} else if (fieldname.equals("总工作年限学制时间")) {
			this.setTotalWorkTime(fieldvalue);
		} else if (fieldname.equals("月收入总额")) {
			this.setIndividualIncome(fieldvalue);
		} else if (fieldname.equals("家庭月收入")) {
			this.setFamilyIncome(fieldvalue);
		} else if (fieldname.equals("个人月支出")) {
			this.setIndividualexpense(fieldvalue);
		}
	}

	public String getLoanNumber() {
		return loanNumber;
	}

	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}

	public String getSaleAddress() {
		return saleAddress;
	}

	public void setSaleAddress(String saleAddress) {
		this.saleAddress = saleAddress;
	}

	public String getSimilarBusiness() {
		return similarBusiness;
	}

	public void setSimilarBusiness(String similarBusiness) {
		this.similarBusiness = similarBusiness;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCertiType() {
		return certiType;
	}

	public void setCertiType(String certiType) {
		this.certiType = certiType;
	}

	public String getCertiId() {
		return certiId;
	}

	public void setCertiId(String certiId) {
		this.certiId = certiId;
	}

	public String getCertiValidDate() {
		return certiValidDate;
	}

	public void setCertiValidDate(String certiValidDate) {
		this.certiValidDate = certiValidDate;
	}

	public String getRegisterType() {
		return registerType;
	}

	public void setRegisterType(String registerType) {
		this.registerType = registerType;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getSocialIdstuId() {
		return socialIdstuId;
	}

	public void setSocialIdstuId(String socialIdstuId) {
		this.socialIdstuId = socialIdstuId;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getMarryStatus() {
		return marryStatus;
	}

	public void setMarryStatus(String marryStatus) {
		this.marryStatus = marryStatus;
	}

	public String getChildNum() {
		return childNum;
	}

	public void setChildNum(String childNum) {
		this.childNum = childNum;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPhoneNumberValidDate() {
		return phoneNumberValidDate;
	}

	public void setPhoneNumberValidDate(String phoneNumberValidDate) {
		this.phoneNumberValidDate = phoneNumberValidDate;
	}

	public String getPhoneMonthFee() {
		return phoneMonthFee;
	}

	public void setPhoneMonthFee(String phoneMonthFee) {
		this.phoneMonthFee = phoneMonthFee;
	}

	public String getPhoneNameIsReal() {
		return phoneNameIsReal;
	}

	public void setPhoneNameIsReal(String phoneNameIsReal) {
		this.phoneNameIsReal = phoneNameIsReal;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQqNumber() {
		return qqNumber;
	}

	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}

	public String getHouseType() {
		return houseType;
	}

	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}

	public String getPresentAddressTime() {
		return presentAddressTime;
	}

	public void setPresentAddressTime(String presentAddressTime) {
		this.presentAddressTime = presentAddressTime;
	}

	public String getRent() {
		return rent;
	}

	public void setRent(String rent) {
		this.rent = rent;
	}

	public String getAddressSameRegister() {
		return addressSameRegister;
	}

	public void setAddressSameRegister(String addressSameRegister) {
		this.addressSameRegister = addressSameRegister;
	}

	public String getCommodityType1() {
		return commodityType1;
	}

	public void setCommodityType1(String commodityType1) {
		this.commodityType1 = commodityType1;
	}

	public String getCommodityPrice1() {
		return commodityPrice1;
	}

	public void setCommodityPrice1(String commodityPrice1) {
		this.commodityPrice1 = commodityPrice1;
	}

	public String getCommodityType2() {
		return commodityType2;
	}

	public void setCommodityType2(String commodityType2) {
		this.commodityType2 = commodityType2;
	}

	public String getCommodityPrice2() {
		return commodityPrice2;
	}

	public void setCommodityPrice2(String commodityPrice2) {
		this.commodityPrice2 = commodityPrice2;
	}

	public String getTotalPrice() {
		return TotalPrice;
	}

	public void setTotalPrice(String totalPrice) {
		TotalPrice = totalPrice;
	}

	public String getPayByMyself() {
		return payByMyself;
	}

	public void setPayByMyself(String payByMyself) {
		this.payByMyself = payByMyself;
	}

	public String getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}

	public String getStageNumber() {
		return stageNumber;
	}

	public void setStageNumber(String stageNumber) {
		this.stageNumber = stageNumber;
	}

	public String getCompanyFullname() {
		return companyFullname;
	}

	public void setCompanyFullname(String companyFullname) {
		this.companyFullname = companyFullname;
	}

	public String getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}

	public String getIndustryCategory() {
		return industryCategory;
	}

	public void setIndustryCategory(String industryCategory) {
		this.industryCategory = industryCategory;
	}

	public String getCompanyProperty() {
		return companyProperty;
	}

	public void setCompanyProperty(String companyProperty) {
		this.companyProperty = companyProperty;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getWorkTime() {
		return workTime;
	}

	public void setWorkTime(String workTime) {
		this.workTime = workTime;
	}

	public String getTotalWorkTime() {
		return totalWorkTime;
	}

	public void setTotalWorkTime(String totalWorkTime) {
		this.totalWorkTime = totalWorkTime;
	}

	public String getIndividualIncome() {
		return individualIncome;
	}

	public void setIndividualIncome(String individualIncome) {
		this.individualIncome = individualIncome;
	}

	public String getFamilyIncome() {
		return familyIncome;
	}

	public void setFamilyIncome(String familyIncome) {
		this.familyIncome = familyIncome;
	}

	public String getIndividualexpense() {
		return individualexpense;
	}

	public void setIndividualexpense(String individualexpense) {
		this.individualexpense = individualexpense;
	}
	
	
}
