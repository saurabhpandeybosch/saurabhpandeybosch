/**
 *
 */
package com.ceea.core.utils;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpHeaders;


/**
 * @author hybris2go
 *
 */
public class CeeaRestUtil
{
	/**
	 * This method is used for basic authentication
	 *
	 * @return
	 */
	public static HttpHeaders createHeaders(final String basicAuthUser, final String basicAuthPwd)
	{

		return new HttpHeaders()
		{
			{
				final String auth = basicAuthUser + ":" + basicAuthPwd;
				final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
				final String authHeader = "Basic " + new String(encodedAuth);
				set("Authorization", authHeader);
			}
		};
	}

}
