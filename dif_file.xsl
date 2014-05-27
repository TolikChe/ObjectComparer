<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema">
	<xsl:template match="/">
		<head>
<!--			<meta http-equiv="X-UA-Compatible" content="IE=edge">
			<meta name="viewport" content="width=device-width, initial-scale=1">
-->
			<title>Результат сравнения</title>
			<!-- Bootstrap -->
			<link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
			<link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css"/>
			<script type="text/javascript" src="js/json3.min.js">//</script>
			<script type="text/javascript" src="js/jquery.js">//</script>
			<script type="text/javascript" src="js/bootstrap.js">//</script>

		</head>
		<body>
		
<!--			<blockquote>
				<h4>Всего таблиц в базе и файле: <xsl:value-of select="count(//Table[@place='both'])"/>
				</h4>
				<h4>Всего таблиц в базе: <xsl:value-of select="count(//Table[@place='base'])"/>
				</h4>
				<h4>Всего таблиц в файле: <xsl:value-of select="count(//Table[@place='file'])"/>
				</h4>
			</blockquote>
-->			<br/>

			<xsl:if test=" count(//Tables/Table[*/state/text()='diff'])  = 0">
				<h4>Существенных отличий не найдено</h4>	
			</xsl:if>
			
			
			<xsl:if test="count(//Tables/Table[*/state/text()='diff' or Columns/Column/*/state/text()='diff']) &gt; 0">
				<h4>Информация по отличиям в таблицах</h4>
				<table class="table table-striped table-bordered" >
					<tr align="center">
						<td><b>Table</b></td>
						<td><b>Owner</b></td>
						<td colspan="2"><b>Status</b></td>
						<td colspan="2"><b>Partitioned</b></td>
						<td colspan="2"><b>Temporary</b></td>
						<td colspan="2"><b>Compression</b></td>
						<td colspan="2"><b>Logging</b></td>
						<td colspan="2"><b>Cache</b></td>
						<td colspan="2"><b>TableLock</b></td>
					</tr>
					<xsl:for-each select="//Tables/Table[@place='base']">			
						<tr align="center">
							<td align="left"><span class="label label-warning">Таблица есть только в базе</span><xsl:value-of select="sTableName"/></td>
							<td><xsl:value-of select="sOwner/base"/></td>
							<td colspan="2"><xsl:value-of select="sStatus/base"/></td>
							<td colspan="2"><xsl:value-of select="sPartitioned/base"/></td>
							<td colspan="2"><xsl:value-of select="sTemporary/base"/></td>
							<td colspan="2"><xsl:value-of select="sCompression/base"/></td>
							<td colspan="2"><xsl:value-of select="sLogging/base"/></td>
							<td colspan="2"><xsl:value-of select="sCache/base"/></td>
							<td colspan="2"><xsl:value-of select="sTableLock/base"/></td>
						</tr>
					</xsl:for-each>
					<xsl:for-each select="//Tables/Table[@place='file']">			
						<tr align="center">
							<td align="left"><span class="label label-danger">Таблица есть только в файле</span><xsl:value-of select="sTableName"/></td>
							<td><xsl:value-of select="sOwner/file"/></td>
							<td colspan="2"><xsl:value-of select="sStatus/file"/></td>
							<td colspan="2"><xsl:value-of select="sPartitioned/file"/></td>
							<td colspan="2"><xsl:value-of select="sTemporary/file"/></td>
							<td colspan="2"><xsl:value-of select="sCompression/file"/></td>
							<td colspan="2"><xsl:value-of select="sLogging/file"/></td>
							<td colspan="2"><xsl:value-of select="sCache/file"/></td>
							<td colspan="2"><xsl:value-of select="sTableLock/file"/></td>
						</tr>
					</xsl:for-each>
					
					<xsl:if test=" count(//Tables/Table[*/state/text()='diff' and @place='both'])  &gt; 0 ">
						<tr align="center">
							<td colspan="2">Изменились свойства таблицы</td>
							<td><small>file</small></td>
							<td><small>db</small></td>
							<td><small>file</small></td>
							<td><small>db</small></td>
							<td><small>file</small></td>
							<td><small>db</small></td>
							<td><small>file</small></td>
							<td><small>db</small></td>
							<td><small>file</small></td>
							<td><small>db</small></td>
							<td><small>file</small></td>
							<td><small>db</small></td>
							<td><small>file</small></td>
							<td><small>db</small></td>
						</tr> 
					</xsl:if>
					
					<xsl:for-each select="//Tables/Table[*/state/text()='diff' and @place='both']">			
						<tr align="center">
							<td align="left"><span class="label label-info">Изменились свойства таблицы</span><xsl:value-of select="sTableName"/></td>
							<td><xsl:value-of select="sOwner/file"/></td>
	
							<xsl:choose>
								<xsl:when test="sStatus/state = 'diff'">
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sStatus/file"/></td>
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sStatus/base"/></td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="sStatus/file"/></td>
									<td><xsl:value-of select="sStatus/base"/></td>
								</xsl:otherwise>
							</xsl:choose>
	
							<xsl:choose>
								<xsl:when test="sPartitioned/state = 'diff'">
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sPartitioned/file"/></td>
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sPartitioned/base"/></td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="sPartitioned/file"/></td>
									<td><xsl:value-of select="sPartitioned/base"/></td>
								</xsl:otherwise>
							</xsl:choose>
	
							<xsl:choose>
								<xsl:when test="sTemporary/state = 'diff'">
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sTemporary/file"/></td>
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sTemporary/base"/></td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="sTemporary/file"/></td>
									<td><xsl:value-of select="sTemporary/base"/></td>
								</xsl:otherwise>
							</xsl:choose>
							
							<xsl:choose>
								<xsl:when test="sCompression/state = 'diff'">
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sCompression/file"/></td>
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sCompression/base"/></td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="sCompression/file"/></td>
									<td><xsl:value-of select="sCompression/base"/></td>
								</xsl:otherwise>
							</xsl:choose>						
													
							<xsl:choose>
								<xsl:when test="sLogging/state = 'diff'">
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sLogging/file"/></td>
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sLogging/base"/></td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="sLogging/file"/></td>
									<td><xsl:value-of select="sLogging/base"/></td>
								</xsl:otherwise>
							</xsl:choose>						
	
							<xsl:choose>
								<xsl:when test="sCache/state = 'diff'">
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sCache/file"/></td>
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sCache/base"/></td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="sCache/file"/></td>
									<td><xsl:value-of select="sCache/base"/></td>
								</xsl:otherwise>
							</xsl:choose>						
							
							<xsl:choose>
								<xsl:when test="sTableLock/state = 'diff'">
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sTableLock/file"/></td>
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sTableLock/base"/></td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="sTableLock/file"/></td>
									<td><xsl:value-of select="sTableLock/base"/></td>
								</xsl:otherwise>
							</xsl:choose>						
						</tr>
					</xsl:for-each>				

					<xsl:if test=" count(//Tables/Table[Columns/Column/*/state/text()='diff'])  &gt; 0 ">
						<tr align="center">
							<td colspan="2">Изменился состав или свойства колонок</td>
							<td><small>file</small></td>
							<td><small>db</small></td>
							<td><small>file</small></td>
							<td><small>db</small></td>
							<td><small>file</small></td>
							<td><small>db</small></td>
							<td><small>file</small></td>
							<td><small>db</small></td>
							<td><small>file</small></td>
							<td><small>db</small></td>
							<td><small>file</small></td>
							<td><small>db</small></td>
							<td><small>file</small></td>
							<td><small>db</small></td>
						</tr> 
					</xsl:if>
					
					<xsl:for-each select="//Tables/Table[Columns/Column/*/state/text()='diff']">			
						<tr align="center">
							<td align="left"><span class="label label-primary">Изменился состав или свойства колонок</span><xsl:value-of select="sTableName"/></td>
							<td><xsl:value-of select="sOwner/file"/></td>
	
							<xsl:choose>
								<xsl:when test="sStatus/state = 'diff'">
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sStatus/file"/></td>
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sStatus/base"/></td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="sStatus/file"/></td>
									<td><xsl:value-of select="sStatus/base"/></td>
								</xsl:otherwise>
							</xsl:choose>
	
							<xsl:choose>
								<xsl:when test="sPartitioned/state = 'diff'">
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sPartitioned/file"/></td>
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sPartitioned/base"/></td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="sPartitioned/file"/></td>
									<td><xsl:value-of select="sPartitioned/base"/></td>
								</xsl:otherwise>
							</xsl:choose>
	
							<xsl:choose>
								<xsl:when test="sTemporary/state = 'diff'">
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sTemporary/file"/></td>
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sTemporary/base"/></td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="sTemporary/file"/></td>
									<td><xsl:value-of select="sTemporary/base"/></td>
								</xsl:otherwise>
							</xsl:choose>
							
							<xsl:choose>
								<xsl:when test="sCompression/state = 'diff'">
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sCompression/file"/></td>
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sCompression/base"/></td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="sCompression/file"/></td>
									<td><xsl:value-of select="sCompression/base"/></td>
								</xsl:otherwise>
							</xsl:choose>						
													
							<xsl:choose>
								<xsl:when test="sLogging/state = 'diff'">
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sLogging/file"/></td>
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sLogging/base"/></td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="sLogging/file"/></td>
									<td><xsl:value-of select="sLogging/base"/></td>
								</xsl:otherwise>
							</xsl:choose>						
	
							<xsl:choose>
								<xsl:when test="sCache/state = 'diff'">
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sCache/file"/></td>
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sCache/base"/></td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="sCache/file"/></td>
									<td><xsl:value-of select="sCache/base"/></td>
								</xsl:otherwise>
							</xsl:choose>						
							
							<xsl:choose>
								<xsl:when test="sTableLock/state = 'diff'">
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sTableLock/file"/></td>
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sTableLock/base"/></td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="sTableLock/file"/></td>
									<td><xsl:value-of select="sTableLock/base"/></td>
								</xsl:otherwise>
							</xsl:choose>						
						</tr>
					</xsl:for-each>									
					
				</table>	
				
			</xsl:if>
	

			<xsl:if test=" count(//Columns/Column[*/state/text()='diff'])  &gt; 0 ">
				<br/>
				<br/>
				<h4>Информация по отличиям в колонках</h4>
				
	
				<table class="table table-striped table-bordered" >
					<tr align="center">
						<td><b>Column</b></td>
						<td><b>Owner</b></td>
						<td colspan="2"><b>DataType</b></td>
						<td colspan="2"><b>DataLength</b></td>
						<td colspan="2"><b>DataPrecision</b></td>
						<td colspan="2"><b>DataScale</b></td>
						<td colspan="2"><b>Nullable</b></td>
						<td colspan="2"><b>DefaultLength</b></td>
					</tr>
					<xsl:for-each select="//Columns/Column[@place='base']">			
						<tr align="center">
							<td align="left"><span class="label label-warning">Колонка есть только в базе</span><xsl:value-of select="sTableName"/>.<xsl:value-of select="sColumnName"/></td>
							<td><xsl:value-of select="sOwner/base"/></td>
							<td colspan="2"><xsl:value-of select="sDataType/base"/></td>
							<td colspan="2"><xsl:value-of select="iDataLength/base"/></td>
							<td colspan="2"><xsl:value-of select="iDataPrecision/base"/></td>
							<td colspan="2"><xsl:value-of select="iDataScale/base"/></td>
							<td colspan="2"><xsl:value-of select="sNullable/base"/></td>
							<td colspan="2"><xsl:value-of select="iDefaultLength/base"/></td>
						</tr>
					</xsl:for-each>
					<xsl:for-each select="//Columns/Column[@place='file']">			
						<tr align="center">
							<td align="left"><span class="label label-danger">Колонка есть только в файле</span><xsl:value-of select="sTableName"/>.<xsl:value-of select="sColumnName"/></td>
							<td><xsl:value-of select="sOwner/file"/></td>
							<td colspan="2"><xsl:value-of select="sDataType/file"/></td>
							<td colspan="2"><xsl:value-of select="iDataLength/file"/></td>
							<td colspan="2"><xsl:value-of select="iDataPrecision/file"/></td>
							<td colspan="2"><xsl:value-of select="iDataScale/file"/></td>
							<td colspan="2"><xsl:value-of select="sNullable/file"/></td>
							<td colspan="2"><xsl:value-of select="iDefaultLength/file"/></td>
						</tr>
					</xsl:for-each>
					
					<xsl:if test=" count(//Columns/Column[*/state/text()='diff' and @place='both'])  &gt; 0 ">
						<tr align="center">
							<td colspan="2">Изменились свойства</td>
							<td><small>file</small></td>
							<td><small>db</small></td>
							<td><small>file</small></td>
							<td><small>db</small></td>
							<td><small>file</small></td>
							<td><small>db</small></td>
							<td><small>file</small></td>
							<td><small>db</small></td>
							<td><small>file</small></td>
							<td><small>db</small></td>
							<td><small>file</small></td>
							<td><small>db</small></td>
						</tr> 
					</xsl:if>
					
					<xsl:for-each select="//Columns/Column[*/state/text()='diff' and @place='both']">			
						<tr align="center">
							<td align="left"><span class="label label-info">Изменились свойства колонки</span><xsl:value-of select="sTableName"/>.<xsl:value-of select="sColumnName"/></td>
							<td><xsl:value-of select="sOwner/file"/></td>
	
							<xsl:choose>
								<xsl:when test="sDataType/state = 'diff'">
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sDataType/file"/></td>
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sDataType/base"/></td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="sDataType/file"/></td>
									<td><xsl:value-of select="sDataType/base"/></td>
								</xsl:otherwise>
							</xsl:choose>
	
							<xsl:choose>
								<xsl:when test="iDataLength/state = 'diff'">
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="iDataLength/file"/></td>
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="iDataLength/base"/></td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="iDataLength/file"/></td>
									<td><xsl:value-of select="iDataLength/base"/></td>
								</xsl:otherwise>
							</xsl:choose>
	
							<xsl:choose>
								<xsl:when test="iDataPrecision/state = 'diff'">
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="iDataPrecision/file"/></td>
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="iDataPrecision/base"/></td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="iDataPrecision/file"/></td>
									<td><xsl:value-of select="iDataPrecision/base"/></td>
								</xsl:otherwise>
							</xsl:choose>
							
							<xsl:choose>
								<xsl:when test="iDataScale/state = 'diff'">
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="iDataScale/file"/></td>
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="iDataScale/base"/></td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="iDataScale/file"/></td>
									<td><xsl:value-of select="iDataScale/base"/></td>
								</xsl:otherwise>
							</xsl:choose>						
													
							<xsl:choose>
								<xsl:when test="sNullable/state = 'diff'">
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sNullable/file"/></td>
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="sNullable/base"/></td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="sNullable/file"/></td>
									<td><xsl:value-of select="sNullable/base"/></td>
								</xsl:otherwise>
							</xsl:choose>						
	
							<xsl:choose>
								<xsl:when test="iDefaultLength/state = 'diff'">
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="iDefaultLength/file"/></td>
									<td style="background-color: #fcf8e3 !important;"><xsl:value-of select="iDefaultLength/base"/></td>
								</xsl:when>
								<xsl:otherwise>
									<td><xsl:value-of select="iDefaultLength/file"/></td>
									<td><xsl:value-of select="iDefaultLength/base"/></td>
								</xsl:otherwise>
							</xsl:choose>						
	
						</tr>
					</xsl:for-each>				
				</table>
			</xsl:if>
		</body>
	</xsl:template>
</xsl:stylesheet>
