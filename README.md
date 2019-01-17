# Vodafone SWDT Task _ Yahoo Mail
  Scenario:  Preconditions: 
Create 2 email accounts on any email provider manually (because usually this part will contain captcha verification). 
 Steps: 
1.     Login from Email account #1  
2.     Send an email with unique/dynamic title (with timestamp) and an attachment to email #2 (use the desktop tool to upload the file).  
3.     Login to Email #2 
4.     Verify the received email content and download the attachment file  

# note: you can Chane Mails and password from 
VodaSWDTTask/src/test/java/demo/VodaSWDTTask/
 ```
	String firstMail = "Write your First Mail ";
	String firstPassword = "password ";

	String secondMail = "Write your Second Mail "; 
	String secondPassword = "Password"; 
 ```
and you can choose Web driver from change the variable in the same path and choose operating system too.
