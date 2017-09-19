Feature: Validate Customer detail page 

@sanityTest
Scenario: Verify Customer details page, after clicking on desired IntelliCloud customer 
	Given I login to tdrive application 
	And I navigate to "Customer management" page 
	When I enter customer detail to search "BevMo" 
	And I click on "BevMo" customer details
	Then I verify "BevMo" customer detail page 
	
@sanityTest
Scenario: Verify the sort option on customer detail page 
	Given I login to tdrive application 
	And I navigate to "Customer management" page 
	When I enter customer detail to search "BevMo" 
	And I click on "BevMo" customer details
	Then I verify "service" "ascending" order 
	
@sanityTest
Scenario: Verify back button functionality
	Given I login to tdrive application 
	And I navigate to "Customer management" page 
	When I enter customer detail to search "BevMo" 
	And I click on "BevMo" customer details
	Then I verify back button functionality 
	
@sanityTest
Scenario: Validate pagination option available on the bottom on customer management page
	Given I login to tdrive application 
	And I navigate to "Customer management" page 
	When I enter customer detail to search "BevMo" 
	And I click on "BevMo" customer details
	Then I verify "customer detail" pagination bar is displayed 
	And I verify pagination functionality 
	
#@sanityTest
#Scenario: Verify update instance button on customer detail page 
#	Given I login to tdrive application 
#	#And I navigate to "Customer management" page 
#	When I enter customer detail to search "BevMo" 
#	And I click on "BevMo" customer details
#	Then I verify action status "Update instance " as "enable" 
#	And I click on Update instance button 
#	Then I verify update instance landing page 
	
@sanityTest
Scenario: Verify delete instance button on customer detail page 
	Given I login to tdrive application 
And I navigate to "Customer management" page 
	When I enter customer detail to search "BevMo" 
	And I click on "BevMo" customer details
	Then I verify action status "Delete instance " as "disable" 
	

	
	