// Initialize the sortable list 
$('.sortable').sortable().bind('sortupdate', function () {
    //Triggered when the user stopped sorting and the DOM position has changed.
	var subjectKeyList = [];
	var index = 0;
	$('.sortable li a').each( function () {
		var href = $(this).attr('href');
		var key = href.split('=')[1];
		subjectKeyList[index] = key;
		index += 1;
	});
	$.ajax({
		type : 'POST',
		data : 'toplist='+JSON.stringify(subjectKeyList) + '&action=updatetopics',
		url : 'subject'
	});
});