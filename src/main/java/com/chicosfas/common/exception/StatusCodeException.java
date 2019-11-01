/*<CHICOSCOPYRIGHT>
*
* Copyright (C) 2006-2019 Chico's.
* All Rights Reserved.  No use, copying or distribution
* of this work may be made except in accordance with a
* valid license agreement from Chico's.  This notice
* must be included on all copies, modifications and
* derivatives of this work.
*
* Chico's MAKES NO REPRESENTATIONS OR WARRANTIES
* ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
* WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
* PURPOSE, OR NON-INFRINGEMENT. MY COMPANY SHALL NOT BE
* LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT
* OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
* DERIVATIVES.
*
*</CHICOSCOPYRIGHT>*/

package com.chicosfas.common.exception;

import org.springframework.http.HttpStatus;

import lombok.Data;

@Data
public class StatusCodeException extends RuntimeException {
	private final HttpStatus status;
	private final String code;
	private final String message;

	public StatusCodeException(HttpStatus status, String message) {
		this.status = status;
		this.code = status.getReasonPhrase();
		this.message = message;
	}

	public StatusCodeException(Integer status, String code, String message) {
		this.status = HttpStatus.valueOf(status);
		this.code = code;
		this.message = message;
	}
}
