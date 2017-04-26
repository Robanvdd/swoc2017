var mysql = require('../../config/connection.js')

exports.retrieveRankHistory = function(req, res) {
    /*mysql(function(connection) {
        var sql = "SELECT user.name, YEAR(datehour) as year, MONTH(datehour) as month, DAY(datehour) as day, HOUR(datehour) as hour, ranking \
                   FROM user_hour_rank \
                   JOIN user ON user_hour_rank.user = user.id \
                   WHERE user.name IS NOT NULL \
                   ORDER BY user.name, datehour;";
        connection.query(sql, function(err, rows, fields) {
            if (err) res.send(err);
            else res.send(rows);
        });
    })*/
}

exports.retrieveTopThreeUsers = function(req, res) {
    /*mysql(function(connection) {
        var sql = "SELECT user.name AS username \
                  FROM current_bot cb \
                  JOIN user ON cb.user=user.id \
                  JOIN bot ON bot.id=cb.bot \
                  ORDER BY -ranking \
                  LIMIT 3;";
        connection.query(sql, function(err, rows, fields) {
            if (err) res.send(err);
            else {
              var names = [];
              for (var i = 0; i < rows.length; i++) {
                names.push(rows[i].username);
              }
              res.send(names);
            }
        });
    })*/
}