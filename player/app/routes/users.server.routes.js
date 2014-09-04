/**
 * Module dependencies.
 */
var users = require('../../app/controllers/users'),
    passport = require('passport');

module.exports = function(app) {

  app.get('/logout', function(req, res){
    console.log('logout');
    req.logout();
    res.redirect('/');
  });

  app.get('/test', function(req, res){
    res.redirect('/#/game_log/');
  });

  app.get('/user', users.requiresLogin, function(req, res){
    console.log('/user: name=' + req.user.username);
    res.send({user: req.user.username});
  });

  // POST /login
  //   This is an alternative implementation that uses a custom callback to
  //   acheive the same functionality.
  app.post('/login', function(req, res, next) {
    passport.authenticate('local', function(err, user, info) {
      if (err) { return next(err) }
      if (!user) {
        req.session.messages =  [info.message];
        console.log('failed to login, user is null' + [info.message]);
        return res.redirect('/login')
      }
      req.logIn(user, function(err) {
        if (err) { return next(err); }
        console.log('login successful');
        return res.redirect('/');
      });
    })(req, res, next);
  });

};