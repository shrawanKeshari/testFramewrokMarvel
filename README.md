# testFramewrokMarvel
This API automation framework currently have two API's automated for testing with following cases:
1. Get the list of all the characters which have description and get the list of series too
2. Get the list of stories which does not involve a character with description

To Setup Project:
1. git clone https://github.com/shrawanKeshari/testFramewrokMarvel.git
2. use master branch only.
3. make sure you have maven install in your system.
4. run mvn clean complie to complie the project, so that maven download the supported dependency on to the system.
      
The Approach:
1. After you setup the porject.
2. Then from the root directory /testFrameworkAritel run the command "mvn clean test" to run all the test.
3. If you want to run only one test then you can modify testng.xml and can change include tag to exclude for not running the method like below
   a> for running both method
      <include name="TC001_GetAllCharactersWithDiscription" />
      <include name="TC002_GetAllStoriesWithNoDiscription" />
   b> for running only one method
      <exclude name="TC001_GetAllCharactersWithDiscription" />
      <include name="TC002_GetAllStoriesWithNoDiscription" />
   b> for not running any of the method
      <exclude name="TC001_GetAllCharactersWithDiscription" />
      <exclude name="TC002_GetAllStoriesWithNoDiscription" />
4. After suite run successfully you can see the HTML report file present in the directory "/testFrameworkAritel/test-output/ExtentReports" and format of file is
   ExtentReportResults<CURRENT-DATE-TIME>.html
5. We have storing the data for Character name and series list in file also and you can find it in directory "/testFrameworkAritel/test-output/logs" and file format is
   CharacterSeriesData<CURRENT-DATE-TIME>.txt
   
The project structure is as follows:
1> src/main/java : this package contains all the packages/class required as core components for the framework
   a> ApiExectutor class : this class contains the method required for executing the API call using rest-assured framework.
   b> Util package : this contains all the utilies class required for the support like date time functions, writing data to file.
   c> Listeners package : contains the listeners class which will be required to gather the required information for the test like status of test pass/fail
   d> ExtentReporting package : this contains the class required for generating Extent HTML report.
2> src/test/java : this contains all the packages/classes related to executing the test
   a> testClass package: this package contains the classes with test cases in it.
   b> Assertions package : this contains the classes for the assertion, as of now only one class with one method because right we have same assertions for both the case.
   
Brief about Test Class:
Test class contains following types of methods:
1. Two @Test methods for each test cases.
2. In Test methods we hit the request and traverse the reponse to form the list of characters and series related to it.
3. One @BeforMethod used for making the curl request with required headers and query parameters for the test cases.
4. One @AfterMethod used for writing the characters and series data into the file and embedding the file link in the HTML report.
