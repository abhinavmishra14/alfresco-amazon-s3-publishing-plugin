/*
 * Created By: Abhinav Kumar Mishra
 * Copyright &copy; 2013-2014. Abhinav Kumar Mishra. 
 * All rights reserved.
 */
package org.abhinav.alfresco.publishing.s3;

import org.alfresco.service.namespace.QName;

/**
 * The S3PublishingModel.<br/>
 * This class contains Constants which will be used by the s3 publishing channel.<br/>
 * @author Abhinav Kumar Mishra
 */
public class S3PublishingModel {

	/** The GLOBAL_PROPERTIESFILE. */
	public static final String GLOBAL_PROPERTIESFILE = "alfresco/module/s3-integration/alfresco-global.properties";

	/** The supported mime key. */
	public static final String SUPPORTD_MIME_KEY = "supportedMimeTypes";

	/** The Constant NAMESPACE. */
	public static final String NAMESPACE = "http://www.alfresco.org/model/publishing/s3/1.0";

	/** The Constant PREFIX. */
	public static final String PREFIX = "s3";
	
	/** The accesskey. */
	public static final String ACCESSKEY = "s3.accessKey";
	
	/** The secretkey. */
	public static final String SECRETKEY = "s3.secretKey";
	
	/** The bucket. */
	public static final String BUCKET = "s3.bucketName";

	/** The prop bucket name. */
	public static final QName PROP_BUCKET_NAME = QName.createQName(NAMESPACE, "bucket");

	/** The Constant TYPE_DELIVERY_CHANNEL. */
	public static final QName TYPE_DELIVERY_CHANNEL = QName.createQName(NAMESPACE, "DeliveryChannel");

	/** The Constant ASPECT_DELIVERY_CHANNEL. */
	public static final QName ASPECT_DELIVERY_CHANNEL = QName.createQName(NAMESPACE, "DeliveryChannelAspect");
}
