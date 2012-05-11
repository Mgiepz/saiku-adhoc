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

import javax.swing.table.TableModel;

import org.pentaho.platform.engine.core.solution.SimpleParameterProvider;
import org.pentaho.reporting.engine.classic.core.DataRow;
import org.pentaho.reporting.engine.classic.core.ParameterMapping;
import org.pentaho.reporting.engine.classic.core.ReportDataFactoryException;
import org.pentaho.reporting.engine.classic.core.util.TypedTableModel;
import org.pentaho.reporting.engine.classic.extensions.datasources.cda.CdaQueryBackend;
import org.pentaho.reporting.engine.classic.extensions.datasources.cda.CdaQueryEntry;
import org.pentaho.reporting.engine.classic.extensions.datasources.cda.CdaResponseParser;

import pt.webdetails.cda.dataaccess.InvalidParameterException;
import pt.webdetails.cda.utils.FormulaEvaluator;
import pt.webdetails.cda.utils.Util;
/**
 * @author mg
 *
 */

public class SaikuCdaQueryBackend extends CdaQueryBackend
{

	private final static String FORMULA_BEGIN = "${";
	private final static String FORMULA_END = "}";

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



	//TODO: Remove this after the official jar has been patched
	@Override
	public synchronized TableModel queryData(final CdaQueryEntry realQuery, final DataRow parameters)
	throws ReportDataFactoryException
	{
		if (realQuery == null)
		{
			throw new NullPointerException("Query is null."); //$NON-NLS-1$
		}

		final TypedTableModel parameterModel = fetchParameter(parameters, realQuery);
		// name = 0
		// type = 1
		// defaultValue = 2
		// pattern = 3
		final HashMap<String, String> extraParams = new HashMap<String, String>();
		extraParams.put("dataAccessId", realQuery.getId());

		final int nameIdx = parameterModel.findColumn("name");
		final int typeIdx = parameterModel.findColumn("type");
		final int defaultValueIdx = parameterModel.findColumn("defaultValue");
		final int patternIdx = parameterModel.findColumn("pattern");
		for (int p = 0; p < parameterModel.getRowCount(); p++)
		{
			final String alias = (String) parameterModel.getValueAt(p, nameIdx);
			final String type = (String) parameterModel.getValueAt(p, typeIdx);
			final String pattern = (String) parameterModel.getValueAt(p, patternIdx);
			final ParameterMapping[] parameterMapping = realQuery.getParameters();
			int i = 0;
			while ((i < parameterMapping.length) && !(parameterMapping[i].getAlias().contentEquals(alias))) {
				/* loop over till we find the right alias for name */
				i+=1;
			}

			String name = alias; /* alias not found , set to alias for backward compatibility*/
			if (i < parameterMapping.length) {
				name = parameterMapping[i].getName();
			}
			//      final String alias = realQuery.getDeclaredParameters();

			// if parameter is null, use default value from cda
			Object value;
			try {
				value = parameters.get(name) == null ? getRealDefaultValue(parameterModel.getValueAt(p, defaultValueIdx)) : parameters.get(name);
			} catch (InvalidParameterException e) {
				throw new ReportDataFactoryException("Malformed formula expression in cda defaultvalue");
			}

			final String param = parameterToString(alias, type, pattern, value);
			//set parameter like CDA Style "param<parameterName>"
			extraParams.put("param" + alias, param);
		}

		return fetchData(parameters, "doQuery", extraParams);
	}

	public Object getRealDefaultValue(Object objValue) throws InvalidParameterException
	{

		if(objValue instanceof String){//may be a string or a parsed value
			final String strValue = (String) objValue;
			//check if it is a formula
			if(strValue != null && strValue.trim().startsWith(FORMULA_BEGIN))
			{
				String formula = Util.getContentsBetween(strValue, FORMULA_BEGIN, FORMULA_END);
				if(formula == null)
				{
					throw new InvalidParameterException("Malformed formula expression", null);
				}
				Object value = FormulaEvaluator.processFormula(formula);
				return value;

			}
			return strValue;
		}
		else return objValue;
	}


}
