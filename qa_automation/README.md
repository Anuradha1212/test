#TDrive2.0 

1.Prerequisites:
  1.1)Have java installed
  1.2)Have maven installed

2.Export Test Execution:

 2.1)cd /home/opex/ReanTData/Tdrive2/qa_automation
 2.2)export CHROME_DRIVER_PATH=/usr/bin/chromedriver
 2.3)export BROWSER=chrome
 2.4)export USER_CREDENTIALS_FILE_PATH=/src/test/resources/UserCredentials.properties
 2.5)export TEST_URL=http://localhost:4200/
 
3.Execute Automation test
	-mvn test -Dcucumber.options="--tags @sanityTest"


