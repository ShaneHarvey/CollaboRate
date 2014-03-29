/**
 *   createAccount.js contains all javascript required for createAccount page.
 *   Requires jquery and jquery-ui
 */

const emailRegex = '^[_a-zA-Z0-9-]+(\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*(\.[a-z]{2,4})$';
const passwordRegex = '^(.{0,}(([a-zA-Z][^a-zA-Z])|([^a-zA-Z][a-zA-Z])).{4,})|(.{1,}(([a-zA-Z][^a-zA-Z])|([^a-zA-Z][a-zA-Z])).{3,})|(.{2,}(([a-zA-Z][^a-zA-Z])|([^a-zA-Z][a-zA-Z])).{2,})|(.{3,}(([a-zA-Z][^a-zA-Z])|([^a-zA-Z][a-zA-Z])).{1,})|(.{4,}(([a-zA-Z][^a-zA-Z])|([^a-zA-Z][a-zA-Z])).{0,})$';

/*
 * Password must contain at least one upper case letter, lower case letter, number, at least 6 characters long
 */
$(function(){
	
	$('#signupButton').click(function(){
		// Make sure email matches email regex
		if(!$('#signupEmail').val().match(emailRegex)) {
			$('#signupEmail').effect('shake');
			return;
		}
		// Make sure password matches password regex
		if(!$('#signupPassword').val().match(passwordRegex)) {
			$('#signupPassword').effect('shake');
			return;
		}
		// Make sure confirm password matches password
		if($('#signupPassword').val() !== $('#signupConfirmPassword').val()) {
			$('#signupPassword').effect('shake');
			$('#signupConfirmPassword').effect('shake');
			return;
		}
		
		// Make sure that user agrees to terms and conditions
		if(!$('#agreeTermsCheckbox').is(':checked')) {
			$('#termsHolder').effect('shake');
			return;
		}
		
		// Show ajax spinner
		$('#signupLoading').show()
		
		// Try to create account
		$.ajax({
            type: 'POST',
            data: '{ "email" : "' + encodeURIComponent($('#signupEmail').val()) + '" , "password" : "' + encodeURIComponent($('#singupPassword')) + '", "action" : "createAccount" }',
            url: 'signup',
            success: function(data) {
            	$('#signupLoading').hide();
            	// If successful Login, redirect to home, if email is used already shake email, else shake all  
            	if(data === 'success') 
            		window.location.href = "home";
            	else
            		$('#signupEmail').effect('shake');
            },
            error: function(data) {
            	// Couldn't connect to the server
            	$('#signupEmail').effect('shake');
    			$('#signupPassword').effect('shake');
    			$('#signupConfirmPassword').effect('shake');
    			$('#signupLoading').hide();
            	console.log(data);
            }
        });
	});
	
});