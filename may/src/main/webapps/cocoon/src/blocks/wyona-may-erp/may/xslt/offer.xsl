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
Customer: <a href="../../customers/{offer:customer/@id}.html"><xsl:value-of select="offer:customer/@id"/></a>
<br/>
Description: <xsl:value-of select="offer:description"/>
<br/>
Status: <xsl:value-of select="offer:status"/>
<br/>
<b>TOTAL</b> (no VAT): <xsl:value-of select="offer:total-amount/@currency"/><xsl:text> </xsl:text><xsl:value-of select="offer:total-amount"/>
<br/>
<h2>Resources</h2>
<xsl:apply-templates select="offer:pdf"/>
<xsl:apply-templates select="offer:sxc"/>
<xsl:apply-templates select="offer:doc"/>
<xsl:apply-templates select="offer:email"/>

<xsl:apply-templates select="offer:tasks"/>
</p>
</body>
</html>
</xsl:template>

<xsl:template match="offer:pdf">
<br/>
PDF: <a href="{$offer-id}/{@href}"><xsl:value-of select="@href"/></a>
</xsl:template>

<xsl:template match="offer:sxc">
<br/>
SXC: <a href="{$offer-id}/{@href}"><xsl:value-of select="@href"/></a>
</xsl:template>

<xsl:template match="offer:doc">
<br/>
DOC: <a href="{$offer-id}/{@href}"><xsl:value-of select="@href"/></a>
</xsl:template>

<xsl:template match="offer:email">
<br/>
E-Mail: <a href="{$offer-id}/{@href}"><xsl:value-of select="@href"/></a>
</xsl:template>

<xsl:template match="offer:tasks">
<h4>Tasks</h4>
<xsl:for-each select="offer:task">
<li><a href="../../tasks/task-{@id}.html"><xsl:value-of select="@id"/></a></li>
</xsl:for-each>
</xsl:template>

</xsl:stylesheet>
