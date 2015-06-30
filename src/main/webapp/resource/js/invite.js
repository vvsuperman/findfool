/**
 * Created by liuzheng on 2014/7/11.
 */
OJApp.controller("invite",function($scope, $http, Data) {
    $scope.url = '#/invite';
    $scope.template = 'page/invite.html';
    $scope.ContentUs = 'page/contentUs.html';
    $scope.leftBar = 'page/testlistleftbar.html';
})

OJApp.controller("Excel",function($scope, $http, Data) {
	$scope.duration=70;
	
	$scope.camera={};
	$scope.camera.selected=0;
	$scope.options=[{id:0,content:"必须开启"},{id:1,content:"不开启"},{id:2,content:"可以开启可以不开启"}];
	$scope.subject = "笔试邀请";
	
	$scope.content = "     我们非常荣幸能收到您的简历，做为优秀的候选人之一，我们诚挚的邀请您参加测试，使我们能进一步了解您的能力。";
	
    $scope.xlsusers = [
        {username: '', email: '', tel: '', test: Data.tname(),openCamera:''}
    ];
    
    
   
    
    $scope.addOne = function (v) {
        var i = $scope.xlsusers.indexOf(v);
        if ($scope.active == 'notSelect') {
            $scope.xlsusers.splice(i + 1, 0, {username: '', email: '', tel: '', test: ''});
        } else {
            $scope.xlsusers.splice(i + 1, 0, {username: '', email: '', tel: '', test: $scope.active});
        }
    };
    
    $scope.removeOne = function (v) {
        var i = $scope.xlsusers.indexOf(v);
        if (i > -1) {
            $scope.xlsusers.splice(i, 1);
        }
    };
    
    $scope.removeTest = function (v) {
        //等待增加功能
//        var tmp = $scope.xlsusers;
//        var i = $scope.xlsusers.indexOf(v);
//        if (i > -1) {
//            $scope.xlsusers.splice(i, 1);
//        }
//        $scope.xlsusers = tmp;
    };
    
    $scope.clean = function () {
        $scope.content = ""
    };
    
//    $scope.refresh = function () {
//        var tmp = $scope.xlsusers;
//        var list = [];
//        $scope.testlist = [];
//        var tmpp = [];
//        for (var i in tmp) {
//            if ($.inArray(tmp[i].email + "::" + tmp[i].test, list) == -1) {
//                list.push(tmp[i].email + "::" + tmp[i].test);
//                tmpp.push(tmp[i]);
//            }
//            if ($.inArray(tmp[i].test, $scope.testlist) == -1) {
//                if (tmp[i].test == '') {
//                    if ($.inArray('notSelect', $scope.testlist) == -1) {
//                        $scope.testlist.push('notSelect');
//                    }
//                    continue;
//                }
//                $scope.testlist.push(tmp[i].test);
//            }
//        }
//        $scope.active = $scope.testlist[0];
//        $scope.selectTest = function (target) {
//            $scope.active = target.getAttribute('data');
//        };
//        $scope.xlsusers = tmpp;
//        for (t in $scope.testlist) {
//            if ($scope.testlist[t] == "notSelect") {
//                $scope.xlsusers.unshift({username: '', email: '', tel: '', test: ''});
//            } else {
//                $scope.xlsusers.unshift({username: '', email: '', tel: '', test: $scope.testlist[t]})
//            }
//        }
//        var tmp = $scope.xlsusers;
//        var tmpp = [];
//        var list = [];
//        for (var i in tmp) {
//            if ($.inArray(tmp[i].email + "::" + tmp[i].test, list) == -1) {
//                list.push(tmp[i].email + "::" + tmp[i].test);
//                tmpp.push(tmp[i]);
//            }
//        }
//        $scope.xlsusers = tmpp;
//    };
    
//    $scope.refresh();
    $scope.testlist =[];
    $scope.testlist.push(Data.tname());
  
  
    
    $scope.tnamelist = {};
    
    /*
     * 该方法有问题，需通过testid来查，否则当同一个用户有相同测试会bug
     * */
//    $scope.queryByName = function (tname) {
//        $http({
//            url: WEBROOT+"/test/queryByName",
//            method: 'POST',
//            headers: {
//                "Authorization": Data.token()
//            },
//            data: {"user": {"uid": Data.uid()}, "name": tname}
//        }).success(function (data) {
//            $scope.state = data["state"];//1 true or 0 false
//            $scope.message = data["message"];
//            if ($scope.state) {
//                $scope.tnamelist[tname] = $scope.message.quizid;
//            } else {
//            }
//        }).error(function (data) {
//        });
//    };
    
//    $scope.testlist.forEach($scope.queryByName);
    
    $scope.upload = function (tname, userlist) {
        $http({
            url: WEBROOT+"/test/manage/invite",
            method: 'POST',
            headers: {
                "Authorization": Data.token()
            },
            
//          data: {"user": {"uid": Data.uid()}, "subject": $scope.subject,"duration":$scope.duration, "replyTo": $scope.replyTo, "quizid": $scope.tnamelist[tname], "invite": userlist, "context": $scope.content}
            data: {"user": {"uid": Data.uid()}, "subject": $scope.subject,"duration":$scope.duration, "replyTo": $scope.replyTo, "quizid":Data.tid(), "invite": userlist, "context": $scope.content, "starttime": $scope.startTime, "deadtime": $scope.endTime}
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            //Data.token = data["token"];
            $scope.message = data["message"];
            if ($scope.state) {
            	removeTip();
            	flashTip("邀请成功")
            } else {
            	flashTip($scope.message.msg)
            }
        }).error(function (data) {
        });	
    };
    
    $scope.sent = function () {
        for (tid in $scope.testlist) {
            var tmp = [];
            for (var user in $scope.xlsusers) {
            	   if($scope.xlsusers[user].email == ""	){
            		   smoke.alert("邮箱不可为空");
            		   return false;
            	   }
            	
                    
                	$scope.xlsusers[user].openCamera=$scope.camera.selected;
                    tmp.push($scope.xlsusers[user]);
            }
            loadingTip();
            $scope.upload($scope.testlist[tid], tmp);
        }
    };
    
    var use_worker = true;
    function xlsworker(data, cb) {
        var worker = new Worker('resource/js/xls/xlsworker.js');
        worker.onmessage = function (e) {
            switch (e.data.t) {
                case 'ready':
                    break;
                case 'e':
                    console.error(e.data);
                    break;
                case 'xls':
                    cb(e.data.d);
                    break;
            }
        };
        worker.postMessage(data);
    }
    function to_json(workbook) {
        var result = {};
        workbook.SheetNames.forEach(function (sheetName) {
            var roa = XLS.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
            if (roa.length > 0) {
                result[sheetName] = roa;
            }
        });
        return result;
    }
    function process_wb(wb) {
        if (typeof Worker !== 'undefined') XLS.SSF.load_table(wb.SSF);
        var output = "";
        output = to_json(wb);
        $scope.$apply(function () {
            $.merge($scope.xlsusers, output["Sheet1"]);
            $scope.xlsusers.shift();
//            $scope.refresh();
        });
    }
    function handleDrop(e) {
        e.stopPropagation();
        e.preventDefault();
        var files = e.dataTransfer.files;
        var i, f;
        for (i = 0, f = files[i]; i != files.length; ++i) {
            var reader = new FileReader();
            reader.onload = function (e) {
                var data = e.target.result;
                if (use_worker && typeof Worker !== 'undefined') {
                    xlsworker(data, process_wb);
                } else {
                    var wb = XLS.read(data, {type: 'binary'});
                    process_wb(wb);
                }
            };
            reader.readAsBinaryString(f);
        }
    }
    function handleDragover(e) {
        e.stopPropagation();
        e.preventDefault();
        e.dataTransfer.dropEffect = 'copy';
    }
    var drop = document.getElementById('drop');
    if (drop.addEventListener) {
        drop.addEventListener('dragenter', handleDragover, false);
        drop.addEventListener('dragover', handleDragover, false);
        drop.addEventListener('drop', handleDrop, false);
    }
});

OJApp.filter('filterTest', function () {
    return function (items, v) {
        var r = [];
        if (v == "notSelect") {
            v = ""
        }
        angular.forEach(items, function (item) {
            if (item.test == v) {
                r.push(item);
            }
        });
        return r;
    }
});
