<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>
    
    <xsl:template match="/">
        <html>
            <head>
                <title>B G Órarend – 2025. I. félév</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        margin: 20px;
                        background-color: #f5f5f5;
                    }
                    h1 {
                        text-align: center;
                        color: #333;
                        margin-bottom: 30px;
                    }
                    table {
                        border-collapse: collapse;
                        width: 95%;
                        margin: 0 auto;
                        background-color: white;
                        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                    }
                    th, td {
                        border: 1px solid #ddd;
                        padding: 12px;
                        text-align: left;
                    }
                    th {
                        background-color: #4CAF50;
                        color: white;
                        font-weight: bold;
                    }
                    tr:nth-child(even) {
                        background-color: #f9f9f9;
                    }
                    tr:hover {
                        background-color: #f1f1f1;
                    }
                    .eloadas {
                        background-color: #e3f2fd;
                    }
                    .gyakorlat {
                        background-color: #fff3e0;
                    }
                </style>
            </head>
            <body>
                <h1>B G Órarend – 2025. I. félév</h1>
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Típus</th>
                        <th>Tantárgy</th>
                        <th>Nap</th>
                        <th>Időpont (tól)</th>
                        <th>Időpont (ig)</th>
                        <th>Helyszín</th>
                        <th>Oktató</th>
                        <th>Szak</th>
                    </tr>
                    <xsl:for-each select="PSD1T8_orarend/ora">
                        <tr>
                            <xsl:attribute name="class">
                                <xsl:value-of select="@tipus"/>
                            </xsl:attribute>
                            <td><xsl:value-of select="@id"/></td>
                            <td><xsl:value-of select="@tipus"/></td>
                            <td><xsl:value-of select="targy"/></td>
                            <td><xsl:value-of select="idopont/nap"/></td>
                            <td><xsl:value-of select="idopont/tol"/></td>
                            <td><xsl:value-of select="idopont/ig"/></td>
                            <td><xsl:value-of select="helyszin"/></td>
                            <td><xsl:value-of select="oktato"/></td>
                            <td><xsl:value-of select="szak"/></td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
