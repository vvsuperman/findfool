(function() {
$(document).ready(function() {
return $("a.btn-facebook").click(function(e) {
var data, h, left, top, w;
return e.preventDefault(), data = e.data, w = 600, h = 350, left = screen.width / 2 - w / 2, 
top = screen.height / 2 - h / 2, _gaq.push([ "_trackPageview", "/loginpage/facebook/" ]), 
window.login_callback = function() {
return location.reload();
}, window.open("/hackers/auth/facebook?display=popup", "facebook_login", "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=" + w + ", height=" + h + ", top=" + top + ", left=" + left);
}), $("a.btn-google").click(function(e) {
var data, h, left, top, w;
return e.preventDefault(), data = e.data, w = 600, h = 500, left = screen.width / 2 - w / 2, 
top = screen.height / 2 - h / 2, _gaq.push([ "_trackPageview", "/loginpage/google/" ]), 
window.login_callback = function() {
return location.reload();
}, window.open("/hackers/auth/google_oauth2?display=popup", "google_login", "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=" + w + ", height=" + h + ", top=" + top + ", left=" + left);
}), $("a.btn-github").click(function(e) {
var data, h, left, top, w;
return e.preventDefault(), data = e.data, w = 960, h = 500, left = screen.width / 2 - w / 2, 
top = screen.height / 2 - h / 2, _gaq.push([ "_trackPageview", "/loginpage/github/" ]), 
window.login_callback = function() {
return location.reload();
}, window.open("/hackers/auth/github?display=popup", "facebook_login", "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=" + w + ", height=" + h + ", top=" + top + ", left=" + left);
}), $("#fbshare").click(function(e) {
var h, left, top, url, w;
return e.preventDefault(), w = 600, h = 350, left = screen.width / 2 - w / 2, top = screen.height / 2 - h / 2, 
url = "https://www.facebook.com/dialog/feed?app_id=347499128655783&" + $(e.currentTarget).attr("href"), 
window.open(url, "_blank", "toolbar=no, location=no, directories=no, status=no, menubar=no, scrollbars=no, resizable=no, copyhistory=no, width=" + w + ", height=" + h + ", top=" + top + ", left=" + left), 
window.focus();
}), $(".homepage_category-single").click(function(e) {
return $(".homepage_category-single").removeClass("active"), $(e.currentTarget).addClass("active"), 
window.tabs_smallified ? void 0 :($(".homepage_categories-nav").removeClass("large").addClass("small"), 
window.tabs_smallified = !0);
});
});
}).call(this), function() {
$(document).ready(function() {
return function() {
var events;
return events = function() {
var hideError, showError;
return $("[data-toggle=tooltip]").tooltip({
trigger:"manual",
animation:!1
}), $("a.password-retrieve").click(function() {
return "none" === $(".js-password-retrieve-area.forgot").css("display") ? (setTimeout(function() {
return $("html body").animate({
scrollTop:$(".js-password-retrieve-area.forgot input").offset().top
}, 300), setTimeout(function() {
return $(".js-password-retrieve-area.forgot input").focus();
}, 300);
}, 600), $(".js-password-retrieve-area.forgot").slideToggle()) :void 0;
}), showError = function(field, error) {
return $("#legacy-signup input#" + field).parent().children("i").css("color", "#f65039"), 
$("#legacy-signup input#" + field).attr("data-original-title", error), $("#legacy-signup input#" + field).tooltip("show");
}, hideError = function(field) {
return $("#legacy-signup input#" + field).parent().children("i").css("color", "#2BC56D"), 
$("#legacy-signup input#" + field).attr("data-original-title", ""), $("#legacy-signup input#" + field).tooltip("hide");
}, $("#legacy-signup input").keydown(function(e) {
var keyCode, val;
return keyCode = e.keyCode || e.which, val = $(this).val(), "" !== $.trim(val) ? ($(this).data("tab", "true"), 
$(this).trigger("keyup")) :void 0;
}), $("#legacy-signup input#username").keyup(function() {
var currenTime, previousTime, username;
return username = $(this).val(), void 0 === $(this).data("tab") || (currenTime = new Date().getTime(), 
previousTime = $(this).attr("data-time") || 0, 100 > currenTime - previousTime) ? void 0 :($(this).attr("data-time", new Date().getTime()), 
$.ajax({
url:"/auth/valid_username",
type:"POST",
data:{
username:username
},
success:function(resp) {
return resp.errors.length > 0 ? showError("username", resp.errors) :hideError("username");
}
}));
}), $("#legacy-signup input#email").keyup(function() {
var email_regex, val;
if (void 0 !== $(this).data("tab")) return val = $(this).val(), email_regex = /^[\w\.\+]+@[\S]+?\.[a-zA-Z+\.]{2,}$/, 
val.match(email_regex) ? hideError("email") :showError("email", "Invalid email address.");
}), $("#legacy-signup input#password").keyup(function() {
var password_regex, val;
if (void 0 !== $(this).data("tab")) return val = $(this).val(), password_regex = /.{6,}/, 
val.match(password_regex) ? hideError("password") :showError("password", "Password should be at least 6 characters long.");
}), $(".signup-button").click(function(e) {
var email, password, username;
return e.preventDefault(), $(e.currentTarget).hasClass("disabled") ? void 0 :($("form#legacy-signup .alert.error").hide().html(""), 
username = $("#legacy-signup input#username").val(), email = $("#legacy-signup input#email").val(), 
password = $("#legacy-signup input#password").val(), $(e.currentTarget).addClass("disabled"), 
$(e.currentTarget).html("Signing you up..."), $.ajax({
url:"/auth/signup" + window.contest_path,
type:"POST",
data:{
username:username,
email:email,
password:password,
confirm_password:password,
contest_crp:$.cookie("" + window.contest_slug + "_crp")
},
success:function(data) {
var error, fields;
return $(e.currentTarget).removeClass("disabled"), data.status ? data.contest_started ? ($(e.currentTarget).html("Logging you in..."), 
document.location.reload()) :document.location.href = document.location.href.replace("/login", "/not_started").replace("/signup", "/not_started") :($(e.currentTarget).html("Sign Up & Start Coding"), 
0 === data.errors.length ? error = "Unknown Error" :(window.data = data, fields = [ "username", "email", "password" ], 
$(fields).each(function(index, field) {
return data.fields[field].length > 0 ? showError(field, data.fields[field].join(",")) :hideError(field);
})));
}
}));
}), $("button.login-button").click(function(e) {
var fallback, login, password, remember_me;
return e.preventDefault(), $(e.currentTarget).hasClass("disabled") ? void 0 :($("form#legacy-signup .alert.error").hide().html(""), 
login = $("#legacy-login input#login").val(), password = $("#legacy-login input#password").val(), 
remember_me = $("#legacy-login input#remember_me").is(":checked"), fallback = "true" === $("input#fallback").val(), 
$(e.currentTarget).addClass("disabled"), $(e.currentTarget).html("Verifying.."), 
$.ajax({
url:"/auth/login" + window.contest_path,
type:"POST",
data:{
login:login,
password:password,
remember_me:remember_me,
contest_crp:$.cookie("" + window.contest_slug + "_crp"),
fallback:fallback
},
success:function(data) {
var back_callback, error, i, len, no_callback, yes_callback, _i;
if ($(e.currentTarget).removeClass("disabled"), fallback && data.hrx_user && "master" === data.contest_slug) return $(e.currentTarget).html("Log In"), 
$(".homepage_admin").hide(), $(".looking-for-hrx-auth").hide(), $("#both-accounts").show(), 
data.hacker_exists ? $("#hr-auth-action").html("Login") :$("#hr-auth-action").html("Signup"), 
$("#homepage_notify").fadeIn(), back_callback = function() {
return $("#homepage_notify").hide(), $("#both-accounts").hide(), $(".homepage_admin").show(), 
$(".looking-for-hrx-auth").show();
}, $("#homepage_notify #back-button").off("click").on("click", back_callback), yes_callback = function() {
return $("#homepage_notify #yes-button").html("Redirecting..."), $.ajax({
type:"POST",
url:"/x/login",
data:{
email:login,
password:password,
dual_login:!0
},
success:function(data) {
var error, error_msg;
return data.status ? document.location.href = document.location.protocol + "//" + document.location.host + "/x/login?utm_source=hr_login" :(error = null, 
data && data.errors && data.errors.length > 0 && (error = data.errors[0]), error_msg = "", 
error_msg = error ? "&signup_error=" + encodeURI(error) :"", document.location.href = document.location.protocol + "//" + document.location.host + "/x/login?utm_source=hr_login&email=" + encodeURI(login) + error_msg);
},
error:function() {
return document.location.href = document.location.protocol + "//" + document.location.host + "/x/login?utm_source=hr_login&email=" + encodeURI(login) + "&signup_error=" + encodeURI("Unknown Error!");
}
});
}, $("#homepage_notify #yes-button").off("click").on("click", yes_callback), no_callback = function() {
return mixpanel.push([ "track", "DualLogin_HRRedirect", {
login:login,
status:data.status,
hr_user:data.hacker_exists,
hrx_user:data.hrx_user
} ]), $("#homepage_notify").hide(), $("#both-accounts").hide(), $(".homepage_admin").show(), 
$(".looking-for-hrx-auth").show(), setTimeout(function() {
return data.hacker_exists ? ($("input#fallback").val("false"), $("button.login-button").click()) :$("li.signup-toggle a").click();
}, 0);
}, $("#homepage_notify #no-button").off("click").on("click", no_callback);
if (data.status) return $(e.currentTarget).html("Logging you in..."), data.contest_started ? document.location.reload() :document.location.href = document.location.href.replace("/login", "/not_started").replace("/signup", "/not_started");
if ($(e.currentTarget).html("Log In"), 0 === data.errors.length) error = "Unknown Error"; else if (1 === data.errors.length) error = data.errors[0]; else {
for (error = "<ul style='text-align: left;'>", len = data.errors.length - 1, i = _i = 0; len >= 0 ? len >= _i :_i >= len; i = len >= 0 ? ++_i :--_i) error += "<li>" + data.errors[i] + "</li>";
error += "</ul>";
}
return $("form#legacy-login .alert.error").show().html(error);
}
}));
}), $(".reset-button").click(function(e) {
var login;
return e.preventDefault(), $(e.currentTarget).hasClass("disabled") ? void 0 :($("form#legacy-reset .message").hide().html(""), 
login = $("#legacy-reset input#login").val(), $(e.currentTarget).addClass("disabled"), 
$(e.currentTarget).html("Verifying details.."), $.ajax({
url:"/auth/forgot_password",
type:"POST",
data:{
login:login
},
success:function(data) {
var error;
return $(e.currentTarget).html("Send Password Reset Instructions"), $(e.currentTarget).removeClass("disabled"), 
data.status ? $("form#legacy-reset .message").show().removeClass("error").addClass("success").html("Password reset instructions have been mailed to you") :(error = 0 === data.errors.length ? "Unknown Error" :data.errors[0], 
$("form#legacy-reset .message").removeClass("success").addClass("error").show().html(error));
}
}));
}), $(".new-password-button").click(function(e) {
var confirm_password, password;
return e.preventDefault(), $(e.currentTarget).hasClass("disabled") ? void 0 :($("form#legacy-new-password .alert.error").hide().html(""), 
password = $("#legacy-new-password input#password").val(), confirm_password = $("#legacy-new-password input#confirm-password").val(), 
$(e.currentTarget).addClass("disabled"), $(e.currentTarget).html("Verifying details.."), 
$.ajax({
url:"/auth/reset_password",
type:"POST",
data:{
password:password,
confirm_password:confirm_password,
reset_password_token:document.URL.split("=")[1]
},
success:function(data) {
var error, i, len, _i;
if ($(e.currentTarget).html("Logging you in.."), $(e.currentTarget).removeClass("disabled"), 
data.status) return $("form#legacy-new-password .alert.error").show().removeClass("error alert").addClass("message success").html("Password successfully reset. Logging you in.."), 
document.location.reload();
if (0 === data.errors.length) error = "Unknown Error"; else {
for (error = "<ul style='text-align: left;'>", len = data.errors.length - 1, i = _i = 0; len >= 0 ? len >= _i :_i >= len; i = len >= 0 ? ++_i :--_i) error += "<li>" + data.errors[i] + "</li>";
error += "</ul>";
}
return $("form#legacy-new-password .alert.error").show().html(error);
}
}));
}), $("form#legacy-reset input#Login").keypress(function(e) {
return 13 === e.which ? (e.preventDefault(), $("form#legacy-reset a.reset-button").click()) :void 0;
}), $(".create-account-button").click(function(e) {
var data;
return e.preventDefault(), $(e.currentTarget).hasClass("disabled") ? void 0 :($(".alert").html("").hide(), 
$(e.currentTarget).addClass("disabled"), $(e.currentTarget).html("Logging in.."), 
data = {
username:$("input[name=username]").val(),
password:$("input[name=password]").val()
}, $.ajax({
type:"POST",
beforeSend:function(xhr) {
return xhr.setRequestHeader("X-CSRF-Token", $('meta[name="csrf-token"]').attr("content"));
},
data:data,
url:document.location.href,
success:function(data) {
var errors, i;
if (data.status) return data.login_token ? $.getJSON(document.location.protocol + "//" + document.location.host + "/auth/login_with_token/" + data.contest, {
token:data.login_token
}, function(_data) {
var contest, login;
return login = void 0, contest = void 0, _data.status ? (login = "/auth/login", 
contest = "master" === _data.contest ? "" :"/" + _data.contest) :(login = "", contest = "master" === _data.contest ? "" :"/contests/" + _data.contest), 
document.location.href = document.location.protocol + "//" + document.location.host + login + contest;
}) :document.location.href = document.location.href;
for ($(e.currentTarget).html("Log In"), $(e.currentTarget).removeClass("disabled"), 
errors = "<ul>", i = 0; i < data.errors.length; ) errors += "<li>" + data.errors[i] + "</li>", 
i++;
return errors += "</ul>", $(".alert").html(errors).show();
}
}));
});
}, events(), window.detach_DOM = null, window.loginDOMaction = function() {
return window.detach_DOM && $("form#legacy-login").prepend(window.detach_DOM), events(), 
window.detach_DOM = "<div class='homepage_signupgroup--legacy'>" + $("form#legacy-signup .homepage_signupgroup--legacy").html() + "</div>", 
$("form#legacy-signup .homepage_signupgroup--legacy").remove();
}, window.signupDOMaction = function() {
return window.detach_DOM && $("form#legacy-signup").prepend(window.detach_DOM), 
events(), window.detach_DOM = "<div class='homepage_signupgroup--legacy'>" + $("form#legacy-login .homepage_signupgroup--legacy").html() + "</div>", 
$("form#legacy-login .homepage_signupgroup--legacy").remove();
}, $(".login-toggle").click(function(e) {
return e.preventDefault(), !$(this).hasClass("active") && ($("[data-toggle=tooltip]").tooltip("hide"), 
window.loginDOMaction(), $(".signup").hide(), $(".login ").fadeIn(), $(".login-message").show(), 
$(".signup-message").hide(), $(this).addClass("active"), $(".signup-toggle").removeClass("active"), 
window.history && history.pushState) ? window.history.pushState(null, document.title, window.document.location.href.replace("/signup", "/login")) :void 0;
}), $(".signup-toggle").click(function(e) {
return e.preventDefault(), !$(this).hasClass("active") && (window.signupDOMaction(), 
$(".signup").fadeIn(), $(".login ").hide(), $(".signup-message").show(), $(".login-message").hide(), 
$(this).addClass("active"), $(".login-toggle").removeClass("active"), $("[data-toggle=tooltip]").tooltip("show"), 
window.history && history.pushState) ? window.history.pushState(null, document.title, window.document.location.href.replace("/login", "/signup")) :void 0;
}), -1 !== document.location.pathname.indexOf("/signup") ? window.signupDOMaction() :window.loginDOMaction();
};
}(this));
}.call(this);