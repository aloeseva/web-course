<#import "../macro/headerMacro.ftl" as h>
<#import "../macro/footerMacro.ftl" as f>
<#import "../macro/headGeneral.ftl" as g>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>403</title>
    <@g.head />

    <link rel="stylesheet" href="/css/fourZeroThreeError.css">
</head>
<body>


<div class="wrapper">

    <@h.header />

    <div class="content">
        <div class="container" style="max-width: 100%;">

            <div class="error">
                <div>403</div>
                <div class="txt">
                    Forbidden<span class="blink">_</span>
                </div>
            </div>
        </div>
    </div>

    <@f.footer />

</div>
</body>
</html>