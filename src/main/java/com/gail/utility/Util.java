package com.gail.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
	
	private static final Logger logger = LoggerFactory.getLogger(Util.class);

	public static String encryptPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public static boolean checkPassword(String password, String storedHash) {
		return BCrypt.checkpw(password, storedHash);
	}

	public static String tokenGenerator(int tokenSize) {
		String asciiCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		StringBuilder random = new StringBuilder();
		int i = 0;
		while (i < tokenSize) {
			random = random.append(asciiCharacters.charAt((int) (Math.random() * asciiCharacters.length())));
			i++;
		}
		return random.toString();
	}
 
	
	public static Date stringToDate(String str) {
		try {
			DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
			return inputFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void sendEmail(String to, String userPassword, String fullName, String path) {
		logger.info("Preparing to send mail....");

		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", Constants.HOST);
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(properties, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(Constants.USER, Constants.PASSWORD);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(Constants.DEFAULT_FROM_EMAIL));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(Constants.FORGOTTEN_PASSWORD_EMAIL_SUBJECT);

			MimeMultipart multipart = new MimeMultipart();
			BodyPart messageBodyPart = new MimeBodyPart();

			Map<String, String> input = new HashMap<String, String>();
			input.put("email", to);
			input.put("new_password", userPassword);
			input.put("name", fullName);

			String htmlText = readEmailFromHtml(path, input);
			messageBodyPart.setContent(htmlText, "text/html");

			multipart.addBodyPart(messageBodyPart);
			message.setContent(multipart);

			// Send message
			Transport.send(message);
			logger.info("Message Sent successfully....");
			System.out.println("Message Sent successfully....");

		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	protected static String readEmailFromHtml(String filePath, Map<String, String> input) {
		String msg = readContentFromFile(filePath);
		try {
			Set<Entry<String, String>> entries = input.entrySet();
			for (Map.Entry<String, String> entry : entries) {
				msg = msg.replace(entry.getKey().trim(), entry.getValue().trim());
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return msg;
	}

	private static String readContentFromFile(String fileName) {
		StringBuffer contents = new StringBuffer();

		try {
			// use buffering, reading one line at a time
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			try {
				String line = null;
				while ((line = reader.readLine()) != null) {
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			} finally {
				reader.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return contents.toString();
	}

	public static JSONObject stringToJson(String string) {
		JSONObject response = new JSONObject();
		try {
			response = new JSONObject(string);
		} catch (JSONException e) {
			try {
				response.put("status_code", 400);
				response.put("object", Constants.ERROR_MALFORMED_REQUEST);
				response.put("success", false);
			} catch (JSONException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	public static JSONObject requiredFieldsMap(String[] fields, JSONObject json) throws JSONException{
		JSONObject object = new JSONObject();
		for(String field:fields){
			if(!json.has(field))
				object.put(field, Constants.ERROR_FIELD_BLANK);
		}
		return object;
	}
	
	public static JSONObject requiredFieldsArray(String[] fields, JSONObject json) throws JSONException{
		JSONObject object = new JSONObject();
		JSONArray jsonArray = null;
		for(String field:fields){
			if(!json.has(field)){
				jsonArray = new JSONArray();
				jsonArray.put(Constants.ERROR_FIELD_BLANK);
				object.put(field, jsonArray);
			}
		}
		return object;
	}

	

    private static SimpleDateFormat format = new SimpleDateFormat(
            "yyyyMMddHHmmss");
    private static SimpleDateFormat formatDate = new SimpleDateFormat(
            "dd.MM.YYYY:HH.mm.ss");
    private static final  SimpleDateFormat IST_FORMAT = new SimpleDateFormat("hh:mm a");
   
    public static Date getCurrentUTCDate() {
        return getCurrentUTCTime().getTime();
    }

    public static Date getFutureDate(int days) {
        Calendar local = Calendar.getInstance(Locale.getDefault());
        int offset = local.getTimeZone().getOffset(local.getTimeInMillis());
        GregorianCalendar utc = new GregorianCalendar(
                TimeZone.getTimeZone("UTC"));
        long oneDayMillis=86400000;
        long noOfDay=days;
        long result=oneDayMillis*noOfDay;
        utc.setTimeInMillis(local.getTimeInMillis()+result);
        utc.add(Calendar.MILLISECOND, -offset);
        return utc.getTime();
    }

    public static GregorianCalendar getCurrentUTCTime() {
        Calendar local = Calendar.getInstance(Locale.getDefault());
        int offset = local.getTimeZone().getOffset(local.getTimeInMillis());
        GregorianCalendar utc = new GregorianCalendar(
                TimeZone.getTimeZone("UTC"));
        utc.setTimeInMillis(local.getTimeInMillis());
        utc.add(Calendar.MILLISECOND, -offset);
        return utc;
    }
    public static String getTimeInISTFormat(Date utcTime) throws ParseException {
        GregorianCalendar utc = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        utc.setTimeInMillis(utcTime.getTime());
        utc.add(Calendar.MILLISECOND, 19800000);
        return IST_FORMAT.format(utc.getTime());
    }
    
    public static String getDateInISTFormat(Date utcTime) throws ParseException {
        GregorianCalendar utc = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        utc.setTimeInMillis(utcTime.getTime());
        utc.add(Calendar.MILLISECOND, 19800000);
        return formatDate.format(utc.getTime());
        
    }
    
    public static String getFormattedDate(Date date) {
        return format.format(date);
    }

    
  
    

    public static  int getMemberAge(Date dateOfBirth)
    {
        Calendar dob = Calendar.getInstance();
        dob.setTime(dateOfBirth);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR))
        {
            age--;
        }
        return age;
    }
    
    public static Date getDateInISTFormats(Date utcTime) throws ParseException {
        GregorianCalendar utc = new GregorianCalendar(TimeZone.getTimeZone("UTC"));
        utc.setTimeInMillis(utcTime.getTime());
        utc.add(Calendar.MILLISECOND, 19800000);
        return utc.getTime();
        
    }
    

	
	
}
