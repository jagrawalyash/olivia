window.SpeechRecognition = window.webkitSpeechRecognition || window.SpeechRecognition;
let finalTranscript = '';
let recognition = new window.SpeechRecognition();

recognition.interimResults = true;
recognition.maxAlternatives = 1;

recognition.abort();

var mainResult = "";
var voices = speechSynthesis.getVoices();
var utterance;

const awaitVoices = new Promise(done => speechSynthesis.onvoiceschanged = done);
awaitVoices.then(()=> {
        voices = speechSynthesis.getVoices();
});



recognition.onresult = (event) => {
	let interimTranscript = '';
    for (let i = event.resultIndex, len = event.results.length; i < len; i++) {
        let transcript = event.results[i][0].transcript;
        if (event.results[i].isFinal) {
          finalTranscript += transcript;
		  if ('speechSynthesis' in window) {
		  $.ajax({
                	url : "/suggestion?obj="+transcript,
              		type : 'POST',
              		timeout:5000,
               		success : function (result) {
               		console.log(result)
               		$("#suggestion").html("");
              			if(result!="")
              			{
              			    $("#suggestion").append("<button onclick=\"sendTo('"+transcript+"')\">"+transcript+"</button>");
              			}
              			for(var i=0; i<result.length;i++){
                          			$("#suggestion").append("<button onclick=\"sendTo('"+result[i]+"')\">"+result[i]+"</button>");
                          		}
               		},
              		error : function (error) {
               			getResult("Invalid input",1);
               		}
               	});
		  }
		
        } else {
          	interimTranscript += transcript;
			if(interimTranscript!=""){
					$("#suggestion").html("<button onclick=\"sendTo('"+interimTranscript+"')\">"+interimTranscript+"</button>");
			}
        }
    }
}

recognition.onspeechend = function() {
    $("#search").css("background-image","");
	$(".inputText").children("img").removeClass("speake");
	recognition.stop();
}
$('#search').click(function(){
    speechSynthesis.cancel();
    recognition.abort();
    $("#search").val("");
    $("#search").css("background-image","none");
});

$("#mic").click(function(){
	if(speechSynthesis.speaking){
		speechSynthesis.cancel();
	}
	
	recognition.abort();
	
	$("#search").val("");
	$("#search").css("background-image","url('src/image/wave.gif')");
	$(".inputText").children("img").addClass("speake");
	recognition.start();
});

$("#search").keyup(function(e){
	var value = $(this).val();
	if(e.which == 13) {
        sendTo(value);
        $(this).val("")
    }
    $.ajax({
      		url : "/suggestion?obj="+value,
    		type : 'POST',
     		success : function (result) {
     		$("#suggestion").html("");
    			if(result!="")
    			{
    			    $("#suggestion").append("<button onclick=\"sendTo('"+value+"')\">"+value+"</button>");
    			}
    			for(var i=0; i<result.length;i++){
                			$("#suggestion").append("<button onclick=\"sendTo('"+result[i]+"')\">"+result[i]+"</button>");
                		}
     		},
    		error : function (error) {
     			console.log (error.message);
     		}
     	});
});

function sendTo(str){
	$(".chatBoard").append(`<div class="right">`+str+`</div>`);
	$(".chatBoard").animate({
		scrollTop : $(".chatBoard").prop("scrollHeight")
	});
	
	
	$.ajax({
  		url : "/getAnswer?obj="+encodeURIComponent(str),
		type : 'POST',
 		success : function (result) {
			if(result!="")
			{
			    getResult(result,1);
			}
//			else{
//				tryAnother(encodeURIComponent(str));
//			}
 		},
		error : function () {
 			getResult("Invalid input",1);
 			console.log("ERROR");
 		}
 	});
	
}

//function tryAnother(obj){
//	$.ajax({
//  		url : "/getAnswer?obj="+obj,
//		type : 'POST',
//		timeout:1000,
// 		success : function (result) {
// 		console.log(result);
//	        if(result=="")
//	        {
//	            ans = "result not found";
//	        }
//	        else
//	        {
//	            ans = result;
//	        }
//            getResult(ans,1);
// 		},
//		error : function (error) {
// 			getResult("Invalid",1);
// 		}
// 	});
//}

function getResult(str,val){
	$(".chatBoard").append(`<div class="left">`+str+`</div>`);
	$(".chatBoard").animate({
		scrollTop : $(".chatBoard").prop("scrollHeight")
	});
	say(str,val);
}
function sendReportResult(str)
{
    $(".chatBoard").append(`<div class="left">`+str+`</div>`);
    	$(".chatBoard").animate({
    		scrollTop : $(".chatBoard").prop("scrollHeight")
    	});
}

function say(str,val) {
		if(speechSynthesis.speaking&&val){
			speechSynthesis.cancel();
		}
	
		str = str.replace(/<br\s*[\/]?>/gi,"\n");
    	voices = speechSynthesis.getVoices();
		console.log(voices);
		utterance = new SpeechSynthesisUtterance(str);
 		utterance.voice = voices[1];
		utterance.lang = voices[1].lang;
		utterance.pitch = 1;
		utterance.rate = 1;
		window.speechSynthesis.speak(utterance);
}

$(".begin").click(function(){
	$(this).parent().parent().css("display","none");
	getResult("Hello!",0);
	getResult("I'm Olivia, you personal physician",0);
	getResult("How may I help you",0);
	utterance.addEventListener('end', event => {
		$("#mic").click();
	});
	
});
$('#file').click(function(){
    speechSynthesis.cancel();
    recognition.abort();
    $("#search").val("");
    $("#search").css("background-image","none");
});
$("#file").change(function(){
	var form_data = new FormData();
	$(".status").html("OCR is in process");
	form_data.append("file", document.getElementById('file').files[0]);
    $.ajax({
    	url:"/uploadReport",
    	method:"POST",
    	data: form_data,
    	contentType: false,
    	cache: false,
    	processData: false,
    	beforeSend:function(){
    	},   
    	success:function(data){
			$(".reportScane").css("display","flex");
			document.getElementById("file").value = "";
     		scanReport("C://Users//vaibhav jangid//Desktop//Spring Boot//scanned_report//"+data);
    	},
   	});
});

function scanReport(report){
	$.ajax({
        	url:"/getTextFromImg?component="+report,
        	method:"POST",
        	contentType: false,
        	cache: false,
        	processData: false,
        	success:function(data){
    			console.log(data);
    			$(".reportScane").css("display","none");
    			if(data.length!=0)
    			{
    			    for(var i=0;i<data.length;i++)
                    {
               		    sendReportResult(data[i]);
                    }
    			}
    			else
    			{
    			    getResult("Components not found",1);
    			}

        	}
       	});
}

