package pl.breku.backend.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import pl.breku.backend.server.invoice.InvoiceService;
import pl.breku.backend.server.invoice.input.model.web.MailServerModel;
import pl.breku.backend.server.invoice.output.ZipFileService;

/**
 * Created by brekol on 11.02.16.
 */
@Controller
@Slf4j
public class InvoiceController {


    private final InvoiceService invoiceService;
    private final ZipFileService zipFileService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, ZipFileService zipFileService) {
        this.invoiceService = invoiceService;
        this.zipFileService = zipFileService;
    }

    @RequestMapping(value = "/invoice", method = RequestMethod.GET)
    public ModelAndView init(Model model) {
        MailServerModel mailServerModel = new MailServerModel();
        return new ModelAndView("invoice/invoice", "mailServerModel", mailServerModel);
    }

    @RequestMapping(value = "/invoice/submit", method = RequestMethod.POST, produces = "application/zip")
    public HttpEntity<byte[]> submitForPdfs(@ModelAttribute("mailServerModel") MailServerModel mailServerModel) {
        log.info("> Creating pdfs");
        invoiceService.clearInvoiceDirectory();
        invoiceService.createInvoicesAndAttachments(mailServerModel);
        final byte[] zips = zipFileService.getZips();

        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "zip"));
        header.set("Content-Disposition",
                "attachment; filename=invoices.zip");
        header.setContentLength(zips.length);

        final HttpEntity<byte[]> result = new HttpEntity<>(zips, header);
        log.info("< Creating pds finished with result={}", result);
        return result;
    }
}
