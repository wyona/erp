<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
  xmlns="http://www.w3.org/1999/xhtml"
  xmlns:xhtml="http://www.w3.org/1999/xhtml"
  xmlns:may="http://www.wyona.org/erp/1.0"
  >

<xsl:template match="may:open-invoices">
<html>
<body>
<h1>Open Invoices</h1>
<a href="?cocoon-view=generator-view">XML view</a>
<p>
<table border="1">
<tr>
  <th>ID</th><th>Due</th><th>Submitted</th><th>Company</th>
</tr>
<xsl:apply-templates select="may:invoice"/>
</table>
</p>
</body>
</html>
</xsl:template>

<xsl:template match="may:invoice">
<tr>
  <td><a href="invoice-{@id}.html"><xsl:value-of select="@id"/></a></td>
  <td><xsl:value-of select="@due-date"/></td>
  <td><xsl:value-of select="@submit-date"/></td>
  <td><xsl:value-of select="@company-name"/></td>
</tr>
</xsl:template>

</xsl:stylesheet>
