package org.scalajs.jquery

import scala.scalajs.js
import org.scalajs.dom.raw._
import scala.scalajs.js.|

@js.native
trait JQueryAjaxSettings extends js.Object {
  /**
   * The content type sent in the request header that tells the server what kind of response it will accept in return. If the accepts setting needs modification, it is recommended to do so once in the $.ajaxSetup() method.
   */
  var accepts: js.Any = js.native
  /**
   * By default, all requests are sent asynchronously (i.e. this is set to true by default). If you need synchronous requests, set this option to false. Cross-domain requests and dataType: "jsonp" requests do not support synchronous operation. Note that synchronous requests may temporarily lock the browser, disabling any actions while the request is active. As of jQuery 1.8, the use of async: false with jqXHR ($.Deferred) is deprecated; you must use the success/error/complete callback options instead of the corresponding methods of the jqXHR object such as jqXHR.done() or the deprecated jqXHR.success().
   */
  var async: Boolean = js.native
  /**
   * A pre-request callback function that can be used to modify the jqXHR (in jQuery 1.4.x, XMLHTTPRequest) object before it is sent. Use this to set custom headers, etc. The jqXHR and settings objects are passed as arguments. This is an Ajax Event. Returning false in the beforeSend function will cancel the request. As of jQuery 1.5, the beforeSend option will be called regardless of the type of request.
   */
  def beforeSend(jqXHR: JQueryXHR, settings: JQueryAjaxSettings): js.Dynamic = js.native
  /**
   * If set to false, it will force requested pages not to be cached by the browser. Note: Setting cache to false will only work correctly with HEAD and GET requests. It works by appending "_={timestamp}" to the GET parameters. The parameter is not needed for other types of requests, except in IE8 when a POST is made to a URL that has already been requested by a GET.
   */
  var cache: Boolean = js.native
  /**
   * A function to be called when the request finishes (after success and error callbacks are executed). The function gets passed two arguments: The jqXHR (in jQuery 1.4.x, XMLHTTPRequest) object and a string categorizing the status of the request ("success", "notmodified", "error", "timeout", "abort", or "parsererror"). As of jQuery 1.5, the complete setting can accept an array of functions. Each function will be called in turn. This is an Ajax Event.
   */
  def complete(jqXHR: JQueryXHR, textStatus: String): js.Dynamic = js.native
  /**
   * An object of string/regular-expression pairs that determine how jQuery will parse the response, given its content type. (version added: 1.5)
   */
  var contents: js.Dictionary[js.Any] = js.native
  //According to jQuery.ajax source code, ajax's option actually allows contentType to set to "false"
  // https://github.com/DefinitelyTyped/DefinitelyTyped/issues/742
  /**
   * When sending data to the server, use this content type. Default is "application/x-www-form-urlencoded; charset=UTF-8", which is fine for most cases. If you explicitly pass in a content-type to $.ajax(), then it is always sent to the server (even if no data is sent). The W3C XMLHttpRequest specification dictates that the charset is always UTF-8; specifying another charset will not force the browser to change the encoding.
   */
  var contentType: js.Any = js.native
  /**
   * This object will be made the context of all Ajax-related callbacks. By default, the context is an object that represents the ajax settings used in the call ($.ajaxSettings merged with the settings passed to $.ajax).
   */
  var context: js.Any = js.native
  /**
   * An object containing dataType-to-dataType converters. Each converter's value is a function that returns the transformed value of the response. (version added: 1.5)
   */
  var converters: js.Dictionary[js.Any] = js.native
  /**
   * If you wish to force a crossDomain request (such as JSONP) on the same domain, set the value of crossDomain to true. This allows, for example, server-side redirection to another domain. (version added: 1.5)
   */
  var crossDomain: Boolean = js.native
  /**
   * Data to be sent to the server. It is converted to a query string, if not already a string. It's appended to the url for GET-requests. See processData option to prevent this automatic processing. Object must be key-value pairs. If value is an Array, jQuery serializes multiple values with same key based on the value of the traditional setting (described below).
   */
  var data: js.Any = js.native
  /**
   * A function to be used to handle the raw response data of XMLHttpRequest.This is a pre-filtering function to sanitize the response. You should return the sanitized data. The function accepts two arguments: The raw data returned from the server and the 'dataType' parameter.
   */
  def dataFilter(data: js.Any, ty: js.Any): js.Dynamic = js.native
  /**
   * The type of data that you're expecting back from the server. If none is specified, jQuery will try to infer it based on the MIME type of the response (an XML MIME type will yield XML, in 1.4 JSON will yield a JavaScript object, in 1.4 script will execute the script, and anything else will be returned as a string).
   */
  var dataType: String = js.native
  /**
   * A function to be called if the request fails. The function receives three arguments: The jqXHR (in jQuery 1.4.x, XMLHttpRequest) object, a string describing the type of error that occurred and an optional exception object, if one occurred. Possible values for the second argument (besides null) are "timeout", "error", "abort", and "parsererror". When an HTTP error occurs, errorThrown receives the textual portion of the HTTP status, such as "Not Found" or "Internal Server Error." As of jQuery 1.5, the error setting can accept an array of functions. Each function will be called in turn. Note: This handler is not called for cross-domain script and cross-domain JSONP requests. This is an Ajax Event.
   */
  def error(jqXHR: JQueryXHR, textStatus: String, errorThrown: String): js.Dynamic = js.native
  /**
   * Whether to trigger global Ajax event handlers for this request. The default is true. Set to false to prevent the global handlers like ajaxStart or ajaxStop from being triggered. This can be used to control various Ajax Events.
   */
  var global: Boolean = js.native
  /**
   * An object of additional header key/value pairs to send along with requests using the XMLHttpRequest transport. The header X-Requested-With: XMLHttpRequest is always added, but its default XMLHttpRequest value can be changed here. Values in the headers setting can also be overwritten from within the beforeSend function. (version added: 1.5)
   */
  var headers: js.Dictionary[js.Any] = js.native
  /**
   * Allow the request to be successful only if the response has changed since the last request. This is done by checking the Last-Modified header. Default value is false, ignoring the header. In jQuery 1.4 this technique also checks the 'etag' specified by the server to catch unmodified data.
   */
  var ifModified: Boolean = js.native
  /**
   * Allow the current environment to be recognized as "local," (e.g. the filesystem), even if jQuery does not recognize it as such by default. The following protocols are currently recognized as local: file, *-extension, and widget. If the isLocal setting needs modification, it is recommended to do so once in the $.ajaxSetup() method. (version added: 1.5.1)
   */
  var isLocal: Boolean = js.native
  /**
   * Override the callback function name in a jsonp request. This value will be used instead of 'callback' in the 'callback=?' part of the query string in the url. So {jsonp:'onJSONPLoad'} would result in 'onJSONPLoad=?' passed to the server. As of jQuery 1.5, setting the jsonp option to false prevents jQuery from adding the "?callback" string to the URL or attempting to use "=?" for transformation. In this case, you should also explicitly set the jsonpCallback setting. For example, { jsonp: false, jsonpCallback: "callbackName" }
   */
  var jsonp: js.Any = js.native
  /**
   * Specify the callback function name for a JSONP request. This value will be used instead of the random name automatically generated by jQuery. It is preferable to let jQuery generate a unique name as it'll make it easier to manage the requests and provide callbacks and error handling. You may want to specify the callback when you want to enable better browser caching of GET requests. As of jQuery 1.5, you can also use a function for this setting, in which case the value of jsonpCallback is set to the return value of that function.
   */
  var jsonpCallback: js.Any = js.native
  /**
   * The HTTP method to use for the request (e.g. "POST", "GET", "PUT"). (version added: 1.9.0)
   */
  var method: String = js.native
  /**
   * A MIME type to override the XHR MIME type. (version added: 1.5.1)
   */
  var mimeType: String = js.native
  /**
   * A password to be used with XMLHttpRequest in response to an HTTP access authentication request.
   */
  var password: String = js.native
  /**
   * By default, data passed in to the data option as an object (technically, anything other than a string) will be processed and transformed into a query string, fitting to the default content-type "application/x-www-form-urlencoded". If you want to send a DOMDocument, or other non-processed data, set this option to false.
   */
  var processData: Boolean = js.native
  /**
   * Only applies when the "script" transport is used (e.g., cross-domain requests with "jsonp" or "script" dataType and "GET" type). Sets the charset attribute on the script tag used in the request. Used when the character set on the local page is not the same as the one on the remote script.
   */
  var scriptCharset: String = js.native
  /**
   * An object of numeric HTTP codes and functions to be called when the response has the corresponding code. f the request is successful, the status code functions take the same parameters as the success callback; if it results in an error (including 3xx redirect), they take the same parameters as the error callback. (version added: 1.5)
   */
  var statusCode: js.Dictionary[js.Any] = js.native
  /**
   * A function to be called if the request succeeds. The function gets passed three arguments: The data returned from the server, formatted according to the dataType parameter; a string describing the status; and the jqXHR (in jQuery 1.4.x, XMLHttpRequest) object. As of jQuery 1.5, the success setting can accept an array of functions. Each function will be called in turn. This is an Ajax Event.
   */
  def success(data: js.Any, textStatus: String, jqXHR: JQueryXHR): js.Dynamic = js.native
  /**
   * Set a timeout (in milliseconds) for the request. This will override any global timeout set with $.ajaxSetup(). The timeout period starts at the point the $.ajax call is made; if several other requests are in progress and the browser has no connections available, it is possible for a request to time out before it can be sent. In jQuery 1.4.x and below, the XMLHttpRequest object will be in an invalid state if the request times out; accessing any object members may throw an exception. In Firefox 3.0+ only, script and JSONP requests cannot be cancelled by a timeout; the script will run even if it arrives after the timeout period.
   */
  var timeout: Double = js.native
  /**
   * Set this to true if you wish to use the traditional style of parameter serialization.
   */
  var traditional: Boolean = js.native
  /**
   * The type of request to make ("POST" or "GET"), default is "GET". Note: Other HTTP request methods, such as PUT and DELETE, can also be used here, but they are not supported by all browsers.
   */
  var `type`: String = js.native
  /**
   * A string containing the URL to which the request is sent.
   */
  var url: String = js.native
  /**
   * A username to be used with XMLHttpRequest in response to an HTTP access authentication request.
   */
  var username: String = js.native
  /**
   * Callback for creating the XMLHttpRequest object. Defaults to the ActiveXObject when available (IE), the XMLHttpRequest otherwise. Override to provide your own implementation for XMLHttpRequest or enhancements to the factory.
   */
  var xhr: js.Any = js.native
  /**
   * An object of fieldName-fieldValue pairs to set on the native XHR object. For example, you can use it to set withCredentials to true for cross-domain requests if needed. In jQuery 1.5, the withCredentials property was not propagated to the native XHR and thus CORS requests requiring it would ignore this flag. For this reason, we recommend using jQuery 1.5.1+ should you require the use of it. (version added: 1.5.1)
   */
  var xhrFields: js.Dictionary[js.Any] = js.native
}

@js.native
trait JQueryXHR extends XMLHttpRequest with JQueryPromise[js.Any] {
  /**
   * The .overrideMimeType() method may be used in the beforeSend() callback function, for example, to modify the response content-type header. As of jQuery 1.5.1, the jqXHR object also contains the overrideMimeType() method (it was available in jQuery 1.4.x, as well, but was temporarily removed in jQuery 1.5).
   */
  def overrideMimeType(mimeType: String): js.Dynamic = js.native
  /**
   * Cancel the request.
   *
   * @param statusText A string passed as the textStatus parameter for the done callback. Default value: "canceled"
   */
  def abort(statusText: String = js.native): Unit = js.native
  /**
   * Incorporates the functionality of the .done() and .fail() methods, allowing (as of jQuery 1.8) the underlying Promise to be manipulated. Refer to deferred.then() for implementation details.
   */
  def `then`[R](doneCallback: js.Function3[js.Any, String, JQueryXHR, R | JQueryPromise[R]], failCallback: js.Function3[JQueryXHR, String, js.Any, Unit] = js.native): JQueryPromise[R] = js.native
  /**
   * Property containing the parsed response if the response content type is json
   */
  var responseJSON: js.Any = js.native
  /**
   * A function to be called if the request fails.
   */
  def error(xhr: JQueryXHR, textStatus: String, errorThrown: String): Unit = js.native
}

@js.native
trait JQueryCallback extends js.Object {
  /**
   * Add a callback or a collection of callbacks to a callback list.
   *
   * @param callbacks A function, or array of functions, that are to be added to the callback list.
   * @see { https://api.jquery.com/callbacks.add/}
   */
  def add(callbacks: js.Function): JQueryCallback = js.native
  /**
   * Add a callback or a collection of callbacks to a callback list.
   *
   * @param callbacks A function, or array of functions, that are to be added to the callback list.
   * @see { https://api.jquery.com/callbacks.add/}
   */
  def add(callbacks: js.Array[js.Function]): JQueryCallback = js.native
  /**
   * Disable a callback list from doing anything more.
   * @see { https://api.jquery.com/callbacks.disable/}
   */
  def disable(): JQueryCallback = js.native
  /**
   * Determine if the callbacks list has been disabled.
   * @see { https://api.jquery.com/callbacks.disabled/}
   */
  def disabled(): Boolean = js.native
  /**
   * Remove all of the callbacks from a list.
   * @see { https://api.jquery.com/callbacks.empty/}
   */
  def empty(): JQueryCallback = js.native
  /**
   * Call all of the callbacks with the given arguments
   *
   * @param arguments The argument or list of arguments to pass back to the callback list.
   * @see { https://api.jquery.com/callbacks.fire/}
   */
  def fire(arguments: js.Any*): JQueryCallback = js.native
  /**
   * Determine if the callbacks have already been called at least once.
   * @see { https://api.jquery.com/callbacks.fired/}
   */
  def fired(): Boolean = js.native
  /**
   * Call all callbacks in a list with the given context and arguments.
   *
   * @param context A reference to the context in which the callbacks in the list should be fired.
   * @param args An argument, or array of arguments, to pass to the callbacks in the list.
   * @see { https://api.jquery.com/callbacks.fireWith/}
   */
  def fireWith(context: js.Any = js.native, args: js.Array[js.Any] = js.native): JQueryCallback = js.native
  /**
   * Determine whether a supplied callback is in a list
   *
   * @param callback The callback to search for.
   * @see { https://api.jquery.com/callbacks.has/}
   */
  def has(callback: js.Function): Boolean = js.native
  /**
   * Lock a callback list in its current state.
   * @see { https://api.jquery.com/callbacks.lock/}
   */
  def lock(): JQueryCallback = js.native
  /**
   * Determine if the callbacks list has been locked.
   * @see { https://api.jquery.com/callbacks.locked/}
   */
  def locked(): Boolean = js.native
  /**
   * Remove a callback or a collection of callbacks from a callback list.
   *
   * @param callbacks A function, or array of functions, that are to be removed from the callback list.
   * @see { https://api.jquery.com/callbacks.remove/}
   */
  def remove(callbacks: js.Function): JQueryCallback = js.native
  /**
   * Remove a callback or a collection of callbacks from a callback list.
   *
   * @param callbacks A function, or array of functions, that are to be removed from the callback list.
   * @see { https://api.jquery.com/callbacks.remove/}
   */
  def remove(callbacks: js.Array[js.Function]): JQueryCallback = js.native
}

@js.native
trait JQueryGenericPromise[T] extends js.Object {
  /**
   * Add handlers to be called when the Deferred object is resolved, rejected, or still in progress.
   *
   * @see { https://api.jquery.com/deferred.then/#deferred-then-doneFilter-failFilter-progressFilter}
   */
  def `then`[U](doneFilter: js.Function, failFilter: js.Function, progressFilter: js.Function): JQueryPromise[U] = js.native
  /**
   * Add handlers to be called when the Deferred object is resolved, rejected, or still in progress.
   *
   * @see { https://api.jquery.com/deferred.then/#deferred-then-doneFilter-failFilter-progressFilter}
   */
}

@js.native
trait JQueryPromiseCallback[T] extends js.Object {
  def apply(value: T, args: js.Any*): Unit = js.native
}

@js.native
trait JQueryPromiseOperator[T, U] extends js.Object {
  def apply(callback1: JQueryPromiseCallback[T] | js.Array[JQueryPromiseCallback[T]], callbacksN: JQueryPromiseCallback[js.Any] | js.Array[JQueryPromiseCallback[js.Any]]*): JQueryPromise[U] = js.native
}

@js.native
trait JQueryPromise[T] extends JQueryGenericPromise[T] {
  /**
   * Determine the current state of a Deferred object.
   * @see { https://api.jquery.com/deferred.state/}
   */
  def state(): String = js.native
  /**
   * Add handlers to be called when the Deferred object is either resolved or rejected.
   *
   * @param alwaysCallback1 A function, or array of functions, that is called when the Deferred is resolved or rejected.
   * @param alwaysCallbackN Optional additional functions, or arrays of functions, that are called when the Deferred is resolved or rejected.
   * @see { https://api.jquery.com/deferred.always/}
   */
  def always(alwaysCallback1: JQueryPromiseCallback[js.Any] | js.Array[JQueryPromiseCallback[js.Any]], alwaysCallbackN: JQueryPromiseCallback[js.Any] | js.Array[JQueryPromiseCallback[js.Any]]*): JQueryPromise[T] = js.native
  /**
   * Add handlers to be called when the Deferred object is resolved.
   *
   * @param doneCallback1 A function, or array of functions, that are called when the Deferred is resolved.
   * @param doneCallbackN Optional additional functions, or arrays of functions, that are called when the Deferred is resolved.
   * @see { https://api.jquery.com/deferred.done/}
   */
  def done(doneCallback1: JQueryPromiseCallback[T] | js.Array[JQueryPromiseCallback[T]], doneCallbackN: JQueryPromiseCallback[T] | js.Array[JQueryPromiseCallback[T]]*): JQueryPromise[T] = js.native
  /**
   * Add handlers to be called when the Deferred object is rejected.
   *
   * @param failCallback1 A function, or array of functions, that are called when the Deferred is rejected.
   * @param failCallbackN Optional additional functions, or arrays of functions, that are called when the Deferred is rejected.
   * @see { https://api.jquery.com/deferred.fail/}
   */
  def fail(failCallback1: JQueryPromiseCallback[js.Any] | js.Array[JQueryPromiseCallback[js.Any]], failCallbackN: JQueryPromiseCallback[js.Any] | js.Array[JQueryPromiseCallback[js.Any]]*): JQueryPromise[T] = js.native
  /**
   * Add handlers to be called when the Deferred object generates progress notifications.
   *
   * @param progressCallback1 A function, or array of functions, to be called when the Deferred generates progress notifications.
   * @param progressCallbackN Optional additional functions, or arrays of functions, to be called when the Deferred generates progress notifications.
   * @see { https://api.jquery.com/deferred.progress/}
   */
  def progress(progressCallback1: JQueryPromiseCallback[js.Any] | js.Array[JQueryPromiseCallback[js.Any]], progressCallbackN: JQueryPromiseCallback[js.Any] | js.Array[JQueryPromiseCallback[js.Any]]*): JQueryPromise[T] = js.native
  // Deprecated - given no typings
  def pipe(doneFilter: js.Function1[js.Any, Any] = js.native, failFilter: js.Function1[js.Any, Any] = js.native, progressFilter: js.Function1[js.Any, Any] = js.native): JQueryPromise[js.Any] = js.native
  /**
   * Return a Deferred's Promise object.
   *
   * @param target Object onto which the promise methods have to be attached
   * @see { https://api.jquery.com/deferred.promise/}
   */
  def promise(target: js.Any = js.native): JQueryPromise[T] = js.native
}

