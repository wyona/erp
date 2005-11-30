<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns="http://www.w3.org/1999/xhtml"
  xmlns:xhtml="http://www.w3.org/1999/xhtml"
  xmlns:offer="http://www.wyona.org/erp/1.0"
  >

<xsl:param name="offer-id" select="'null'"/>

<xsl:template match="offer:offer">
<html>
<body>
<h1>Offer</h1>
<a href="?cocoon-view=generator-view">XML view</a>

<p>
Description: <xsl:value-of select="offer:description"/>
<br/>
<h2>Resources</h2>
PDF: <a href="{$offer-id}/{offer:pdf/@href}"><xsl:value-of select="offer:pdf/@href"/></a>
<br/>
SXC: <a href="{$offer-id}/{offer:sxc/@href}"><xsl:value-of select="offer:sxc/@href"/></a>
</p>
</body>
</html>
</xsl:template>

</xsl:stylesheet>
