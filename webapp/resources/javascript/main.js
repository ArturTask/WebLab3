var r;
var prevR;

function rButtonsValidate(input, value) {
	prevR=r;
	r = value;
	changeR(r);
	let buttons = document.getElementsByClassName("rButton");
	
	let hiddenInput = document.getElementById("rHiddenButton");

	hiddenInput.value = input.value;
	console.log(hiddenInput.value);

	for (let i = 0; i < buttons.length; i++)
	{
		if (buttons[i].className.includes(" greenButton"))
		{
			buttons[i].className = buttons[i].className.replace(" greenButton", "");
		}
	}

	if (input.className.includes(" greenButton"))
	{
		input.className.replace(" greenButton", "");
	}
	else
	{
		input.className = input.className + " greenButton";
	}
}
	// function clearallbuttons() {
	// 	$("coord").trigger("reset")
	// }



