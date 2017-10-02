package pl.breku.backend.server.invoice.input.model.web;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by brekol on 11.02.16.
 */
public class MailServerModel {

	private String mailContent;

	public MailServerModel() {
	}

	public MailServerModel(String mailContent) {
		this.mailContent = mailContent;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("mailContent", mailContent)
				.toString();
	}
}