@js.native
trait JQueryDeferred[T] extends JQueryGenericPromise[T] {
  /**
   * Determine the current state of a Deferred object.
   * @see { https://api.jquery.com/deferred.state/}
   */
  def state(): String = js.native
  /**
   * Add handlers to be called when the Deferred object is either resolved or rejected.
   *
   * @param alwaysCallback1 A function, or array of functions, that is called when the Deferred is resolved or rejected.
   * @param alwaysCallbackN Optional additional functions, or arrays of functions, that are called when the Deferred is resolved or rejected.
   * @see { https://api.jquery.com/deferred.always/}
   */
  def always(alwaysCallback1: JQueryPromiseCallback[js.Any] | js.Array[JQueryPromiseCallback[js.Any]], alwaysCallbackN: JQueryPromiseCallback[js.Any] | js.Array[JQueryPromiseCallback[js.Any]]*): JQueryDeferred[T] = js.native
  /**
   * Add handlers to be called when the Deferred object is resolved.
   *
   * @param doneCallback1 A function, or array of functions, that are called when the Deferred is resolved.
   * @param doneCallbackN Optional additional functions, or arrays of functions, that are called when the Deferred is resolved.
   * @see { https://api.jquery.com/deferred.done/}
   */
  def done(doneCallback1: JQueryPromiseCallback[T] | js.Array[JQueryPromiseCallback[T]], doneCallbackN: JQueryPromiseCallback[T] | js.Array[JQueryPromiseCallback[T]]*): JQueryDeferred[T] = js.native
  /**
   * Add handlers to be called when the Deferred object is rejected.
   *
   * @param failCallback1 A function, or array of functions, that are called when the Deferred is rejected.
   * @param failCallbackN Optional additional functions, or arrays of functions, that are called when the Deferred is rejected.
   * @see { https://api.jquery.com/deferred.fail/}
   */
  def fail(failCallback1: JQueryPromiseCallback[js.Any] | js.Array[JQueryPromiseCallback[js.Any]], failCallbackN: JQueryPromiseCallback[js.Any] | js.Array[JQueryPromiseCallback[js.Any]]*): JQueryDeferred[T] = js.native
  /**
   * Add handlers to be called when the Deferred object generates progress notifications.
   *
   * @param progressCallback1 A function, or array of functions, to be called when the Deferred generates progress notifications.
   * @param progressCallbackN Optional additional functions, or arrays of functions, to be called when the Deferred generates progress notifications.
   * @see { https://api.jquery.com/deferred.progress/}
   */
  def progress(progressCallback1: JQueryPromiseCallback[js.Any] | js.Array[JQueryPromiseCallback[js.Any]], progressCallbackN: JQueryPromiseCallback[js.Any] | js.Array[JQueryPromiseCallback[js.Any]]*): JQueryDeferred[T] = js.native
  /**
   * Call the progressCallbacks on a Deferred object with the given args.
   *
   * @param args Optional arguments that are passed to the progressCallbacks.
   * @see { https://api.jquery.com/deferred.notify/}
   */
  def notify(value: js.Any, args: js.Any*): JQueryDeferred[T] = js.native
  /**
   * Call the progressCallbacks on a Deferred object with the given context and args.
   *
   * @param context Context passed to the progressCallbacks as the this object.
   * @param args Optional arguments that are passed to the progressCallbacks.
   * @see { https://api.jquery.com/deferred.notifyWith/}
   */
  def notifyWith(context: js.Any, args: js.Array[js.Any] = js.native): JQueryDeferred[T] = js.native
  /**
   * Reject a Deferred object and call any failCallbacks with the given args.
   *
   * @param args Optional arguments that are passed to the failCallbacks.
   * @see { https://api.jquery.com/deferred.reject/}
   */
  def reject(value: js.Any, args: js.Any*): JQueryDeferred[T] = js.native
  /**
   * Reject a Deferred object and call any failCallbacks with the given context and args.
   *
   * @param context Context passed to the failCallbacks as the this object.
   * @param args An optional array of arguments that are passed to the failCallbacks.
   * @see { https://api.jquery.com/deferred.rejectWith/}
   */
  def rejectWith(context: js.Any, args: js.Array[js.Any] = js.native): JQueryDeferred[T] = js.native
  /**
   * Resolve a Deferred object and call any doneCallbacks with the given args.
   *
   * @param value First argument passed to doneCallbacks.
   * @param args Optional subsequent arguments that are passed to the doneCallbacks.
   * @see { https://api.jquery.com/deferred.resolve/}
   */
  def resolve(value: T, args: js.Any*): JQueryDeferred[T] = js.native
  /**
   * Resolve a Deferred object and call any doneCallbacks with the given context and args.
   *
   * @param context Context passed to the doneCallbacks as the this object.
   * @param args An optional array of arguments that are passed to the doneCallbacks.
   * @see { https://api.jquery.com/deferred.resolveWith/}
   */
  def resolveWith(context: js.Any, args: js.Array[T] = js.native): JQueryDeferred[T] = js.native
  /**
   * Return a Deferred's Promise object.
   *
   * @param target Object onto which the promise methods have to be attached
   * @see { https://api.jquery.com/deferred.promise/}
   */
  def promise(target: js.Any = js.native): JQueryPromise[T] = js.native
  // Deprecated - given no typings
  def pipe(doneFilter: js.Function1[js.Any, Any] = js.native, failFilter: js.Function1[js.Any, Any] = js.native, progressFilter: js.Function1[js.Any, Any] = js.native): JQueryPromise[js.Any] = js.native
}

@js.native
trait BaseJQueryEventObject extends Event {
  /**
   * The current DOM element within the event bubbling phase.
   * @see { https://api.jquery.com/event.currentTarget/}
   */
  /**
   * An optional object of data passed to an event method when the current executing handler is bound.
   * @see { https://api.jquery.com/event.data/}
   */
  var data: js.Any = js.native
  /**
   * The element where the currently-called jQuery event handler was attached.
   * @see { https://api.jquery.com/event.delegateTarget/}
   */
  var delegateTarget: Element = js.native
  /**
   * Returns whether event.preventDefault() was ever called on this event object.
   * @see { https://api.jquery.com/event.isDefaultPrevented/}
   */
  def isDefaultPrevented(): Boolean = js.native
  /**
   * Returns whether event.stopImmediatePropagation() was ever called on this event object.
   * @see { https://api.jquery.com/event.isImmediatePropagationStopped/}
   */
  def isImmediatePropagationStopped(): Boolean = js.native
  /**
   * Returns whether event.stopPropagation() was ever called on this event object.
   * @see { https://api.jquery.com/event.isPropagationStopped/}
   */
  def isPropagationStopped(): Boolean = js.native
  /**
   * The namespace specified when the event was triggered.
   * @see { https://api.jquery.com/event.namespace/}
   */
  var namespace: String = js.native
  /**
   * The browser's original Event object.
   * @see { https://api.jquery.com/category/events/event-object/}
   */
  var originalEvent: Event = js.native
  /**
   * If this method is called, the default action of the event will not be triggered.
   * @see { https://api.jquery.com/event.preventDefault/}
   */
  /**
   * The other DOM element involved in the event, if any.
   * @see { https://api.jquery.com/event.relatedTarget/}
   */
  var relatedTarget: Element = js.native
  /**
   * The last value returned by an event handler that was triggered by this event, unless the value was undefined.
   * @see { https://api.jquery.com/event.result/}
   */
  var result: js.Any = js.native
  /**
   * Keeps the rest of the handlers from being executed and prevents the event from bubbling up the DOM tree.
   * @see { https://api.jquery.com/event.stopImmediatePropagation/}
   */
  /**
   * Prevents the event from bubbling up the DOM tree, preventing any parent handlers from being notified of the event.
   * @see { https://api.jquery.com/event.stopPropagation/}
   */
  /**
   * The DOM element that initiated the event.
   * @see { https://api.jquery.com/event.target/}
   */
  /**
   * The mouse position relative to the left edge of the document.
   * @see { https://api.jquery.com/event.pageX/}
   */
  var pageX: Double = js.native
  /**
   * The mouse position relative to the top edge of the document.
   * @see { https://api.jquery.com/event.pageY/}
   */
  var pageY: Double = js.native
  /**
   * For key or mouse events, this property indicates the specific key or button that was pressed.
   * @see { https://api.jquery.com/event.which/}
   */
  var which: Double = js.native
  /**
   * Indicates whether the META key was pressed when the event fired.
   * @see { https://api.jquery.com/event.metaKey/}
   */
  var metaKey: Boolean = js.native
}

@js.native
trait JQueryInputEventObject extends BaseJQueryEventObject {
  var altKey: Boolean = js.native
  var ctrlKey: Boolean = js.native
  var shiftKey: Boolean = js.native
}

@js.native
trait JQueryMouseEventObject extends JQueryInputEventObject {
  var button: Double = js.native
  var clientX: Double = js.native
  var clientY: Double = js.native
  var offsetX: Double = js.native
  var offsetY: Double = js.native
  var screenX: Double = js.native
  var screenY: Double = js.native
}

@js.native
trait JQueryKeyEventObject extends JQueryInputEventObject {
  var char: js.Any = js.native
  var charCode: Double = js.native
  var key: js.Any = js.native
  var keyCode: Double = js.native
}

@js.native
trait JQueryEventObject extends BaseJQueryEventObject with JQueryInputEventObject with JQueryMouseEventObject with JQueryKeyEventObject {
}

@js.native
trait JQuerySupport extends js.Object {
  var ajax: Boolean = js.native
  var boxModel: Boolean = js.native
  var changeBubbles: Boolean = js.native
  var checkClone: Boolean = js.native
  var checkOn: Boolean = js.native
  var cors: Boolean = js.native
  var cssFloat: Boolean = js.native
  var hrefNormalized: Boolean = js.native
  var htmlSerialize: Boolean = js.native
  var leadingWhitespace: Boolean = js.native
  var noCloneChecked: Boolean = js.native
  var noCloneEvent: Boolean = js.native
  var opacity: Boolean = js.native
  var optDisabled: Boolean = js.native
  var optSelected: Boolean = js.native
  def scriptEval(): Boolean = js.native
  var style: Boolean = js.native
  var submitBubbles: Boolean = js.native
  var tbody: Boolean = js.native
}

@js.native
trait JQueryParam extends js.Object {
  /**
   * Create a serialized representation of an array or object, suitable for use in a URL query string or Ajax request.
   *
   * @param obj An array or object to serialize.
   */
  def apply(obj: js.Any): String = js.native
  /**
   * Create a serialized representation of an array or object, suitable for use in a URL query string or Ajax request.
   *
   * @param obj An array or object to serialize.
   * @param traditional A Boolean indicating whether to perform a traditional "shallow" serialization.
   */
  def apply(obj: js.Any, traditional: Boolean): String = js.native
}

@js.native
trait JQueryEventConstructor extends js.Object {
  def apply(name: String, eventProperties: js.Any): JQueryEventObject = js.native
  //??? ConstructorMember(FunSignature(List(),List(FunParam(Ident(name),false,Some(TypeRefTree(CoreType(string),List()))), FunParam(Ident(eventProperties),true,Some(TypeRefTree(CoreType(any),List())))),Some(TypeRefTree(TypeName(JQueryEventObject),List()))))
}

@js.native
trait JQueryCoordinates extends js.Object {
  var left: Double = js.native
  var top: Double = js.native
}

@js.native
trait JQuerySerializeArrayElement extends js.Object {
  var name: String = js.native
  var value: String = js.native
}

@js.native
trait JQueryAnimationOptions extends js.Object {
  /**
   * A string or number determining how long the animation will run.
   */
  var duration: js.Any = js.native
  /**
   * A string indicating which easing function to use for the transition.
   */
  var easing: String = js.native
  /**
   * A function to call once the animation is complete.
   */
  var complete: js.Function = js.native
  /**
   * A function to be called for each animated property of each animated element. This function provides an opportunity to modify the Tween object to change the value of the property before it is set.
   */
  var step: js.Function2[Double, js.Any, Any] = js.native
  /**
   * A function to be called after each step of the animation, only once per animated element regardless of the number of animated properties. (version added: 1.8)
   */
  var progress: js.Function3[JQueryPromise[js.Any], Double, Double, Any] = js.native
  /**
   * A function to call when the animation begins. (version added: 1.8)
   */
  var start: js.Function1[JQueryPromise[js.Any], Any] = js.native
  /**
   * A function to be called when the animation completes (its Promise object is resolved). (version added: 1.8)
   */
  var done: js.Function2[JQueryPromise[js.Any], Boolean, Any] = js.native
  /**
   * A function to be called when the animation fails to complete (its Promise object is rejected). (version added: 1.8)
   */
  var fail: js.Function2[JQueryPromise[js.Any], Boolean, Any] = js.native
  /**
   * A function to be called when the animation completes or stops without completing (its Promise object is either resolved or rejected). (version added: 1.8)
   */
  var always: js.Function2[JQueryPromise[js.Any], Boolean, Any] = js.native
  /**
   * A Boolean indicating whether to place the animation in the effects queue. If false, the animation will begin immediately. As of jQuery 1.7, the queue option can also accept a string, in which case the animation is added to the queue represented by that string. When a custom queue name is used the animation does not automatically start; you must call .dequeue("queuename") to start it.
   */
  var queue: js.Any = js.native
  /**
   * A map of one or more of the CSS properties defined by the properties argument and their corresponding easing functions. (version added: 1.4)
   */
  var specialEasing: Object = js.native
}

@js.native
trait JQueryEasingFunction extends js.Object {
  def apply(percent: Double): Double = js.native
}

@js.native
trait JQueryEasingFunctions extends js.Object {
  @js.annotation.JSBracketAccess
  def apply(name: String): JQueryEasingFunction = js.native
  @js.annotation.JSBracketAccess
  def update(name: String, v: JQueryEasingFunction): Unit = js.native
  var linear: JQueryEasingFunction = js.native
  var swing: JQueryEasingFunction = js.native
}

