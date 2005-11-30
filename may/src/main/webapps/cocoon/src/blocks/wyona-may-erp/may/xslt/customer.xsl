<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns="http://www.w3.org/1999/xhtml"
  xmlns:xhtml="http://www.w3.org/1999/xhtml"
  xmlns:com="http://www.wyona.org/erp/1.0"
  >

<xsl:param name="company-id" select="'null'"/>

<xsl:template match="com:company">
<html>
<body>
<h1>Company</h1>
<a href="?cocoon-view=generator-view">XML view</a>

<p>
Name: <xsl:value-of select="com:name"/>
<br/>
ID: <xsl:value-of select="com:id"/>
<br/>
Description: <xsl:value-of select="com:description"/>
</p>
</body>
</html>
</xsl:template>

</xsl:stylesheet>
