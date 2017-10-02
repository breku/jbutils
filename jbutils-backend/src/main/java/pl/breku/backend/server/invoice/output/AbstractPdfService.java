package pl.breku.backend.server.invoice.output;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import pl.breku.backend.server.config.JbUtilsConfiguration;
import pl.breku.backend.server.invoice.input.FastInvoceException;
import pl.breku.backend.server.invoice.input.model.InvoiceDetails;
import pl.breku.backend.server.invoice.input.model.UserDetails;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by brekol on 21.01.16.
 */

@Slf4j
public abstract class AbstractPdfService {

	protected Font font;

	protected Font boldFont;

	@Autowired
	protected DateService dateService;

	@Autowired
	private JbUtilsConfiguration jbUtilsConfiguration;

	AbstractPdfService() {
		initializeFont();
	}

	protected void saveDocumentAsPdf(Document document, UserDetails userDetails, InvoiceDetails invoiceDetails) {
		try {
			PdfWriter.getInstance(document, new FileOutputStream(new File(jbUtilsConfiguration.getPdfSaveDirectory() + getPdfFileName
					(userDetails, invoiceDetails))));
		} catch (DocumentException | FileNotFoundException e) {
			log.error("Error during getting instance of pdf writer (saving document)", e);
			throw new FastInvoceException(e.getMessage(), e);
		}
	}

	protected void addText(Document document, int aligment, String text) throws DocumentException {
		final Paragraph paragraph = new Paragraph(text, font);
		paragraph.setAlignment(aligment);
		document.add(paragraph);
	}

	protected void createLineSeparator(Document document) throws DocumentException {
		document.add(new Chunk(new LineSeparator(0.2f, 100f, BaseColor.BLACK, Element.ALIGN_LEFT, 0)));
	}

	protected PdfPCell createPdfCell(String text) {
		PdfPCell result = new PdfPCell(new Phrase(text, font));
		result.setHorizontalAlignment(Element.ALIGN_CENTER);
		result.setVerticalAlignment(Element.ALIGN_MIDDLE);
		return result;
	}

	protected String getPdfFileName(UserDetails userDetails, InvoiceDetails invoiceDetails) {
		log.trace(">> called with invoiceDetails={} userDetails={}", invoiceDetails, userDetails);
		final String datePrefix = dateService.getPreviousMonthsYear() + "-" + dateService.getPreviousMonth();
		final String filePrefix = Iterables.getLast(Splitter.on("/").split(userDetails.getAgreementNumber()));
		final String result = Joiner.on("-").join(datePrefix, filePrefix, getPdfFileNameType(), invoiceDetails.getNumber());
		final String resultWithFileExtension = result + ".pdf";
		log.trace("<< Finished with resultWithFileExtension={}", resultWithFileExtension);
		return resultWithFileExtension;
	}

	protected abstract String getPdfFileNameType();

	private void initializeFont() {
		try {
			final BaseFont bf = BaseFont.createFont("invoice/OpenSans-Light.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			font = new Font(bf, 12, Font.NORMAL);
			boldFont = new Font(bf, 12, Font.BOLD);
		} catch (DocumentException | IOException e) {
			log.error("Error during font loading", e);
			throw new FastInvoceException(e.getMessage(), e);
		}
	}
}
