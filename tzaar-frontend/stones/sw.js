"use strict";

importScripts("polyfills/idbCacheUtils.js");
importScripts("polyfills/fetchPolyfill.js");
importScripts("polyfills/idbCachePolyfill.js");
importScripts("polyfills/idbCacheStoragePolyfill.js");

var log = console.log.bind(console);
var err = console.error.bind(console);
this.onerror = err;

var notify = function() {
  if (self.Notification && self.Notification.permission == "granted") {
    new self.Notification(arguments[0]);
  } else {
    log.apply(arguments)
  }
};

var baseUrl = (new URL("./", this.location.href) + "");
notify(baseUrl);

this.addEventListener("install", function(e) {
  e.waitUntil(caches.create("core").then(function(core) {
    var resourceUrls = [
      "",
      "?offline",
      "components/core-ajax/core-ajax.html",
      "components/core-ajax/core-xhr.html",
      "components/core-animated-pages/core-animated-pages.css",
      "components/core-animated-pages/core-animated-pages.html",
      "components/core-animated-pages/transitions/core-transition-pages.html",
      "components/core-animated-pages/transitions/cross-fade.html",
      "components/core-animated-pages/transitions/hero-transition.html",
      "components/core-animated-pages/transitions/scale-up.html",
      "components/core-animated-pages/transitions/slide-down.html",
      "components/core-animated-pages/transitions/slide-up.html",
      "components/core-animated-pages/transitions/tile-cascade.html",
      "components/core-drawer-panel/core-drawer-panel.css",
      "components/core-drawer-panel/core-drawer-panel.html",
      "components/core-icon/core-icon.css",
      "components/core-icon/core-icon.html",
      "components/core-icons/core-icons.html",
      "components/core-icons/iconsets/av-icons.html",
      "components/core-icons/iconsets/icons.html",
      "components/core-icons/iconsets/social-icons.html",
      "components/core-iconset-svg/core-iconset-svg.html",
      "components/core-iconset/core-iconset.html",
      "components/core-input/core-input.css",
      "components/core-input/core-input.html",
      "components/core-media-query/core-media-query.html",
      "components/core-meta/core-meta.html",
      "components/core-range/core-range.html",
      "components/core-scroll-header-panel/core-scroll-header-panel.css",
      "components/core-scroll-header-panel/core-scroll-header-panel.html",
      "components/core-selection/core-selection.html",
      "components/core-selector/core-selector.html",
      "components/core-style/core-style.html",
      "components/core-toolbar/core-toolbar.css",
      "components/core-toolbar/core-toolbar.html",
      "components/core-transition/core-transition.html",
      "components/firebase-element/firebase-element.html",
      "components/firebase-element/firebase-import.html",
      "components/firebase-element/firebase-login.html",
      "components/firebase-element/firebase-simple-login-import.html",
      "components/firebase-simple-login/firebase-simple-login.js",
      "components/firebase/firebase.js",
      "components/font-roboto/roboto.html",
      "components/paper-button/paper-button.css",
      "components/paper-button/paper-button.html",
      "components/paper-checkbox/paper-checkbox.css",
      "components/paper-checkbox/paper-checkbox.html",
      "components/paper-fab/paper-fab.css",
      "components/paper-fab/paper-fab.html",
      "components/paper-focusable/paper-focusable.html",
      "components/paper-icon-button/paper-icon-button.css",
      "components/paper-icon-button/paper-icon-button.html",
      "components/paper-input/error-200.png",
      "components/paper-input/paper-input.css",
      "components/paper-input/paper-input.html",
      "components/paper-item/paper-item.css",
      "components/paper-item/paper-item.html",
      "components/paper-progress/paper-progress.css",
      "components/paper-progress/paper-progress.html",
      "components/paper-radio-button/paper-radio-button.css",
      "components/paper-radio-button/paper-radio-button.html",
      "components/paper-ripple/paper-ripple.html",
      "components/paper-shadow/paper-shadow.css",
      "components/paper-shadow/paper-shadow.html",
      "components/paper-slider/paper-slider.css",
      "components/paper-slider/paper-slider.html",
      "components/paper-toggle-button/paper-toggle-button.css",
      "components/paper-toggle-button/paper-toggle-button.html",
      "components/platform/platform.js",
      "components/platform/platform.js.map",
      "components/polymer/layout.html",
      "components/polymer/polymer.html",
      "components/polymer/polymer.js",
      "components/polymer/polymer.js.map",
      "stones-elements/avatars.html",
      "stones-elements/categories.json",
      "stones-elements/category-icons.html",
      "stones-elements/category-images.html",
      "stones-elements/images/splash.svg",
      "stones-elements/templates/stones-quiz-alpha-picker.html",
      "stones-elements/templates/stones-quiz-fill-blank.html",
      "stones-elements/templates/stones-quiz-fill-two-blanks.html",
      "stones-elements/templates/stones-quiz-four-quarter.html",
      "stones-elements/templates/stones-quiz-multi-select.html",
      "stones-elements/templates/stones-quiz-picker.css",
      "stones-elements/templates/stones-quiz-picker.html",
      "stones-elements/templates/stones-quiz-single-select-item.html",
      "stones-elements/templates/stones-quiz-single-select.html",
      "stones-elements/templates/stones-quiz-toggle-translate.html",
      "stones-elements/templates/stones-quiz-true-false.html",
      "stones-elements/theme.html",
      "stones-elements/stones-app.css",
      "stones-elements/stones-app.html",
      "stones-elements/stones-categories.css",
      "stones-elements/stones-categories.html",
      "stones-elements/stones-category-front-page.html",
      "stones-elements/stones-category-themes.css",
      "stones-elements/stones-category.css",
      "stones-elements/stones-category.html",
      "stones-elements/stones-datasource.html",
      "stones-elements/stones-leaderboard.html",
      "stones-elements/stones-profile.html",
      "stones-elements/stones-quiz-base.html",
      "stones-elements/stones-quiz-score.html",
      "stones-elements/stones-quiz-view.html",
      "stones-elements/stones-quizzes.css",
      "stones-elements/stones-quizzes.html",
      "stones-elements/stones-results.css",
      "stones-elements/stones-results.html",
      "stones-elements/stones-status-bar.html",
      "stones-elements/stones-user-score.css",
      "stones-elements/stones-user-score.html",
      "icons/icon-196.png",
      "images/splash.svg",
      "index.html",
      "polyfills/fonts/fonts.css",
      "polyfills/fonts/roboto.html",
      "polyfills/fonts/RobotoDraft-Black.woff2",
      "polyfills/fonts/RobotoDraft-Bold.woff2",
      "polyfills/fonts/RobotoDraft-BoldItalic.woff2",
      "polyfills/fonts/RobotoDraft-Light.woff2",
      "polyfills/fonts/RobotoDraft-Medium.woff2",
      "polyfills/fonts/RobotoDraft-Regular.woff2",
      "polyfills/fonts/RobotoDraft-Thin.woff2",
      "theme.css",
      "vulcanized.html",
    ];

    return Promise.all(resourceUrls.map(function(relativeUrl) {
      return core.add(baseUrl + relativeUrl);
    }));
  }));
});

this.addEventListener("fetch", function(e) {
  var request = e.request;

  if (this.scope.indexOf(request.origin) == -1) {
    return;
  }

  // Basic read-through caching.
  e.respondWith(
    caches.match(request, "core").then(
      function(response) {
        return response;
      },
      function() {
        // we didn't have it in the cache, so add it to the cache and return it
        return caches.get("core").then(
          function(core) {
            log("runtime caching:", request.url);

            // FIXME(slighltyoff): add should take/return an array
            return core.add(request).then(
              function(response) {
                return response;
              }
            );
          }
        );
      }
    )
  );
});

this.addEventListener("sync", function(e) {
  this.clients.getServiced().then(log, err);
});
