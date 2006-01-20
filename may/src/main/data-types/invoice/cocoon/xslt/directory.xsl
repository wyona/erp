<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns="http://www.w3.org/1999/xhtml" xmlns:dir="http://apache.org/cocoon/directory/2.0" version="1.0">
    <xsl:template match="dir:directory">
        <html>
            <head><title>Rechnungen</title></head>
            <body>
                <h1>Rechnungen</h1>
                <table>
                    <tr><td width="100px">ID</td><td width="50px">Download</td><td width="200px">Kunde</td><td width="200px">Beschreibung</td></tr>
                    <tr><td>?</td><td><a href="sampleinvoice.odt">Download</a></td><td>Beispielkunde</td><td>Beispielrechnung</td></tr>
                    <xsl:apply-templates select="dir:file"/>
                </table>
            </body>
        </html>
    </xsl:template>
    <xsl:template match="dir:file">
        <tr><td><xsl:value-of select="dir:xpath"/></td><td><a href="{substring(@name, 1, string-length(@name)-4)}.odt">Download</a></td><td>Beispielkunde</td><td>Beispielrechnung</td></tr>
    </xsl:template>
</xsl:stylesheet>