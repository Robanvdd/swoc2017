
/**
 * Module init function.
 */
module.exports = function() {

	/**
	 * Add our server node extensions
	 */
	require.extensions['.server.controller.js'] = require.extensions['.js'];
	require.extensions['.server.model.js'] = require.extensions['.js'];
	require.extensions['.server.routes.js'] = require.extensions['.js'];
	
}