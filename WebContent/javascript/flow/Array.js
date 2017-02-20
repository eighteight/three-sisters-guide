
	Array.prototype.sortAscending	= function()
	{
		for (var i=0; i<this.length; i++)
			for (j=0; j<this.length-1; j++)
				if (this[j] > this[j+1])
				{
					var tmp		= this[ j ]
					this[ j ]	= this[ j+1 ]
					this[ j+1 ]	= tmp
				}
		return this
	}
	
	Array.prototype.parseInt		= function()
	{
		for (var i=0; i<this.length; i++)
			this[ i ]	= (parseInt( this[ i ] ) == NaN) 
				? 0
				: parseInt( this[ i ] )
		return this
	}
	
	Array.prototype.parseFloat		= function()
	{
		for (var i=0; i<this.length; i++)
			this[ i ]	= parseInt( this[ i ] )
		return this
	}
	
	Array.prototype.remove			= function( i )
	{
		return this.splice( i, 1 )
	}
	
	Array.prototype.last			= function()
	{
		return this[ this.length-1 ]
	}
