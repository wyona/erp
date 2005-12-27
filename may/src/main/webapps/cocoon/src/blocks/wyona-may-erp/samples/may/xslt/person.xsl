<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns="http://www.w3.org/1999/xhtml"
  xmlns:xhtml="http://www.w3.org/1999/xhtml"
  xmlns:may="http://www.wyona.org/erp/1.0"
  xmlns:employee="http://www.wyona.org/erp/1.0"
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
<xsl:apply-templates select="may:skype"/>
<br/><br/>
Company: <a href="../customers/{may:company/@id}.html"><xsl:value-of select="may:company/@id"/></a>
</p>

<xsl:apply-templates select="may:misc"/>

<xsl:apply-templates select="employee:absences"/>
</body>
</html>
</xsl:template>

<xsl:template match="may:phone">
<xsl:value-of select="."/><br/>
</xsl:template>

<xsl:template match="may:email">
<a href="mailto:{.}"><xsl:value-of select="."/></a><br/>
</xsl:template>

<xsl:template match="may:skype">
Skype: <a href="callto:{.}"><xsl:value-of select="."/></a><br/>
</xsl:template>

<xsl:template match="may:misc">
<h4>Misc</h4>
<p>
<xsl:value-of select="."/>
</p>
</xsl:template>

<xsl:template match="employee:absences">
<h4>Absences</h4>
<p>
<table border="1">
  <tr>
  <th>Date</th>
  <th>Type</th>
  <th>Duration</th>
  <th>Comment</th>
  </tr>
<xsl:for-each select="employee:absence">
  <tr>
  <td><xsl:value-of select="@date"/></td>
  <td><xsl:value-of select="@type"/></td>
  <td><xsl:value-of select="@duration"/></td>
  <td><xsl:apply-templates/></td>
  </tr>
</xsl:for-each>
</table>

<xsl:apply-templates select="employee:total"/>
</p>
</xsl:template>

<xsl:template match="employee:total">
<b>TOTAL:</b><xsl:value-of select="."/> - <xsl:value-of select="@type"/> (<xsl:value-of select="@year"/>)
<br/>
</xsl:template>

</xsl:stylesheet>
