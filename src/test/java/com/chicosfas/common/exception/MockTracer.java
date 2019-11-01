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

import java.util.Map;
import java.util.Map.Entry;

import io.opentracing.Scope;
import io.opentracing.ScopeManager;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.tag.Tag;

public class MockTracer implements Tracer {

	@Override
	public ScopeManager scopeManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Span activeSpan() {
		return new Span() {

			@Override
			public SpanContext context() {
				return new SpanContext() {

					@Override
					public String toTraceId() {
						return "FOOBAR";
					}

					@Override
					public String toSpanId() {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public Iterable<Entry<String, String>> baggageItems() {
						// TODO Auto-generated method stub
						return null;
					}

				};
			}

			@Override
			public Span setTag(String key, String value) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Span setTag(String key, boolean value) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Span setTag(String key, Number value) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> Span setTag(Tag<T> tag, T value) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Span log(Map<String, ?> fields) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Span log(long timestampMicroseconds, Map<String, ?> fields) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Span log(String event) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Span log(long timestampMicroseconds, String event) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Span setBaggageItem(String key, String value) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getBaggageItem(String key) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Span setOperationName(String operationName) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void finish() {
				// TODO Auto-generated method stub

			}

			@Override
			public void finish(long finishMicros) {
				// TODO Auto-generated method stub

			}

		};
	}

	@Override
	public Scope activateSpan(Span span) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SpanBuilder buildSpan(String operationName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <C> void inject(SpanContext spanContext, Format<C> format, C carrier) {
		// TODO Auto-generated method stub

	}

	@Override
	public <C> SpanContext extract(Format<C> format, C carrier) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
