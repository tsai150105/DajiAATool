document.write('<a href="http://fc2.com/"><img id="fc2analyzerimg_" src="https://analyzer52.fc2.com/ana/icon0.gif" alt="FC2 Analyzer" style="position:absolute;left:0px;top:0px;border:solid 0px #ffffff;" /></a>');
setTimeout( function(){ if(document.getElementById){ document.getElementById("fc2analyzerimg_").style.display = 'none'; }else if(document.all){ document.all("fc2analyzerimg_").style.display = 'none'; } }, 8 * 1000 );

{
var expire = new Date;
expire.setTime(expire.getTime()+7200000);
document.cookie = 'FC2ANASESSION1791575=17581508; expires='+expire.toGMTString();
}
