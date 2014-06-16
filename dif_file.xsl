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
-->
			<br/>
			<xsl:if test=" count(//Tables/Table[*/state/text()='diff'])  = 0">
				<h4>Существенных отличий не найдено</h4>
			</xsl:if>
			
			<div class="panel-group" id="accordionTablesTop">
				<xsl:if test="count(//Table[@place='file']) &gt; 0">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordionTablesTop" href="#collapseJustFileTables">Таблицы только в файле (<xsl:value-of select="count(//Table[@place='file'])"/>)</a>
							</h4>
						</div>
						<div id="collapseJustFileTables" class="panel-collapse collapse">
							<div class="panel-body">
								<table class="table table-striped table-bordered">
									<tr align="center">
										<td><b>TableName</b></td>	
										<td><b>Owner</b></td>
										<td><b>Status</b></td>
										<td><b>Partitioned</b></td>																						
										<td><b>Temporary</b></td>																						
										<td><b>Compression</b></td>																						
										<td><b>Logging</b></td>																						
										<td><b>Cache</b></td>																						
										<td><b>TableLock</b></td>	
									</tr>
									<xsl:for-each select="//Table[@place='file']">
										<tr align="center">		
											<td>					
												<button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#collapseJustFile{sTableName}"><xsl:value-of select="sTableName"/></button>
											</td>
											<td><small><xsl:value-of select="sOwner/file"/></small></td>
											<td><small><xsl:value-of select="sStatus/file"/></small></td>
											<td><small><xsl:value-of select="sPartitioned/file"/></small></td>																						
											<td><small><xsl:value-of select="sTemporary/file"/></small></td>
											<td><small><xsl:value-of select="sCompression/file"/></small></td>
											<td><small><xsl:value-of select="sLogging/file"/></small></td>
											<td><small><xsl:value-of select="sCache/file"/></small></td>
											<td><small><xsl:value-of select="sTableLock/file"/></small></td>
										</tr>	
										<tr>
											<td colspan="9">
												<div id="collapseJustFile{sTableName}" class="collapse">
													<table class="table table-striped table-bordered">
														<tr align="center">
															<td><b>Column</b></td>
															<td><b>Owner</b></td>
															<td><b>DataType</b></td>
															<td><b>DataLength</b></td>
															<td><b>DataPrecision</b></td>
															<td><b>DataScale</b></td>
															<td><b>Nullable</b></td>
															<td><b>DefaultLength</b></td>
														</tr>
														<xsl:for-each select="Columns/Column">
															<tr align="center">
																<td align="left"><xsl:value-of select="sColumnName"/></td>
																<td><small><xsl:value-of select="sOwner/file"/></small></td>
																<td><small><xsl:value-of select="sDataType/file"/></small></td>
																<td><small><xsl:value-of select="iDataLength/file"/></small></td>
																<td><small><xsl:value-of select="iDataPrecision/file"/></small></td>
																<td><small><xsl:value-of select="iDataScale/file"/></small></td>
																<td><small><xsl:value-of select="sNullable/file"/></small></td>
																<td><small><xsl:value-of select="iDefaultLength/file"/></small></td>
															</tr>
														</xsl:for-each>
													</table>
												</div>
											</td>
										</tr>
									</xsl:for-each>
								</table>
							</div>
						</div>
					</div>
				</xsl:if>
				<xsl:if test="count(//Table[@place='base']) &gt; 0">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordionTablesTop" href="#collapseJustBaseTables">Таблицы только в базе (<xsl:value-of select="count(//Table[@place='base'])"/>)</a>
							</h4>
						</div>
						<div id="collapseJustBaseTables" class="panel-collapse collapse">
							<div class="panel-body">
								<table class="table table-striped table-bordered">
									<tr align="center">
										<td><b>TableName</b></td>	
										<td><b>Owner</b></td>
										<td><b>Status</b></td>
										<td><b>Partitioned</b></td>																						
										<td><b>Temporary</b></td>																						
										<td><b>Compression</b></td>																						
										<td><b>Logging</b></td>																						
										<td><b>Cache</b></td>																						
										<td><b>TableLock</b></td>	
									</tr>
									<xsl:for-each select="//Table[@place='base']">
										<tr align="center">		
											<td>					
												<button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#collapseJustBase{sTableName}"><xsl:value-of select="sTableName"/></button>
											</td>
											<td><small><xsl:value-of select="sOwner/base"/></small></td>
											<td><small><xsl:value-of select="sStatus/base"/></small></td>
											<td><small><xsl:value-of select="sPartitioned/base"/></small></td>																						
											<td><small><xsl:value-of select="sTemporary/base"/></small></td>																						
											<td><small><xsl:value-of select="sCompression/base"/></small></td>																						
											<td><small><xsl:value-of select="sLogging/base"/></small></td>																						
											<td><small><xsl:value-of select="sCache/base"/></small></td>																						
											<td><small><xsl:value-of select="sTableLock/base"/></small></td>																						
										</tr>	
										<tr>
											<td colspan="9">
												<div id="collapseJustBase{sTableName}" class="collapse">
													<table class="table table-striped table-bordered">
														<tr align="center">
															<td><b>Column</b></td>
															<td><b>Owner</b></td>
															<td><b>DataType</b></td>
															<td><b>DataLength</b></td>
															<td><b>DataPrecision</b></td>
															<td><b>DataScale</b></td>
															<td><b>Nullable</b></td>
															<td><b>DefaultLength</b></td>
														</tr>
														<xsl:for-each select="Columns/Column">
															<tr align="center">
																<td align="left"><xsl:value-of select="sColumnName"/></td>
																<td><small><xsl:value-of select="sOwner/base"/></small></td>
																<td><small><xsl:value-of select="sDataType/base"/></small></td>
																<td><small><xsl:value-of select="iDataLength/base"/></small></td>
																<td><small><xsl:value-of select="iDataPrecision/base"/></small></td>
																<td><small><xsl:value-of select="iDataScale/base"/></small></td>
																<td><small><xsl:value-of select="sNullable/base"/></small></td>
																<td><small><xsl:value-of select="iDefaultLength/base"/></small></td>
															</tr>
														</xsl:for-each>
													</table>
												</div>
											</td>
										</tr>
									</xsl:for-each>
								</table>
							</div>
						</div>
					</div>
				</xsl:if>
				<xsl:if test="count(//Table[*/state/text()='diff' and @place='both']) &gt; 0">	
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordionTablesTop" href="#accordionTablesWithDifProperties">Таблицы с изменениями в свойствах (<xsl:value-of select="count(//Table[*/state/text()='diff' and @place='both'])"/>)</a>
							</h4>
						</div>
						<div id="accordionTablesWithDifProperties" class="panel-collapse collapse">
							<div class="panel-body">
								<table class="table table-striped table-bordered">
									<tr align="center">
										<td><b>TableName</b></td>	
										<td colspan="2"><b>Owner</b></td>
										<td colspan="2"><b>Status</b></td>
										<td colspan="2"><b>Partitioned</b></td>																						
										<td colspan="2"><b>Temporary</b></td>																						
										<td colspan="2"><b>Compression</b></td>																						
										<td colspan="2"><b>Logging</b></td>																						
										<td colspan="2"><b>Cache</b></td>																						
										<td colspan="2"><b>TableLock</b></td>	
									</tr>
									<tr align="center">
										<td></td>	
										<td>file</td>
										<td>base</td>
										<td>file</td>
										<td>base</td>
										<td>file</td>
										<td>base</td>
										<td>file</td>
										<td>base</td>
										<td>file</td>
										<td>base</td>
										<td>file</td>
										<td>base</td>
										<td>file</td>
										<td>base</td>
										<td>file</td>
										<td>base</td>
									</tr>									
									<xsl:for-each select="//Table[*/state/text()='diff' and @place='both']">
										<tr align="center">		
											<td>					
												<button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#collapseDifProperties{sTableName}"><xsl:value-of select="sTableName"/></button>
											</td>
											<xsl:choose>
												<xsl:when test="sOwner/state = 'diff'">
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sOwner/file"/></small>
													</td>
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sOwner/base"/></small>
													</td>
												</xsl:when>
												<xsl:otherwise>
													<td>
														<small><xsl:value-of select="sOwner/file"/></small>
													</td>
													<td>
														<small><xsl:value-of select="sOwner/base"/></small>
													</td>
												</xsl:otherwise>
											</xsl:choose>											
											<xsl:choose>
												<xsl:when test="sStatus/state = 'diff'">
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sStatus/file"/></small>
													</td>
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sStatus/base"/></small>
													</td>
												</xsl:when>
												<xsl:otherwise>
													<td>
														<small><xsl:value-of select="sStatus/file"/></small>
													</td>
													<td>
														<small><xsl:value-of select="sStatus/base"/></small>
													</td>
												</xsl:otherwise>
											</xsl:choose>
											<xsl:choose>
												<xsl:when test="sPartitioned/state = 'diff'">
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sPartitioned/file"/></small>
													</td>
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sPartitioned/base"/></small>
													</td>
												</xsl:when>
												<xsl:otherwise>
													<td>
														<small><xsl:value-of select="sPartitioned/file"/></small>
													</td>
													<td>
														<small><xsl:value-of select="sPartitioned/base"/></small>
													</td>
												</xsl:otherwise>
											</xsl:choose>
											<xsl:choose>
												<xsl:when test="sTemporary/state = 'diff'">
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sTemporary/file"/></small>
													</td>
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sTemporary/base"/></small>
													</td>
												</xsl:when>
												<xsl:otherwise>
													<td>
														<small><xsl:value-of select="sTemporary/file"/></small>
													</td>
													<td>
														<small><xsl:value-of select="sTemporary/base"/></small>
													</td>
												</xsl:otherwise>
											</xsl:choose>																				
											<xsl:choose>
												<xsl:when test="sCompression/state = 'diff'">
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sCompression/file"/></small>
													</td>
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sCompression/base"/></small>
													</td>
												</xsl:when>
												<xsl:otherwise>
													<td>
														<small><xsl:value-of select="sCompression/file"/></small>
													</td>
													<td>
														<small><xsl:value-of select="sCompression/base"/></small>
													</td>
												</xsl:otherwise>
											</xsl:choose>																					
											<xsl:choose>
												<xsl:when test="sLogging/state = 'diff'">
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sLogging/file"/></small>
													</td>
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sLogging/base"/></small>
													</td>
												</xsl:when>
												<xsl:otherwise>
													<td>
														<small><xsl:value-of select="sLogging/file"/></small>
													</td>
													<td>
														<small><xsl:value-of select="sLogging/base"/></small>
													</td>
												</xsl:otherwise>
											</xsl:choose>																					
											<xsl:choose>
												<xsl:when test="sCache/state = 'diff'">
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sCache/file"/></small>
													</td>
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sCache/base"/></small>
													</td>
												</xsl:when>
												<xsl:otherwise>
													<td>
														<small><xsl:value-of select="sCache/file"/></small>
													</td>
													<td>
														<small><xsl:value-of select="sCache/base"/></small>
													</td>
												</xsl:otherwise>
											</xsl:choose>																					
											<xsl:choose>
												<xsl:when test="sTableLock/state = 'diff'">
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sTableLock/file"/></small>
													</td>
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sTableLock/base"/></small>
													</td>
												</xsl:when>
												<xsl:otherwise>
													<td>
														<small><xsl:value-of select="sTableLock/file"/></small>
													</td>
													<td>
														<small><xsl:value-of select="sTableLock/base"/></small>
													</td>
												</xsl:otherwise>
											</xsl:choose>																					
										</tr>	
										<tr>
											<td colspan="17">
												<div id="collapseDifProperties{sTableName}" class="collapse">
													<table class="table table-striped table-bordered">
														<tr align="center">
															<td><b>Column</b></td>
															<td colspan="2"><b>DataType</b></td>
															<td colspan="2"><b>DataLength</b></td>
															<td colspan="2"><b>DataPrecision</b></td>
															<td colspan="2"><b>DataScale</b></td>
															<td colspan="2"><b>Nullable</b></td>
															<td colspan="2"><b>DefaultLength</b></td>
														</tr>
														<tr align="center">
															<td></td>
															<td>file</td>
															<td>base</td>
															<td>file</td>
															<td>base</td>															
															<td>file</td>
															<td>base</td>
															<td>file</td>
															<td>base</td>
															<td>file</td>
															<td>base</td>
															<td>file</td>
															<td>base</td>
														</tr>														
														<xsl:for-each select="Columns/Column">
															<tr align="center">
																<td align="left"><xsl:value-of select="sColumnName"/></td>
																<xsl:choose>
																	<xsl:when test="sDataType/state = 'diff'">
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="sDataType/file"/></small>
																		</td>
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="sDataType/base"/></small>
																		</td>
																	</xsl:when>
																	<xsl:otherwise>
																		<td>
																			<small><xsl:value-of select="sDataType/file"/></small>
																		</td>
																		<td>
																			<small><xsl:value-of select="sDataType/base"/></small>
																		</td>
																	</xsl:otherwise>
																</xsl:choose>																
																<xsl:choose>
																	<xsl:when test="iDataLength/state = 'diff'">
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="iDataLength/file"/></small>
																		</td>
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="iDataLength/base"/></small>
																		</td>
																	</xsl:when>
																	<xsl:otherwise>
																		<td>
																			<small><xsl:value-of select="iDataLength/file"/></small>
																		</td>
																		<td>
																			<small><xsl:value-of select="iDataLength/base"/></small>
																		</td>
																	</xsl:otherwise>
																</xsl:choose>
																<xsl:choose>
																	<xsl:when test="iDataPrecision/state = 'diff'">
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="iDataPrecision/file"/></small>
																		</td>
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="iDataPrecision/base"/></small>
																		</td>
																	</xsl:when>
																	<xsl:otherwise>
																		<td>
																			<small><xsl:value-of select="iDataPrecision/file"/></small>
																		</td>
																		<td>
																			<small><xsl:value-of select="iDataPrecision/base"/></small>
																		</td>
																	</xsl:otherwise>
																</xsl:choose>
																<xsl:choose>
																	<xsl:when test="iDataScale/state = 'diff'">
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="iDataScale/file"/></small>
																		</td>
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="iDataScale/base"/></small>
																		</td>
																	</xsl:when>
																	<xsl:otherwise>
																		<td>
																			<small><xsl:value-of select="iDataScale/file"/></small>
																		</td>
																		<td>
																			<small><xsl:value-of select="iDataScale/base"/></small>
																		</td>
																	</xsl:otherwise>
																</xsl:choose>
																<xsl:choose>
																	<xsl:when test="sNullable/state = 'diff'">
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="sNullable/file"/></small>
																		</td>
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="sNullable/base"/></small>
																		</td>
																	</xsl:when>
																	<xsl:otherwise>
																		<td>
																			<small><xsl:value-of select="sNullable/file"/></small>
																		</td>
																		<td>
																			<small><xsl:value-of select="sNullable/base"/></small>
																		</td>
																	</xsl:otherwise>
																</xsl:choose>
																<xsl:choose>
																	<xsl:when test="iDefaultLength/state = 'diff'">
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="iDefaultLength/file"/></small>
																		</td>
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="iDefaultLength/base"/></small>
																		</td>
																	</xsl:when>
																	<xsl:otherwise>
																		<td>
																			<small><xsl:value-of select="iDefaultLength/file"/></small>
																		</td>
																		<td>
																			<small><xsl:value-of select="iDefaultLength/base"/></small>
																		</td>
																	</xsl:otherwise>
																</xsl:choose>
															</tr>
														</xsl:for-each>
														<xsl:for-each select="Columns/Column[@place='file']">
															<tr align="center">
																<td align="left"><span class="label label-warning">Только в файле</span> <xsl:value-of select="sColumnName"/></td>
																<td colspan="2"><small><xsl:value-of select="sDataType/file"/></small></td>
																<td colspan="2"><small><xsl:value-of select="iDataLength/file"/></small></td>
																<td colspan="2"><small><xsl:value-of select="iDataPrecision/file"/></small></td>
																<td colspan="2"><small><xsl:value-of select="iDataScale/file"/></small></td>
																<td colspan="2"><small><xsl:value-of select="sNullable/file"/></small></td>
																<td colspan="2"><small><xsl:value-of select="iDefaultLength/file"/></small></td>
															</tr>
														</xsl:for-each>
														<xsl:for-each select="Columns/Column[@place='base']">
															<tr align="center">
																<td align="left"><span class="label label-warning">Только в базе</span> <xsl:value-of select="sColumnName"/></td>
																<td colspan="2"><small><xsl:value-of select="sDataType/base"/></small></td>
																<td colspan="2"><small><xsl:value-of select="iDataLength/base"/></small></td>
																<td colspan="2"><small><xsl:value-of select="iDataPrecision/base"/></small></td>
																<td colspan="2"><small><xsl:value-of select="iDataScale/base"/></small></td>
																<td colspan="2"><small><xsl:value-of select="sNullable/base"/></small></td>
																<td colspan="2"><small><xsl:value-of select="iDefaultLength/base"/></small></td>
															</tr>
														</xsl:for-each>														
														
													</table>
												</div>
											</td>
										</tr>
									</xsl:for-each>
								</table>
							</div>
						</div>
					</div>
				</xsl:if>				
				<xsl:if test="count(//Table[@place='both' and Columns/Column/*/state/text()='diff']) &gt; 0">					
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordionTablesTop" href="#accordionTablesWithDifColumns">Таблицы с изменениями в колонках (<xsl:value-of select="count(//Table[@place='both' and Columns/Column/*/state/text()='diff'])"/>)</a>
							</h4>
						</div>
						<div id="accordionTablesWithDifColumns" class="panel-collapse collapse">
							<div class="panel-body">
								<table class="table table-striped table-bordered">
									<tr align="center">
										<td><b>TableName</b></td>	
										<td colspan="2"><b>Owner</b></td>
										<td colspan="2"><b>Status</b></td>
										<td colspan="2"><b>Partitioned</b></td>																						
										<td colspan="2"><b>Temporary</b></td>																						
										<td colspan="2"><b>Compression</b></td>																						
										<td colspan="2"><b>Logging</b></td>																						
										<td colspan="2"><b>Cache</b></td>																						
										<td colspan="2"><b>TableLock</b></td>	
									</tr>
									<tr align="center">
										<td></td>	
										<td>file</td>
										<td>base</td>
										<td>file</td>
										<td>base</td>
										<td>file</td>
										<td>base</td>
										<td>file</td>
										<td>base</td>
										<td>file</td>
										<td>base</td>
										<td>file</td>
										<td>base</td>
										<td>file</td>
										<td>base</td>
										<td>file</td>
										<td>base</td>
									</tr>									
									<xsl:for-each select="//Table[@place='both' and Columns/Column/*/state/text()='diff']">
										<tr align="center">		
											<td>					
												<button type="button" class="btn btn-primary" data-toggle="collapse" data-target="#collapseDifColumns{sTableName}"><xsl:value-of select="sTableName"/></button>
											</td>
											<xsl:choose>
												<xsl:when test="sOwner/state = 'diff'">
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sOwner/file"/></small>
													</td>
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sOwner/base"/></small>
													</td>
												</xsl:when>
												<xsl:otherwise>
													<td>
														<small><xsl:value-of select="sOwner/file"/></small>
													</td>
													<td>
														<small><xsl:value-of select="sOwner/base"/></small>
													</td>
												</xsl:otherwise>
											</xsl:choose>											
											<xsl:choose>
												<xsl:when test="sStatus/state = 'diff'">
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sStatus/file"/></small>
													</td>
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sStatus/base"/></small>
													</td>
												</xsl:when>
												<xsl:otherwise>
													<td>
														<small><xsl:value-of select="sStatus/file"/></small>
													</td>
													<td>
														<small><xsl:value-of select="sStatus/base"/></small>
													</td>
												</xsl:otherwise>
											</xsl:choose>
											<xsl:choose>
												<xsl:when test="sPartitioned/state = 'diff'">
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sPartitioned/file"/></small>
													</td>
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sPartitioned/base"/></small>
													</td>
												</xsl:when>
												<xsl:otherwise>
													<td>
														<small><xsl:value-of select="sPartitioned/file"/></small>
													</td>
													<td>
														<small><xsl:value-of select="sPartitioned/base"/></small>
													</td>
												</xsl:otherwise>
											</xsl:choose>
											<xsl:choose>
												<xsl:when test="sTemporary/state = 'diff'">
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sTemporary/file"/></small>
													</td>
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sTemporary/base"/></small>
													</td>
												</xsl:when>
												<xsl:otherwise>
													<td>
														<small><xsl:value-of select="sTemporary/file"/></small>
													</td>
													<td>
														<small><xsl:value-of select="sTemporary/base"/></small>
													</td>
												</xsl:otherwise>
											</xsl:choose>																				
											<xsl:choose>
												<xsl:when test="sCompression/state = 'diff'">
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sCompression/file"/></small>
													</td>
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sCompression/base"/></small>
													</td>
												</xsl:when>
												<xsl:otherwise>
													<td>
														<small><xsl:value-of select="sCompression/file"/></small>
													</td>
													<td>
														<small><xsl:value-of select="sCompression/base"/></small>
													</td>
												</xsl:otherwise>
											</xsl:choose>																					
											<xsl:choose>
												<xsl:when test="sLogging/state = 'diff'">
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sLogging/file"/></small>
													</td>
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sLogging/base"/></small>
													</td>
												</xsl:when>
												<xsl:otherwise>
													<td>
														<small><xsl:value-of select="sLogging/file"/></small>
													</td>
													<td>
														<small><xsl:value-of select="sLogging/base"/></small>
													</td>
												</xsl:otherwise>
											</xsl:choose>																					
											<xsl:choose>
												<xsl:when test="sCache/state = 'diff'">
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sCache/file"/></small>
													</td>
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sCache/base"/></small>
													</td>
												</xsl:when>
												<xsl:otherwise>
													<td>
														<small><xsl:value-of select="sCache/file"/></small>
													</td>
													<td>
														<small><xsl:value-of select="sCache/base"/></small>
													</td>
												</xsl:otherwise>
											</xsl:choose>																					
											<xsl:choose>
												<xsl:when test="sTableLock/state = 'diff'">
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sTableLock/file"/></small>
													</td>
													<td style="background-color: #fcf8e3 !important;">
														<small><xsl:value-of select="sTableLock/base"/></small>
													</td>
												</xsl:when>
												<xsl:otherwise>
													<td>
														<small><xsl:value-of select="sTableLock/file"/></small>
													</td>
													<td>
														<small><xsl:value-of select="sTableLock/base"/></small>
													</td>
												</xsl:otherwise>
											</xsl:choose>																					
										</tr>	
										<tr>
											<td colspan="17">
												<div id="collapseDifColumns{sTableName}" class="collapse">
													<table class="table table-striped table-bordered">
														<tr align="center">
															<td><b>Column</b></td>
															<td colspan="2"><b>DataType</b></td>
															<td colspan="2"><b>DataLength</b></td>
															<td colspan="2"><b>DataPrecision</b></td>
															<td colspan="2"><b>DataScale</b></td>
															<td colspan="2"><b>Nullable</b></td>
															<td colspan="2"><b>DefaultLength</b></td>
														</tr>
														<tr align="center">
															<td></td>
															<td>file</td>
															<td>base</td>
															<td>file</td>
															<td>base</td>															
															<td>file</td>
															<td>base</td>
															<td>file</td>
															<td>base</td>
															<td>file</td>
															<td>base</td>
															<td>file</td>
															<td>base</td>
														</tr>														
														<xsl:for-each select="Columns/Column[@place='both']">
															<tr align="center">
																<td align="left"><xsl:value-of select="sColumnName"/></td>
																<xsl:choose>
																	<xsl:when test="sDataType/state = 'diff'">
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="sDataType/file"/></small>
																		</td>
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="sDataType/base"/></small>
																		</td>
																	</xsl:when>
																	<xsl:otherwise>
																		<td>
																			<small><xsl:value-of select="sDataType/file"/></small>
																		</td>
																		<td>
																			<small><xsl:value-of select="sDataType/base"/></small>
																		</td>
																	</xsl:otherwise>
																</xsl:choose>																
																<xsl:choose>
																	<xsl:when test="iDataLength/state = 'diff'">
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="iDataLength/file"/></small>
																		</td>
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="iDataLength/base"/></small>
																		</td>
																	</xsl:when>
																	<xsl:otherwise>
																		<td>
																			<small><xsl:value-of select="iDataLength/file"/></small>
																		</td>
																		<td>
																			<small><xsl:value-of select="iDataLength/base"/></small>
																		</td>
																	</xsl:otherwise>
																</xsl:choose>
																<xsl:choose>
																	<xsl:when test="iDataPrecision/state = 'diff'">
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="iDataPrecision/file"/></small>
																		</td>
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="iDataPrecision/base"/></small>
																		</td>
																	</xsl:when>
																	<xsl:otherwise>
																		<td>
																			<small><xsl:value-of select="iDataPrecision/file"/></small>
																		</td>
																		<td>
																			<small><xsl:value-of select="iDataPrecision/base"/></small>
																		</td>
																	</xsl:otherwise>
																</xsl:choose>
																<xsl:choose>
																	<xsl:when test="iDataScale/state = 'diff'">
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="iDataScale/file"/></small>
																		</td>
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="iDataScale/base"/></small>
																		</td>
																	</xsl:when>
																	<xsl:otherwise>
																		<td>
																			<small><xsl:value-of select="iDataScale/file"/></small>
																		</td>
																		<td>
																			<small><xsl:value-of select="iDataScale/base"/></small>
																		</td>
																	</xsl:otherwise>
																</xsl:choose>
																<xsl:choose>
																	<xsl:when test="sNullable/state = 'diff'">
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="sNullable/file"/></small>
																		</td>
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="sNullable/base"/></small>
																		</td>
																	</xsl:when>
																	<xsl:otherwise>
																		<td>
																			<small><xsl:value-of select="sNullable/file"/></small>
																		</td>
																		<td>
																			<small><xsl:value-of select="sNullable/base"/></small>
																		</td>
																	</xsl:otherwise>
																</xsl:choose>
																<xsl:choose>
																	<xsl:when test="iDefaultLength/state = 'diff'">
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="iDefaultLength/file"/></small>
																		</td>
																		<td style="background-color: #fcf8e3 !important;">
																			<small><xsl:value-of select="iDefaultLength/base"/></small>
																		</td>
																	</xsl:when>
																	<xsl:otherwise>
																		<td>
																			<small><xsl:value-of select="iDefaultLength/file"/></small>
																		</td>
																		<td>
																			<small><xsl:value-of select="iDefaultLength/base"/></small>
																		</td>
																	</xsl:otherwise>
																</xsl:choose>
															</tr>
														</xsl:for-each>
														<xsl:for-each select="Columns/Column[@place='file']">
															<tr align="center">
																<td align="left"><span class="label label-danger">Только в файле</span> <xsl:value-of select="sColumnName"/></td>
																<td colspan="2"><small><xsl:value-of select="sDataType/file"/></small></td>
																<td colspan="2"><small><xsl:value-of select="iDataLength/file"/></small></td>
																<td colspan="2"><small><xsl:value-of select="iDataPrecision/file"/></small></td>
																<td colspan="2"><small><xsl:value-of select="iDataScale/file"/></small></td>
																<td colspan="2"><small><xsl:value-of select="sNullable/file"/></small></td>
																<td colspan="2"><small><xsl:value-of select="iDefaultLength/file"/></small></td>
															</tr>
														</xsl:for-each>
														<xsl:for-each select="Columns/Column[@place='base']">
															<tr align="center">
																<td align="left"><span class="label label-danger">Только в базе</span> <xsl:value-of select="sColumnName"/></td>
																<td colspan="2"><small><xsl:value-of select="sDataType/base"/></small></td>
																<td colspan="2"><small><xsl:value-of select="iDataLength/base"/></small></td>
																<td colspan="2"><small><xsl:value-of select="iDataPrecision/base"/></small></td>
																<td colspan="2"><small><xsl:value-of select="iDataScale/base"/></small></td>
																<td colspan="2"><small><xsl:value-of select="sNullable/base"/></small></td>
																<td colspan="2"><small><xsl:value-of select="iDefaultLength/base"/></small></td>
															</tr>
														</xsl:for-each>														
													</table>
												</div>
											</td>
										</tr>
									</xsl:for-each>
								</table>
							</div>
						</div>
					</div>
				</xsl:if>

<!--
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordionTablesTop" href="#accordionTablesWithDifIndexes">
					  Таблицы с изменениями в индексах
					</a>
						</h4>
					</div>
					<div id="accordionTablesWithDifIndexes" class="panel-collapse collapse">
						<div class="panel-body">
					Таблицы с изменениями в индексах
				  </div>
					</div>
				</div>
				<div class="panel panel-default">
					<div class="panel-heading">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#accordionTablesTop" href="#accordionTablesWithDifTriggers">
					  Таблицы с изменениями в триггерах
					</a>
						</h4>
					</div>
					<div id="accordionTablesWithDifTriggers" class="panel-collapse collapse">
						<div class="panel-body">
					Таблицы с изменениями в триггерах
				  </div>
					</div>
				</div>
-->
			</div>


		</body>
	</xsl:template>
</xsl:stylesheet>
