<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xi="http://www.w3.org/2001/XInclude"
    xmlns:xforms="http://www.w3.org/2002/xforms" version="1.0">
    <xsl:param name="invoicepath"/>
    <xsl:param name="invoicesubmiturl"/>
    <xsl:template match="xforms:instance">
        <!--xforms:instance id="invoice" src="http://localhost:8888/erp-may/oooinvoice/invoicecontents|{$invoicepath}"-->
        <xforms:instance id="invoice">
            <instanceData>
                <xi:include href="{$invoicepath}"/>
            <!--invoice id="987654">
                <description>Eine Beschreibung</description>
                <vattext select="Mehrwertsteuer">ür Deutschland entfällt die Schweizer Mehrwertsteuer.
Bezüglich der deutschen Steuerschuld geht die Steuerschuldnerschaft
nach § 13 b UstG auf CustomerName über.</vattext>
                <dates invoicedate="0" timelimit="30" maturity="0"/>
                <customer id="12">
                    <name>HALLO</name>
                    <address>
                        <contact>Herr Irgendwer</contact>
                        <street>SomeStreet 12</street>
                        <zip>CH-3001 SomeWhere</zip>
                    </address>
                </customer>
                <extratext>
                    <head1/>
                    <head2/>
                    <bottom1/>
                </extratext>
                <amount tax="7.6" vat="1.0108" net="13.3" currency="€" sum="14.3108"/>
            </invoice-->
            </instanceData>
        </xforms:instance>
    </xsl:template>
    
    <xsl:template match="xforms:submission">
        <xforms:submission
            id="serversubmission"
            action="{$invoicesubmiturl}"
            method="post" indent="false"
            omit-xml-declaration="false"
            standalone="false"
            replace="none"/>
    </xsl:template>
    
    
    <!--xsl:template match="instanceData">
        <instanceData>
            <xi:include href="{$invoicepath}"/>
            <invoice id="987654">
                <describtion>Eine Beschreibung</describtion>
                <vattext select="Mehrwertsteuer">ür Deutschland entfällt die Schweizer Mehrwertsteuer.
Bezüglich der deutschen Steuerschuld geht die Steuerschuldnerschaft
nach § 13 b UstG auf CustomerName über.</vattext>
                <dates invoicedate="0" timelimit="30" maturity="0"/>
                <customer id="12">
                    <name>HALLO</name>
                    <address>
                        <contact>Herr Irgendwer</contact>
                        <street>SomeStreet 12</street>
                        <zip>CH-3001 SomeWhere</zip>
                    </address>
                </customer>
                <extratext>
                    <head1/>
                    <head2/>
                    <bottom1/>
                </extratext>
                <amount tax="7.6" vat="1.0108" net="13.3" currency="€" sum="14.3108"/>
            </invoice>
        </instanceData>
    </xsl:template-->
    <xsl:template match="@*|node()" priority="-1">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>
</xsl:stylesheet>
