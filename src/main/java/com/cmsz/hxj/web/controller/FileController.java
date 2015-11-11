package com.cmsz.hxj.web.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.cmsz.hxj.web.util.FileUtil;
import com.cmsz.hxj.web.util.IdUtil;
import com.cmsz.hxj.web.util.Log;

@Controller
@RequestMapping("/file")
public class FileController extends BaseController {
	
	@Value("${image.path}")
	private String imgPath;
	
	@Value("${image.suffix}")
	private String imgSuffix;
	
	private Object lock = new Object();
	
	@RequestMapping("/upload")
	public Map<String, Object> upLoadImg(HttpSession session, HttpServletRequest request) {
		String localRootPath = session.getServletContext().getRealPath("/");
		String urlRootPath = session.getServletContext().getContextPath();
		StringBuffer RequestUrl=request.getRequestURL();
		String httpUrl=RequestUrl.substring(0, RequestUrl.indexOf(urlRootPath));
		Log.SERVICE.debug("localRootpath=" + localRootPath);
		Log.SERVICE.debug("urlRootPath=" + urlRootPath);
		Log.SERVICE.debug("httpUrl=" + httpUrl);
		httpUrl=httpUrl+urlRootPath+imgPath;
		
		
		String imgId = null;
		Map<String, Object> outputMap=new HashMap<String, Object>();
		Map<String, Object>	dataMap=new HashMap<String, Object>();
		File file=null;
		File folder = new File(localRootPath + imgPath);
		do {
			imgId = IdUtil.getTimeToMd5();
			imgId =imgId.substring(0, 10);
			file  = new File(localRootPath + imgPath + imgId + imgSuffix);
			try {
				if(!folder.exists()){
					folder.mkdirs();
				}
				file.createNewFile();
				break;
			} catch (IOException e) {
				Log.SERVICE.info("创建文件失败：" + file.getAbsolutePath());
				e.printStackTrace();
				continue;
			}
		} while (true);
		
		OutputStream out=null;
		InputStream in=null;
		try {
			out=new BufferedOutputStream(new FileOutputStream(file));
			in=request.getInputStream();
			long count=FileUtil.transfer(in, out);
			dataMap.put("ImgUrl", httpUrl + imgId + imgSuffix);
			dataMap.put("ImgId",  imgId);
			this.genSuccOutputMap(outputMap, dataMap);
			Log.SERVICE.info("上传图片成功 file url:"+ urlRootPath + imgPath + imgId+" local:"+localRootPath + imgPath + imgId);
		} catch (FileNotFoundException e) {
			Log.SERVICE.error("写入文件不存在 "+ file.getAbsolutePath());
			this.genErrOutputMap(outputMap, "IoException:"+e.getMessage());
		} catch (IOException e) {
			Log.SERVICE.error("存储文件出现IO错误 "+ e.getMessage());
			this.genErrOutputMap(outputMap, "IoException:"+e.getMessage());
		}finally{
			try {
				out.flush();
				out.close();
				in.close();
			} catch (IOException e) {
				Log.SERVICE.error("FileController.upLoadImg 执行失败");
				this.genErrOutputMap(outputMap, "IoException:"+e.getMessage());
				e.printStackTrace();
			}
		}
		return outputMap;
	}
	
	
	@RequestMapping("/uploadByForm")
	public Map<String, Object> uploadByForm(HttpSession session, HttpServletRequest request) {
		  Map<String, Object> outputMap=new HashMap<String, Object>();
		  Map<String, Object>	dataMap=new HashMap<String, Object>();
		
		  CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		  MultipartHttpServletRequest multiRequest=null;
		  MultipartFile multFile=null;
		  if(multipartResolver.isMultipart(request)){
			  multiRequest = (MultipartHttpServletRequest)request;
			  multFile=multiRequest.getFile("avatar");
		  }else
		  {
			  this.genErrOutputMapWithCode(outputMap, "request multipart error", ReturnCode.PARAM_ERROR);
			  return outputMap;
		  }
		  
//		  MultipartHttpServletRequest  mult=(MultipartHttpServletRequest)request;
//		  MultipartFile multFile=mult.getFile("avatar");
		
		String localRootPath = session.getServletContext().getRealPath("/");
		String urlRootPath = session.getServletContext().getContextPath();
		StringBuffer RequestUrl=request.getRequestURL();
		String httpUrl=RequestUrl.substring(0, RequestUrl.indexOf(urlRootPath));
		Log.SERVICE.debug("request uploadByForm");
		Log.SERVICE.debug("localRootpath=" + localRootPath);
		Log.SERVICE.debug("urlRootPath=" + urlRootPath);
		Log.SERVICE.debug("httpUrl=" + httpUrl);
		httpUrl=httpUrl+urlRootPath+imgPath;
		
		
		
		
		String imgId = null;
		
		File file=null;
		File folder = new File(localRootPath + imgPath);
		do {
			imgId = IdUtil.getTimeToMd5();
			imgId =imgId.substring(0, 10);
			file  = new File(localRootPath + imgPath + imgId + imgSuffix);
			try {
				if(!folder.exists()){
					folder.mkdirs();
				}
				file.createNewFile();
				break;
			} catch (IOException e) {
				Log.SERVICE.info("创建文件失败：" + file.getAbsolutePath());
				e.printStackTrace();
				continue;
			}
		} while (true);
		
		
		
		OutputStream out=null;
		InputStream in=null;
		
		try {
			out=new BufferedOutputStream(new FileOutputStream(file));
			
//			in=request.getInputStream();
			in=multFile.getInputStream();
			
			long count=FileUtil.transfer(in, out);
			dataMap.put("ImgUrl", httpUrl + imgId + imgSuffix);
			dataMap.put("ImgId",  imgId);
			this.genSuccOutputMap(outputMap, dataMap);
			Log.SERVICE.info("上传图片成功 file url:"+ urlRootPath + imgPath + imgId+" local:"+localRootPath + imgPath + imgId);
		} catch (FileNotFoundException e) {
			Log.SERVICE.error("写入文件不存在 "+ file.getAbsolutePath());
			this.genErrOutputMap(outputMap, "IoException:"+e.getMessage());
		} catch (IOException e) {
			Log.SERVICE.error("存储文件出现IO错误 "+ e.getMessage());
			this.genErrOutputMap(outputMap, "IoException:"+e.getMessage());
		}finally{
			try {
				out.flush();
				out.close();
				in.close();
			} catch (IOException e) {
				Log.SERVICE.error("FileController.uploadByForm 执行失败");
				this.genErrOutputMap(outputMap, "IoException:"+e.getMessage());
				e.printStackTrace();
			}
		}
		return outputMap;
	}

}
