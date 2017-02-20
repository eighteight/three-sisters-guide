package ru.scriptum.view.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.myfaces.custom.fileupload.UploadedFile;

import ru.scriptum.data.impl.TemplateElement;
import ru.scriptum.model.data.IElement;
import ru.scriptum.model.data.ITemplateElement;
import ru.scriptum.model.domaindata.ImageElement;
import ru.scriptum.model.domaindata.User;
import ru.scriptum.model.exception.BeanException;
import ru.scriptum.model.properties.IProperty;
import ru.scriptum.view.servicelocator.IServiceLocator;
import ru.scriptum.view.util.FacesUtils;

import com.db4o.ext.Status;
import com.db4o.types.Blob;

public class UploadBean extends TemplateBean {
	private static Map<String, String> mimeTypeExtensionMap = new HashMap<String, String>();
	
	static {
		mimeTypeExtensionMap.put("avi","video/x-msvideo");
		mimeTypeExtensionMap.put("mpg","video/mpeg");
		mimeTypeExtensionMap.put("flv","video/x-flv");
	}
	// Returns the contents of the file in a byte array.
	private static byte[] getBytesFromFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		// You cannot create an array using a long type.
		// It needs to be an int type.
		// Before converting to an int type, check
		// to ensure that file is not larger than Integer.MAX_VALUE.
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}
	private UploadedFile upFile;
	private String mimeType;
	private long size;

	public UploadBean(){
		super();
	}

	private String findMimeType(String ext) {
		return mimeTypeExtensionMap .get(ext);
	}

	public String getContent() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		try {
			OutputStream responseStream = ((HttpServletResponse) facesContext
					.getExternalContext().getResponse()).getOutputStream();
			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();
			response.setContentType(getMimeType());
			response.setHeader("Content-Disposition", "attachment;filename=\""
					+ getFileName() + "\"");
			File file = new File(FacesUtils.getApplicationRoot() + getUrl());
			response.setContentLength((int) file.length());
			responseStream.write(getBytesFromFile(file));
			response.flushBuffer();
		} catch (IOException exception) {
			exception.printStackTrace();
		}

		facesContext.responseComplete();

		return "downloadContentData";
	}

	private IElement getElement() {
		Long id = getId();
		if (element == null && id!=null){
			element = (IElement) getServiceLocator().getBeanService().getPersistable(id);
		}
		return element;
	}

	private String getFileName() {
		return (element == null ? "" : ((TemplateElement) element)
				.getProperties()[1].getValue());
	}

	public String getMimeType() {
		if (mimeType != null) return mimeType;
//		return "video/x-msvideo";
		element = getElement();
		return (element == null) ? "" : getProperties()[2].getValue();
	}

	public IProperty[] getProperties() {
		if (element != null)
			return ((TemplateElement) element).getProperties();
		return super.getProperties();
	}

	public UploadedFile getUpFile() {
		return upFile;
	}

	public String getUrl() {
		return element == null ? "" : "/blobs/" + getFileName();
	}

	private void readBlobLocal(Blob blob, File file) {
		try {
			blob.readLocal(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		// reading blobs runs in a dedicated thread
		// you could update a progress window in the loop

		double status = blob.getStatus();
		while (status > Status.COMPLETED) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			status = blob.getStatus();
		}
	}

	public void setMimeType(String accept) {
		this.mimeType = accept;
	}

	public void setServiceLocator(IServiceLocator newServiceLocator) {
		super.setServiceLocator(newServiceLocator);
	}

	public void setUpFile(UploadedFile upFile) {
		this.upFile = upFile;
		this.size = upFile.getSize();
		String fileName = upFile.getName();
		int li = fileName.lastIndexOf(".");
		if (li == -1) return;
		String ext = fileName.substring(li+1);
		String mime = findMimeType(ext);
		this.mimeType = mime;
	}

	public String upload() throws IOException {
		ITemplateElement element = new ImageElement();
		try {
			BeanUtils.copyProperties(element, this);
			element.initName();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			e1.printStackTrace();
		}
		try {
			setParent(element);
			User user = FacesUtils.getLoggedInUser();
			element.setUser(user);
			serviceLocator.getBeanService().save(element);
		} catch (BeanException e) {
			e.printStackTrace();
		}
		File tempFile = writeTempFile(upFile);
		readBlobLocal(((ImageElement) element).blob, tempFile);

		String fileName = ((ImageElement) element).getFileName();

		element.getProperties()[1].setValue(fileName);
		element.getProperties()[2].setValue(mimeType);
		element.getProperties()[3].setValue(size+"");

		setParent(element);
		this.id = element.getId();
		this.element = element;
		try {
			serviceLocator.getBeanService().save(element);
		} catch (BeanException e) {
			e.printStackTrace();
		}
		return "ok";
	}
	
	private File writeTempFile(UploadedFile upFile) {
		File file = new File(upFile.getName());
		try {
			FileOutputStream os = new FileOutputStream(file);
			os.write(upFile.getBytes());
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}
}
