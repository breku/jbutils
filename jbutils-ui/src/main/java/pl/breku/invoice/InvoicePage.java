package pl.breku.invoice;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pl.breku.backend.server.invoice.InvoiceService;
import pl.breku.backend.server.invoice.input.model.web.MailServerModel;
import pl.breku.backend.server.invoice.output.ZipFileService;
import pl.breku.page.AbstractPage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by breku on 13.09.17.
 */
@Slf4j
@SpringView(name = InvoicePage.VIEW_NAME)
public class InvoicePage extends AbstractPage {

	public static final String VIEW_NAME = "invoice";

	private static final long serialVersionUID = -5867976681717716832L;

	private final InvoiceService invoiceService;

	private final ZipFileService zipFileService;

	@Autowired
	public InvoicePage(InvoiceService invoiceService, ZipFileService zipFileService) {
		this.invoiceService = invoiceService;
		this.zipFileService = zipFileService;
	}

	private TextArea textArea;

	private Button button;

	@Override
	protected void createComponent(VerticalLayout wrapper) {
		wrapper.addComponent(createDashboardImage());
		wrapper.addComponent(new Label("This is Invoice page"));
		wrapper.addComponent(createTextArea());
		wrapper.addComponent(createButton());
	}

	private VerticalLayout createTextArea() {
		VerticalLayout verticalLayout = new VerticalLayout();
		textArea = new TextArea("Mail content");
		textArea.setSizeFull();
		verticalLayout.addComponent(textArea);
		verticalLayout.setSpacing(true);
		return verticalLayout;
	}



	private VerticalLayout createButton() {
		VerticalLayout verticalLayout = new VerticalLayout();
		button = new Button("Generate invoices");
		button.addClickListener((Button.ClickListener) event -> {
			if(StringUtils.isNotBlank(textArea.getValue())){
				log.info("> Creating pdfs");
				MailServerModel mailServerModel = new MailServerModel(textArea.getValue());
				invoiceService.clearInvoiceDirectory();
				invoiceService.createInvoicesAndAttachments(mailServerModel);
				log.info("< Creating pdfs finished.");
			}else {
				log.info("Skip creating pdfs due to empty textArea");
			}


		});
		FileDownloader fileDownloader = new FileDownloader(getPDFStream());
		fileDownloader.extend(button);

		verticalLayout.addComponent(button);
		verticalLayout.setSpacing(true);
		return verticalLayout;
	}

	private StreamResource getPDFStream() {
		StreamResource.StreamSource source = (StreamResource.StreamSource) () -> new ByteArrayInputStream(zipFileService.getZips());
		return new StreamResource ( source, "invoices.zip");
	}
}
