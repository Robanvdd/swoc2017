/**
 * Module dependencies.
 */
var statistics = require('../../app/controllers/statistics');

module.exports = function(app) {

    app.get('/api/statistics/rank-history', statistics.retrieveRankHistory);

}