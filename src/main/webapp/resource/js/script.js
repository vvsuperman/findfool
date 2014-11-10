var util ={
	isLocalStorage: function() {
		if ('localStorage' in window && window.localStorage !== null){
			return true;
		}
	}
};

$(window).load(function() {

    // preparing clipboard
    ZeroClipboard.setMoviePath('static/ZeroClipboard10.swf');

    var copyMd = new ZeroClipboard.Client(),
		copyHtml = new ZeroClipboard.Client();

    copyMd.addEventListener('mouseOver', function (client) {
		copyMd.setText('<p><markdown>\n'+ '#' + $('#title').val() + '#\n' + $('#wmd-input').val() +'\n</markdown></p>');
	});
	copyHtml.addEventListener('mouseOver', function (client) {
		copyHtml.setText($('#wmd-output').text());
	});

	copyMd.addEventListener('complete', function (client, text) {
		alert("Markdown copied to clipboard");
    });
	copyHtml.addEventListener('complete', function (client, text) {
		alert("HTML copied to clipboard");
    });

    copyMd.glue( 'copy_md_btn', 'copy_md' );
    copyHtml.glue( 'copy_html_btn', 'copy_html' );

	

	// Resizable textarea
    $('textarea.resizable:not(.processed)').TextAreaResizer();

	// Highlighting code with Google prettify 
	var prettify = function(){	
		$('#wmd-preview pre').addClass('prettyprint');
		prettyPrint();
	};


	$('#wmd-input').keyup(function(){
		prettify();
		if(util.isLocalStorage()){
			localStorage.text = $(this).val();
		}
	});
	
	// storing text locally
	if(util.isLocalStorage()){
		if(localStorage.text){		
			$('#wmd-input').val(localStorage.text);
		}
	}
	
	// remove data locally when textarea is cleared, so the text reverts to defaults
	$('#clear').click(function(){
        $('#wmd-input').val('');
		if(util.isLocalStorage()){
			localStorage.removeItem('text');
		}
    });
	
	prettify();

});


