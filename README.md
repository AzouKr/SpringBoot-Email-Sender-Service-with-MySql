# SpringBoot-EmailSender-MySQL

## Table of contents

1. [Overview](#Overview)
2. [Installation](#Installation)
3. [Sending Email](#Sending_Email)

## Overview<a name="Overview"></a>

In this Spring Boot project,  we'll walk through the steps needed to send emails from Spring Boot application. For the former, we'll use the JavaMail library, and the latter will use the spring-boot-starter-mail dependency.




## Installation<a name="installation"></a>

### Maven Dependencies

First, we need to add the dependencies to our pom.xml.


```
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context-support</artifactId>
    <version>5.2.8.RELEASE</version>
</dependency>
```

### Spring Boot Mail Server Properties

Once the dependency is in place, the next step is to specify the mail server properties in the application.properties file using the spring.mail.* namespace.

We can specify the properties for the Gmail SMTP server this way:

```
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=<login user to smtp server>
spring.mail.password=<login password to smtp server>
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

We can send an email via Gmail SMTP server. Have a look at the [documentation](https://support.google.com/mail/answer/7104828?hl=en&rd=3&visit_id=637812960575206948-2906502522) to see the Gmail outgoing mail SMTP server properties.

Our application.properties file is already configured to use Gmail SMTP (see the previous section).

Note that the password for our account should not be an ordinary password but an application password generated for our Google account. Follow this link to see the details and to generate your Google App Password.
<br/><br/><br/>

## Sending Email<a name="Sending_Email"></a>


### MailSenderService.java

Let's first compose and send a simple email message :

```
@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender emailSender;

    public void sendSimpleMessage(
      String to, String subject, String text) {
        ...
        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom("Your Email here");
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);
        emailSender.send(message);
        ...
    }
}
```
<br/>

Now using the sendMail methode in MailSenderService.java we can send emails to all the users in our database.
<br/>

```
@GetMapping(path="/send")
	public boolean Send() {
				
  	String sql = "SELECT * FROM user ";
	  String subject = "Login Alert !!";
	  String body = "Someone just logged on the site";
			        
		List<User> users = jdbcTemplate.query(sql,new UserMapper());
				 	
	  String[] emailIds = new String[users.size()];
				 	
		for(int i=0;i<users.size();i++) {
				emailIds[i]=users.get(i).getEmail();
	   }
		service.sendMail(emailIds, body, subject);
		return true;
		}
```
<br/>
