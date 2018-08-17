package com.dummy.contactmanager.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class Work {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long workId;  

	@Column(length=255)
	private String comapnyName;
	
	@Column(length=100)
	private String companyEmailId;
	
	@Column(length=15)
	private String work_number;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Work [work_id=" + workId + ", comapny_name=" + comapnyName + ", company_email_id=" + companyEmailId
				+ ", work_number=" + work_number + "]";
	}

	

	/**
	 * @return the workId
	 */
	public Long getWorkId() {
		return workId;
	}



	/**
	 * @param workId the workId to set
	 */
	public void setWorkId(Long workId) {
		this.workId = workId;
	}



	/**
	 * @return the comapnyName
	 */
	public String getComapnyName() {
		return comapnyName;
	}



	/**
	 * @param comapnyName the comapnyName to set
	 */
	public void setComapnyName(String comapnyName) {
		this.comapnyName = comapnyName;
	}



	/**
	 * @return the companyEmailId
	 */
	public String getCompanyEmailId() {
		return companyEmailId;
	}



	/**
	 * @param companyEmailId the companyEmailId to set
	 */
	public void setCompanyEmailId(String companyEmailId) {
		this.companyEmailId = companyEmailId;
	}



	/**
	 * @return the work_number
	 */
	public String getWork_number() {
		return work_number;
	}



	/**
	 * @param work_number the work_number to set
	 */
	public void setWork_number(String work_number) {
		this.work_number = work_number;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((workId == null) ? 0 : workId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Work other = (Work) obj;
		if (workId == null) {
			if (other.workId != null)
				return false;
		} else if (!workId.equals(other.workId))
			return false;
		return true;
	}
	
	
}
