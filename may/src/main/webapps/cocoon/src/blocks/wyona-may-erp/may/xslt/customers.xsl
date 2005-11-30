<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns="http://www.w3.org/1999/xhtml"
  xmlns:xhtml="http://www.w3.org/1999/xhtml"
  xmlns:collection="http://apache.org/cocoon/collection/1.0"
  >

<xsl:template match="collection:collection">
<html>
<body>
<h1>Customers</h1>
<a href="?cocoon-view=generator-view">XML view</a>
<ul>
<!-- NOTE: Don't show subversion directory -->
<xsl:for-each select="collection:collection[@name != '.svn']">
<li><a href="{@name}.html"><xsl:value-of select="@name"/></a>
<ul>
<li><xsl:value-of select="collection:resource/collection:xpath/invoice:invoice/invoice:description" xmlns:invoice="http://www.wyona.org/erp/1.0"/></li>
</ul>
</li>
</xsl:for-each>
</ul>
</body>
</html>
</xsl:template>

<!--
<xsl:template match="@*|node()">
  <xsl:copy><xsl:apply-templates select="@*|node()"/></xsl:copy>
</xsl:template>
-->

</xsl:stylesheet>
