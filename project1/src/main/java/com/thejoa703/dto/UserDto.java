package com.thejoa703.dto;

import java.time.LocalDateTime;

public class UserDto {
    private int APPUSERID;
    private String PASSWORD;
    private String NICKNAME;
    private String EMAIL;
    private String MOBILE;
    private LocalDateTime JOINDATE;
	public UserDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public UserDto(int aPPUSERID, String pASSWORD, String nICKNAME, String eMAIL, String mOBILE,
			LocalDateTime jOINDATE) {
		super();
		APPUSERID = aPPUSERID;
		PASSWORD = pASSWORD;
		NICKNAME = nICKNAME;
		EMAIL = eMAIL;
		MOBILE = mOBILE;
		JOINDATE = jOINDATE;
	}
	@Override
	public String toString() {
		return "UserDto [APPUSERID=" + APPUSERID + ", PASSWORD=" + PASSWORD + ", NICKNAME=" + NICKNAME + ", EMAIL="
				+ EMAIL + ", MOBILE=" + MOBILE + ", JOINDATE=" + JOINDATE + "]";
	}
	public int getAPPUSERID() {
		return APPUSERID;
	}
	public void setAPPUSERID(int aPPUSERID) {
		APPUSERID = aPPUSERID;
	}
	public String getPASSWORD() {
		return PASSWORD;
	}
	public void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}
	public String getNICKNAME() {
		return NICKNAME;
	}
	public void setNICKNAME(String nICKNAME) {
		NICKNAME = nICKNAME;
	}
	public String getEMAIL() {
		return EMAIL;
	}
	public void setEMAIL(String eMAIL) {
		EMAIL = eMAIL;
	}
	public String getMOBILE() {
		return MOBILE;
	}
	public void setMOBILE(String mOBILE) {
		MOBILE = mOBILE;
	}
	public LocalDateTime getJOINDATE() {
		return JOINDATE;
	}
	public void setJOINDATE(LocalDateTime jOINDATE) {
		JOINDATE = jOINDATE;
	}

	
	
}
