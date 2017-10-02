package pl.breku.backend.server.invoice.output;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.breku.backend.server.config.JbUtilsConfiguration;
import pl.breku.backend.server.invoice.input.FastInvoceException;

import java.io.*;
import java.util.Collection;

/**
 * Created by brekol on 16.02.16.
 */
@Service
@Slf4j
public class ZipFileService {


	private final JbUtilsConfiguration jbUtilsConfiguration;

	@Autowired
	public ZipFileService(JbUtilsConfiguration jbUtilsConfiguration) {
		this.jbUtilsConfiguration = jbUtilsConfiguration;
	}

	public byte[] getZips() {
		final String zipFileName = "invoices.zip";
		createZip(zipFileName);
		return getBytesFormCreatedZipFile(zipFileName);
	}

	private byte[] getBytesFormCreatedZipFile(String zipFileName) {
		try {
			InputStream inputStream = new FileInputStream(jbUtilsConfiguration.getPdfSaveDirectory() + "/" + zipFileName);
			return IOUtils.toByteArray(inputStream);
		} catch (IOException e) {
			log.error("Error during zipping pdf files", e);
			throw new FastInvoceException(e.getMessage(), e);
		}
	}

	private void createZip(String zipFileName) {
		try {

			final File source = new File(jbUtilsConfiguration.getPdfSaveDirectory());
			final File destination = new File(jbUtilsConfiguration.getPdfSaveDirectory() + "/" + zipFileName);

			final OutputStream archiveStream = new FileOutputStream(destination);
			final ArchiveOutputStream archive = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP,
					archiveStream);

			final Collection<File> files = FileUtils.listFiles(source, new String[]{"pdf"}, false);

			for (File file : files) {
				String entryName = getEntryName(source, file);
				ZipArchiveEntry entry = new ZipArchiveEntry(entryName);
				archive.putArchiveEntry(entry);

				BufferedInputStream input = new BufferedInputStream(new FileInputStream(file));

				IOUtils.copy(input, archive);
				input.close();
				archive.closeArchiveEntry();
			}
			archive.close();
		} catch (IOException | ArchiveException e) {
			log.error("Error during zipping pdf files", e);
			throw new FastInvoceException(e.getMessage(), e);
		}
	}

	private String getEntryName(File source, File file) throws IOException {
		int index = source.getAbsolutePath().length() + 1;
		String path = file.getCanonicalPath();
		return path.substring(index);
	}
}
