
var numletter="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

submitentrySolution();

// origine
function submitentry(){
	verification = document.getElementById("passwd").value;
	
	alert("Searching.");
	alert("Searching..");
	alert("Searching...");
	
	password = numletter.substring(11,12); // c
	password = password + numletter.substring(18,19); // i
	password = password + numletter.substring(23,24); // p
	password = password + numletter.substring(16,17); // h
	password = password + numletter.substring(24,25); // e
	password = password + numletter.substring(1,4);
    
	if(verification == password){
		alert("Well done, you've got it!");
	} else {
		alert("Nahh, thats wrong!");
	}
}

// solution
function submitentrySolution(){
	
	password = numletter.substring(11,12); // c
	password = password + numletter.substring(18,19); // i
	password = password + numletter.substring(23,24); // p
	password = password + numletter.substring(16,17); // h
	password = password + numletter.substring(24,25); // e
	password = password + numletter.substring(1,4);
    
    alert(password);
    // bingo123

}
