package com.avitas.qa.utilities;

import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;

import com.avitas.qa.automationcore.account.Account;
import com.avitas.qa.automationcore.account.AccountImpl;
import com.avitas.qa.automationcore.mediatype.impl.MovieImpl;
import com.avitas.qa.pojo.AccountBean;

/**
 * @author Venkat
 * Base class to initialize / destroy web driver 
 */

public class TestBase {
	public static Logger log = Logger.getLogger(MovieImpl.class);

	public static AccountBean anAccountBean;
	public JsonParsers jsonParsers = new JsonParsers();

	@BeforeTest(alwaysRun = true)
	public void initialize() throws Exception {
		Account anAccount = new AccountImpl();
		anAccountBean = anAccount.fetAccountDetails();
		log.info("AccountBean Details: AccountID: " + anAccountBean.getAccountId() + ", SessionID: "+anAccountBean.getSessionId());
	}
	
	@AfterSuite
	public void tearDown() {
		anAccountBean = null;
	}
}
