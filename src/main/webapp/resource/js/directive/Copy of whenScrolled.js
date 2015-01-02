OJApp.directive('whenscrolled', function() { 
  return function(scope, elm, attr) { 
    var raw = elm[0]; 
    elm.bind('scroll', function() { 
      if (raw.scrollTop+raw.offsetHeight >= raw.scrollHeight) { 
        scope.$apply(attr.whenScrolled); 
      } 
    }); 
  }; 
});

OJApp.directive('simditor', function ($timeout) {
    return {
        restrict: 'A',
        require: '?ngModel',
        scope: true,
        link: function (scope, elem, attrs, ngModel) {
        	
        }
    }
});


function mouseScrollRun (site) {
    var marginPosition = null;
    if(site === "down" && page < 6){
        marginPosition = parseInt($container.css("marginTop"));
        if(page == 1){
            animate(marginPosition - clientHeight - 90);
        }else if(page == 5){
            animate(marginPosition - footerHeight);
            $headerWrap.hide();
        }else {
            animate(marginPosition - clientHeight);
        }
        page++;
    }else if(site === "up" && page > 1){
        marginPosition = parseInt($container.css("marginTop"));
        if(page == 2){
            animate(marginPosition + clientHeight + 90);
        }else if(page == 6){
            animate(marginPosition + footerHeight);
            $headerWrap.show();
        }else {
            animate(marginPosition + clientHeight);
        }
        page--;
    }else if(site === "init"){
        if(page == 1){
            $container.css("marginTop", 0);
        }else if(page == 6){
            $container.css("marginTop", -clientHeight * (page - 2) - footerHeight - 90);
        }else {
            $container.css("marginTop", -clientHeight * (page - 1) - 90);
        }
    }else {
        timeLimit();
    }
}

function animate (terminal) {
    $container.animate({
        marginTop : terminal
    }, 500, timeLimit);
}

//翻页时间限制
function timeLimit () {
    clearTimeout(scrollSwitchTimer);
    scrollSwitchTimer = setTimeout(function () {
        scrollSwitch = true;
    }, 1000)
}

//事件
function mouseScrollFn (e) {
    if(scrollSwitch){
        scrollSwitch = false;
        e = e || window.event;
        //火狐浏览器tetail数值为正数时是向下滚动，与其他浏览器相反
        if(e.wheelDelta){
            if(e.wheelDelta > 0 || e.wheelDelta == 120){
                mouseScrollRun("up");
            }else {
                mouseScrollRun("down");
            }
        }else if(e.detail){
            if(e.detail > 0){
                mouseScrollRun("down");
            }else{
                mouseScrollRun("up");
            }
        }
    }
}
