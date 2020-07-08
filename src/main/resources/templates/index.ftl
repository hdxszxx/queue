<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>排队队列</title>
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,user-scalable=yes, minimum-scale=0.4, initial-scale=0.8,target-densitydpi=low-dpi"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>
</head>
<body>
    <div>
        <div id="games">
        </div>
        <p><span>当前选择的游戏为：</span><p id="gameName"></p></p>
    </div>
    <div>填写需要排队的人数：<input type="number" id="userNum"/></div>
    <div>
        <button type="button" id="start">开始排队</button>
<#--        <button type="button" id="cancel">取消排队</button>-->
    </div>
    <div id="userQueue">
    </div>
<script type="application/javascript">
    $(function(){
        var game = "";
        $.ajax({
            type    : 'POST',
            url     : "/queue/games",
            success : function(data) {
                for (var gameName in data){
                    var element = "<button class=\"games\" value = \""+data[gameName]+"\" type=\"button\">"+data[gameName]+"</button>";
                    $("#games").append(element);
                }
            }
        });
        $( "#games" ).on( "click", ".games", function() {
            game = $(this).prop("value");
            $("#gameName").text(game);
        });

        $("#start").click(function(){
            startQueue();
        });
        $("#cancel").click(function(){
            cancelQueue();
        });

        function startQueue(){
            if(!game){
                alert("请选择游戏");
                return;
            }
            var userNum = $("#userNum").val();
            for (var i=0;i<userNum;i++){
                var userName = new Date().getTime()+i;
                var element = "<p>我是第<span id='head"+i+"'></span>进去<span>当前用户名为：</span><span>"+userName+"</span>，<span>前面还有</span><span id='num"+i+"'>0</span>人</p>";
                $("#userQueue").append(element);
                start(null,i,userName);
            }
        }

        function check(interval,index,userName){
            $.ajax({
                type    : 'POST',
                url     : "/queue/check",
                data : {"game":game,"name":userName},
                success : function(data) {
                    data = Number.parseFloat(data);
                    if(data < 0){
                        $("#num"+index).text((data+1));
                        if(interval){
                            clearInterval(interval);
                        }
                    }else{
                        $("#num"+index).text((data+1));
                        var a=setTimeout(function(){
                            check(a,index,userName)
                        },2000);
                    }
                }
            });
        }

        function start(interval,index,userName){
            $.ajax({
                type    : 'POST',
                url     : "/queue/start",
                data : {"game":game,"name":userName},
                success : function(data) {
                    check(null,index,userName)
                }
            });
        }
        function cancelQueue(){
            $.ajax({
                type    : 'POST',
                url     : "/queue/cancel",
                data : {"game":game,"name":userName},
                success : function(data) {
                  clearInterval(interval);
                }
            });
        }
    });
</script>
</body>
</html>