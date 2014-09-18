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
package org.abhinav.alfresco.publishing.s3;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.alfresco.service.namespace.QName;

import com.abhinav.alfresco.publishing.utils.ConfigReader;
import com.abhinav.alfresco.publishing.utils.MimeTypesProvider;
import com.abhinav.alfresco.publishing.utils.S3RESTService;

/**
 * The S3PublishingHelper<br/>
 * Utility class that will be used by the s3 publishing channel.<br/>
 *  
 * @author abhinav kumar mishra
 */
public class S3PublishingHelper {
  
	/**
	 * Gets the s3 service.
	 *
	 * @param channelProperties the channel properties
	 * @return the s3 service
	 */
	public S3RESTService getS3Service(final Map<QName, Serializable> channelProperties){
		//Getting the secret keys from properties file.
		final Properties props = ConfigReader.getInstance().getKeys();
		//Amazon Web Services Access Key
		final String accessKey = props.getProperty(S3PublishingModel.ACCESSKEY);
		//Amazon Web Services Secret Key
		final String secretKey = props.getProperty(S3PublishingModel.SECRETKEY);
		//Amazon Web Services BucketName
		final String bucketName = (String) channelProperties.get(S3PublishingModel.PROP_BUCKET_NAME);
		return new S3RESTService(accessKey,secretKey,bucketName);
	}
	
	
	/**
	 * Gets the mime types to be supported.<br/>
	 * Gets the supported mimetypes form the alfresco-global.properties file, if not defined then return the default mimetypes.<br/>
	 * <b>To declare mimetypes use following comma seperated syntax: </b><br/>
	 * supportedMimeTypes=application/json, application/msword, application/octet-stream,... etc.
	 *
	 * @return the mime types to be supported
	 */
	public static Set<String> getMimeTypesToBeSupported() {
		return MimeTypesProvider.getInstance().getMimeTypes();
	} 
	
	
	/**
	 * Gets the bytes.
	 *
	 * @param fileObj the file obj
	 * @return the bytes
	 */
	public static byte[] getBytes(final File fileObj){
		final byte[] fileContent = new byte[(int) fileObj.length()];
		try (FileInputStream fileInputStream = new FileInputStream(fileObj)) {
			// convert file into array of bytes
			fileInputStream.read(fileContent);
		} catch (IOException ioex) {
			System.err.println("Error occured while converting file to bytes: "+ ioex);
		}
		return fileContent;
	}
}
