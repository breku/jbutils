package pl.breku.backend.server.invoice.input;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.breku.backend.server.invoice.input.model.UserDetails;

import java.util.Properties;

/**
 * Created by brekol on 17.01.16.
 */
@Service
@Slf4j
public class UserDetailsFileReader extends AbstractFileReader {
	private static final String AGREEMENT_NUMBER_KEY = "agreementNumber";

	private static final String COMPANY_NAME_KEY = "companyName";

	private static final String ADDRESS_KEY = "address";

	private static final String NIP_KEY = "nip";

	private static final String PHONE_NUMBER_KEY = "phoneNumber";

	private static final String BANK_ACCOUNT_NUMBER_KEY = "bankAccountNumber";

	private static final String USER_DETAILS_FILE_NAME = "userDetails.txt";


	public UserDetails getUserDetails() {
		log.debug(">> called");
		final Properties properties = getFileAsProperties(USER_DETAILS_FILE_NAME);
		final String agreementNumber = properties.getProperty(AGREEMENT_NUMBER_KEY);
		final String companyName = properties.getProperty(COMPANY_NAME_KEY);
		final String address = properties.getProperty(ADDRESS_KEY);
		final String nip = properties.getProperty(NIP_KEY);
		final String phoneNumber = properties.getProperty(PHONE_NUMBER_KEY);
		final String bankAccountNumber = properties.getProperty(BANK_ACCOUNT_NUMBER_KEY);
		final UserDetails userDetails = new UserDetails(agreementNumber, companyName, address, nip, phoneNumber, bankAccountNumber);
		log.debug("<< finished with userDetails={}", userDetails);
		return userDetails;
	}
}
