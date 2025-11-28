<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>
    
    <xsl:template match="/">
        <html>
            <head>
                <title>Dokumentum elemszáma</title>
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
                        color: #FF5722;
                        margin-top: 20px;
                    }
                </style>
            </head>
            <body>
                <h1>Mennyi elemből áll a dokumentum?</h1>
                <div class="result">
                    <xsl:value-of select="count(//*)"/> elem
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
