/*
 * views-help-flex.js
 * flex相关功能的帮助类
 * @author zengjie 下午10:08:38
*/
define(
        'app/exam/views-help-flex',
        function(require, exports, module) {
            require('app/flex/flex.css');
            require('app/flex/swfobject');
            require('app/flex/flex');
            var msg = require('core/msg'), timeUtil = require('app/time/timeUtil'), utils = require('core/utils'), settingAction = require('app/exam/action-exam-setting');
            var constant = require('core/constant'), properties = require('core/properties'), stateMap = {
                $container : $('#flex-wrap'),
                isBindEvent : false
            }, configMap = {
                mediaLimit : 600,//媒体的录制时间限制为10分钟,以秒为单位
                flexModelQueueMap : {},
                isWaitSetModel : false,
                isExamCallUpload : false,//是否考试端发起的上传
                model : 1,//1.拍照；2；音频；3；视频;4：设置
                answerTime : 0,
                pubMap : {
                    gevent_help_flex_monitorOnReady : "views-help-flex-monitorOnReady",
                    gevent_help_flex_getPhotoUrl : "views-help-flex-getPhotoUrl",
                    gevent_help_flex_getAudioAnswer : "views-help-flex-getAudioAnswer",
                    gevent_help_flex_getVideoAnswer : "views-help-flex-getVideoAnswer",
                    gevent_help_flex_isUpload : "views-help-flex-isUpload",
                    gevent_help_flex_isUploadComplete : "views-help-flex-isUploadComplete"
                }
            }, jqueryMap = {
                $flex : $('#flex'),
                $root : $("#exam"),
                $container : $('#flex-wrap')
            }, flex_isLoad = false, //
            initTemplate, //
            viewMap = {
                mediaRecordTime : 0,
                mediaLimit : configMap.mediaLimit,
                removeFlex : false,
                isCallServe : false,
                flex_isLoad : false,
                isOnready : false,
                model : 1, //1.拍照；2；音频；3；视频; 4.监控
                state : 0,//0:初始；1:已准备好; 2.取消
                playSate : 0,//0:停止；1.录制
                urlAnswer : "",
                isShow : false
            //0.初始状态;1.准备好；2.完成拍照或录像
            }, bindEvent, subscribeEvent, //
            checkCamera, checkMicrophone, //
            handFlexOnReady, //
            loadFlex, hideFlex, removeFlex, recoverFlex, uploadMediaByExam, isNeedMicrophone, isNeedCamera, isLoadFlex, //
            setFlexOffset, isFlexInit, takeRecord, //
            setPhotoMode, closePhotoMark, takePhoto, uploadPhoto, //
            setAudioMode, uploadMedia, onUploadComplete, onUploadError, //
            setVideoMode, flashPlay, onFlashStop, stopPlay, //
            setMonitorMode, checkUserMedia;
            var modelHelp = {
                flex_isPhoto : function(model) {
                    return model == 1;
                },
                flex_isAudio : function(model) {
                    return model == 2;
                },
                flex_isVideo : function(model) {
                    return model == 3;
                },
                flex_isMonitor : function(model) {
                    return model == 4;
                },
                flex_onInit : function(state) {
                    return state == 0;
                },
                flex_onReady : function(state) {
                    return state == 1;
                },
                flex_onCancel : function(state) {
                    return state == 2;
                },
                flex_isPlay : function(playSate) {
                    return playSate == 1;
                }
            };
            initTemplate = function($container) {
                $.views.helpers(modelHelp);
                $.templates({
                    flexWraplTmpl : "#flex-wrap-tmpl"
                });
                viewMap.isCallServe = false;
                viewMap.state = 0;
                viewMap.playSate = 0;
                viewMap.removeFlex = false;
                $.link.flexWraplTmpl($container, viewMap);
                jqueryMap.$flexWindow = $container.find(".flex-window");
                configMap.flexWidth = jqueryMap.$flexWindow.width();
                configMap.flexHeight = jqueryMap.$flexWindow.height();
            };
            subscribeEvent = function() {
                $.gevent.subscribe(jqueryMap.$flex, flex_pub.gevent_flash_onReady, handFlexOnReady);
                $.gevent.subscribe(jqueryMap.$flex, flex_pub.gevent_flash_onUploadComplete, onUploadComplete);
                $.gevent.subscribe(jqueryMap.$flex, flex_pub.gevent_flash_onUploadError, onUploadError);
                $.gevent.subscribe(jqueryMap.$flex, flex_pub.gevent_flash_onConnectError, onConnectError);
                $.gevent.subscribe(jqueryMap.$flex, flex_pub.gevent_flash_onStop, onFlashStop);

            };
            /**
             * flex准备完毕
             */
            handFlexOnReady = function(event, data) {
                $.observable(viewMap).setProperty({
                    "state" : 1,
                    playSate : 1
                });
                configMap.isWaitSetModel = false;
                if (configMap.timeInterval) {
                    configMap.mediaTime = 0;
                    clearInterval(configMap.timeInterval);
                }
                if (configMap.model == 4) {//监控
                    delete configMap.flexModelQueueMap.Monitor;
                    $.gevent.publish(configMap.pubMap.gevent_help_flex_monitorOnReady, true);
                    settingAction.subMonitor(true);
                    hideFlex(true);//隐藏不重置
                    jqueryMap.$container.hide();
                    if (configMap.flexModelQueueMap.callBack) {
                        configMap.flexModelQueueMap.callBack(configMap.flexModelQueueMap.callBack.option);
                        configMap.flexModelQueueMap.callBack = null;
                    }
                } else if (configMap.model == 2 || configMap.model == 3) {
                    settingAction.subMonitor(false);
                    //显示录制时长
                    $.observable(viewMap).setProperty("mediaRecordTime", "00:00:00");
                    if (configMap.model == 2) { //音频模式要隐藏flex
                        hideFlex(true);
                    }
                    configMap.timeInterval = setInterval(function() {
                        configMap.mediaTime++;
                        var timeStr = timeUtil.getFormatTime(configMap.mediaTime);
                        $.observable(viewMap).setProperty("mediaRecordTime", timeStr || "00:00:00");
                        if(!configMap.mediaLimit){
                            return;
                        }
                        if (configMap.mediaTime >= configMap.mediaLimit) { //超过录制的限制时间
                            //清除定时器
                            if (configMap.timeInterval) {
                                window.clearInterval(configMap.timeInterval);
                                configMap.timeInterval = null;
                            }
                            //触发录制完成
                            if (configMap.model == 2) {
                                jqueryMap.$root.find('.audio-end').trigger("click");
                                return false;
                            } else if (configMap.model == 3) {
                                jqueryMap.$root.find('.video-end').trigger("click");
                                return false;
                            }
                        }

                    }, 1000);
                }
            };
            /**
             * 上传完成后
             */
            onUploadComplete = function(event, data) {
                msg.getWaiting().hide();
                $.observable(viewMap).setProperty("isCallServe", false);
                data = JSON.parse(data) || {};
                var err = data.errorCode;
                var url = data.data;
                configMap.urlAnswer = url;
                if (err != 0) {
                    //业务错误
                    data.busiErr = true;
                    onUploadError(event, data);
                    return false;
                }
                $.observable(viewMap).setProperty({
                    state : 0,
                    urlAnswer : url
                });
                if (configMap.model == 1) {
                    closePhotoMark();
                    hideFlex();
                    //获取url
                    $.gevent.publish(configMap.pubMap.gevent_help_flex_getPhotoUrl, url);
                } else if (configMap.model == 2) {
                    stopPlay();
                    $.gevent.publish(configMap.pubMap.gevent_help_flex_getAudioAnswer, {
                        urlAnswer : url,
                        answerTime : configMap.mediaTime
                    });
                    if (configMap.isExamCallUpload === true) {
                        $.gevent.publish(configMap.pubMap.gevent_help_flex_isUploadComplete, [ {
                            media : 'audio',
                            'result' : true
                        } ]);
                        configMap.isExamCallUpload = false;
                    }
                } else if (configMap.model == 3) {
                    stopPlay();
                    $.gevent.publish(configMap.pubMap.gevent_help_flex_getVideoAnswer, {
                        urlAnswer : url,
                        answerTime : configMap.mediaTime
                    });
                    if (configMap.isExamCallUpload === true) {
                        $.gevent.publish(configMap.pubMap.gevent_help_flex_isUploadComplete, [ {
                            media : 'video',
                            'result' : true
                        } ]);
                        configMap.isExamCallUpload = false;
                    }
                }
            };
            /**
             * 上传错误
             */
            onUploadError = function(event, data) {
                msg.getWaiting().hide();
                $.observable(viewMap).setProperty("isCallServe", false);
                if (configMap.model == 1) {
                    var err = "上传照片失败，请重试或联系管理员！";
                    if (data && data.busiErr) {
                        err = "上传照片发生了错误，请重试或联系管理员！";
                    }
                    msg.showMsg(err, "warning");
                } else if (configMap.model == 2) {
                    if (configMap.isExamCallUpload === true) {
                        $.gevent.publish(configMap.pubMap.gevent_help_flex_isUploadComplete, [ {
                            media : 'audio',
                            'result' : false
                        } ]);
                        configMap.isExamCallUpload = false;
                    } else {
                        var err = "上传音频失败，请重试或联系管理员！";
                        if (data && data.busiErr) {
                            err = "上传音频发生了错误，请重试或联系管理员！";
                        }
                        msg.showMsg(err, "warning");
                    }
                } else if (configMap.model == 3) {
                    if (configMap.isExamCallUpload === true) {
                        $.gevent.publish(configMap.pubMap.gevent_help_flex_isUploadComplete, [ {
                            media : 'video',
                            'result' : false
                        } ]);
                        configMap.isExamCallUpload = false;
                    } else {
                        var err = "上传视频失败，请重试或联系管理员！";
                        if (data && data.busiErr) {
                            err = "上传视频发生了错误，请重试或联系管理员！";
                        }
                        msg.showMsg(err, "warning");
                    }
                }
            };
            /**
             * 连接服务错误
             */
            onConnectError = function(event, data) {
                msg.getWaiting().hide();
                $.observable(viewMap).setProperty("isCallServe", false);
                msg.getWaiting().showWithAction('网络不给力：连接不到服务，请重试或者换一个网络环境再答！', true, [ {
                    label : '重试一下',
                    type : 'success',
                    action : function() {
                        msg.getWaiting().hide();
                        if (configMap.model == 1) {
                            //拍照
                            flash_setPhotoMode();
                            takePhoto();
                            msg.getWaiting().hide();
                        } else {
                            //重新录制
                            if (configMap.model == 2) {
                                flash_setAudioMode(configMap.testId, configMap.questionId);
                            } else if (configMap.model == 3) {
                                flash_setVideoMode(configMap.testId, configMap.questionId);
                            }
                            takeRecord();
                        }
                    }
                }, {
                    label : '关闭',
                    type : 'success',
                    action : function() {
                        msg.getWaiting().hide();
                    }
                } ]);
            };
            /**
             * flash回放完毕
             */
            onFlashStop = function(event, data) {
                if (viewMap.urlAnswer) {
                    stopPlay();
                }
            };
            /**
             * 停止播放
             */
            stopPlay = function() {
                hideFlex();
                if (configMap.model == 2 || configMap.model == 3) {
                    //音频题
                    $.observable(viewMap).setProperty({
                        'state' : 0,
                        'playSate' : 0
                    });
                }
            };
            bindEvent = function() {
                if (stateMap.isBindEvent === true) {
                    return false;
                }
                window.onbeforeunload = function(e) {
                    flash_reset();//刷新和关闭页面的时候reset flash
                };
                /*调用模态和离开次数的时候要处理flex的显示状态*/
                $('body').on('shown.bs.modal shown.msg.modal exam-openSwitch exam-openProgress', function(e) {
                    removeFlex();
                });
                $('body').on('hidden.bs.modal hidden.msg.modal exam-closeSwitch exam-closeProgress', function(e) {
                    recoverFlex();
                });
                jqueryMap.$container.on("click", '.closePhotoMark', function(event) {
                    //隐藏模态层
                    closePhotoMark();
                    //隐藏摄像头
                    hideFlex();
                }).on('click', '.takePhoto', function(event) {
                    //拍照
                    takePhoto();
                    return false;
                }).on('click', '.retakePhoto', function(event) {
                    //重拍照片
                    flash_setPhotoMode();
                }).on('click', '.confirmPhoto', function(event) {
                    //确定上传照片
                    uploadPhoto();
                });
                jqueryMap.$root.on('click', '.setFlex', function(event) {
                    //设置FLEX
                    flash_showSettings();
                    //设置flex容器的位置
                    setFlexOffset();
                    return false;
                }).on('click', '.audio-start ', function(event) { //开始录音
                    if (viewMap.urlAnswer) {
                        //看回放
                        $.observable(viewMap).setProperty({
                            'state' : 1,
                            'playSate' : 1
                        });
                        flash_setAudioMode(configMap.testId, configMap.questionId);
                        flashPlay();
                    } else {
                        //1.设置flex位置
                        setFlexOffset();
                        //开始录制
                        takeRecord();
                    }
                }).on('click', '.video-start', function(event) {//开始录像
                    if (viewMap.urlAnswer) {
                        //看回放
                        $.observable(viewMap).setProperty({
                            'state' : 1,
                            'playSate' : 1
                        });
                        viewMap.urlAnswer = configMap.urlAnswer;
                        flash_setVideoMode(configMap.testId, configMap.questionId);
                        flashPlay();
                        //1.设置flex位置
                        setFlexOffset();
                    } else {
                        //1.设置flex位置
                        setFlexOffset();
                        //开始录制
                        takeRecord();
                    }
                }).on('click', '.takePhoto-start', function(event) {
                    //开始拍照
                }).on('click', '.audio-end', function(event) {//录制音频结束
                    //1.停止录音
                    flash_stop();
                    //2.停止计时
                    if (configMap.timeInterval) {
                        clearInterval(configMap.timeInterval);
                    }
                    //加遮罩
                    msg.getWaiting().show('正在上传音频...');
                    //3.上传
                    uploadMedia();
                    return false;
                }).on('click', '.video-end', function(event) {//录制视频结束
                    //   1.停止录像
                    flash_stop();
                    //2.停止计时
                    if (configMap.timeInterval) {
                        clearInterval(configMap.timeInterval);
                    }
                    //3.加遮罩
                    msg.getWaiting().show('正在上传视频...');
                    //4.上传
                    uploadMedia();
                    return false;
                });
                $(window).on('resize', function(e) {
                    if (viewMap.model === 3 && viewMap.playSate == 0) {
                        return;
                    }
                    if (viewMap.model == 3) {
                        setFlexOffset();
                    }
                });
                $('body').on('bodyShowScroll bodyHideScroll', function(e) {
                    if (viewMap.model == 3) {
                        setFlexOffset();
                    }
                });
                stateMap.isBindEvent === true;
            };
            /**	
             * 设置flex位置
             */
            setFlexOffset = function() {
                if (flex_isLoad === false) {
                    return;
                }
                //拍照模式
                if (viewMap.model == 1 && !jqueryMap.$container.is(":visible")) {
                    return;
                }
                jqueryMap.$flex.width(jqueryMap.$flexWindow.width());
                jqueryMap.$flex.height(jqueryMap.$flexWindow.height());
                jqueryMap.$flex.css({
                    left : jqueryMap.$flexWindow.offset().left,
                    top : jqueryMap.$flexWindow.offset().top
                }).removeClass("flex-close");
                if (viewMap.model == 4 || viewMap.model == 1) {
                    jqueryMap.$flex.css({
                        'z-index' : 13
                    });
                } else {
                    jqueryMap.$flex.css({
                        'z-index' : 11
                    });
                }
                $.observable(viewMap).setProperty("isShow", true);
            };
            /**
             * 加载flex
             */
            loadFlex = function(requiredDevices, callBack) {
                configMap.isCallFlex = true;
                if (flex_isLoad === true) {
                    return;
                }
                var hasFlash = flashChecker();
                if (!hasFlash) {
                    //提示安装flash
                    alertFlashInstalled();
                    return false;
                } else {
                    //设置参数
                    flash_setProperty({
                        red5Url : properties.red5Url,
                        baseUrl : properties.baseUrl,
                        monitorUrl : properties.monitorUrl,
                        checkFreq : properties.checkFreq
                    });
                    //1.订阅flex创建完毕的回调
                    $.gevent.subscribe(jqueryMap.$container, flex_pub.gevent_flash_onCreationComplete, function() {
                        var device_isOk = true;
                        //2.检查摄像头
                        if (isNeedCamera(requiredDevices)) {
                            device_isOk = checkCamera();
                            if (!device_isOk) {
                                window.location.href = utils.getRoot() + "app/exam/error.jsp?state=noCamera";
                            }
                        }
                        //3.检查麦克风
                        if (isNeedMicrophone(requiredDevices)) {
                            device_isOk = checkMicrophone();
                            if (!device_isOk) {
                                window.location.href = utils.getRoot() + "app/exam/error.jsp?state=noMicrophone";
                            }
                        }
                        if (device_isOk) {
                            flex_isLoad = true;
                            $.observable(viewMap).setProperty("flex_isLoad", true);
                            callBack();
                        }
                    });
                    //2.初始化flex
                    createSwfObject();
                }
                subscribeEvent();
                bindEvent();
            };
            /**
             * 隐藏flex 
             */
            hideFlex = function(notRest) {
                if (flex_isLoad === false) {
                    return;
                }
                //重置摄像头
                if (notRest) {
                    configMap.flexReset = true;
                } else {
                    configMap.isWaitSetModel =false;//不再等待设置模式
                    flash_reset();
                }
                jqueryMap.$flex.removeClass().css({
                    top : '-1000px'
                }).addClass("flex-close");
                $.observable(viewMap).setProperty("isShow", false);
            };
            /**
             * 移除flex
             * 在触发modal的时候移除flex的高度
             */
            removeFlex = function() {
                if (!jqueryMap.$flexWindow) {
                    return;
                }
                $.observable(viewMap).setProperty("removeFlex", true);
                configMap.flexWidth = jqueryMap.$flexWindow.width();
                configMap.flexHeight = jqueryMap.$flexWindow.height();
                if (configMap.model != 3) {
                    return;
                }
                jqueryMap.$flex.height(0);
                jqueryMap.$flex.width(0);
            };
            recoverFlex = function() {
                if (configMap.model != 3 || !jqueryMap.$flex) {
                    return;
                }
                $.observable(viewMap).setProperty("removeFlex", false);
                jqueryMap.$flex.height(configMap.flexHeight);
                jqueryMap.$flex.width(configMap.flexWidth);
            };
            /**
             * 判断是否需要麦克风
             */
            isNeedMicrophone = function(deviceType) {
                var state = deviceType & constant.DeviceType.MICROPHONE;
                return !!state;//把state强转为boolean
            };

            /**
             * 判断是否需要摄像头
             */
            isNeedCamera = function(deviceType) {
                var state = deviceType & constant.DeviceType.CAMERA;
                return !!state;//把state强转为boolean
            };
            //提示需要安装flash
            alertFlashInstalled = function() {
                jqueryMap.$flex.css({
                    top : 0
                });
                jqueryMap.$flex.find('#flashContent').show();
                $('#exam').hide();
            };
            //是否已加载flex
            isLoadFlex = function() {
                return flex_isLoad;
            };
            /**
             * 拍照模式
             */
            setPhotoMode = function($container) {
                jqueryMap.$container = stateMap.$container;
                if (configMap.isWaitSetModel == false) {
                    configMap.model = 1;
                    viewMap.model = configMap.model; //1.拍照；2；音频；3；视频
                    viewMap.state = 0;
                    viewMap.playSate = 0;
                    viewMap.urlAnswer = "";
                    initTemplate(stateMap.$container);
                    jqueryMap.$container.show();
                    flex_modal_isShow = true;
                }
                if (flex_isLoad === true) {
                    if (configMap.checkFlexTimeout) {
                        clearTimeout(configMap.checkFlexTimeout);
                        delete configMap.checkFlexTimeout;
                    }
                    var hasCamera = checkCamera();
                    if (hasCamera) {
                        flash_setPhotoMode();
                        configMap.isWaitSetModel = false;
                        setFlexOffset();
                    }
                } else {
                    if (configMap.isCallFlex != true) {
                        loadFlex();
                    }
                    configMap.isWaitSetModel = true;
                    configMap.checkFlexTimeout = setTimeout(function() {
                        setPhotoMode($container);
                    }, 500);
                }
            };
            /**
             * 设置音频模式
             * option:
             * $container : 容器
             * testId ：测试id
             * questionId ： 题目id
             * [urlAnswer]：答案
             * [elapsedTime] :答题时间
             */
            setAudioMode = function(option) {
                if (configMap.flexModelQueueMap.Monitor) {
                    //正在设置监控模式
                    configMap.flexModelQueueMap.callBack = arguments.callee;
                    configMap.flexModelQueueMap.callBack.option = option;
                    return;
                }
                if (!option.$container) {
                    return;
                }
                if (configMap.isWaitSetModel == false || flex_isLoad === true) {
                    //缓存jquery对象
                    jqueryMap.$container = option.$container;
                    configMap.testId = option.testId || 0;
                    configMap.questionId = option.questionId || 0;
                    configMap.urlAnswer = option.urlAnswer || '';
                    configMap.mediaTime = option.mediaTime || 0;
                    //渲染页面
                    configMap.model = 2;
                    viewMap.model = configMap.model; //1.拍照；2；音频；3；视频
                    viewMap.state = 0;
                    viewMap.playSate = 0;
                    viewMap.urlAnswer = "";
                    if (configMap.urlAnswer) {
                        viewMap.urlAnswer = configMap.urlAnswer;
                        var timeStr = timeUtil.getFormatTime(configMap.mediaTime);
                        viewMap.mediaRecordTime = timeStr;
                    }
                    initTemplate(jqueryMap.$container);
                    jqueryMap.$container.find(".audio-help").tooltip(
                            {
                                placement : 'left',
                                html : true,
                                title : '<div class="tl">' + '1. 系统上没有安装flash？视频题需要使用flash技术进行录音。<br/>'
                                        + '2.检测不到麦克风？电脑上如果没有安装麦克风，或者麦克风设备不正常，请换一台麦克风设备正常可用的电脑进行答题。<br/>'
                                        + '3.flash设置为拒绝？请检查flash设置中是否允许使用麦克风。<br/>' + '</div>',
                                trigger : 'hover',
                                container : 'body'
                            });
                }
                if (flex_isLoad === true) {
                    if (configMap.checkFlexTimeout) {
                        clearTimeout(configMap.checkFlexTimeout);
                        delete configMap.checkFlexTimeout;
                    }
                    flash_setAudioMode(configMap.testId, configMap.questionId);
                    settingAction.subMonitor(false);
                    configMap.isWaitSetModel = false;
                } else {
                    configMap.isWaitSetModel = true;
                    if (configMap.isCallFlex != true) {
                        loadFlex();
                    }
                    clearTimeout(configMap.checkFlexTimeout);
                    configMap.checkFlexTimeout = setTimeout(function() {
                        setAudioMode(option);
                    }, 500);
                }
            };
            /**
             * 设置视频模式
             * 	option:
             * $container : 容器
             * testId ：测试id
             * questionId ： 题目id
             * [urlAnswer]：答案
             * [elapsedTime] :答题时间
             */
            setVideoMode = function(option) {
                if (configMap.flexModelQueueMap.Monitor) {
                    //正在设置监控模式
                    configMap.flexModelQueueMap.callBack = arguments.callee;
                    configMap.flexModelQueueMap.callBack.option = option;
                    return;
                }
                if (!option.$container) {
                    return;
                }
                //1.缓存jquery对象
                jqueryMap.$container = option.$container;
                configMap.testId = option.testId || 0;
                configMap.questionId = option.questionId || 0;
                configMap.urlAnswer = option.urlAnswer || '';
                configMap.mediaTime = option.mediaTime || 0;
                //2.	渲染页面
                if (configMap.isWaitSetModel == false) {
                    configMap.model = 3;
                    viewMap.model = configMap.model; //1.拍照；2；音频；3；视频
                    viewMap.state = 0;
                    viewMap.playSate = 0;
                    viewMap.urlAnswer = "";
                    if (configMap.urlAnswer) {
                        viewMap.urlAnswer = configMap.urlAnswer;
                        var timeStr = timeUtil.getFormatTime(configMap.mediaTime);
                        viewMap.mediaRecordTime = timeStr;
                    }
                    initTemplate(jqueryMap.$container);
                    jqueryMap.$container.find(".video-help").tooltip(
                            {
                                placement : 'left',
                                html : true,
                                title : '<div class="tl">' + '1. 系统上没有安装flash？视频题需要使用flash技术进行录像。<br/>'
                                        + '2.检测不到摄像头？电脑上如果没有安装摄像头，或者摄像头设备不正常，请换一台摄像头设备正常可用的电脑进行答题。<br/>'
                                        + '3.flash设置为拒绝？请检查flash设置中是否允许使用摄像头和麦克风。<br/>' + '</div>',
                                trigger : 'hover',
                                container : 'body'
                            });
                }
                if (flex_isLoad === true) {
                    if (configMap.checkFlexTimeout) {
                        clearTimeout(configMap.checkFlexTimeout);
                        delete configMap.checkFlexTimeout;
                    }
                    flash_setVideoMode(configMap.testId, configMap.questionId);
                    settingAction.subMonitor(false);
                    configMap.isWaitSetModel = false;
                } else {
                    //msg.getWaiting().show("正在加载flash...");
                    configMap.isWaitSetModel = true;
                    if (configMap.isCallFlex != true) {
                        loadFlex();
                    }
                    clearTimeout(configMap.checkFlexTimeout);
                    configMap.checkFlexTimeout = setTimeout(function() {
                        setVideoMode(option);
                    }, 500);
                }
            };
            /**
             * 设置监控模式
             */
            setMonitorMode = function(testId) {
                configMap.testId = testId || 0;
                configMap.flexModelQueueMap.Monitor = true;
                if (configMap.isWaitSetModel == false || flex_isLoad === true) {
                    configMap.model = 4;
                    viewMap.model = configMap.model;
                    viewMap.state = 0;
                    viewMap.playSate = 0;
                    viewMap.urlAnswer = "";
                    initTemplate(stateMap.$container);
                    stateMap.$container.find(".flex-setting-help").tooltip(
                            {
                                placement : 'right',
                                html : true,
                                title : '<div class="tl">' + '1. 系统上没有安装flash？系统需要使用flash技术进行监考，请先去官网下载安装flash。<br/>'
                                        + '2.检测不到摄像头？电脑上如果没有安装摄像头，或者摄像头设备不正常，请换一台摄像头设备正常可用的电脑进行答题。<br/>'
                                        + '3.flash设置为拒绝？请点击【设置】检查flash设置中是否允许使用摄像头和麦克风。<br/>' + '</div>',
                                trigger : 'hover',
                                container : 'body'
                            });
                    jqueryMap.$container = stateMap.$container;
                    jqueryMap.$container.show();
                    flex_modal_isShow = true;
                }
                if (flex_isLoad === true) {
                    if (configMap.checkFlexTimeout) {
                        clearTimeout(configMap.checkFlexTimeout);
                        delete configMap.checkFlexTimeout;
                    }
                    var isMuted = flash_isMuted();
                    var hasCamera = checkCamera();
                    if (!hasCamera) {
                        //需要跳错误页面，不能继续考试
                    }
                    flash_setMonitorMode(testId);
                    if (isMuted) {//禁用了摄像头要让用户设置
                        setFlexOffset();
                    } else {
                        configMap.isWaitSetModel = false;
                        delete configMap.flexModelQueueMap.Monitor;
                    }
                } else {
                    if (configMap.isCallFlex != true) {
                        loadFlex();
                    }
                    configMap.isWaitSetModel = true;
                    configMap.checkFlexTimeout = setTimeout(function() {
                        setMonitorMode(testId);
                    }, 500);
                }
            };
            /**
             * 关闭模态窗
             */
            closePhotoMark = function(isReset) {
                jqueryMap.$container.hide();
                //关闭拍照模式下的遮罩
                jqueryMap.$flex.removeClass('flexmodal').css({
                    top : '-100%'
                });
            };
            /**
             * 拍照
             */
            takePhoto = function() {
                //1.调用flex拍照
                flash_takePhoto();
                //2.更新状态
                $.observable(viewMap).setProperty("state", 2);
            };
            /**
             * 上传照片
             */
            uploadPhoto = function() {
                //确定上传照片
                $.observable(viewMap).setProperty("isCallServe", true);
                flash_uploadPhoto();
            };
            /**
             * 上传多媒体
             */
            uploadMedia = function() {
                $.observable(viewMap).setProperty("isCallServe", true);
                flash_uploadMedia();
                $.gevent.publish(configMap.pubMap.gevent_help_flex_isUpload, true);
                $('body').trigger("flex_record_over");
            };
            /**
             * 录制
             */
            takeRecord = function() {
                flash_record();//调用flex录制
                $('body').trigger("flex_record");
            };

            /**
             * 检查摄像头
             */
            checkCamera = function() {
                var hasCamera = flash_hasCamera();
                if (!hasCamera) {
                    msg.showMsg("没有检测到摄像头，本次测试需要使用摄像头,请确保安装了摄像头并启用摄像头！", "warning");
                    return false;
                }
                return true;
            };
            /**
             * 查看麦克风
             */
            checkMicrophone = function() {
                var hasMicrophone = flash_hasMicrophone();
                if (!hasMicrophone) {
                    msg.showMsg("没有检测到麦克风，本次测试需要使用麦克风,请确保安装了麦克风并启用麦克风！", "warning");
                }
                return true;
            };
            /**
             * 播放
             */
            flashPlay = function() {
                if (viewMap.urlAnswer) {
                    if (configMap.flexReset == true) {
                        if (configMap.model == 2) {
                            flash_setAudioMode(configMap.testId, configMap.questionId);
                        } else if (configMap.model == 3) {
                            flash_setVideoMode(configMap.testId, configMap.questionId);
                        }
                    }
                    flash_play(viewMap.urlAnswer);
                    configMap.flexReset == false;
                }
            };
            /**
             * flex是否初始化
             */
            isFlexInit = function() {
                if (viewMap.state == 0) {
                    return true;
                }
                return false;
            };
            /**
             * 考试端调用上传
             * type:audio:音频；video:视频
             */
            uploadMediaByExam = function(type) {
                configMap.isExamCallUpload = true;//是否考试端调用的上传
                if (type == "audio") {
                    jqueryMap.$root.find('.audio-end').trigger('click');
                } else if (type == "video") {
                    jqueryMap.$root.find('.video-end').trigger('click');
                }
            };
            /**
             * 浏览器设置media
             * mediaType:audio ,video
             */
            checkUserMedia = function(mediaType, callBack) {
                console.log("checkUserMedia");
                if (!mediaType) {
                    return;
                }
                callBack = callBack || configMap.userMediaCallBack;
                navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia
                        || navigator.mozGetUserMedia || navigator.msGetUserMedia;
                if (!navigator.getUserMedia) {//如果没有直接执行回调
                    $.observable(viewMap).setProperty({
                        "setUserMediaInfo" : "",
                        "setUserMedia" : true
                    });
                    if (callBack && typeof callBack == "function") {
                        callBack();
                    }
                    return;
                }
                var successCallBack = function(e) {
                    $.observable(viewMap).setProperty({
                        "setUserMediaInfo" : "",
                        "setUserMedia" : true
                    });
                    if (callBack && typeof callBack == "function") {
                        callBack();
                    }
                };
                configMap.userMediaCallBack = null;
                delete configMap.userMediaCallBack;
                var failureCallBack = function(e) {
                    var errMsg = "", media = mediaType == "video" ? "视频" : "麦克风";
                    errMsg = "不能获取" + media + "的权限。<br/>";
                    errMsg += "如果您不小心点击了拒绝按钮请在浏览器的【设置菜单】的媒体设置中允许本网站使用" + media + "。<br/>";
                    errMsg += "如果您不小心点击了关闭按钮请点击下面的【设置按钮】后设置为允许。";
                    $.observable(viewMap).setProperty({
                        "setUserMediaInfo" : errMsg,
                        "mediaType" : mediaType
                    });
                    configMap.userMediaCallBack = callBack;
                };
                if (mediaType == "video") {
                    navigator.getUserMedia({
                        video : !0
                    }, successCallBack, failureCallBack);
                } else if (mediaType == "audio") {
                    navigator.getUserMedia({
                        audio : !0
                    }, successCallBack, failureCallBack);
                }
            };
            return {
                loadFlex : loadFlex,
                hideFlex : hideFlex,
                isFlexInit : isFlexInit,
                alertFlashInstalled : alertFlashInstalled,
                isloadFlex : isLoadFlex,
                isNeedMicrophone : isNeedMicrophone,
                isNeedCamera : isNeedCamera,
                setPhotoMode : setPhotoMode,
                setAudioMode : setAudioMode,
                setVideoMode : setVideoMode,
                setMonitorMode : setMonitorMode,
                uploadMediaByExam : uploadMediaByExam,
                pub : configMap.pubMap,
                removeFlex : removeFlex,
                recoverFlex : recoverFlex
            };
        });
