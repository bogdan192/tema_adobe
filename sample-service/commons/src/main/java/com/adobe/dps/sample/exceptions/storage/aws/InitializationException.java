/* ADOBE CONFIDENTIAL
 * Copyright 2013 Adobe Systems Incorporated. All rights reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 */

package com.adobe.dps.sample.exceptions.storage.aws;

public class InitializationException extends RuntimeException {

	private static final long serialVersionUID = -7815447515466122870L;

	public InitializationException() {
		super();
	}

	public InitializationException(String message) {
		super(message);
	}

	public InitializationException(Exception exp) {
		super(exp);
	}
}
