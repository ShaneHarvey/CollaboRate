/**
 *   account.js contains all javascript required for create account and account settings page.
 *   Requires jquery and jquery-ui
 */

var editing = false;
const emailRegex = '^[_a-zA-Z0-9-]+(\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*(\.[a-z]{2,4})$';
const passwordRegex = '^(.{0,}(([a-zA-Z][^a-zA-Z])|([^a-zA-Z][a-zA-Z])).{4,})|(.{1,}(([a-zA-Z][^a-zA-Z])|([^a-zA-Z][a-zA-Z])).{3,})|(.{2,}(([a-zA-Z][^a-zA-Z])|([^a-zA-Z][a-zA-Z])).{2,})|(.{3,}(([a-zA-Z][^a-zA-Z])|([^a-zA-Z][a-zA-Z])).{1,})|(.{4,}(([a-zA-Z][^a-zA-Z])|([^a-zA-Z][a-zA-Z])).{0,})$';


$(function(){
	
	// CREATE ACCOUNT PAGE
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
            data: 'email=' + encodeURIComponent($('#signupEmail').val()) + '&password=' + encodeURIComponent($('#signupPassword').val()) + '&action=createaccount',
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
	
	// ACCOUNT SETTINGS PAGE
	$('#allowEditButton').click(function(){
		if(!editing) {
			editing = true;
			// Show all hidden stuff
			$('.hideGroup').show();
			$('input').removeAttr('disabled');
		}
		else {
			editing = false;
			$('.hideGroup').hide();
			$('input').attr('disabled', 'disabled');
		}
	});
	$('#editInfoButton').click(function(){
		// Make sure current password is there
		if($('#currentPassword').val() === '') {
			$('#currentPassword').effect('shake');
			return;
		}
		// Make sure password is valid and they match
		// Make sure password matches password regex
		if(!$('#newPassword').val().match(passwordRegex)) {
			$('#newPassword').effect('shake');
			return;
		}
		// Make sure confirm password matches password
		if($('#newPassword').val() !== $('#confirmNewPassword').val()) {
			$('#newPassword').effect('shake');
			$('#confirmNewPassword').effect('shake');
			return;
		}
		// Show ajax spinner
		$('#accountLoading').show()
		
		// Try to change account info
		$.ajax({
            type: 'POST',
            data: 'displayName=' + encodeURIComponent($('#displayName').val()) + '&currentPassword=' + encodeURIComponent($('#currentPassword').val()) + '&newPassword=' + encodeURIComponent($('#newPassword').val()) + '&action=updateaccount',
            url: '/account',
            success: function(data) {
            	$('#accountLoading').hide();
            	// If successful update, hide stuff, else shake  
            	if(data === 'success') 
            		$('#allowEditButton').click();
            	else {
            		$('#displayName').effect('shake');
            		$('#currentPassword').effect('shake');
            		$('#newPassword').effect('shake');
            		$('#confirmNewPassword').effect('shake');
            	}
            },
            error: function(data) {
            	// Couldn't connect to the server
            	$('#displayName').effect('shake');
        		$('#currentPassword').effect('shake');
        		$('#newPassword').effect('shake');
        		$('#confirmNewPassword').effect('shake');
    			$('#accountLoading').hide();
            	console.log(data);
            }
        });
	});
});