package com.amalitech.amalitechprojectdashboard.controllers.registration.email;

import com.amalitech.amalitechprojectdashboard.responses.ApdAuthException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class SendEmailService implements SendEmail{
	private final static Logger LOGGER = LoggerFactory.getLogger(SendEmailService.class);
	private final JavaMailSender javaMailSender;
	
	public SendEmailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	@Override
	@Async
	public void send(String to, String email) {
		try{
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setText(email,true);
			mimeMessageHelper.setSubject("Password Reset");
			mimeMessageHelper.setFrom("noreply@arms.amalitech.org");
			javaMailSender.send(mimeMessage);
		}catch (MessagingException e){
			LOGGER.error("Failed to send email", e);
			throw new ApdAuthException("Failed to send email");
		}
	}
	
	public String buildEmail(String name, String link) {
		return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
				"\n" +
				"<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
				"\n" +
				"  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
				"    <tbody><tr>\n" +
				"      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
				"        \n" +
				"        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
				"          <tbody><tr>\n" +
				"            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
				"                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
				"                  <tbody><tr>\n" +
				"                    <td style=\"padding-left:10px\">\n" +
				"                  \n" +
				"                    </td>\n" +
				"                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
				"                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
				"                    </td>\n" +
				"                  </tr>\n" +
				"                </tbody></table>\n" +
				"              </a>\n" +
				"            </td>\n" +
				"          </tr>\n" +
				"        </tbody></table>\n" +
				"        \n" +
				"      </td>\n" +
				"    </tr>\n" +
				"  </tbody></table>\n" +
				"  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
				"    <tbody><tr>\n" +
				"      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
				"      <td>\n" +
				"        \n" +
				"                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
				"                  <tbody><tr>\n" +
				"                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
				"                  </tr>\n" +
				"                </tbody></table>\n" +
				"        \n" +
				"      </td>\n" +
				"      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
				"    </tr>\n" +
				"  </tbody></table>\n" +
				"\n" +
				"\n" +
				"\n" +
				"  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
				"    <tbody><tr>\n" +
				"      <td height=\"30\"><br></td>\n" +
				"    </tr>\n" +
				"    <tr>\n" +
				"      <td width=\"10\" valign=\"middle\"><br></td>\n" +
				"      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
				"        \n" +
				"            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
				"        \n" +
				"      </td>\n" +
				"      <td width=\"10\" valign=\"middle\"><br></td>\n" +
				"    </tr>\n" +
				"    <tr>\n" +
				"      <td height=\"30\"><br></td>\n" +
				"    </tr>\n" +
				"  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
				"\n" +
				"</div></div>";
	}
	
	public String emailBody(String name, String link){
		return "<div style=\"margin: 0; padding: 0;\">\n" +
				"    <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n" +
				"      <tr>\n" +
				"        <td style=\"padding: 20px 0 30px 0;\">\n" +
				"  \n" +
				"            <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\" style=\"border-collapse: collapse; border: 0;\">\n" +
				"                <tr>\n" +
				"                <td align=\"center\" style=\"padding: 30px 0 20px 0; border-bottom: solid #dd5928 1px; background: #f0f0f0; \">\n" +
				"                    <img src=\"https://arms.amalitech.org/logo.png\" alt=\"AmaliTech Services\" width=\"250\" height=\"auto\" style=\"display: block;\" />\n" +
				"                </td>\n" +
				"                </tr>\n" +
				"                <tr>\n" +
				"                <td style=\"padding: 40px 30px 40px 30px; background: #f0f0f0;\">\n" +
				"                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse; text-align: center;\">\n" +
				"                    <tr>\n" +
				"                        <td style=\"color: #153643; font-family: Roboto, sans-serif;\">\n" +
				"                        <h1 style=\"font-size: 35px; margin: 0; font-weight: 100;\">Password Reset</h1>\n" +
				"                        </td>\n" +
				"                    </tr>\n" +
				"                    <tr>\n" +
				"                        <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 24px; padding: 20px 0 30px 0;\">\n" +
				"                        <p style=\"margin: 0;\">Hi "+ name +", your account is almost ready. <br>   Just click on the button below to change your password!</p>\n" +
				"                        </td>\n" +
				"                    </tr>\n" +
				"                    <tr>\n" +
				"                        <td style=\"color: #153643; font-family: Arial, sans-serif; font-size: 16px; line-height: 24px; padding: 20px 0 30px 0;\">\n" +
				"                            <a href=\""+ link +"\" style=\"padding: 10px; background-color: #dd5928; color: #fff; text-decoration: none;\">Change Password</button>    \n" +
				"                        </td>\n" +
				"                    </tr>\n" +
				"                    </table>\n" +
				"                </td>\n" +
				"                </tr>\n" +
				"                <tr>\n" +
				"                <td bgcolor=\"#dd5928\" style=\"padding: 30px 30px;\">\n" +
				"                    <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse;\">\n" +
				"                    <tr>\n" +
				"                        <td align=\"center\">\n" +
				"                        <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse;\">\n" +
				"                            <tr>\n" +
				"                            <td>\n" +
				"                                <a href=\"http://www.twitter.com/\">\n" +
				"                                <img src=\"https://assets.codepen.io/210284/tw.gif\" alt=\"Twitter.\" width=\"38\" height=\"38\" style=\"display: block;\" border=\"0\" />\n" +
				"                                </a>\n" +
				"                            </td>\n" +
				"                            <td style=\"font-size: 0; line-height: 0;\" width=\"20\">&nbsp;</td>\n" +
				"                            <td>\n" +
				"                                <a href=\"http://www.twitter.com/\">\n" +
				"                                <img src=\"https://assets.codepen.io/210284/fb.gif\" alt=\"Facebook.\" width=\"38\" height=\"38\" style=\"display: block;\" border=\"0\" />\n" +
				"                                </a>\n" +
				"                            </td>\n" +
				"                            </tr>\n" +
				"                        </table>\n" +
				"                        </td>\n" +
				"                    </tr>\n" +
				"                    </table>\n" +
				"                </td>\n" +
				"                </tr>\n" +
				"            </table>\n" +
				"  \n" +
				"        </td>\n" +
				"      </tr>\n" +
				"    </table>\n" +
				"</div>";
	}
}
