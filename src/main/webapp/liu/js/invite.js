/**
 * Created by liuzheng on 2014/7/11.
 */

function invite($scope, $http, Data) {
    $scope.url = '#/invite';
    $scope.template = 'invite.html';
    $scope.ContentUs = 'contentUs.html';
    $scope.leftBar = '';
    console.log(Data.tname);
    $scope.xlsusers = [
        {Fname: 'dd', Lname: 'Data.tname', email: 'dsa@ss', tel: '12332', test: Data.tname},
        {Fname: 'dd', Lname: 'test1', email: 'dsa@ss', tel: '12332', test: 'test1'}
    ];

    $scope.addOne = function (v) {
//        var tmp = $scope.xlsusers;
        var i = $scope.xlsusers.indexOf(v);
//        if (i > -1) {
        $scope.xlsusers.splice(i + 1, 0, {Fname: '', Lname: '', email: '', tel: '', test: ''});
//        }
//      $scope.xlsusers.push({Fname: '', Lname: '', email: '', tel: ''}) ;
    };

    $scope.removeOne = function (v) {
//        var tmp = $scope.xlsusers;
        var i = $scope.xlsusers.indexOf(v);
        if (i > -1) {
            $scope.xlsusers.splice(i, 1);
        }
//        $scope.xlsusers = tmp;
    };


    $scope.refresh = function () {
//    去重
        var tmp = $scope.xlsusers;
        var list = ['::'];
        $scope.testlist = [];
        var tmpp = [
            {Fname: '', Lname: '', email: '', tel: '', test: ''}
        ];
        for (var i in tmp) {
            if ($.inArray(tmp[i].email + "::" + tmp[i].test, list) == -1) {
                list.push(tmp[i].email + "::" + tmp[i].test);
                tmpp.push(tmp[i]);
                if ($.inArray(tmp[i].test, $scope.testlist) == -1) {
                    if (tmp[i].test == '') {
                        $scope.testlist.push('notSelect');
                        continue
                    }
                    $scope.testlist.push(tmp[i].test);
                }
            }


        }
        console.log($scope.testlist);
        $scope.xlsusers = tmpp;
    };
    $scope.refresh();
    $scope.active = $scope.testlist[1];
//    $scope.$watch('xlsusers', function() {
////        $scope.updated++;
//
//        console.log('ddddddddddd');
//        console.log($scope.xlsusers);
//        console.log('ddddddddddd');
//    });
    $scope.selectTest = function (target) {
        $scope.active = target.getAttribute('data');
    };

    /*
     excel read*/

    var use_worker = true;

    function xlsworker(data, cb) {
        var worker = new Worker('./js/xlsworker.js');
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
            $scope.refresh();
//            var tmp = [];
//            $.each($scope.xlsusers, function (i, el) {
//                if ($.inArray(el, tmp) === -1) tmp.push(el);
//            });
//            $scope.xlsusers = tmp;
//            $.unique($scope.xlsusers);
        });
//        console.log($scope.xlsusers);
//        console.log(output["Sheet1"]);
    }


    function handleDrop(e) {
        e.stopPropagation();
        e.preventDefault();
        var files = e.dataTransfer.files;
        var i, f;
        for (i = 0, f = files[i]; i != files.length; ++i) {
            var reader = new FileReader();
            var name = f.name;
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

    window.onload = function () {
        var drop = document.getElementById('drop');
        if (drop.addEventListener) {
            drop.addEventListener('dragenter', handleDragover, false);
            drop.addEventListener('dragover', handleDragover, false);
            drop.addEventListener('drop', handleDrop, false);
        }

    };

    $scope.upload = function () {
        $http({
            url: "/test/manage/invite",
            method: 'POST',
            headers: {
                "Authorization": Data.token
            },
            data: {"user": {"uid": Data.uid}, "subject": $scope.subject, "replyTo": $scope.replyTo, "tid": $scope.tid, "invite": $scope.userlist}
        }).success(function (data) {
            $scope.state = data["state"];//1 true or 0 false
            Data.token = data["token"];
            $scope.message = data["message"];
            if ($scope.state) {

            } else {

            }
        }).error(function (data) {

        });
    }
}
OJApp.filter('filterTest', function () {
    return function (items, v) {
        var r = [];
        if (v=="notSelect"){v=""};
        angular.forEach(items, function (item) {
            if (item.test == v) {
                r.push(item);
            } else {
                if ((item.test == '') && (item.email == '')) {
                    r.push(item)
                }
            }
        });
        return r;
    }
});
