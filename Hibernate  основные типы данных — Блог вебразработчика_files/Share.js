; (function() {

    if (window.Ya && window.Ya.Share) {
        Ya.Share.update();
        return;
    }

    var doc = document;

    var STATIC_BASE = Ya && Ya.STATIC_BASE;
    var START_BASE = Ya && Ya.START_BASE;
    var COUNTER_VALUE_URL = 'ajax/share-counter.xml';
    var NEW_WINDOW_URL = 'posts_share_link.xml';

    // Detecting data:URI support
    var isDataUriFeatureDetected = false;
    var HAS_DATA_URI_SUPPORT;
    var testImage = new Image();
    testImage.onload = testImage.onerror = function(){
        if(this.width != 1 || this.height != 1) {
            HAS_DATA_URI_SUPPORT = false;
        } else {
            HAS_DATA_URI_SUPPORT = true;
        }
        isDataUriFeatureDetected = true;
    }
    // trying to load 1x1 pixex via data:URI
    testImage.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";

    // The following part of this script should be execetued only after data:URI feature is detected
    var dataUriDetectionTimer = setInterval(function(){
        if (isDataUriFeatureDetected) {
            clearInterval(dataUriDetectionTimer);

            /* Dynamically inserting required css styles */
            var BACKGROUND_IMAGE_URL = STATIC_BASE + "/i/share/share-button-sprite.png";
            var BACKGROUND_IMAGE_DATA_URI = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABoAAAGQCAMAAACHyID2AAAAA3NCSVQICAjb4U/gAAACbVBMVEX////+/v79/f38/Pz7+/v6+vr5+fn4+Pj89vX39/f29vb+9fP19fX09PT98e7z8/Py8vLx8fH87+3w8PD47+7v7+/67Oru7u7t7e3s7Oz86OP85uH95d7p6en05ubo6Oj44+H94drm5ub/3t7l5eXk5OTj4+P239763Nfi4uLu4ODh4eHg4ODf39/e3t7d3d3a2trZ2dn40tDY2NjX19fW1tbg09PU1NT3ysfT09Pcz8/R0dH6x8LQ0NDPz8/5xcHOzs7Nzc3+wrL1wsLMzMzXysrLy8vKysr9vLPJycnjwsLIyMj9vK3XxcXHx8fGxsbRxMTFxcX7uK/ivb3ExMTMw8PDw8P8tqn6ta7MwMDCwsLeu7vBwcHAwMD5sq6/v7+9vb3hs7PlsLC6urq4uLj7pp3nqqrypqbNrq60tLSzs7Pyo6OysrKwsLD7nZv+nYOtra2srKyrq6v+loPsmZmqqqqoqKinp6f+kXelpaWkpKT8jYOioqKdnZ38hHn+g2iamprrg4OZmZn5f3yYmJjngoLsgIDVhYXjgYGUlJT+el3fgICTk5PSg4PPf3/+dF+Ojo6NjY2MjIyLi4uKior9a2P/akf+alOGhobXcXGFhYX/ZUSDg4P0ZWXYa2v3YWH/YTzpYmL/X0F9fX39W1LoX197e3vvW1v/WTr/VTX+U0P+U0L+Ukj/UDTpU1P/SC/+QzjgSkr+Qj3/QjH/Nyz5MTHbNTXqMTH/Kh/wLCz/KBzlKSn1JSX/GxX1Gxv/GBPXHh7+ExL+EA/9DQ37DAz4DAz2DAzwDAzpDAzoDAzjDAzfDAzcDAzXDAzYDAzeWte0AAAAz3RSTlMA//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////83IMNd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";

            // CSS_TEXT variable is filled with content of ya-share-button.css file being compressed.
            // Also background urls have been replaced by special wildcards
            // It's very important to remove semicolons after wildcard added by css compressor
            var CSS_TEXT = "a.ya-share{text-decoration:none;display:-moz-inline-box;display:inline-block;cursor:pointer;cursor:hand;color:#3F3F3F;}a.ya-share:hover,a.ya-share:hover *{color:#3F3F3F!important;text-decoration:none!important;}.ya-share .text{font-family:Arial,sans-serif;text-decoration:none!important;text-shadow:0 -1px 1px rgba(0,0,0,0.1); vertical-align:top;}.ya-share-large .text{font-size:12px;}.ya-share-small .text{font-size:11px;}.ya-share .share-button{margin:0 4px 0 0;}.ya-share span.bg{background:url({BACKGROUND_IMAGE}) no-repeat;display:-moz-inline-box;display:inline-block;}.ya-share-large span.bg{height:22px;line-height:22px;}.ya-share-small span.bg{height:16px;line-height:16px;}.ya-share-large .share-button .a{background-position:left 0;}.ya-share-large .share-button .b{background-position:right -22px;margin:0 0 0 20px;}.ya-share-large .share-button .c{margin:0 8px 0 0;padding:0 3px 0 0;background-position:0 -44px;background-repeat:repeat-x;}.ya-share-large .share-icon .a{background-position:left -151px;width:26px;margin:0 3px 0 0;}.ya-share-large .share-counter .a{background-position:left -173px;}.ya-share-large .share-counter .b{background-position:right -195px;margin:0 0 0 4px;}.ya-share-large .share-counter .c{margin:0 4px 0 0;padding:0;background-position:0 -44px;background-repeat:repeat-x;}.ya-share-small .share-icon .a{background-position:right -131px;width:19px;margin:0 3px 0 0;}.ya-share-small .share-button .a{background-position:left -70px;}.ya-share-small .share-button .b{background-position:right -86px;margin:0 0 0 16px;}.ya-share-small .share-button .c{margin:0 7px 0 0;padding:0 1px 0 0;background-position:0 -102px;background-repeat:repeat-x;}.ya-share-small .share-counter .a{background-position:left -218px;}.ya-share-small .share-counter .b{background-position:right -234px;margin:0 0 0 3px;}.ya-share-small .share-counter .c{margin:0 3px 0 0;padding:0;background-position:0 -102px;background-repeat:repeat-x;}";
            var replacementObject = {};
            replacementObject.BACKGROUND_IMAGE = HAS_DATA_URI_SUPPORT ? BACKGROUND_IMAGE_DATA_URI : BACKGROUND_IMAGE_URL;
            CSS_TEXT = supplant(CSS_TEXT, replacementObject);

            var styleElement = document.createElement('style');
            styleElement.type = "text/css";
            if (styleElement.styleSheet) {
                styleElement.styleSheet.cssText = CSS_TEXT;
            } else {
                styleElement.appendChild(doc.createTextNode(CSS_TEXT));
            }
            doc.getElementsByTagName('head')[0].appendChild(styleElement);


            /*-------------------------------------------------------------------------------------------------*/

            if (!window.Ya) {
                window.Ya = {};
            }
            window.Ya.SHARE_COUNTERS_IDS = window.Ya.SHARE_COUNTERS_IDS ||[];

            var COLLECTION = {};
            var COUNT = 0;

            var SHARE_TEXT = "поделиться";
            var COUNTER_HTML_ID_PREFIX = "ya_share_counter_html_";
            var SHARE_BUTTON_TEXT_ID_PREFIX = "ya_share_button_text_"

            var BUTTON_HTML_TEMPLATE = '<span title="Поделиться ссылкой в Я.ру" class="share-button"><span class="a bg"><span class="b bg"><span class="c bg text">{SHARE_TEXT}</span></span></span></span>';
            var COUNTER_HTML_TEMPLATE = '<span title="Поделиться ссылкой в Я.ру" class="share-counter" id="{COUNTER_ID}"  style="display:none;"><span class="a bg"><span class="b bg"><span id="' + COUNTER_HTML_ID_PREFIX + '{COUNTER_ID}" class="c bg text">&#160;</span></span></span></span>';
            var ICON_HTML_TEMPLATE = '<span title="Поделиться ссылкой в Я.ру" class="share-icon"><span class="a bg text">&nbsp;</span></span>';
            var BASE_CLASS_NAME = 'ya-share';
            var SHARE_BUTTON_NAME = 'ya-share';
            var COUNTER_ID_PREFIX = 'ya-share-count-';
            var IS_PREVIEW_MODE = true && window.g_share_button_preview;
            var PREVIEW_COUNT = '5476';

            var Share = function() {};

            Share.prototype.init = function(params) {
                this.selectedText = "";
                this.domain = params.domain;
                return this;
            };

            Share.prototype.getButtons = function() {
                return doc.getElementsByName(SHARE_BUTTON_NAME);
            };

            Share.prototype.getCounters = function() {
                var length = window.Ya.SHARE_COUNTERS_IDS.length;
                var counters = [];
                for (var i = 0; i < length; i++) {
                    var counter = doc.getElementById(COUNTER_ID_PREFIX + window.Ya.SHARE_COUNTERS_IDS[i]);
                    // we should make sure of existance element with such id since in the case of preview
                    // ids are regenerated every time user is modifying button settings
                    if (counter) {
                        counters.push(counter);
                    }
                }
                return counters;
            };

            Share.prototype.renderButtons = function() {
                var shareButtons = this.getButtons();
                var length = shareButtons.length;
                for (var i = 0; i < length; i++) {
                    var button = shareButtons[i];
                    if (IS_PREVIEW_MODE || !button.isRendered) {
                        var size = button.size = button.getAttribute('size');
                        var hasCounter = button.counter = button.getAttribute('counter');
                        var type = button.type = button.getAttribute('type');
                        if (!button.isBeingRendered) {
                            button.isBeingRendered = true;
                            this.renderButton(button, {
                                type : type,
                                hasCounter: hasCounter,
                                size: size
                            })
                            this.bindClickHandler(button);

                        }
                    }
                }
            };

            Share.prototype.bindClickHandler = function(button) {
                var that = this;
                button.onmousedown = function() {
                    that.selectedText = that.getSelectedText();
                }
                button.onclick = function() {
                    that.shareThis(that.isValidTarget(button));
                }
            };
            
            Share.prototype.renderButton = function(button, params) {
                var uid  = COUNT;
                button.setAttribute("yauid", uid);

                params.uid = uid;
                params.title = button.getAttribute('share_title');
                params.url = button.getAttribute('share_url');
                params.text = button.getAttribute('share_text');

                var html = this.composeHTML(params);
                button.innerHTML = html;

                COUNT++;
                COLLECTION[uid] = params;

                button.className = BASE_CLASS_NAME + " " + BASE_CLASS_NAME + "-" + params.size + " " + BASE_CLASS_NAME + "-" + params.type;
                button.isBeingRendered = false;
                button.isRendered = true;
            };

            Share.prototype.composeHTML = function(params) {
                var html = "";
                if (params.hasCounter == "yes") {
                    // we have to assign id to counter element as we can't get reference to dynamically created elements via getElementsByName in IE
                    var counterHTML = supplant(COUNTER_HTML_TEMPLATE, {'COUNTER_ID' : COUNTER_ID_PREFIX + params.uid})
                    window.Ya.SHARE_COUNTERS_IDS.push(COUNT);
                }
                html += params.type == "icon" ? ICON_HTML_TEMPLATE : supplant(BUTTON_HTML_TEMPLATE, {'SHARE_TEXT' : params.text || SHARE_TEXT});
                html += params.hasCounter == "yes" ? counterHTML : '';

                return html;
            };

            Share.prototype.showCounter = function(count) {
                if (parseInt(count,10) > 0) {
                    var counters = this.getCounters();
                    var length = counters.length;
                    for (var i = 0; i < length; i++) {
                        var counter = counters[i];
                        var counterHTMLContainer = doc.getElementById(COUNTER_HTML_ID_PREFIX + counter.id);
                        counterHTMLContainer.innerHTML = parseInt(count, 10);
                        counter.style.display = '';
                    }
                }
            };

            Share.prototype.isValidTarget = function(target) {
                if (target) {
                    if (target.className.indexOf(BASE_CLASS_NAME + " ") >= 0 ) {
                        var uid = String(target.getAttribute("yauid"));
                        return COLLECTION[uid];
                    }
                }
                return false;
            };

            Share.prototype.getSelectedText = function() {
                if (window.getSelection) {
                    return window.getSelection().toString();
                } else if (document.getSelection) {
                    return document.getSelection();
                } else if (document.selection) {
                    return document.selection.createRange().text;
                } else {
                    return null;
                }
            };

            Share.prototype.openWindow = function(data) {
                var userAgent = window.navigator.userAgent;
                var isMacChrome = (userAgent.indexOf('Mac OS') > -1) && (userAgent.indexOf('Chrome') > -1);

                var width = 600;
                var height = isMacChrome ? 450 : 420;
                var winleft = (screen.width - width) / 2;
                var wintop = (screen.height - height) / 2;
                var params = 'scrollbars=0, resizable=1, menubar=0, left=' + winleft + ', top=' + wintop + ', width=' + width + ', height=' + height + ', toolbar=0, status=0';
                var wintype = "_blank";
                var title = data.title || doc.title;
                var link = data.url || doc.location.href;

                var selectedText = this.selectedText;
                if (selectedText === null) { selectedText = ""}
                if (selectedText != "") {
                    selectedText = "<blockquote>" + selectedText + "</blockquote>"
                }

                var url = this.domain.concat(NEW_WINDOW_URL.concat("?url=", encodeURIComponent(link), "&title=", encodeURIComponent(title), "&body=", encodeURIComponent(selectedText)));
                window.open(url, wintype, params);
            };

            Share.prototype.update = function() {
                this.renderButtons();
                if (IS_PREVIEW_MODE) {
                    this.showCounter(PREVIEW_COUNT);
                } else {
                    this.getCounterValue();
                }
            };

            Share.prototype.getCounterValue = function(data) {
                var url = doc.location.href;
                var scriptElement = doc.createElement("script");
                scriptElement.type = "text/javascript";
                scriptElement.async = "true";
                scriptElement.src = this.domain.concat(COUNTER_VALUE_URL.concat("?".concat("url=", url)));
                (document.getElementsByTagName("head")[0] || document.body).appendChild(scriptElement);
            };

            Share.prototype.shareThis = function(data) {
                this.openWindow(data);
            };

            /*------------------------------------------------------------------------------------------------------------*/

            window.Ya.Share = new Share().init({
                domain: START_BASE
            });

            window.Ya.Share.update();
        }
    },1);


    function supplant (string, obj) {
        return string.replace(/{([^{}]*)}/g,
            function (a, b) {
                var r = obj[b];
                return typeof r === 'string' || typeof r === 'number' ? r : a;
            }
        );
    };

})();
