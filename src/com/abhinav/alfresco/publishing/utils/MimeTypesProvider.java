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

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import org.abhinav.alfresco.publishing.s3.S3PublishingModel;
import org.alfresco.repo.content.MimetypeMap;
import org.alfresco.util.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class MimeTypesProvider.<br/>
 * Singleton class will provide the supported mime types mapped in
 * alfresco-global.properties file.<br/>
 * 
 * @author Abhinav kumar mishra
 */
public final class MimeTypesProvider {

	/** The Constant LOG. */
	private final static Log LOG = LogFactory.getLog(MimeTypesProvider.class);

	/** The Constant supportedMimeTypes. */
	private final Set<String> supportedMimeTypes = new HashSet<String>();

	/** The Constant instance. */
	private static final MimeTypesProvider INSTANCE = new MimeTypesProvider();

	/**
	 * Instantiates a new MIME types provider.
	 */
	private MimeTypesProvider() {
		super();
		init();
	}

	/**
	 * Gets the single instance of MimeTypesProvider.
	 *
	 * @return single instance of MimeTypesProvider
	 */
	public static MimeTypesProvider getInstance() {
		return INSTANCE;
	}

	/**
	 * Loads the mime types from the alfresco-global.properties.
	 */
	private void init() {
		final Properties props = new Properties();
		try (InputStream inStream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(S3PublishingModel.GLOBAL_PROPERTIESFILE)) {
			props.load(inStream);
		} catch (IOException ioex) {
			if (LOG.isErrorEnabled()) {
				LOG.error("Exception getting the mimetypes from alfresco-global.properties: ",ioex);
			}
		}
		// Get the supported mimetypes from alfresco-global.properties file
		if (props.getProperty(S3PublishingModel.SUPPORTD_MIME_KEY) != null) {
			final StringTokenizer tokens = new StringTokenizer(
					props.getProperty(S3PublishingModel.SUPPORTD_MIME_KEY), ",");
			while (tokens.hasMoreTokens()) {
				supportedMimeTypes.add(tokens.nextToken().trim());
			}
			LOG.info("SupportedMimeTypes by s3 publishing channel: "+ supportedMimeTypes);
		}
	}

	/**
	 * Gets the mime types<br/>
	 * Gets the supported mimetypes form the alfresco-global.properties file, if
	 * not defined then return the default mimetypes.<br/>
	 * <b>To declare mimetypes use following comma seperated syntax: </b><br/>
	 * supportedMimeTypes=application/json, application/msword,
	 * application/octet-stream,... etc.
	 *
	 * @return the mime types to be supported
	 */
	public Set<String> getMimeTypes() {
		if (supportedMimeTypes.isEmpty()) {
			// If mimetypes are not defined in properties file then return the
			// default supported mimetypes
			return CollectionUtils.unmodifiableSet(MimetypeMap.MIMETYPE_XML,
					MimetypeMap.MIMETYPE_XHTML, MimetypeMap.MIMETYPE_JSON,
					MimetypeMap.MIMETYPE_PDF, MimetypeMap.MIMETYPE_WORD,
					MimetypeMap.MIMETYPE_EXCEL,MimetypeMap.MIMETYPE_TEXT_PLAIN,
					MimetypeMap.MIMETYPE_PPT,MimetypeMap.MIMETYPE_HTML,
					MimetypeMap.MIMETYPE_OPENXML_SPREADSHEET,
					MimetypeMap.MIMETYPE_OPENXML_PRESENTATION,
					MimetypeMap.MIMETYPE_OPENXML_WORDPROCESSING,
					MimetypeMap.MIMETYPE_IMAGE_GIF,MimetypeMap.MIMETYPE_IMAGE_JPEG,
					MimetypeMap.MIMETYPE_IMAGE_PNG);
		} else {
			// Get the supported mimetypes from alfreco-global.properties file
			return CollectionUtils.unmodifiableSet(supportedMimeTypes);
		}
	}
}
