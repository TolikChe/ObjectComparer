<?xml version="1.0"?>
<xsl:stylesheet version="2.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema">
	<xsl:template match="/">
		<head>
<!--			<meta http-equiv="X-UA-Compatible" content="IE=edge">
			<meta name="viewport" content="width=device-width, initial-scale=1">
-->
			<title>Bootstrap 101 Template</title>
			<!-- Bootstrap -->
			<link rel="stylesheet" type="text/css" href="css/bootstrap.css"/>
			<link rel="stylesheet" type="text/css" href="css/bootstrap-theme.css"/>
			<script type="text/javascript" src="js/json3.min.js">//</script>
			<script type="text/javascript" src="js/jquery.js">//</script>
			<script type="text/javascript" src="js/bootstrap.js">//</script>

		</head>
		<body>
			<blockquote>
				<h4>Всего таблиц в базе и файле: <xsl:value-of select="count(//Table[@place='both'])"/>
				</h4>
				<h4>Всего таблиц в базе: <xsl:value-of select="count(//Table[@place='base'])"/>
				</h4>
				<h4>Всего таблиц в файле: <xsl:value-of select="count(//Table[@place='file'])"/>
				</h4>
			</blockquote>
			
			<blockquote>
				<h4>В базе и файле</h4>
			</blockquote>			
			<div class="panel-group" id="tblAccordion">
			<!--	<div class="panel panel-default">				-->
				<xsl:for-each select="//Tables/Table[@place='both']">
					<div class="panel-heading" style="background: #EBFFEA;">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#tblAccordion" href="#collapse{sTableName}"><xsl:value-of select="sTableName"/></a>
						</h4>
					</div>
					<div id="collapse{sTableName}" class="panel-collapse collapse">
						<div class="panel-body">
							<table class="table" style="width: 30%;">
								<tr>
									<th>Property</th>
									<th>Values from file</th>
									<th>Values from base</th>
								</tr>
								<xsl:choose>
									<xsl:when test="sOwner/state = 'same'">
										<tr class="success">
											<td>Owner</td>
											<td><xsl:value-of select="sOwner/file"/></td>
											<td><xsl:value-of select="sOwner/base"/></td>
										</tr>
									</xsl:when>
									<xsl:otherwise>
										<tr class="danger">
											<td>Owner</td>
											<td><xsl:value-of select="sOwner/file"/></td>
											<td><xsl:value-of select="sOwner/base"/></td>
										</tr>										
									</xsl:otherwise>
								</xsl:choose>								
								<xsl:choose>
									<xsl:when test="sStatus/state = 'same'">
										<tr class="success">
											<td>Status</td>
											<td><xsl:value-of select="sStatus/file"/></td>
											<td><xsl:value-of select="sStatus/base"/></td>
										</tr>
									</xsl:when>
									<xsl:otherwise>
										<tr class="danger">
											<td>Status</td>
											<td><xsl:value-of select="sStatus/file"/></td>
											<td><xsl:value-of select="sStatus/base"/></td>
										</tr>										
									</xsl:otherwise>
								</xsl:choose>	
								<xsl:choose>
									<xsl:when test="sPartitioned/state = 'same'">
										<tr class="success">
											<td>Partitioned</td>
											<td><xsl:value-of select="sPartitioned/file"/></td>
											<td><xsl:value-of select="sPartitioned/base"/></td>
										</tr>
									</xsl:when>
									<xsl:otherwise>
										<tr class="danger">
											<td>Partitioned</td>
											<td><xsl:value-of select="sPartitioned/file"/></td>
											<td><xsl:value-of select="sPartitioned/base"/></td>
										</tr>										
									</xsl:otherwise>
								</xsl:choose>								

								<xsl:choose>
									<xsl:when test="sTemporary/state = 'same'">
										<tr class="success">
											<td>Temporary</td>
											<td><xsl:value-of select="sTemporary/file"/></td>
											<td><xsl:value-of select="sTemporary/base"/></td>
										</tr>
									</xsl:when>
									<xsl:otherwise>
										<tr class="danger">
											<td>Temporary</td>
											<td><xsl:value-of select="sTemporary/file"/></td>
											<td><xsl:value-of select="sTemporary/base"/></td>
										</tr>										
									</xsl:otherwise>
								</xsl:choose>								

								<xsl:choose>
									<xsl:when test="sCompression/state = 'same'">
										<tr class="success">
											<td>Compression</td>
											<td><xsl:value-of select="sCompression/file"/></td>
											<td><xsl:value-of select="sCompression/base"/></td>
										</tr>
									</xsl:when>
									<xsl:otherwise>
										<tr class ="danger">
											<td>Compression</td>
											<td><xsl:value-of select="sCompression/file"/></td>
											<td><xsl:value-of select="sCompression/base"/></td>
										</tr>										
									</xsl:otherwise>
								</xsl:choose>									

								<xsl:choose>
									<xsl:when test="sLogging/state = 'same'">
										<tr class="success">
											<td>Logging</td>
											<td><xsl:value-of select="sLogging/file"/></td>
											<td><xsl:value-of select="sLogging/base"/></td>
										</tr>
									</xsl:when>
									<xsl:otherwise>
										<tr class ="danger">
											<td>Logging</td>
											<td><xsl:value-of select="sLogging/file"/></td>
											<td><xsl:value-of select="sLogging/base"/></td>
										</tr>										
									</xsl:otherwise>
								</xsl:choose>

								<xsl:choose>
									<xsl:when test="sCache/state = 'same'">
										<tr class="success">
											<td>Cache</td>
											<td><xsl:value-of select="sCache/file"/></td>
											<td><xsl:value-of select="sCache/base"/></td>
										</tr>
									</xsl:when>
									<xsl:otherwise>
										<tr class ="danger">
											<td>Cache</td>
											<td><xsl:value-of select="sCache/file"/></td>
											<td><xsl:value-of select="sCache/base"/></td>
										</tr>										
									</xsl:otherwise>
								</xsl:choose>								
								
								<xsl:choose>
									<xsl:when test="sTableLock/state = 'same'">
										<tr class="success">
											<td>TableLock</td>
											<td><xsl:value-of select="sTableLock/file"/></td>
											<td><xsl:value-of select="sTableLock/base"/></td>
										</tr>
									</xsl:when>
									<xsl:otherwise>
										<tr class ="danger">
											<td>TableLock</td>
											<td><xsl:value-of select="sTableLock/file"/></td>
											<td><xsl:value-of select="sTableLock/base"/></td>
										</tr>										
									</xsl:otherwise>
								</xsl:choose>									
							</table>

							<table class="table">
								<tr>
									<th>Колонки</th>
								</tr>
								<tr>
									<td>Колонки и в базе и в файле</td>
								</tr>
								<tr>
									<td>
										<xsl:for-each select="Columns/Column[@place='both']">
											<div class="panel-heading" style="background: #EBFFEA;">
												<h4 class="panel-title">
													<a data-toggle="collapse" data-parent="#col{sTableName}Accordion" href="#collapse{sTableName}{sColumnName}"><xsl:value-of select="sColumnName"/></a>
												</h4>
											</div>
											<div id="collapse{sTableName}{sColumnName}" class="panel-collapse collapse">
												<div class="panel-body">
													<table class="table" style="width: 30%;">
														<tr>
															<th>Properties</th>
															<th>Values from file</th>
															<th>Values from base</th>
														</tr>
														<xsl:choose>
															<xsl:when test="sDataType/state = 'same'">
																<tr class="success">
																	<td>DataType</td>
																	<td><xsl:value-of select="sDataType/file"/></td>
																	<td><xsl:value-of select="sDataType/base"/></td>
																</tr>
															</xsl:when>
															<xsl:otherwise>
																<tr class ="danger">
																	<td>DataType</td>
																	<td><xsl:value-of select="sDataType/file"/></td>
																	<td><xsl:value-of select="sDataType/base"/></td>
																</tr>										
															</xsl:otherwise>
														</xsl:choose>	
														
														<xsl:choose>
															<xsl:when test="iDataLength/state = 'same'">
																<tr class="success">
																	<td>DataLength</td>
																	<td><xsl:value-of select="iDataLength/file"/></td>
																	<td><xsl:value-of select="iDataLength/base"/></td>
																</tr>
															</xsl:when>
															<xsl:otherwise>
																<tr class ="danger">
																	<td>DataLength</td>
																	<td><xsl:value-of select="iDataLength/file"/></td>
																	<td><xsl:value-of select="iDataLength/base"/></td>
																</tr>										
															</xsl:otherwise>
														</xsl:choose>	
														
														<xsl:choose>
															<xsl:when test="iDataPrecision/state = 'same'">
																<tr class="success">
																	<td>DataPrecision</td>
																	<td><xsl:value-of select="iDataPrecision/file"/></td>
																	<td><xsl:value-of select="iDataPrecision/base"/></td>
																</tr>
															</xsl:when>
															<xsl:otherwise>
																<tr class ="danger">
																	<td>DataPrecision</td>
																	<td><xsl:value-of select="iDataPrecision/file"/></td>
																	<td><xsl:value-of select="iDataPrecision/base"/></td>
																</tr>										
															</xsl:otherwise>
														</xsl:choose>				
														
														<xsl:choose>
															<xsl:when test="iDataScale/state = 'same'">
																<tr class="success">
																	<td>DataScale</td>
																	<td><xsl:value-of select="iDataScale/file"/></td>
																	<td><xsl:value-of select="iDataScale/base"/></td>
																</tr>
															</xsl:when>
															<xsl:otherwise>
																<tr class ="danger">
																	<td>DataScale</td>
																	<td><xsl:value-of select="iDataScale/file"/></td>
																	<td><xsl:value-of select="iDataScale/base"/></td>
																</tr>										
															</xsl:otherwise>
														</xsl:choose>																										

														<xsl:choose>
															<xsl:when test="sNullable/state = 'same'">
																<tr class="success">
																	<td>Nullable</td>
																	<td><xsl:value-of select="sNullable/file"/></td>
																	<td><xsl:value-of select="sNullable/base"/></td>
																</tr>
															</xsl:when>
															<xsl:otherwise>
																<tr class ="danger">
																	<td>Nullable</td>
																	<td><xsl:value-of select="sNullable/file"/></td>
																	<td><xsl:value-of select="sNullable/base"/></td>
																</tr>										
															</xsl:otherwise>
														</xsl:choose>	

														<xsl:choose>
															<xsl:when test="iDefaultLength/state = 'same'">
																<tr class="success">
																	<td>DefaultLength</td>
																	<td><xsl:value-of select="iDefaultLength/file"/></td>
																	<td><xsl:value-of select="iDefaultLength/base"/></td>
																</tr>
															</xsl:when>
															<xsl:otherwise>
																<tr class ="danger">
																	<td>DefaultLength</td>
																	<td><xsl:value-of select="iDefaultLength/file"/></td>
																	<td><xsl:value-of select="iDefaultLength/base"/></td>
																</tr>										
															</xsl:otherwise>
														</xsl:choose>	
													</table>
												</div>
											</div>
										</xsl:for-each>
									</td>
								</tr>		
								<tr>
									<td>Колонки только в файле</td>
								</tr>
								<tr>
									<td>
										<xsl:for-each select="Columns/Column[@place='file']">
											<div class="panel-heading" style="background: #F8DADA;">
												<h4 class="panel-title">
													<a data-toggle="collapse" data-parent="#col{sTableName}Accordion" href="#collapse{sTableName}{sColumnName}"><xsl:value-of select="sColumnName"/></a>
												</h4>
											</div>
											<div id="collapse{sTableName}{sColumnName}" class="panel-collapse collapse">
												<div class="panel-body">
													<table class="table" style="width: 30%;">
														<tr>
															<th>Properties</th>
															<th>Values from file</th>
														</tr>
														<tr>
															<td>DataType</td>
															<td><xsl:value-of select="sDataType/file"/></td>
														</tr>
														<tr>
															<td>DataLength</td>
															<td><xsl:value-of select="iDataLength/file"/></td>
														</tr>
														<tr>
															<td>DataPrecision</td>
															<td><xsl:value-of select="iDataPrecision/file"/></td>
														</tr>
														<tr>
															<td>DataScale</td>
															<td><xsl:value-of select="iDataScale/file"/></td>
														</tr>
														<tr>
															<td>Nullable</td>
															<td><xsl:value-of select="sNullable/file"/></td>
														</tr>
														<tr>
															<td>DefaultLength</td>
															<td><xsl:value-of select="iDefaultLength/file"/></td>
														</tr>
													</table>
												</div>
											</div>
										</xsl:for-each>
									</td>
								</tr>																
								<tr>
									<td>Колонки только в базе</td>
								</tr>								
								<tr>
									<td>
										<xsl:for-each select="Columns/Column[@place='base']">
											<div class="panel-heading" style="background: #F8DADA;">
												<h4 class="panel-title">
													<a data-toggle="collapse" data-parent="#col{sTableName}Accordion" href="#collapse{sTableName}{sColumnName}"><xsl:value-of select="sColumnName"/></a>
												</h4>
											</div>
											<div id="collapse{sTableName}{sColumnName}" class="panel-collapse collapse">
												<div class="panel-body">
													<table class="table" style="width: 30%;">
														<tr>
															<th>Properties</th>
															<th>Values from base</th>
														</tr>
														<tr>
															<td>DataType</td>
															<td><xsl:value-of select="sDataType/base"/></td>
														</tr>
														<tr>
															<td>DataLength</td>
															<td><xsl:value-of select="iDataLength/base"/></td>
														</tr>
														<tr>
															<td>DataPrecision</td>
															<td><xsl:value-of select="iDataPrecision/base"/></td>
														</tr>
														<tr>
															<td>DataScale</td>
															<td><xsl:value-of select="iDataScale/base"/></td>
														</tr>
														<tr>
															<td>Nullable</td>
															<td><xsl:value-of select="sNullable/base"/></td>
														</tr>
														<tr>
															<td>DefaultLength</td>
															<td><xsl:value-of select="iDefaultLength/base"/></td>
														</tr>
													</table>
												</div>
											</div>
										</xsl:for-each>
									</td>
								</tr>																								

							</table> 
							
							
							
						</div>
					</div>
				</xsl:for-each>				

				<blockquote>
					<h4>Только в файле</h4>
				</blockquote>			
				<xsl:for-each select="//Tables/Table[@place='file']">
					<div class="panel-heading" style="background: #F8DADA;">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#tblAccordion" href="#collapse{sTableName}"><xsl:value-of select="sTableName"/></a>
						</h4>
					</div>
					<div id="collapse{sTableName}" class="panel-collapse collapse">
						<div class="panel-body">
							<table class="table" style="width: 30%;">
								<tr>
									<th>Properties</th>
									<th>Values from file</th>
								</tr>
								<tr>
									<td>Owner</td>
									<td>Вася</td>
								</tr>
							</table>
						</div>
					</div>
				</xsl:for-each>								
				
				<blockquote>
					<h4>Только в базе</h4>
				</blockquote>			
				<xsl:for-each select="//Tables/Table[@place='base']">
					<div class="panel-heading" style="background: #FFFACB;">
						<h4 class="panel-title">
							<a data-toggle="collapse" data-parent="#tblAccordion" href="#collapse{sTableName}"><xsl:value-of select="sTableName"/></a>
						</h4>
					</div>
					<div id="collapse{sTableName}" class="panel-collapse collapse">
						<div class="panel-body">
							<table class="table" style="width: 30%;">
								<tr>
									<th>Properties</th>
									<th>Values from base</th>
								</tr>
								<tr>
									<td>Owner</td>
									<td>sOwner/file</td>
								</tr>
							</table>
						</div>
					</div>
				</xsl:for-each>								

<!--			</div>				-->
				
			</div>
		</body>
	</xsl:template>
</xsl:stylesheet>
