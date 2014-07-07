try {
    for (var lastpass_iter = 0; lastpass_iter < document.forms.length; lastpass_iter++) {
        var lastpass_f = document.forms[lastpass_iter];
        if (typeof(lastpass_f.lpsubmitorig2) == "undefined") {
            lastpass_f.lpsubmitorig2 = lastpass_f.submit;
            lastpass_f.submit = function () {
                var form = this;
                var customEvent = document.createEvent("Event");
                customEvent.initEvent("lpCustomEvent", true, true);
                var d = document.getElementById("hiddenlpsubmitdiv");
                if (d) {
                    for (var i = 0; i < document.forms.length; i++) {
                        if (document.forms[i] == form) {
                            d.innerText = i;
                        }
                    }
                    d.dispatchEvent(customEvent);
                }
                form.lpsubmitorig2();
            }
        }
    }
} catch (e) {
}