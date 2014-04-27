/**
 *   login.js contains all javascript required for login page.
 *   Requires jquery and jquery-ui
 */

$(function(){
	// Set enter key to click the loginButton
	$("#loginEmail, #loginPassword").keyup(function(event){
	    if(event.keyCode == 13){
	        $("#loginButton").click();
	    }
	});
	// Try to log in
	$('#loginButton').click(function(){
		
		// Make sure email isn't empty
		if($('#loginEmail').val() === '') {
			$('#loginEmail').effect('shake');
			return;
		}
		// Make sure password isn't empty
		if($('#loginPassword').val() === ''){
			$('#loginPassword').effect('shake');
			return;
		}
		// Show ajax spinner
		$('#loginLoading').show();
		// Try to log in
		$.ajax({
            type: 'POST',
            data: 'email=' + encodeURIComponent($('#loginEmail').val()) + '&password=' + encodeURIComponent($('#loginPassword').val()) + '&action=login',
            url: 'login',
            success: function(data) {
            	$('#loginLoading').hide();
            	// If successful Login, redirect to home else  
            	if(data === 'success') 
            		window.location.href = "home";
            	else {
            		$('#loginEmail').effect('shake');
            		$('#loginPassword').effect('shake');
            	}   		
            },
            error: function(data) {
            	// Couldn't connect to the server
            	$('#loginLoading').hide();
            	$('#loginEmail').effect('shake');
        		$('#loginPassword').effect('shake');
            	console.log(data);
            }
        });
	});
	
});