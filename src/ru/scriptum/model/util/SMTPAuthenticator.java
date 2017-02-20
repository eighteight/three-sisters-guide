/**
 * 
 */
package ru.scriptum.model.util;

import javax.mail.PasswordAuthentication;

class SMTPAuthenticator extends javax.mail.Authenticator
{
	
    private String username;
	private String password;

	public SMTPAuthenticator(String smtpUsername, String smtpPassword) {
		this.username = smtpUsername;
		this.password = smtpPassword;
	}

	public PasswordAuthentication getPasswordAuthentication()
    {

        return new PasswordAuthentication(username, password);
}
}