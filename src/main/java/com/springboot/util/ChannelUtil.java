package com.springboot.util;

public class ChannelUtil {
	public static String getChannelName(String channelCode) {
		if (channelCode.equals("2000007")) {
			return "MaShangFinTech";
		}
		else if (channelCode.equals("2000008")) {
			return "XW";
		}
		else if (channelCode.equals("2000009")) {
			return "ChangAn";
		}
		else if (channelCode.equals("2000010") ) {
			return "QiangWei";
		}
		else if (channelCode .equals("2000011")) {
			return "...";
		}else{
		    return "not known channelCode";
        }
	}

}
