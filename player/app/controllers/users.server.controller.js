'use strict';

/**
 * Module dependencies.
 */
var _ = require('lodash'),
    errorHandler = require('./errors'),
    mongoose = require('mongoose'),
    passport = require('passport'),
    User = mongoose.model('User');

/**
 * Signin after passport authentication
 */
exports.signin = function(req, res, next) {
    passport.authenticate('local', function(err, user, info) {
        if (err || !user) {
            res.redirect('/#/login');
        } else {
            // Remove sensitive data before login
            user.password = undefined;
            user.salt = undefined;

            req.login(user, function(err) {
                if (err) {
                    res.redirect('/#/login');
                } else {
                    res.redirect('/');
                }
            });
        }
    })(req, res, next);
};

/**
 * Signout
 */
exports.signout = function(req, res) {
    req.logout();
    req.session.destroy(function (err) {
            res.redirect('/'); //Inside a callback… bulletproof!
    });
};

/**
 * Create user (admin function)
 */
exports.createuser = function(req, res) {
    // Init Variables
    var user = new User(req.body); //username, password, email

    // Then save the user 
    user.save(function(err) {
        if (err) {
            return res.status(400).send({
                message: errorHandler.getErrorMessage(err)
            });
        } else {
            return res.send({ 
                message: 'User created succesfully'
            });
        }
    });
};

/**
 * Send User
 */
exports.me = function(req, res) {
    res.send({user: req.user.username});
};

/**
 * Require login routing middleware
 */
exports.requiresLogin = function(req, res, next) {
    if (!req.isAuthenticated()) {
        return res.status(401).send({
            message: 'User is not logged in'
        });
    }

    next();
};

/**
 * Require login routing middleware
 */
exports.requiresAdmin = function(req, res, next) {
    if (!req.isAuthenticated() || req.user.username !== 'admin') {
        return res.status(401).send({
            message: 'User is not logged in or not admin'
        });
    }

    next();
};