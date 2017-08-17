Hallo ${user.firstName} ${user.surName},

es wurde eine neues Passwort für ${application} angefordert.

Dieses lautet: ${password}

Mit diesem Passwort können Sie sich auf ${url} einloggen.
<#if user.requestChange>
Das Passwort muss nach der Anmeldung sofort geändert werden.
Halten Sie also bitte ein neues bereit.
</#if>

      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(draw${chartname});

      function draw${chartname}() {

        // Create the data table.
        var data = new google.visualization.DataTable();
        data.addColumn('string', 'Topping');
        data.addColumn('number', 'Slices');
        data.addRows([
          ['Mushrooms', 3],
          ['Onions', 1],
          ['Olives', 1],
          ['Zucchini', 1],
          ['Pepperoni', 2]
        ]);

        // Set chart options
        var options = {'title':'How Much Pizza I Ate Last Night',
                       'width':400,
                       'height':300};

        // Instantiate and draw our chart, passing in some options.
        var ${chartname} = new google.visualization.PieChart(document.getElementById('${elementId}'));
        ${chartname}.draw(data, options);
      }

