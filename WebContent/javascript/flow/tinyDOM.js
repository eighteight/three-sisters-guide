	
	function $( id ) 
	{ 
		return document.getElementById( id ) 
	}
	
	var DOM	= 
	{
		
		parseRER		: new RegExp( '^ct' ),
		parseTemplate	: function( obj, template )
		{
			var obj 		= obj 		|| document.body
			var template	= template	|| {}

			for (var i=0; i<obj.childNodes.length; i++)
				if (obj.childNodes[i].nodeType == 1)
				{
					if (/^ct/.test(obj.childNodes[i].id))
					{
						var name	= obj.childNodes[i].id.replace( DOM.parseRER, '' )
						template[ name ]	=
						{
							e		: obj.childNodes[i]
						}
						if (obj.childNodes[i].hasChildNodes())
							DOM.parseTemplate( obj.childNodes[i], template[ name ] )
					}
					else
						if (obj.childNodes[i].hasChildNodes())
							DOM.parseTemplate( obj.childNodes[i], template )
				}
			
			return template
		}
	}