@js.native
trait JQueryStatic extends js.Object {
  /**
   * Perform an asynchronous HTTP (Ajax) request.
   *
   * @param settings A set of key/value pairs that configure the Ajax request. All settings are optional. A default can be set for any option with $.ajaxSetup().
   * @see { https://api.jquery.com/jQuery.ajax/#jQuery-ajax-settings}
   */
  def ajax(settings: JQueryAjaxSettings): JQueryXHR = js.native
  /**
   * Perform an asynchronous HTTP (Ajax) request.
   *
   * @param url A string containing the URL to which the request is sent.
   * @param settings A set of key/value pairs that configure the Ajax request. All settings are optional. A default can be set for any option with $.ajaxSetup().
   * @see { https://api.jquery.com/jQuery.ajax/#jQuery-ajax-url-settings}
   */
  def ajax(url: String, settings: JQueryAjaxSettings): JQueryXHR = js.native
  /**
   * Handle custom Ajax options or modify existing options before each request is sent and before they are processed by $.ajax().
   *
   * @param dataTypes An optional string containing one or more space-separated dataTypes
   * @param handler A handler to set default values for future Ajax requests.
   * @see { https://api.jquery.com/jQuery.ajaxPrefilter/}
   */
  def ajaxPrefilter(dataTypes: String, handler: js.Function3[js.Any, JQueryAjaxSettings, JQueryXHR, Any]): Unit = js.native
  /**
   * Handle custom Ajax options or modify existing options before each request is sent and before they are processed by $.ajax().
   *
   * @param handler A handler to set default values for future Ajax requests.
   * @see { https://api.jquery.com/jQuery.ajaxPrefilter/}
   */
  def ajaxPrefilter(handler: js.Function3[js.Any, JQueryAjaxSettings, JQueryXHR, Any]): Unit = js.native
  /**
   * Creates an object that handles the actual transmission of Ajax data.
   *
   * @param dataType A string identifying the data type to use.
   * @param handler A handler to return the new transport object to use with the data type provided in the first argument.
   * @see { https://api.jquery.com/jQuery.ajaxTransport/}
   */
  def ajaxTransport(dataType: String, handler: js.Function3[js.Any, JQueryAjaxSettings, JQueryXHR, Any]): Unit = js.native
  var ajaxSettings: JQueryAjaxSettings = js.native
  /**
   * Set default values for future Ajax requests. Its use is not recommended.
   *
   * @param options A set of key/value pairs that configure the default Ajax request. All options are optional.
   * @see { https://api.jquery.com/jQuery.ajaxSetup/}
   */
  def ajaxSetup(options: JQueryAjaxSettings): Unit = js.native
  /**
   * Load data from the server using a HTTP GET request.
   *
   * @param url A string containing the URL to which the request is sent.
   * @param success A callback function that is executed if the request succeeds.
   * @param dataType The type of data expected from the server. Default: Intelligent Guess (xml, json, script, or html).
   * @see { https://api.jquery.com/jQuery.get/#jQuery-get-url-data-success-dataType}
   */
  def get(url: String, success: js.Function3[js.Any, String, JQueryXHR, Any], dataType: String): JQueryXHR = js.native
  /**
   * Load data from the server using a HTTP GET request.
   *
   * @param url A string containing the URL to which the request is sent.
   * @param data A plain object or string that is sent to the server with the request.
   * @param success A callback function that is executed if the request succeeds.
   * @param dataType The type of data expected from the server. Default: Intelligent Guess (xml, json, script, or html).
   * @see { https://api.jquery.com/jQuery.get/#jQuery-get-url-data-success-dataType}
   */
  def get(url: String, data: Object | String, success: js.Function3[js.Any, String, JQueryXHR, Any], dataType: String): JQueryXHR = js.native
  /**
   * Load data from the server using a HTTP GET request.
   *
   * @param settings The JQueryAjaxSettings to be used for the request
   * @see { https://api.jquery.com/jQuery.get/#jQuery-get-settings}
   */
  def get(settings: JQueryAjaxSettings): JQueryXHR = js.native
  /**
   * Load JSON-encoded data from the server using a GET HTTP request.
   *
   * @param url A string containing the URL to which the request is sent.
   * @param success A callback function that is executed if the request succeeds.
   * @see { https://api.jquery.com/jQuery.getJSON/}
   */
  def getJSON(url: String, success: js.Function3[js.Any, String, JQueryXHR, Any]): JQueryXHR = js.native
  /**
   * Load JSON-encoded data from the server using a GET HTTP request.
   *
   * @param url A string containing the URL to which the request is sent.
   * @param data A plain object or string that is sent to the server with the request.
   * @param success A callback function that is executed if the request succeeds.
   * @see { https://api.jquery.com/jQuery.getJSON/}
   */
  def getJSON(url: String, data: Object | String, success: js.Function3[js.Any, String, JQueryXHR, Any]): JQueryXHR = js.native
  /**
   * Load a JavaScript file from the server using a GET HTTP request, then execute it.
   *
   * @param url A string containing the URL to which the request is sent.
   * @param success A callback function that is executed if the request succeeds.
   * @see { https://api.jquery.com/jQuery.getScript/}
   */
  def getScript(url: String, success: js.Function3[String, String, JQueryXHR, Any] = js.native): JQueryXHR = js.native
  /**
   * Create a serialized representation of an array or object, suitable for use in a URL query string or Ajax request.
   *
   * @see { https://api.jquery.com/jQuery.param/}
   */
  var param: JQueryParam = js.native
  /**
   * Load data from the server using a HTTP POST request.
   *
   * @param url A string containing the URL to which the request is sent.
   * @param success A callback function that is executed if the request succeeds. Required if dataType is provided, but can be null in that case.
   * @param dataType The type of data expected from the server. Default: Intelligent Guess (xml, json, script, text, html).
   * @see { https://api.jquery.com/jQuery.post/#jQuery-post-url-data-success-dataType}
   */
  def post(url: String, success: js.Function3[js.Any, String, JQueryXHR, Any], dataType: String): JQueryXHR = js.native
  /**
   * Load data from the server using a HTTP POST request.
   *
   * @param url A string containing the URL to which the request is sent.
   * @param data A plain object or string that is sent to the server with the request.
   * @param success A callback function that is executed if the request succeeds. Required if dataType is provided, but can be null in that case.
   * @param dataType The type of data expected from the server. Default: Intelligent Guess (xml, json, script, text, html).
   * @see { https://api.jquery.com/jQuery.post/#jQuery-post-url-data-success-dataType}
   */
  def post(url: String, data: Object | String, success: js.Function3[js.Any, String, JQueryXHR, Any], dataType: String): JQueryXHR = js.native
  /**
   * Load data from the server using a HTTP POST request.
   *
   * @param settings The JQueryAjaxSettings to be used for the request
   * @see { https://api.jquery.com/jQuery.post/#jQuery-post-settings}
   */
  def post(settings: JQueryAjaxSettings): JQueryXHR = js.native
  /**
   * A multi-purpose callbacks list object that provides a powerful way to manage callback lists.
   *
   * @param flags An optional list of space-separated flags that change how the callback list behaves.
   * @see { https://api.jquery.com/jQuery.Callbacks/}
   */
  def Callbacks(flags: String = js.native): JQueryCallback = js.native
  /**
   * Holds or releases the execution of jQuery's ready event.
   *
   * @param hold Indicates whether the ready hold is being requested or released
   * @see { https://api.jquery.com/jQuery.holdReady/}
   */
  def holdReady(hold: Boolean): Unit = js.native
  /**
   * Accepts a string containing a CSS selector which is then used to match a set of elements.
   *
   * @param selector A string containing a selector expression
   * @param context A DOM Element, Document, or jQuery to use as context
   * @see { https://api.jquery.com/jQuery/#jQuery-selector-context}
   */
  def apply(selector: String, context: Element | JQuery): JQuery = js.native
  /**
   * Accepts a string containing a CSS selector which is then used to match a set of elements.
   *
   * @param element A DOM element to wrap in a jQuery object.
   * @see { https://api.jquery.com/jQuery/#jQuery-element}
   */
  def apply(element: Element): JQuery = js.native
  /**
   * Accepts a string containing a CSS selector which is then used to match a set of elements.
   *
   * @param elementArray An array containing a set of DOM elements to wrap in a jQuery object.
   * @see { https://api.jquery.com/jQuery/#jQuery-elementArray}
   */
  def apply(elementArray: js.Array[Element]): JQuery = js.native
  /**
   * Binds a function to be executed when the DOM has finished loading.
   *
   * @param callback A function to execute after the DOM is ready.
   * @see { https://api.jquery.com/jQuery/#jQuery-callback}
   */
  def apply(callback: js.Function1[JQueryStatic, Any]): JQuery = js.native
  /**
   * Accepts a string containing a CSS selector which is then used to match a set of elements.
   *
   * @param object A plain object to wrap in a jQuery object.
   * @see { https://api.jquery.com/jQuery/#jQuery-object}
   */
  def apply(`object`: js.Any): JQuery = js.native
  /**
   * Accepts a string containing a CSS selector which is then used to match a set of elements.
   *
   * @param object An existing jQuery object to clone.
   * @see { https://api.jquery.com/jQuery/#jQuery-object}
   */
  def apply(`object`: JQuery): JQuery = js.native
  /**
   * Specify a function to execute when the DOM is fully loaded.
   * @see { https://api.jquery.com/jQuery/#jQuery}
   */
  def apply(): JQuery = js.native
  /**
   * Creates DOM elements on the fly from the provided string of raw HTML.
   *
   * @param html A string of HTML to create on the fly. Note that this parses HTML, not XML.
   * @param ownerDocument A document in which the new elements will be created.
   * @see { https://api.jquery.com/jQuery/#jQuery-html-ownerDocument}
   */
  def apply(html: String, ownerDocument: Document): JQuery = js.native
  /**
   * Creates DOM elements on the fly from the provided string of raw HTML.
   *
   * @param html A string defining a single, standalone, HTML element (e.g. <div/> or <div></div>).
   * @param attributes An object of attributes, events, and methods to call on the newly-created element.
   * @see { https://api.jquery.com/jQuery/#jQuery-html-attributes}
   */
  def apply(html: String, attributes: Object): JQuery = js.native
  /**
   * Relinquish jQuery's control of the $ variable.
   *
   * @param removeAll A Boolean indicating whether to remove all jQuery variables from the global scope (including jQuery itself).
   * @see { https://api.jquery.com/jQuery.noConflict/}
   */
  def noConflict(removeAll: Boolean = js.native): JQueryStatic = js.native
  /**
   * Provides a way to execute callback functions based on one or more objects, usually Deferred objects that represent asynchronous events.
   *
   * @param deferreds One or more Deferred objects, or plain JavaScript objects.
   * @see { https://api.jquery.com/jQuery.when/}
   */
  def when[T](deferreds: T | JQueryPromise[T]*): JQueryPromise[T] = js.native
  /**
   * Hook directly into jQuery to override how particular CSS properties are retrieved or set, normalize CSS property naming, or create custom properties.
   * @see { https://api.jquery.com/jQuery.cssHooks/}
   */
  var cssHooks: js.Dictionary[js.Any] = js.native
  /**
   * An object containing all CSS properties that may be used without a unit. The .css() method uses this object to see if it may append px to unitless values.
   * @see { https://api.jquery.com/jQuery.cssNumber/}
   */
  var cssNumber: js.Any = js.native
  /**
   * Store arbitrary data associated with the specified element. Returns the value that was set.
   *
   * @param element The DOM element to associate with the data.
   * @param key A string naming the piece of data to set.
   * @param value The new data value.
   * @see { https://api.jquery.com/jQuery.data/#jQuery-data-element-key-value}
   */
  def data[T](element: Element, key: String, value: T): T = js.native
  /**
   * Returns value at named data store for the element, as set by jQuery.data(element, name, value), or the full data store for the element.
   *
   * @param element The DOM element to associate with the data.
   * @param key A string naming the piece of data to set.
   * @see { https://api.jquery.com/jQuery.data/#jQuery-data-element-key}
   */
  def data(element: Element, key: String): js.Dynamic = js.native
  /**
   * Returns value at named data store for the element, as set by jQuery.data(element, name, value), or the full data store for the element.
   *
   * @param element The DOM element to associate with the data.
   * @see { https://api.jquery.com/jQuery.data/#jQuery-data-element}
   */
  def data(element: Element): js.Dynamic = js.native
  /**
   * Execute the next function on the queue for the matched element.
   *
   * @param element A DOM element from which to remove and execute a queued function.
   * @param queueName A string containing the name of the queue. Defaults to fx, the standard effects queue.
   * @see { https://api.jquery.com/jQuery.dequeue/}
   */
  def dequeue(element: Element, queueName: String = js.native): Unit = js.native
  /**
   * Determine whether an element has any jQuery data associated with it.
   *
   * @param element A DOM element to be checked for data.
   * @see { https://api.jquery.com/jQuery.hasData/}
   */
  def hasData(element: Element): Boolean = js.native
  /**
   * Show the queue of functions to be executed on the matched element.
   *
   * @param element A DOM element to inspect for an attached queue.
   * @param queueName A string containing the name of the queue. Defaults to fx, the standard effects queue.
   * @see { https://api.jquery.com/jQuery.queue/#jQuery-queue-element-queueName}
   */
  def queue(element: Element, queueName: String): js.Array[js.Any] = js.native
  /**
   * Manipulate the queue of functions to be executed on the matched element.
   *
   * @param element A DOM element where the array of queued functions is attached.
   * @param queueName A string containing the name of the queue. Defaults to fx, the standard effects queue.
   * @param newQueue An array of functions to replace the current queue contents.
   * @see { https://api.jquery.com/jQuery.queue/#jQuery-queue-element-queueName-newQueue}
   */
  def queue(element: Element, queueName: String, newQueue: js.Array[js.Function]): JQuery = js.native
  /**
   * Manipulate the queue of functions to be executed on the matched element.
   *
   * @param element A DOM element on which to add a queued function.
   * @param queueName A string containing the name of the queue. Defaults to fx, the standard effects queue.
   * @param callback The new function to add to the queue.
   * @see { https://api.jquery.com/jQuery.queue/#jQuery-queue-element-queueName-callback}
   */
  def queue(element: Element, queueName: String, callback: js.Function): JQuery = js.native
  /**
   * Remove a previously-stored piece of data.
   *
   * @param element A DOM element from which to remove data.
   * @param name A string naming the piece of data to remove.
   * @see { https://api.jquery.com/jQuery.removeData/}
   */
  def removeData(element: Element, name: String = js.native): JQuery = js.native
  /**
   * A constructor function that returns a chainable utility object with methods to register multiple callbacks into callback queues, invoke callback queues, and relay the success or failure state of any synchronous or asynchronous function.
   *
   * @param beforeStart A function that is called just before the constructor returns.
   * @see { https://api.jquery.com/jQuery.Deferred/}
   */
  def Deferred[T](beforeStart: js.Function1[JQueryDeferred[T], Any] = js.native): JQueryDeferred[T] = js.native
  /**
   * Effects
   */
  var easing: JQueryEasingFunctions = js.native
  var fx: js.Any = js.native
  /**
   * Takes a function and returns a new one that will always have a particular context.
   *
   * @param func The function whose context will be changed.
   * @param context The object to which the context (this) of the function should be set.
   * @param additionalArguments Any number of arguments to be passed to the function referenced in the function argument.
   * @see { https://api.jquery.com/jQuery.proxy/#jQuery-proxy-function-context-additionalArguments}
   */
  def proxy(func: js.Function, context: Object, additionalArguments: js.Any*): js.Dynamic = js.native
  /**
   * Takes a function and returns a new one that will always have a particular context.
   *
   * @param context The object to which the context (this) of the function should be set.
   * @param name The name of the function whose context will be changed (should be a property of the context object).
   * @param additionalArguments Any number of arguments to be passed to the function named in the name argument.
   * @see { https://api.jquery.com/jQuery.proxy/#jQuery-proxy-context-name-additionalArguments}
   */
  def proxy(context: Object, name: String, additionalArguments: js.Any*): js.Dynamic = js.native
  var Event: JQueryEventConstructor = js.native
  /**
   * Takes a string and throws an exception containing it.
   *
   * @param message The message to send out.
   * @see { https://api.jquery.com/jQuery.error/}
   */
  def error(message: js.Any): JQuery = js.native
  var expr: js.Any = js.native
  var fn: js.Any = js.native
  //TODO: Decide how we want to type this
  var isReady: Boolean = js.native
  // Properties
  var support: JQuerySupport = js.native
  /**
   * Check to see if a DOM element is a descendant of another DOM element.
   *
   * @param container The DOM element that may contain the other element.
   * @param contained The DOM element that may be contained by (a descendant of) the other element.
   * @see { https://api.jquery.com/jQuery.contains/}
   */
  def contains(container: Element, contained: Element): Boolean = js.native
  /**
   * A generic iterator function, which can be used to seamlessly iterate over both objects and arrays. Arrays and array-like objects with a length property (such as a function's arguments object) are iterated by numeric index, from 0 to length-1. Other objects are iterated via their named properties.
   *
   * @param collection The object or array to iterate over.
   * @param callback The function that will be executed on every object.
   * @see { https://api.jquery.com/jQuery.each/#jQuery-each-array-callback}
   */
  def each[T](collection: js.Array[T], callback: js.Function2[Double, T, Any]): js.Dynamic = js.native
  /**
   * A generic iterator function, which can be used to seamlessly iterate over both objects and arrays. Arrays and array-like objects with a length property (such as a function's arguments object) are iterated by numeric index, from 0 to length-1. Other objects are iterated via their named properties.
   *
   * @param collection The object or array to iterate over.
   * @param callback The function that will be executed on every object.
   * @see { https://api.jquery.com/jQuery.each/#jQuery-each-object-callback}
   */
  def each(collection: js.Any, callback: js.Function2[js.Any, js.Any, Any]): js.Dynamic = js.native
  /**
   * Merge the contents of two or more objects together into the first object.
   *
   * @param target An object that will receive the new properties if additional objects are passed in or that will extend the jQuery namespace if it is the sole argument.
   * @param object1 An object containing additional properties to merge in.
   * @param objectN Additional objects containing properties to merge in.
   * @see { https://api.jquery.com/jQuery.extend/#jQuery-extend-target-object1-objectN}
   */
  def extend(target: js.Any, object1: js.Any, objectN: js.Any*): js.Dynamic = js.native
  /**
   * Merge the contents of two or more objects together into the first object.
   *
   * @param deep If true, the merge becomes recursive (aka. deep copy).
   * @param target The object to extend. It will receive the new properties.
   * @param object1 An object containing additional properties to merge in.
   * @param objectN Additional objects containing properties to merge in.
   * @see { https://api.jquery.com/jQuery.extend/#jQuery-extend-deep-target-object1-objectN}
   */
  def extend(deep: Boolean, target: js.Any, object1: js.Any, objectN: js.Any*): js.Dynamic = js.native
  /**
   * Execute some JavaScript code globally.
   *
   * @param code The JavaScript code to execute.
   * @see { https://api.jquery.com/jQuery.globalEval/}
   */
  def globalEval(code: String): js.Dynamic = js.native
  /**
   * Finds the elements of an array which satisfy a filter function. The original array is not affected.
   *
   * @param array The array to search through.
   * @param func The function to process each item against. The first argument to the function is the item, and the second argument is the index. The function should return a Boolean value.  this will be the global window object.
   * @param invert If "invert" is false, or not provided, then the function returns an array consisting of all elements for which "callback" returns true. If "invert" is true, then the function returns an array consisting of all elements for which "callback" returns false.
   * @see { https://api.jquery.com/jQuery.grep/}
   */
  def grep[T](array: js.Array[T], func: js.Function2[T, Double, Boolean], invert: Boolean = js.native): js.Array[T] = js.native
  /**
   * Search for a specified value within an array and return its index (or -1 if not found).
   *
   * @param value The value to search for.
   * @param array An array through which to search.
   * @param fromIndex The index of the array at which to begin the search. The default is 0, which will search the whole array.
   * @see { https://api.jquery.com/jQuery.inArray/}
   */
  def inArray[T](value: T, array: js.Array[T], fromIndex: Double = js.native): Double = js.native
  /**
   * Determine whether the argument is an array.
   *
   * @param obj Object to test whether or not it is an array.
   * @see { https://api.jquery.com/jQuery.isArray/}
   */
  def isArray(obj: js.Any): Boolean = js.native
  /**
   * Check to see if an object is empty (contains no enumerable properties).
   *
   * @param obj The object that will be checked to see if it's empty.
   * @see { https://api.jquery.com/jQuery.isEmptyObject/}
   */
  def isEmptyObject(obj: js.Any): Boolean = js.native
  /**
   * Determine if the argument passed is a JavaScript function object.
   *
   * @param obj Object to test whether or not it is a function.
   * @see { https://api.jquery.com/jQuery.isFunction/}
   */
  def isFunction(obj: js.Any): Boolean = js.native
  /**
   * Determines whether its argument is a number.
   *
   * @param value The value to be tested.
   * @see { https://api.jquery.com/jQuery.isNumeric/}
   */
  def isNumeric(value: js.Any): Boolean = js.native
  /**
   * Check to see if an object is a plain object (created using "{}" or "new Object").
   *
   * @param obj The object that will be checked to see if it's a plain object.
   * @see { https://api.jquery.com/jQuery.isPlainObject/}
   */
  def isPlainObject(obj: js.Any): Boolean = js.native
  /**
   * Determine whether the argument is a window.
   *
   * @param obj Object to test whether or not it is a window.
   * @see { https://api.jquery.com/jQuery.isWindow/}
   */
  def isWindow(obj: js.Any): Boolean = js.native
  /**
   * Check to see if a DOM node is within an XML document (or is an XML document).
   *
   * @param node The DOM node that will be checked to see if it's in an XML document.
   * @see { https://api.jquery.com/jQuery.isXMLDoc/}
   */
  def isXMLDoc(node: Node): Boolean = js.native
  /**
   * Convert an array-like object into a true JavaScript array.
   *
   * @param obj Any object to turn into a native Array.
   * @see { https://api.jquery.com/jQuery.makeArray/}
   */
  def makeArray(obj: js.Any): js.Array[js.Any] = js.native
  /**
   * Translate all items in an array or object to new array of items.
   *
   * @param array The Array to translate.
   * @param callback The function to process each item against. The first argument to the function is the array item, the second argument is the index in array The function can return any value. Within the function, this refers to the global (window) object.
   * @see { https://api.jquery.com/jQuery.map/#jQuery-map-array-callback}
   */
  def map[T, U](array: js.Array[T], callback: js.Function2[T, Double, U]): js.Array[U] = js.native
  /**
   * Translate all items in an array or object to new array of items.
   *
   * @param arrayOrObject The Array or Object to translate.
   * @param callback The function to process each item against. The first argument to the function is the value; the second argument is the index or key of the array or object property. The function can return any value to add to the array. A returned array will be flattened into the resulting array. Within the function, this refers to the global (window) object.
   * @see { https://api.jquery.com/jQuery.map/#jQuery-map-object-callback}
   */
  def map(arrayOrObject: js.Any, callback: js.Function2[js.Any, js.Any, Any]): js.Dynamic = js.native
  /**
   * Merge the contents of two arrays together into the first array.
   *
   * @param first The first array to merge, the elements of second added.
   * @param second The second array to merge into the first, unaltered.
   * @see { https://api.jquery.com/jQuery.merge/}
   */
  def merge[T](first: js.Array[T], second: js.Array[T]): js.Array[T] = js.native
  /**
   * An empty function.
   * @see { https://api.jquery.com/jQuery.noop/}
   */
  def noop(): js.Dynamic = js.native
  /**
   * Return a number representing the current time.
   * @see { https://api.jquery.com/jQuery.now/}
   */
  def now(): Double = js.native
  /**
   * Takes a well-formed JSON string and returns the resulting JavaScript object.
   *
   * @param json The JSON string to parse.
   * @see { https://api.jquery.com/jQuery.parseJSON/}
   */
  def parseJSON(json: String): js.Dynamic = js.native
  /**
   * Parses a string into an XML document.
   *
   * @param data a well-formed XML string to be parsed
   * @see { https://api.jquery.com/jQuery.parseXML/}
   */
  def parseXML(data: String): Document = js.native
  /**
   * Remove the whitespace from the beginning and end of a string.
   *
   * @param str Remove the whitespace from the beginning and end of a string.
   * @see { https://api.jquery.com/jQuery.trim/}
   */
  def trim(str: String): String = js.native
  /**
   * Determine the internal JavaScript [[Class]] of an object.
   *
   * @param obj Object to get the internal JavaScript [[Class]] of.
   * @see { https://api.jquery.com/jQuery.type/}
   */
  def `type`(obj: js.Any): String = js.native
  /**
   * Sorts an array of DOM elements, in place, with the duplicates removed. Note that this only works on arrays of DOM elements, not strings or numbers.
   *
   * @param array The Array of DOM elements.
   * @see { https://api.jquery.com/jQuery.unique/}
   */
  def unique(array: js.Array[Element]): js.Array[Element] = js.native
  /**
   * Parses a string into an array of DOM nodes.
   *
   * @param data HTML string to be parsed
   * @param context DOM element to serve as the context in which the HTML fragment will be created
   * @param keepScripts A Boolean indicating whether to include scripts passed in the HTML string
   * @see { https://api.jquery.com/jQuery.parseHTML/}
   */
  def parseHTML(data: String, context: HTMLElement, keepScripts: Boolean): js.Array[js.Any] = js.native
  /**
   * Parses a string into an array of DOM nodes.
   *
   * @param data HTML string to be parsed
   * @param context DOM element to serve as the context in which the HTML fragment will be created
   * @param keepScripts A Boolean indicating whether to include scripts passed in the HTML string
   * @see { https://api.jquery.com/jQuery.parseHTML/}
   */
  def parseHTML(data: String, context: Document, keepScripts: Boolean): js.Array[js.Any] = js.native
}

