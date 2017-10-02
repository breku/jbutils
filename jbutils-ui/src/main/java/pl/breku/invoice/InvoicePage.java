package pl.breku.invoice;

import com.vaadin.server.FileDownloader;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import pl.breku.backend.server.invoice.InvoiceService;
import pl.breku.backend.server.invoice.input.model.web.MailServerModel;
import pl.breku.backend.server.invoice.output.ZipFileService;
import pl.breku.page.AbstractPage;

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
			log.info("> Creating pdfs");
			MailServerModel mailServerModel = new MailServerModel(textArea.getValue());
			invoiceService.clearInvoiceDirectory();
			invoiceService.createInvoicesAndAttachments(mailServerModel);
			final byte[] zips = zipFileService.getZips();

		});
		verticalLayout.addComponent(button);
		verticalLayout.setSpacing(true);
		return verticalLayout;
	}
}
