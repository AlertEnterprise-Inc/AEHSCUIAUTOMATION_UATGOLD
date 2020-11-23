Feature: Auth Token

Background:
	* url url
	* def login = login

Scenario: Get auth token

	Given path 'api/auth/token'
	And request login
	When method post
	Then status 200
	And match response contains {'success':true}