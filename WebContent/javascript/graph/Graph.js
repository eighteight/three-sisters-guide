   function Graph(){
  			var loader = null;
		    this.onAjaxComplete = function (xml){
		    	var xmlDoc = null;
		    	if (window.ActiveXObject) {
  				    xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
                    xmlDoc.async="false";
                    xmlDoc.loadXML(xml);
                } // code for Mozilla, Firefox, Opera, etc.
                else {
  					var parser=new DOMParser();
  					xmlDoc=parser.parseFromString(xml,"text/xml");
  				}
		    	loader.handle(xmlDoc);
		    	this.rootNode = loader.rootNode;
		    }

		    this.clear = function(){
		    	layout.clear();
		    }
			this.init = function init(g, m, gt, mt) {
				var graph = g;
				var matrix = m;
				var matrixTitle = mt;
				var graphTitle = gt;
				
				layout = new ForceDirectedLayout(graph, true );
				
				layout.view.skewBase=300;
				layout.setSize();
				layout.config._default = {				
					model: function( dataNode ) {
						return {
							mass: .5
						}
					},
					view: function( dataNode, modelNode ) {
						//layout.svg;
						if (false ) {
							var nodeContainer = document.createElementNS("http://www.w3.org/2000/svg", "text");
							nodeContainer.setAttribute('stroke', '#888888');
							nodeContainer.setAttribute('stroke-width', '.25px');
							nodeContainer.setAttribute('fill', dataNode.color);
							nodeContainer.setAttribute('r', 6 + 'px');
							
							setNodeElement(nodeContainer,dataNode, modelNode);
							
//							nodeContainer.onmousedown =  new EventHandler( layout, layout.handleMouseDownEvent, modelNode.id )
//							if (dataNode.text)	{
//								nodeContainer.innerHTML = dataNode.text;
//								nodeContainer.onclick = new EventHandler( null, clickHandler, layout, dataNode, modelNode);
//								nodeContainer.oncontextmenu = new EventHandler( null, doubleClickHandler, dataNode, modelNode);
//							}
							return nodeContainer;
						} else {
							var nodeContainer = document.createElement( 'div' );
							nodeContainer.style.position = "absolute";

							var color = dataNode.color.replace( "#", "" );
							setNodeElement(nodeContainer,dataNode, modelNode);
							return nodeContainer;
						}
					}
				}
				
				function setNodeElement(nodeContainer,dataNode, modelNode ){
					var contentLink = document.createElement("div");
						nodeContainer.appendChild(contentLink);
						
					setContentLinkClass(contentLink, dataNode);

					if (dataNode.text)	{
					    var nodeElement = document.createElement("div");
					    nodeElement.className = "theatreNodeTextDiv";
					    var textNode = document.createTextNode(""+dataNode.text );
					    nodeElement.appendChild(textNode);
						nodeContainer.appendChild(nodeElement);
						nodeContainer.onmouseover = new EventHandler(layout, mouseOverHandler, dataNode, modelNode, nodeContainer);
						nodeContainer.onmouseout = new EventHandler(layout, mouseOutHandler, dataNode, modelNode, nodeContainer);
						if (dataNode.fontSize) {
							nodeElement.style.fontSize = dataNode.fontSize;
						}
					}
					if (dataNode.image){
						nodeContainer.oncontextmenu = new EventHandler( null, imageHandler, dataNode, modelNode, nodeContainer);
						if (!dataNode.target){
							nodeContainer.onclick = new EventHandler( null, imageHandler, dataNode, modelNode, nodeContainer);
						}
						
					}
					if (dataNode.video){
						nodeContainer.oncontextmenu = new EventHandler( null, videoHandler, dataNode, modelNode, nodeContainer);
						if (!dataNode.target){
							nodeContainer.onclick = new EventHandler( null, videoHandler, dataNode, modelNode, nodeContainer);
						}
					}
					if (dataNode.webObject){
						nodeContainer.oncontextmenu = new EventHandler( null, webObjectHandler, dataNode, modelNode, nodeContainer);
						if (!dataNode.target){
							nodeContainer.onclick = new EventHandler( null, webObjectHandler, dataNode, modelNode, nodeContainer);
						}
					}

					if (dataNode.target) nodeContainer.onclick = new EventHandler( null, clickHandler, layout, dataNode, modelNode, nodeContainer);

					nodeContainer.onmousedown =  new EventHandler( layout, layout.handleMouseDownEvent, modelNode.id );
					nodeContainer.className = dataNode.root? "theatreNodeRoot":"theatreNode";
					
					//nodeContainer.className = dataNode.xhtml? "theatreNodeLink":nodeContainer.className;
				    
				    //addClass(nodeContainer, 'infocus');
					
					//if (isInVisitedTargets(dataNode)) addClass(nodeContainer, 'visitedNode');	
				}
				
				function setContentLinkClass(contentLink, dataNode){
					if (dataNode.target){
						if ((dataNode.image || dataNode.video)) {
							contentLink.className = "theatreNodeLinkContentAndTarget";
							return;
						} else {
							contentLink.className = "theatreNodeLinkTarget";
							return;
						}
					} else {
						if ((dataNode.image || dataNode.video)){
							contentLink.className = "theatreNodeLinkContent";
							return;
						} else {
							contentLink.className = "theatreNodeLink";
						}
					}
				}

				function mouseOverHandler(dataNode, modelNode, nodeContainer){
					addClass(nodeContainer, 'infocus');
				}
				function getRestLength(){
					return this.graph.rootNode ? this.graph.rootNode.edgeLength:40;
				}

				function mouseOutHandler(dataNode, modelNode, nodeContainer){
					removeClass(nodeContainer, 'infocus');
				}
				
				function addClass(nodeContainer, className){
					var cn = nodeContainer.className;
					nodeContainer.className = cn + ' ' + className;	
				}

				function removeClass (nodeContainer, className){
					var cn = nodeContainer.className;
					nodeContainer.className = cn.replace(' ' + className, '');	
				}
				
				function doubleClickHandler (dataNode, modelNode){
					//addClass(nodeContainer, 'visitedNode');
					matrix.innerHTML = "";
					if (matrixTitle == null) {
						matrixTitle = document.createElement("div");
						matrixTitle.className = 'matrixTitle';
						matrixTitle.id = 'matrixTitle';
						matrix.appendChild(matrixTitle);
					}
					matrixTitle.innerHTML = dataNode.text;
					
					var content = document.createElement("div");
					
					var innerhtml = dataNode.xhtml.replaceAll(")", ">");
					var innerhtml2 = innerhtml.replaceAll("(","<");
					var innerhtml3 = innerhtml2.replaceAll("+","&");
					content.innerHTML = innerhtml3;
					matrix.innerHTML = innerhtml3;
					scroll(-50,0);
					return true;
				}

        		layout.forces.spring._default = function( nodeA, nodeB, isParentChild ) {
					if (isParentChild) {
						return {
							springConstant: 0.5,
							dampingConstant: 0.2,
							restLength: getRestLength()
						}
					} else {
						return {
							springConstant: 0.2,
							dampingConstant: 0.2,
							restLength: getRestLength()
						}
					}
				}
				layout.forces.spring['A'] = {};
        		layout.forces.spring['A']['B'] = function( nodeA, nodeB, isParentChild ) {
					return {
						springConstant: 0.4,
						dampingConstant: 0.2,
						restLength: 20
					}
				}
        		layout.forces.magnet = function() {
					return {
						magnetConstant: -2000,
						minimumDistance: 10
					}
				}
				
				layout.viewEdgeBuilder = function( dataNodeSrc, dataNodeDest ) {
					if ( this.svg ) {
						return {
							'stroke': dataNodeSrc.color,
							'stroke-width': '2px',
							'stroke-dasharray': '2,4'
						}
					} else {
						return {
							'pixelColor': '#000000',//dataNodeSrc.color,
							'pixelWidth': '4px',
							'pixelHeight': '4px',
							'pixels': 6
						}
					}
				}

				loader = new XMLTreeLoader( layout.dataGraph );
				var loadListener = new Object();
				loadListener.notify = function(rootNode){
					graphTitle.innerHTML = rootNode.text;
				}
				loader.subscribe(loadListener);
				var buildTimer = new Timer( 150 );
				buildTimer.subscribe( layout );
				buildTimer.start();
			}
		}