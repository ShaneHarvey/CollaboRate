/**
 *  Functions for tiny mce
 */

function tinyitize(){
	tinymce.init({
		plugins : [
				"eqneditor advlist autolink lists link image charmap print preview anchor",
				"searchreplace visualblocks code fullscreen",
				"insertdatetime media table contextmenu paste" ],
		toolbar : "undo redo | eqneditor link image | styleselect | bold italic | bullist numlist outdent indent ",
		selector : ".rt"
	});
}

function validTinyInput(input) {
	return input !== '' && input !== '<p><br data-mce-bogus="1"></p>' && input !== '<p></p>';
}