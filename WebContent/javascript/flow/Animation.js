
	//
	// Тело объекта анимации
	//
	// {

	function myAnimation( prms ) 
	{
		var thisA	= this
		
		// Входные параметры
		if (!prms)
			prms = {}
		
		// Дефолтные значения
		this.transformations 	= []
		this.duration 			= prms.duration * 1000	|| 1000
		this.speed 				= prms.speed 			|| 10
		this.unit 				= prms.unit 			|| 'px'
		this.motion 			= prms.motion 			|| false
		this.obj 				= prms.obj 				|| false
		this.running 			= false
		this.paused 			= false
		this.direction			= true
		this.i					= 0
			
		// Хендлеры функций
		this.handlers = 
		{
			start	: function() { return thisA.start(-1) },
			step	: function() { return thisA.step() },
			stop	: function() { return thisA.stop() },
			resume	: function() { return thisA.resume() }
		}
			
		// Трансформации, если заданы
		if (prms.transformations)
			for (var i=0; i<prms.transformations.length; i++)
				if (prms.transformations[i])
					this.setTransformation
					( 
						prms.transformations[i]
					)
			
		// Внутренние события
		this.events = 
		{
			start			: [ prms.onStart ],
			beforeStart		: [ prms.onBeforeStart ],
			complete		: [ prms.onComplete ],
			afterStep		: [],
			beforeStep		: [],
			beforeRender	: [],
			afterRender		: []
		}
		
		return this
	}
	
	myAnimation.prototype.reInterval = function() 
	{
		if (this.intervalObj)
		{
			clearInterval( this.intervalObj )
			this.intervalObj = setInterval( this.handlers.step, this.speed )
		}
		return true
	}
	
	myAnimation.prototype.setSpeed = function( speed ) 
	{
		this.speed	= speed
		return true
	}
	
	myAnimation.prototype.speedUp = function( d ) 
	{
		this.speed	+=	d || 1
		return true
	}
	
	myAnimation.prototype.speedDown = function( d ) 
	{
		if (this.speed > 1)
			this.speed	-=	d || 1
		return true
	}
		
	myAnimation.prototype.addEventListener = function( eventName, handler ) 
	{
		if (this.events[eventName]) 
		{
			this.events[eventName].push
			( 
				handler 
			)
			return true
		}
		return false
	}
		
	myAnimation.prototype.removeEventListener = function( eventName, handler ) 
	{
		if (this.events[eventName]) 
		{
			for (var i=0; i<this.events[eventName].length; i++) 
			{
				if (this.events[eventName][i] == handler) 
				{
					this.events[eventName][i] = false
					return true
				}
			}
		}
		return false
	}
		
	myAnimation.prototype.clearEvents = function( eventName )
	{
		this.events[ eventName ] = []
		return true
	}

	myAnimation.prototype.runEvent = function( eventName ) 
	{
		for (var i=0; i<this.events[eventName].length; i++)
			if (this.events[eventName][i])
				this.events[eventName][i](this)
		return true
	}

	myAnimation.prototype.start = function( delay ) 
	{
		// Если анимация уже запущена
		if ( delay != -1 && this.isRunning() && !this.isPaused() )
			return false
		
		this.runEvent( 'beforeStart' )
		this.running = true
		
		if ( !delay || delay == -1 ) 
		{
			this.startDelay = false
			this.time 		= 0
			this.i			= 0
			for (var i=0; i<this.transformations.length; i++) 
			{
				if (!this.transformations[i].start && this.transformations[i].start != 0)
					this.transformations[i].start = this.getIntegerStyleProperty( this.transformations[i].property )
				this.transformations[i].lI	= 0
			}
			this.intervalObj = setInterval( this.handlers.step, this.speed )
			this.runEvent( 'start' )
			return true
		} else 
			this.startDelay = setTimeout( this.handlers.start, delay )

		return true
	}
		
	myAnimation.prototype.clearStartDelay = function() 
	{ 
		if (this.startDelay) 
		{
			clearTimeout( this.startDelay ) 
			this.startDelay = false
			this.running = false
			return true
		}
		return false
	}
		
	myAnimation.prototype.pause = function( delay ) 
	{ 
		return this.stop( delay ) 
	}
	myAnimation.prototype.stop = function( delay ) 
	{
		if (this.isRunning()) 
		{
			if (delay)
				setTimeout( this.handlers.stop, delay )
			else 
			{
				this.paused = true
				clearInterval( this.intervalObj )
				return true
			}
		} else
			return false
	}
		
	myAnimation.prototype.resume = function( delay ) 
	{
		if (this.isRunning()) 
		{
			if (delay)
				setTimeout( this.handlers.resume, delay )
			else 
			{
				if (this.intervalObj)
					clearInterval( this.intervalObj )
				this.intervalObj = setInterval( this.handlers.step, this.speed )
				this.paused = false
			}
		} else
			return false
		return true
	}
		
	myAnimation.prototype.reset = function() 
	{
		this.paused		= false
		this.running	= false
		return true
	}
	
	myAnimation.prototype.reverse = function() 
	{
		this.direction	= !this.direction
		if (!this.running)
			this.start()
		return true
	}
		
	myAnimation.prototype.step = function() 
	{
		switch (this.direction)
		{
		
			case true:
				if (this.i < this.duration) 
					this.i	+= this.speed
				else
					return this.complete()
				break
				
			case false:
				if (this.i > 0) 
					this.i	-= this.speed
				else
					return this.complete()
				break
		
		}
		this.render()
		return true
	}
	
	myAnimation.prototype.complete = function() 
	{
		this.stop()
		this.paused 	= false
		this.running 	= false
		for (var i=0; i<this.transformations.length; i++)
			this.setIntegerStyleProperty
			( 
				this.transformations[ i ].property, 
				this.transformations[ i ][ this.direction ? 'end' : 'start' ]
			)	
		this.runEvent( 'complete' )
		return true
	}
	
	myAnimation.prototype.render = function() 
	{
		this.runEvent( 'beforeRender' )
		for (var i=0; i<this.transformations.length; i++)
			this.setIntegerStyleProperty
			( 
				this.transformations[ i ].property,
				Math[ this.transformations[ i ].motion || this.motion ]
				( 
					this.i,
					this.transformations[ i ].start, 
					this.transformations[ i ].end - this.transformations[ i ].start, 
					this.duration
				)
			)
		this.runEvent( 'afterRender' )
	}
		
	myAnimation.prototype.getIntegerStyleProperty = function( stylePropertyName ) 
	{ 
		return parseInt( this.obj.style[ stylePropertyName ] ) 
	}
	
	myAnimation.prototype.setIntegerStyleProperty = function( stylePropertyName, value ) 
	{ 
		if (/color|Color/.test(stylePropertyName))
			this.obj.style[ stylePropertyName ] = 'rgb('+parseInt(value)+','+parseInt(value)+','+parseInt(value)+')'
		else 
		if (stylePropertyName == 'opacity')
		{
			this.obj.style[ stylePropertyName ] = parseInt(value) / 100
			this.obj.style.filter = 'alpha(opacity=' + parseInt(value) + ')'
		}
		else
			this.obj.style[ stylePropertyName ] = Math.round( value ) + this.unit 
	}
	
	myAnimation.prototype.setTransformation	= function( tranformationObj ) 
	{ 
		return this.transformations.push( tranformationObj ) 
	}
	
	myAnimation.prototype.isRunning			= function() { return this.running }
	myAnimation.prototype.isPaused			= function() { return this.paused }
	
	// }
	//
	// Методы анимации (расширения для объекта Math)
	//
	// {
	
	Math.linear = function(t, b, c, d) { return c*t/d }
	Math.linearTween = function(t, b, c, d) { return c*t/d + b }
	Math.easeInQuad = function(t, b, c, d) { return c*(t/=d)*t + b }
	Math.easeOutQuad = function(t, b, c, d) { return -c *(t/=d)*(t-2) + b }
	Math.easeInOutQuad = function (t, b, c, d) { return ((t/=d/2) < 1) ? c/2*t*t + b : -c/2 * ((--t)*(t-2) - 1) + b }
	Math.easeInCubic = function (t, b, c, d) { return c*(t/=d)*t*t + b }
	Math.easeOutCubic = function (t, b, c, d) { return c*((t=t/d-1)*t*t + 1) + b }
	Math.easeInOutCubic = function (t, b, c, d) { return ((t/=d/2) < 1) ? c/2*t*t*t + b : c/2*((t-=2)*t*t + 2) + b }
	Math.easeInQuart = function (t, b, c, d) { return c*(t/=d)*t*t*t + b }
	Math.easeOutQuart = function (t, b, c, d) { return -c * ((t=t/d-1)*t*t*t - 1) + b }
	Math.easeInOutQuart = function (t, b, c, d) { return ((t/=d/2) < 1) ? c/2*t*t*t*t + b : -c/2 * ((t-=2)*t*t*t - 2) + b }
	Math.easeInQuint = function (t, b, c, d) { return c*(t/=d)*t*t*t*t + b }
	Math.easeOutQuint = function (t, b, c, d) { return c*((t=t/d-1)*t*t*t*t + 1) + b }
	Math.easeInOutQuint = function (t, b, c, d) { return ((t/=d/2) < 1) ? c/2*t*t*t*t*t + b : c/2*((t-=2)*t*t*t*t + 2) + b }
	Math.easeInSine = function (t, b, c, d) { return -c * Math.cos(t/d * (Math.PI/2)) + c + b }
	Math.easeOutSine = function (t, b, c, d) { return c * Math.sin(t/d * (Math.PI/2)) + b }
	Math.easeInOutSine = function (t, b, c, d) { return -c/2 * (Math.cos(Math.PI*t/d) - 1) + b }
	Math.easeInExpo = function (t, b, c, d) { return (t==0) ? b : c * Math.pow(2, 10 * (t/d - 1)) + b }
	Math.easeOutExpo = function (t, b, c, d) { return (t==d) ? b+c : c * (-Math.pow(2, -10 * t/d) + 1) + b }
	Math.easeInCirc = function (t, b, c, d) { return -c * (Math.sqrt(1 - (t/=d)*t) - 1) + b }
	Math.easeOutCirc = function (t, b, c, d) { return c * Math.sqrt(1 - (t=t/d-1)*t) + b }
	Math.easeInOutCirc = function (t, b, c, d) { return ((t/=d/2) < 1) ? -c/2 * (Math.sqrt(1 - t*t) - 1) + b : c/2 * (Math.sqrt(1 - (t-=2)*t) + 1) + b }
	Math.easeInBounce = function (t, b, c, d) { return c - Math.easeOutBounce (d-t, 0, c, d) + b }
	
	Math.easeInOutExpo = function (t, b, c, d) { 
		if (t==0) return b
		if (t==d) return b+c
		if ((t/=d/2) < 1) return c/2 * Math.pow(2, 10 * (t - 1)) + b
		return c/2 * (-Math.pow(2, -10 * --t) + 2) + b
		}

	Math.easeInElastic = function (t, b, c, d, a, p) {
		if (t==0) return b
		if ((t/=d)==1) return b+c
		if (!p) p=d*.3
		if (a < Math.abs(c)) {
			var s = p/4
			a = c
			} else 
				var s = p/(2*Math.PI) * Math.asin (c/a)
		return -(a*Math.pow(2,10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )) + b
		}

	Math.easeOutElastic = function (t, b, c, d, a, p) {
		if (t==0) 
			return b
		if ((t/=d)==1) 
			return b + c
		if (!p) p = d*.3
		if (a < Math.abs(c)) { 
			var s = p/4
			a = c
			} else 
				var s = p/(2*Math.PI) * Math.asin (c/a)
		return a*Math.pow(2,-10*t) * Math.sin( (t*d-s)*(2*Math.PI)/p ) + c + b
		}

	Math.easeInOutElastic = function (t, b, c, d, a, p) {
		if (t==0) 
			return b
		if ((t/=d/2)==2) 
			return b + c
		if (!p) p = d*(.3*1.5)
		if (a < Math.abs(c)) { 
			a = c
			var s = p/4
			} else 
				var s = p/(2*Math.PI) * Math.asin (c/a);
		if (t < 1) 
			return -.5*(a*Math.pow(2,10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )) + b
			else
			return a*Math.pow(2,-10*(t-=1)) * Math.sin( (t*d-s)*(2*Math.PI)/p )*.5 + c + b
		}

	Math.easeInBack = function (t, b, c, d, s) {
		if (s == undefined) 
			s = 1.70158
		return c*(t/=d)*t*((s+1)*t - s) + b
		}

	Math.easeOutBack = function (t, b, c, d, s) {
		if (s == undefined) 
			s = 1.70158
		return c*((t=t/d-1)*t*((s+1)*t + s) + 1) + b
		}

	Math.easeInOutBack = function (t, b, c, d, s) {
		if (s == undefined) 
			s = 1.70158; 
		if ((t/=d/2) < 1) 
			return c/2*(t*t*(((s*=(1.525))+1)*t - s)) + b
			else
			return c/2*((t-=2)*t*(((s*=(1.525))+1)*t + s) + 2) + b
		}

	Math.easeOutBounce = function (t, b, c, d) {
		if ((t/=d) < (1/2.75))
			return c*(7.5625*t*t) + b
			else if (t < (2/2.75))
			return c*(7.5625*(t-=(1.5/2.75))*t + .75) + b
			else if (t < (2.5/2.75))
			return c*(7.5625*(t-=(2.25/2.75))*t + .9375) + b
			else
			return c*(7.5625*(t-=(2.625/2.75))*t + .984375) + b
		}

	Math.easeInOutBounce = function (t, b, c, d) {
		if (t < d/2) 
			return Math.easeInBounce (t*2, 0, c, d) * .5 + b
			else
			return Math.easeOutBounce (t*2-d, 0, c, d) * .5 + c*.5 + b
		}

	// }