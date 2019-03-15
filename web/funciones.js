    function generate(){
        var padre=document.getElementById('carac');
        if(padre !== null)
            while (padre.hasChildNodes())
                padre.removeChild(padre.lastChild);
        var tipo=document.getElementById('tipo').selectedIndex;
        if(tipo === 0)
            padre.innerHTML = '<label for="x1">x1:</label><input type="text" id="x1" name="x1"/>'+
                              '<label for="y1">y1:</label><input type="text" id="y1" name="x2"/>'+
                              '<label for="x2">x2:</label><input type="text" id="x2" name="x3"/>'+
                              '<label for="y2">y2:</label><input type="text" id="y2" name="x4"/>';
        else if(tipo === 1)
            padre.innerHTML = '<label for="x">x:</label><input type="text" id="x" name="x"/>'+
                              '<label for="y">y:</label><input type="text" id="y" name="y"/>'+
                              '<label for="radio">radio:</label><input type="text" id="radio" name="radio" />';
        else if(tipo === 2)
            padre.innerHTML = '<label for="eje_1">Eje 1</label><input type="text" id="eje_1" name="eje_1"/>'+
                              '<label for="eje_2">Eje2</label><input type="text" id="eje_2" name="eje_2"/>'+
                              '<label for="x">Centro en x</label><input type="text" id="x" name="x"/>'+
                              '<label for="y">Centro en y</label><input type="text" id="y" name="y"/>';
        else if(tipo === 3)
            padre.innerHTML = '<label for="eje_1">Eje transverso:</label><input type="text" id="eje_1" name="eje_1"/>'+
                              '<label for="eje_2">Eje conjugado:</label><input type="text" id="eje_2" name="eje_2"/>'+
                              '<label for="px">centro en x:</label><input type="text" id="x" name="x"/>'+
                              '<label for="py">centro en y:</label><input type="text" id="y" name="y"/>'+
                              '<label for="eje_focal">Eje focal paralelo a...</label>'+
                              '<select id="eje_focal" name="eje_focal"/>'+
                              '<option value="x">eje x</option>'+
                              '<option value="y">eje y</option>'+
                              '</select>';
        else
            alert("No entra en ninguna opcion :(");

}
function choose(){
    var tipo=document.getElementById('tipo').selectedIndex;
    switch(tipo){
        case 0:
            drawL();
            break;
        case 1:
            drawC();
            break;
        case 2:
            drawE();
            break;
        case 3:
            drawH();
            break;
    }
}
function drawL() {
    var div = document.getElementById("plot-id");
    var x1=parseInt(document.getElementById('x1').value);
    var y1=parseInt(document.getElementById('x2').value);
    var x2=parseInt(document.getElementById('y1').value);
    var y2=parseInt(document.getElementById('y2').value);
    var a=y2-y1;
    var b=x1-x2;
    var c=y1*x2-x1*y2;
    div.textContent=a+"x+"+b+"y+"+c;
    var text = div.textContent;
    try {
        functionPlot({
          target: '#plot-id',
          data: [{
                  yAxis: {domain: [-5, 5]},
                  xAxis: {domain: [-5, 5]},
                  fnType: 'implicit',
                  fn: text
          }]
        });
    }
    catch (err) {
      console.log(err);
      alert(err);
    }
}

function drawC() {
    var div = document.getElementById("plot-id");
    var px=parseInt(document.getElementById('x').value);
    var py=parseInt(document.getElementById('y').value);
    var radio=parseInt(document.getElementById('radio').value);
    div.textContent="(x-"+px+")^2+(y-"+py+")^2-"+radio+"^2";
    var text = div.textContent;
    try {
      functionPlot({
        target: '#plot-id',
        grid: true,
        data: [{
        yAxis: {domain: [-5, 5]},
                xAxis: {domain: [-5, 5]},
                fnType: 'implicit',
          fn: text
        }]
      });
    }
    catch (err) {
      console.log(err);
      alert(err);
    }
}
function drawE() {
    var div = document.getElementById("plot-id");
    var e1=parseInt(document.getElementById('eje_1').value);
    var e2=parseInt(document.getElementById('eje_2').value);
    var px=parseInt(document.getElementById('x').value);
    var py=parseInt(document.getElementById('y').value);
    div.textContent="(x-"+px+")^2/"+e1+"^2+(y-"+py+")^2/"+e2+"^2-1";
    var text = div.textContent;
    try {
      functionPlot({
        target: '#plot-id',
        grid: true,
        data: [{
        yAxis: {domain: [-5, 5]},
                xAxis: {domain: [-5, 5]},
                fnType: 'implicit',
          fn: text
        }]
      });
    }
    catch (err) {
      console.log(err);
      alert(err);
    }
}
function drawH() {
    var div = document.getElementById("plot-id");
    var e1=parseInt(document.getElementById('eje_1').value);
    var e2=parseInt(document.getElementById('eje_2').value);
    var px=parseInt(document.getElementById('x').value);
    var py=parseInt(document.getElementById('y').value);
    var focal=document.getElementById('eje_focal').selectedIndex;
    if(focal===0)
      div.textContent="(x-"+px+")^2/"+e1+"^2-(y-"+py+")^2/"+e2+"^2-1";
    else
      div.textContent="(y-"+py+")^2/"+e1+"^2-(x-"+px+")^2/"+e2+"^2-1";
    var text = div.textContent;
    try {
        functionPlot({
          target: '#plot-id',
          grid: true,
          data: [{
          yAxis: {domain: [-5, 5]},
                  xAxis: {domain: [-5, 5]},
                  fnType: 'implicit',
            fn: text
          }]
        });
    }
    catch (err) {
    console.log(err);
    alert(err);
  }
}

