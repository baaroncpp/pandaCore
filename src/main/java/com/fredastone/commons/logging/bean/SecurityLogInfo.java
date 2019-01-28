package com.fredastone.commons.logging.bean;

import com.fredastone.commons.text.StringUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class SecurityLogInfo extends AbstractLogInfo{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 8235785674194936857L;
	@Getter @Setter private String userName;
	 @Getter @Setter private String resultInfo;
	 @Getter @Setter  private String staticInfo;
	 @Getter @Setter   private String extendInfo;
	 
	 
	 @Override
	public String toString() {
		 
		 final StringBuilder result = new StringBuilder();
		 
		 result.append(getLogTime()).append(PIPE)
		  .append(StringUtil.dealNull(userName)).append(PIPE)
		 .append(StringUtil.dealNull(resultInfo)).append(PIPE)
		 .append(StringUtil.dealNull(staticInfo)).append(PIPE)
		 .append(StringUtil.dealNull(extendInfo))
		 .append("\n");
		 
		 return result.toString();
	}


	@Override
	public String getFormat() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getFormattedMessage() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object[] getParameters() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Throwable getThrowable() {
		// TODO Auto-generated method stub
		return null;
	}
}
