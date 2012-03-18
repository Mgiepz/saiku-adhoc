/**
 * 
 */
package org.saiku.adhoc.service.cda;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.pentaho.platform.engine.core.solution.SimpleParameterProvider;
import org.pentaho.reporting.engine.classic.core.DataRow;
import org.pentaho.reporting.engine.classic.core.ReportDataFactoryException;
import org.pentaho.reporting.engine.classic.core.util.TypedTableModel;
import org.pentaho.reporting.engine.classic.extensions.datasources.cda.CdaQueryBackend;
import org.pentaho.reporting.engine.classic.extensions.datasources.cda.CdaResponseParser;
/**
 * @author mg
 *
 */

public class SaikuCdaQueryBackend extends CdaQueryBackend
{
	public SaikuCdaQueryBackend()
	{
	}

	public TypedTableModel fetchData(final DataRow dataRow, final String method,
			final Map<String, String> extraParameter)
	throws ReportDataFactoryException
	{
		{
			try
			{
				final Map<String, Object> parameters = new HashMap<String, Object>();

				final Set<Entry<String, String>> paramterSet = extraParameter.entrySet();
				for (final Entry<String, String> entry : paramterSet)
				{
					parameters.put(entry.getKey(), entry.getValue());
				}

				parameters.put("outputType", "xml");
				parameters.put("solution", encodeParameter(getSolution()));
				parameters.put("path", encodeParameter(getPath()));
				parameters.put("file", encodeParameter(getFile()));

				final String responseBody = PluginUtils.callPlugin("cda", method, new SimpleParameterProvider(parameters));


				// convert String into InputStream
				final InputStream responseBodyIs = new ByteArrayInputStream(responseBody.getBytes("UTF-8"));

				return CdaResponseParser.performParse(responseBodyIs);
			}
			catch (UnsupportedEncodingException use)
			{
				throw new ReportDataFactoryException("Failed to encode parameter", use);
			}
			catch (Exception e)
			{
				throw new ReportDataFactoryException("Failed to send request", e);
			}
		}
	}


}
