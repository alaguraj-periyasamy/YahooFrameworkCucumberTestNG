
package com.yahoo.mail;

import com.yahoo.constants.FrameworkConstants;

/**
 * Data for Sending email after execution
 */
public class EmailConfig {

    public static final String SERVER = "smtp.gmail.com";
    public static final String PORT = "587";

    public static final String FROM = "automationcentral301@gmail.com";
    public static final String PASSWORD = "**********";

    public static final String[] TO = {"testerworld301@gmail.com"};
    public static final String SUBJECT = FrameworkConstants.REPORT_TITLE;
}