@js.native
trait JQuery extends js.Object {
  /**
   * Register a handler to be called when Ajax requests complete. This is an AjaxEvent.
   *
   * @param handler The function to be invoked.
   * @see { https://api.jquery.com/ajaxComplete/}
   */
  def ajaxComplete(handler: js.Function3[JQueryEventObject, XMLHttpRequest, js.Any, Any]): JQuery = js.native
  /**
   * Register a handler to be called when Ajax requests complete with an error. This is an Ajax Event.
   *
   * @param handler The function to be invoked.
   * @see { https://api.jquery.com/ajaxError/}
   */
  def ajaxError(handler: js.Function4[JQueryEventObject, JQueryXHR, JQueryAjaxSettings, js.Any, Any]): JQuery = js.native
  /**
   * Attach a function to be executed before an Ajax request is sent. This is an Ajax Event.
   *
   * @param handler The function to be invoked.
   * @see { https://api.jquery.com/ajaxSend/}
   */
  def ajaxSend(handler: js.Function3[JQueryEventObject, JQueryXHR, JQueryAjaxSettings, Any]): JQuery = js.native
  /**
   * Register a handler to be called when the first Ajax request begins. This is an Ajax Event.
   *
   * @param handler The function to be invoked.
   * @see { https://api.jquery.com/ajaxStart/}
   */
  def ajaxStart(handler: js.Function0[Any]): JQuery = js.native
  /**
   * Register a handler to be called when all Ajax requests have completed. This is an Ajax Event.
   *
   * @param handler The function to be invoked.
   * @see { https://api.jquery.com/ajaxStop/}
   */
  def ajaxStop(handler: js.Function0[Any]): JQuery = js.native
  /**
   * Attach a function to be executed whenever an Ajax request completes successfully. This is an Ajax Event.
   *
   * @param handler The function to be invoked.
   * @see { https://api.jquery.com/ajaxSuccess/}
   */
  def ajaxSuccess(handler: js.Function3[JQueryEventObject, XMLHttpRequest, JQueryAjaxSettings, Any]): JQuery = js.native
  /**
   * Load data from the server and place the returned HTML into the matched element.
   *
   * @param url A string containing the URL to which the request is sent.
   * @param data A plain object or string that is sent to the server with the request.
   * @param complete A callback function that is executed when the request completes.
   * @see { https://api.jquery.com/load/}
   */
  def load(url: String, data: String | Object, complete: js.Function3[String, String, XMLHttpRequest, Any]): JQuery = js.native
  /**
   * Encode a set of form elements as a string for submission.
   * @see { https://api.jquery.com/serialize/}
   */
  def serialize(): String = js.native
  /**
   * Encode a set of form elements as an array of names and values.
   * @see { https://api.jquery.com/serializeArray/}
   */
  def serializeArray(): js.Array[JQuerySerializeArrayElement] = js.native
  /**
   * Adds the specified class(es) to each of the set of matched elements.
   *
   * @param className One or more space-separated classes to be added to the class attribute of each matched element.
   * @see { https://api.jquery.com/addClass/#addClass-className}
   */
  def addClass(className: String): JQuery = js.native
  /**
   * Adds the specified class(es) to each of the set of matched elements.
   *
   * @param func A function returning one or more space-separated class names to be added to the existing class name(s). Receives the index position of the element in the set and the existing class name(s) as arguments. Within the function, this refers to the current element in the set.
   * @see { https://api.jquery.com/addClass/#addClass-function}
   */
  def addClass(func: js.Function2[Double, String, String]): JQuery = js.native
  /**
   * Add the previous set of elements on the stack to the current set, optionally filtered by a selector.
   * @see { https://api.jquery.com/addBack/}
   */
  def addBack(selector: String = js.native): JQuery = js.native
  /**
   * Get the value of an attribute for the first element in the set of matched elements.
   *
   * @param attributeName The name of the attribute to get.
   * @see { https://api.jquery.com/attr/#attr-attributeName}
   */
  def attr(attributeName: String): String = js.native
  /**
   * Set one or more attributes for the set of matched elements.
   *
   * @param attributeName The name of the attribute to set.
   * @param value A value to set for the attribute. If this is `null`, the attribute will be deleted.
   * @see { https://api.jquery.com/attr/#attr-attributeName-value}
   */
  def attr(attributeName: String, value: String | Double | Null): JQuery = js.native
  /**
   * Set one or more attributes for the set of matched elements.
   *
   * @param attributeName The name of the attribute to set.
   * @param func A function returning the value to set. this is the current element. Receives the index position of the element in the set and the old attribute value as arguments.
   * @see { https://api.jquery.com/attr/#attr-attributeName-function}
   */
  def attr(attributeName: String, func: js.Function2[Double, String, String | Double]): JQuery = js.native
  /**
   * Set one or more attributes for the set of matched elements.
   *
   * @param attributes An object of attribute-value pairs to set.
   * @see { https://api.jquery.com/attr/#attr-attributes}
   */
  def attr(attributes: Object): JQuery = js.native
  /**
   * Determine whether any of the matched elements are assigned the given class.
   *
   * @param className The class name to search for.
   * @see { https://api.jquery.com/hasClass/}
   */
  def hasClass(className: String): Boolean = js.native
  /**
   * Get the HTML contents of the first element in the set of matched elements.
   * @see { https://api.jquery.com/html/#html}
   */
  def html(): String = js.native
  /**
   * Set the HTML contents of each element in the set of matched elements.
   *
   * @param htmlString A string of HTML to set as the content of each matched element.
   * @see { https://api.jquery.com/html/#html-htmlString}
   */
  def html(htmlString: String): JQuery = js.native
  /**
   * Set the HTML contents of each element in the set of matched elements.
   *
   * @param func A function returning the HTML content to set. Receives the index position of the element in the set and the old HTML value as arguments. jQuery empties the element before calling the function; use the oldhtml argument to reference the previous content. Within the function, this refers to the current element in the set.
   * @see { https://api.jquery.com/html/#html-function}
   */
  def html(func: js.Function2[Double, String, String]): JQuery = js.native
  /**
   * Get the value of a property for the first element in the set of matched elements.
   *
   * @param propertyName The name of the property to get.
   * @see { https://api.jquery.com/prop/#prop-propertyName}
   */
  def prop(propertyName: String): js.Dynamic = js.native
  /**
   * Set one or more properties for the set of matched elements.
   *
   * @param propertyName The name of the property to set.
   * @param value A value to set for the property.
   * @see { https://api.jquery.com/prop/#prop-propertyName-value}
   */
  def prop(propertyName: String, value: String | Double | Boolean): JQuery = js.native
  /**
   * Set one or more properties for the set of matched elements.
   *
   * @param properties An object of property-value pairs to set.
   * @see { https://api.jquery.com/prop/#prop-properties}
   */
  def prop(properties: Object): JQuery = js.native
  /**
   * Set one or more properties for the set of matched elements.
   *
   * @param propertyName The name of the property to set.
   * @param func A function returning the value to set. Receives the index position of the element in the set and the old property value as arguments. Within the function, the keyword this refers to the current element.
   * @see { https://api.jquery.com/prop/#prop-propertyName-function}
   */
  def prop(propertyName: String, func: js.Function2[Double, js.Any, Any]): JQuery = js.native
  /**
   * Remove an attribute from each element in the set of matched elements.
   *
   * @param attributeName An attribute to remove; as of version 1.7, it can be a space-separated list of attributes.
   * @see { https://api.jquery.com/removeAttr/}
   */
  def removeAttr(attributeName: String): JQuery = js.native
  /**
   * Remove a single class, multiple classes, or all classes from each element in the set of matched elements.
   *
   * @param className One or more space-separated classes to be removed from the class attribute of each matched element.
   * @see { https://api.jquery.com/removeClass/#removeClass-className}
   */
  def removeClass(className: String): JQuery = js.native
  /**
   * Remove a single class, multiple classes, or all classes from each element in the set of matched elements.
   *
   * @param func A function returning one or more space-separated class names to be removed. Receives the index position of the element in the set and the old class value as arguments.
   * @see { https://api.jquery.com/removeClass/#removeClass-function}
   */
  def removeClass(func: js.Function2[Double, String, String]): JQuery = js.native
  /**
   * Remove a property for the set of matched elements.
   *
   * @param propertyName The name of the property to remove.
   * @see { https://api.jquery.com/removeProp/}
   */
  def removeProp(propertyName: String): JQuery = js.native
  /**
   * Add or remove one or more classes from each element in the set of matched elements, depending on either the class's presence or the value of the switch argument.
   *
   * @param className One or more class names (separated by spaces) to be toggled for each element in the matched set.
   * @param swtch A Boolean (not just truthy/falsy) value to determine whether the class should be added or removed.
   * @see { https://api.jquery.com/toggleClass/#toggleClass-className}
   */
  def toggleClass(className: String, swtch: Boolean): JQuery = js.native
  /**
   * Add or remove one or more classes from each element in the set of matched elements, depending on either the class's presence or the value of the switch argument.
   *
   * @param swtch A boolean value to determine whether the class should be added or removed.
   * @see { https://api.jquery.com/toggleClass/#toggleClass-state}
   */
  def toggleClass(swtch: Boolean): JQuery = js.native
  /**
   * Add or remove one or more classes from each element in the set of matched elements, depending on either the class's presence or the value of the switch argument.
   *
   * @param func A function that returns class names to be toggled in the class attribute of each element in the matched set. Receives the index position of the element in the set, the old class value, and the switch as arguments.
   * @param swtch A boolean value to determine whether the class should be added or removed.
   * @see { https://api.jquery.com/toggleClass/#toggleClass-function-state}
   */
  def toggleClass(func: js.Function3[Double, String, Boolean, String], swtch: Boolean): JQuery = js.native
  /**
   * Get the current value of the first element in the set of matched elements.
   * @see { https://api.jquery.com/val/#val}
   */
  def `val`(): js.Dynamic = js.native
  /**
   * Set the value of each element in the set of matched elements.
   *
   * @param value A string of text, an array of strings or number corresponding to the value of each matched element to set as selected/checked.
   * @see { https://api.jquery.com/val/#val-value}
   */
  def `val`(value: String | js.Array[String] | Double): JQuery = js.native
  /**
   * Set the value of each element in the set of matched elements.
   *
   * @param func A function returning the value to set. this is the current element. Receives the index position of the element in the set and the old value as arguments.
   * @see { https://api.jquery.com/val/#val-function}
   */
  def `val`(func: js.Function2[Double, String, String]): JQuery = js.native
  /**
   * Get the value of style properties for the first element in the set of matched elements.
   *
   * @param propertyName A CSS property.
   * @see { https://api.jquery.com/css/#css-propertyName}
   */
  def css(propertyName: String): String = js.native
  /**
   * Set one or more CSS properties for the set of matched elements.
   *
   * @param propertyName A CSS property name.
   * @param value A value to set for the property.
   * @see { https://api.jquery.com/css/#css-propertyName-value}
   */
  def css(propertyName: String, value: String | Double): JQuery = js.native
  /**
   * Set one or more CSS properties for the set of matched elements.
   *
   * @param propertyName A CSS property name.
   * @param value A function returning the value to set. this is the current element. Receives the index position of the element in the set and the old value as arguments.
   * @see { https://api.jquery.com/css/#css-propertyName-function}
   */
  def css(propertyName: String, value: js.Function2[Double, String, String | Double]): JQuery = js.native
  /**
   * Set one or more CSS properties for the set of matched elements.
   *
   * @param properties An object of property-value pairs to set.
   * @see { https://api.jquery.com/css/#css-properties}
   */
  def css(properties: Object): JQuery = js.native
  /**
   * Get the current computed height for the first element in the set of matched elements.
   * @see { https://api.jquery.com/height/#height}
   */
  def height(): Double = js.native
  /**
   * Set the CSS height of every matched element.
   *
   * @param value An integer representing the number of pixels, or an integer with an optional unit of measure appended (as a string).
   * @see { https://api.jquery.com/height/#height-value}
   */
  def height(value: Double | String): JQuery = js.native
  /**
   * Set the CSS height of every matched element.
   *
   * @param func A function returning the height to set. Receives the index position of the element in the set and the old height as arguments. Within the function, this refers to the current element in the set.
   * @see { https://api.jquery.com/height/#height-function}
   */
  def height(func: js.Function2[Double, Double, Double | String]): JQuery = js.native
  /**
   * Get the current computed height for the first element in the set of matched elements, including padding but not border.
   * @see { https://api.jquery.com/innerHeight/#innerHeight}
   */
  def innerHeight(): Double = js.native
  /**
   * Sets the inner height on elements in the set of matched elements, including padding but not border.
   *
   * @param value An integer representing the number of pixels, or an integer along with an optional unit of measure appended (as a string).
   * @see { https://api.jquery.com/innerHeight/#innerHeight-value}
   */
  def innerHeight(value: Double | String): JQuery = js.native
  /**
   * Get the current computed width for the first element in the set of matched elements, including padding but not border.
   * @see { https://api.jquery.com/innerWidth/#innerWidth}
   */
  def innerWidth(): Double = js.native
  /**
   * Sets the inner width on elements in the set of matched elements, including padding but not border.
   *
   * @param value An integer representing the number of pixels, or an integer along with an optional unit of measure appended (as a string).
   * @see { https://api.jquery.com/innerWidth/#innerWidth-value}
   */
  def innerWidth(value: Double | String): JQuery = js.native
  /**
   * Get the current coordinates of the first element in the set of matched elements, relative to the document.
   * @see { https://api.jquery.com/offset/#offset}
   */
  def offset(): JQueryCoordinates = js.native
  /**
   * An object containing the properties top and left, which are integers indicating the new top and left coordinates for the elements.
   *
   * @param coordinates An object containing the properties top and left, which are integers indicating the new top and left coordinates for the elements.
   * @see { https://api.jquery.com/offset/#offset-coordinates}
   */
  def offset(coordinates: JQueryCoordinates): JQuery = js.native
  /**
   * An object containing the properties top and left, which are integers indicating the new top and left coordinates for the elements.
   *
   * @param func A function to return the coordinates to set. Receives the index of the element in the collection as the first argument and the current coordinates as the second argument. The function should return an object with the new top and left properties.
   * @see { https://api.jquery.com/offset/#offset-function}
   */
  def offset(func: js.Function2[Double, JQueryCoordinates, JQueryCoordinates]): JQuery = js.native
  /**
   * Get the current computed height for the first element in the set of matched elements, including padding, border, and optionally margin. Returns an integer (without "px") representation of the value or null if called on an empty set of elements.
   *
   * @param includeMargin A Boolean indicating whether to include the element's margin in the calculation.
   * @see { https://api.jquery.com/outerHeight/#outerHeight-includeMargin}
   */
  def outerHeight(includeMargin: Boolean): Double = js.native
  /**
   * Sets the outer height on elements in the set of matched elements, including padding and border.
   *
   * @param value An integer representing the number of pixels, or an integer along with an optional unit of measure appended (as a string).
   * @see { https://api.jquery.com/outerHeight/#outerHeight-value}
   */
  def outerHeight(value: Double | String): JQuery = js.native
  /**
   * Get the current computed width for the first element in the set of matched elements, including padding and border.
   *
   * @param includeMargin A Boolean indicating whether to include the element's margin in the calculation.
   * @see { https://api.jquery.com/outerWidth/#outerWidth-includeMargin}
   */
  def outerWidth(includeMargin: Boolean): Double = js.native
  /**
   * Sets the outer width on elements in the set of matched elements, including padding and border.
   *
   * @param value An integer representing the number of pixels, or an integer along with an optional unit of measure appended (as a string).
   * @see { https://api.jquery.com/outerWidth/#outerWidth-value}
   */
  def outerWidth(value: Double | String): JQuery = js.native
  /**
   * Get the current coordinates of the first element in the set of matched elements, relative to the offset parent.
   * @see { https://api.jquery.com/position/}
   */
  def position(): JQueryCoordinates = js.native
  /**
   * Get the current horizontal position of the scroll bar for the first element in the set of matched elements or set the horizontal position of the scroll bar for every matched element.
   * @see { https://api.jquery.com/scrollLeft/#scrollLeft}
   */
  def scrollLeft(): Double = js.native
  /**
   * Set the current horizontal position of the scroll bar for each of the set of matched elements.
   *
   * @param value An integer indicating the new position to set the scroll bar to.
   * @see { https://api.jquery.com/scrollLeft/#scrollLeft-value}
   */
  def scrollLeft(value: Double): JQuery = js.native
  /**
   * Get the current vertical position of the scroll bar for the first element in the set of matched elements or set the vertical position of the scroll bar for every matched element.
   * @see { https://api.jquery.com/scrollTop/#scrollTop}
   */
  def scrollTop(): Double = js.native
  /**
   * Set the current vertical position of the scroll bar for each of the set of matched elements.
   *
   * @param value An integer indicating the new position to set the scroll bar to.
   * @see { https://api.jquery.com/scrollTop/#scrollTop-value}
   */
  def scrollTop(value: Double): JQuery = js.native
  /**
   * Get the current computed width for the first element in the set of matched elements.
   * @see { https://api.jquery.com/width/#width}
   */
  def width(): Double = js.native
  /**
   * Set the CSS width of each element in the set of matched elements.
   *
   * @param value An integer representing the number of pixels, or an integer along with an optional unit of measure appended (as a string).
   * @see { https://api.jquery.com/width/#width-value}
   */
  def width(value: Double | String): JQuery = js.native
  /**
   * Set the CSS width of each element in the set of matched elements.
   *
   * @param func A function returning the width to set. Receives the index position of the element in the set and the old width as arguments. Within the function, this refers to the current element in the set.
   * @see { https://api.jquery.com/width/#width-function}
   */
  def width(func: js.Function2[Double, Double, Double | String]): JQuery = js.native
  /**
   * Remove from the queue all items that have not yet been run.
   *
   * @param queueName A string containing the name of the queue. Defaults to fx, the standard effects queue.
   * @see { https://api.jquery.com/clearQueue/}
   */
  def clearQueue(queueName: String = js.native): JQuery = js.native
  /**
   * Store arbitrary data associated with the matched elements.
   *
   * @param key A string naming the piece of data to set.
   * @param value The new data value; it can be any JavaScript type including Array or Object.
   * @see { https://api.jquery.com/data/#data-key-value}
   */
  def data(key: String, value: js.Any): JQuery = js.native
  /**
   * Return the value at the named data store for the first element in the jQuery collection, as set by data(name, value) or by an HTML5 data-* attribute.
   *
   * @param key Name of the data stored.
   * @see { https://api.jquery.com/data/#data-key}
   */
  def data(key: String): js.Dynamic = js.native
  /**
   * Store arbitrary data associated with the matched elements.
   *
   * @param obj An object of key-value pairs of data to update.
   * @see { https://api.jquery.com/data/#data-obj}
   */
  def data(obj: js.Dictionary[js.Any]): JQuery = js.native
  /**
   * Return the value at the named data store for the first element in the jQuery collection, as set by data(name, value) or by an HTML5 data-* attribute.
   * @see { https://api.jquery.com/data/#data}
   */
  def data(): js.Dynamic = js.native
  /**
   * Execute the next function on the queue for the matched elements.
   *
   * @param queueName A string containing the name of the queue. Defaults to fx, the standard effects queue.
   * @see { https://api.jquery.com/dequeue/}
   */
  def dequeue(queueName: String = js.native): JQuery = js.native
  /**
   * Remove a previously-stored piece of data.
   *
   * @param name A string naming the piece of data to delete or space-separated string naming the pieces of data to delete.
   * @see { https://api.jquery.com/removeData/#removeData-name}
   */
  def removeData(name: String): JQuery = js.native
  /**
   * Remove a previously-stored piece of data.
   *
   * @param list An array of strings naming the pieces of data to delete.
   * @see { https://api.jquery.com/removeData/#removeData-list}
   */
  def removeData(list: js.Array[String]): JQuery = js.native
  /**
   * Remove all previously-stored piece of data.
   * @see { https://api.jquery.com/removeData/}
   */
  def removeData(): JQuery = js.native
  /**
   * Return a Promise object to observe when all actions of a certain type bound to the collection, queued or not, have finished.
   *
   * @param type The type of queue that needs to be observed. (default: fx)
   * @param target Object onto which the promise methods have to be attached
   * @see { https://api.jquery.com/promise/}
   */
  def promise(`type`: String = js.native, target: Object = js.native): JQueryPromise[js.Any] = js.native
  /**
   * Perform a custom animation of a set of CSS properties.
   *
   * @param properties An object of CSS properties and values that the animation will move toward.
   * @param duration A string or number determining how long the animation will run.
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/animate/#animate-properties-duration-easing-complete}
   */
  def animate(properties: Object, duration: String | Double, complete: js.Function): JQuery = js.native
  /**
   * Perform a custom animation of a set of CSS properties.
   *
   * @param properties An object of CSS properties and values that the animation will move toward.
   * @param duration A string or number determining how long the animation will run.
   * @param easing A string indicating which easing function to use for the transition. (default: swing)
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/animate/#animate-properties-duration-easing-complete}
   */
  def animate(properties: Object, duration: String | Double, easing: String, complete: js.Function): JQuery = js.native
  /**
   * Perform a custom animation of a set of CSS properties.
   *
   * @param properties An object of CSS properties and values that the animation will move toward.
   * @param options A map of additional options to pass to the method.
   * @see { https://api.jquery.com/animate/#animate-properties-options}
   */
  def animate(properties: Object, options: JQueryAnimationOptions): JQuery = js.native
  /**
   * Set a timer to delay execution of subsequent items in the queue.
   *
   * @param duration An integer indicating the number of milliseconds to delay execution of the next item in the queue.
   * @param queueName A string containing the name of the queue. Defaults to fx, the standard effects queue.
   * @see { https://api.jquery.com/delay/}
   */
  def delay(duration: Double, queueName: String = js.native): JQuery = js.native
  /**
   * Display the matched elements by fading them to opaque.
   *
   * @param duration A string or number determining how long the animation will run.
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/fadeIn/#fadeIn-duration-complete}
   */
  def fadeIn(duration: Double | String, complete: js.Function): JQuery = js.native
  /**
   * Display the matched elements by fading them to opaque.
   *
   * @param duration A string or number determining how long the animation will run.
   * @param easing A string indicating which easing function to use for the transition.
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/fadeIn/#fadeIn-duration-easing-complete}
   */
  def fadeIn(duration: Double | String, easing: String, complete: js.Function): JQuery = js.native
  /**
   * Display the matched elements by fading them to opaque.
   *
   * @param options A map of additional options to pass to the method.
   * @see { https://api.jquery.com/fadeIn/#fadeIn-options}
   */
  def fadeIn(options: JQueryAnimationOptions): JQuery = js.native
  /**
   * Hide the matched elements by fading them to transparent.
   *
   * @param duration A string or number determining how long the animation will run.
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/fadeOut/#fadeOut-duration-complete}
   */
  def fadeOut(duration: Double | String, complete: js.Function): JQuery = js.native
  /**
   * Hide the matched elements by fading them to transparent.
   *
   * @param duration A string or number determining how long the animation will run.
   * @param easing A string indicating which easing function to use for the transition.
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/fadeOut/#fadeOut-duration-easing-complete}
   */
  def fadeOut(duration: Double | String, easing: String, complete: js.Function): JQuery = js.native
  /**
   * Hide the matched elements by fading them to transparent.
   *
   * @param options A map of additional options to pass to the method.
   * @see { https://api.jquery.com/fadeOut/#fadeOut-options}
   */
  def fadeOut(options: JQueryAnimationOptions): JQuery = js.native
  /**
   * Adjust the opacity of the matched elements.
   *
   * @param duration A string or number determining how long the animation will run.
   * @param opacity A number between 0 and 1 denoting the target opacity.
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/fadeTo/#fadeTo-duration-opacity-complete}
   */
  def fadeTo(duration: String | Double, opacity: Double, complete: js.Function): JQuery = js.native
  /**
   * Adjust the opacity of the matched elements.
   *
   * @param duration A string or number determining how long the animation will run.
   * @param opacity A number between 0 and 1 denoting the target opacity.
   * @param easing A string indicating which easing function to use for the transition.
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/fadeTo/#fadeTo-duration-opacity-easing-complete}
   */
  def fadeTo(duration: String | Double, opacity: Double, easing: String, complete: js.Function): JQuery = js.native
  /**
   * Display or hide the matched elements by animating their opacity.
   *
   * @param duration A string or number determining how long the animation will run.
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/fadeToggle/#fadeToggle-duration-easing-complete}
   */
  def fadeToggle(duration: Double | String, complete: js.Function): JQuery = js.native
  /**
   * Display or hide the matched elements by animating their opacity.
   *
   * @param duration A string or number determining how long the animation will run.
   * @param easing A string indicating which easing function to use for the transition.
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/fadeToggle/#fadeToggle-duration-easing-complete}
   */
  def fadeToggle(duration: Double | String, easing: String, complete: js.Function): JQuery = js.native
  /**
   * Display or hide the matched elements by animating their opacity.
   *
   * @param options A map of additional options to pass to the method.
   * @see { https://api.jquery.com/fadeToggle/#fadeToggle-options}
   */
  def fadeToggle(options: JQueryAnimationOptions): JQuery = js.native
  /**
   * Stop the currently-running animation, remove all queued animations, and complete all animations for the matched elements.
   *
   * @param queue The name of the queue in which to stop animations.
   * @see { https://api.jquery.com/finish/}
   */
  def finish(queue: String = js.native): JQuery = js.native
  /**
   * Hide the matched elements.
   *
   * @param duration A string or number determining how long the animation will run.
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/hide/#hide}
   */
  def hide(duration: Double | String, complete: js.Function): JQuery = js.native
  /**
   * Hide the matched elements.
   *
   * @param duration A string or number determining how long the animation will run.
   * @param easing A string indicating which easing function to use for the transition.
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/hide/#hide-duration-easing-complete}
   */
  def hide(duration: Double | String, easing: String, complete: js.Function): JQuery = js.native
  /**
   * Hide the matched elements.
   *
   * @param options A map of additional options to pass to the method.
   * @see { https://api.jquery.com/hide/#hide-options}
   */
  def hide(options: JQueryAnimationOptions): JQuery = js.native
  /**
   * Display the matched elements.
   *
   * @param duration A string or number determining how long the animation will run.
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/show/#show}
   */
  def show(duration: Double | String, complete: js.Function): JQuery = js.native
  /**
   * Display the matched elements.
   *
   * @param duration A string or number determining how long the animation will run.
   * @param easing A string indicating which easing function to use for the transition.
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/show/#show-duration-easing-complete}
   */
  def show(duration: Double | String, easing: String, complete: js.Function): JQuery = js.native
  /**
   * Display the matched elements.
   *
   * @param options A map of additional options to pass to the method.
   * @see { https://api.jquery.com/show/#show-options}
   */
  def show(options: JQueryAnimationOptions): JQuery = js.native
  /**
   * Display the matched elements with a sliding motion.
   *
   * @param duration A string or number determining how long the animation will run.
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/slideDown/#slideDown-duration-complete}
   */
  def slideDown(duration: Double | String, complete: js.Function): JQuery = js.native
  /**
   * Display the matched elements with a sliding motion.
   *
   * @param duration A string or number determining how long the animation will run.
   * @param easing A string indicating which easing function to use for the transition.
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/slideDown/#slideDown-duration-easing-complete}
   */
  def slideDown(duration: Double | String, easing: String, complete: js.Function): JQuery = js.native
  /**
   * Display the matched elements with a sliding motion.
   *
   * @param options A map of additional options to pass to the method.
   * @see { https://api.jquery.com/slideDown/#slideDown-options}
   */
  def slideDown(options: JQueryAnimationOptions): JQuery = js.native
  /**
   * Display or hide the matched elements with a sliding motion.
   *
   * @param duration A string or number determining how long the animation will run.
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/slideToggle/#slideToggle-duration-complete}
   */
  def slideToggle(duration: Double | String, complete: js.Function): JQuery = js.native
  /**
   * Display or hide the matched elements with a sliding motion.
   *
   * @param duration A string or number determining how long the animation will run.
   * @param easing A string indicating which easing function to use for the transition.
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/slideToggle/#slideToggle-duration-easing-complete}
   */
  def slideToggle(duration: Double | String, easing: String, complete: js.Function): JQuery = js.native
  /**
   * Display or hide the matched elements with a sliding motion.
   *
   * @param options A map of additional options to pass to the method.
   * @see { https://api.jquery.com/slideToggle/#slideToggle-options}
   */
  def slideToggle(options: JQueryAnimationOptions): JQuery = js.native
  /**
   * Hide the matched elements with a sliding motion.
   *
   * @param duration A string or number determining how long the animation will run.
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/slideUp/#slideUp-duration-complete}
   */
  def slideUp(duration: Double | String, complete: js.Function): JQuery = js.native
  /**
   * Hide the matched elements with a sliding motion.
   *
   * @param duration A string or number determining how long the animation will run.
   * @param easing A string indicating which easing function to use for the transition.
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/slideUp/#slideUp-duration-easing-complete}
   */
  def slideUp(duration: Double | String, easing: String, complete: js.Function): JQuery = js.native
  /**
   * Hide the matched elements with a sliding motion.
   *
   * @param options A map of additional options to pass to the method.
   * @see { https://api.jquery.com/slideUp/#slideUp-options}
   */
  def slideUp(options: JQueryAnimationOptions): JQuery = js.native
  /**
   * Stop the currently-running animation on the matched elements.
   *
   * @param clearQueue A Boolean indicating whether to remove queued animation as well. Defaults to false.
   * @param jumpToEnd A Boolean indicating whether to complete the current animation immediately. Defaults to false.
   * @see { https://api.jquery.com/stop/#stop-clearQueue-jumpToEnd}
   */
  def stop(clearQueue: Boolean, jumpToEnd: Boolean): JQuery = js.native
  /**
   * Stop the currently-running animation on the matched elements.
   *
   * @param queue The name of the queue in which to stop animations.
   * @param clearQueue A Boolean indicating whether to remove queued animation as well. Defaults to false.
   * @param jumpToEnd A Boolean indicating whether to complete the current animation immediately. Defaults to false.
   * @see { https://api.jquery.com/stop/#stop-queue-clearQueue-jumpToEnd}
   */
  def stop(queue: String, clearQueue: Boolean, jumpToEnd: Boolean): JQuery = js.native
  /**
   * Display or hide the matched elements.
   *
   * @param duration A string or number determining how long the animation will run.
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/toggle/#toggle-duration-complete}
   */
  def toggle(duration: Double | String, complete: js.Function): JQuery = js.native
  /**
   * Display or hide the matched elements.
   *
   * @param duration A string or number determining how long the animation will run.
   * @param easing A string indicating which easing function to use for the transition.
   * @param complete A function to call once the animation is complete.
   * @see { https://api.jquery.com/toggle/#toggle-duration-easing-complete}
   */
  def toggle(duration: Double | String, easing: String, complete: js.Function): JQuery = js.native
  /**
   * Display or hide the matched elements.
   *
   * @param options A map of additional options to pass to the method.
   * @see { https://api.jquery.com/toggle/#toggle-options}
   */
  def toggle(options: JQueryAnimationOptions): JQuery = js.native
  /**
   * Display or hide the matched elements.
   *
   * @param showOrHide A Boolean indicating whether to show or hide the elements.
   * @see { https://api.jquery.com/toggle/#toggle-display}
   */
  def toggle(showOrHide: Boolean): JQuery = js.native
  /**
   * Attach a handler to an event for the elements.
   *
   * @param eventType A string containing one or more DOM event types, such as "click" or "submit," or custom event names.
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/bind/#bind-eventType-eventData-handler}
   */
  def bind(eventType: String, eventData: js.Any, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Attach a handler to an event for the elements.
   *
   * @param eventType A string containing one or more DOM event types, such as "click" or "submit," or custom event names.
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/bind/#bind-eventType-eventData-handler}
   */
  def bind(eventType: String, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Attach a handler to an event for the elements.
   *
   * @param eventType A string containing one or more DOM event types, such as "click" or "submit," or custom event names.
   * @param eventData An object containing data that will be passed to the event handler.
   * @param preventBubble Setting the third argument to false will attach a function that prevents the default action from occurring and stops the event from bubbling. The default is true.
   * @see { https://api.jquery.com/bind/#bind-eventType-eventData-preventBubble}
   */
  def bind(eventType: String, eventData: js.Any, preventBubble: Boolean): JQuery = js.native
  /**
   * Attach a handler to an event for the elements.
   *
   * @param eventType A string containing one or more DOM event types, such as "click" or "submit," or custom event names.
   * @param preventBubble Setting the third argument to false will attach a function that prevents the default action from occurring and stops the event from bubbling. The default is true.
   * @see { https://api.jquery.com/bind/#bind-eventType-eventData-preventBubble}
   */
  def bind(eventType: String, preventBubble: Boolean): JQuery = js.native
  /**
   * Attach a handler to an event for the elements.
   *
   * @param events An object containing one or more DOM event types and functions to execute for them.
   * @see { https://api.jquery.com/bind/#bind-events}
   */
  def bind(events: js.Any): JQuery = js.native
  /**
   * Trigger the "blur" event on an element
   * @see { https://api.jquery.com/blur/#blur}
   */
  def blur(): JQuery = js.native
  /**
   * Bind an event handler to the "blur" JavaScript event
   *
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/blur/#blur-handler}
   */
  def blur(handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "blur" JavaScript event
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/blur/#blur-eventData-handler}
   */
  def blur(eventData: js.Any, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Trigger the "change" event on an element.
   * @see { https://api.jquery.com/change/#change}
   */
  def change(): JQuery = js.native
  /**
   * Bind an event handler to the "change" JavaScript event
   *
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/change/#change-handler}
   */
  def change(handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "change" JavaScript event
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/change/#change-eventData-handler}
   */
  def change(eventData: js.Any, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Trigger the "click" event on an element.
   * @see { https://api.jquery.com/click/#click}
   */
  def click(): JQuery = js.native
  /**
   * Bind an event handler to the "click" JavaScript event
   *
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/click/#click-handler}
   */
  def click(handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "click" JavaScript event
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/click/#click-eventData-handler}
   */
  def click(eventData: js.Any, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Trigger the "contextmenu" event on an element.
   * @see { https://api.jquery.com/contextmenu/#contextmenu}
   */
  def contextmenu(): JQuery = js.native
  /**
   * Bind an event handler to the "contextmenu" JavaScript event.
   *
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/contextmenu/#contextmenu-handler}
   */
  def contextmenu(handler: js.Function1[JQueryMouseEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "contextmenu" JavaScript event.
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/contextmenu/#contextmenu-eventData-handler}
   */
  def contextmenu(eventData: Object, handler: js.Function1[JQueryMouseEventObject, Any]): JQuery = js.native
  /**
   * Trigger the "dblclick" event on an element.
   * @see { https://api.jquery.com/dblclick/#dblclick}
   */
  def dblclick(): JQuery = js.native
  /**
   * Bind an event handler to the "dblclick" JavaScript event
   *
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/dblclick/#dblclick-handler}
   */
  def dblclick(handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "dblclick" JavaScript event
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/dblclick/#dblclick-eventData-handler}
   */
  def dblclick(eventData: js.Any, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Attach a handler to one or more events for all elements that match the selector, now or in the future, based on a specific set of root elements.
   * @see { https://api.jquery.com/delegate/#delegate-selector-eventType-handler}
   */
  def delegate(selector: js.Any, eventType: String, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Attach a handler to one or more events for all elements that match the selector, now or in the future, based on a specific set of root elements.
   * @see { https://api.jquery.com/delegate/#delegate-selector-eventType-eventData-handler}
   */
  def delegate(selector: js.Any, eventType: String, eventData: js.Any, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Trigger the "focus" event on an element.
   * @see { https://api.jquery.com/focus/#focus}
   */
  def focus(): JQuery = js.native
  /**
   * Bind an event handler to the "focus" JavaScript event
   *
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/focus/#focus-handler}
   */
  def focus(handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "focus" JavaScript event
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/focus/#focus-eventData-handler}
   */
  def focus(eventData: js.Any, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Trigger the "focusin" event on an element.
   * @see { https://api.jquery.com/focusin/#focusin}
   */
  def focusin(): JQuery = js.native
  /**
   * Bind an event handler to the "focusin" JavaScript event
   *
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/focusin/#focusin-handler}
   */
  def focusin(handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "focusin" JavaScript event
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/focusin/#focusin-eventData-handler}
   */
  def focusin(eventData: Object, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Trigger the "focusout" event on an element.
   * @see { https://api.jquery.com/focusout/#focusout}
   */
  def focusout(): JQuery = js.native
  /**
   * Bind an event handler to the "focusout" JavaScript event
   *
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/focusout/#focusout-handler}
   */
  def focusout(handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "focusout" JavaScript event
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/focusout/#focusout-eventData-handler}
   */
  def focusout(eventData: Object, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Bind two handlers to the matched elements, to be executed when the mouse pointer enters and leaves the elements.
   *
   * @param handlerIn A function to execute when the mouse pointer enters the element.
   * @param handlerOut A function to execute when the mouse pointer leaves the element.
   * @see { https://api.jquery.com/hover/#hover-handlerIn-handlerOut}
   */
  def hover(handlerIn: js.Function1[JQueryEventObject, Any], handlerOut: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Bind a single handler to the matched elements, to be executed when the mouse pointer enters or leaves the elements.
   *
   * @param handlerInOut A function to execute when the mouse pointer enters or leaves the element.
   * @see { https://api.jquery.com/hover/#hover-handlerInOut}
   */
  def hover(handlerInOut: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Trigger the "keydown" event on an element.
   * @see { https://api.jquery.com/keydown/#keydown}
   */
  def keydown(): JQuery = js.native
  /**
   * Bind an event handler to the "keydown" JavaScript event
   *
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/keydown/#keydown-handler}
   */
  def keydown(handler: js.Function1[JQueryKeyEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "keydown" JavaScript event
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/keydown/#keydown-eventData-handler}
   */
  def keydown(eventData: js.Any, handler: js.Function1[JQueryKeyEventObject, Any]): JQuery = js.native
  /**
   * Trigger the "keypress" event on an element.
   * @see { https://api.jquery.com/keypress/#keypress}
   */
  def keypress(): JQuery = js.native
  /**
   * Bind an event handler to the "keypress" JavaScript event
   *
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/keypress/#keypress-handler}
   */
  def keypress(handler: js.Function1[JQueryKeyEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "keypress" JavaScript event
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/keypress/#keypress-eventData-handler}
   */
  def keypress(eventData: js.Any, handler: js.Function1[JQueryKeyEventObject, Any]): JQuery = js.native
  /**
   * Trigger the "keyup" event on an element.
   * @see { https://api.jquery.com/keyup/#keyup}
   */
  def keyup(): JQuery = js.native
  /**
   * Bind an event handler to the "keyup" JavaScript event
   *
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/keyup/#keyup-handler}
   */
  def keyup(handler: js.Function1[JQueryKeyEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "keyup" JavaScript event
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/keyup/#keyup-eventData-handler}
   */
  def keyup(eventData: js.Any, handler: js.Function1[JQueryKeyEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "load" JavaScript event.
   *
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/load/}
   */
  def load(handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "load" JavaScript event.
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/load/}
   */
  def load(eventData: js.Any, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Trigger the "mousedown" event on an element.
   * @see { https://api.jquery.com/mousedown/#mousedown}
   */
  def mousedown(): JQuery = js.native
  /**
   * Bind an event handler to the "mousedown" JavaScript event.
   *
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/mousedown/#mousedown-handler}
   */
  def mousedown(handler: js.Function1[JQueryMouseEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "mousedown" JavaScript event.
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/mousedown/#mousedown-eventData-handler}
   */
  def mousedown(eventData: Object, handler: js.Function1[JQueryMouseEventObject, Any]): JQuery = js.native
  /**
   * Trigger the "mouseenter" event on an element.
   * @see { https://api.jquery.com/mouseenter/#mouseenter}
   */
  def mouseenter(): JQuery = js.native
  /**
   * Bind an event handler to be fired when the mouse enters an element.
   *
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/mouseenter/#mouseenter-handler}
   */
  def mouseenter(handler: js.Function1[JQueryMouseEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to be fired when the mouse enters an element.
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/mouseenter/#mouseenter-eventData-handler}
   */
  def mouseenter(eventData: Object, handler: js.Function1[JQueryMouseEventObject, Any]): JQuery = js.native
  /**
   * Trigger the "mouseleave" event on an element.
   * @see { https://api.jquery.com/mouseleave/#mouseleave}
   */
  def mouseleave(): JQuery = js.native
  /**
   * Bind an event handler to be fired when the mouse leaves an element.
   *
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/mouseleave/#mouseleave-handler}
   */
  def mouseleave(handler: js.Function1[JQueryMouseEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to be fired when the mouse leaves an element.
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/mouseleave/#mouseleave-eventData-handler}
   */
  def mouseleave(eventData: Object, handler: js.Function1[JQueryMouseEventObject, Any]): JQuery = js.native
  /**
   * Trigger the "mousemove" event on an element.
   * @see { https://api.jquery.com/mousemove/#mousemove}
   */
  def mousemove(): JQuery = js.native
  /**
   * Bind an event handler to the "mousemove" JavaScript event.
   *
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/mousemove/#mousemove-handler}
   */
  def mousemove(handler: js.Function1[JQueryMouseEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "mousemove" JavaScript event.
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/mousemove/#mousemove-eventData-handler}
   */
  def mousemove(eventData: Object, handler: js.Function1[JQueryMouseEventObject, Any]): JQuery = js.native
  /**
   * Trigger the "mouseout" event on an element.
   * @see { https://api.jquery.com/mouseout/#mouseout}
   */
  def mouseout(): JQuery = js.native
  /**
   * Bind an event handler to the "mouseout" JavaScript event.
   *
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/mouseout/#mouseout-handler}
   */
  def mouseout(handler: js.Function1[JQueryMouseEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "mouseout" JavaScript event.
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/mouseout/#mouseout-eventData-handler}
   */
  def mouseout(eventData: Object, handler: js.Function1[JQueryMouseEventObject, Any]): JQuery = js.native
  /**
   * Trigger the "mouseover" event on an element.
   * @see { https://api.jquery.com/mouseover/#mouseover}
   */
  def mouseover(): JQuery = js.native
  /**
   * Bind an event handler to the "mouseover" JavaScript event.
   *
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/mouseover/#mouseover-handler}
   */
  def mouseover(handler: js.Function1[JQueryMouseEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "mouseover" JavaScript event.
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/mouseover/#mouseover-eventData-handler}
   */
  def mouseover(eventData: Object, handler: js.Function1[JQueryMouseEventObject, Any]): JQuery = js.native
  /**
   * Trigger the "mouseup" event on an element.
   * @see { https://api.jquery.com/mouseup/#mouseup}
   */
  def mouseup(): JQuery = js.native
  /**
   * Bind an event handler to the "mouseup" JavaScript event.
   *
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/mouseup/#mouseup-handler}
   */
  def mouseup(handler: js.Function1[JQueryMouseEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "mouseup" JavaScript event.
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/mouseup/#mouseup-eventData-handler}
   */
  def mouseup(eventData: Object, handler: js.Function1[JQueryMouseEventObject, Any]): JQuery = js.native
  /**
   * Remove an event handler.
   * @see { https://api.jquery.com/off/#off}
   */
  def off(): JQuery = js.native
  /**
   * Remove an event handler.
   *
   * @param events One or more space-separated event types and optional namespaces, or just namespaces, such as "click", "keydown.myPlugin", or ".myPlugin".
   * @param selector A selector which should match the one originally passed to .on() when attaching event handlers.
   * @param handler A handler function previously attached for the event(s), or the special value false.
   * @see { https://api.jquery.com/off/#off-events-selector-handler}
   */
  def off(events: String, selector: String, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Remove an event handler.
   *
   * @param events One or more space-separated event types and optional namespaces, or just namespaces, such as "click", "keydown.myPlugin", or ".myPlugin".
   * @param handler A handler function previously attached for the event(s), or the special value false. Takes handler with extra args that can be attached with on().
   * @see { https://api.jquery.com/off/#off-events-selector-handler}
   */
  def off(events: String, handler: js.Function): JQuery = js.native
  /**
   * Remove an event handler.
   *
   * @param events One or more space-separated event types and optional namespaces, or just namespaces, such as "click", "keydown.myPlugin", or ".myPlugin".
   * @param handler A handler function previously attached for the event(s), or the special value false.
   * @see { https://api.jquery.com/off/#off-events-selector-handler}
   */
  def off(events: String, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Remove an event handler.
   *
   * @param events An object where the string keys represent one or more space-separated event types and optional namespaces, and the values represent handler functions previously attached for the event(s).
   * @param selector A selector which should match the one originally passed to .on() when attaching event handlers.
   * @see { https://api.jquery.com/off/#off-events-selector}
   */
  def off(events: js.Dictionary[js.Any], selector: String): JQuery = js.native
  /**
   * Attach an event handler function for one or more events to the selected elements.
   *
   * @param events One or more space-separated event types and optional namespaces, such as "click" or "keydown.myPlugin".
   * @param handler A function to execute when the event is triggered. The value false is also allowed as a shorthand for a function that simply does return false. Rest parameter args is for optional parameters passed to jQuery.trigger(). Note that the actual parameters on the event handler function must be marked as optional (? syntax).
   * @see { https://api.jquery.com/on/#on-events-selector-data-handler}
   */
  def on(events: String, handler: js.Function): JQuery = js.native
  /**
   * Attach an event handler function for one or more events to the selected elements.
   *
   * @param events One or more space-separated event types and optional namespaces, such as "click" or "keydown.myPlugin".
   * @param data Data to be passed to the handler in event.data when an event is triggered.
   * @param handler A function to execute when the event is triggered. The value false is also allowed as a shorthand for a function that simply does return false.
   * @see { https://api.jquery.com/on/#on-events-selector-data-handler}
   */
  def on(events: String, data: js.Any, handler: js.Function): JQuery = js.native
  /**
   * Attach an event handler function for one or more events to the selected elements.
   *
   * @param events One or more space-separated event types and optional namespaces, such as "click" or "keydown.myPlugin".
   * @param selector A selector string to filter the descendants of the selected elements that trigger the event. If the selector is null or omitted, the event is always triggered when it reaches the selected element.
   * @param handler A function to execute when the event is triggered. The value false is also allowed as a shorthand for a function that simply does return false.
   * @see { https://api.jquery.com/on/#on-events-selector-data-handler}
   */
  def on(events: String, selector: String, handler: js.Function): JQuery = js.native
  /**
   * Attach an event handler function for one or more events to the selected elements.
   *
   * @param events One or more space-separated event types and optional namespaces, such as "click" or "keydown.myPlugin".
   * @param selector A selector string to filter the descendants of the selected elements that trigger the event. If the selector is null or omitted, the event is always triggered when it reaches the selected element.
   * @param data Data to be passed to the handler in event.data when an event is triggered.
   * @param handler A function to execute when the event is triggered. The value false is also allowed as a shorthand for a function that simply does return false.
   * @see { https://api.jquery.com/on/#on-events-selector-data-handler}
   */
  def on(events: String, selector: String, data: js.Any, handler: js.Function): JQuery = js.native
  /**
   * Attach an event handler function for one or more events to the selected elements.
   *
   * @param events An object in which the string keys represent one or more space-separated event types and optional namespaces, and the values represent a handler function to be called for the event(s).
   * @param selector A selector string to filter the descendants of the selected elements that will call the handler. If the selector is null or omitted, the handler is always called when it reaches the selected element.
   * @param data Data to be passed to the handler in event.data when an event occurs.
   * @see { https://api.jquery.com/on/#on-events-selector-data}
   */
  def on(events: js.Dictionary[js.Any], selector: String, data: js.Any): JQuery = js.native
  /**
   * Attach an event handler function for one or more events to the selected elements.
   *
   * @param events An object in which the string keys represent one or more space-separated event types and optional namespaces, and the values represent a handler function to be called for the event(s).
   * @param data Data to be passed to the handler in event.data when an event occurs.
   * @see { https://api.jquery.com/on/#on-events-selector-data}
   */
  def on(events: js.Dictionary[js.Any], data: js.Any): JQuery = js.native
  /**
   * Attach a handler to an event for the elements. The handler is executed at most once per element per event type.
   *
   * @param events A string containing one or more JavaScript event types, such as "click" or "submit," or custom event names.
   * @param handler A function to execute at the time the event is triggered.
   * @see { https://api.jquery.com/one/#one-events-data-handler}
   */
  def one(events: String, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Attach a handler to an event for the elements. The handler is executed at most once per element per event type.
   *
   * @param events A string containing one or more JavaScript event types, such as "click" or "submit," or custom event names.
   * @param data An object containing data that will be passed to the event handler.
   * @param handler A function to execute at the time the event is triggered.
   * @see { https://api.jquery.com/one/#one-events-data-handler}
   */
  def one(events: String, data: Object, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Attach a handler to an event for the elements. The handler is executed at most once per element per event type.
   *
   * @param events One or more space-separated event types and optional namespaces, such as "click" or "keydown.myPlugin".
   * @param selector A selector string to filter the descendants of the selected elements that trigger the event. If the selector is null or omitted, the event is always triggered when it reaches the selected element.
   * @param handler A function to execute when the event is triggered. The value false is also allowed as a shorthand for a function that simply does return false.
   * @see { https://api.jquery.com/one/#one-events-selector-data-handler}
   */
  def one(events: String, selector: String, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Attach a handler to an event for the elements. The handler is executed at most once per element per event type.
   *
   * @param events One or more space-separated event types and optional namespaces, such as "click" or "keydown.myPlugin".
   * @param selector A selector string to filter the descendants of the selected elements that trigger the event. If the selector is null or omitted, the event is always triggered when it reaches the selected element.
   * @param data Data to be passed to the handler in event.data when an event is triggered.
   * @param handler A function to execute when the event is triggered. The value false is also allowed as a shorthand for a function that simply does return false.
   * @see { https://api.jquery.com/one/#one-events-selector-data-handler}
   */
  def one(events: String, selector: String, data: js.Any, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Attach a handler to an event for the elements. The handler is executed at most once per element per event type.
   *
   * @param events An object in which the string keys represent one or more space-separated event types and optional namespaces, and the values represent a handler function to be called for the event(s).
   * @param selector A selector string to filter the descendants of the selected elements that will call the handler. If the selector is null or omitted, the handler is always called when it reaches the selected element.
   * @param data Data to be passed to the handler in event.data when an event occurs.
   * @see { https://api.jquery.com/one/#one-events-selector-data}
   */
  def one(events: js.Dictionary[js.Any], selector: String, data: js.Any): JQuery = js.native
  /**
   * Attach a handler to an event for the elements. The handler is executed at most once per element per event type.
   *
   * @param events An object in which the string keys represent one or more space-separated event types and optional namespaces, and the values represent a handler function to be called for the event(s).
   * @param data Data to be passed to the handler in event.data when an event occurs.
   * @see { https://api.jquery.com/one/#one-events-selector-data}
   */
  def one(events: js.Dictionary[js.Any], data: js.Any): JQuery = js.native
  /**
   * Specify a function to execute when the DOM is fully loaded.
   *
   * @param handler A function to execute after the DOM is ready.
   * @see { https://api.jquery.com/ready/}
   */
  def ready(handler: js.Function1[JQueryStatic, Any]): JQuery = js.native
  /**
   * Trigger the "resize" event on an element.
   * @see { https://api.jquery.com/resize/#resize}
   */
  def resize(): JQuery = js.native
  /**
   * Bind an event handler to the "resize" JavaScript event.
   *
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/resize/#resize-handler}
   */
  def resize(handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "resize" JavaScript event.
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/resize/#resize-eventData-handler}
   */
  def resize(eventData: Object, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Trigger the "scroll" event on an element.
   * @see { https://api.jquery.com/scroll/#scroll}
   */
  def scroll(): JQuery = js.native
  /**
   * Bind an event handler to the "scroll" JavaScript event.
   *
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/scroll/#scroll-handler}
   */
  def scroll(handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "scroll" JavaScript event.
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/scroll/#scroll-eventData-handler}
   */
  def scroll(eventData: Object, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Trigger the "select" event on an element.
   * @see { https://api.jquery.com/select/#select}
   */
  def select(): JQuery = js.native
  /**
   * Bind an event handler to the "select" JavaScript event.
   *
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/select/#select-handler}
   */
  def select(handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "select" JavaScript event.
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/select/#select-eventData-handler}
   */
  def select(eventData: Object, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Trigger the "submit" event on an element.
   * @see { https://api.jquery.com/submit/#submit}
   */
  def submit(): JQuery = js.native
  /**
   * Bind an event handler to the "submit" JavaScript event
   *
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/submit/#submit-handler}
   */
  def submit(handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "submit" JavaScript event
   *
   * @param eventData An object containing data that will be passed to the event handler.
   * @param handler A function to execute each time the event is triggered.
   * @see { https://api.jquery.com/submit/#submit-eventData-handler}
   */
  def submit(eventData: js.Any, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Execute all handlers and behaviors attached to the matched elements for the given event type.
   *
   * @param eventType A string containing a JavaScript event type, such as click or submit.
   * @param extraParameters Additional parameters to pass along to the event handler.
   * @see { https://api.jquery.com/trigger/#trigger-eventType-extraParameters}
   */
  def trigger(eventType: String, extraParameters: js.Array[js.Any] | Object): JQuery = js.native
  /**
   * Execute all handlers and behaviors attached to the matched elements for the given event type.
   *
   * @param event A jQuery.Event object.
   * @param extraParameters Additional parameters to pass along to the event handler.
   * @see { https://api.jquery.com/trigger/#trigger-event-extraParameters}
   */
  def trigger(event: JQueryEventObject, extraParameters: js.Array[js.Any] | Object): JQuery = js.native
  /**
   * Execute all handlers attached to an element for an event.
   *
   * @param eventType A string containing a JavaScript event type, such as click or submit.
   * @param extraParameters An array of additional parameters to pass along to the event handler.
   * @see { https://api.jquery.com/triggerHandler/#triggerHandler-eventType-extraParameters}
   */
  def triggerHandler(eventType: String, extraParameters: js.Any*): Object = js.native
  /**
   * Execute all handlers attached to an element for an event.
   *
   * @param event A jQuery.Event object.
   * @param extraParameters An array of additional parameters to pass along to the event handler.
   * @see { https://api.jquery.com/triggerHandler/#triggerHandler-event-extraParameters}
   */
  def triggerHandler(event: JQueryEventObject, extraParameters: js.Any*): Object = js.native
  /**
   * Remove a previously-attached event handler from the elements.
   *
   * @param eventType A string containing a JavaScript event type, such as click or submit.
   * @param handler The function that is to be no longer executed.
   * @see { https://api.jquery.com/unbind/#unbind-eventType-handler}
   */
  def unbind(eventType: String, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Remove a previously-attached event handler from the elements.
   *
   * @param eventType A string containing a JavaScript event type, such as click or submit.
   * @param fls Unbinds the corresponding 'return false' function that was bound using .bind( eventType, false ).
   * @see { https://api.jquery.com/unbind/#unbind-eventType-false}
   */
  def unbind(eventType: String, fls: Boolean): JQuery = js.native
  /**
   * Remove a previously-attached event handler from the elements.
   *
   * @param evt A JavaScript event object as passed to an event handler.
   * @see { https://api.jquery.com/unbind/#unbind-event}
   */
  def unbind(evt: js.Any): JQuery = js.native
  /**
   * Remove a handler from the event for all elements which match the current selector, based upon a specific set of root elements.
   * @see { https://api.jquery.com/undelegate/#undelegate}
   */
  def undelegate(): JQuery = js.native
  /**
   * Remove a handler from the event for all elements which match the current selector, based upon a specific set of root elements.
   *
   * @param selector A selector which will be used to filter the event results.
   * @param eventType A string containing a JavaScript event type, such as "click" or "keydown"
   * @param handler A function to execute at the time the event is triggered.
   * @see { https://api.jquery.com/undelegate/#undelegate-selector-eventType}
   */
  def undelegate(selector: String, eventType: String, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Remove a handler from the event for all elements which match the current selector, based upon a specific set of root elements.
   *
   * @param selector A selector which will be used to filter the event results.
   * @param events An object of one or more event types and previously bound functions to unbind from them.
   * @see { https://api.jquery.com/undelegate/#undelegate-selector-events}
   */
  def undelegate(selector: String, events: Object): JQuery = js.native
  /**
   * Remove a handler from the event for all elements which match the current selector, based upon a specific set of root elements.
   *
   * @param namespace A string containing a namespace to unbind all events from.
   * @see { https://api.jquery.com/undelegate/#undelegate-namespace}
   */
  def undelegate(namespace: String): JQuery = js.native
  /**
   * Bind an event handler to the "unload" JavaScript event. (DEPRECATED from v1.8)
   *
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/unload/#unload-handler}
   */
  def unload(handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "unload" JavaScript event. (DEPRECATED from v1.8)
   *
   * @param eventData A plain object of data that will be passed to the event handler.
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/unload/#unload-eventData-handler}
   */
  def unload(eventData: js.Any, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * The DOM node context originally passed to jQuery(); if none was passed then context will likely be the document. (DEPRECATED from v1.10)
   * @see { https://api.jquery.com/context/}
   */
  var context: Element = js.native
  var jquery: String = js.native
  /**
   * Bind an event handler to the "error" JavaScript event. (DEPRECATED from v1.8)
   *
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/error/#error-handler}
   */
  def error(handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Bind an event handler to the "error" JavaScript event. (DEPRECATED from v1.8)
   *
   * @param eventData A plain object of data that will be passed to the event handler.
   * @param handler A function to execute when the event is triggered.
   * @see { https://api.jquery.com/error/#error-eventData-handler}
   */
  def error(eventData: js.Any, handler: js.Function1[JQueryEventObject, Any]): JQuery = js.native
  /**
   * Add a collection of DOM elements onto the jQuery stack.
   *
   * @param elements An array of elements to push onto the stack and make into a new jQuery object.
   * @see { https://api.jquery.com/pushStack/#pushStack-elements}
   */
  def pushStack(elements: js.Array[js.Any]): JQuery = js.native
  /**
   * Add a collection of DOM elements onto the jQuery stack.
   *
   * @param elements An array of elements to push onto the stack and make into a new jQuery object.
   * @param name The name of a jQuery method that generated the array of elements.
   * @param arguments The arguments that were passed in to the jQuery method (for serialization).
   * @see { https://api.jquery.com/pushStack/#pushStack-elements-name-arguments}
   */
  def pushStack(elements: js.Array[js.Any], name: String, arguments: js.Array[js.Any]): JQuery = js.native
  /**
   * Insert content, specified by the parameter, after each element in the set of matched elements.
   *
   * @param content1 HTML string, DOM element, DocumentFragment, array of elements, or jQuery object to insert after each element in the set of matched elements.
   * @param content2 One or more additional DOM elements, arrays of elements, HTML strings, or jQuery objects to insert after each element in the set of matched elements.
   * @see { https://api.jquery.com/after/#after-content-content}
   */
  def after(content1: JQuery | js.Array[js.Any] | Element | DocumentFragment | Text | String, content2: js.Any*): JQuery = js.native
  /**
   * Insert content, specified by the parameter, after each element in the set of matched elements.
   *
   * @param func A function that returns an HTML string, DOM element(s), or jQuery object to insert after each element in the set of matched elements. Receives the index position of the element in the set as an argument. Within the function, this refers to the current element in the set.
   * @see { https://api.jquery.com/after/#after-function}
   */
  def after(func: js.Function2[Double, String, String | Element | JQuery]): JQuery = js.native
  /**
   * Insert content, specified by the parameter, to the end of each element in the set of matched elements.
   *
   * @param content1 DOM element, DocumentFragment, array of elements, HTML string, or jQuery object to insert at the end of each element in the set of matched elements.
   * @param content2 One or more additional DOM elements, arrays of elements, HTML strings, or jQuery objects to insert at the end of each element in the set of matched elements.
   * @see { https://api.jquery.com/append/#append-content-content}
   */
  def append(content1: JQuery | js.Array[js.Any] | Element | DocumentFragment | Text | String, content2: js.Any*): JQuery = js.native
  /**
   * Insert content, specified by the parameter, to the end of each element in the set of matched elements.
   *
   * @param func A function that returns an HTML string, DOM element(s), or jQuery object to insert at the end of each element in the set of matched elements. Receives the index position of the element in the set and the old HTML value of the element as arguments. Within the function, this refers to the current element in the set.
   * @see { https://api.jquery.com/append/#append-function}
   */
  def append(func: js.Function2[Double, String, String | Element | JQuery]): JQuery = js.native
  /**
   * Insert every element in the set of matched elements to the end of the target.
   *
   * @param target A selector, element, HTML string, array of elements, or jQuery object; the matched set of elements will be inserted at the end of the element(s) specified by this parameter.
   * @see { https://api.jquery.com/appendTo/}
   */
  def appendTo(target: JQuery | js.Array[js.Any] | Element | String): JQuery = js.native
  /**
   * Insert content, specified by the parameter, before each element in the set of matched elements.
   *
   * @param content1 HTML string, DOM element, DocumentFragment, array of elements, or jQuery object to insert before each element in the set of matched elements.
   * @param content2 One or more additional DOM elements, arrays of elements, HTML strings, or jQuery objects to insert before each element in the set of matched elements.
   * @see { https://api.jquery.com/before/#before-content-content}
   */
  def before(content1: JQuery | js.Array[js.Any] | Element | DocumentFragment | Text | String, content2: js.Any*): JQuery = js.native
  /**
   * Insert content, specified by the parameter, before each element in the set of matched elements.
   *
   * @param func A function that returns an HTML string, DOM element(s), or jQuery object to insert before each element in the set of matched elements. Receives the index position of the element in the set as an argument. Within the function, this refers to the current element in the set.
   * @see { https://api.jquery.com/before/#before-function}
   */
  def before(func: js.Function2[Double, String, String | Element | JQuery]): JQuery = js.native
  /**
   * Create a deep copy of the set of matched elements.
   *
   * @param withDataAndEvents A Boolean indicating whether event handlers and data should be copied along with the elements. The default value is false.
   * @param deepWithDataAndEvents A Boolean indicating whether event handlers and data for all children of the cloned element should be copied. By default its value matches the first argument's value (which defaults to false).
   * @see { https://api.jquery.com/clone/}
   */
  def clone(withDataAndEvents: Boolean = js.native, deepWithDataAndEvents: Boolean = js.native): JQuery = js.native
  /**
   * Remove the set of matched elements from the DOM.
   *
   * @param selector A selector expression that filters the set of matched elements to be removed.
   * @see { https://api.jquery.com/detach/}
   */
  def detach(selector: String = js.native): JQuery = js.native
  /**
   * Remove all child nodes of the set of matched elements from the DOM.
   * @see { https://api.jquery.com/empty/}
   */
  def empty(): JQuery = js.native
  /**
   * Insert every element in the set of matched elements after the target.
   *
   * @param target A selector, element, array of elements, HTML string, or jQuery object; the matched set of elements will be inserted after the element(s) specified by this parameter.
   * @see { https://api.jquery.com/insertAfter/}
   */
  def insertAfter(target: JQuery | js.Array[js.Any] | Element | Text | String): JQuery = js.native
  /**
   * Insert every element in the set of matched elements before the target.
   *
   * @param target A selector, element, array of elements, HTML string, or jQuery object; the matched set of elements will be inserted before the element(s) specified by this parameter.
   * @see { https://api.jquery.com/insertBefore/}
   */
  def insertBefore(target: JQuery | js.Array[js.Any] | Element | Text | String): JQuery = js.native
  /**
   * Insert content, specified by the parameter, to the beginning of each element in the set of matched elements.
   *
   * @param content1 DOM element, DocumentFragment, array of elements, HTML string, or jQuery object to insert at the beginning of each element in the set of matched elements.
   * @param content2 One or more additional DOM elements, arrays of elements, HTML strings, or jQuery objects to insert at the beginning of each element in the set of matched elements.
   * @see { https://api.jquery.com/prepend/#prepend-content-content}
   */
  def prepend(content1: JQuery | js.Array[js.Any] | Element | DocumentFragment | Text | String, content2: js.Any*): JQuery = js.native
  /**
   * Insert content, specified by the parameter, to the beginning of each element in the set of matched elements.
   *
   * @param func A function that returns an HTML string, DOM element(s), or jQuery object to insert at the beginning of each element in the set of matched elements. Receives the index position of the element in the set and the old HTML value of the element as arguments. Within the function, this refers to the current element in the set.
   * @see { https://api.jquery.com/prepend/#prepend-function}
   */
  def prepend(func: js.Function2[Double, String, String | Element | JQuery]): JQuery = js.native
  /**
   * Insert every element in the set of matched elements to the beginning of the target.
   *
   * @param target A selector, element, HTML string, array of elements, or jQuery object; the matched set of elements will be inserted at the beginning of the element(s) specified by this parameter.
   * @see { https://api.jquery.com/prependTo/}
   */
  def prependTo(target: JQuery | js.Array[js.Any] | Element | String): JQuery = js.native
  /**
   * Remove the set of matched elements from the DOM.
   *
   * @param selector A selector expression that filters the set of matched elements to be removed.
   * @see { https://api.jquery.com/remove/}
   */
  def remove(selector: String = js.native): JQuery = js.native
  /**
   * Replace each target element with the set of matched elements.
   *
   * @param target A selector string, jQuery object, DOM element, or array of elements indicating which element(s) to replace.
   * @see { https://api.jquery.com/replaceAll/}
   */
  def replaceAll(target: JQuery | js.Array[js.Any] | Element | String): JQuery = js.native
  /**
   * Replace each element in the set of matched elements with the provided new content and return the set of elements that was removed.
   *
   * @param newContent The content to insert. May be an HTML string, DOM element, array of DOM elements, or jQuery object.
   * @see { https://api.jquery.com/replaceWith/#replaceWith-newContent}
   */
  def replaceWith(newContent: JQuery | js.Array[js.Any] | Element | Text | String): JQuery = js.native
  /**
   * Replace each element in the set of matched elements with the provided new content and return the set of elements that was removed.
   *
   * @param func A function that returns content with which to replace the set of matched elements.
   * @see { https://api.jquery.com/replaceWith/#replaceWith-function}
   */
  def replaceWith(func: js.Function0[Element | JQuery]): JQuery = js.native
  /**
   * Get the combined text contents of each element in the set of matched elements, including their descendants.
   * @see { https://api.jquery.com/text/#text}
   */
  def text(): String = js.native
  /**
   * Set the content of each element in the set of matched elements to the specified text.
   *
   * @param text The text to set as the content of each matched element. When Number or Boolean is supplied, it will be converted to a String representation.
   * @see { https://api.jquery.com/text/#text-text}
   */
  def text(text: String | Double | Boolean): JQuery = js.native
  /**
   * Set the content of each element in the set of matched elements to the specified text.
   *
   * @param func A function returning the text content to set. Receives the index position of the element in the set and the old text value as arguments.
   * @see { https://api.jquery.com/text/#text-function}
   */
  def text(func: js.Function2[Double, String, String]): JQuery = js.native
  /**
   * Retrieve all the elements contained in the jQuery set, as an array.
   * @see { https://api.jquery.com/toArray/}
   */
  def toArray(): js.Array[HTMLElement] = js.native
  /**
   * Remove the parents of the set of matched elements from the DOM, leaving the matched elements in their place.
   * @see { https://api.jquery.com/unwrap/}
   */
  def unwrap(): JQuery = js.native
  /**
   * Wrap an HTML structure around each element in the set of matched elements.
   *
   * @param wrappingElement A selector, element, HTML string, or jQuery object specifying the structure to wrap around the matched elements.
   * @see { https://api.jquery.com/wrap/#wrap-wrappingElement}
   */
  def wrap(wrappingElement: JQuery | Element | String): JQuery = js.native
  /**
   * Wrap an HTML structure around each element in the set of matched elements.
   *
   * @param func A callback function returning the HTML content or jQuery object to wrap around the matched elements. Receives the index position of the element in the set as an argument. Within the function, this refers to the current element in the set.
   * @see { https://api.jquery.com/wrap/#wrap-function}
   */
  def wrap(func: js.Function1[Double, String | JQuery]): JQuery = js.native
  /**
   * Wrap an HTML structure around all elements in the set of matched elements.
   *
   * @param wrappingElement A selector, element, HTML string, or jQuery object specifying the structure to wrap around the matched elements.
   * @see { https://api.jquery.com/wrapAll/#wrapAll-wrappingElement}
   */
  def wrapAll(wrappingElement: JQuery | Element | String): JQuery = js.native
  /**
   * Wrap an HTML structure around all elements in the set of matched elements.
   *
   * @param func A callback function returning the HTML content or jQuery object to wrap around all the matched elements. Within the function, this refers to the first element in the set.
   * @see { https://api.jquery.com/wrapAll/#wrapAll-function}
   */
  def wrapAll(func: js.Function1[Double, String]): JQuery = js.native
  /**
   * Wrap an HTML structure around the content of each element in the set of matched elements.
   *
   * @param wrappingElement An HTML snippet, selector expression, jQuery object, or DOM element specifying the structure to wrap around the content of the matched elements.
   * @see { https://api.jquery.com/wrapInner/#wrapInner-wrappingElement}
   */
  def wrapInner(wrappingElement: JQuery | Element | String): JQuery = js.native
  /**
   * Wrap an HTML structure around the content of each element in the set of matched elements.
   *
   * @param func A callback function which generates a structure to wrap around the content of the matched elements. Receives the index position of the element in the set as an argument. Within the function, this refers to the current element in the set.
   * @see { https://api.jquery.com/wrapInner/#wrapInner-function}
   */
  def wrapInner(func: js.Function1[Double, String]): JQuery = js.native
  /**
   * Iterate over a jQuery object, executing a function for each matched element.
   *
   * @param func A function to execute for each matched element.
   * @see { https://api.jquery.com/each/}
   */
  def each(func: js.Function2[Double, Element, Any]): JQuery = js.native
  /**
   * Retrieve one of the elements matched by the jQuery object.
   *
   * @param index A zero-based integer indicating which element to retrieve.
   * @see { https://api.jquery.com/get/#get-index}
   */
  def get(index: Double): HTMLElement = js.native
  /**
   * Retrieve the elements matched by the jQuery object.
   * @see { https://api.jquery.com/get/#get}
   */
  def get(): js.Array[HTMLElement] = js.native
  /**
   * Search for a given element from among the matched elements.
   * @see { https://api.jquery.com/index/#index}
   */
  def index(): Double = js.native
  /**
   * Search for a given element from among the matched elements.
   *
   * @param selector A selector representing a jQuery collection in which to look for an element.
   * @see { https://api.jquery.com/index/#index-selector}
   */
  def index(selector: String | JQuery | Element): Double = js.native
  /**
   * The number of elements in the jQuery object.
   * @see { https://api.jquery.com/length/}
   */
  var length: Double = js.native
  /**
   * A selector representing selector passed to jQuery(), if any, when creating the original set.
   * version deprecated: 1.7, removed: 1.9
   * @see { https://api.jquery.com/selector/}
   */
  var selector: String = js.native
  @js.annotation.JSBracketAccess
  def apply(index: String): js.Any = js.native
  @js.annotation.JSBracketAccess
  def update(index: String, v: js.Any): Unit = js.native
  @js.annotation.JSBracketAccess
  def apply(index: Double): HTMLElement = js.native
  @js.annotation.JSBracketAccess
  def update(index: Double, v: HTMLElement): Unit = js.native
  /**
   * Add elements to the set of matched elements.
   *
   * @param selector A string representing a selector expression to find additional elements to add to the set of matched elements.
   * @param context The point in the document at which the selector should begin matching; similar to the context argument of the $(selector, context) method.
   * @see { https://api.jquery.com/add/#add-selector}
   */
  def add(selector: String, context: Element): JQuery = js.native
  /**
   * Add elements to the set of matched elements.
   *
   * @param elements One or more elements to add to the set of matched elements.
   * @see { https://api.jquery.com/add/#add-elements}
   */
  def add(elements: Element*): JQuery = js.native
  /**
   * Add elements to the set of matched elements.
   *
   * @param html An HTML fragment to add to the set of matched elements.
   * @see { https://api.jquery.com/add/#add-html}
   */
  def add(html: String): JQuery = js.native
  /**
   * Add elements to the set of matched elements.
   *
   * @param obj An existing jQuery object to add to the set of matched elements.
   * @see { https://api.jquery.com/add/#add-selection}
   */
  def add(obj: JQuery): JQuery = js.native
  /**
   * Get the children of each element in the set of matched elements, optionally filtered by a selector.
   *
   * @param selector A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/children/}
   */
  def children(selector: String = js.native): JQuery = js.native
  /**
   * For each element in the set, get the first element that matches the selector by testing the element itself and traversing up through its ancestors in the DOM tree.
   *
   * @param selector A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/closest/#closest-selector}
   */
  def closest(selector: String): JQuery = js.native
  /**
   * For each element in the set, get the first element that matches the selector by testing the element itself and traversing up through its ancestors in the DOM tree.
   *
   * @param selector A string containing a selector expression to match elements against.
   * @param context A DOM element within which a matching element may be found. If no context is passed in then the context of the jQuery set will be used instead.
   * @see { https://api.jquery.com/closest/#closest-selector-context}
   */
  def closest(selector: String, context: Element): JQuery = js.native
  /**
   * For each element in the set, get the first element that matches the selector by testing the element itself and traversing up through its ancestors in the DOM tree.
   *
   * @param obj A jQuery object to match elements against.
   * @see { https://api.jquery.com/closest/#closest-selection}
   */
  def closest(obj: JQuery): JQuery = js.native
  /**
   * For each element in the set, get the first element that matches the selector by testing the element itself and traversing up through its ancestors in the DOM tree.
   *
   * @param element An element to match elements against.
   * @see { https://api.jquery.com/closest/#closest-element}
   */
  def closest(element: Element): JQuery = js.native
  /**
   * Get an array of all the elements and selectors matched against the current element up through the DOM tree.
   *
   * @param selectors An array or string containing a selector expression to match elements against (can also be a jQuery object).
   * @param context A DOM element within which a matching element may be found. If no context is passed in then the context of the jQuery set will be used instead.
   * @see { https://api.jquery.com/closest/#closest-selectors-context}
   */
  def closest(selectors: js.Any, context: Element): js.Array[js.Any] = js.native
  /**
   * Get the children of each element in the set of matched elements, including text and comment nodes.
   * @see { https://api.jquery.com/contents/}
   */
  def contents(): JQuery = js.native
  /**
   * End the most recent filtering operation in the current chain and return the set of matched elements to its previous state.
   * @see { https://api.jquery.com/end/}
   */
  def end(): JQuery = js.native
  /**
   * Reduce the set of matched elements to the one at the specified index.
   *
   * @param index An integer indicating the 0-based position of the element. OR An integer indicating the position of the element, counting backwards from the last element in the set.
   * @see { https://api.jquery.com/eq/}
   */
  def eq(index: Double): JQuery = js.native
  /**
   * Reduce the set of matched elements to those that match the selector or pass the function's test.
   *
   * @param selector A string containing a selector expression to match the current set of elements against.
   * @see { https://api.jquery.com/filter/#filter-selector}
   */
  def filter(selector: String): JQuery = js.native
  /**
   * Reduce the set of matched elements to those that match the selector or pass the function's test.
   *
   * @param func A function used as a test for each element in the set. this is the current DOM element.
   * @see { https://api.jquery.com/filter/#filter-function}
   */
  def filter(func: js.Function2[Double, Element, Any]): JQuery = js.native
  /**
   * Reduce the set of matched elements to those that match the selector or pass the function's test.
   *
   * @param element An element to match the current set of elements against.
   * @see { https://api.jquery.com/filter/#filter-elements}
   */
  def filter(element: Element): JQuery = js.native
  /**
   * Reduce the set of matched elements to those that match the selector or pass the function's test.
   *
   * @param obj An existing jQuery object to match the current set of elements against.
   * @see { https://api.jquery.com/filter/#filter-selection}
   */
  def filter(obj: JQuery): JQuery = js.native
  /**
   * Get the descendants of each element in the current set of matched elements, filtered by a selector, jQuery object, or element.
   *
   * @param selector A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/find/#find-selector}
   */
  def find(selector: String): JQuery = js.native
  /**
   * Get the descendants of each element in the current set of matched elements, filtered by a selector, jQuery object, or element.
   *
   * @param element An element to match elements against.
   * @see { https://api.jquery.com/find/#find-element}
   */
  def find(element: Element): JQuery = js.native
  /**
   * Get the descendants of each element in the current set of matched elements, filtered by a selector, jQuery object, or element.
   *
   * @param obj A jQuery object to match elements against.
   * @see { https://api.jquery.com/find/#find-element}
   */
  def find(obj: JQuery): JQuery = js.native
  /**
   * Reduce the set of matched elements to the first in the set.
   * @see { https://api.jquery.com/first/}
   */
  def first(): JQuery = js.native
  /**
   * Reduce the set of matched elements to those that have a descendant that matches the selector or DOM element.
   *
   * @param selector A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/has/#has-selector}
   */
  def has(selector: String): JQuery = js.native
  /**
   * Reduce the set of matched elements to those that have a descendant that matches the selector or DOM element.
   *
   * @param contained A DOM element to match elements against.
   * @see { https://api.jquery.com/has/#has-contained}
   */
  def has(contained: Element): JQuery = js.native
  /**
   * Check the current matched set of elements against a selector, element, or jQuery object and return true if at least one of these elements matches the given arguments.
   *
   * @param selector A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/is/#is-selector}
   */
  def is(selector: String): Boolean = js.native
  /**
   * Check the current matched set of elements against a selector, element, or jQuery object and return true if at least one of these elements matches the given arguments.
   *
   * @param func A function used as a test for the set of elements. It accepts one argument, index, which is the element's index in the jQuery collection.Within the function, this refers to the current DOM element.
   * @see { https://api.jquery.com/is/#is-function}
   */
  def is(func: js.Function2[Double, Element, Boolean]): Boolean = js.native
  /**
   * Check the current matched set of elements against a selector, element, or jQuery object and return true if at least one of these elements matches the given arguments.
   *
   * @param obj An existing jQuery object to match the current set of elements against.
   * @see { https://api.jquery.com/is/#is-selection}
   */
  def is(obj: JQuery): Boolean = js.native
  /**
   * Check the current matched set of elements against a selector, element, or jQuery object and return true if at least one of these elements matches the given arguments.
   *
   * @param elements One or more elements to match the current set of elements against.
   * @see { https://api.jquery.com/is/#is-elements}
   */
  def is(elements: js.Any): Boolean = js.native
  /**
   * Reduce the set of matched elements to the final one in the set.
   * @see { https://api.jquery.com/last/}
   */
  def last(): JQuery = js.native
  /**
   * Pass each element in the current matched set through a function, producing a new jQuery object containing the return values.
   *
   * @param callback A function object that will be invoked for each element in the current set.
   * @see { https://api.jquery.com/map/}
   */
  def map(callback: js.Function2[Double, Element, Any]): JQuery = js.native
  /**
   * Get the immediately following sibling of each element in the set of matched elements. If a selector is provided, it retrieves the next sibling only if it matches that selector.
   *
   * @param selector A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/next/}
   */
  def next(selector: String = js.native): JQuery = js.native
  /**
   * Get all following siblings of each element in the set of matched elements, optionally filtered by a selector.
   *
   * @param selector A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/nextAll/}
   */
  def nextAll(selector: String = js.native): JQuery = js.native
  /**
   * Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object passed.
   *
   * @param selector A string containing a selector expression to indicate where to stop matching following sibling elements.
   * @param filter A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/nextUntil/#nextUntil-selector-filter}
   */
  def nextUntil(selector: String, filter: String): JQuery = js.native
  /**
   * Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object passed.
   *
   * @param element A DOM node or jQuery object indicating where to stop matching following sibling elements.
   * @param filter A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/nextUntil/#nextUntil-element-filter}
   */
  def nextUntil(element: Element, filter: String): JQuery = js.native
  /**
   * Get all following siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object passed.
   *
   * @param obj A DOM node or jQuery object indicating where to stop matching following sibling elements.
   * @param filter A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/nextUntil/#nextUntil-element-filter}
   */
  def nextUntil(obj: JQuery, filter: String): JQuery = js.native
  /**
   * Remove elements from the set of matched elements.
   *
   * @param selector A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/not/#not-selector}
   */
  def not(selector: String): JQuery = js.native
  /**
   * Remove elements from the set of matched elements.
   *
   * @param func A function used as a test for each element in the set. this is the current DOM element.
   * @see { https://api.jquery.com/not/#not-function}
   */
  def not(func: js.Function2[Double, Element, Boolean]): JQuery = js.native
  /**
   * Remove elements from the set of matched elements.
   *
   * @param elements One or more DOM elements to remove from the matched set.
   * @see { https://api.jquery.com/not/#not-selection}
   */
  def not(elements: Element | js.Array[Element]): JQuery = js.native
  /**
   * Remove elements from the set of matched elements.
   *
   * @param obj An existing jQuery object to match the current set of elements against.
   * @see { https://api.jquery.com/not/#not-selection}
   */
  def not(obj: JQuery): JQuery = js.native
  /**
   * Get the closest ancestor element that is positioned.
   * @see { https://api.jquery.com/offsetParent/}
   */
  def offsetParent(): JQuery = js.native
  /**
   * Get the parent of each element in the current set of matched elements, optionally filtered by a selector.
   *
   * @param selector A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/parent/}
   */
  def parent(selector: String = js.native): JQuery = js.native
  /**
   * Get the ancestors of each element in the current set of matched elements, optionally filtered by a selector.
   *
   * @param selector A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/parents/}
   */
  def parents(selector: String = js.native): JQuery = js.native
  /**
   * Get the ancestors of each element in the current set of matched elements, up to but not including the element matched by the selector, DOM node, or jQuery object.
   *
   * @param selector A string containing a selector expression to indicate where to stop matching ancestor elements.
   * @param filter A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/parentsUntil/#parentsUntil-selector-filter}
   */
  def parentsUntil(selector: String, filter: String): JQuery = js.native
  /**
   * Get the ancestors of each element in the current set of matched elements, up to but not including the element matched by the selector, DOM node, or jQuery object.
   *
   * @param element A DOM node or jQuery object indicating where to stop matching ancestor elements.
   * @param filter A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/parentsUntil/#parentsUntil-element-filter}
   */
  def parentsUntil(element: Element, filter: String): JQuery = js.native
  /**
   * Get the ancestors of each element in the current set of matched elements, up to but not including the element matched by the selector, DOM node, or jQuery object.
   *
   * @param obj A DOM node or jQuery object indicating where to stop matching ancestor elements.
   * @param filter A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/parentsUntil/#parentsUntil-element-filter}
   */
  def parentsUntil(obj: JQuery, filter: String): JQuery = js.native
  /**
   * Get the immediately preceding sibling of each element in the set of matched elements, optionally filtered by a selector.
   *
   * @param selector A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/prev/}
   */
  def prev(selector: String = js.native): JQuery = js.native
  /**
   * Get all preceding siblings of each element in the set of matched elements, optionally filtered by a selector.
   *
   * @param selector A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/prevAll/}
   */
  def prevAll(selector: String = js.native): JQuery = js.native
  /**
   * Get all preceding siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object.
   *
   * @param selector A string containing a selector expression to indicate where to stop matching preceding sibling elements.
   * @param filter A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/prevUntil/#prevUntil-selector-filter}
   */
  def prevUntil(selector: String, filter: String): JQuery = js.native
  /**
   * Get all preceding siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object.
   *
   * @param element A DOM node or jQuery object indicating where to stop matching preceding sibling elements.
   * @param filter A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/prevUntil/#prevUntil-element-filter}
   */
  def prevUntil(element: Element, filter: String): JQuery = js.native
  /**
   * Get all preceding siblings of each element up to but not including the element matched by the selector, DOM node, or jQuery object.
   *
   * @param obj A DOM node or jQuery object indicating where to stop matching preceding sibling elements.
   * @param filter A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/prevUntil/#prevUntil-element-filter}
   */
  def prevUntil(obj: JQuery, filter: String): JQuery = js.native
  /**
   * Get the siblings of each element in the set of matched elements, optionally filtered by a selector.
   *
   * @param selector A string containing a selector expression to match elements against.
   * @see { https://api.jquery.com/siblings/}
   */
  def siblings(selector: String = js.native): JQuery = js.native
  /**
   * Reduce the set of matched elements to a subset specified by a range of indices.
   *
   * @param start An integer indicating the 0-based position at which the elements begin to be selected. If negative, it indicates an offset from the end of the set.
   * @param end An integer indicating the 0-based position at which the elements stop being selected. If negative, it indicates an offset from the end of the set. If omitted, the range continues until the end of the set.
   * @see { https://api.jquery.com/slice/}
   */
  def slice(start: Double, end: Double = js.native): JQuery = js.native
  /**
   * Show the queue of functions to be executed on the matched elements.
   *
   * @param queueName A string containing the name of the queue. Defaults to fx, the standard effects queue.
   * @see { https://api.jquery.com/queue/#queue-queueName}
   */
  def queue(queueName: String): js.Array[js.Any] = js.native
  /**
   * Manipulate the queue of functions to be executed, once for each matched element.
   *
   * @param newQueue An array of functions to replace the current queue contents.
   * @see { https://api.jquery.com/queue/#queue-queueName-newQueue}
   */
  def queue(newQueue: js.Array[js.Function]): JQuery = js.native
  /**
   * Manipulate the queue of functions to be executed, once for each matched element.
   *
   * @param callback The new function to add to the queue, with a function to call that will dequeue the next item.
   * @see { https://api.jquery.com/queue/#queue-queueName-callback}
   */
  def queue(callback: js.Function): JQuery = js.native
  /**
   * Manipulate the queue of functions to be executed, once for each matched element.
   *
   * @param queueName A string containing the name of the queue. Defaults to fx, the standard effects queue.
   * @param newQueue An array of functions to replace the current queue contents.
   * @see { https://api.jquery.com/queue/#queue-queueName-newQueue}
   */
  def queue(queueName: String, newQueue: js.Array[js.Function]): JQuery = js.native
  /**
   * Manipulate the queue of functions to be executed, once for each matched element.
   *
   * @param queueName A string containing the name of the queue. Defaults to fx, the standard effects queue.
   * @param callback The new function to add to the queue, with a function to call that will dequeue the next item.
   * @see { https://api.jquery.com/queue/#queue-queueName-callback}
   */
  def queue(queueName: String, callback: js.Function): JQuery = js.native
}

@js.native
@js.annotation.JSGlobalScope
object Jquery extends js.Object {
  /* *****************************************************************************
Copyright (c) Microsoft Corporation. All rights reserved.
Licensed under the Apache License, Version 2.0 (the "License"); you may not use
this file except in compliance with the License. You may obtain a copy of the
License at http://www.apache.org/licenses/LICENSE-2.0

THIS CODE IS PROVIDED *AS IS* BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, EITHER EXPRESS OR IMPLIED, INCLUDING WITHOUT LIMITATION ANY IMPLIED
WARRANTIES OR CONDITIONS OF TITLE, FITNESS FOR A PARTICULAR PURPOSE,
MERCHANTABLITY OR NON-INFRINGEMENT.

See the Apache Version 2.0 License for specific language governing permissions
and limitations under the License.
***************************************************************************** */
  /**
   * Interface for the AJAX setting that will configure the AJAX request
   * @see { https://api.jquery.com/jQuery.ajax/#jQuery-ajax-settings}
   */
  /**
   * Interface for the jqXHR object
   * @see { https://api.jquery.com/jQuery.ajax/#jqXHR}
   */
  /**
   * Interface for the JQuery callback
   * @see { https://api.jquery.com/category/callbacks-object/}
   */
  /**
   * Allows jQuery Promises to interop with non-jQuery promises
   */
  /**
   * Interface for the JQuery promise/deferred callbacks
   */
  /**
   * Interface for the JQuery promise, part of callbacks
   * @see { https://api.jquery.com/category/deferred-object/}
   */
  /**
   * Interface for the JQuery deferred, part of callbacks
   * @see { https://api.jquery.com/category/deferred-object/}
   */
  /**
   * Interface of the JQuery extension of the W3C event object
   * @see { https://api.jquery.com/category/events/event-object/}
   */
  /**
   * A collection of properties that represent the presence of different browser features or bugs.
   *
   * Intended for jQuery's internal use; specific properties may be removed when they are no longer needed internally
   * to improve page startup performance. For your own project's feature-detection needs, we strongly recommend the
   * use of an external library such as { http://modernizr.com/|Modernizr} instead of dependency on properties
   * in jQuery.support.
   *
   * @deprecated since version 1.9
   */
  /**
   * The interface used to construct jQuery events (with $.Event). It is
   * defined separately instead of inline in JQueryStatic to allow
   * overriding the construction function with specific strings
   * returning specific event objects.
   */
  /**
   * The interface used to specify coordinates.
   */
  /**
   * Elements in the array returned by serializeArray()
   */
  /**
   * @see { https://api.jquery.com/animate/}
   */
  /**
   * Static members of jQuery (those on $ and jQuery themselves)
   *
   * @see { https://api.jquery.com/Types/#jQuery}
   */
  /**
   * The jQuery instance members
   *
   * @see { https://api.jquery.com/Types/#jQuery}
   */
  var jQuery: JQueryStatic = js.native
  var `$`: JQueryStatic = js.native
}