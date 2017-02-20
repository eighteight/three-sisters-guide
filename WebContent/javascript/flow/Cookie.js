
	var Client	=
	{
		set		: function( name, value )
		{
			if ( name.constructor == Object && !value )
				for (var i in name)
					Client.set
					( 
						i, 
						name[ i ] 
					)
			else 
			{
				if (name == undefined || value == undefined)
					return false
				if ( name.constructor == String )
				{
					if ( value.constructor == Array )
						value	= value.join( '|' )
					document.cookie	= name + '=' + escape(value) + ';path=/'
				}
			}
			return Client.refresh()
		},
		get		: function( name )
		{
			if (Client.cookies[ name ])
			{
				if (/\|/.test(Client.cookies[ name ]))
					return Client.cookies[ name ].split('|')
				switch (Client.cookies[ name ])
				{
					case 'true':
						return true
					case 'false':
						return false
					default:
						if (/^[0-9]+$/.test(Client.cookies[ name ]))
							return parseInt( Client.cookies[ name ] )
						return unescape(Client.cookies[ name ])
				}
			}
			return undefined
		},
		refresh	: function()
		{
			var cookies	= document.cookie.split(/; ?/)
			for (var i=0; i<cookies.length; i++)
			{
				var nv	= cookies[ i ].split('=', 2)
				Client.cookies[ nv[0] ] = nv[ 1 ]
			}
			return true
		},
		cookies	: {}
	}
	
	Client.refresh()
	
