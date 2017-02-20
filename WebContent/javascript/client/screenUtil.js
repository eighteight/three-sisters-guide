function Resolution(){}

function getScreenResolution() {
	if (window.innerWidth)
	{
		theHeight = window.innerHeight
	}
	else if (document.documentElement && document.documentElement.clientHeight)
	{
		theHeight = document.documentElement.clientHeight
	}
	else if (document.body)
	{
		theHeight = document.body.clientHeight
	}
	
	var resolution = new Resolution();
	
	resolution.width = screen.width;
	resolution.height = theHeight;	
	return screen.width+"#"+theHeight;//resolution;
}

function writeCookie(resolution){
	var date = new Date();
	ThreeHundredDays=3*24*60*60*1000*100; //in millisec

	date.setTime(date.getTime()+ThreeHundredDays);
	document.cookie='width='+ resolution.width +'; expires='+date.toGMTString();
	document.cookie='height='+ resolution.height+'; expires='+date.toGMTString();
	
}

function getResolutionFromCookie() {
	var nameEQ = "viewport=";
	var ca = document.cookie.split(';');
	for(var i=0;i < ca.length;i++) {
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1,c.length);
		if (c.indexOf(nameEQ) == 0) {
			var resolution = new Resolution();
			var res = c.substring(nameEQ.length, c.length).split('x');
			resolution.width = res[0];
			resolution.height = res[1];
			return resolution;
		}
	}
	return null;
}


function getQueryString() {
	var qsParm = new Array();
	var query = window.location.search.substring(1);
	var parms = query.split('&');
	for (var i=0; i<parms.length; i++) {
		var pos = parms[i].indexOf('=');
		if (pos > 0) {
			var key = parms[i].substring(0,pos);
			var val = parms[i].substring(pos+1);
			qsParm[key] = val;
		}
	}
	return qsParm;
}