package pl.breku.backend.server.invoice.input.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by brekol on 17.01.16.
 */
public class UserDetails {

	private String agreementNumber;

	private String companyName;

	private String address;

	private String nip;

	private String phoneNumber;

	private String bankAccountNumber;

	public UserDetails(String agreementNumber, String companyName, String address, String nip, String phoneNumber, String
			bankAccountNumber) {
		this.agreementNumber = agreementNumber;
		this.companyName = companyName;
		this.address = address;
		this.nip = nip;
		this.phoneNumber = phoneNumber;
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getBankAccountNumber() {
		return bankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNip() {
		return nip;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAgreementNumber() {
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("agreementNumber", "#hashed#")
				.append("companyName", "#hashed#")
				.append("address", "#hashed#")
				.append("nip", "#hashed#")
				.append("phoneNumber", "#hashed#")
				.append("bankAccountNumber", "#hashed#")
				.toString();
	}
}
