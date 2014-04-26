/**
 *   verify.js contains all javascript required for verifying content.
 *   Requires jquery
 */

$(function(){
	
	// Verify Question
	$('.verify').click(function(){
		var btn = $(this);
		$.ajax({
            type: 'POST',
            data: 'cid=' + $(this).attr('cid') + '&action=verifycontent',
            url: '/verify',
            success: function(data){
            	btn.closest('.content-holder').remove();
            }
        });
	});
	
	$('.remove').click(function(){
		var btn = $(this);
		$.ajax({
            type: 'POST',
            data: 'cid=' + $(this).attr('cid') + '&action=removecontent',
            url: '/verify',
            success: function(data){
            	btn.closest('.content-holder').remove();
            }
        });
	});
	
});