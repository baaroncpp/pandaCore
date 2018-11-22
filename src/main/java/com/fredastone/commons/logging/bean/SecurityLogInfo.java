package com.fredastone.commons.logging.bean;

import com.fredastone.commons.text.StringUtil;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class SecurityLogInfo extends AbstractLogInfo{

	 @Getter @Setter private String userName;
	 @Getter @Setter private String resultInfo;
	 @Getter @Setter  private String staticInfo;
	 @Getter @Setter   private String extendInfo;
	 
	 
	 @Override
	public String toString() {
		 
		 final StringBuilder result = new StringBuilder();
		 
		 result.append(StringUtil.dealNull(userName)).append(PIPE)
		 .append(StringUtil.dealNull(resultInfo)).append(PIPE)
		 .append(StringUtil.dealNull(staticInfo)).append(PIPE)
		 .append(StringUtil.dealNull(extendInfo));
		 
		 return result.toString();
	}
}
