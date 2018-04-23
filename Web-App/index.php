<!-- START SIGMA IMPORTS -->
<script src="src/sigma.core.js"></script>
<script src="src/conrad.js"></script>
<script src="src/utils/sigma.utils.js"></script>
<script src="src/utils/sigma.polyfills.js"></script>
<script src="src/sigma.settings.js"></script>
<script src="src/classes/sigma.classes.dispatcher.js"></script>
<script src="src/classes/sigma.classes.configurable.js"></script>
<script src="src/classes/sigma.classes.graph.js"></script>
<script src="src/classes/sigma.classes.camera.js"></script>
<script src="src/classes/sigma.classes.quad.js"></script>
<script src="src/classes/sigma.classes.edgequad.js"></script>
<script src="src/captors/sigma.captors.mouse.js"></script>
<script src="src/captors/sigma.captors.touch.js"></script>
<script src="src/renderers/sigma.renderers.canvas.js"></script>
<script src="src/renderers/sigma.renderers.webgl.js"></script>
<script src="src/renderers/sigma.renderers.svg.js"></script>
<script src="src/renderers/sigma.renderers.def.js"></script>
<script src="src/renderers/webgl/sigma.webgl.nodes.def.js"></script>
<script src="src/renderers/webgl/sigma.webgl.nodes.fast.js"></script>
<script src="src/renderers/webgl/sigma.webgl.edges.def.js"></script>
<script src="src/renderers/webgl/sigma.webgl.edges.fast.js"></script>
<script src="src/renderers/webgl/sigma.webgl.edges.arrow.js"></script>
<script src="src/renderers/canvas/sigma.canvas.labels.def.js"></script>
<script src="src/renderers/canvas/sigma.canvas.hovers.def.js"></script>
<script src="src/renderers/canvas/sigma.canvas.nodes.def.js"></script>
<script src="src/renderers/canvas/sigma.canvas.edges.def.js"></script>
<script src="src/renderers/canvas/sigma.canvas.edges.curve.js"></script>
<script src="src/renderers/canvas/sigma.canvas.edges.arrow.js"></script>
<script src="src/renderers/canvas/sigma.canvas.edges.curvedArrow.js"></script>
<script src="src/renderers/canvas/sigma.canvas.edgehovers.def.js"></script>
<script src="src/renderers/canvas/sigma.canvas.edgehovers.curve.js"></script>
<script src="src/renderers/canvas/sigma.canvas.edgehovers.arrow.js"></script>
<script src="src/renderers/canvas/sigma.canvas.edgehovers.curvedArrow.js"></script>
<script src="src/renderers/canvas/sigma.canvas.extremities.def.js"></script>
<script src="src/renderers/svg/sigma.svg.utils.js"></script>
<script src="src/renderers/svg/sigma.svg.nodes.def.js"></script>
<script src="src/renderers/svg/sigma.svg.edges.def.js"></script>
<script src="src/renderers/svg/sigma.svg.edges.curve.js"></script>
<script src="src/renderers/svg/sigma.svg.labels.def.js"></script>
<script src="src/renderers/svg/sigma.svg.hovers.def.js"></script>
<script src="src/middlewares/sigma.middlewares.rescale.js"></script>
<script src="src/middlewares/sigma.middlewares.copy.js"></script>
<script src="src/misc/sigma.misc.animation.js"></script>
<script src="src/misc/sigma.misc.bindEvents.js"></script>
<script src="src/misc/sigma.misc.bindDOMEvents.js"></script>
<script src="src/misc/sigma.misc.drawHovers.js"></script>
<script src="../sigma.parsers.json.js"></script>
<script src="../sigma.plugins.dragNodes.js"></script>
<!-- END SIGMA IMPORTS -->
<script src="plugins/sigma.renderers.edgeLabels/settings.js"></script>
<script src="plugins/sigma.renderers.edgeLabels/sigma.canvas.edges.labels.def.js"></script>
<script src="plugins/sigma.renderers.edgeLabels/sigma.canvas.edges.labels.curve.js"></script>
<script src="plugins/sigma.renderers.edgeLabels/sigma.canvas.edges.labels.curvedArrow.js"></script>
<script src="plugins/sigma.plugins.neighborhoods/sigma.plugins.neighborhoods.js"></script>
<script src="plugins/sigma.layout.forceAtlas2/supervisor.js"></script>
<script src="plugins/sigma.layout.forceAtlas2/worker.js"></script>
<script src="lib/jquery-2.1.1.min.js"></script>

<div id="container">
<style>
        #graph-container {
            
            top: 0;
            bottom: 0;
            left: 0;
            right: 0;
            position: absolute;
            background-color: #455660;
            margin-top: 5%;
        }

        .sigma-edge {
            stroke: #14191C;
        }

        .sigma-node {
            fill: green;
            stroke: #14191C;
            stroke-width: 2px;
        }

        .sigma-node:hover {
            fill: blue;
        }

        .muted {
            fill-opacity: 0.1;
            stroke-opacity: 0.1;
        }
       
        #graph{
            
            
        }
        
    </style>
<div id="graph">
      <p>Enter file name to load</p>
      <input type="text" name="fileName" id="fName"><button id="btn">Load file</button>
   </div>
    
    <div id="graph-container"></div>
