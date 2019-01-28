package com.fredastone.commons.logging.layout;

import java.nio.charset.Charset;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;

import com.fredastone.commons.logging.bean.SecurityLogInfo;


@Plugin(name = "SecurityLogLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public class SecurityLogLayout  extends AbstractStringLayout{

	protected SecurityLogLayout(Charset aCharset) {
		super(aCharset);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toSerializable(LogEvent event) {
		// TODO Auto-generated method stub
		if(event.getMessage() == null || !(event.getMessage() instanceof SecurityLogInfo)) {
			LogManager.getRootLogger().error("Security Logger got bad logging event");
			return null;
		}
	
		return ((SecurityLogInfo)event.getMessage()).toString();
	}

	 @PluginFactory
	    public static SecurityLogLayout createLayout(
	                                            @PluginAttribute(value = "charset", defaultString = "UTF-8") Charset charset) {
	        return new SecurityLogLayout(charset);
	    }	
}
