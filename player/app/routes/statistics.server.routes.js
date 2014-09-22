/**
 * Module dependencies.
 */
var statistics = require('../../app/controllers/statistics');

module.exports = function(app) {

    app.get('/api/statistics/rank-history', statistics.retrieveRankHistory);

    app.get('/api/statistics/top-three-users', statistics.retrieveTopThreeUsers);

}