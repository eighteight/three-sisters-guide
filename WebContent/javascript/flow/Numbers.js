
	Number.prototype.toHuman		= function( withoutSign )
	{
		var digits	= Math.abs(this).toString().split('').reverse()
		var humaned	= []
		
		for (var i=0; i<digits.length; i++)
		{
			if (i && i%3 == 0)
				humaned.push(' ')
			humaned.push( digits[ i ] )
		}
		
		if (!withoutSign)
			humaned.push
			(
				(this >= 0)
					? '+ '
					: '– '
			)
		
		return humaned.reverse().join('')
	}
	
	Number.prototype.leadingZero	= function( number )
	{
		if (this.digits() < number)
		{
			var lz	= this.toString()
			for (var i=0; i<number-this.digits(); i++)
				lz	= '0' + lz
			return lz
		}
		return this.toString()
	}
	
	Number.prototype.digits			= function()
	{
		return Math.abs( this ).toString().length
	}
	
	Number.prototype.integerPart	= function()
	{
		return (this < 0)
			? Math.ceil( this )
			: Math.floor( this )
	}
	
	Number.prototype.floatPart		= function( pow )
	{
		if (!pow)
			pow = 2
		return Math.floor
		(
			(
				Math.abs(this) - Math.floor( Math.abs(this) )
			) * Math.pow(10, pow)
		)
	}
	
	Number.prototype._toString		= Number.prototype.toString
	Number.prototype.toString		= function( pow )
	{
		var str	= this._toString()
		if (str.length < pow)
			for (var i=0; i<pow-str.length; i++)
				str	= '0' + str
		return str
	}
	
	
	
	
