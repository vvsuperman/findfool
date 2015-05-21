/*
 * exam.js
 * 考试
 * @author zengjie 下午3:08:51
*/
define(
        "app/exam/exam",
        function(require, exports, module) {
            var stateMap = {
                $container : null
            }, route = require('core/route'), //
            infoView = require('app/exam/view-exam-info'), tryExamView = require('app/exam/view-exam-tryExam'), examView = require('app/exam/view-exam-examing'), //
            paperInfoView = require('app/exam/view-exam-paper-info'), headerView = require('app/header/view-header'), settingController = require('app/exam/action-exam-setting'), //
            subscribeEvent, //
            handGetCompanyLogo, handGetUserName, handMonitor, handShowProgress, handToggleProgress, handIsTry, handShowTimeView, //
            initModule;

            //--------------------------------START EVENTBIND METHOD----------------------------------//
            subscribeEvent = function() {
                //订阅获取公司logo
                $.gevent
                        .subscribe(stateMap.$container, settingController.pub.gevent_setCompanyLogo, handGetCompanyLogo);
                //订阅获取姓名
                $.gevent.subscribe(stateMap.$container, settingController.pub.gevent_setUserName, handGetUserName);
                //订阅设置监控
                $.gevent.subscribe(stateMap.$container, settingController.pub.gevent_setMonitor, handMonitor);
                //订阅设置答题进度信息
                $.gevent.subscribe(stateMap.$container, settingController.pub.gevent_setProgressView, handShowProgress);
                $.gevent
                        .subscribe(stateMap.$container, settingController.pub.gevent_toggleProgress, handToggleProgress);
                //订阅设置倒计时信息
                $.gevent
                        .subscribe(stateMap.$container, settingController.pub.gevent_setTimeCountDown, handShowTimeView);
                //订阅是否显示练习标示
                $.gevent.subscribe(stateMap.$container, settingController.pub.gevent_setPractice, handIsTry);
            };
            //--------------------------------START EVENTBIND METHOD----------------------------------//

            //--------------------------------START HAND METHOD----------------------------------//
            /**
             * 监听获取第三方公司LOGO
             */
            handGetCompanyLogo = function(event, data) {
                headerView.configModule({
                    "companyLogo" : data
                });
            }
            /**
             * 监听获取用户姓名
             */
            handGetUserName = function(event, data) {
                headerView.configModule({
                    "userName" : data
                });
            };
            handMonitor = function(event, data) {
                headerView.configModule({
                    "isMonitor" : data || false
                });
            };
            handShowProgress = function(event, data) {
                headerView.configModule({
                    "isShowPartInfo" : data || false
                });
            };
            handToggleProgress = function(event, data) {
                headerView.configModule({
                    "toggleProgress" : data || false
                });
            };
            handIsTry = function(event, data) {
                headerView.configModule({
                    "isShowTry" : data || false
                });
            };
            handShowTimeView = function(event, data) {
                headerView.configModule({
                    "isShowTime" : data || false
                });
            };
            //--------------------------------START PRIVATE METHOD----------------------------------//

            //--------------------------------START PUBLIC METHOD----------------------------------//
            initModule = function($container, param) {
                headerView.configModule({
                    "userName" : ""
                });
                stateMap.$container = $container;
                //1.初始化header
                headerView.initModule($("#sets-header-logoarea"));
                //2.配置paperId
                infoView.configModule({
                    param : param
                });
                //3.订阅
                subscribeEvent();

                //2.配置路由
                route.initModule($container, {
                    default_view_name : 'info',
                    view_module_map : {
                        info : {
                            selector : '>#exam-info',
                            requireAnchor :false,//不记录锚点
                            module : infoView
                        },
                        tryExam : {
                            selector : ">#exam-examing",
                            requireAnchor :false,//不记录锚点
                            module : tryExamView
                        },
                        exam : {
                            selector : ">#exam-examing",
                            requireAnchor :true,//记录锚点
                            module : examView
                        },
                        paperInfo : {
                            selector : ">#exam-paperInfo",
                            requireAnchor :false,//不记录锚点
                            module : paperInfoView
                        }
                    }
                });

                return true;
            };
            //--------------------------------END PUBLIC METHOD------------------------------------//
            return {
                initModule : initModule
            };
        });