var config  = require('./config'),
    mysql   = require('mysql')
    conn    = null;

module.exports = function(callback){
  if(conn){
    console.log('retrieving existing connection');
    callback(conn);
    return;
  }

  // Bootstrap mysql connection
  conn = mysql.createConnection(config.db_mysql);
  conn.connect(function(err) {
      if (err) {
          console.error('\x1b[31m', 'Could not connect to MySQL database!');
          console.log(err);
          throw new Error(err);
      } else {
          console.log('Connected to mysql database');
          callback(conn);
      }
  });
}