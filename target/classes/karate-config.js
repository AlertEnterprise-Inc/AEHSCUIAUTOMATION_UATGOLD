function fn() {    
	
	var env = karate.env; // get system property 'karate.env'
	karate.log('karate.env system property was:', env);
	if (!env) {
		env = 'automation';
	}
	karate.log('karate.env = ', env);
	var config = {

			url:'http://192.168.193.169:60/',
			login:{username:'admin',
	  		password:'Alert1234'},
			dbConfig:{ username: 'aehsc', password: 'Alert1234', url: 'jdbc:sqlserver://192.168.194.124:1433;databaseName=aehsc', driverClassName: 'com.microsoft.sqlserver.jdbc.SQLServerDriver'}
			}
	if (env == 'dev') {
		config.url='http://devhsc.alertenterprise.com/';
		config.login={username:'admin',password:'alert'};
		config.dbConfig={ username: 'alert', password: 'alert123', url: 'jdbc:postgresql://54.200.247.61:5432/aehscdb?currentSchema=devhsc', driverClassName: 'org.postgresql.Driver'};
	}
	if (env == 'qa') {
		config.url='http://192.168.192.112/';
		config.login={username:'admin',password:'Alert1234'};
		config.dbConfig={ username: 'AEQA13893', password: 'Alert1234', url: 'jdbc:sqlserver://192.168.193.124:1433;databaseName=AEQA13893', driverClassName: 'com.microsoft.sqlserver.jdbc.SQLServerDriver'};
	}
	if (env == 'automation') {
		config.url='http://192.168.193.169:60/';
		config.login={username:'admin',password:'Alert1234'};
		config.dbConfig={ username: 'aehsc', password: 'Alert1234', url: 'jdbc:sqlserver://192.168.194.124:1433;databaseName=aehsc', driverClassName: 'com.microsoft.sqlserver.jdbc.SQLServerDriver'};
	}
	//waiting for a connection or if servers don't respond within 5 seconds
	karate.configure('connectTimeout', 5000);
	karate.configure('readTimeout', 5000);
	return config;
}