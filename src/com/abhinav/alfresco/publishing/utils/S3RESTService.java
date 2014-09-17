/*
 * Created By: Abhinav Kumar Mishra
 * Copyright &copy; 2013-2014. Abhinav Kumar Mishra. 
 * All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.abhinav.alfresco.publishing.utils;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import org.abhinav.alfresco.publishing.s3.S3PublishingModel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jets3t.service.ServiceException;
import org.jets3t.service.StorageService;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.StorageBucket;
import org.jets3t.service.model.StorageObject;
import org.jets3t.service.security.AWSCredentials;

/**
 * The Class S3RESTService.<br/>
 * This class can be used to store and delete contents from the s3 bucket via
 * REST Interface.
 *
 * @author Abhinav kumar mishra
 */
public class S3RESTService {

	/** The access key. */
	private final String accessKey;

	/** The secret key. */
	private final String secretKey;

	/** The bucket name. */
	private final String bucketName;

	/** The s3Service. */
	private StorageService s3Service;

	/** The bucket. */
	private StorageBucket bucket;

	/** The Constant logger. */
	private static final Log LOG = LogFactory.getLog(S3RESTService.class);

	
	/**
	 * Instantiates a new s3 rest service.
	 */
	public S3RESTService() {
		// Getting the secret keys from properties file.
		final Properties props = ConfigReader.getInstance().getKeys();
		// Amazon Web Services Access Key
		this.accessKey = props.getProperty(S3PublishingModel.ACCESSKEY);
		// Amazon Web Services Secret Key
		this.secretKey = props.getProperty(S3PublishingModel.SECRETKEY);
		// Amazon Web Services BucketName
		this.bucketName = props.getProperty(S3PublishingModel.BUCKET);

		if (LOG.isDebugEnabled()) {
			LOG.debug("S3RESTService Initializing: accessKey=" + accessKey
					+ " secretKey=" + secretKey + " bucketName=" + bucketName);
		}

		// System.out.println("S3RESTService Initializing: accessKey="+accessKey+ " secretKey="+secretKey+" bucketName="+bucketName);

		// Instantiate S3 Service and create necessary bucket.
		try {
			s3Service = new RestS3Service(new AWSCredentials(accessKey,secretKey));
			bucket = s3Service.getOrCreateBucket(bucketName);
			if (LOG.isInfoEnabled()) {
				LOG.info("S3RESTService connected to : bucket: " + bucketName);
			}
			if (LOG.isInfoEnabled()) {
				LOG.info("S3RESTService Initialization Complete");
			}

			// System.out.println("S3RESTService Initialization Complete");
		} catch (ServiceException s3ServExcp) {
			if (LOG.isErrorEnabled()) {
				LOG.error("S3RESTService Initialization Error: " + s3ServExcp);
			}
		}
	}


	/**
	 * Instantiates a new s3 rest service.
	 *
	 * @param accessKey the access key
	 * @param secretKey the secret key
	 * @param bucketName the bucket name
	 */
	public S3RESTService(final String accessKey, final String secretKey,
			final String bucketName) {
		// Amazon Web Services Access Key
		this.accessKey = accessKey;
		// Amazon Web Services Secret Key
		this.secretKey = secretKey;
		// Amazon Web Services BucketName
		this.bucketName = bucketName;

		if (LOG.isDebugEnabled()) {
			LOG.debug("S3RESTService Initializing: accessKey=" + accessKey
					+ " secretKey=" + secretKey + " bucketName=" + bucketName);
		}

		// System.out.println("S3RESTService Initializing: accessKey="+accessKey+" secretKey="+secretKey+" bucketName="+bucketName);

		// Instantiate S3 Service and create necessary bucket.
		try {
			s3Service = new RestS3Service(new AWSCredentials(accessKey,
					secretKey));
			if (LOG.isInfoEnabled()) {
				LOG.info("S3RESTService connected to : bucket: " + bucketName);
			}
			bucket = s3Service.getOrCreateBucket(bucketName);
			if (LOG.isInfoEnabled()) {
				LOG.info("S3RESTService Initialization Complete");
			}

			// System.out.println("S3RESTService Initialization Complete");
		} catch (ServiceException s3ServExcp) {
			if (LOG.isErrorEnabled()) {
				LOG.error("S3RESTService Initialization Error: " + s3ServExcp);
			}
		}
	}


	/**
	 * Put object.
	 *
	 * @param fileName the file name
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServiceException the service exception
	 */
	public void putObject(final String fileName)
			throws NoSuchAlgorithmException, IOException, ServiceException {
		s3Service.putObject(bucketName, new StorageObject(new File(fileName)));
		LOG.info("File: " + fileName + " published successfully to s3 in: "
				+ bucketName);
	}


	/**
	 * Put object.
	 *
	 * @param fileName the file name
	 * @param content the content
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServiceException the service exception
	 */
	public void putObject(final String fileName, final byte[] content)
			throws NoSuchAlgorithmException, IOException, ServiceException {
		s3Service.putObject(bucketName, new StorageObject(fileName, content));
		LOG.info("File: " + fileName + " published successfully to s3 in: "
				+ bucketName);
	}

	/**
	 * Put object.
	 *
	 * @param fileObj the file obj
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServiceException the service exception
	 */
	public void putObject(final File fileObj) throws NoSuchAlgorithmException,
			IOException, ServiceException {
		s3Service.putObject(bucketName, new StorageObject(fileObj));
		LOG.info("File: " + fileObj.getName()+ " published successfully to s3 in: " + bucketName);
	}


	/**
	 * Delete object.
	 *
	 * @param fileName the file name
	 * @throws NoSuchAlgorithmException the no such algorithm exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ServiceException the service exception
	 */
	public void deleteObject(final String fileName)
			throws NoSuchAlgorithmException, IOException, ServiceException {
		s3Service.deleteObject(bucketName, fileName);
		LOG.info("File: " + fileName + " unpublished successfully from s3");
	}


	/**
	 * Shut down s3 service.
	 *
	 * @throws ServiceException the service exception
	 */
	public void shutDownS3Service() throws ServiceException {
		s3Service.shutdown();
	}

	/**
	 * Gets the bucket name.
	 *
	 * @return the bucket name
	 */
	public String getBucketName() {
		return bucketName;
	}

	/**
	 * Gets the s3 service.
	 *
	 * @return the s3 service
	 */
	public StorageService getS3Service() {
		return s3Service;
	}

	/**
	 * Gets the bucket.
	 *
	 * @return the bucket
	 */
	public StorageBucket getBucket() {
		return bucket;
	}
}