<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>
    
    <xsl:template match="/">
        <html>
            <head>
                <title>Autók rendszáma és ára</title>
                <style>
                    table {
                        border-collapse: collapse;
                        width: 60%;
                        margin: 20px auto;
                    }
                    th, td {
                        border: 1px solid black;
                        padding: 8px;
                        text-align: left;
                    }
                    th {
                        background-color: #2196F3;
                        color: white;
                    }
                    tr:nth-child(even) {
                        background-color: #f2f2f2;
                    }
                    h1 {
                        text-align: center;
                    }
                </style>
            </head>
            <body>
                <h1>Autók rendszáma és ára az ár szerinti sorrendben előállító listája</h1>
                <table>
                    <tr>
                        <th>Rendszám</th>
                        <th>Ár</th>
                    </tr>
                    <xsl:for-each select="autok/auto">
                        <xsl:sort select="ar" data-type="number" order="ascending"/>
                        <tr>
                            <td><xsl:value-of select="@rsz"/></td>
                            <td><xsl:value-of select="ar"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
