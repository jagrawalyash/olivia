
function delete_data()
{
       $('#drug_div').css("display","none");
       $('#suggestion-for-drug-table').empty();
       $('#input-for-drug').val("");

       $('#disease_div').css("display","none");
       $('#suggestion-for-disease-table').empty();
       $('#input-for-disease').val("");


       $('#location_div').css("display","none");
       $('#suggestion-for-center-table').empty();
       $('#input-for-center').val("");

       $('#doctor_div').css("display","none");
       $('#suggestion-for-doctor-table').empty();
       $('#input-for-doctor').val("");
}

$("#input-for-drug").keyup(function(e){
    $('#drug_div').css("display","none");
	var value = $(this).val();
    $.ajax({
      		url : "/drugSuggestion?obj="+value,
    		type : 'POST',
     		success : function (result) {
     		$("#suggestion-for-drug-table").empty();
    			if(result.length==0)
    			{
    			   var x = "<tr>"+
    			   "<td onclick=\"drug_select('');\">"+value+"</td>"+
    			   "</tr>";
    			   $('#suggestion-for-drug-table').append(x);
    			}
    			for(var i=0; i<result.length;i++){
                			var x = "<tr>"+
                              "<td onclick=\"drug_select('" +result[i]['value']+"');\">"+result[i]['key']+"</td>"+
                               "</tr>";
                            $('#suggestion-for-drug-table').append(x);
                		}
     		},
    		error : function (error) {
     			console.log (error.message);
     		}
     	});
});


function drug_select(x)
{
    $.ajax({
        url:"/getDrug?dname="+x,
        type:"POST",
        success:function(result){
            if(result !="")
            {
                $('#input-for-drug').val(result['key']);
                $('#suggestion-for-drug-table').empty();
                $('#drug_div').css("display","block");
                $('#drug_description').text(result['value']);
            }
            else
            {
                alert("Drug not Found");
            }
        }
    });
}



$("#input-for-disease").keyup(function(e){
    $('#disease_div').css('display','none');
	var value = $(this).val();
    $.ajax({
      		url : "/diseaseSuggestion?obj="+value,
    		type : 'POST',
     		success : function (result) {
     		$("#suggestion-for-disease-table").empty();
    			if(result.length==0)
    			{
    			   var x = "<tr>"+
    			   "<td onclick=\"disease_select('');\">"+value+"</td>"+
    			   "</tr>";
    			   $('#suggestion-for-disease-table').append(x);
    			}
    			for(var i=0; i<result.length;i++){
                			var x = "<tr>"+
                              "<td onclick=\"disease_select('" +result[i]['value']+"');\">"+result[i]['key']+"</td>"+
                               "</tr>";
                            $('#suggestion-for-disease-table').append(x);
                		}
     		},
    		error : function (error) {
     			console.log (error.message);
     		}
     	});
});
function disease_select(x)
{
    $.ajax({
        url:"/getDisease?dname="+x,
        type:"POST",
        success:function(result){
            $('#input-for-disease').val(result[0]);
            $('#suggestion-for-disease-table').empty();
            $('#disease_div').css('display','block');
            $('#disease_description').text(result[1]);
        }
    });
}

$("#input-for-center").keyup(function(e){
    $('#location_div').css('display','none');
	var value = $(this).val();
    $.ajax({
      		url : "/centerSuggestion?obj="+value,
    		type : 'POST',
     		success : function (result) {
     		$("#suggestion-for-center-table").empty();
    			if(result.length==0)
    			{
    			   var x = "<tr>"+
    			   "<td onclick=\"center_select('" +value+"');\">"+value+"</td>"+
    			   "</tr>";
    			   $('#suggestion-for-center-table').append(x);
    			}
    			for(var i=0; i<result.length;i++){
                			var x = "<tr>"+
                              "<td onclick=\"center_select('" +result[i]+"');\">"+result[i]+"</td>"+
                               "</tr>";
                            $('#suggestion-for-center-table').append(x);
                		}
     		},
    		error : function (error) {
     			console.log (error.message);
     		}
     	});
});
function center_select(x)
{
    $('#input-for-center').val(x);
    $.ajax({
        url:"getCenterDescription?name="+x,
        type:"POST",
        success:function(result){
            $('#location_div').css('display','block');
            $('#suggestion-for-center-table').empty();
            $('#center_name').text(result[0]);
            $('#center_location').text(result[1]);
            $('#center_contact_number').text(result[2]);
        }
    });
}

