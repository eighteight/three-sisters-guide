/*
 * Created on Nov 16, 2005
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package ru.scriptum.model.domaindata;

import ru.scriptum.data.impl.TemplateElement;
import ru.scriptum.view.util.FacesUtils;

import com.db4o.types.Blob;

public class ImageElement extends TemplateElement {

	public Blob blob;

	public ImageElement() {
		super("imageTemplate");
	}

	public String getFileName() {
		String filename = blob.getFileName();
		if (filename.indexOf(".avi") > 0){
			filename = filename.replace(".avi",".mp4");
		}
		if (filename.indexOf(".mpg") > 0){
			filename = filename.replace(".mpg",".mp4");
		}
		if (filename.indexOf(".flv") > 0){
			filename = filename.replace(".flv",".mp4");
		}
		return filename;
	}

	public String getPath() {
		return FacesUtils.getContextPath() +"/blobs/"+getFileName();
	}
}
