
	if (/*@cc_on 1+@*/0)
	try { document.execCommand('BackgroundImageCache', false, true) } catch (e) { false }

	function TV( RSSfeedURL )
	{
		var thisZ		= this
		this.global		= 1
		this.url		= RSSfeedURL
		this.canGet		= true
		this.paused		= false
		this.pausedUser	= false
		this.dialog		= false
		this.I			= 0
		this.timer		= 0
		this.list		= []
		this.conf		=
		{
			xmlInterval				: 15000,
			launchInterval			: 100,
			sysAnimationDuration	: 0.1,
			sysAnimationMotion		: 'easeInOutSine',
			slowFactor				: 5
		}
 		this.es					= DOM.parseTemplate()
 		this.defaultSettings	= 
 		{
			dynamicDescription	: true,
			dynamicPauseAll		: false,
			dynamicSlow			: false,
			dynamicLaunch		: true,
			debug				: false,
			onScreen			: 40,
			speed				: 1.5,
			language			: (/^ru/.test(navigator.language)) ? 'RU' : 'EN',
			displayUserPics		: true,
			displayUserNames	: false,
			displayHelp			: true,
			displayTimer		: false,
			singleColorFiler	: true,
			style				: 'igrick',
			kwUsers				: new String(),
			algorithm			: 'A',
			filter				: 'all',
			version				: 3
		}
		for (var i=0; i<9; i++)
			this.defaultSettings[ 'kw' + i ]	= new String()
		this.settings			= this.cloneConfig( this.defaultSettings )			
		
		// }
		//
		//
		// Событийные хендлеры
		//
		// {
		
		this.handlers	=
		{
			showDescription		: function(e)
			{
				return thisZ.describe( e, this, true )
			},
			hideDescription		: function(e)
			{
				return thisZ.describe( e, this, false )
			},
			fixImage			: function()
			{
				if (this.offsetWidth > 50)
					this.style.marginLeft	= (this.offsetWidth - 50) / -2 + 'px'
				if (this.offsetHeight > 50)
					this.style.marginTop	= (this.offsetHeight - 50) / -2 + 'px'
				return true
			},
			parseSetting		: function()
			{
				switch (this.tagName.toLowerCase())
				{
					case 'input':
						switch ( this.type )
						{
							case 'checkbox':
								thisZ.settings[ this.name ]	= this.checked
								break
							case 'text':
								thisZ.settings[ this.name ]	= this.value
								break
						}
						break
					case 'select':
						thisZ.settings[ this.name ]	= this.value
						break
				}
				thisZ.applySettings()
			},
			jailEvent			: function(e)
			{
				var e 	= e || event
				var kc	= e.which || e.keyCode
				if (kc != 27)
				{
					if (e.stopPropogation)
						e.stopPropogation()
					event.cancelBubble	= true
				}
				return true
			},
			timer				: function()
			{
				var now		= new Date()
				var seconds	= Math.floor( (now.getTime() - thisZ.timer) / 1000 )
				thisZ.es.Timer.e.firstChild.nodeValue	= Math.floor( seconds / 60 ).leadingZero( 2 ) + ' : ' + Math.floor( seconds - Math.floor(seconds/60)*60 ).leadingZero( 2 )
				return true
			}
		}
		
		//
		//
		// Восстановление настроек
		//
		// {
		
		if ( Client.get('settings') && (Client.get('version') == this.settings.version ) )
			for (var i in this.settings)
				this.settings[ i ]	= Client.get( i )
		
		this.displaySettings()
		this.applySettings()
		
		// }
		//
		// Диалоги
		//
		// {
		
		if (this.es.Pause)
			this.createDialog( this.es.Pause.e )
		if (this.es.Keywords)
		{
			this.createDialog( this.es.Keywords.e )
			this.es.Keywords.e.onkeypress	= this.handlers.jailEvent
			this.es.Keywords.Save.e.onclick	= function() { return thisZ.setKeywords() }
			this.es.Keywords.Reset.e.onclick	= function() 
			{ 
				thisZ.es.Keywords.Area.e.value	= new String()
				return thisZ.setKeywords() 
			}
		}
		if (this.es.Settings)
			this.createDialog( this.es.Settings.e )
		if (this.es.Help)
		{
			this.createDialog( this.es.Help.e )
			if (this.es.HelpLine)
				this.es.HelpLine.e.onclick	= function() { return thisZ.runDialog( 'Help' ) }
		}
		
		// }
		//
		// Мониторы событий
		//
		// {
		
		document.onkeypress	= function(e) 
		{ 
			return thisZ.parseKeyboard( e ) 
		}
		
		this.iGet		= setInterval
		( 
			function() 
			{ 
				return thisZ.get() 
			},
			this.conf.xmlInterval
		)
		
		this.get() 
		return true
	}
	
	TV.prototype.displaySettings		= function()
	{
		this.es.Settings.onkeypress	= function(e) { e.stopPopogation(); return false; }
		for (var i=0; i<this.es.Settings.Form.e.elements.length; i++)
		{
			var input	= this.es.Settings.Form.e.elements[ i ]
			if (input.name in this.settings)
			{
				input.onchange	= input.onclick	= this.handlers.parseSetting
				switch (input.tagName.toLowerCase())
				{
					case 'input':
						switch (input.type)
						{
							case 'checkbox':
								input.checked	= this.settings[ input.name ]
								break
							case 'text':
								input.value		= this.settings[ input.name ]
								break
						}
						break
					case 'select':
						for (var s=0; s<input.options.length; s++)
							if (input.options[ s ].value == this.settings[ input.name ])
								input.options[ s ].selected	= true
						break
				}
			}
		}
		return true
	}
	
	TV.prototype.saveSettings		= function()
	{
		Client.set( this.settings )
		Client.set( 'settings', true )
		return true
	}
	
	TV.prototype.applySettings		= function()
	{
		document.body.className	= this.settings.language
		with (this.es)
		{	
			Debug.e.style.display			= this.settings.debug ? 'block' : 'none'
			Debug.e.parentNode.className	= this.settings.debug ? Debug.e.parentNode.className + ' debug' : Debug.e.parentNode.className.replace( / ?debug/g, '' )
			Baloon.UserPic.e.style.display	= this.settings.displayUserPics ? 'block' : 'none'
			HelpLine.e.style.display		= this.settings.displayHelp ? 'block' : 'none'
			Timer.e.style.display			= this.settings.displayTimer ? 'block' : 'none'
		}
		this.es.Baloon.e.className			= this.settings.displayUserPics ? this.es.Baloon.e.className + ' wUP' : this.es.Baloon.e.className.replace(/ ?wUP ?/g, '')
		this.saveSettings()
		return true
	}
	
	TV.prototype.parseKeyboard		= function( e )
	{
		var e 	= e || event
		var kc	= e.which || e.keyCode
		switch ( kc )
		{
			case 98:
			case 1080:
				
				return false
			case 102:
			case 1072:
				if ( e.altKey )
					this.list	= []
				return true
			case 32:
				this.list
				if (this.dialog)
					return false
				this.pausedUser	= !this.pausedUser
				this.pause( !this.paused )
				return false
				break
			//case 48:
			//	this.setKeywords( 'Users' )
			//	return false
			case 49:
			case 50:
			case 51:
			case 52:
			case 53:
			case 54:
			case 55:
			case 56:
			case 57:
				this.setKeywords( Math.abs( 48 - kc ) )
				return false
			case 61:
			case 43:
				this.speedUp()
				return false
			case 45:
				this.speedDown()
				return false
			case 27:
				if (this.dialog)
					this.runDialog( this.dialog )
				else
					if 
					(
						this.hoveredObj
						&&
						(
							this.paused
							||
							( !this.paused && this.settings.dynamicDescription )
						)
					)
						this.describe
						( 
							{ altKey: true },
							false,
							false
						)
				return false
			case 121:
			case 104:
			case 1088:
			case 1085:
				this.runDialog( 'Help' )
				return false
			case 223:
			case 115:
			case 1099:
				if (e.altKey)
				{
					this.settings	= this.cloneConfig( this.defaultSettings )
					this.displaySettings()
					this.applySettings()
					return false
				}
				this.runDialog( 'Settings' )
				return false
			case 116:
			case 1077:
				if ( e.altKey || e.ctrlKey || e.shiftKey || e.metaKey )
					return true
				this.settings.displayTimer	= !this.settings.displayTimer
				this.applySettings()
				return false
			case 100:
			case 1074:
				this.settings.debug	= !this.settings.debug
				this.displaySettings()
				this.applySettings()
				return false
		}
		return true
	}
	
	TV.prototype.runDialog			= function( name )
	{
		if (this.dialog && !this.es[ name ].e.active)
			return false
		this.es[ name ].e.active		= !this.es[ name ].e.active
		this.dialog 					= this.es[ name ].e.active ? name : false
		this.es[ name ].e.style.display	= this.es[ name ].e.active ? 'block' : 'none'
		this.pause( this.es[ name ].e.active )
		return true
	}
	
	TV.prototype.describe		= function( e, aObj, show )
	{
		if (this.dialog)
			return false
		e = e || event
		if (this.dialog)
			return false
		if (!aObj)
			aObj	= this.hoveredObj
		if (this.settings.dynamicDescription)
		{
			if (this.settings.dynamicPauseAll)
			{
				if (!(this.paused && this.pausedUser))
					this.pause( show )
			}
			else
			{
				if (this.settings.dynamicSlow && !this.paused)
					this.slow( show )
				if (show)
					aObj.animation.pause()
				else
				{
					if ( !(this.paused && !e.altKey) )
						aObj.animation.resume()
				}
			}
		}
		else
			if (!this.paused)
				return false
		
		if (show)
		{
			var now				= new Date()
			var top				= ( aObj.offsetTop > document.body.offsetHeight/2 )
			var interval		= aObj.item.interval + Math.floor( (now.getTime() - aObj.item.added) / 1000 ) 
			this.hoveredObj		= aObj
			with (this.es.Baloon)
			{
				User.e.firstChild.nodeValue	= aObj.item.user
				Time.e.firstChild.nodeValue	= Math.floor( interval / 60 ).leadingZero( 2 ) + ':' + Math.floor( interval - Math.floor(interval/60)*60 ).leadingZero( 2 )
				Text.e.firstChild.nodeValue	= aObj.item.post || ''
				e.style.left				= (aObj.offsetLeft < 10)
											? '50px'
											: (aObj.offsetLeft + e.offsetWidth > document.body.offsetWidth)
												? document.body.offsetWidth - e.offsetWidth - 10 + 'px'
												: aObj.offsetLeft + 'px'
				if (this.settings.displayUserPics)
				{
					with 
					(
						UserPic.e.appendChild
						( 
							document.createElement( 'img' )
						)
					)
					{
						src	= 'xml/lj-defaultuserpic.tve?' + aObj.item.user
					}
					UserPic.e.firstChild.onload	= this.handlers.fixImage
					e.style.height	= (e.offsetHeight < 95) ? '95px' : 'auto'
				}
				e.style.top					= (top)
											? aObj.offsetTop - e.offsetHeight + 'px'
											: aObj.offsetTop + aObj.offsetHeight + 'px'
				e.className	= (top) ? e.className + ' top' : e.className.replace(/ ?top/g, '')
				if (/*@cc_on 1+@*/0)
				{
					if (e.offsetWidth > 388)
						e.style.width	= '388px'
					if (e.offsetHeight > 388)
						e.style.height	= '388px'
					Right.e.style.height	= e.offsetHeight + 'px'
					Bottom.e.style.width	= e.offsetWidth + 'px'
				}
			}
		}
		else
		{
			this.hoveredObj					= false
			this.es.Baloon.e.style.top		= '-400px'
			this.es.Baloon.e.style.height	= 'auto'
			if (this.es.Baloon.UserPic.e.firstChild)
				this.es.Baloon.UserPic.e.removeChild( this.es.Baloon.UserPic.e.firstChild )
		}

		return true
	}
	
	TV.prototype.get		= function()
	{
		var thisZ	= this
		if (this.canGet && !this.paused)
		{
			this.canGet	= false
			new myXMLOverHTTP
			(
				{ 
					proceedor			: function( xml, json ) 
					{ 
						thisZ.canGet	= true
						try 
						{
							return thisZ.parse( eval( '(' + json + ')' ) )
						}
						catch (e)
						{
							false
						}
					},
					onResponseXMLError	: function() { thisZ.canGet	= true }
				}
			).get
			( 
				this.url
			)
		}
		return true
	}
	
	TV.prototype.setKeywords	= function( kw )
	{
		if ( kw !== undefined )
			with (this.es.Keywords)
			{
				e.index			= kw 
				e.className		= e.className.replace
								( 
									/ set[0-9a-zA-Z]+/, 
									(this.settings.singleColorFiler && kw != 'Users')
										? ' set1'
										: ' set'+e.index 
								)
				Area.e.value	= Client.get( 'kw' + e.index ) || new String()
				
			}	
		else
			Client.set
			( 
				'kw' + this.es.Keywords.e.index,
				(this.es.Keywords.e.index == 'Users')
					? this.es.Keywords.Area.e.value.replace( / *, *|\n/g, ',' )
					: this.es.Keywords.Area.e.value
			)
		this.runDialog( 'Keywords' )
		//if ( kw !== undefined )
		//	this.es.Keywords.Area.e.focus()
		return true
	}
	
	TV.prototype.parse		= function( jsonObj )
	{
		if (!jsonObj)
			return false
		for (var i=0; i<jsonObj.posts.length; i++)
		{
			this.list.push
			(
				{
					url			: jsonObj.posts[ i ].link,
					user		: jsonObj.posts[ i ].user,
					found		: jsonObj.posts[ i ].found,
					title		: jsonObj.posts[ i ].title,
					size		: jsonObj.posts[ i ].size,
					post		: jsonObj.posts[ i ].content,
					interval	: jsonObj.posts[ i ].interval,
					kind		: 'title',
					added		: new Date().getTime()
				}
			)
			this.I++
		}
		if (this.settings.dynamicLaunch)
			this.reLaunch()
		this.state()
		if (this.es.Wait)
		{
			this.es.Wait.e.parentNode.removeChild( this.es.Wait.e )
			this.es.Wait	= false
		}
		return true
	}
	
	TV.prototype.launch	= function()
	{
		var thisZ	= this
		if (!this.timer)
		{
			this.timer	= new Date().getTime()
			setInterval( this.handlers.timer, 1000 )
		}
		if (this.list.length && !this.paused && this.es.Flow.e.childNodes.length < this.settings.onScreen)
		{
			var item	= this.list.shift()
			var obj		= this.es.Flow.e.appendChild( document.createElement('a') )
			with (obj)
			{
				className	= 'i' + item.size
				setAttribute( 'href', item.url )
				setAttribute( 'target', '_blank' )
				switch (item.kind)
				{
					case 'title':
						if (this.settings.displayUserNames)
							with ( appendChild( document.createElement( 'em' ) ) )
								appendChild
								(
									document.createTextNode( item.user + ': ' )
								)
						appendChild
						(
							document.createTextNode( item.title )
						)
						break
				}
				style.top	= 5 + Math.floor( Math.random() * 80 ) + '%'
			}
			if (item.found >= 0)
				obj.className	+= ' kw' + ( this.settings.singleColorFiler ? 0 : item.found )
			obj.item		= item
			obj.animation	= new myAnimation
			(
				{
					obj				: obj,
					duration		: (item.size + 1) * this.settings.speed + Math.random() * this.settings.speed,
					motion			: 'linearTween',
					transformations	: 
					[
						{ 
							property	: 'left', 
							start		: document.body.offsetWidth, 
							end			: obj.offsetWidth * -1
						}
					]
				}
			)
			obj.animation.addEventListener
			(
				'complete',
				function( ani )
				{
					ani.obj.parentNode.removeChild( ani.obj )
					thisZ.state()
					return true
				}
			)
			obj.animation.originalDuration	= obj.animation.duration
			if (this.slowNow)
				obj.animation.duration	*= this.conf.slowFactor
			obj.animation.start()
			obj.onmouseover	= this.handlers.showDescription
			obj.onmouseout	= this.handlers.hideDescription
			this.state()
			return true
		}
		return false
	}
	
	TV.prototype.state	= function()
	{
		if (this.settings.debug)
			this.es.Debug.e.firstChild.nodeValue	= this.list.length + ' — ' + (this.es.Flow.e.childNodes.length) + ' (' + this.I + ') [' + (Math.ceil(this.settings.speed * 100) / 100) + ']'
		return true
	}
	
	TV.prototype.reLaunch	= function()
	{
		var thisZ		= this
		this.conf.launchInterval = Math.round(this.conf.xmlInterval*2/this.list.length)
		if (this.conf.launchInterval < 10)
			this.conf.launchInterval = 10
		if (this.conf.launchInterval > 1000)
			this.conf.launchInterval = 1000
		if (this.iLaunch)
			clearInterval( this.iLaunch )
		this.iLaunch	= setInterval
		( 
			function()
			{ 
				return thisZ.launch() 
			},
			this.conf.launchInterval
		)
		return true
	}
	
	TV.prototype.pause	= function( pause )
	{
		if (pause == this.paused)
			return false
		if (pause != undefined)
			this.paused	= pause
		else
			this.paused	= !this.paused
		var list	= this.getActive()
		for (var i=0; i<list.length; i++)
			list[ i ].animation[ this.paused ? 'pause' : 'resume' ]()
		if (!this.paused)
			this.es.Baloon.e.style.top	= '-400px'
		this.es.Pause.e.style.display	= pause ? 'block' : 'none'
		return this.paused
	}
	
	TV.prototype.slow			= function( slow )
	{
		var a
		var c
		if (this.busy)
			return false
		this.busy	= true
		if ( slow == this.slowNow )
			return true
		this.slowNow	= slow
		var list		= this.getActive()
		for (var i=0; i<list.length; i++)
		{
			a	= list[ i ].animation
			a.stop()
			a.duration	= (list[ i ].offsetLeft+list[ i ].offsetWidth)/document.body.offsetWidth * a.originalDuration * ( this.slowNow ? this.conf.slowFactor : 1 )
			a.transformations[0].start	= list[ i ].offsetLeft
			a.start()
			a.slow	= slow
		}
		this.busy	= false
		return slow
	}
	
	TV.prototype.setSpeed		= function( speed )
	{
		if (this.busy)
			return false
		this.busy			= true
		
		var list	= this.getActive()
		for (var i=0; i<list.length; i++)
		{
			if (!this.paused)
				list[ i ].animation.stop()
			list[ i ].animation.duration					= (list[ i ].offsetLeft+list[ i ].offsetWidth)/document.body.offsetWidth * list[ i ].animation.originalDuration * speed
			list[ i ].animation.transformations[0].start	= list[ i ].offsetLeft
			if (!this.paused)
				list[ i ].animation.start()
		}
		
		this.settings.speed	= speed
		this.applySettings()
		this.state()
		this.busy	= false
		return true
	}
	
	TV.prototype.speedUp	= function()
	{
		if (this.settings.speed > 0.1)
			return this.setSpeed( this.settings.speed - 0.05 )
		return false
	}
	
	TV.prototype.speedDown	= function()
	{
		return this.setSpeed( this.settings.speed + 0.05 )
	}
	
	TV.prototype.getActive	= function()
	{
		return this.es.Flow.e.childNodes
	}
	
	TV.prototype.createDialog	= function( obj )
	{
		obj.active		= false
		obj.onkepress	= this.handlers.jailEvent
		if ( false )
		{
			obj.animation	= new myAnimation
			(
				{
					obj				: obj,
					duration		: this.conf.sysAnimationDuration,
					motion			: this.conf.sysAnimationMotion,
					transformations	: 
					[
						{ 
							property	: 'opacity', 
							start		: 100, 
							end			: 0
						}
					]
				}
			)
			obj.animation.addEventListener
			(
				'complete',
				function( a )
				{
					if (a.direction)
						a.obj.style.display	= 'none'
				}
			)
			obj.animation.addEventListener
			(
				'start',
				function( a )
				{
					a.obj.style.display	= 'block'
				}
			)
		}
		return true
	}
	
	TV.prototype.cloneConfig	= function( srcConfig )
	{
		var config	= {}
		for (var i in srcConfig)
			config[ i ] = srcConfig[ i ]
		return config
	}
	
	
	