# AvitasWebApiAutomation

Instructions:
1. Clone the project.
2. Go to terminal and run below command AND provide valid login credentials of https://www.themoviedb.org/ website
mvn clean -U install -Dapi_key=318749acc7205bd8acb28cd7c681993a -Dusername=XXXXXX -Dpassword=XXXXX

OR

From Eclipse/Intelli j IDE, Right click on testng.xml and run as TestNG suite by providng VM args as given below. Please update valid credentials
-Dapi_key=318749acc7205bd8acb28cd7c681993a -Dusername=XXXXXX -Dpassword=XXXXX

External Reports are not configured for now. Basic report can be viewed from below path
<project location>/target/surefire-reports/index.html