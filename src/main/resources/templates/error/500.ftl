<#import "../macro/headerMacro.ftl" as h>
<#import "../macro/footerMacro.ftl" as f>
<#import "../macro/headGeneral.ftl" as g>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>500</title>
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
                    <span id="left"></span>Что то пошло не так<span id="right"></span>
                </div>
                <div class="error__text"
                     style="text-align: center; font-size: 50px; font-weight: 500; margin: 30px" on="getEmoji()">
                    Ошибка: 500
                </div>

            </div>
        </div>
    </div>

    <@f.footer />

</div>
</body>
</html>