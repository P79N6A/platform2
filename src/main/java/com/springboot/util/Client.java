package com.springboot.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
	

	/*
	 * webservice����������
	 */
	

	private String serviceURL = "";
	private String sn = "";// ���к�
	private String password = "";
	private String pwd = "";// ����

	/*
	 * ���캯��175259202  
	 */
	public Client(String sn, String password, String Url) throws UnsupportedEncodingException {
		serviceURL=Url;
		this.sn = sn;
		this.password = password;
		//����Ϊmd5(sn+password)
		this.pwd = this.getMD5(sn + password);
	}

	/*
	 * �������ƣ�getMD5 
	 * ��    �ܣ��ַ���MD5���� 
	 * ��    ������ת���ַ��� 
	 * �� �� ֵ������֮���ַ���
	 */
	public String getMD5(String sourceStr) throws UnsupportedEncodingException {
		String resultStr = "";
		try {
			byte[] temp = sourceStr.getBytes();
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(temp);
			// resultStr = new String(md5.digest());
			byte[] b = md5.digest();
			for (int i = 0; i < b.length; i++) {
				char[] digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
						'9', 'A', 'B', 'C', 'D', 'E', 'F' };
				char[] ob = new char[2];
				ob[0] = digit[(b[i] >>> 4) & 0X0F];
				ob[1] = digit[b[i] & 0X0F];
				resultStr += new String(ob);
			}
			return resultStr;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}


	/*
	 * �������ƣ�mdgetSninfo 
	 * ��    �ܣ���ȡ��Ϣ
	 * ��    ����sn,pwd(������кţ���������md5(sn+password))
	 * 
	 */
	public String mdgetSninfo() {
		String result = "";
		String soapAction = "http://entinfo.cn/mdgetSninfo";
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		xml += "<soap:Body>";
		xml += "<mdgetSninfo xmlns=\"http://entinfo.cn/\">";
		xml += "<sn>" + sn + "</sn>";
		xml += "<pwd>" + pwd + "</pwd>";
		xml += "<mobile>" + "" + "</mobile>";
		xml += "<content>" + "" + "</content>";
		xml += "<ext>" + "" + "</ext>";
		xml += "<stime>" + "" + "</stime>";
		xml += "<rrid>" + "" + "</rrid>";
		xml += "<msgfmt>" + "" + "</msgfmt>";
		xml += "</mdgetSninfo>";
		xml += "</soap:Body>";
		xml += "</soap:Envelope>";

		URL url;
		ByteArrayOutputStream bout = null;
		OutputStream out = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		try {
			url = new URL(serviceURL);

			URLConnection connection = url.openConnection();
			HttpURLConnection httpconn = (HttpURLConnection) connection;
			bout = new ByteArrayOutputStream();
			bout.write(xml.getBytes());
			byte[] b = bout.toByteArray();
			httpconn.setRequestProperty("Content-Length", String
					.valueOf(b.length));
			httpconn.setRequestProperty("Content-Type",
					"text/xml; charset=gb2312");
			httpconn.setRequestProperty("SOAPAction", soapAction);
			httpconn.setRequestMethod("POST");
			httpconn.setDoInput(true);
			httpconn.setDoOutput(true);

			out = httpconn.getOutputStream();
			out.write(b);

			isr = new InputStreamReader(httpconn
					.getInputStream());
			in = new BufferedReader(isr);
			String inputLine;
			while (null != (inputLine = in.readLine())) {
				Pattern pattern = Pattern.compile("<mdgetSninfoResult>(.*)</mdgetSninfoResult>");
				Matcher matcher = pattern.matcher(inputLine);
				while (matcher.find()) {
					result = matcher.group(1);
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}finally {
			if (bout!=null) {
				try {
					bout.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(isr!=null){
				try {
					isr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	
	/*
	 * �������ƣ�mdgxsend 
	 * ��    �ܣ����͸��Զ��� 
	 * ��    ����mobile,content,ext,stime,rrid,msgfmt(�ֻ��ţ����ݣ���չ�룬��ʱʱ�䣬Ψһ��ʶ�����ݱ���)
	 * �� �� ֵ��Ψһ��ʶ���������дrrid������ϵͳ���ɵ�
	 */
	public String mdgxsend(String mobile, String content, String ext, String stime,
			String rrid, String msgfmt) {
		String result = "";
		String soapAction = "http://entinfo.cn/mdgxsend";
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		xml += "<soap:Body>";
		xml += "<mdgxsend xmlns=\"http://entinfo.cn/\">";
		xml += "<sn>" + sn + "</sn>";
		xml += "<pwd>" + pwd + "</pwd>";
		xml += "<mobile>" + mobile + "</mobile>";
		xml += "<content>" + content + "</content>";
		xml += "<ext>" + ext + "</ext>";
		xml += "<stime>" + stime + "</stime>";
		xml += "<rrid>" + rrid + "</rrid>";
		xml += "<msgfmt>" + msgfmt + "</msgfmt>";
		xml += "</mdgxsend>";
		xml += "</soap:Body>";
		xml += "</soap:Envelope>";

		ByteArrayOutputStream bout = null;
		OutputStream out = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		URL url;
		try {
			url = new URL(serviceURL);

			URLConnection connection = url.openConnection();
			HttpURLConnection httpconn = (HttpURLConnection) connection;
			bout = new ByteArrayOutputStream();
			bout.write(xml.getBytes());
			byte[] b = bout.toByteArray();
			httpconn.setRequestProperty("Content-Length", String
					.valueOf(b.length));
			httpconn.setRequestProperty("Content-Type",
					"text/xml; charset=gb2312");
			httpconn.setRequestProperty("SOAPAction", soapAction);
			httpconn.setRequestMethod("POST");
			httpconn.setDoInput(true);
			httpconn.setDoOutput(true);

			out = httpconn.getOutputStream();
			out.write(b);
			out.close();

			isr = new InputStreamReader(httpconn
					.getInputStream());
			in = new BufferedReader(isr);
			String inputLine;
			while (null != (inputLine = in.readLine())) {
				Pattern pattern = Pattern.compile("<mdgxsendResult>(.*)</mdgxsendResult>");
				Matcher matcher = pattern.matcher(inputLine);
				while (matcher.find()) {
					result = matcher.group(1);
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}finally {
			if (bout!=null) {
				try {
					bout.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(isr!=null){
				try {
					isr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/*
	 * �������ƣ�mdsmssend
	 * ��    �ܣ����Ͷ��� 
	 * ��    ����mobile,content,ext,stime,rrid,msgfmt(�ֻ��ţ����ݣ���չ�룬��ʱʱ�䣬Ψһ��ʶ�����ݱ���)
	 * �� �� ֵ��Ψһ��ʶ���������дrrid������ϵͳ���ɵ�
	 */
	public String mdsmssend(String mobile, String content, String ext, String stime,
			String rrid,String msgfmt) {
		String result = "";
		String soapAction = "http://entinfo.cn/mdsmssend";
		String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
		xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
		xml += "<soap:Body>";
		xml += "<mdsmssend  xmlns=\"http://entinfo.cn/\">";
		xml += "<sn>" + sn + "</sn>";
		xml += "<pwd>" + pwd + "</pwd>";
		xml += "<mobile>" + mobile + "</mobile>";
		xml += "<content>" + content + "</content>";
		xml += "<ext>" + ext + "</ext>";
		xml += "<stime>" + stime + "</stime>";
		xml += "<rrid>" + rrid + "</rrid>";
		xml += "<msgfmt>" + msgfmt + "</msgfmt>";
		xml += "</mdsmssend>";
		xml += "</soap:Body>";
		xml += "</soap:Envelope>";

		ByteArrayOutputStream bout = null;
		OutputStream out = null;
		InputStreamReader isr = null;
		BufferedReader in = null;
		URL url;
		try {
			url = new URL(serviceURL);

			URLConnection connection = url.openConnection();
			HttpURLConnection httpconn = (HttpURLConnection) connection;
			bout = new ByteArrayOutputStream();
			bout.write(xml.getBytes());
			byte[] b = bout.toByteArray();
			httpconn.setRequestProperty("Content-Length", String
					.valueOf(b.length));
			httpconn.setRequestProperty("Content-Type",
					"text/xml; charset=gb2312");
			httpconn.setRequestProperty("SOAPAction", soapAction);
			httpconn.setRequestMethod("POST");
			httpconn.setDoInput(true);
			httpconn.setDoOutput(true);

			out = httpconn.getOutputStream();
			out.write(b);
			out.close();

			isr = new InputStreamReader(httpconn
					.getInputStream());
			in = new BufferedReader(isr);
			String inputLine;
			while (null != (inputLine = in.readLine())) {
				Pattern pattern = Pattern.compile("<mdsmssendResult>(.*)</mdsmssendResult>");
				Matcher matcher = pattern.matcher(inputLine);
				while (matcher.find()) {
					result = matcher.group(1);
				}
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}finally {
			if (bout!=null) {
				try {
					bout.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(isr!=null){
				try {
					isr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}



}
