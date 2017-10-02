package pl.breku.backend.server.invoice;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.breku.backend.server.config.JbUtilsConfiguration;
import pl.breku.backend.server.invoice.input.InvoiceFileReader;
import pl.breku.backend.server.invoice.input.UserDetailsFileReader;
import pl.breku.backend.server.invoice.input.model.InvoiceDetails;
import pl.breku.backend.server.invoice.input.model.UserDetails;
import pl.breku.backend.server.invoice.input.model.web.MailServerModel;
import pl.breku.backend.server.invoice.output.PdfAttachmentService;
import pl.breku.backend.server.invoice.output.PdfInvoiceService;

import java.io.File;
import java.util.Collection;
import java.util.List;

/**
 * Created by brekol on 23.01.16.
 */
@Slf4j
@Service
public class InvoiceService {


	@Autowired
	private InvoiceFileReader invoiceFileReader;

	@Autowired
	private UserDetailsFileReader userDetailsFileReader;

	@Autowired
	private PdfInvoiceService pdfInvoiceService;

	@Autowired
	private PdfAttachmentService pdfAttachmentService;

	@Autowired
	private JbUtilsConfiguration jbUtilsConfiguration;

	public void createInvoicesAndAttachments(MailServerModel mailServerModel) {
		log.debug(">> called");
		log.trace(">> called with mailServerModel={}", mailServerModel);

		final UserDetails userDetails = userDetailsFileReader.getUserDetails();
		final List<InvoiceDetails> invoiceDetailsList = invoiceFileReader.getInvoiceDetailsList(mailServerModel.getMailContent());
		pdfInvoiceService.createPdfs(invoiceDetailsList, userDetails);
		pdfAttachmentService.createAttachments(invoiceDetailsList, userDetails);

		log.debug("<< Finished.");
	}

	public void clearInvoiceDirectory() {

		final File pdfSaveDirectory = new File(jbUtilsConfiguration.getPdfSaveDirectory());

		final Collection<File> pdfFiles = FileUtils.listFiles(pdfSaveDirectory, null, false);
		for (File pdfFile : pdfFiles) {
			final boolean deleteStatus = pdfFile.delete();
			log.debug("Pdf file={} delete status={}", pdfFile.getName(), deleteStatus);
		}
	}
}
