// Validating Empty Field
function check_empty() {
	alert("m in");
if (document.getElementById('name').value == "" || document.getElementById('type').value == "" || document.getElementById('condition').value == "" || document.getElementById('value').value == "") {
alert("Fill All Fields !");
} else {
document.getElementById('form').submit();
alert("Form Submitted Successfully...");
}
}
//Function To Display Popup
function div_show() {
document.getElementById('abc').style.display = "block";
}
//Function to Hide Popup
function div_hide(){
document.getElementById('abc').style.display = "none";
}