package com.fredastone.commons.logging.bean;

import java.util.Date;

import org.apache.logging.log4j.message.Message;

public abstract class AbstractLogInfo  implements Message{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7151989874391165427L;
	public  static final char PIPE = '|';
	
	
	private static final ThreadLocal<Date> DATE = new ThreadLocal<Date>() {
		@Override
		protected Date initialValue() {
			return new Date();
		}
		
	};
	

	protected String getLogTime() {
		return DATE.get().toString();
	}
	

}
