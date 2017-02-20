/*
 * Scriptum Project
 */
package ru.scriptum.model.domaindata;

import java.util.Date;

import ru.scriptum.data.impl.Element;

/**
 * User business object.
 * 
 * @author <a href="mailto:dev@scriptum.ru">Developer</a>
 */
public class User extends Element {

	private String email;

	private String locale;

	private String password;

	private Date regDate;

	private String signature;

	private String status;

	private String username;

	/**
	 * Default constructor.
	 */
	public User() {
	}

	public boolean equals(Object thatObject) {
		if (thatObject instanceof User) {
			if (this.name.equals(((User) thatObject).getName()))
				return true;
		}
		return false;
	}

	public String getEmail() {
		return email;
	}

	public String getLocale() {
		return locale;
	}

	public String getPassword() {
		return this.password;
	}

	public Date getRegDate() {
		return regDate;
	}

	public String getSignature() {
		return this.signature;
	}

	public String getUsername() {
		return this.username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLocale(String userLocale) {
		this.locale = userLocale;
	}

	public void setPassword(String newPassword) {
		this.password = newPassword;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public void setSignature(String sig) {
		this.signature = sig;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUsername(String newUsername) {
		this.username = newUsername;
	}
}
