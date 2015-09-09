/*
 * object for page
 * created by zpl on 2014/7/18
 */

//!window.Simditor && 
//document.write('<script src=resource/js/simditor/module.js><\/script>');
//document.write('<script src=resource/js/simditor/hotkeys.js><\/script>');
//document.write('<script src=resource/js/simditor/uploader.js><\/script>');
//document.write('<script src=resource/js/simditor/simditor.js><\/script>');
OJApp.controller('MyTestBank', [ '$scope', function($scope) {
	$scope.url = '#/mybank';
	$scope.template = 'page/mytestBank.html';
	$scope.ContentUs = 'page/contentUs.html';
	$scope.leftBar = 'page/libleftBar.html';
	$scope.active = 1;
	$scope.show = 1;
} ]);

OJApp
		.controller(
				'mytestbank',
				[
						'$scope',
						'$http',
						'Data',
						'$sce',
						'$modal',
						function($scope, $http, Data, $sce, $modal) {

							$scope.url = '#/mybank';
							$scope.template = 'page/mytestBank.html';
							$scope.ContentUs = 'page/contentUs.html';
							$scope.leftBar = 'page/libleftBar.html';
							$scope.active = 1;
							$scope.show = 1;

							$scope.active = 1;
							$scope.show = 1;

							$scope.token = {};
							$scope.token.data = Data.token();

							$scope.Qtype = [ {
								name : '选择题',
								data : '1'
							},
							// { name: '编程题', data: '2'},
							{
								name : '简答题',
								data : '3'
							}, {
								name : '设计题',
								data : '4'
							} ];
							$scope.page = 1;
							$scope.keyword = "";
							$scope.tag = "";
							$scope.context = "";
							// document.getElementById("context").
							// add by zpl
							$scope.reciveData = new Object();
							$scope.reciveData.selectedSets = null;
							$scope.reciveData.totalPage = 1;
							$scope.reciveData.pageNum = 10;// 默认一页10个
							$scope.reciveData.type = 1;
							$scope.reciveData.keyword = '';
							$scope.reciveData.pagelist = new Array();
							$scope.reciveData.frontPage = false;
							$scope.reciveData.rearPage = false;
							$scope.reciveData.currentPage = 1;
							$scope.reciveData.index = 1;
							$scope.reciveData.choosedQ = null;
							$scope.progrma = new Object();
							$scope.progrma.show = false;
							$scope.newQuestion = new QuestionMeta();
							$scope.myu = 1;
							$scope.reciveData.choosedQlist = new Array();

							$scope.editorOptions = {
								language : 'ru',
								uiColor : '#000000'
							};

							$scope.onSuccess = function(data) {
								if (data.state == 0) {
									flashTip(data.msg)
								} else {

								}
							}

							$scope
									.$on(
											"questionModify",
											function(event, data) {
												// $timeout(function(){
												// $scope.qs[$scope.modifyQsIndex]=data;
												// },1000);

												$scope.reciveData.questions[$scope.modifyQsIndex] = data;
											});

							// 查看和修改试题的通用方法
							$scope.modifyQuestionInTest = function(size, q,
									params, modifyQsIndex) {
								// · var question = jQuery.extend(true, {}, q);
								// 为何要用深拷贝？
								var modalInstance = $modal.open({
									templateUrl : 'page/myModalContent.html',
									controller : 'ModalInstanceCtrl',
									size : size,
									resolve : {
										params : function() {
											var obj = {};
											obj.operation = params.operation;
											obj.title = params.title;
											obj.question = q;
											obj.index = modifyQsIndex;
											return obj;
										}
									}
								});

							};

							// add by zpl
							$scope.computePage = function() {
								var index = Math
										.ceil($scope.reciveData.totalPage
												/ $scope.reciveData.pageNum);
								index = index == 0 ? 1 : index;
								$scope.reciveData.index = index;
								if (index <= 1) {
									$scope.reciveData.frontPage = false;
									$scope.reciveData.rearPage = false;
								}
								if ($scope.reciveData.currentPage >= index) {
									$scope.reciveData.currentPage = index;
									$scope.reciveData.rearPage = false;
									$scope.reciveData.frontPage = true;
								} else if ($scope.reciveData.currentPage <= 1) {
									$scope.reciveData.currentPage = 1;
									$scope.reciveData.rearPage = true;
									$scope.reciveData.frontPage = false;
								} else {
									$scope.reciveData.rearPage = true;
									$scope.reciveData.frontPage = true;
								}
								$scope.reciveData.pagelist = new Array();
								for (i = 1; i <= index; i++) {
									var obj = new Object();
									obj.current = false;
									obj.index = i;
									$scope.reciveData.pagelist.push(obj);
								}
								$scope.reciveData.pagelist[$scope.reciveData.currentPage - 1].current = true;
							}

							$scope.getQuestions = function(sendData) {
								$http({
									url : WEBROOT + "/search/my",
									method : 'POST',
									headers : {
										"Authorization" : Data.token()
									},
									data : sendData
								})
										.success(
												function(data) {
													$scope.state = data["state"];// 1
													// true
													// or 0
													// false
													$scope.message = data["message"];
													if ($scope.state) {
														// 仅需要对message中的数据做处理
														// total pageNum
														$scope.reciveData.totalPage = $scope.message.totalPage;
														$scope.reciveData.pageNum = $scope.message.pageNum;
														// 删除已添加到测试中的试题
														/*
														 * var questions =
														 * $scope.message.questions;
														 * var pids=[]; for(i in
														 * $scope.qs){
														 * pids.push($scope.qs[i].pid); }
														 * for(i in questions){
														 * if(pids.indexOf(questions[i].pid)!=-1){
														 * questions.splice(i,1); } }
														 * $scope.reciveData.questions =
														 * questions;
														 */
														$scope.reciveData.questions = $scope.message.questions;
														$scope.computePage();
													} else {
														$scope.reciveData.totalPage = 1;
														$scope.reciveData.pageNum = 0;
														$scope.reciveData.questions = null;
													}
												}).error(function(data) {
											// error
										});
							}

							$scope.oneAtATime = true;

							$scope.showContent = function(qid) {
								console.log("qid........." + qid);
								var panelId = "panel" + qid;
								panelId = true;
							}

							$scope.gettrustContext = function(q) {
								var trust = "<span>内容:";
								trust += q.context + "</span><br/>";
								trust += "<span>&nbsp;&nbsp;&nbsp;标签:";
								trust += q.tag + "</span><br/> <span>";
								for (var i = 0; i < q.answer.length; i++) {
									trust += "<p>" + i + ":";
									trust += q.answer[i].text;
									trust += "</p>";
								}
								trust += "</span>";
								return $sce.trustAsHtml(trust);
							};

							$scope.queryQuestions = function(index) {
								$scope.reciveData.currentPage = index;
								for (i = 0; i < $scope.reciveData.pagelist.length; i++) {
									$scope.reciveData.pagelist[i].current = false;
								}
								$scope.reciveData.pagelist[index - 1].current = true;
								var sendData = new Object();
								sendData.user = new Object();
								sendData.user.uid = Data.uid();
								sendData.type = $scope.reciveData.type;
								sendData.keyword = $scope.reciveData.keyword;
								sendData.page = index;
								sendData.pageNum = $scope.reciveData.pageNum;
								$scope.getQuestions(sendData);
							}
							$scope.computePage();
							$scope.queryQuestions(1);

							// $scope.template = $scope.Qtype[0];
							$scope.GoPage = function(data) {
								// console.log($scope.context);
								$scope.show = 1;
								$scope.keyword = "";
								$scope.tag = "";
								$scope.context = "";
								$scope.active = data;
								$scope.reciveData.type = $scope.active;
								// $scope.newQuestion.tag = "";
								$scope.newQuestion = new QuestionMeta();
								$scope.queryQuestions(1);
								// $scope.template =
								// $scope.templates[$scope.active - 1];
							};

							$scope.showAddPage = function(active) {
								// 初期只允许新增选择题和简答题
								$scope.active = active;
								// $scope.active = active;
								$scope.show = 0;
								$scope.context = "";
								$scope.tag = "";
							};

							$scope.isNum = function(q) {
								if (q == null || q == "")
									return;
								var r = /^\+?[0-9][0-9]*$/;
								if (!r.test(q)) {
									flashTip("必须是数字！");
									q = 0;
								}
							};
							$scope.pushQuestion = function(sendData) {

								$http({
									url : WEBROOT + "/question/add",
									method : 'POST',
									headers : {
										"Authorization" : Data.token()
									},
									data : sendData
								})
										.success(
												function(data) {
													$scope.state = data["state"];// 1
													// true
													// or 0
													// false
													if (data["token"] != ""
															&& data["token"] != null)
														Data
																.setToken(data["token"]);
													$scope.message = data["message"];
													if ($scope.state) {
														flashTip('添加成功');
														$scope.show = "1";
														$scope
																.queryQuestions(1);
														$scope.newQuestion = new QuestionMeta();
													} else {
														flashTip('添加失败');
													}
												}).error(function(data) {

										});
							};

							$scope.addQuestion = function() {
								$scope.newQuestion.type = parseInt($scope.active);
								if (($scope.newQuestion.answer == "")
										&& ($scope.newQuestion.type == 1)) {
									flashTip("请输入至少一个选项");
									return false;
								}
								if ($scope.active == "1") {
									for (a in $scope.newQuestion.answer) {
										a.isright = (a.isright == false ? "0"
												: "1");
									}
								}
								var tags = $scope.newQuestion.tag;

								if (tags == "") {
									$scope.newQuestion.tag = [];
								} else if (tags.split(",").length >= tags
										.split("，")) {
									$scope.newQuestion.tag = tags.split(",");
								} else {
									$scope.newQuestion.tag = tags.split("，");
								}

								$scope.newQuestion.setid = 0;

								sendData = {
									"user" : {
										"uid" : Data.uid()
									},
									"quizId" : $scope.tid,
									"question" : $scope.newQuestion
								};
								$scope.pushQuestion(sendData);
							};

							$scope.cancel = function() {
								$scope.show = "1";
							};
							$scope.resetQuestion = function() {
								$scope.newQuestion.name = "";
								$scope.newQuestion.context = "";
								$scope.newQuestion.answer = null;
								// $scope.newQuestion.answer = new Array();
								$scope.newQuestion.tag = "";
								$scope.newQuestion.type = $scope.active;
								// var ans = new Object();
								// ans.text="";
								// ans.score=0;
								// if($scope.active == "1")
								// ans.isright=false;
								// else
								// ans.isright = "";
								// $scope.newQuestion.answer.push(ans);
								// var tags = "";
								// $scope.newQuestion.tag = tags;
							};
							$scope.searchmy = function(keyword) {

							};
							// $scope.searchmy();
							$scope.addOne = function() {
								// var ans = new Answers();
								var ans = {};
								if ($scope.active == "1")
									ans.isright = false;
								else
									ans.isright = "";
								$scope.newQuestion.addAnswer(ans);
							};

							$scope.removeOne = function(v) {
								$scope.newQuestion.removeAnswer(v);
							};

							$scope.InsertQuestion = function() {
								// $scope.show = "0";
								// console.log($scope.reciveData.choosedQ);
								// $scope.newQuestion.qid =
								// $scope.reciveData.choosedQ.qid;
								var qid = [];
								console.log($scope.qs);
								for (q in $scope.qs) {
									qid.push($scope.qs[q].qid)
								}
								qid.push($scope.reciveData.choosedQ.qid);
								var uqid = [];
								$.each(qid, function(i, el) {
									if ($.inArray(el, uqid) === -1)
										uqid.push(el);
								});
								$http({
									url : WEBROOT + "/test/manage/submite",
									method : 'POST',
									headers : {
										"Authorization" : Data.token()
									},
									data : {
										"user" : {
											"uid" : Data.uid()
										},
										"quizid" : $scope.tid,
										"qids" : uqid
									}
								})
										.success(
												function(data) {
													$scope.state = data["state"];// 1
													// true
													// or 0
													// false
													// Data.token =
													// data["token"];
													$scope.message = data["message"];
													if ($scope.state) {
														// 仅需要对message中的数据做处理
														// alert($scope.message.msg);
														$scope.tid = $scope.message.quizid;
														window.location.href = '#/test/'
																+ $scope.tid;
													} else {

													}
												}).error(function(data) {

										});
							};
							$scope.addOnelist = function(q) {
								var idx = $scope.reciveData.choosedQlist
										.indexOf(q);

								// is currently selected
								if (idx > -1) {
									$scope.reciveData.choosedQlist.splice(idx,
											1);
								}
								// is newly selected
								else {
									if ($scope.myu == 1) {
										$scope.reciveData.choosedQ = q;
										$scope.reciveData.choosedQlist = new Array();
									}
									$scope.reciveData.choosedQlist.push(q);
								}
							}
						} ]);
