var NullEventHandler = function() {
	return( function( e ) {
		if( e ) { 
			e.cancelBubble = true; 
		}
			return false;
	} );
};