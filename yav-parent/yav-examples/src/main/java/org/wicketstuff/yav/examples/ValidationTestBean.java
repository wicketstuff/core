package org.wicketstuff.yav.examples;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Zenika
 *
 */
public class ValidationTestBean implements Serializable {
	private static final long serialVersionUID = -8220082905294958691L;

	private Date typeDate1;
	private Date typeDate2;
	private int typeInt;
	private Float typeDecimal;
	private BigDecimal typeBigDecimal;

	private String maxLengthString;
	private String minLengthString;
	private String exactLengthString;
	private String lengthBetweenString;
	
	private String email;

	private Date dateOfBirth1;
	private Date dateOfBirth2;
	
	private Long rangeLong;

	public int getTypeInt() {
		return typeInt;
	}

	public void setTypeInt(int typeInt) {
		this.typeInt = typeInt;
	}

	public Float getTypeDecimal() {
		return typeDecimal;
	}

	public void setTypeDecimal(Float typeDecimal) {
		this.typeDecimal = typeDecimal;
	}

	public Date getTypeDate1() {
		return typeDate1;
	}

	public void setTypeDate1(Date typeDate1) {
		this.typeDate1 = typeDate1;
	}

	public Date getTypeDate2() {
		return typeDate2;
	}

	public void setTypeDate2(Date typeDate2) {
		this.typeDate2 = typeDate2;
	}

	public String getMaxLengthString() {
		return maxLengthString;
	}

	public void setMaxLengthString(String maxLengthString) {
		this.maxLengthString = maxLengthString;
	}

	public String getMinLengthString() {
		return minLengthString;
	}

	public void setMinLengthString(String minLengthString) {
		this.minLengthString = minLengthString;
	}

	public String getExactLengthString() {
		return exactLengthString;
	}

	public void setExactLengthString(String exactLengthString) {
		this.exactLengthString = exactLengthString;
	}

	public String getLengthBetweenString() {
		return lengthBetweenString;
	}

	public void setLengthBetweenString(String lengthBetweenString) {
		this.lengthBetweenString = lengthBetweenString;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDateOfBirth1() {
		return dateOfBirth1;
	}

	public void setDateOfBirth1(Date dateOfBirth1) {
		this.dateOfBirth1 = dateOfBirth1;
	}

	public Date getDateOfBirth2() {
		return dateOfBirth2;
	}

	public void setDateOfBirth2(Date dateOfBirth2) {
		this.dateOfBirth2 = dateOfBirth2;
	}

	public Long getRangeLong() {
		return rangeLong;
	}

	public void setRangeLong(Long rangeLong) {
		this.rangeLong = rangeLong;
	}

	public BigDecimal getTypeBigDecimal() {
		return typeBigDecimal;
	}

	public void setTypeBigDecimal(BigDecimal typeBigDecimal) {
		this.typeBigDecimal = typeBigDecimal;
	}

}
