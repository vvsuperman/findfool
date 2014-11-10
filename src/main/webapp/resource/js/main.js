/**
 * Created by liuzheng on 6/10/14.
 */
require.config({
    paths: {
        "jquery": "jquery.min",
        "bootstrap": "bootstrap.min",
        "angular": "angular.min"
    }
});


require(['jquery', 'bootstrap', 'angular'],
    function (holder, controller){
// some code here
});

//
//    <script src="http://cdn.bootcss.com/holder/2.0/holder.min.js"></script>
//    <script src="js/holder.min.js"></script>
//    <script src="js/angular.min.js"></script>
//    <script src="js/controller.js"></script>