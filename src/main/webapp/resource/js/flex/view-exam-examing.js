/*
 * view-exam-examing.js
 * 考试视图
 * @author zengjie 下午3:26:09
*/

define(
        "app/exam/view-exam-examing",
        function(require, exports, module) {
            var flexHelp = require('app/exam/views-help-flex'), route = require('core/route'), gevent = require('core/gevent'), timeView = require('app/time/view-timeCountDown'), partView = require('app/exam/view-exam-part'), listeningLeaveView = require('app/exam/view-exam-listeningLeave'), questionViewHelp = require('app/exam/views-help-question'), constant = require("core/constant"), //
            msg = require('core/msg'), paperModel = require('app/exam/model-exam-paper'), utils = require('core/utils'), //
            partAction = require('app/exam/action-exam-part'), questionAction = require('app/exam/action-exam-question'), //
            settingAction = require('app/exam/action-exam-setting'), //
            configMap = {
                heartbeat : 60, //发送心跳的频率为1分钟，60秒
                timeCountModel : 0,//倒计时模式，0：部分计时，1：试卷计时；2：单题计时
                isMonitor : true,//是否监控
                isSendHeart : true,//是否发送心跳
                isCountDown : true,//是否倒计时
                isListeningLeave : true,//是否监听离开页面
                isShowProgress : true,//是否显示答题进度
                isShowQuestionPanel : true,//是否显示题目面板
                isOk : false,//环境是否准备好
                testInfo : {}, //当前测试实例
                currentPart : {//当前部分
                    partId : null,
                    seq : 0,//当前部分序号，从0开始
                    backward : true,
                    timeLimit : 0
                //当前部分的题是否允许回溯，默认为允许
                },
                currentQuestion : {//当前题目
                    questionNum : 0,//当前是第几个题
                    questionSeq : 0,//当前题序号,从0开始,不含子题目
                    subSeq : -1,//-1标示无效序号
                    questionId : null,
                },
                forwardQuestion : {
                    questionNum : 0
                //跳转到第几个题
                },
                submitCallBack : null
            //提交的回调
            }, //
            stateMap = {
                $container : null,
                $showContainer : $("#exam-formal-examing"),
                selfView : "exam",
                isInit : false,
                isUnload : false
            }, jqueryMap, setJqueryMap, //
            viewMap = {
                model : 'formal',
                questionError : false,
                isFirst : true,
                isLast : false,
                isPageLast : false,
                isWideLayout : false,
                isCallServer : true,
                question : {}
            //当前题目
            }, confimViewMap = {
                content : "",
                target : 0
            //1:提交部分；2：交卷；3：自动提交部分；4：自动交卷;5:已交卷状态;6:离开次数达到极限
            }, //
            checkPaperState, startExam, startPart, startTrackPart, startFirstQuestion, finishExam, makeSubscribeQueue, toSubmitQuestion, subscribeLeave, //
            confirmSubmit, confirmGoNextPart, confirmBox, alertBox, //
            getPrev, getNext, submitPart, submitPaper, checkQuestion, checkFlexUpload, //
            refreshViewByQuestion, dealServeError, changeProgramLang, //
            refreshQuestion, rendQuestion, beforeRefreshQuestion, afterRefreshQuestion, setTimeCountDown, stopTimeCountDown, //
            handShow, unload, handGetTestByCandidate, handStartExam, handQueryQuestion, handHandInPaper, handHandInPart, handHandInQuestion, handStartPart, //
            prepareEnvironment, unloadEnviroment, handArriveSwitchLimit, handGetPointQuestion, //
            handGetAudioAnswer, handGetVideoAnswer, handGetSubQuestionAnswer, handTimeOver, //
            handFlexUploadCompleteAfterSubmit, isFirstQuestion, isLastQuestion, forwardFinish, forwardPaperInfo, subscribeSendHeart, setHeartTime, throwError, //
            bindEvent, geventEvent, initModule, initTemplate, clearStorage;
            //-----------------------START DOM METHOD ---------------------------------------//
            setJqueryMap = function() {
                $("#sets-header-logoarea").addClass("narrow");
                var $container = stateMap.$container;
                jqueryMap = {
                    $container : $container
                };
            };
            initTemplate = function() {
                $.templates({
                    examQuestionTmpl : "#exam-question-tmpl"
                });
                $.link.examQuestionTmpl(jqueryMap.$container, viewMap);
                $.observe(viewMap, "isWideLayout", function(ev, eventArgs) {
                    if (eventArgs.value == true) {
                        $("#sets-header-logoarea").removeClass("narrow");
                    } else {
                        $("#sets-header-logoarea").addClass("narrow");
                    }
                });
            };
            clearStorage = function() {
                utils.storage.removeItem('exam-timeCountDown');//清除倒计时的缓存
                if (configMap.sendHeartTimeout) {
                    window.clearTimeout(configMap.sendHeartTimeout);
                }
            };
            //-----------------------END DOM METHOD -----------------------------------------//

            //-----------------------START EVENTBIND METHOD ---------------------------------------//
            makeSubscribeQueue = function() {
                stateMap.subscribeQueue = {
                    getTestByCandidate : {//根据姓名邮箱获取测试的回调
                        event : paperModel.pub.gevent_getTestByCandidateFeedBack,
                        feedBack : handGetTestByCandidate
                    },
                    startExam : {//开始考试   
                        event : gevent.gevent.startExamFeedBack,
                        feedBack : handStartExam
                    },
                    handInPaper : {//交卷
                        event : gevent.gevent.handInPaperFeedBack,
                        feedBack : handHandInPaper
                    },
                    startPart : {//开始部分
                        event : gevent.gevent.startPartFeedBack,
                        feedBack : handStartPart
                    },
                    handInPart : {//提交部分
                        event : gevent.gevent.handInPartFeedBack,
                        feedBack : handHandInPart
                    },
                    getPointQuestion : {//题板跳题
                        event : partView.pub.gevent_getPointQuestion,
                        feedBack : handGetPointQuestion
                    },
                    queryQuestion : {//取题
                        event : gevent.gevent.queryQuestionFeedBack,
                        feedBack : function(event, data) {
                            handQueryQuestion(event, data);
                        }
                    },
                    handInQuestion : {//提交题
                        event : gevent.gevent.submitQuestionFeedBack,
                        feedBack : handHandInQuestion
                    },
                    getAudioAnswer : {//获取音频答案
                        event : flexHelp.pub.gevent_help_flex_getAudioAnswer,
                        feedBack : handGetAudioAnswer
                    },
                    getVideoAnswer : {//获取视频答案
                        event : flexHelp.pub.gevent_help_flex_getVideoAnswer,
                        feedBack : handGetVideoAnswer
                    },
                    isFlexUpload : {
                        event : flexHelp.pub.gevent_help_flex_isUpload,
                        feedBack : function(event, data) {//音视频上传中要停止计时
                            if (data == true) {
                                timeView.stopTimeCountDown();
                            } else if (data == false) {
                                timeView.startTimeCountDown();
                            }
                        }
                    },
                    isFlexUploadComplete : {
                        event : flexHelp.pub.gevent_help_flex_isUploadComplete,
                        feedBack : handFlexUploadCompleteAfterSubmit
                    //音视频上传完成
                    },
                    monitorOnReady : {//监控准备好
                        event : flexHelp.pub.gevent_help_flex_monitorOnReady,
                        feedBack : function() {
                            if (configMap.isOk === true) {
                                return false;
                            }
                            configMap.isOk = true;
                            if (configMap.isListeningLeave === true && configMap.paperInfo.paperParam
                                    && configMap.paperInfo.paperParam.switchLimit && configMap.isOk == true) {
                                subscribeLeave();//监控准备好才注册页面切换
                            }
                            startExam();
                            return false;
                        }
                    },
                    updateSubQuestionAnswer : {//更新子题目
                        event : questionAction.pub.gevent_updateSubQuestionAnswer,
                        feedBack : handGetSubQuestionAnswer
                    }
                }
            };
            /**
             * 订阅或者取消订阅
             * state:true:订阅；false:取消订阅
             */
            geventEvent = function(state) {
                $.each(stateMap.subscribeQueue, function(index, value) {
                    var event = value.event, feedBack = value.feedBack;
                    if (state === true) {
                        $.gevent.subscribe(stateMap.$container, event, feedBack);
                    } else {
                        $.gevent.unsubscribe(stateMap.$container, event);
                    }
                });
            };
            bindEvent = function() {
                jqueryMap.$container.on('click', '.exam-prev-btn', function(event) {//上一题
                    getPrev();
                }).on('click', '.exam-next-btn', function(event) {//下一题
                    getNext();
                }).on('click', '.exam-submit-part-btn', function(event) {//提交部分,
                    submitPart();
                }).on('click', '.exam-submit-paper-btn ', function(event) {//交卷
                    submitPaper();
                }).on('keyup', '.exam-blankInput', function(event) {
                    var val = $(this).val();
                    if (val) {
                        var iCount = val.replace(/[^\u0000-\u00ff]/g, "aa");
                        $(this).attr('size', iCount.length + 1);
                    } else {
                        $(this).attr('size', 0);
                    }
                }).on(
                        'click',
                        ".program .programV2 .language-selecter .dropdown-menu>li",
                        function(event) {//编程题切换语言
                            var dataItem = $.view(this).data;
                            var key = dataItem.key;
                            var val = dataItem.value;
                            var origProgramLanguage = viewMap.question.program.candAnswer.origProgramLanguage;
                            if (origProgramLanguage == key) {
                                return;
                            } else {
                                var hasAnswer = questionAction.checkHasAnswer(viewMap.question);
                                if (hasAnswer) {
                                    confirmBox({
                                        confirm : "继续",
                                        content : "你已用当前语言答题，切换语言将丢失当前答案，是否继续？",
                                        onConfirm : function() {
                                            $.observable(viewMap.question.program.candAnswer).setProperty(
                                                    "programLanguageStr", val);
                                            changeProgramLang(key);
                                        }
                                    });
                                } else {
                                    $.observable(viewMap.question.program.candAnswer).setProperty("programLanguageStr",
                                            val);
                                    changeProgramLang(key);
                                }
                            }
                        }).on('click','.btn-answer-save',function(event){//保存答案
                            if($(this).hasClass('exam-fail-disabled')){
                                return false;
                            }
                            questionAction.submitCurrentQuestion();
                        });
            };
            //-----------------------END EVENTBIND METHOD ---------------------------------------//

            //-----------------------START EVENT HANDLERS---------------------------------------//
            handShow = function(event, data) {
                if (data && data.target == stateMap.selfView) {
                    configMap.isHistory = data.forwardTrigger == false ? true : false;
                    if (configMap.isHistory === true && viewMap.isCallServer === false) {//历史的后退前进处理
                        showHeadSet();
                        return false;
                    }
                    //window.location.replace(window.location.href);
                    data.forwardData = data.forwardData || route.getForwardData().forwardData || {};
                    //1.缓存
                    configMap.paperInfo = data.forwardData.paperInfo;
                    configMap.currentPaper = data.forwardData.currentPaper;
                    configMap.candidateInfo = data.forwardData.candidateInfo;

                    //3.解除绑定后重新绑定
                    if (stateMap.isInit === true && stateMap.isUnload === true) {
                        unload();
                        stateMap.isInit = false;
                        initModule(stateMap.$container);
                    }
                    //4.获取测评实例
                    if (data.relatedTarget && data.forwardData.testInfo) {
                        configMap.testInfo = data.forwardData.testInfo;
                        prepareEnvironment();
                    } else {
                        //刷新页面的，从服务端从新取数据
                        paperModel.getTestByCandidate(configMap.candidateInfo);
                        return false;
                    }
                }
            };
            /**
             * 卸裁事件和订阅
             */
            unload = function() {
                geventEvent(false);
                jqueryMap.$container.off();
                unloadEnviroment();
                stateMap.isUnload = true;
            };
            /**
             * 监听根据姓名邮箱获取测试
             */
            handGetTestByCandidate = function(event, data) {
                if (data.failure) {
                    dealServeError(data);
                } else {
                    configMap.testInfo = data;
                    prepareEnvironment();
                }
            };
            /**
             * 监听开始考试
             */
            handStartExam = function(event, data) {
                if (data.failure) {
                    dealServeError(data);
                } else {
                    //开始部分
                    try {
                        if (configMap.testInfo.testTrack
                                && configMap.testInfo.testTrack.partSeq != constant.SetsConst.PART_SEQ_PRACTICE) {
                            //1.1 还原轨迹
                            startTrackPart(configMap.testInfo.testTrack.partSeq,
                                    configMap.testInfo.testTrack.questionId);//服务端返回的seq是一个唯一ID，不能代表序号
                        } else {
                            //1.2 第一次进入，全局缓存的时间在第一次进入时设置为0
                            utils.storage.setItem('exam-timeCountDown', 0);
                            var part = configMap.testInfo.parts[0];
                            partAction.noticeStartPart(part.partSeq);
                        }
                        //2.设置计时
                        if (configMap.isCountDown == true && configMap.timeCountModel == 1
                                && configMap.testInfo.paperTime) {
                            //试卷计时
                            setTimeCountDown(configMap.testInfo.paperTime, configMap.testInfo.elapsedTime || 0);
                        }
                    } catch (e) {
                        throwError(e);
                    }

                }
            };
            /**
             * 监听开始部分
             */
            handStartPart = function(event, data) {
                if (data.failure) {
                    dealServeError(data);
                } else {
                    //1.渲染部分信息
                    try {
                        startPart(data.partId);
                    } catch (e) {
                        throwError(e);
                    }
                    // 2.获取第一个题目
                    startFirstQuestion();
                }
            };
            /**
             * 监听获取题目
             */
            handQueryQuestion = function(event, data) {
                $.observable(viewMap).setProperty("isCallServer", false);
                if (data.failure) {
                    dealServeError(data);
                } else {
                    try {
                        //1.处理刷新前置任务
                        var oldQuestion = questionAction.getCurrentQuestion();
                        beforeRefreshQuestion(oldQuestion, data);
                        //2.刷新视图
                        var currentQuestion = refreshQuestion(data);
                        //3. 刷新后置任务
                        if (currentQuestion !== null) {
                            afterRefreshQuestion(currentQuestion);
                        }
                    } catch (e) {
                        throwError(e, data);
                    }

                }
                return false;
            };
            /**
             * 监听提交答案
             */
            handHandInQuestion = function(event, data) {
                $.observable(viewMap).setProperty("isCallServer", false);
                if (data.failure) {
                    dealServeError(data);
                } else {
                    if (confimViewMap.target == 1 || confimViewMap.target == 2 || confimViewMap.target == 3
                            || confimViewMap.target == 4 || confimViewMap.target == 6) {
                        //提交部分或提交卷子（包含自动）
                        partAction.noticeSubmitPart();
                    } else {
                        //组合题提交
                        if (viewMap.question.combination) {
                            var questionIntro = partAction.getQuestionIntroByNum(configMap.forwardQuestion.questionNum);
                            if (questionIntro.subSeq != undefined) {
                                var oldQuestionIntro = partAction
                                        .getQuestionIntroByNum(configMap.currentQuestion.questionNum);
                                //1.处理刷新前置任务
                                var oldQuestion = viewMap.question.combination.subQuestionInfos[oldQuestionIntro.subSeq];
                                var asksubQuestion = viewMap.question.combination.subQuestionInfos[questionIntro.subSeq];
                                beforeRefreshQuestion(oldQuestion, asksubQuestion);
                                //2.刷新视图
                                console.log("子题目取题:");
                                var currentQuestion = refreshQuestion(asksubQuestion);
                                //3. 刷新后置任务
                                if (currentQuestion !== null) {
                                    afterRefreshQuestion(currentQuestion);
                                }
                            }
                        }else{//编程题保存答案
                            msg.showMsg('保存代码成功！','success');
                        }
                    }
                }
            };
            /**
             * 监听交卷
             */
            handHandInPaper = function(event, data) {
                if (data.failure) {
                    dealServeError(data);
                } else {
                    unload();
                    var contentMsg = "";
                    if (confimViewMap.target == 4) {
                        contentMsg = "当前测试的考试时间已到，系统已自动帮您交卷。"
                    } else if (confimViewMap.target == 5) {
                        contentMsg = "当前测试已交卷。"
                    } else if (confimViewMap.target == 6) {//达到最大离开次数
                        contentMsg = configMap.arriveSwitchLimitContent || null;
                        delete configMap.arriveSwitchLimitContent;
                    }
                    if (contentMsg) {
                        //自动交卷
                        alertBox({
                            content : contentMsg,
                            onConfirm : function() {
                                if (configMap.paperInfo && configMap.paperInfo.requiredDevices) {
                                    flexHelp.hideFlex();
                                }
                                finishExam();
                            }
                        });
                    } else {
                        finishExam();
                    }
                }
            };
            /**
             * 监听提交部分
             */
            handHandInPart = function(event, data) {
                if (data.failure) {
                    dealServeError(data);
                } else {
                    //1.清除掉提交的部分的计时
                    if (configMap.isCountDown == true && configMap.timeCountModel == 0) {
                        utils.storage.setItem('exam-timeCountDown', 0);
                    }
                    //2.标记当前部分已完成
                    partView.rendCurrentPart(configMap.currentPart.seq, false);
                    if (confimViewMap.target == 2 || confimViewMap.target == 4 || confimViewMap.target == 6) {//手动交卷/,自动交卷，
                        var testState = (confimViewMap.target == 4 || confimViewMap.target == 6) ? constant.TestState.SUBMITTED_AUTO_FRONT
                                : constant.TestState.SUBMITTED;
                        partAction.noticeSubmitPaper(testState);
                        return;
                    } else if (confimViewMap.target == 1) {//手动提交部分
                        confirmGoNextPart();
                    } else if (confimViewMap.target == 3) {//自动提交部分
                        confirmGoNextPart(true);
                    }
                }
            };
            /**
             * 监听获取音频答案
             */
            handGetAudioAnswer = function(event, data) {
                var question = questionAction.getCurrentQuestion();
                question.audio.candAnswer = question.audio.candAnswer || {};
                question.audio.candAnswer.urlAnswer = data.urlAnswer;
                question.audio.candAnswer.mediaTime = data.answerTime || 0;
                if (configMap.isOk == true) {
                    timeView.startTimeCountDown();
                }
            };
            /**
             * 监听获取视频答案
             */
            handGetVideoAnswer = function(event, data) {
                var question = questionAction.getCurrentQuestion();
                question.video.candAnswer = question.video.candAnswer || {};
                question.video.candAnswer.urlAnswer = data.urlAnswer;
                question.video.candAnswer.mediaTime = data.answerTime || 0;
                if (configMap.isOk == true) {
                    timeView.startTimeCountDown();
                }
            };
            /**
             * 提交后等待flex上传后的回调
             */
            handFlexUploadCompleteAfterSubmit = function(event, data) {
                var media = data.media, result = data.result, contentMsg = "";
                if (confimViewMap.target == 0 || confimViewMap.target == 1 || confimViewMap.target == 2) {//手动
                    if (result === false) {
                        if (media == "video") {
                            contentMsg = "当前视频题没有成功上传，作答无效，是否确定提交？";
                        } else if (media == "audio") {
                            contentMsg = "当前音频题没有成功上传，作答无效，是否确定提交？";
                        }
                        if (contentMsg) {
                            msg.confirm({
                                title : '提示',
                                content : contentMsg,
                                onConfirm : function() {
                                    flexHelp.hideFlex();
                                    if (configMap.isMonitor === true) {
                                        flexHelp.setMonitorMode(configMap.testInfo.testId);
                                    }
                                    configMap.submitCallBack();
                                    configMap.submitCallBack = null;
                                },
                                onCancel : function() {
                                    if (configMap.isOk == true) {
                                        timeView.startTimeCountDown();
                                    }
                                }
                            });
                            return false;
                        }
                    } else {
                        if (confimViewMap.target == 1) {//手动提交部分
                            confirmSubmit("part");
                        } else if (confimViewMap.target == 2) {//手动交卷
                            confirmSubmit("paper");
                        } else {
                            configMap.submitCallBack();
                            configMap.submitCallBack = null;
                        }

                    }
                } else if (confimViewMap.target == 3 || confimViewMap.target == 4 || confimViewMap.target == 6) {//自动提交
                    configMap.submitCallBack();
                    configMap.submitCallBack = null;
                }

            };
            /**
             * 监听获取子题目答案来更新视图答案
             */
            handGetSubQuestionAnswer = function(event, data) {
                var subSeq = configMap.currentQuestion.subSeq;
                if (viewMap.question.combination) {
                    var sub = viewMap.question.combination.subQuestionInfos[subSeq];
                    if (!sub) {
                        return;
                    }
                    if (sub.elapsedTime > data.answerTime) {
                        consle.log("更新子题目答题时间出错了：答题时间小于之前的时间");
                        return;
                    }
                    sub.elapsedTime = data.answerTime;
                    if (sub.choice) {
                        sub.choice.candAnswer = sub.choice.candAnswer || {};
                        if (data.answer && data.answer.optAnswer != undefined) {
                            sub.choice.candAnswer.optAnswer = data.answer.optAnswer;
                        }
                        if (data.answer && data.answer.textAnswer != undefined) {
                            sub.choice.candAnswer.textAnswer = data.answer.textAnswer;
                        }
                    } else if (sub.program) {
                        sub.program.candAnswer = sub.program.candAnswer || {};
                        sub.program.candAnswer.programAnswer = data.answer.programAnswer;
                    } else if (sub.audio) {
                        sub.audio.candAnswer = sub.audio.candAnswer || {};
                        sub.audio.candAnswer.urlAnswer = data.answer.urlAnswer;
                    } else if (sub.video) {
                        sub.video.candAnswer = sub.video.candAnswer || {};
                        sub.video.candAnswer.urlAnswer = data.answer.urlAnswer;
                    } else if (sub.blank) {
                        sub.blank.candAnswer = sub.blank.candAnswer || {};
                        sub.blank.candAnswer.blankAnswer = data.answer.blankAnswer;
                    } else if(sub.text ){
                        sub.text.candAnswer = sub.text.candAnswer || {};
                        sub.text.candAnswer.urlAnswer = data.answer.urlAnswer || '';
                    }
                }
            };
            /**
             * 监听超过离开次数
             */
            handArriveSwitchLimit = function(event, data) {
                confimViewMap.target = 6;
                if (data) {
                    configMap.arriveSwitchLimitContent = data;
                }
                console.log("handArriveSwitchLimit：监听达到离开次数极限");
                submitPaper(true);
            };
            /**
             * 监听设置跳题信息
             */
            handGetPointQuestion = function(event, data) { //传递下一个题的questionId
                var askNum = data.num;
                getAppoint(askNum);
            };
            /**
             * 监听倒计时结束
             */
            handTimeOver = function(event, data) {
                if (configMap.testInfo.paperTime) {
                    //试卷计时
                    submitPaper(true);
                } else {
                    //部分计时
                    if (configMap.currentPart.seq == configMap.testInfo.parts.length - 1) {
                        submitPaper(true);
                    } else {
                        submitPart(true);
                    }
                }
            };
            /**
             * 完成考试
             */
            finishExam = function() {
                if (configMap.paperInfo.grouped != true) {//1.普通试卷
                    forwardFinish();
                    return false;
                } else {//2.试卷组
                    var len = configMap.paperInfo.relations.length;
                    var currentPaperId = configMap.currentPaper.paperId;
                    if (configMap.paperInfo.relations[len - 1].paperId == currentPaperId) {//2.1 最后一张卷子
                        forwardFinish();
                        return false;
                    } else {//2.2找出下一张卷子
                        var isChoose = configMap.currentPaper.relationType == constant.RelationType.OR;//是否选考
                        var nextPaper = null, currentIndex = -1, chooseFinish = false;//
                        for (var i = 0; i < len; i++) { //遍历找出下一张试卷
                            var paperState = configMap.paperInfo.relations[i].testState;
                            var _isChoose = configMap.paperInfo.relations[i].relationType == constant.RelationType.OR;
                            if (currentPaperId == configMap.paperInfo.relations[i].paperId) {
                                currentIndex = i;
                                continue;
                            }
                            if (constant.TestState.SUBMITTED == paperState
                                    || constant.TestState.SUBMITTED_AUTO == paperState
                                    || constant.TestState.SUBMITTED_AUTO_FRONT == paperState) {//如果已经答完，继续找下一张
                                if (_isChoose) {
                                    chooseFinish = true;
                                } else {
                                    chooseFinish = false;
                                }
                                nextPaper = null;
                                continue;
                            }
                            if (currentIndex != -1 && i > currentIndex) {
                                if ((isChoose || chooseFinish) && _isChoose) {
                                    continue;
                                }
                                var _nextPaper = configMap.paperInfo.relations[i];
                                if (!_isChoose) {//必考
                                    nextPaper = nextPaper || _nextPaper;
                                    break;
                                } else {
                                    nextPaper = _nextPaper;
                                }
                            }
                        }
                        if (nextPaper == null) {
                            forwardFinish();
                            return false;
                        } else {
                            //2.3 找出了下一张卷子
                            configMap.paperInfo.relations[currentIndex].testState = constant.TestState.SUBMITTED;//更新当前卷子的状态
                            //3.跳转下一张卷子
                            configMap.candidateInfo.paperId = nextPaper.paperId;
                            unload();
                            forwardPaperInfo({
                                forwardData : {
                                    currentPaper : nextPaper,
                                    paperInfo : configMap.paperInfo,
                                    candidateInfo : configMap.candidateInfo
                                }
                            });
                        }
                    }
                }

            };
            /**
             * 跳转试卷信息
             */
            forwardPaperInfo = function(forwardData) {
                route.setAnchorRequire('paperInfo', true);
                route.setIgnoreHistory(true);
                route.forward('paperInfo', forwardData);
            };
            /**
             * 跳转结束
             */
            forwardFinish = function(state) {
                clearStorage();//清除缓存
                if (configMap.paperInfo.redirectUrl) {
                    settingAction.getRedirectUrl(jqueryMap.$container, configMap.paperInfo.redirectUrl);
                } else {
                    var finishUrl = utils.getRoot() + "finish";
                    if (state == "finish") {
                        finishUrl += "?state=finish";
                    }
                    if (!configMap.paperInfo.paperParam.queryScore) {
                        if (finishUrl.indexOf("?") >= 0) {
                            finishUrl += "&queryScore=false";
                        } else {
                            finishUrl += "?queryScore=false";
                        }
                    }
                    window.location.replace(finishUrl);
                }
            };
            /**
             * 确认是否提交
             * type:提交类型，提交部分，试卷,默认为部分
             */
            confirmSubmit = function(type) {
                var contentMsg = "您已经答完当前部分所有的题目，提交后不能再次进入当前部分，是否确认提交当前部分？";
                if (type == "paper") {
                    contentMsg = "您已经答完了所有的题目，现在就要交卷吗？";
                }
                var isAnsweredAll = partView.checkAnsweredAll();
                if (!isAnsweredAll) {
                    if (type == "paper") {
                        contentMsg = "您还有未答的题目，现在就要交卷吗？";
                    } else {
                        contentMsg = "您还有未答的题目，提交后不能再次进入当前部分，是否确认提交当前部分？";
                    }
                }
                confirmBox({
                    content : contentMsg,
                    onConfirm : function() {
                        stopTimeCountDown();//停止计时
                        toSubmitQuestion('submit');
                    },
                    onCancel : function() {
                        confimViewMap.target = 0;
                    }
                });
            };
            /**
             * 确认是否进入下一部分
             */
            confirmGoNextPart = function(isAuto) {
                var messge = "您已成功提交当前部分，点击继续答题按钮开始下一部分";
                if (isAuto) {
                    messge = "当前部分的考试时间已到，系统已经自动帮您提交该部分，点击继续答题按钮开始下一部分";
                }
                alertBox({
                    content : messge,
                    confirm : "继续答题",
                    onConfirm : function() { //进入下一部分
                        var seq = configMap.currentPart.seq + 1;
                        var partSeq = configMap.testInfo.parts[seq].partSeq;
                        partAction.noticeStartPart(partSeq);
                    }
                });
            };
            //-----------------------END  EVENT HANDLERS ---------------------------------------//

            //-----------------------START PRIVATE METHOD ---------------------------------------//
            /**
             * 停止倒计时
             */
            stopTimeCountDown = function() {
                var countTime = timeView.stopTimeCountDown();
                var question = questionAction.getCurrentQuestion();
                if (question === null) {
                    return;
                }
                if (question.questionId) {
                    var answerTime1 = questionAction.getAnswerTime();
                    var answerTime2 = question.elapsedTime ? question.elapsedTime + countTime : countTime;
                    console.log("stopTimeCountDown:countTime:" + countTime);
                    console.log("stopTimeCountDown:elapsedTime:" + question.elapsedTime || 0);
                    var answerTime = answerTime1 > answerTime2 ? answerTime1 : answerTime2;
                    questionAction.setAnswerTime(answerTime);
                    console.log("stopTimeCountDown:questionId:" + question.questionId + ";answerTime:" + answerTime);
                }
            };
            /**
             * 注册心跳
             * heartbeat:心跳频率
             */
            subscribeSendHeart = function() {
                if (!configMap.isSendHeart || !configMap.heartbeat) {
                    return;
                }
                var interVal = configMap.heartbeat * 1000; //单位为毫秒
                configMap.sendHeartTimeout = null;
                configMap.sendHeartTimeout = setTimeout(setHeartTime, interVal);
            };
            /**
             * 设置心跳时间
             */
            setHeartTime = function() {
                var interVal = configMap.heartbeat * 1000; //单位为毫秒
                if (viewMap.question.questionId) {
                    var question = questionAction.getCurrentQuestion();
                    if (question && question.questionId) {
                        var answerTime = questionAction.getAnswerTime();
                        answerTime = answerTime + configMap.heartbeat;
                        questionAction.sendHeart(question.questionId, answerTime);
                        questionAction.setAnswerTime(answerTime);
                        console.log("setHeartTime:questionId:" + question.questionId + ";answerTime:" + answerTime);
                    }
                    if (viewMap.question.combination) {
                        var subSeq = configMap.currentQuestion.subSeq;
                        var sub = viewMap.question.combination.subQuestionInfos[subSeq];
                        if (!sub) {
                            return;
                        }
                        sub.elapsedTime = answerTime;
                    }
                }
                if (configMap.sendHeartTimeout) {
                    window.clearTimeout(configMap.sendHeartTimeout);
                }
                configMap.sendHeartTimeout = null;
                configMap.sendHeartTimeout = setTimeout(setHeartTime, interVal);
            };
            /**
             * 刷新题目的前置任务
             * oldQuestion:刷新前的题目
             */
            beforeRefreshQuestion = function(oldQuestion, newQuestion) {
                //1.清掉心跳并重新注册
                if (configMap.sendHeartTimeout) {
                    clearTimeout(configMap.sendHeartTimeout);
                }
                subscribeSendHeart();
                //2.给离开次数传partSeq和questionId
                if (configMap.isListeningLeave === true && newQuestion && newQuestion.questionId) {
                    listeningLeaveView.configModule({
                        partSeq : configMap.testInfo.parts[configMap.currentPart.seq].partSeq,
                        questionId : newQuestion.questionId,
                        fileAllowed : newQuestion.text && newQuestion.text.fileAllowed || false
                    });
                }
                //3.取消题目序号
                if (oldQuestion && oldQuestion.questionId) {
                    var oldNum = partAction.getQuestionIntroById(oldQuestion.questionId).num;
                    partView.rendCurrent(oldNum, false); //取消当前题,seq包括子题目
                }
                //4.更新当前序号
                if (newQuestion && newQuestion.questionId) {
                    var questionIntro = partAction.getQuestionIntroById(newQuestion.questionId);
                    partView.rendCurrent(questionIntro.num, true);//更新当前题
                    configMap.currentQuestion.questionNum = questionIntro.num;
                    configMap.currentQuestion.questionSeq = questionIntro.seq;
                    configMap.currentQuestion.questionId = questionIntro.subquestionId || questionIntro.questionId;//子题目要传子题目的题目Id
                }
                //5.视频题和音频题需要重置模式
                if (!oldQuestion) {
                    return;
                }
                if (oldQuestion.audio || oldQuestion.video) {
                    flexHelp.hideFlex();
                    if (configMap.isMonitor === true) {
                        flexHelp.setMonitorMode(configMap.testInfo.testId);
                    }
                }

            };
            /**
             * 刷新题目
             */
            refreshQuestion = function(data) {
                var currentQuestion = data;
                //1.处理答案
                questionAction.dealAnswer(currentQuestion);
                //2.准备数据
                var rendData = {};
                var questionIntro = partAction.getQuestionIntroById(currentQuestion.questionId);
                var questionsNum = partAction.getPartQuestionsLen();
                if (questionIntro.subSeq != undefined) {
                    //3.1 子题目
                    var subQuestionQnums = partAction.getCombinationMapById(questionIntro.questionId);
                    rendData.subQuestionQnums = subQuestionQnums;
                    currentQuestion.isSub = true;
                    rendData.subQuestions = [ {
                        question : currentQuestion
                    } ];
                } else {
                    //3.2 正常题目
                    rendData.questionIndex = questionViewHelp.getQuestionIndex(questionIntro.seq + 1, questionsNum);
                    rendData.question = currentQuestion;
                    if (questionIntro.isCombination == true) {
                        //组合题还需要刷新子题目
                        var subQuestionQnums = partAction.getCombinationMapById(questionIntro.questionId);
                        rendData.subQuestionQnums = subQuestionQnums;
                        questionIntro = partAction.getQuestionIntroByNum(configMap.forwardQuestion.questionNum);
                        currentQuestion = data.combination.subQuestionInfos[questionIntro.subSeq];
                        questionAction.dealAnswer(currentQuestion);
                        currentQuestion.isSub = true;
                        rendData.subQuestions = [ {
                            question : currentQuestion
                        } ];
                    }
                }
                //3.缓存当前值
                questionAction.createQuestion(currentQuestion);
                //4.渲染视图
                var isFirst = questionAction.isFirstQuestion(questionIntro);
                var isLast = questionAction.isLastQuestion(questionIntro);
                var isPageLast = false;
                if (isLast == true) {
                    isPageLast = configMap.currentPart.seq == configMap.testInfo.parts.length - 1;
                }
                //3.刷新视图
                rendData.isFirst = isFirst;
                rendData.isLast = isLast;
                rendData.questionError = false;
                rendData.isPageLast = isPageLast;
                rendData.isWideLayout = currentQuestion.program ? true : false;
                rendQuestion(rendData);
                //4.	辅助刷新，根据不同的题目刷新视图
                refreshViewByQuestion(currentQuestion);
                return currentQuestion;
            };
            /**切换编程语言*/
            changeProgramLang = function(lang) {
                //切换语言后需要重新取题
                viewMap.question.program.force = true;
                questionAction.setProgramLanguage(lang);
                var askNum = configMap.currentQuestion.questionNum;
                getAppoint(askNum);
            };
            /** 渲染题目 */
            rendQuestion = function(data) {
                $.observable(viewMap).setProperty(data);
            };
            /**
             * 刷新题目的后置任务
             */
            afterRefreshQuestion = function(data) {
                //1.更新当前题目信息
                var questionIntro = partAction.getQuestionIntroById(data.questionId);
                //				configMap.currentQuestion.questionNum = questionIntro.num;
                //				configMap.currentQuestion.questionSeq = questionIntro.seq;
                //				configMap.currentQuestion.questionId = questionIntro.subquestionId || questionIntro.questionId;//子题目要传子题目的题目Id
                if (data.combination) {
                    configMap.currentQuestion.subSeq = 0;
                } else {
                    if (questionIntro.subSeq != undefined) {
                        configMap.currentQuestion.subSeq = questionIntro.subSeq;
                    }
                }
                //partView.rendCurrent(questionIntro.num, true);//更新当前题
                //2.开始计时
                if (configMap.currentPart.timeLimit < 0) {
                    handTimeOver();
                } else {
                    if (configMap.isOk == true) {
                        timeView.startTimeCountDown();
                    }
                }
            };
            /**
             * 提交前对题目做一些检查
             */
            checkQuestion = function(callBack) {
                var currentQuestion = questionAction.getCurrentQuestion();
                if (!currentQuestion || !currentQuestion.questionId) {//没有题目和题目id是异常情况发起的，直接去取题，返回true
                    return true;
                }
                //1.当前部分不可以回溯时提交前要检查每题是否答了
                var hasAnswer = questionAction.checkHasAnswer(currentQuestion);
                if (configMap.currentPart.backward == false && viewMap.isLast != true) {
                    if (hasAnswer == false) {
                        confirmBox({
                            content : "您还没有作答当前题，题目不能回溯，是否放弃作答？",
                            confirm : "确定",
                            onConfirm : function() {
                                result = false;
                                callBack();
                            }
                        });
                        return false;
                    }
                }
                if (questionViewHelp.isMultiChoice(currentQuestion.questionType) == true) { //2.都需要检查项,多选题只选了一个要提示
                    var isValid = questionAction.isValidMultiChoice(currentQuestion);
                    if (isValid == false) {
                        confirmBox({
                            content : "当前题是多选题，至少要选择2个选项，您只选择了一个选项，是否确定提交？",
                            onConfirm : function() {
                                callBack();
                            }
                        });
                        return false;
                    }
                } else if (currentQuestion.audio || currentQuestion.video) { //3.都需要检查项,音频题和视频题未上传的提交前要上传，上传失败要提示
                    return checkFlexUpload(callBack);
                }

                return true;
            };
            /**
             * 检查音视频上传
             */
            checkFlexUpload = function(callBack) {
                var _currentQuestion = questionAction.getCurrentQuestion();
                if (_currentQuestion && (_currentQuestion.audio || _currentQuestion.video)) { //3.都需要检查项,音频题和视频题未上传的提交前要上传，上传失败要提示
                    if (!flexHelp.isFlexInit()) { // flex初始化状态不检查答案
                        //	var contentMsg = "";
                        if (_currentQuestion.audio
                                && (!_currentQuestion.audio.candAnswer || !_currentQuestion.audio.candAnswer.urlAnswer)) {
                            configMap.submitCallBack = callBack;
                            flexHelp.uploadMediaByExam('audio');
                            return false;
                        } else if (_currentQuestion.video
                                && (!_currentQuestion.video.candAnswer || !_currentQuestion.video.candAnswer.urlAnswer)) {
                            configMap.submitCallBack = callBack;
                            flexHelp.uploadMediaByExam('video');
                            return false;
                        }
                    }
                }
                return true;
            };
            /**
             * 处理错误信息
             */
            dealServeError = function(errInfo) {
                if (errInfo.errorCode === constant.ErrorCode.EUSER) {//会话失效要重新建立会话
                    errInfo.desc = errInfo.desc || "" + "会话失效，正在重新建立会话，请稍后重试。";
                    //刷新页面的，从服务端从新取数据
                    paperModel.getTestByCandidate(configMap.candidateInfo);
                }
                $.observable(viewMap).setProperty({
                    serverErr : errInfo.desc
                });
                msg.showMsg(errInfo.desc, 'danger');
            };
            /**
             * 根据题型更新视图
             */
            refreshViewByQuestion = function(data, isSubQuestion) {
                //编程题
                if (data.program) {
                    questionViewHelp.setCodeMatrix(jqueryMap.$container.find('#codeProgram'), {
                        questionId : data.questionId,
                        program : data.program
                    });
                }
                //音频题
                if (data.audio) {
                    var option = {};
                    option.$container = jqueryMap.$container.find('#exam-question-audio');
                    option.testId = configMap.testInfo.testId;
                    option.questionId = data.questionId;
                    if (data.audio.candAnswer && data.audio.candAnswer.urlAnswer) {
                        option.urlAnswer = data.audio.candAnswer.urlAnswer;
                        option.mediaTime = data.audio.candAnswer.mediaTime;
                    }
                    flexHelp.setAudioMode(option);
                }
                //视频题
                if (data.video) {
                    var option = {};
                    option.$container = jqueryMap.$container.find('#exam-question-video');
                    option.testId = configMap.testInfo.testId;
                    option.questionId = data.questionId;
                    if (data.video.candAnswer && data.video.candAnswer.urlAnswer) {
                        option.urlAnswer = data.video.candAnswer.urlAnswer;
                        option.mediaTime = data.video.candAnswer.mediaTime;
                    }
                    flexHelp.setVideoMode(option);
                }
                if (data.text || (data.choice && questionViewHelp.isNeedExplain(data.questionType))) {
                    tabIndent.renderAll();
                }
                //问答附件题
                if(data.text && data.text.fileAllowed===true) {
                    var $uploadBtn = jqueryMap.$container.find('.uploadbtn');
                    questionViewHelp.initUpload($uploadBtn,configMap.testInfo.testId,data);
                }
            };
            /**
             * 检查试卷状态
             */
            checkPaperState = function() {
                var result = true;
                //1.检查距离考试结束的时间，本次测试结束了，不能参加测试了
                if (configMap.paperInfo && configMap.paperInfo.timeLeftToExpDate
                        && configMap.paperInfo.timeLeftToExpDate <= 0) {
                    result = false;
                } else if (configMap.paperInfo && configMap.paperInfo.timeLeftToActEndDate
                        && configMap.paperInfo.timeLeftToActEndDate < 0) {//考场时间
                    result = false;
                }
                if (result === false) {
                    window.location.href = utils.getRoot() + "app/exam/questionError.jsp";
                }
                //2. 跳转已完成的测试，您已完成本次测试，谢谢您的参与。
                if (configMap.testInfo.testState == constant.TestState.SUBMITTED
                        || configMap.testInfo.testState == constant.TestState.SUBMITTED_AUTO) {
                    result = false;
                    forwardFinish("finish");
                }
            };
            /**
             * 开始考试
             */
            startExam = function() {
                console.log("startExam.....");
                //3.通知开始考试
                partAction.noticeStartExam(configMap.testInfo.parts);
            };
            /**
             * 设置倒计时
             * defineLimit:定义的时间
             * elapsedTime：服务端花费的时间
             */
            setTimeCountDown = function(defineLimit, elapsedTime) {
                if (!configMap.isCountDown) {//不计时
                    return;
                }
                var timeLimit = 0;

                //1.判断是否有缓存时间
                var storageTime = utils.storage.getItem('exam-timeCountDown');
                if (storageTime) {//1.1 有缓存时间以缓存为准
                    storageTime = Number(storageTime);
                    timeLimit = defineLimit - storageTime;
                    /*理论上缓存的时间不应小于服务端的时间*/
                    if (elapsedTime > storageTime) {
                        console.log("时间有问题，服务端时间大于页面缓存时间：elapsedTime：" + elapsedTime + ";storageTime:" + storageTime);
                    }
                } else {//1.2 无缓存时间以服务端为准
                    timeLimit = defineLimit - elapsedTime;
                }
                //2. 比较考试距离结束时间
                if (configMap.paperInfo.timeLeftToExpDate && configMap.paperInfo.timeLeftToExpDate < timeLimit) {//邀请时间
                    //距离考试结束的时间小于限制时间，以考试结束时间倒计时
                    timeLimit = configMap.paperInfo.timeLeftToExpDate;
                } else if (configMap.paperInfo.timeLeftToActEndDate
                        && configMap.paperInfo.timeLeftToActEndDate < timeLimit) {//考场时间
                    timeLimit = configMap.paperInfo.timeLeftToActEndDate;
                }
                if (timeLimit >= 0) {
                    timeView.setTimeCountDown(timeLimit);
                }
                if (timeLimit <= 0) {
                    handTimeOver();
                }
            };
            /**
             * 创建当前部分
             * partId:部分唯一标示
             */
            startPart = function(partId) {
                if (configMap.isMonitor === true) {
                    flexHelp.setMonitorMode(configMap.testInfo.testId);
                }
                confimViewMap.target = 0;
                //1.创建部分
                var part = partAction.getPartByPartId(partId);
                partAction.createPart(part);
                //1.1 更新部分信息:partId,seq,backward
                configMap.currentPart.partId = part.partSeq;
                configMap.currentPart.seq = part.seq;
                configMap.currentPart.backward = part.backward;
                //1.2 检查当前部分是否允许回溯
                if (configMap.currentPart.backward == false) {
                    //不允许回溯，要禁用题目面板和回上一题
                    $.observable(viewMap).setProperty('backward', false);
                } else {
                    $.observable(viewMap).setProperty('backward', true);
                }
                //2.渲染题目面板
                if (configMap.isShowQuestionPanel === true) {
                    var isLastPart = configMap.currentPart.seq == configMap.testInfo.parts.length - 1;
                    partView.rendQuestionPanel(isLastPart);
                }
                //标记当前部分
                partView.rendCurrentPart(configMap.currentPart.seq, true, viewMap.backward);
                //设置计时
                if (configMap.isCountDown == true && configMap.timeCountModel == 0 && part.partTime) {//partTime标示部分计时
                    setTimeCountDown(part.partTime, part.elapsedTime || 0);
                }
            };
            /**
             * 开始答第一个题
             * questionId ：题目Id
             */
            startFirstQuestion = function() {
                //初始化题目信息
                configMap.forwardQuestion.questionNum = 0;
                configMap.currentQuestion.questionSeq = 0;
                configMap.currentQuestion.questionNum = 0;
                configMap.currentQuestion.subSeq = -1;
                configMap.currentQuestion.questionId = partAction.getQuestionBySeq(0).questionId;
                questionAction.initQuestion();
                //获取题目
                questionAction.getQuestion(configMap.currentQuestion.questionId);
            };
            /**
             * 根据轨迹创建部分
             */
            startTrackPart = function(partId, questionId) {
                //1.获取轨迹部分
                startPart(partId);
                //2.获取轨迹题目
                if (questionId) {
                    //2.2 当前题
                    configMap.currentQuestion.questionId = questionId;
                    var questionIntro = partAction.getQuestionIntroById(configMap.currentQuestion.questionId);
                    configMap.currentQuestion.questionNum = questionIntro.num;
                    configMap.currentQuestion.questionSeq = questionIntro.seq;
                    configMap.currentQuestion.subSeq = -1;
                    configMap.forwardQuestion.questionNum = questionIntro.num;
                    //处理组合题
                    if (questionIntro.isCombination === true) {
                        //组合题的轨迹要特殊处理，如果都答完跳最后一个子题目，否则跳到最后一个未答得
                        var subQuestions = partAction.getCombinationMapById(questionId);
                        for (var i = subQuestions.length - 1; i > 0; i--) {
                            if (subQuestions[i].answered === true) {
                                var _index = (i + 1) >= subQuestions.length ? i : (i + 1);
                                configMap.currentQuestion.subSeq = _index;
                                configMap.forwardQuestion.questionNum = subQuestions[_index].num;
                                break;
                            }
                        }
                    }
                    //2.3 跳转题
                    getAppoint(configMap.forwardQuestion.questionNum, true);
                } else {
                    //临界点，最后一题已答完，直接进入下一部分
                    var seq = configMap.currentPart.seq + 1;
                    if (seq < configMap.testInfo.parts.length) {
                        partView.rendCurrentPart(configMap.currentPart.seq, false);
                        partAction.noticeStartPart(configMap.testInfo.parts[seq].partSeq);
                    } else {//已交卷
                        confimViewMap.target = 5;
                        finishExam();
                        //partAction.noticeSubmitPart();
                    }
                }
            };
            /*设置头部*/
            showHeadSet = function() {
                if (configMap.isHistory === true) {//历史回退
                    //1.显示进度
                    settingAction.subProgress(true);
                    //2.显示计时
                    if (configMap.isCountDown === true) {
                        settingAction.subTimeCountDown(true);
                    }
                } else {//非历史回退
                    //1.设置用户名
                    if (configMap.candidateInfo && configMap.candidateInfo.candidateName) {
                        settingAction.subUserName(configMap.candidateInfo.candidateName);
                    }
                    //设置公司LOGO
                    if (configMap.paperInfo.companyLogo) {
                        settingAction.setCompanyLogo(configMap.paperInfo.companyLogo);
                    }
                    //2.答题进度
                    if (configMap.isShowProgress === true && configMap.testInfo.parts.length) {
                        settingAction.subProgress(true);
                        partView.initModule(jqueryMap.$container.find('#exam-part-progress'),
                                configMap.isShowQuestionPanel);
                        partView.rendPartProgress(configMap.testInfo.paperName, configMap.testInfo.parts);
                    }
                    //3.设置计时模式
                    if (configMap.isCountDown === true) {
                        settingAction.subTimeCountDown(true);
                        $.gevent.subscribe(stateMap.$container, timeView.pub.gevent_timeOver, handTimeOver);//倒计时结束
                        if (configMap.testInfo.paperTime) {
                            configMap.timeCountModel = 1;//试卷计时
                        } else {
                            configMap.timeCountModel = 0;//部分计时
                        }
                        timeView.initModule($('#sets-header-logoarea').find('.time'), configMap.timeCountModel);
                    }
                    //5.设置监控
                    if (configMap.isMonitor) {
                        flexHelp.setMonitorMode(configMap.testInfo.testId);
                    } else {
                        configMap.isOk = true;
                        startExam();
                    }
                    //4.离开页面提示
                    if (configMap.isListeningLeave === true && configMap.paperInfo.paperParam
                            && configMap.paperInfo.paperParam.switchLimit && configMap.isOk == true) {
                        subscribeLeave();
                    }
                }
            };
            /**
             * 考试环境准备
             * 1.用户名
             * 公司logo
             * 2.答题进度；
             * 3.监考标示
             * 4.计时
             * 5.题目面板
             * 6.离开提示
             */
            prepareEnvironment = function() {
                //1.检查试卷状态
                if (checkPaperState() == false) {
                    return;
                }
                //******************************页面加载*****************************//
                configMap.isMonitor = configMap.isMonitor && configMap.paperInfo && configMap.paperInfo.paperParam
                        && configMap.paperInfo.paperParam.proctorCamera;
                //判断是否要加载flex
                if (configMap.paperInfo && configMap.paperInfo.requiredDevices || configMap.isMonitor) {
                    flexHelp.loadFlex(configMap.paperInfo.requiredDevices);
                }
                //******************数据初始化*********************************//
                questionAction.initModule();
                //******************************头部设置*****************************//
                showHeadSet();

            };
            /*注册离开*/
            subscribeLeave = function() {
                var switchTime = configMap.paperInfo.paperParam.switchLimit;
                var isInit = listeningLeaveView.initModule(switchTime);
                if (isInit === true) {
                    return;
                }
                $.gevent.subscribe(jqueryMap.$container, listeningLeaveView.pub.gevent_arriveSwitchLimit,
                        handArriveSwitchLimit);//达到离开次数
            };
            /**
             * 卸裁环境
             */
            unloadEnviroment = function() {
                //卸裁离开次数
                if (configMap.isListeningLeave === true && configMap.paperInfo.paperParam
                        && configMap.paperInfo.paperParam.switchLimit) {
                    $.gevent.unsubscribe(jqueryMap.$container, listeningLeaveView.pub.gevent_arriveSwitchLimit);
                    listeningLeaveView.unload();
                }
                //1.判断是否要卸裁flex
                if (configMap.paperInfo && configMap.paperInfo.requiredDevices) {
                    flexHelp.hideFlex();
                    settingAction.subMonitor(false);
                }
                //2.答题进度
                if (configMap.isShowProgress === true && configMap.testInfo.parts.length) {
                    settingAction.subProgress(false);
                }
                //是否监控
                if (configMap.paperInfo && configMap.paperInfo.paperParam
                        && configMap.paperInfo.paperParam.proctorCamera) {
                    settingAction.subMonitor(false);
                }
                //4.计时并注销心跳
                if (configMap.isCountDown === true) {
                    settingAction.subTimeCountDown(false);
                }
                $.observable(viewMap).setProperty({
                    model : 'formal',
                    questionError : false,
                    isFirst : true,
                    isLast : false,
                    isPageLast : false,
                    isWideLayout : false,
                    isCallServer : true,
                    question : {}
                });
                delete viewMap.subQuestionQnums;
                delete viewMap.subQuestions;
                configMap.isOk = false;
                clearStorage();//清除缓存
            };
            /**
             * 提交当前题并获取上一题
             */
            getPrev = function() {
                var askNum = configMap.currentQuestion.questionNum - 1;
                getAppoint(askNum);
            };
            /**
             * 提交当前题并获取下一题
             */
            getNext = function() {
                var askNum = configMap.currentQuestion.questionNum + 1;
                getAppoint(askNum);
            };
            /**
             * 提交题目
             * type:'query':提交题目并查询;'submit':提交
             */
            toSubmitQuestion = function(type, askNum, notSubmit) {
                stopTimeCountDown();//1.停止计时
                if (type == "query") {
                    $.observable(viewMap).setProperty("isCallServer", true);//2.禁止操作按钮
                    //传递下一个题的questionId
                    questionAction.getAppoint(askNum, notSubmit);
                    //刷新缓存的跳转信息
                    configMap.forwardQuestion.questionNum = askNum;
                } else if (type == "submit") {
                    questionAction.submitCurrentQuestion();
                }
            };
            /**
             * 获取指定题
             * askNum :取的第几个题，含子题目，对应题目面板上的题数
             * notSubmit:不提交只获取
             */
            getAppoint = function(askNum, notSubmit) {
                var callBack = function() {
                    toSubmitQuestion('query', askNum, notSubmit);
                };
                if (!notSubmit) {//提交题目需要校验题目
                    if (checkQuestion(callBack) == true) {
                        callBack();
                    }
                } else {
                    callBack();
                }
            };
            /**
             * 提交部分
             */
            submitPart = function(isAuto) {
                var callBack = function() {
                    toSubmitQuestion('submit');
                };
                if (isAuto) {
                    confimViewMap.target = 3; //自动提交
                    if (checkFlexUpload(callBack) == true) {
                        callBack();
                    }
                } else {
                    confimViewMap.target = 1;
                    if (checkQuestion(callBack) == true) {
                        confirmSubmit("part");
                    }
                }
            };
            /**
             * 交卷
             */
            submitPaper = function(isAuto) {
                var callBack = function() {
                    toSubmitQuestion('submit');
                };
                if (isAuto) {
                    if (confimViewMap.target != 6) {
                        confimViewMap.target = 4; //时间到自动提交
                    }
                    if (checkFlexUpload(callBack) == true) {
                        callBack();
                    }
                } else {
                    confimViewMap.target = 2;
                    if (checkQuestion(callBack) == true) {
                        confirmSubmit("paper");
                    }
                }
            };
            /**
             * 确认框
             */
            confirmBox = function(option) {
                var _option = {
                    title : "提示：",
                    type : "primary",
                    confirm : option.confirm,
                    content : option.content,
                    onConfirm : option.onConfirm,
                    onCancel : option.onCancel
                };
                msg.confirm(_option);
            };
            /**
             * 警告框
             */
            alertBox = function(option) {
                var _option = {
                    title : "提示：",
                    type : "primary",
                    alert : true,
                    confirm : option.confirm,
                    content : option.content,
                    onConfirm : option.onConfirm
                };
                msg.confirm(_option);
            };
            /**
             * 抛出异常
             */
            throwError = function(e, data) {
                var err = [], errRequest = {};
                errRequest.candidateName = configMap.candidateInfo.candidateName;//姓名
                errRequest.candidateEmail = configMap.candidateInfo.candidateEmail;//邮箱
                errRequest.testId = configMap.testInfo && configMap.testInfo.testId || 0;//testId
                errRequest.questionId = data && data.questionId || 0;//questionId
                err.push("errorMsg: ");
                err.push(e.message);
                err.push("\n");
                err.push("errorStack: ");
                err.push(e.stack);
                var errStr = err.join('');
                errRequest.cause = errStr;
                var rendErr = utils.copyFrom(errRequest);
                rendErr.model = 'formal';
                rendErr.questionError = true;
                if (data && data.questionId) {
                    var questionIntro = partAction.getQuestionIntroById(data.questionId);
                    var isFirst = questionIntro ? questionAction.isFirstQuestion(questionIntro) : true;
                    var isLast = questionIntro ? questionAction.isLastQuestion(questionIntro) : false;
                    var isPageLast = false;
                    if (isLast == true) {
                        isPageLast = configMap.currentPart.seq == configMap.testInfo.parts.length - 1;
                    }
                    rendErr.isFirst = isFirst;
                    rendErr.isLast = isLast;
                    rendErr.isPageLast = isPageLast;
                } else {
                    rendErr.noQuestion = true;
                }
                $.observable(viewMap).setProperty(rendErr);
                questionAction.saveError(errRequest);
                throw new Error(errStr);
            };
            //-----------------------END  PRIVATE METHOD ---------------------------------------//

            //-----------------------START PUBLIC METHOD ---------------------------------------//
            initModule = function($container) {
                if (stateMap.isInit == true) {
                    return false;
                }
                //1. 缓存jquery对象
                stateMap.$container = $container;
                setJqueryMap();
                //2. 初始化模板
                initTemplate();
                //3.绑定和订阅事件
                $.gevent.subscribe(stateMap.$showContainer, route.pub.gevent_viewShow, handShow);//当页面加载时执行
                makeSubscribeQueue();
                geventEvent(true);
                bindEvent();
                stateMap.isInit = true;
            };
            //-----------------------END PUBLIC METHOD ---------------------------------------//
            return {
                initModule : initModule,
                pub : configMap.pubMap
            };
        });
