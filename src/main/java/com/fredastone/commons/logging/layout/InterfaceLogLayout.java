package com.fredastone.commons.logging.layout;

import java.nio.charset.Charset;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;

import com.fredastone.commons.logging.bean.InterfaceLogInfo;
import com.fredastone.commons.logging.bean.SecurityLogInfo;


@Plugin(name = "InterfaceLogLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public class InterfaceLogLayout  extends AbstractStringLayout{

	protected InterfaceLogLayout(Charset aCharset, byte[] header, byte[] footer) {
		super(aCharset, header, footer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toSerializable(LogEvent event) {
		// TODO Auto-generated method stub
		if(event.getMessage() == null || !(event.getMessage() instanceof SecurityLogInfo)) {
			LogManager.getRootLogger().error("Security Logger got bad logging event");
			return null;
		}
		return ((InterfaceLogInfo)event.getMessage()).toString();
	}

	

}
