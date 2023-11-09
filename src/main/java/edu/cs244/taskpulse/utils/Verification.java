package edu.cs244.taskpulse.utils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class Verification{
	
	public static void sendMail(String recepient) throws Exception {
		Properties properties = new Properties();
		
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		String myAccountEmail = "taskdiverreminder@gmail.com";
		String password = "dzbu jdli sjar kabf";
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myAccountEmail, password);
			}
		});
		
		Message message = prepareMessage(session,myAccountEmail, recepient);
		
		Transport.send(message);
	
	}
	
	private static Message prepareMessage(Session session, String myAccountEmail, String recepient) {
		try {
		//generate code and send it
		int randomCode = 10000 + (int)(Math.random() * 90000);
		String code = String.valueOf(randomCode);
		sendCodeDatbase(code, recepient);
		
		
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(myAccountEmail));
		message.setRecipient(Message.RecipientType.TO,new InternetAddress(recepient));
		message.setSubject("Email Verification");
		message.setText("Your code for email Verification is: " + code);
		return message;
		}catch (Exception ex) {
			Logger.getLogger(Mail.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
	
	public static void sendCodeDatbase(String code, String recepient) {
		Connection connection = null;
		try {
			String sql = "UPDATE users "
					+ "SET code = ? WHERE email = ?";
			connection = DatabaseHandler.getConnection();
			
			PreparedStatement pStmt = connection.prepareStatement(sql);

//			// Set Parameters
			pStmt.setString(1, code);
			pStmt.setString(2, recepient);
			pStmt.executeUpdate();
			connection.commit();
			connection.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}
	
	public static boolean checkStatus(String UserName) {
		Connection connection = null;
		try {

			String sql = "SELECT active FROM users WHERE username = ? ";

			connection = DatabaseHandler.getConnection();

			PreparedStatement pStmt = connection.prepareStatement(sql);
//
//			// Set Parameters
			pStmt.setString(1, UserName);
			
			ResultSet rs = pStmt.executeQuery();
			
			if(rs.next()) {
				String active = rs.getString("active");
				int status = Integer.parseInt(active);  
				if(status == 1) {
					return true;
				}
			}
			return false;

		} catch (Exception ex) {
			ex.getMessage();
			return false;
		}

	}
	
	public static boolean checkCode(String email,String code) {
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			connection = DatabaseHandler.getConnection();

			String retrieveCodeQuery = "SELECT code FROM users WHERE email = ?";
			stmt = connection.prepareStatement(retrieveCodeQuery);
			stmt.setString(1, email);

			rs = stmt.executeQuery();

			if (rs.next()) {
				String storedCode = rs.getString("code");

				if (code.equals(storedCode)) {
					String updateStatus = "UPDATE users SET active = \"1\" WHERE email = ?";
					PreparedStatement pStmt = connection.prepareStatement(updateStatus);
					pStmt.setString(1, email);
					pStmt.executeUpdate();
					connection.commit();
					connection.close();
					return true;
				}
			}
				
			return false; // code incorrect
			
			
		} catch (SQLException ex) {
			ex.printStackTrace(); 
			return false;
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (connection != null)
					connection.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}
	
}
