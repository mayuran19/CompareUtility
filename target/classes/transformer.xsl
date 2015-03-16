<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<!-- <xsl:template match="root|@*">
		<xsl:copy>
			<xsl:apply-templates select="catalog/book">
				<xsl:sort select="author" order="ascending" />
			</xsl:apply-templates>
		</xsl:copy>
	</xsl:template> -->
	<xsl:template match="@* | node()">
		<xsl:copy>
			<xsl:apply-templates select="@* | node()">
				<xsl:sort />
			</xsl:apply-templates>
		</xsl:copy>
	</xsl:template>
</xsl:stylesheet>