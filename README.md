swoc2017
========

Sioux Weekend of Code 2017

See player/readme.md for the initial setup
The MySQL DB might not be completely neccessary as it was added AFTER the SWOC weekend 2014 itself.


Some DB related code I found:
swoc2014/player/app/controllers/statistics.server.controller.js:
var sql = "SELECT user.name, YEAR(datehour) as year, MONTH(datehour) as month, DAY(datehour) as day, HOUR(datehour) as hour, ranking \
                   FROM user_hour_rank \
                   JOIN user ON user_hour_rank.user = user.id \
                   WHERE user.name IS NOT NULL \
                   ORDER BY user.name, datehour;";
				   
				   var sql = "SELECT user.name AS username \
                  FROM current_bot cb \
                  JOIN user ON cb.user=user.id \
                  JOIN bot ON bot.id=cb.bot \
                  ORDER BY -ranking \
                  LIMIT 3;";
				  
				  
				  
swoc2014/player/config/config.js
	db: 'mongodb://localhost/swoc-dev',
	port: process.env.PORT || 8090,
	sessionSecret: 'Valar Morghulis',
	sessionCollection: 'sessions',
	db_mysql: {
		host: 'localhost',
		database: 'swoc',
		user: 'swocreader',
	}
