<#import "../macro/headerMacro.ftl" as h>
<#import "../macro/footerMacro.ftl" as f>
<#import "../macro/headGeneral.ftl" as g>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>404</title>
    <@g.head />
</head>
<body>

<script>
    function getEmoji() {
        let emoji = [
            '(=\'X\'=)', '(o^^)o', 'ಠ_ಠ', '╯︿╰',
            '(^-^*)', '(·_·)', '\\(^Д^)/', '(≥o≤)', '(; - ;)',
            '\\(o_o)/', '(>_<)', 'ಥ_ಥ', '¯\\_(ツ)_/¯'
        ];

        let left = document.getElementById("left")
        let right = document.getElementById("right")

        left.innerHTML = emoji[Math.floor(Math.random() * Math.floor(emoji.length))]
        right.innerHTML = emoji[Math.floor(Math.random() * Math.floor(emoji.length))]
    }

    document.addEventListener("DOMContentLoaded", getEmoji);
</script>

<div class="wrapper">

    <@h.header />

    <div class="content">
        <div class="container">
            <div class="error"
                 style="display: flex; flex-direction: column; height: 100%; align-items: center; justify-content: center">
                <div class="error__text"
                     style="text-align: center; font-size: 50px; font-weight: 500; margin-bottom: 30px" on="getEmoji()">
                    <span id="left"></span> Sorry. This page does not exist. <span id="right"></span>
                </div>

                <img src="/image/server.gif" alt="Server ;(">

                <div class="error__text"
                     style="text-align: center; font-size: 50px; font-weight: 500; margin: 30px" on="getEmoji()">
                    404
                </div>

            </div>
        </div>
    </div>

    <@f.footer />

</div>
</body>
</html>