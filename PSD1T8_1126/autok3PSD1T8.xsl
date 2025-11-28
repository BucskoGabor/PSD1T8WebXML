<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>
    
    <xsl:template match="/">
        <html>
            <head>
                <title>Drága autók száma</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        margin: 20px;
                        text-align: center;
                    }
                    h1 {
                        color: #333;
                    }
                    .result {
                        font-size: 24px;
                        color: #4CAF50;
                        margin-top: 20px;
                    }
                </style>
            </head>
            <body>
                <h1>Mennyi autó drágább mint 30000?</h1>
                <div class="result">
                    <xsl:value-of select="count(autok/auto[ar &gt; 30000])"/> db
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
