package com.cmsz.hxj.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
	

	  public static final Log DEFAULT   = new Log("DEFAULT");
	  public static final Log HTTP      = new Log("HTTP");
	  public static final Log SCHEDULER = new Log("SCHEDULER");
	  public static final Log DATABASE  = new Log("DATABASE");
	  public static final Log SERVICE   = new Log("SERVICE");

	  private Logger log;

	  private Log(String logname) {
	    this.log = LoggerFactory.getLogger(logname);
	  }

	  //
	  // Debug
	  //

	  public void debug (String message) {
	    log.debug(message);
	  }

	  public void debug(String message, Object... params) {
	    log.debug(message, params);
	  }

	  //
	  // Info
	  //

	  public void info (String message) {
	    log.info(message);
	  }

	  public void info(String message, Object... params) {
	    log.info(message, params);
	  }

	  //
	  // Warn
	  //

	  public void warn(String message) {
	    log.warn(message);
	  }

	  public void warn(String message, Object... params) {
	    log.warn(message, params);
	  }

	  //
	  // Error
	  //

	  public void error(String message) {
	    log.error(message);
	  }

	  public void error(String message, Object... params) {
	    log.error(message, params);
	  }

	  public void error(String message, Throwable e) {
	    log.error(message, e);
	  }

}
