package com.cmsz.hxj.web.util;

public class Constants {
	public static final String defaultImageURL = "/main/webapp/image/defaultAvatar.jpg";
	public static final int male = 0;
	public static final int female = 1;
	
	public class ActivityStatus{
		public static final int create = 10;
		public static final int stopEnroll = 11;
		public static final int run = 12;
		public static final int end = 20;
		public static final int cancel = 21;
	}
	
	public class EnrollStatus{
		public static final int uninformed = 0;
		public static final int hasinformed = 1;
		
		public static final int quit = 0;
		public static final int enroll = 1;
		public static final int confirm = 2;
		public static final int end = 3;
		
	}
}
