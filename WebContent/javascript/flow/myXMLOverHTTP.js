
	function myXMLOverHTTP( prms ) 
	{
		var thisXOH		= this
		var baseObj		= document.getElementsByTagName('base')[0]
		
		// Свойства
		this.prms				= prms || {}
		this.proceedor			= prms.proceedor || false
		this.onResponseXMLError	= prms.onResponseXMLError || false
		this.base				= baseObj ? baseObj.href : false
		
		// Создаем объект обмена
		this.transport = window.XMLHttpRequest 
			? new XMLHttpRequest()
			: new ActiveXObject('Msxml2.XMLHTTP') || new ActiveXObject('Microsoft.XMLHTTP')
		if (this.transport)
			this.transport.onreadystatechange = function()
			{
				return thisXOH.listen()
			}
		
		return this
		}
	
	myXMLOverHTTP.prototype.get = function( url ) 
	{
		if (this.base)
			url = this.base + url
		this.lastUrl	= url
		this.transport.open
		( 
			'GET', 
			(/[?]/.test(url)) 
				? url + '&' + Math.random() 
				: url + '?' + Math.random(),
			true
		)
		if (window.Progress)
			window.Progress()
		return this.transport.send( null )
	}
		
	myXMLOverHTTP.prototype.post = function( url, obj ) 
	{
		var postString = []
				
		// Формируем строку
		if (obj.tagName && obj.tagName.toLowerCase() == 'form') 
			for (var i=0; i<formObj.elements.length; i++)
				postString.push( formObj.elements[i].name + '=' + formObj.elements[i].value )
		else if (obj.constructor == Object)
			for (var i in obj)
			{
				if (obj[i] != null)
					postString.push( i + '=' + obj[i] )
			}
		else if (obj.constructor == String) 
			postString.push( obj )
		
		// Отправляем запрос
		if (this.base)
			url = this.base + url
		this.transport.open
		( 
			'POST', 
			url, 
			true 
		)
		this.transport.setRequestHeader
		(
			'Content-Type', 
			'application/x-www-form-urlencoded'
		)
		if (window.Progress)
			window.Progress()
		return this.transport.send
		( 
			(postString.length > 0) 
				? postString.join('&')
				: null 
		)
	}
	
	myXMLOverHTTP.prototype.listen = function() 
	{
		switch (this.transport.readyState) 
		{
		
			case 0:
				break
				
			case 1:
				break
				
			case 2:
				break
				
			case 3:
				break
				
			case 4:
				this.lastXML 	= this.transport.responseXML || false
				this.lastText	= this.transport.responseText || false
				if ((this.lastXML || this.lastText) && this.proceedor) 
					this.proceedor( this.lastXML, this.lastText )
				else
				{
					if (this.onResponseXMLError)
						this.onResponseXMLError()
					else
					{
						false
					}
				}
				if (window.Progress)
					window.UnProgress()
				break
		
		}
	return true
	}
	