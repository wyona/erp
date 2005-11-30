<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns="http://www.w3.org/1999/xhtml"
  xmlns:xhtml="http://www.w3.org/1999/xhtml"
  xmlns:may="http://www.wyona.org/erp/1.0"
  >

<xsl:param name="offer-id" select="'null'"/>

<xsl:template match="may:person">
<html>
<body>
<h1>Person</h1>
<a href="?cocoon-view=generator-view">XML view</a>

<p>
<b><xsl:value-of select="may:firstname"/> <xsl:value-of select="may:middlename"/> <xsl:value-of select="may:lastname"/></b>
<br/>
<xsl:apply-templates select="may:phone"/>
<xsl:apply-templates select="may:email"/>
<br/><br/>
Company: <a href="../customers/{may:company/@id}.html"><xsl:value-of select="may:company/@id"/></a>
</p>
</body>
</html>
</xsl:template>

<xsl:template match="may:phone">
<xsl:value-of select="."/><br/>
</xsl:template>

<xsl:template match="may:email">
<a href="mailto:{.}"><xsl:value-of select="."/></a><br/>
</xsl:template>

</xsl:stylesheet>