$("#input-for-doctor").keyup(function(e){
    $('#doctor_div').css("display","none");
	var value = $(this).val();
    $.ajax({
      		url : "/doctorSuggestion?obj="+value,
    		type : 'POST',
     		success : function (result) {
     		$("#suggestion-for-doctor-table").empty();
    			if(result.length==0)
    			{
    			   var x = "<tr>"+
    			   "<td onclick=\"doctor_select('" +value+"');\">"+value+"</td>"+
    			   "</tr>";
    			   $('#suggestion-for-doctor-table').append(x);
    			}
    			for(var i=0; i<result.length;i++){
                			var x = "<tr>"+
                              "<td onclick=\"doctor_select('" +result[i]+"');\">"+result[i]+"</td>"+
                               "</tr>";
                            $('#suggestion-for-doctor-table').append(x);
                		}
     		},
    		error : function (error) {
     			console.log (error.message);
     		}
     	});
});

function doctor_select(x)
{
    $('#input-for-doctor').val(x);
    $.ajax({
        url:"getDoctorDescription?name="+x,
        type:"POST",
        success:function(result){
            $('#doctor_div').css('display','block');
            $('#suggestion-for-doctor-table').empty();
            $('#doctor_name').text(result[0].toUpperCase());
            $('#doctor_location').text(result[1].toUpperCase());
            $('#doctor_experience').text(result[2]);
            $('#doctor_description').text(result[3]);
        },
    });
}
function dark_mode()
{
    $('body').css("background-color","#181818");
    $('body').css("color","white");

    $('h1').css("color","white");
    $('h3').css("color","#E6E6FA");
    $('h6').css("color","white");

    $('p').css("color","white");

    $('.menu1').css("background-color","#00BFFF");
    $('.menu1').css("color","black");
    $('.panel button').css("background-color","#00BFFF");
    $('.panel button').css("color","black");
    $('.btn-close').css("background-color","#00BFFF");
    $('.btn-close').css("color","black");
    $('.btn btn-secondary').css("background-color","#00BFFF");
    $('.btn btn-secondary').css("color","black");

    $('.inputText').css("background-color","#696969");
    $('input').css("background-color","#696969");
    $('input').css("color","white");

    $('.suggestion button').css("background-color","#00BFFF");
    $('.suggestion button').css("color","black");

    $(".modal-content").css("background-color","#808080");
    $(".modal-body").css("background-color","#808080");

    $('.title').css("color","#E6E6FA");
    $('.icon i').css("color","white");

    $('table').css("background-color","#808080");
    $('table').css("color","black");

    $(".status").css("color","black");
}
function light_mode()
{
    $('body').css("background-color","white");
    $('body').css("color","grey");

    $('h1').css("color","black");
    $('h3').css("color","#2c4964");
    $('h6').css("color","black");

    $('p').css("color","black");

    $('.menu1').css("background-color","#F0F0F0");
    $('.menu1').css("color","grey");
    $('.panel button').css("background-color","#F0F0F0");
    $('.panel button').css("color","grey");
    $('.btn-close').css("background-color","white");
    $('.btn-close').css("color","#000");
    $('.btn btn-secondary').css("background-color","#6c757d");
    $('.btn btn-secondary').css("color","white");

    $('.inputText').css("background-color","white");
    $('input').css("background-color","white");
    $('input').css("color","black");

    $('.suggestion button').css("background-color","transparent");
    $('.suggestion button').css("color","lightgray");

     $(".modal-content").css("background-color","white");
     $(".modal-body").css("background-color","white");

     $('.title').css("color","black");
     $('.icon i').css("color","black");

     $('table').css("background-color","#F0F8FF");
     $('table').css("color","white");

     $(".status").css("color","black");

}


