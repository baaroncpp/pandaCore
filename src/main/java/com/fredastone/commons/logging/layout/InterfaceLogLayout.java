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

import com.fredastone.commons.logging.bean.InterfaceLogInfo;


@Plugin(name = "InterfaceLogLayout", 
	category = Node.CATEGORY, 
	elementType = Layout.ELEMENT_TYPE, 
	printObject = true)
public class InterfaceLogLayout  extends AbstractStringLayout{
	
	protected InterfaceLogLayout(Charset charset) {
		super(charset);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toSerializable(LogEvent event) {
		// TODO Auto-generated method stub
		if( (event.getMessage() == null)  || !(event.getMessage() instanceof InterfaceLogInfo)) {
			LogManager.getRootLogger().error("Security Logger got bad logging event");
			return null;
		}
		return ((InterfaceLogInfo)event.getMessage()).toString();
	}
	
	
    @PluginFactory
    public static InterfaceLogLayout createLayout(
                                            @PluginAttribute(value = "charset", defaultString = "UTF-8") Charset charset) {
        return new InterfaceLogLayout(charset);
    }	

}