</div>
<script>
    /**
     * This is a basic example of how one could spawn a freestyle svg renderer
     * to achieve his/her goal through css and jQuery to display fancy graphs
     * but somewhat less performant.
     */
    var i,
        s,
        N = 100,
        E = 500,
        g = {
            nodes: [],
            edges: []
        };



    // Instantiate sigma:
    s = new sigma({
        graph: g,
        settings: {
            enableHovering: false
        }
    });

    s.addRenderer({
        id: 'main',
        type: 'svg',
        container: document.getElementById('graph-container'),
        freeStyle: true
    });

    sigma.parsers.json('../graph1.json', s, function() {
        // create local variables
        var i,
            nodes = s.graph.nodes(),
            len = nodes.length,
            edges = s.graph.edges();
        // loop over the node array
        for (i = 0; i < len; i++) {
            // give  each node a random x, y position

            nodes[i].x = Math.random();
            nodes[i].y = Math.random();
            // give it node the same size
            nodes[i].size = 4;
            nodes[i].color = '#666'

        }
        // loop over the edges array
        for (j = 0; j < edges.length; j++) {
            // gjve the edges a color and a hover color and sjze
            edges[j].color = '#CCC';
            edges[j].hover_color = '#000';
            edges[j].size = 1;
            edges[j].label = "Edge"+(j+1);
            if(edges[j].source==edges[j].target)
             {
                edges[j].type='curve';
             }

        }
        $( "#btn" ).click(function() {
      
      // using sigma to parse in the json file
      
      var file =$('#fName').val()
      
      $('#graph-container').remove(); 
      $('#graph').append('<div id="graph-container"></div>'); 
       // Instantiate sigma:
    s = new sigma({
        graph: g,
        settings: {
            enableHovering: false
        }
    });

    s.addRenderer({
        id: 'main',
        type: 'svg',
        container: document.getElementById('graph-container'),
        freeStyle: true
    });

    sigma.parsers.json('../'+file+'.json', s, function() {
        // create local variables
        var i,
            nodes = s.graph.nodes(),
            len = nodes.length,
            edges = s.graph.edges();
        // loop over the node array
        for (i = 0; i < len; i++) {
            // give  each node a random x, y position

            nodes[i].x = Math.random();
            nodes[i].y = Math.random();
            // give it node the same size
            nodes[i].size = 4;
            nodes[i].color = '#666'

        }
        // loop over the edges array
        for (i = 0; i < edges.length; i++) {
            // give the edges a color and a hover color and size
            edges[i].color = '#CCC';
            edges[i].hover_color = '#000';
            edges[i].size = 2;
            edges[i].label = 'Edge '+(i+1);
            if(edges[i].source==edges[i].target)
             {
                edges[i].type='curve';
             }
        }
        s.refresh();
        s.refresh();
        s.startForceAtlas2();
          setTimeout(function(){  s.killForceAtlas2(); }, 2000);
       
        $('.sigma-node').click(function() {
            
            // Muting
            $('.sigma-node, .sigma-edge').each(function() {
                mute(this);
            });

            // Unmuting neighbors
            var neighbors = s.graph.neighborhood($(this).attr('data-node-id'));
            neighbors.nodes.forEach(function(node) {
                unmute($('[data-node-id="' + node.id + '"]')[0]);
            });

            neighbors.edges.forEach(function(edge) {
                unmute($('[data-edge-id="' + edge.id + '"]')[0]);
            });
        });
        // Binding silly interactions
        function mute(node) {
            if (!~node.getAttribute('class').search(/muted/))
                node.setAttributeNS(null, 'class', node.getAttribute('class') + ' muted');
        }

        function unmute(node) {
            node.setAttributeNS(null, 'class', node.getAttribute('class').replace(/(\s|^)muted(\s|$)/g, '$2'));
        }



        s.bind('clickStage', function() {
            $('.sigma-node, .sigma-edge').each(function() {
                unmute(this);
            });
        });
        });
        });
        s.refresh();
        s.startForceAtlas2();
          setTimeout(function(){  s.killForceAtlas2(); }, 2000);
       
        $('.sigma-node').click(function() {
            
            // Muting
            $('.sigma-node, .sigma-edge').each(function() {
                mute(this);
            });

            // Unmuting neighbors
            var neighbors = s.graph.neighborhood($(this).attr('data-node-id'));
            neighbors.nodes.forEach(function(node) {
                unmute($('[data-node-id="' + node.id + '"]')[0]);
            });

            neighbors.edges.forEach(function(edge) {
                unmute($('[data-edge-id="' + edge.id + '"]')[0]);
            });
        });
        // Binding silly interactions
        function mute(node) {
            if (!~node.getAttribute('class').search(/muted/))
                node.setAttributeNS(null, 'class', node.getAttribute('class') + ' muted');
        }

        function unmute(node) {
            node.setAttributeNS(null, 'class', node.getAttribute('class').replace(/(\s|^)muted(\s|$)/g, '$2'));
        }
      
        s.bind('clickStage', function() {
            $('.sigma-node, .sigma-edge').each(function() {
                unmute(this);
            });
        });
    });
</script>