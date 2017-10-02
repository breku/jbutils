package pl.breku.backend.server.invoice.output;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalDate;
import org.springframework.stereotype.Service;
import pl.breku.backend.server.invoice.input.FastInvoceException;
import pl.breku.backend.server.invoice.input.model.InvoiceDetails;
import pl.breku.backend.server.invoice.input.model.UserDetails;

import java.util.List;

/**
 * Created by brekol on 17.01.16.
 */
@Service
@Slf4j
public class PdfInvoiceService extends AbstractPdfService {


	private static final double VAT_FACTOR = 0.23;

	public static double round(double value, int places) {
		if (places < 0) throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public void createPdfs(List<InvoiceDetails> invoiceDetailsList, UserDetails userDetails) {
		for (final InvoiceDetails invoiceDetails : invoiceDetailsList) {
			createPdf(invoiceDetails, userDetails);
		}
	}

	@Override
	protected String getPdfFileNameType() {
		return "faktura";
	}

	private void createPdf(InvoiceDetails invoiceDetails, UserDetails userDetails) {

		Document document = new Document(PageSize.A4, 40, 40, 50, 50);
		saveDocumentAsPdf(document, userDetails, invoiceDetails);
		document.open();
		addPdfContent(invoiceDetails, userDetails, document);
		document.close();
	}

	private void addPdfContent(InvoiceDetails invoiceDetails, UserDetails userDetails, Document document) {
		try {
			addText(document, Element.ALIGN_LEFT, getHeader(invoiceDetails));
			createLineSeparator(document);
			document.add(createSellerAndBuyerTable(userDetails));
			createLineSeparator(document);
			document.add(createDatesAndBankAccountTable(userDetails));
			createLineSeparator(document);
			document.add(createPaymentTable(invoiceDetails));
			document.add(Chunk.NEWLINE);
			document.add(createSummary(invoiceDetails));
		} catch (DocumentException e) {
			log.error("Error during generatring pdf", e);
			throw new FastInvoceException(e.getMessage(), e);
		}
	}

	private Element createSummary(InvoiceDetails invoiceDetails) {
		final PdfPTable table = new PdfPTable(new float[]{2f, 12f, 5f, 5f, 5f, 5f, 5f, 5f,});
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.setWidthPercentage(100);

		final PdfPCell pdfCell = createPdfCellWithoutBorder("Razem do zapłaty: " + calculateTotalValue(invoiceDetails) + " PLN");
		pdfCell.setColspan(4);
		table.addCell(pdfCell);

		table.addCell(createPdfCellWithoutBorderWithCenterAlignment(invoiceDetails.getValue()));
		table.addCell(createPdfCellWithoutBorderWithCenterAlignment("23%"));
		table.addCell(createPdfCellWithoutBorderWithCenterAlignment(calculateValueWithVat(invoiceDetails.getValue())));
		table.addCell(createPdfCellWithoutBorderWithCenterAlignment(calculateTotalValue(invoiceDetails)));
		return table;
	}

	private Element createPaymentTable(InvoiceDetails invoiceDetails) {
		final PdfPTable table = new PdfPTable(new float[]{2f, 12f, 5f, 5f, 5f, 5f, 5f, 5f,});
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.setWidthPercentage(100);
		table.addCell(createPdfCell("Lp."));
		table.addCell(createPdfCell("Nazwa"));
		table.addCell(createPdfCell("Ilość"));
		table.addCell(createPdfCell("Cena netto"));
		table.addCell(createPdfCell("Wartość netto"));
		table.addCell(createPdfCell("Stawka VAT"));
		table.addCell(createPdfCell("Kwota VAT"));
		table.addCell(createPdfCell("Wartość brutto"));

		table.addCell(createPdfCell("1"));
		table.addCell(createPdfCell(invoiceDetails.getName()));
		table.addCell(createPdfCell(invoiceDetails.getNumberOfDays()));
		table.addCell(createPdfCell(invoiceDetails.getSalary()));
		table.addCell(createPdfCell(invoiceDetails.getValue()));
		table.addCell(createPdfCell("23%"));
		table.addCell(createPdfCell(calculateValueWithVat(invoiceDetails.getValue())));
		table.addCell(createPdfCell(calculateTotalValue(invoiceDetails)));
		return table;
	}

	private String calculateTotalValue(InvoiceDetails invoiceDetails) {
		final String vat = calculateValueWithVat(invoiceDetails.getValue());
		final String value = invoiceDetails.getValue();
		return String.valueOf(round(Double.parseDouble(vat) + Double.parseDouble(value), 2));
	}

	private String calculateValueWithVat(String nettoValue) {
		final double parsedInvoiceNetto = Double.parseDouble(nettoValue);
		final double result = parsedInvoiceNetto * VAT_FACTOR;
		return String.valueOf(round(result, 2));
	}

	private Element createDatesAndBankAccountTable(UserDetails userDetails) {
		final PdfPTable table = new PdfPTable(new float[]{3f, 7f});
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.setWidthPercentage(100);
		table.addCell(createPdfCellWithoutBorder("Data wystawienia:"));
		table.addCell(createPdfCellWithoutBorder(dateService.getLastDayOfPreviousMonth()));
		table.addCell(createPdfCellWithoutBorder("Data sprzedaży:"));
		table.addCell(createPdfCellWithoutBorder(dateService.getLastDayOfPreviousMonth()));
		table.addCell(createPdfCellWithoutBorder("Sposób płatności:"));
		table.addCell(createPdfCellWithoutBorder("Przelew"));
		table.addCell(createPdfCellWithoutBorder("Numer konta:"));
		table.addCell(createPdfCellWithoutBorder(userDetails.getBankAccountNumber()));
		return table;
	}

	private PdfPTable createSellerAndBuyerTable(UserDetails userDetails) {
		PdfPTable table = new PdfPTable(new float[]{7f, 10f, 5f, 5f, 12f});
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.setWidthPercentage(100);
		table.addCell(createPdfCellWithoutBorder("Sprzedawca:"));
		table.addCell(createPdfCellWithoutBorder(userDetails.getCompanyName()));
		table.addCell(createPdfCellWithoutBorder(""));
		table.addCell(createPdfCellWithoutBorder("Nabywca:"));
		table.addCell(createPdfCellWithoutBorder("Consdata Sp. z o.o."));
		table.completeRow();
		table.addCell(createPdfCellWithoutBorder("Adres:"));
		table.addCell(createPdfCellWithoutBorder(userDetails.getAddress()));
		table.addCell(createPdfCellWithoutBorder(""));
		table.addCell(createPdfCellWithoutBorder("Adres:"));
		table.addCell(createPdfCellWithoutBorder("ul. Bolesława Krysiewicza 9/14, 61-825 Poznań"));
		table.completeRow();
		table.addCell(createPdfCellWithoutBorder("NIP:"));
		table.addCell(createPdfCellWithoutBorder(userDetails.getNip()));
		table.addCell(createPdfCellWithoutBorder(""));
		table.addCell(createPdfCellWithoutBorder("NIP:"));
		table.addCell(createPdfCellWithoutBorder("7822261960"));
		table.completeRow();
		table.addCell(createPdfCellWithoutBorder("Telefon:"));
		table.addCell(createPdfCellWithoutBorder(userDetails.getPhoneNumber()));
		table.addCell(createPdfCellWithoutBorder(""));
		table.addCell(createPdfCellWithoutBorder("Telefon:"));
		table.addCell(createPdfCellWithoutBorder("+48 61 41 51 000"));
		table.completeRow();
		return table;
	}

	private PdfPCell createPdfCellWithoutBorderWithCenterAlignment(String text) {
		PdfPCell result = createPdfCellWithoutBorder(text);
		result.setHorizontalAlignment(Element.ALIGN_CENTER);
		return result;
	}

	private PdfPCell createPdfCellWithoutBorder(String text) {
		PdfPCell result = new PdfPCell(new Phrase(text, font));
		result.setBorder(Rectangle.NO_BORDER);
		return result;
	}

	private String getHeader(InvoiceDetails invoiceDetails) {
		final LocalDate today = new LocalDate();
		final LocalDate shiftedDate = today.minusMonths(1);
		return "Faktura nr " + invoiceDetails.getNumber() + "/" + shiftedDate.getMonthOfYear() + "/" + shiftedDate.getYear();
	}
}
